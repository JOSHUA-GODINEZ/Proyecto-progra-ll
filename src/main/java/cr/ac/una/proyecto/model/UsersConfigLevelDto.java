package cr.ac.una.proyecto.model;
import javafx.beans.property.SimpleLongProperty;

public class UsersConfigLevelDto {

    private final SimpleLongProperty gameId = new SimpleLongProperty();
    private final SimpleLongProperty userLevel = new SimpleLongProperty();
    private final SimpleLongProperty userLevelGundamage = new SimpleLongProperty();
    private final SimpleLongProperty userLevelGunspeed = new SimpleLongProperty();
    private final SimpleLongProperty userLevelBaseheath = new SimpleLongProperty();
    private final SimpleLongProperty userLevelCapacityelixir = new SimpleLongProperty();
    private final SimpleLongProperty userLevelSpeedelixir = new SimpleLongProperty();
    private final SimpleLongProperty userLevelPowerbombdamage = new SimpleLongProperty();
    private final SimpleLongProperty userLevelPowerbombrange = new SimpleLongProperty();
    private final SimpleLongProperty userLevelPowerfreezeduration = new SimpleLongProperty();
    private final SimpleLongProperty userLevelPowerfreezerange = new SimpleLongProperty();
    private final SimpleLongProperty userPoints = new SimpleLongProperty(); 


    public UsersConfigLevelDto() {
    }

    public UsersConfigLevelDto(UsersConfigLevel entity) {
        if (entity != null) {
            this.gameId.set(entity.getGameId());
            this.userLevel.set(entity.getUserLevel());
            this.userLevelGundamage.set(entity.getUserLevelGundamage());
            this.userLevelGunspeed.set(entity.getUserLevelGunspeed());
            this.userLevelBaseheath.set(entity.getUserLevelBaseheath());
            this.userLevelCapacityelixir.set(entity.getUserLevelCapacityelixir());
            this.userLevelSpeedelixir.set(entity.getUserLevelSpeedelixir());
            this.userLevelPowerbombdamage.set(entity.getUserLevelPowerbombdamage());
            this.userLevelPowerbombrange.set(entity.getUserLevelPowerbombrange());
            this.userLevelPowerfreezeduration.set(entity.getUserLevelPowerfreezeduration());
            this.userLevelPowerfreezerange.set(entity.getUserLevelPowerfreezerange());
            this.userPoints.set(entity.getUserPoints()); 
        }
    }

    public SimpleLongProperty gameIdProperty() { return gameId; }
    public Long getGameId() { return gameId.get(); }
    public void setGameId(Long gameId) { this.gameId.set(gameId); }

    public SimpleLongProperty userLevelProperty() { return userLevel; }
    public Long getUserLevel() { return userLevel.get(); }
    public void setUserLevel(Long userLevel) { this.userLevel.set(userLevel); }

    public SimpleLongProperty userLevelGundamageProperty() { return userLevelGundamage; }
    public Long getUserLevelGundamage() { return userLevelGundamage.get(); }
    public void setUserLevelGundamage(Long userLevelGundamage) { this.userLevelGundamage.set(userLevelGundamage); }

    public SimpleLongProperty userLevelGunspeedProperty() { return userLevelGunspeed; }
    public Long getUserLevelGunspeed() { return userLevelGunspeed.get(); }
    public void setUserLevelGunspeed(Long userLevelGunspeed) { this.userLevelGunspeed.set(userLevelGunspeed); }

    public SimpleLongProperty userLevelBaseheathProperty() { return userLevelBaseheath; }
    public Long getUserLevelBaseheath() { return userLevelBaseheath.get(); }
    public void setUserLevelBaseheath(Long userLevelBaseheath) { this.userLevelBaseheath.set(userLevelBaseheath); }

    public SimpleLongProperty userLevelCapacityelixirProperty() { return userLevelCapacityelixir; }
    public Long getUserLevelCapacityelixir() { return userLevelCapacityelixir.get(); }
    public void setUserLevelCapacityelixir(Long userLevelCapacityelixir) { this.userLevelCapacityelixir.set(userLevelCapacityelixir); }

    public SimpleLongProperty userLevelSpeedelixirProperty() { return userLevelSpeedelixir; }
    public Long getUserLevelSpeedelixir() { return userLevelSpeedelixir.get(); }
    public void setUserLevelSpeedelixir(Long userLevelSpeedelixir) { this.userLevelSpeedelixir.set(userLevelSpeedelixir); }

    public SimpleLongProperty userLevelPowerbombdamageProperty() { return userLevelPowerbombdamage; }
    public Long getUserLevelPowerbombdamage() { return userLevelPowerbombdamage.get(); }
    public void setUserLevelPowerbombdamage(Long userLevelPowerbombdamage) { this.userLevelPowerbombdamage.set(userLevelPowerbombdamage); }

    public SimpleLongProperty userLevelPowerbombrangeProperty() { return userLevelPowerbombrange; }
    public Long getUserLevelPowerbombrange() { return userLevelPowerbombrange.get(); }
    public void setUserLevelPowerbombrange(Long userLevelPowerbombrange) { this.userLevelPowerbombrange.set(userLevelPowerbombrange); }

    public SimpleLongProperty userLevelPowerfreezedurationProperty() { return userLevelPowerfreezeduration; }
    public Long getUserLevelPowerfreezeduration() { return userLevelPowerfreezeduration.get(); }
    public void setUserLevelPowerfreezeduration(Long userLevelPowerfreezeduration) { this.userLevelPowerfreezeduration.set(userLevelPowerfreezeduration); }

    public SimpleLongProperty userLevelPowerfreezerangeProperty() { return userLevelPowerfreezerange; }
    public Long getUserLevelPowerfreezerange() { return userLevelPowerfreezerange.get(); }
    public void setUserLevelPowerfreezerange(Long userLevelPowerfreezerange) { this.userLevelPowerfreezerange.set(userLevelPowerfreezerange); }


    public SimpleLongProperty userPointsProperty() { return userPoints; }
    public Long getUserPoints() { return userPoints.get(); }
    public void setUserPoints(Long userPoints) { this.userPoints.set(userPoints); }

    @Override
    public String toString() {
        return "Nivel: " + userLevel.get();
    }


    
   
    
    
    
    
}