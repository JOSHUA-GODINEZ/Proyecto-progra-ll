package cr.ac.una.proyecto.model;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserConfigGeneralDto {
    private final SimpleLongProperty userGeneralId = new SimpleLongProperty();
    private final SimpleStringProperty userStylegun = new SimpleStringProperty();
    private final SimpleStringProperty userController = new SimpleStringProperty();
    private final SimpleStringProperty userGamemode = new SimpleStringProperty();
    private final SimpleStringProperty userScreen = new SimpleStringProperty();
    private final SimpleStringProperty userLanguage = new SimpleStringProperty();
    private final SimpleStringProperty userFirsttime = new SimpleStringProperty();
    private final SimpleDoubleProperty userAudiomenu = new SimpleDoubleProperty();
    private final SimpleDoubleProperty userAudioenemys = new SimpleDoubleProperty();
    private final SimpleDoubleProperty userAudioefect = new SimpleDoubleProperty();
    private final SimpleDoubleProperty userAudiogun = new SimpleDoubleProperty();
   
         public UserConfigGeneralDto(){}
    
        public UserConfigGeneralDto(UserConfigGeneral entity) {
        if (entity != null) {
        this.userGeneralId.set(entity.getUserGeneralId());
        this.userStylegun.set(entity.getUserStylegun());
        this.userController.set(entity.getUserController());
        this.userGamemode.set(entity.getUserGamemode());
        this.userScreen.set(entity.getUserScreen());
        this.userLanguage.set(entity.getUserLanguage());
        this.userFirsttime.set(entity.getUserFirsttime());
        this.userAudiomenu.set(entity.getUserAudiomenu());
        this.userAudioenemys.set(entity.getUserAudioenemys());
        this.userAudioefect.set(entity.getUserAudioefect());
        this.userAudiogun.set(entity.getUserAudiogun());    
        }
        }
    public SimpleLongProperty getUserGeneralIdProperty() {return userGeneralId;}

    public SimpleStringProperty getUserStylegunProperty() {return userStylegun;}

    public SimpleStringProperty getUserControllerProperty() {return userController;}

    public SimpleStringProperty getUserGamemodeProperty() {return userGamemode;}

    public SimpleStringProperty getUserScreenProperty() {return userScreen;}
    
    public SimpleStringProperty getUserLanguageProperty() {return userLanguage;}

    public SimpleStringProperty getUserFirsttimeProperty() {return userFirsttime;}

    public SimpleDoubleProperty getUserAudiomenuProperty() {return userAudiomenu;}

    public SimpleDoubleProperty getUserAudioenemysProperty() {return userAudioenemys;}

    public SimpleDoubleProperty getUserAudioefectProperty() {return userAudioefect;}

    public SimpleDoubleProperty getUserAudiogunProperty() {return userAudiogun;}
        
    public Long getUserGeneralId() {return userGeneralId.get();}

    public String getUserStylegun() {return userStylegun.get();}

    public String getUserController() {return userController.get();}

    public String getUserGamemode() {return userGamemode.get();}

    public String getUserScreen() {return userScreen.get();}
    
    public String getUserLanguage() {return userLanguage.get();}

    public String getUserFirsttime() {return userFirsttime.get();}

    public Double getUserAudiomenu() {return userAudiomenu.get();}

    public Double getUserAudioenemys() {return userAudioenemys.get();}

    public Double getUserAudioefect() {return userAudioefect.get();}

    public Double getUserAudiogun() {return userAudiogun.get();} 
          
          
public void setUserGeneralId(long userGeneralId) {
    this.userGeneralId.set(userGeneralId);
}

public void setUserStylegun(String userStylegun) {
    this.userStylegun.set(userStylegun);
}

public void setUserController(String userController) {
    this.userController.set(userController);
}

public void setUserGamemode(String userGamemode) {
    this.userGamemode.set(userGamemode);
}

public void setUserScreen(String userScreen) {
    this.userScreen.set(userScreen);
}

public void setUserLanguage(String userLanguage) {
    this.userLanguage.set(userLanguage);
}

public void setUserFirsttime(String userFirsttime) {
    this.userFirsttime.set(userFirsttime);
}

public void setUserAudiomenu(double userAudiomenu) {
    this.userAudiomenu.set(userAudiomenu);
}

public void setUserAudioenemys(double userAudioenemys) {
    this.userAudioenemys.set(userAudioenemys);
}

public void setUserAudioefect(double userAudioefect) {
    this.userAudioefect.set(userAudioefect);
}

public void setUserAudiogun(double userAudiogun) {
    this.userAudiogun.set(userAudiogun);
}   
}
