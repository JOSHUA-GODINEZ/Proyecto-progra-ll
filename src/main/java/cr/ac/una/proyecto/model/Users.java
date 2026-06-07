package cr.ac.una.proyecto.model;
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
/**
 *
 * @author joshu
 */
@Entity
@Table(name = "USERS")
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserId", query = "SELECT u FROM Users u WHERE u.userId = :userId"),
    @NamedQuery(name = "Users.findByUserName", query = "SELECT u FROM Users u WHERE u.userName = :userName"),
    @NamedQuery(name = "Users.findByUserImage", query = "SELECT u FROM Users u WHERE u.userAvatar = :userAvatar"),
    @NamedQuery(name = "Users.findByUserVersion", query = "SELECT u FROM Users u WHERE u.userVersion = :userVersion")})

public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_USERS_GEN")
@SequenceGenerator(name = "SEC_USERS_GEN", sequenceName = "USER_USERID_SEQ01", allocationSize = 1)
@Column(name = "USER_ID")
private Long userId;
@Basic(optional = false)
@Column(name = "USER_NAME", unique = true, length = 100)
private String userName;
@Lob
@Column(name = "USER_AVATAR")
private byte[] userAvatar;
@Basic(optional = false)
@Column(name = "USER_VERSION")
@Version
private Long userVersion;
@OneToMany(cascade = CascadeType.ALL, mappedBy = "userUsers", fetch = FetchType.LAZY)
private List<UsersConfig> usersConfigList;

    public Users() {
    }

    public Users(Long userId) {
        this.userId = userId;
    }

    public Users(Long userId, String userName, byte[] userAvatar, Long userVersion) {
        this.userId = userId;
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.userVersion = userVersion;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

public byte[] getUserAvatar() { return userAvatar; }
public void setUserAvatar(byte[] userAvatar) { this.userAvatar = userAvatar; }

    public Long getUserVersion() {
        return userVersion;
    }

    public void setUserVersion(Long userVersion) {
        this.userVersion = userVersion;
    }

    public List<UsersConfig> getUsersConfigList() {
        return usersConfigList;
    }

    public void setUsersConfigList(List<UsersConfig> usersConfigList) {
        this.usersConfigList = usersConfigList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyecto.model.Users[ userId=" + userId + " ]";
    }
    
}
