package cr.ac.una.proyecto.util;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class Gun {
    private double damage;
    private double attackSpeed;
    private Image sprite;
    private AudioClip shootSound;

          // Maneja los atributos del arma
    public Gun(double damage, double attackSpeed, Image sprite, AudioClip shootSound) {
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.sprite = sprite;
        this.shootSound = shootSound;
    }

    // Getters y Setters
    public double getDamage() { return damage; }
    public void setDamage(double damage) { this.damage = damage; }

    public double getAttackSpeed() { return attackSpeed; }
    public void setAttackSpeed(double attackSpeed) { this.attackSpeed = attackSpeed; }

    public Image getSprite() { return sprite; }
    public void setSprite(Image sprite) { this.sprite = sprite; }

    public AudioClip getShootSound() { return shootSound; }
    public void setShootSound(AudioClip shootSound) { this.shootSound = shootSound; }
    
    public void playSound() {
        if (shootSound != null) {
            shootSound.play(SoundManager.getGunVolume());
        }
    }
}
