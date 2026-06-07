package cr.ac.una.proyecto.util;
import javafx.animation.PauseTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

public class Enemy {
    private Pane view;
    private final ImageView sprite;
    private ProgressBar hpBar;

    private double x;
    private final double y;
    private final double speed;
    private double time = 0;
    private boolean MovedY; 

    private DoubleProperty health;
    private double maxHealth;

    private final double damageSecond;
    private final int DistanceAttack; 
    private boolean atacando = false;
    private boolean atacandoHelper;
    private boolean lastAtacando = false;
    public long lastAttackTime = 0;

    private boolean hit;
    private long hitEndTime;
    private boolean frozen = false;
    private long freezeEndTime = 0;

    private boolean dying = false;
    private boolean dead = false;
    private boolean rewardGiven;

    private Image walkImg;
    private Image attackImg;
    private Image hitImg;
    private Image freezeImg;

    private Animation currentAnim;
    private Animation walkAnim;
    private Animation attackAnim;
    private Animation hitAnim;
    private Animation freezeAnim;
    private Animation deathAnim;
    private int currentFrame = 0;
    private long lastFrameTime = 0;

    private AudioClip attackSound;
    private AudioClip hitSound;
    private AudioClip impact; 
    private AudioClip deathSound;
    // Constructor con las estadisticas de los enemigos
public Enemy(double health, double damageSecond, double speed, int distanceAttack,boolean MovedY,
             double x, double y,
             Image walkImg, Image attackImg, Image deathImg,Image freezeImg,Image hitImg, AudioClip attackSound, AudioClip deathSound, AudioClip hitSound) {

    this.maxHealth = health;
    this.health = new SimpleDoubleProperty(health);
    this.freezeImg = freezeImg;
    this.hitImg=hitImg;
    this.damageSecond = damageSecond;
    this.speed = speed;
    this.DistanceAttack = distanceAttack;
    this.MovedY=MovedY;
    this.x = x;
    this.y = y;

    this.walkAnim = new Animation(walkImg, 7,100);     
    this.attackAnim = new Animation(attackImg, 4,250);
    this.deathAnim = new Animation(deathImg, 4,200);   
    this.freezeAnim = new Animation(freezeImg,1, 200);
    this.hitAnim= new Animation(hitImg,2, 100);
    this.currentAnim = walkAnim;

    
    // Genera la sombra debajo
    javafx.scene.shape.Ellipse sombra = new javafx.scene.shape.Ellipse();
    sombra.setRadiusX(25);
    sombra.setRadiusY(8); 
    sombra.setCenterX(30); 
    sombra.setCenterY(105); 
    sombra.setFill(javafx.scene.paint.Color.BLACK);
    sombra.setOpacity(0.3);
    

    sprite = new ImageView(currentAnim.image);
    sprite.setFitWidth(110);
    sprite.setFitHeight(110);
    sprite.setViewport(new Rectangle2D(
        0,
        0,
        currentAnim.frameWidth,
        currentAnim.frameHeight
    ));
    sprite.setStyle("-fx-border-color: red");

    hpBar = new ProgressBar();
    hpBar.setPrefWidth(40);
    hpBar.setMaxHeight(11);
    hpBar.setLayoutX(8);
    hpBar.setLayoutY(7);
    hpBar.setStyle("-fx-accent: red;");

    hpBar.progressProperty().bind(
        this.health.divide(maxHealth)
    );
   // Une los tres en uno
   view = new Pane(sombra, sprite, hpBar);
   view.setLayoutX(x);
   view.setLayoutY(y);
   this.attackSound = attackSound;
   this.deathSound = deathSound;
   this.hitSound = hitSound;
   //Daño al enemigo
   impact = new AudioClip(
    getClass().getResource(
        "/cr/ac/una/proyecto/resources/Audios/hitBase.wav"
    ).toExternalForm()
);

}

