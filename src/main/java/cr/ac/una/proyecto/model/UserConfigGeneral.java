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
@Table(name = "USER_CONFIG_GENERAL")
@NamedQueries({
    @NamedQuery(name = "UserConfigGeneral.findAll", query = "SELECT u FROM UserConfigGeneral u"),
    @NamedQuery(name = "UserConfigGeneral.findByUserGeneralId", query = "SELECT u FROM UserConfigGeneral u WHERE u.userGeneralId = :userGeneralId"),
    @NamedQuery(name = "UserConfigGeneral.findByUserStylegun", query = "SELECT u FROM UserConfigGeneral u WHERE u.userStylegun = :userStylegun"),
    @NamedQuery(name = "UserConfigGeneral.findByUserController", query = "SELECT u FROM UserConfigGeneral u WHERE u.userController = :userController"),
    @NamedQuery(name = "UserConfigGeneral.findByUserGamemode", query = "SELECT u FROM UserConfigGeneral u WHERE u.userGamemode = :userGamemode"),
    @NamedQuery(name = "UserConfigGeneral.findByUserAudiomenu", query = "SELECT u FROM UserConfigGeneral u WHERE u.userAudiomenu = :userAudiomenu"),
    @NamedQuery(name = "UserConfigGeneral.findByUserAudioenemys", query = "SELECT u FROM UserConfigGeneral u WHERE u.userAudioenemys = :userAudioenemys"),
    @NamedQuery(name = "UserConfigGeneral.findByUserAudioefect", query = "SELECT u FROM UserConfigGeneral u WHERE u.userAudioefect = :userAudioefect"),
    @NamedQuery(name = "UserConfigGeneral.findByUserAudiogun", query = "SELECT u FROM UserConfigGeneral u WHERE u.userAudiogun = :userAudiogun"),
    @NamedQuery(name = "UserConfigGeneral.findByUserScreen", query = "SELECT u FROM UserConfigGeneral u WHERE u.userScreen = :userScreen"),
    @NamedQuery(name = "UserConfigGeneral.findByUserLanguage", query = "SELECT u FROM UserConfigGeneral u WHERE u.userLanguage = :userLanguage"),
   
    @NamedQuery(name = "UserConfigGeneral.findByUserFirsttime", query = "SELECT u FROM UserConfigGeneral u WHERE u.userFirsttime = :userFirsttime")})
public class UserConfigGeneral implements Serializable {
    private static final long serialVersionUID = 1L;
  @Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_gen")
@SequenceGenerator(name = "sec_gen", sequenceName = "SEC_USERS_CONFIG_GENERAL", allocationSize = 1)
@Column(name = "USER_GENERAL_ID")
private Long userGeneralId;
    @Basic(optional = false)
    @Column(name = "USER_STYLEGUN")
    private String userStylegun="A";
    @Basic(optional = false)
    @Column(name = "USER_CONTROLLER")
    private String userController="M";
    @Basic(optional = false)
    @Column(name = "USER_GAMEMODE")
    private String userGamemode="N";
    @Basic(optional = false)
    @Column(name = "USER_AUDIOMENU")
    private double userAudiomenu=0.5;
    @Basic(optional = false)
    @Column(name = "USER_AUDIOENEMYS")
    private double userAudioenemys=0.5;
    @Basic(optional = false)
    @Column(name = "USER_AUDIOEFECT")
    private double userAudioefect=0.5;
    @Basic(optional = false)
    @Column(name = "USER_AUDIOGUN")
    private double userAudiogun=0.5;
    @Basic(optional = false)
    @Column(name = "USER_SCREEN")
    private String userScreen="B";
    @Basic(optional = false)
    @Column(name = "USER_LANGUAGE")
    private String userLanguage="S";
    @Basic(optional = false)
    @Column(name = "USER_FIRSTTIME")
    private String userFirsttime="S";
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userConfigGeneral", fetch = FetchType.LAZY)
    private List<UsersConfig> usersConfigList;

    public UserConfigGeneral() {
    }

    public UserConfigGeneral(Long userGeneralId) {
        this.userGeneralId = userGeneralId;
    }

    public UserConfigGeneral(Long userGeneralId, String userStylegun, String userController, String userGamemode, double userAudiomenu, double userAudioenemys, double userAudioefect, double userAudiogun, String userScreen, String userLanguage, String userFirsttime) {
        this.userGeneralId = userGeneralId;
        this.userStylegun = userStylegun;
        this.userController = userController;
        this.userGamemode = userGamemode;
        this.userAudiomenu = userAudiomenu;
        this.userAudioenemys = userAudioenemys;
        this.userAudioefect = userAudioefect;
        this.userAudiogun = userAudiogun;
        this.userScreen = userScreen;
        this.userLanguage = userLanguage;
        this.userFirsttime = userFirsttime;
    }

    public Long getUserGeneralId() {
        return userGeneralId;
    }

    public void setUserGeneralId(Long userGeneralId) {
        this.userGeneralId = userGeneralId;
    }

    public String getUserStylegun() {
        return userStylegun;
    }

    public void setUserStylegun(String userStylegun) {
        this.userStylegun = userStylegun;
    }

    public String getUserController() {
        return userController;
    }

    public void setUserController(String userController) {
        this.userController = userController;
    }

    public String getUserGamemode() {
        return userGamemode;
    }

    public void setUserGamemode(String userGamemode) {
        this.userGamemode = userGamemode;
    }

    public double getUserAudiomenu() {
        return userAudiomenu;
    }

    public void setUserAudiomenu(double userAudiomenu) {
        this.userAudiomenu = userAudiomenu;
    }

    public double getUserAudioenemys() {
        return userAudioenemys;
    }

    public void setUserAudioenemys(double userAudioenemys) {
        this.userAudioenemys = userAudioenemys;
    }

    public double getUserAudioefect() {
        return userAudioefect;
    }

    public void setUserAudioefect(double userAudioefect) {
        this.userAudioefect = userAudioefect;
    }

    public double getUserAudiogun() {
        return userAudiogun;
    }

    public void setUserAudiogun(double userAudiogun) {
        this.userAudiogun = userAudiogun;
    }

    public String getUserScreen() {
        return userScreen;
    }

    public void setUserScreen(String userScreen) {
        this.userScreen = userScreen;
    }

    public String getUserLanguage() {
        return userLanguage;
    }

    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    public String getUserFirsttime() {
        return userFirsttime;
    }

    public void setUserFirsttime(String userFirsttime) {
        this.userFirsttime = userFirsttime;
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
        hash += (userGeneralId != null ? userGeneralId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserConfigGeneral)) {
            return false;
        }
        UserConfigGeneral other = (UserConfigGeneral) object;
        if ((this.userGeneralId == null && other.userGeneralId != null) || (this.userGeneralId != null && !this.userGeneralId.equals(other.userGeneralId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.proyecto.model.UserConfigGeneral[ userGeneralId=" + userGeneralId + " ]";
    }
    
}
