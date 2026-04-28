package cr.ac.una.proyecto.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GameState {

    // 🔥 instancia única (singleton simple)
    private static final GameState instance = new GameState();

    public static GameState getInstance() {
        return instance;
    }
int defaultHealth =1000;
    // 🔥 propiedades (para binding)
    private IntegerProperty health = new SimpleIntegerProperty(1000);
    private IntegerProperty coins  = new SimpleIntegerProperty(0);
    private IntegerProperty wave   = new SimpleIntegerProperty(1);
    private IntegerProperty baseHealth = new SimpleIntegerProperty(1000);
    private IntegerProperty bonusHealth = new SimpleIntegerProperty(1000);
    private IntegerProperty upgradeLevel = new SimpleIntegerProperty(1);
    private IntegerProperty upgradeCost = new SimpleIntegerProperty(50);
   // 🔥 ELIXIR
    private DoubleProperty elixir = new SimpleDoubleProperty(0);
    private DoubleProperty maxElixir = new SimpleDoubleProperty(100);
    private DoubleProperty elixirRegen = new SimpleDoubleProperty(1); 
    // 🔹 health
    public int getHealth() { return health.get(); }
    public void setHealth(int value) { health.set(value); }
    public IntegerProperty healthProperty() { return health; }

    // 🔹 coins
    public int getCoins() { return coins.get(); }
    public void setCoins(int value) { coins.set(value); }
    public IntegerProperty coinsProperty() { return coins; }

    // 🔹 wave
    public int getWave() { return wave.get(); }
    public void setWave(int value) { wave.set(value); }
    public IntegerProperty waveProperty() { return wave; }
    
    //Default
    public int getDefaultHealth() { return 1000; }
    
    //Labels mejoras
    public int getBaseHealth() { return baseHealth.get(); }
    public int getBonusHealth() { return bonusHealth.get(); }
    public int getUpgradeLevel() { return upgradeLevel.get(); }
    public int getUpgradeCost() { return upgradeCost.get(); }
 
    public IntegerProperty baseHealthProperty() { return baseHealth; }
    public IntegerProperty bonusHealthProperty() { return bonusHealth; }
    public IntegerProperty upgradeLevelProperty() { return upgradeLevel; }
    public IntegerProperty upgradeCostProperty() { return upgradeCost; }
    
    
    public int getTotalHealth() {
    return getBaseHealth() + getBonusHealth();
}
    public void upgradeHealth() {

    if (coins.get() >= upgradeCost.get()) {

        coins.set(coins.get() - upgradeCost.get());

        health.set(defaultHealth + bonusHealth.get());
defaultHealth+=bonusHealth.get();
baseHealth.set(baseHealth.get() + bonusHealth.get());
        upgradeLevel.set(upgradeLevel.get() + 1);

        // costo escala
        upgradeCost.set(upgradeCost.get() + 50);
    }
}
    public int getMaxHealth() {
    return getBaseHealth();
}
    
    
    public double getElixir() { return elixir.get(); }
public void setElixir(double value) { elixir.set(value); }
public DoubleProperty elixirProperty() { return elixir; }

public double getMaxElixir() { return maxElixir.get(); }
public DoubleProperty maxElixirProperty() { return maxElixir; }

public double getElixirRegen() { return elixirRegen.get(); }
public DoubleProperty elixirRegenProperty() { return elixirRegen; }
}