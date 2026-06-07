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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
/**
 *
 * @author joshu
 */
@Entity
@Table(name = "USERS_CONFIG_LEVEL")
@NamedQueries({
    @NamedQuery(name = "UsersConfigLevel.findAll", query = "SELECT u FROM UsersConfigLevel u"),
    @NamedQuery(name = "UsersConfigLevel.findByGameId", query = "SELECT u FROM UsersConfigLevel u WHERE u.gameId = :gameId"),
    @NamedQuery(name = "UsersConfigLevel.findByUserLevel", query = "SELECT u FROM UsersConfigLevel u WHERE u.userLevel = :userLevel"),
    @NamedQuery(name = "UsersConfigLevel.findByUserLevelGundamage", query = "SELECT u FROM UsersConfigLevel u WHERE u.userLevelGundamage = :userLevelGundamage"),
    @NamedQuery(name = "UsersConfigLevel.findByUserLevelGunspeed", query = "SELECT u FROM UsersConfigLevel u WHERE u.userLevelGunspeed = :userLevelGunspeed"),
    @NamedQuery(name = "UsersConfigLevel.findByUserLevelBaseheath", query = "SELECT u FROM UsersConfigLevel u WHERE u.userLevelBaseheath = :userLevelBaseheath"),
    @NamedQuery(name = "UsersConfigLevel.findByUserLevelCapacityelixir", query = "SELECT u FROM UsersConfigLevel u WHERE u.userLevelCapacityelixir = :userLevelCapacityelixir"),
    @NamedQuery(name = "UsersConfigLevel.findByUserLevelSpeedelixir", query = "SELECT u FROM UsersConfigLevel u WHERE u.userLevelSpeedelixir = :userLevelSpeedelixir"),
    @NamedQuery(name = "UsersConfigLevel.findByUserLevelPowerbombdamage", query = "SELECT u FROM UsersConfigLevel u WHERE u.userLevelPowerbombdamage = :userLevelPowerbombdamage"),
    @NamedQuery(name = "UsersConfigLevel.findByUserLevelPowerbombrange", query = "SELECT u FROM UsersConfigLevel u WHERE u.userLevelPowerbombrange = :userLevelPowerbombrange"),
    @NamedQuery(name = "UsersConfigLevel.findByUserLevelPowerfreezeduration", query = "SELECT u FROM UsersConfigLevel u WHERE u.userLevelPowerfreezeduration = :userLevelPowerfreezeduration"),
    @NamedQuery(name = "UsersConfigLevel.findByUserLevelPowerfreezerange", query = "SELECT u FROM UsersConfigLevel u WHERE u.userLevelPowerfreezerange = :userLevelPowerfreezerange"),
    @NamedQuery(name = "UsersConfigLevel.findByUserPoints", query = "SELECT u FROM UsersConfigLevel u WHERE u.userPoints = :userPoints")})
public class UsersConfigLevel implements Serializable {
    private static final long serialVersionUID = 1L;
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_USERS_CONFIG_LEVEL_GEN")
@SequenceGenerator(name = "SEC_USERS_CONFIG_LEVEL_GEN", sequenceName = "SEC_USERS_CONFIG_LEVEL", allocationSize = 1 )
@Basic(optional = false)
@Column(name = "GAME_ID")
private Long gameId;
    @Basic(optional = false)
    @Column(name = "USER_LEVEL")
    private Long userLevel=1L;
    @Basic(optional = false)
    @Column(name = "USER_LEVEL_GUNDAMAGE")
    private Long userLevelGundamage=1L;
    @Basic(optional = false)
    @Column(name = "USER_LEVEL_GUNSPEED")
    private Long userLevelGunspeed=1L;
    @Basic(optional = false)
    @Column(name = "USER_LEVEL_BASEHEATH")
    private Long userLevelBaseheath=1L;
    @Basic(optional = false)
    @Column(name = "USER_LEVEL_CAPACITYELIXIR")
    private Long userLevelCapacityelixir=1L;
    @Basic(optional = false)
    @Column(name = "USER_LEVEL_SPEEDELIXIR")
    private Long userLevelSpeedelixir=1L;
    @Basic(optional = false)
    @Column(name = "USER_LEVEL_POWERBOMBDAMAGE")
    private Long userLevelPowerbombdamage=1L;
    @Basic(optional = false)
    @Column(name = "USER_LEVEL_POWERBOMBRANGE")
    private Long userLevelPowerbombrange=1L;
    @Basic(optional = false)
    @Column(name = "USER_LEVEL_POWERFREEZEDURATION")
    private Long userLevelPowerfreezeduration=1L;
    @Basic(optional = false)
    @Column(name = "USER_LEVEL_POWERFREEZERANGE")
    private Long userLevelPowerfreezerange=1L;
    @Basic(optional = false)
    @Column(name = "USER_POINTS")
    private Long userPoints=0L;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userConfig", fetch = FetchType.LAZY)
    private List<UsersConfig> usersConfigList;

