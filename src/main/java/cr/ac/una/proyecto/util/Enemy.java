package cr.ac.una.proyecto.util;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy {
    private final int health;
    private final double speed;
    private double x;
    private final double y;
    private final ImageView sprite;
    private final int damageSecond;
    private boolean atacando = false;
    public long lastAttackTime = 0;
    private final int DistanceAttack;
   private double time = 0;

   public Enemy(int health, int damageSecond, double speed,int distanceAttack, double x, double y, Image img) {
        this.health = health;
        this.damageSecond=damageSecond;
        this.speed = speed;
        this.DistanceAttack = distanceAttack;
        this.x = x;
        this.y = y;
      

        sprite = new ImageView(img);
        sprite.setX(x);
        sprite.setY(y);

        sprite.setFitWidth(60);
        sprite.setFitHeight(60);
    }

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

        sprite.setX(x);

        // MOVIMIENTO FLOTANTE SOLO SI TIENE RANGO
       
    }
     if (DistanceAttack > 0) {

            time += 0.03; // velocidad del movimiento

            double offsetY = Math.sin(time) * 10; // amplitud (5 px)

            sprite.setY(y + offsetY);
        }
}


      public int getDamageSecond() {
    return damageSecond;
}
      public void setX(double x){this.x=x;}
    public double getX() { return x; }
    public double getY() { return y; }
    public int getHealth() { return health; }
     public ImageView getSprite() {
    return sprite;
}
public double getHeight() {
    return sprite.getBoundsInParent().getHeight();
}
public boolean isAtacando() {
    return atacando;
}

public void setAtacando(boolean atacando) {
    this.atacando = atacando;
}
public int getDistanceAtack(){return DistanceAttack;}

}