      // Actualiza el estado general del enemigo: movimiento, animaciones y efectos de combate
public void update(double limiteGun) {

    long now = System.currentTimeMillis();

    // Estado: Muerte en progreso
    if (dying) {
        atacando = false;
        currentAnim = deathAnim;
    }

    // Estado: Congelado

    else if (frozen) {
        currentAnim = freezeAnim;

        // Pausa el ataque actual mientras dure la congelación
        if (atacando) {
            atacandoHelper = true;
            atacando = false; 
        }
        
        // Finaliza el efecto de congelación
        if (now > freezeEndTime) {
            frozen = false;
            currentFrame = 0;
            
            //  Valida si el retroceso lo alejó del rango de ataque
            double limiteAtaque = limiteGun + DistanceAttack;
            
            if (x <= limiteAtaque) {
                // Si tras los golpes sigue dentro del rango, recupera su ataque
                atacando = atacandoHelper; 
            } else {
                // Si el retroceso lo alej, se cancela el ataque y se le obliga a caminar!
                atacando = false; 
            }
            
            atacandoHelper = false; 

            if (atacando) {
                currentAnim = attackAnim;
            } else {
                currentAnim = walkAnim;
            }
        }
    }
    
    // Estado: Recibe daño (aturdimiento breve)
    else if (hit) {
        currentAnim = hitAnim;
        
        // Finaliza el estado de impacto y vuelve a la animacion que corresponda
        if (now > hitEndTime) {
            hit = false;
            currentFrame = 0;
            
            if (atacando) {
                currentAnim = attackAnim;
            } else {
                currentAnim = walkAnim;
            }
        }
    }
    
    // Estado: Normal (sin alteraciones)
    else {
        // Control de movimiento y rango de ataque en el eje X
        if (!atacando) {
            double siguienteX = x - speed;
            double limiteAtaque = limiteGun + DistanceAttack;

            // Si alcanza el objetivo, se detiene y comienza a atacar
            if (siguienteX <= limiteAtaque) {
                x = limiteAtaque;
                atacando = true;
                lastAttackTime = now;
            } else {
                x = siguienteX;
            }
        }

        // Detecta transiciones entre caminar y atacar para reiniciar los fotogramas
        if (lastAtacando != atacando) {
            currentFrame = 0;

            if (atacando) {
                currentAnim = attackAnim;
            } else {
                currentAnim = walkAnim;
            }
            lastAtacando = atacando;
        }
    }
    
    // Aplica la posicion calculada en el eje X
    view.setLayoutX(x);

    // Movimiento especial: Oscilacion vertical tipo seno (solo si el estado lo permite)
    if (MovedY && !dying && !frozen && !hit) {
        if (!isAtacando()) {
            time += 0.1;
            view.setLayoutY(y + Math.sin(time) * 60);
        }
    } else {
        view.setLayoutY(y);
    }

    // Gestion del temporizador de fotogramas y renderizado del spritesheet
    if (now - lastFrameTime > currentAnim.frameSpeed) {
        if (dying) {
            currentFrame++;

            // Si termina la animacion de muerte, se marca como destruido permanentemente
            if (currentFrame >= currentAnim.frames) {
                dead = true;
                return;
            }
        } else {
            // Ciclo infinito para animaciones normales
            currentFrame = (currentFrame + 1) % currentAnim.frames;
        }
        
        // Actualiza la textura e invierte el viewport segun el diseño del spritesheet
        sprite.setImage(currentAnim.image);
        sprite.setViewport(new Rectangle2D(
            (currentAnim.frames - 1 - currentFrame) * currentAnim.frameWidth, 
            0, 
            currentAnim.frameWidth, 
            currentAnim.frameHeight
        ));
        
        // Ajuste de escala y centrado visual del sprite
        double scale = sprite.getFitWidth() / currentAnim.frameWidth;
        sprite.setLayoutX(-(currentAnim.frameWidth * scale) / 2 + 25);
        lastFrameTime = now;
    }
}

// Registra el daño recibido por el enemigo, actualiza su vida y gestiona los efectos visuales de impacto
public void takeDamage(double damage) {

    // Si el enemigo ya esta en proceso de muerte o destruido, ignora el daño
    if (dying || dead) {
        return;
    }

    // Aplica la reduccion de vida de forma reactiva
    health.set(health.get() - damage);

    // Reproduce el efecto de sonido de impacto
    playHitSound();

    // Evalua si el daño recibido causa la muerte del enemigo
    if (health.get() <= 0) {
        die();
        return;
    }

    // Aplica efecto de retroceso (knockback) y aturdimiento si el enemigo no esta atacando
    if (!atacando) {
        x += 20; // Desplaza al enemigo hacia atras en el eje X
        hit = true;
        
        // Define la duracion del estado de impacto (200 milisegundos)
        hitEndTime = System.currentTimeMillis() + 200;
        
        // Reinicia la animacion actual para reproducir los fotogramas de daño
        currentFrame = 0;
        currentAnim = hitAnim;
    }
}

    public boolean isDead() {
        return health.get() <= 0;
    }

    public int getDamageSecond() {
        return (int)damageSecond;
    }

    public double getHealth() {
        return health.get();
    }

    public Pane getView() {
        return view;
    }

    public boolean isAtacando() {
        return atacando;
    }
  public boolean isFroozen() {
        return frozen;
    }
    public void setAtacando(boolean atacando) {
        this.atacando = atacando;
    }

    public int getDistanceAtack() {
        return DistanceAttack;
    }

    // ❄️ FREEZE
    public void setFrozen(boolean value,double duracion) {
        frozen = value;
        freezeEndTime = System.currentTimeMillis() + (long)(duracion * 1000);
        
    }

    // 🔫 DISPARO
    public Projectile shoot() {
      
        ImageView bullet = new ImageView(
            new Image(getClass().getResource("/cr/ac/una/proyecto/resources/bullet.png").toExternalForm())
        );

        bullet.setFitWidth(15);
        bullet.setFitHeight(10);

        double px = view.getLayoutX()+10;
        double py = view.getLayoutY()+65;

        return new Projectile(px, py, (int)damageSecond, bullet,impact);
    }

    public boolean canShoot() {
        return atacando && DistanceAttack > 0;
    }
 public Bounds getHitbox() {
    Bounds b = sprite.getBoundsInParent();
    return new BoundingBox(
        b.getMinX()+50,
        b.getMinY()+30,
        b.getWidth()-70,
        b.getHeight()-15
    );
}
 public Bounds getHitbox1() {
   Bounds b = sprite.getBoundsInParent();
    return new BoundingBox(
        b.getMinX()+50,
        b.getMinY()+40,
        b.getWidth()-40,
        b.getHeight()-40
    );
}
 
 
public void die() {
    deathSound.play(SoundManager.getEnemyVolume());
    if (!dying) {
        dying = true;
        atacando = false;
        currentFrame = 0;
        currentAnim = deathAnim;
        hpBar.progressProperty().unbind();
    
    // 2. Le clavamos un CERO absoluto a la barra para que se vacíe por completo
    hpBar.setProgress(0.0);
    }
}
 
 public boolean isDeadA() {
    return dead;
}

public boolean isDying() {
    return dying;
}

 public void playAttackSound() {
   attackSound.play(SoundManager.getEnemyVolume());
}

 public void playHitSound() {

    if (hitSound != null) {

        hitSound.play(
            SoundManager.getEnemyVolume()
        );
    }
}
 public boolean isRewardGiven() {
    return rewardGiven;
}

public void setRewardGiven(boolean value) {
    rewardGiven = value;
}
}