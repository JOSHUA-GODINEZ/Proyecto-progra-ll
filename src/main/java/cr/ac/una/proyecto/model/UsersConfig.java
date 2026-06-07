package cr.ac.una.proyecto.model;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "USERS_CONFIG")
public class UsersConfig implements Serializable {

private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected UsersConfigPK usersConfigPK;

    // Relación con Usuario apunta a la columna USER_USERS
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_USERS", referencedColumnName = "USER_ID", insertable = false, updatable = false) 
    private Users userUsers;

    // Relación con Nivel apunta a la columna USER_CONFIG
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_CONFIG", referencedColumnName = "GAME_ID", insertable = false, updatable = false) 
    private UsersConfigLevel userConfig;

    // Relación con Configuración General apunta a USER_CONFIGGENERAL
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_CONFIGGENERAL", referencedColumnName = "USER_GENERAL_ID", insertable = false, updatable = false)
    private UserConfigGeneral userConfigGeneral;
    
    public UsersConfig() {
    }

    public UsersConfig(UsersConfigPK usersConfigPK) {
        this.usersConfigPK = usersConfigPK;
    }

    public UsersConfigPK getUsersConfigPK() { return usersConfigPK; }
    public void setUsersConfigPK(UsersConfigPK usersConfigPK) { this.usersConfigPK = usersConfigPK; }

    public Users getUserUsers() { return userUsers; }
    public void setUserUsers(Users userUsers) { this.userUsers = userUsers; }

    public UsersConfigLevel getUserConfig() { return userConfig; }
    public void setUserConfig(UsersConfigLevel userConfig) { this.userConfig = userConfig; }

    public UserConfigGeneral getUserConfigGeneral() {
        return userConfigGeneral;
    }

    public void setUserConfigGeneral(UserConfigGeneral userConfigGeneral) {
        this.userConfigGeneral = userConfigGeneral;
    }
}