package cr.ac.una.proyecto.util;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Enemy {

    private final double speed;
    private double x;
    private final double y;

    private final ImageView sprite;
    private final int damageSecond;
    private boolean atacando = false;
    public long lastAttackTime = 0;
    private final int DistanceAttack;

    private double time = 0;


    private DoubleProperty health;
    private double maxHealth;


    private ProgressBar hpBar;
    private Pane view;

    public Enemy(int health, int damageSecond, double speed, int distanceAttack,
                 double x, double y, Image img) {

        this.maxHealth = health;
        this.health = new SimpleDoubleProperty(health);

        this.damageSecond = damageSecond;
        this.speed = speed;
        this.DistanceAttack = distanceAttack;
        this.x = x;
        this.y = y;

        //  SPRITE
        sprite = new ImageView(img);
        sprite.setFitWidth(60);
        sprite.setFitHeight(60);
        sprite.setLayoutX(0);
        sprite.setLayoutY(0);

        //  BARRA VIDA
        hpBar = new ProgressBar();
        hpBar.setPrefWidth(60);
        hpBar.setPrefHeight(10);
        hpBar.setLayoutY(-8);
        hpBar.setStyle("-fx-accent: red;");

        hpBar.progressProperty().bind(
            this.health.divide(maxHealth)
        );

        //  CONTENEDOR
        view = new Pane(hpBar, sprite);
        view.setLayoutX(x);
        view.setLayoutY(y);
    }

    // MOVIMIENTO
    public void update(double limiteGun) {

        if (!atacando) {

            double siguienteX = x - speed;
            double limiteAtaque = limiteGun + DistanceAttack;

            if (siguienteX <= limiteAtaque) {
                x = limiteAtaque;
                atacando = true;
                lastAttackTime = System.currentTimeMillis();
            } else {
                x = siguienteX;
            }
        }

        // aplicar movimiento al contenedor
        view.setLayoutX(x);

        // movimiento flotante (solo rango)
        if (DistanceAttack > 0) {
            time += 0.03;
            double offsetY = Math.sin(time) * 10;
            view.setLayoutY(y + offsetY);
        } else {
            view.setLayoutY(y);
        }
    }

    // 🔥 DAÑO
    public void takeDamage(double damage) {
        health.set(health.get() - damage);
    }

 
    public boolean isDead() {
        return health.get() <= 0;
    }

  
    public int getDamageSecond() {
        return damageSecond;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public double getHealth() {
        return health.get();
    }

    public Pane getView() {
        return view;
    }

    public boolean isAtacando() {
        return atacando;
    }

    public void setAtacando(boolean atacando) {
        this.atacando = atacando;
    }

    public int getDistanceAtack() {
        return DistanceAttack;
    }
}