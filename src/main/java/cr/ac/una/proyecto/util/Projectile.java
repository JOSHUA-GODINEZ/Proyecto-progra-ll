
package cr.ac.una.proyecto.util;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;

public class Projectile {
private double x, y;
private double speed;
private double dx;
private double dy;
private int damage;
private AudioClip hitSound;
private ImageView sprite;
private double tamano;

// Proyectil que tira el enemigo
public Projectile(double x,double y,int damage,ImageView sprite,AudioClip hitSound) {
    this.x = x;
    this.y = y;
    this.damage = damage;
    this.sprite = sprite;
    this.hitSound = hitSound;
    sprite.setLayoutX(x);
    sprite.setLayoutY(y);
    dx = -1;
    dy = 0;
    speed = 20;
}
 // Proyectil que dispara el arma
public Projectile(double x, double y, double dx, double dy, double speed, int damage, Image image, AudioClip hitSound, double tamano) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
        this.damage = damage;
        this.hitSound = hitSound;
        this.tamano = tamano;

        this.sprite = new ImageView(image);
        this.sprite.setFitWidth(tamano);
        this.sprite.setFitHeight(tamano);    
        this.sprite.setLayoutX(x);
        this.sprite.setLayoutY(y);
    }

  // Actualiza la posicion del proyectil
    public void update() {
        // Calcula la nueva posición de punto de llegada, punto de salida y velocidad
        x += dx * speed;
        y += dy * speed;

        // Lo refleja visualmente en la pantalla 
        sprite.setLayoutX(x);
        sprite.setLayoutY(y);
    }
     // El proyectil de los enemigos chocan con la base
    public boolean hitBase(double limiteGun) {
        return x <= limiteGun;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public int getDamage() {
        return damage;
    }
    public void playHitSound() {
    if (hitSound != null) {
        hitSound.play(SoundManager.getEnemyVolume());
    }
}
    // Getters 
    public double getX() { return x; }
    public double getY() { return y; }
}