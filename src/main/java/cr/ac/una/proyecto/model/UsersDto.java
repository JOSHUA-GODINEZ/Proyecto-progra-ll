package cr.ac.una.proyecto.model;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class UsersDto {

    private final ObjectProperty<Long> userId = new SimpleObjectProperty<>();
    private final StringProperty userName = new SimpleStringProperty();
    private final ObjectProperty<byte[]> userAvatar = new SimpleObjectProperty<>();
    private final ObjectProperty<Long> userVersion = new SimpleObjectProperty<>();
    private final ObjectProperty<ObservableList<UsersConfigDto>> usersConfigList = new SimpleObjectProperty<>(FXCollections.observableArrayList());

    public UsersDto() {
    }

    public UsersDto(Users user) {
        if (user != null) {
            this.userId.set(user.getUserId());
            this.userName.set(user.getUserName());
            this.userAvatar.set(user.getUserAvatar());
            this.userVersion.set(user.getUserVersion());

            if (user.getUsersConfigList() != null) {
                for (UsersConfig uc : user.getUsersConfigList()) {
                    this.usersConfigList.get().add(new UsersConfigDto(uc));
                }
            }
        }
    }

    public ObjectProperty<Long> userIdProperty() { return userId; }
    public StringProperty userNameProperty() { return userName; }
    public ObjectProperty<byte[]> userAvatarProperty() { return userAvatar; }
    public ObjectProperty<Long> userVersionProperty() { return userVersion; }
    
    public ObjectProperty<ObservableList<UsersConfigDto>> usersConfigListProperty() { return usersConfigList; }

    public Long getUserId() { return userId.get(); }
    public void setUserId(Long id) { this.userId.set(id); }

    public String getUserName() { return userName.get(); }
    public void setUserName(String name) { this.userName.set(name); }

public byte[] getUserAvatar() { return userAvatar.get(); }
public void setUserAvatar(byte[] userAvatar) { this.userAvatar.set(userAvatar); }

    public Long getUserVersion() { return userVersion.get(); }
    public void setUserVersion(Long version) { this.userVersion.set(version); }

    public ObservableList<UsersConfigDto> getUsersConfigList() { return usersConfigList.get(); }
    public void setUsersConfigList(ObservableList<UsersConfigDto> usersConfigList) { this.usersConfigList.set(usersConfigList); }
}
