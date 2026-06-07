package cr.ac.una.proyecto.model;
import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UsersConfigPK implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "USER_USERS")
    private Long userUsers;
    @Column(name = "USER_CONFIG")
    private Long userConfig;
    @Column(name = "USER_CONFIGGENERAL")
    private Long userGeneral;

    public UsersConfigPK() {
    }

    public UsersConfigPK(Long userUsers, Long userConfig, Long userGeneral) {
        this.userUsers = userUsers;
        this.userConfig = userConfig;
         this.userGeneral = userGeneral;
        
    }

    public Long getUserUsers() { return userUsers; }
    public void setUserUsers(Long userUsers) { this.userUsers = userUsers; }

    public Long getUserConfig() { return userConfig; }
    public void setUserConfig(Long userConfig) { this.userConfig = userConfig; }

    public Long getUserGeneral() {return userGeneral; }

    public void setUserGeneral(Long userGeneral) {this.userGeneral = userGeneral;  }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UsersConfigPK other = (UsersConfigPK) obj;
        return Objects.equals(this.userUsers, other.userUsers) && 
               Objects.equals(this.userConfig, other.userConfig) &&
               Objects.equals(this.userGeneral, other.userGeneral); 
    }

    @Override
    public int hashCode() { return Objects.hash(userUsers, userConfig,userGeneral); }
}