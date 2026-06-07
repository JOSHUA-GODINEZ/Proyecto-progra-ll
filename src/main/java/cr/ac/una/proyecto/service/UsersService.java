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
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;



public class UsersService {

    // 🔥 SOLUCIÓN 1: Eliminamos el 'final' y la asignación estática inmediata
    private EntityManager em;
    private EntityTransaction et;

    // 🔥 SOLUCIÓN 2: Creamos un método privado de control para asegurar que 'em' nunca sea null
    private void verificarConexion() {
        if (this.em == null || !this.em.isOpen()) {
            this.em = EntityManagerHelper.getManager();
        }
    }
public UsersDto obtenerUsuario(Long userId) {
    verificarConexion();
    try {
        Users user = em.find(Users.class, userId);
        if (user != null) {
            em.refresh(user); 
            
            System.out.println("LOG SERVICE: Entidad refrescada directamente desde las tablas de Oracle.");
            
            if (user.getUsersConfigList() != null) {
                user.getUsersConfigList().size(); 
                
                if (!user.getUsersConfigList().isEmpty() && user.getUsersConfigList().get(0).getUserConfig() != null) {
                    em.refresh(user.getUsersConfigList().get(0).getUserConfig());
                    
                    // 🔥 NUEVO: Refrescar la entidad General si existe en la relación
                    if (user.getUsersConfigList().get(0).getUserConfigGeneral() != null) {
                        em.refresh(user.getUsersConfigList().get(0).getUserConfigGeneral());
                    }
                }
            }
            
            UsersDto usersDto = new UsersDto(user);
            
            // Mapeo manual de seguridad
            if ((usersDto.getUsersConfigList() == null || usersDto.getUsersConfigList().isEmpty()) 
                    && user.getUsersConfigList() != null && !user.getUsersConfigList().isEmpty()) {
                
                UsersConfig relacionEntidad = user.getUsersConfigList().get(0);
                UsersConfigDto intermediaDto = new UsersConfigDto(relacionEntidad);
                UsersConfigLevelDto nivelDto = new UsersConfigLevelDto(relacionEntidad.getUserConfig());
                
                // 🔥 NUEVO: Crear e inyectar el DTO General a partir de la entidad física de Oracle
                if (relacionEntidad.getUserConfigGeneral() != null) {
                    UserConfigGeneralDto generalDto = new UserConfigGeneralDto(relacionEntidad.getUserConfigGeneral());
                    intermediaDto.setUsersConfigGeneral(generalDto); // Seteamos el general en la intermedia
                }
                
                intermediaDto.setUsersConfigLevel(nivelDto);
                usersDto.getUsersConfigList().add(intermediaDto);
            }
            
            return usersDto; 
        } else {
            return null;
        }
    } catch (Exception e) {
        System.err.println("LOG SERVICE: Error al obtener el usuario: " + e.getMessage());
        e.printStackTrace();
        return null;
    }
}
    public UsersDto getUser(Long id) {
        verificarConexion(); // Garantiza que 'em' esté vivo antes de usarlo
        try {
            TypedQuery<Users> qryUsers = em.createNamedQuery("Users.findById", Users.class);
            qryUsers.setParameter("id", id);
            
            UsersDto usersDto = new UsersDto(qryUsers.getSingleResult());
            return usersDto;
        } catch (NoResultException ex) {
            System.out.println("LOG: No existe un usuario con el id ingresado [" + id + "].");
            return null;
        } catch (NonUniqueResultException ex) {
            System.err.println("ERROR: Ocurrio un error al consultar el usuario (Duplicado).");
            return null;
        } catch (Exception ex) {
            System.err.println("ERROR obteniendo el usuario [" + id + "]: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public List<Users> getUsers() {
        verificarConexion();
        try {
            TypedQuery<Users> query = em.createQuery("SELECT u FROM Users u", Users.class);
            return query.getResultList();
        } catch (Exception ex) {
            System.err.println("ERROR al obtener la lista de usuarios: " + ex.getMessage());
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

public UsersDto guardarUsuario(UsersDto usersDto) {
    verificarConexion();
    try {
        et = em.getTransaction();
        et.begin();
        
        Users user;
        

        if (usersDto.getUserId() != null && usersDto.getUserId() > 0) {
            user = em.find(Users.class, usersDto.getUserId());
            if (user == null) {
                System.err.println("ERROR: No se encontro el usuario a modificar.");
                et.rollback(); 
                return null;
            }
            
            // 🛡️ VALIDACIÓN PREVIA AL MODIFICAR: Verificar si cambió de nombre y si el nuevo ya existe
            if (!user.getUserName().equalsIgnoreCase(usersDto.getUserName())) {
                Long coincidencia = em.createQuery("SELECT COUNT(u) FROM Users u WHERE LOWER(u.userName) = :name AND u.userId != :id", Long.class)
                        .setParameter("name", usersDto.getUserName().toLowerCase())
                        .setParameter("id", usersDto.getUserId())
                        .getSingleResult();
               if (coincidencia > 0) {
    System.err.println("ERROR INTERNO: El nombre '" + usersDto.getUserName() + "' ya está ocupado por otro jugador.");
    
    if (et.isActive()) {
        et.rollback(); // Cancelamos la transacción de forma segura
    }
    
    // 🔥 EN LUGAR DE 'RETURN NULL', LANZAMOS EL ERROR:
    throw new IllegalArgumentException("NOMBRE_DUPLICADO");
}
            }
            
            user.setUserName(usersDto.getUserName());
            user.setUserAvatar(usersDto.getUserAvatar());
            user.setUserVersion(usersDto.getUserVersion());
            
            if (usersDto.getUsersConfigList() != null && !usersDto.getUsersConfigList().isEmpty()) {
                
                // --- 1. Sincronización de Niveles ---
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

                // --- 2. Sincronización de Configuración General ---
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
                        System.out.println("LOG SERVICE: Atributos de UserConfigGeneral guardados en Oracle con exito.");
                    }
                }
            }
            
            user = em.merge(user);
            em.flush();
            
        } else {
            // ======================================================================
            // CASE B: REGISTRO DE UN NUEVO USUARIO DESDE CERO
            // ======================================================================
            System.out.println("\n🚀 === [REGISTRO SEGURO EN CADENA - SOLUCIÓN DEFAULTS] ===");
            
            // 🛡️ VALIDACIÓN PREVIA AL CREAR: Evita disparar secuencias e inserts si el nombre ya existe
            Long coincidencia = em.createQuery("SELECT COUNT(u) FROM Users u WHERE LOWER(u.userName) = :name", Long.class)
                    .setParameter("name", usersDto.getUserName().toLowerCase())
                    .getSingleResult();
            
            if (coincidencia > 0) {
                 SoundManager.playErrorSound();
                System.err.println("ERROR INTERNO: Intento de registrar un nombre duplicado: " + usersDto.getUserName());
                et.rollback();
                return null; // Retornamos null para que la vista de JavaFX sepa que el nombre ya existía
            }
            
            // 1. Crear y asegurar el Usuario
            user = new Users();
            user.setUserName(usersDto.getUserName());
            user.setUserAvatar(usersDto.getUserAvatar());
            user.setUserVersion(usersDto.getUserVersion());
            em.persist(user); 
            em.flush(); 
            
            // 2. Crear y asegurar la Configuración de Nivel
            UsersConfigLevel nuevoNivel = new UsersConfigLevel();
            em.persist(nuevoNivel); 
            em.flush(); 
            
            // 3. Crear y asegurar la Configuración General
            UserConfigGeneral nuevoGeneral = new UserConfigGeneral();
            em.persist(nuevoGeneral); 
            em.flush(); 
            
            // Capturamos los IDs consolidados
            Long idUsuarioReal = user.getUserId();
            Long idNivelReal = nuevoNivel.getGameId();
            Long idGeneralReal = nuevoGeneral.getUserGeneralId();
            
            System.out.println("👉 IDs confirmados físicamente en Oracle -> User: " + idUsuarioReal + " | Level: " + idNivelReal + " | General: " + idGeneralReal);
            
            // 4. Construimos la Llave Primaria Compuesta
            UsersConfigPK pk = new UsersConfigPK();
            pk.setUserUsers(idUsuarioReal);         
            pk.setUserConfig(idNivelReal);         
            pk.setUserGeneral(idGeneralReal);       

            // 5. Armamos la entidad intermedia
            UsersConfig relacionIntermedia = new UsersConfig();
            relacionIntermedia.setUsersConfigPK(pk); 

            relacionIntermedia.setUserUsers(user);              
            relacionIntermedia.setUserConfig(nuevoNivel);  
            relacionIntermedia.setUserConfigGeneral(nuevoGeneral); 

            // 6. Persistimos de forma segura
            em.persist(relacionIntermedia);
            em.flush();
        }
        
        et.commit();
        System.out.println("LOG: Transaccion comprometida con exito. ¡Estructuras generales salvadas!");
        
        return new UsersDto(user);
        
    } catch (Exception ex) {
        if (et != null && et.isActive()) {
            et.rollback();
        }
        
        // 🛡️ CAPTURA DE RESPALDO DE EXCEPCIONES DE LA BASE DE DATOS
        String errorMsg = ex.getMessage() != null ? ex.getMessage() : "";
        String causaMsg = (ex.getCause() != null && ex.getCause().getMessage() != null) ? ex.getCause().getMessage() : "";
        
        if (errorMsg.contains("UQ_USERS_USERNAME") || causaMsg.contains("unique constraint") || causaMsg.contains("violación de restricción única")) {
            System.err.println("🚨 ADVERTENCIA SERVICE: Oracle rebotó la transacción. El nombre de usuario ya existe.");
             
        } else {
            System.err.println("ERROR GuardarUsuario: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        return null; // Siempre retornamos null en caso de fallo o nombre repetido
    }
}

  
public boolean eliminarUsuarioCompleto(UsersDto usersDto) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNAPU");
    EntityManager em = emf.createEntityManager();
    EntityTransaction et = em.getTransaction();
    
    try {
        et.begin();
        Long userId = usersDto.getUserId();
        System.out.println("LOG SERVICE: Iniciando purga absoluta para el ID Usuario: " + userId);
        
        // 1. Buscamos todas las intermedias relacionadas a este usuario para extraer los IDs de los padres
        List<UsersConfig> intermedias = em.createQuery(
            "SELECT uc FROM UsersConfig uc WHERE uc.userUsers.userId = :userId", UsersConfig.class)
            .setParameter("userId", userId)
            .getResultList();
            
        System.out.println("LOG SERVICE: Registros intermedios encontrados para borrar: " + intermedias.size());
        
        // 2. Si hay configuraciones, extraemos sus IDs de Nivel y General para borrarlos individualmente
        for (UsersConfig config : intermedias) {
            
            // PASO A: Borrar la fila de la tabla intermedia USERS_CONFIG primero
            em.remove(em.merge(config));
            em.flush(); // Registramos el borrado inmediato de la intermedia
            
            // PASO B: Borrar el nivel asociado (USERS_CONFIG_LEVEL)
            if (config.getUserConfig() != null) {
                Long levelId = config.getUserConfig().getGameId();
                em.createQuery("DELETE FROM UsersConfigLevel l WHERE l.gameId = :levelId")
                  .setParameter("levelId", levelId)
                  .executeUpdate();
            }
            
            // PASO C: Borrar la configuración general asociada (USER_CONFIG_GENERAL)
            if (config.getUserConfigGeneral() != null) {
                Long generalId = config.getUserConfigGeneral().getUserGeneralId();
                em.createQuery("DELETE FROM UserConfigGeneral g WHERE g.userGeneralId = :generalId")
                  .setParameter("generalId", generalId)
                  .executeUpdate();
            }
        }
        
        // 3. PASO D: Ahora que no queda NADA amarrado a él, borramos el usuario de la tabla USERS
        System.out.println("LOG SERVICE: Eliminando el usuario maestro de la tabla USERS...");
        em.createQuery("DELETE FROM Users u WHERE u.userId = :userId")
          .setParameter("userId", userId)
          .executeUpdate();
        
        // 4. Consolidamos la transacción completa en Oracle
        et.commit();
        System.out.println("LOG SERVICE: ¡Todo el historial, niveles, generales e intermedia fueron purgados con éxito!");
        return true;
        
    } catch (Exception e) {
        if (et != null && et.isActive()) et.rollback();
        System.err.println("LOG SERVICE: Error crítico en la purga del usuario: " + e.getMessage());
        e.printStackTrace();
        return false;
    } finally {
        if (em != null && em.isOpen()) em.close();
        if (emf != null && emf.isOpen()) emf.close();
    }
}
  public List<UsersDto> obtenerUsuarios() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNAPU");
    EntityManager em = emf.createEntityManager();
    List<UsersDto> listaDtos = new ArrayList<>();
    
    try {
        // ⚡ Usamos "LEFT JOIN FETCH" para traer al usuario y amarrar su lista de configuración de un solo golpe,
        // evitando que de errores de carga diferida (LazyInitializationException) al cerrar el EntityManager.
        String jpql = "SELECT DISTINCT u FROM Users u LEFT JOIN FETCH u.usersConfigList";
        List<Users> listaEntidades = em.createQuery(jpql, Users.class).getResultList();
        
        // Convertimos cada Entidad a su respectivo DTO para la tabla de JavaFX
        for (Users entidad : listaEntidades) {
            UsersDto dto = new UsersDto(entidad); 
            listaDtos.add(dto);
        }
        
        System.out.println("LOG SERVICE: Se recuperaron " + listaDtos.size() + " usuarios de Oracle con éxito.");
        
    } catch (Exception e) {
        System.err.println("LOG SERVICE: Error crítico al obtener la lista de usuarios: " + e.getMessage());
        e.printStackTrace();
    } finally {
        if (em != null && em.isOpen()) em.close();
        if (emf != null && emf.isOpen()) emf.close();
    }
    
    return listaDtos;
}
  
  
  
  
  
  
  
}