    public UsersConfigLevel() {
    }

    public UsersConfigLevel(Long gameId) {
        this.gameId = gameId;
    }

    public UsersConfigLevel(Long gameId, Long userLevel, Long userLevelGundamage, Long userLevelGunspeed, Long userLevelBaseheath, Long userLevelCapacityelixir, Long userLevelSpeedelixir, Long userLevelPowerbombdamage, Long userLevelPowerbombrange, Long userLevelPowerfreezeduration, Long userLevelPowerfreezerange, Long userPoints) {
        this.gameId = gameId;
        this.userLevel = userLevel;
        this.userLevelGundamage = userLevelGundamage;
        this.userLevelGunspeed = userLevelGunspeed;
        this.userLevelBaseheath = userLevelBaseheath;
        this.userLevelCapacityelixir = userLevelCapacityelixir;
        this.userLevelSpeedelixir = userLevelSpeedelixir;
        this.userLevelPowerbombdamage = userLevelPowerbombdamage;
        this.userLevelPowerbombrange = userLevelPowerbombrange;
        this.userLevelPowerfreezeduration = userLevelPowerfreezeduration;
        this.userLevelPowerfreezerange = userLevelPowerfreezerange;
        this.userPoints = userPoints;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Long userLevel) {
        this.userLevel = userLevel;
    }

    public Long getUserLevelGundamage() {
        return userLevelGundamage;
    }

    public void setUserLevelGundamage(Long userLevelGundamage) {
        this.userLevelGundamage = userLevelGundamage;
    }

    public Long getUserLevelGunspeed() {
        return userLevelGunspeed;
    }

    public void setUserLevelGunspeed(Long userLevelGunspeed) {
        this.userLevelGunspeed = userLevelGunspeed;
    }

    public Long getUserLevelBaseheath() {
        return userLevelBaseheath;
    }

    public void setUserLevelBaseheath(Long userLevelBaseheath) {
        this.userLevelBaseheath = userLevelBaseheath;
    }

    public Long getUserLevelCapacityelixir() {
        return userLevelCapacityelixir;
    }

    public void setUserLevelCapacityelixir(Long userLevelCapacityelixir) {
        this.userLevelCapacityelixir = userLevelCapacityelixir;
    }

    public Long getUserLevelSpeedelixir() {
        return userLevelSpeedelixir;
    }

    public void setUserLevelSpeedelixir(Long userLevelSpeedelixir) {
        this.userLevelSpeedelixir = userLevelSpeedelixir;
    }

    public Long getUserLevelPowerbombdamage() {
        return userLevelPowerbombdamage;
    }

    public void setUserLevelPowerbombdamage(Long userLevelPowerbombdamage) {
        this.userLevelPowerbombdamage = userLevelPowerbombdamage;
    }

    public Long getUserLevelPowerbombrange() {
        return userLevelPowerbombrange;
    }

    public void setUserLevelPowerbombrange(Long userLevelPowerbombrange) {
        this.userLevelPowerbombrange = userLevelPowerbombrange;
    }

    public Long getUserLevelPowerfreezeduration() {
        return userLevelPowerfreezeduration;
    }

    public void setUserLevelPowerfreezeduration(Long userLevelPowerfreezeduration) {
        this.userLevelPowerfreezeduration = userLevelPowerfreezeduration;
    }

    public Long getUserLevelPowerfreezerange() {
        return userLevelPowerfreezerange;
    }

    public void setUserLevelPowerfreezerange(Long userLevelPowerfreezerange) {
        this.userLevelPowerfreezerange = userLevelPowerfreezerange;
    }

    public Long getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(Long userPoints) {
        this.userPoints = userPoints;
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
        hash += (gameId != null ? gameId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersConfigLevel)) {
            return false;
        }
        UsersConfigLevel other = (UsersConfigLevel) object;
        if ((this.gameId == null && other.gameId != null) || (this.gameId != null && !this.gameId.equals(other.gameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyecto.model.UsersConfigLevel[ gameId=" + gameId + " ]";
    }
    
}
