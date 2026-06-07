package cr.ac.una.proyecto.model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class UsersConfigDto {
    private final ObjectProperty<UsersDto> users = new SimpleObjectProperty<>();
    private final ObjectProperty<UsersConfigLevelDto> usersConfigLevel = new SimpleObjectProperty<>();
    private final ObjectProperty<UserConfigGeneralDto> usersConfigGeneral = new SimpleObjectProperty<>();

    public UsersConfigDto() {
    }

   public UsersConfigDto(UsersConfig usersConfig) {
    if (usersConfig != null) {
        this.users.set(null); 

        if (usersConfig.getUserConfig() != null) {
            this.usersConfigLevel.set(new UsersConfigLevelDto(usersConfig.getUserConfig()));
        }
         if (usersConfig.getUserConfigGeneral() != null) {
            this.usersConfigGeneral.set(new UserConfigGeneralDto(usersConfig.getUserConfigGeneral()));
        }
    }
}

    public ObjectProperty<UsersDto> usersProperty() {
        return users;
    }
    public UsersDto getUsers() {
        return users.get();
    }

    public void setUsers(UsersDto users) {
        this.users.set(users);
    }

    public ObjectProperty<UsersConfigLevelDto> usersConfigLevelProperty() {
        return usersConfigLevel;
    }

    public UsersConfigLevelDto getUsersConfigLevel() {
        return usersConfigLevel.get();
    }

    public void setUsersConfigLevel(UsersConfigLevelDto usersConfigLevel) {
        this.usersConfigLevel.set(usersConfigLevel);
    }
    
       public ObjectProperty<UserConfigGeneralDto> usersConfigGeneralProperty() {
        return usersConfigGeneral;
    }

    public UserConfigGeneralDto getUsersConfigGeneral() {
        return usersConfigGeneral.get();
    }

    public void setUsersConfigGeneral(UserConfigGeneralDto usersConfigGeneral) {
        this.usersConfigGeneral.set(usersConfigGeneral);
    }
}