package cr.ac.una.proyecto.service;

import cr.ac.una.proyecto.model.UserConfigGeneral;
import cr.ac.una.proyecto.model.UserConfigGeneralDto;
import cr.ac.una.proyecto.model.Users;
import cr.ac.una.proyecto.model.UsersConfig;
import cr.ac.una.proyecto.model.UsersConfigDto;
import cr.ac.una.proyecto.model.UsersConfigLevel;
import cr.ac.una.proyecto.model.UsersConfigLevelDto;
import cr.ac.una.proyecto.model.UsersConfigPK;
import cr.ac.una.proyecto.model.UsersDto;
import cr.ac.una.proyecto.util.EntityManagerHelper;
import cr.ac.una.proyecto.util.SoundManager;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

public class UsersService {

    private EntityManager em;
    private EntityTransaction et;

    // Asegura que el gestor de entidades este activo antes de cualquier operacion
    private void verificarConexion() {
        if (this.em == null || !this.em.isOpen()) {
            this.em = EntityManagerHelper.getManager();
        }
    }

    // Busca un usuario por su identificador unico y mapea todas sus configuraciones asociadas
    public UsersDto obtenerUsuario(Long userId) {
        verificarConexion();
        try {
            Users user = em.find(Users.class, userId);
            if (user != null) {
                em.refresh(user); 

                
                if (user.getUsersConfigList() != null) {
                    user.getUsersConfigList().size(); 
                    
                    if (!user.getUsersConfigList().isEmpty() && user.getUsersConfigList().get(0).getUserConfig() != null) {
                        em.refresh(user.getUsersConfigList().get(0).getUserConfig());
                        
                        if (user.getUsersConfigList().get(0).getUserConfigGeneral() != null) {
                            em.refresh(user.getUsersConfigList().get(0).getUserConfigGeneral());
                        }
                    }
                }
                
                UsersDto usersDto = new UsersDto(user);
                
                if ((usersDto.getUsersConfigList() == null || usersDto.getUsersConfigList().isEmpty()) 
                        && user.getUsersConfigList() != null && !user.getUsersConfigList().isEmpty()) {
                    
                    UsersConfig relacionEntidad = user.getUsersConfigList().get(0);
                    UsersConfigDto intermediaDto = new UsersConfigDto(relacionEntidad);
                    UsersConfigLevelDto nivelDto = new UsersConfigLevelDto(relacionEntidad.getUserConfig());
                    
                    if (relacionEntidad.getUserConfigGeneral() != null) {
                        UserConfigGeneralDto generalDto = new UserConfigGeneralDto(relacionEntidad.getUserConfigGeneral());
                        intermediaDto.setUsersConfigGeneral(generalDto);
                    }
                    
                    intermediaDto.setUsersConfigLevel(nivelDto);
                    usersDto.getUsersConfigList().add(intermediaDto);
                }
                
                return usersDto; 
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Obtiene un usuario mediante una consulta nombrada por identificador
    public UsersDto getUser(Long id) {
        verificarConexion(); 
        try {
            TypedQuery<Users> qryUsers = em.createNamedQuery("Users.findById", Users.class);
            qryUsers.setParameter("id", id);
            
            UsersDto usersDto = new UsersDto(qryUsers.getSingleResult());
            return usersDto;
        } catch (NoResultException ex) {
            return null;
        } catch (NonUniqueResultException ex) {
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // Retorna la lista completa de entidades de usuarios registradas
    public List<Users> getUsers() {
        verificarConexion();
        try {
            TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u", Users.class);
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Guarda o actualiza un usuario controlando las validaciones de nombres duplicados
public UsersDto guardarUsuario(UsersDto usersDto) {
        verificarConexion();

        if (usersDto.getUserId() != null && usersDto.getUserId() > 0) {

            Users usuarioActual = em.find(Users.class, usersDto.getUserId());
            if (usuarioActual != null && !usuarioActual.getUserName().equalsIgnoreCase(usersDto.getUserName())) {
                Long coincidencia = em.createQuery("SELECT COUNT(u) FROM Users u WHERE LOWER(u.userName) = :name AND u.userId != :id", Long.class)
                        .setParameter("name", usersDto.getUserName().toLowerCase())
                        .setParameter("id", usersDto.getUserId())
                        .getSingleResult();
                if (coincidencia > 0) {
                    throw new IllegalArgumentException("NOMBRE_DUPLICADO");
                }
            }
        }

        try {
            et = em.getTransaction();
            et.begin();
            Users user;
            
            if (usersDto.getUserId() != null && usersDto.getUserId() > 0) {
                user = em.find(Users.class, usersDto.getUserId());
                if (user == null) {
                    if (et.isActive()) et.rollback(); 
                    return null;
                }    
                user.setUserName(usersDto.getUserName());
                user.setUserAvatar(usersDto.getUserAvatar());
                
                if (usersDto.getUsersConfigList() != null && !usersDto.getUsersConfigList().isEmpty()) {
                    UsersConfigLevelDto nivelDto = usersDto.getUsersConfigList().get(0).getUsersConfigLevel();
                    if (nivelDto != null && nivelDto.getGameId() != null) {
                        UsersConfigLevel nivelEntidad = em.find(UsersConfigLevel.class, nivelDto.getGameId());
                        if (nivelEntidad != null) {
                            nivelEntidad.setUserLevel(nivelDto.getUserLevel());
                            nivelEntidad.setUserPoints(nivelDto.getUserPoints());
                            nivelEntidad.setUserLevelBaseheath(nivelDto.getUserLevelBaseheath()); 
                            nivelEntidad.setUserLevelCapacityelixir(nivelDto.getUserLevelCapacityelixir()); 
                            nivelEntidad.setUserLevelSpeedelixir(nivelDto.getUserLevelSpeedelixir());
                            nivelEntidad.setUserLevelGundamage(nivelDto.getUserLevelGundamage()); 
                            nivelEntidad.setUserLevelGunspeed(nivelDto.getUserLevelGunspeed());
                            nivelEntidad.setUserLevelPowerbombdamage(nivelDto.getUserLevelPowerbombdamage());
                            nivelEntidad.setUserLevelPowerbombrange(nivelDto.getUserLevelPowerbombrange());
                            nivelEntidad.setUserLevelPowerfreezeduration(nivelDto.getUserLevelPowerfreezeduration());
                            nivelEntidad.setUserLevelPowerfreezerange(nivelDto.getUserLevelPowerfreezerange());
                            em.merge(nivelEntidad);
                        }
                    }

                    UserConfigGeneralDto generalDto = usersDto.getUsersConfigList().get(0).getUsersConfigGeneral();
                    if (generalDto != null && generalDto.getUserGeneralId() != null) {
                        UserConfigGeneral generalEntidad = em.find(UserConfigGeneral.class, generalDto.getUserGeneralId());
                        if (generalEntidad != null) {
                            generalEntidad.setUserStylegun(generalDto.getUserStylegun());
                            generalEntidad.setUserController(generalDto.getUserController());
                            generalEntidad.setUserGamemode(generalDto.getUserGamemode());
                            generalEntidad.setUserScreen(generalDto.getUserScreen());
                            generalEntidad.setUserLanguage(generalDto.getUserLanguage());
                            generalEntidad.setUserFirsttime(generalDto.getUserFirsttime());
                            generalEntidad.setUserAudiomenu(generalDto.getUserAudiomenu());
                            generalEntidad.setUserAudioenemys(generalDto.getUserAudioenemys());
                            generalEntidad.setUserAudioefect(generalDto.getUserAudioefect());
                            generalEntidad.setUserAudiogun(generalDto.getUserAudiogun());
                            em.merge(generalEntidad);
                        }
                    }
                }
                
                user = em.merge(user);
                em.flush();
                
            } else {

                Long coincidencia = em.createQuery("SELECT COUNT(u) FROM Users u WHERE LOWER(u.userName) = :name", Long.class)
                        .setParameter("name", usersDto.getUserName().toLowerCase())
                        .getSingleResult();
                
                if (coincidencia > 0) {
                    if (et.isActive()) et.rollback();
                    throw new IllegalArgumentException("NOMBRE_DUPLICADO");
                }
                // nuevo usuario
                user = new Users();
                user.setUserName(usersDto.getUserName());
                user.setUserAvatar(usersDto.getUserAvatar());
                em.persist(user); 
                em.flush(); 
                
                UsersConfigLevel nuevoNivel = new UsersConfigLevel();
                em.persist(nuevoNivel); 
                em.flush(); 
                
                UserConfigGeneral nuevoGeneral = new UserConfigGeneral();
                em.persist(nuevoGeneral); 
                em.flush(); 
                
                Long idUsuarioReal = user.getUserId();
                Long idNivelReal = nuevoNivel.getGameId();
                Long idGeneralReal = nuevoGeneral.getUserGeneralId();
                
                UsersConfigPK pk = new UsersConfigPK();
                pk.setUserUsers(idUsuarioReal);         
                pk.setUserConfig(idNivelReal);         
                pk.setUserGeneral(idGeneralReal);       

                UsersConfig relacionIntermedia = new UsersConfig();
                relacionIntermedia.setUsersConfigPK(pk); 
                relacionIntermedia.setUserUsers(user);              
                relacionIntermedia.setUserConfig(nuevoNivel);  
                relacionIntermedia.setUserConfigGeneral(nuevoGeneral); 

                em.persist(relacionIntermedia);
                em.flush();
            }
            
            et.commit();
            return new UsersDto(user);
            
        } catch (IllegalArgumentException ex) {
            if (et != null && et.isActive()) et.rollback();
            throw ex; 
            
        } catch (Exception ex) {
            if (et != null && et.isActive()) et.rollback();
            String mensaje = ex.getMessage() != null ? ex.getMessage().toLowerCase() : "";
            if (mensaje.contains("uq_users_username") || mensaje.contains("unique") || mensaje.contains("unica")) {
                throw new IllegalArgumentException("NOMBRE_DUPLICADO");
            }
            
            if (usersDto.getUserName() == null || usersDto.getUserName().trim().isEmpty()) {
                throw new IllegalArgumentException("NOMBRE_INVALIDO");
            }
            
            throw new RuntimeException("ERROR_SISTEMA: " + ex.getMessage());
        }
    }

    // Elimina por completo un usuario junto con todas sus tablas de configuraciones amarradas
    public boolean eliminarUsuarioCompleto(UsersDto usersDto) {
        verificarConexion();
        try {
            et = em.getTransaction();
            et.begin();
            Long userId = usersDto.getUserId();
            
            List<UsersConfig> intermedias = em.createQuery(
                "SELECT uc FROM UsersConfig uc WHERE uc.userUsers.userId = :userId", UsersConfig.class)
                .setParameter("userId", userId)
                .getResultList();            
            
            for (UsersConfig config : intermedias) {
                em.remove(em.merge(config));
                em.flush(); 
                
                if (config.getUserConfig() != null) {
                    Long levelId = config.getUserConfig().getGameId();
                    em.createQuery("DELETE FROM UsersConfigLevel l WHERE l.gameId = :levelId")
                      .setParameter("levelId", levelId)
                      .executeUpdate();
                }
                
                if (config.getUserConfigGeneral() != null) {
                    Long generalId = config.getUserConfigGeneral().getUserGeneralId();
                    em.createQuery("DELETE FROM UserConfigGeneral g WHERE g.userGeneralId = :generalId")
                      .setParameter("generalId", generalId)
                      .executeUpdate();
                }
            }

            em.createQuery("DELETE FROM Users u WHERE u.userId = :userId")
              .setParameter("userId", userId)
              .executeUpdate();
            
            et.commit();
            return true;
            
        } catch (Exception e) {
            if (et != null && et.isActive()) {
                et.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    // Obtiene la lista total de usuarios en formato DTO para las tablas de JavaFX
    public List<UsersDto> obtenerUsuarios() {
        verificarConexion();
        List<UsersDto> listaDtos = new ArrayList<>();
        try {
            String jpql = "SELECT DISTINCT u FROM Users u LEFT JOIN FETCH u.usersConfigList";
            List<Users> listaEntidades = em.createQuery(jpql, Users.class).getResultList();
            
            for (Users entidad : listaEntidades) {
                UsersDto dto = new UsersDto(entidad); 
                listaDtos.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaDtos;
    }
}