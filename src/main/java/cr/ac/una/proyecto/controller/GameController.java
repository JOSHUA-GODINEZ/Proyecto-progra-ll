package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.App;
import cr.ac.una.proyecto.model.UsersConfigDto;
import cr.ac.una.proyecto.model.UsersConfigLevelDto;
import cr.ac.una.proyecto.model.UsersDto;
import cr.ac.una.proyecto.service.UsersService;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.Enemy;
import cr.ac.una.proyecto.util.Gun;
import cr.ac.una.proyecto.util.Projectile;
import cr.ac.una.proyecto.util.SoundManager;
import static cr.ac.una.proyecto.util.SoundManager.getWalkVolume;
import cr.ac.una.proyecto.util.Validations;
import java.io.IOException;
import javafx.beans.binding.Bindings;
import static java.lang.invoke.MethodHandles.loop;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;


public class GameController implements Initializable {
    
private List<Enemy> enemy = new ArrayList<>();
    @FXML
    private Pane rootSpawner;
    @FXML
    private Label LblHP;
    @FXML
    private VBox rootGameOver;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label LblLevel;
    @FXML
    private VBox rootWin;
    @FXML
    private VBox rootPause;
    @FXML
    private VBox rootGun;
    @FXML
    private Label LblCoins;
    private List<Projectile> enemyProjectiles = new ArrayList<>();    
    private int healthBase;
    private int elixirBase;
    
    private Image monsterNormalImg;
    private Image monsterFastImg;
    private Image monsterTankImg;
    private Image monsterDistanceImg;
    private Image monsterMoveImg;
    private Image monsterNormalImg1;
    private Image monsterNormalImg3;
    private Image monsterFastImg1;
    private Image monsterFastImg3;
    private Image monsterTankImg1;
    private Image monsterTankImg3;
    private Image monsterMoveImg1;
    private Image monsterMoveImg3;
    private Image monsterDistanceImg1;
    private Image monsterDistanceImg3;
    //freeze
    private Image zombieFreeze;
    private Image fastFreeze;
     private Image tankFreeze;
     private Image distanceFreeze;
      private Image moveFreeze;
    //hit
    private Image monsterNormalHitImg;
     private Image monsterFastHitImg;
     private Image monsterTankHitImg;
     private Image monsterDistanHitImg;
      private Image monsterMoveHitImg;
     

    private boolean gameOver = false;
    private AnimationTimer loop;
    private int coins = 0;
    
     Timeline spawnLoop;
    private int wave = 1;
    private int enemigosPorSpawn = 0;
    private int enemigosSpawneados = 0;
    private boolean enOleada = false;
    private final int MAX_WAVES = 3;
    private int level = 1;
    private boolean enPreSpawn = false;
    private int totalEnemigosNivel = 0;
    private int enemigosGeneradosTotal = 0;
    private Timeline elixirLoop;
    @FXML
    private Label lblProgress;


    @FXML
    private Label LblElixir;
    @FXML
    private ProgressBar progressHealth;
    @FXML
    private ProgressBar progressElixir;
    long lastTime = System.currentTimeMillis();
    
    private ImageView areaSkill;
    private boolean dragging = false;
    private String currentSkill = "";
    private Validations validations = new Validations();
    @FXML
    private Label LblMessage;
    @FXML
    private VBox rootValidations;
    private double skillRadius = 100;
    int tipo;
    
   private double[] healthEnemies = {50, 30, 100, 25,50};
   private double[] speedEnemies = {5, 9, 3, 6,7};
   private double[] damageEnemies = {4, 3, 7, 5,4};
    
    private boolean visualFreezeReady = true;
    private boolean visualDamageReady = true; // Controlará el bloqueo exacto
    
    private long lastUpdate = 0;
    
    
    //Enemigos
    //zombie

    private AudioClip zombieAttack;
    private AudioClip zombieDeath;
    private AudioClip  zombieHit;
    //rapido
    private AudioClip fastAttack;
   // private AudioClip fastWalk;
    private AudioClip fastDeath;
    //lento
   // private AudioClip tankWalk;
    private AudioClip tankAttack;
    private AudioClip tankDeath;
    //distancia
  //  private AudioClip distanceWalk;
    private AudioClip distanceAttack;
    private AudioClip distanceDeath;
    //movedor
  //  private AudioClip moveWalk;
    private AudioClip moveAttack;
    private AudioClip moveDeath;
    
 private AudioClip bombSound;

private AudioClip freezeSound;
    
    private Image damageImg;

private Image freezeImg;

private Image invalidImg;
private Image damageFueraImg;
private Image freezeFueraImg;
private Image freezeCaer;
private Image damageCaer;

private String modoJuegoActivo;
private AudioClip waveSound;

private AudioClip finalWaveSound;
private final DoubleProperty saludActualJugador = new SimpleDoubleProperty();


private long lastDamageSkill = 0;
private long lastFreezeSkill = 0;
private final long DAMAGE_COOLDOWN = 5_000;
private final long FREEZE_COOLDOWN = 5_000;
    @FXML
    private VBox rootWinAnimation;
    @FXML
    private VBox rootLoseAnimation;
    
  

private long gunLastShot = 0;

private long gunFireRate = 700;

private Gun currentGun;
private List<Projectile> playerProjectiles = new ArrayList<>();
private AudioClip audioGun;
 private Image imageGun;
 private Image imageGun2;
 private Image bulletGun;
    @FXML
    private ImageView gun;
  private boolean isShooting = false;
private long lastShotTime = 0;
    @FXML
    private Button btnDamagePower;
    @FXML
    private Button btnFreezePower;
    @FXML
    private StackPane stackDamagePower;
    @FXML
    private StackPane stackFreezePower;
    private int damageGun;
     private double speedGun;
      private Image imageGunDisparando;
      private Image imageGunDisparando2;
      Image imagenAUsarDisparo;
Image imagenAUsarNormal;
      
      private boolean keyUp = false;
private boolean keyDown = false;
private boolean keySpace = false; // Para disparar

private final double VELOCIDAD_JUGADOR = 5.0;
private double mouseXActual = 0;
private double mouseYActual = 0;
private boolean mouseEnMovimiento = false; // Nueva bandera de control

 // Ajusta este valor según el largo real de tu sprite de arma en píxeles

int levelBind;


private UsersDto usuarioActivo;
    @FXML
    private VBox rootNivel;
    @FXML
    private VBox rootNIvelAnimation;
    @FXML
    private Label LblLevel1;
    @FXML
    private Pane rootProgressbar;
   
    Image imagenIcono;
    Image imagenIconoDeath;
    ImageView icono;
    private String controlActivo = "M";
    @FXML
    private VBox rootWin1;
    @FXML
    private VBox rootWinAnimation1;
    @FXML
    private ImageView imageCoin;
    @FXML
    private Label lblElixirBomb;
    @FXML
    private Label lblElixirFreeze;
          @Override
           public void initialize(URL url, ResourceBundle rb) {
               SoundManager.stopFondo1Sound();
               SoundManager.playFondo1Sound2();
               
cargarYVerificarUsuario();

   SoundManager.cargarEfectosLose();
     SoundManager.cargarEfectosWin();
btnDamagePower.setFocusTraversable(false);
    btnFreezePower.setFocusTraversable(false);
    

    rootSpawner.sceneProperty().addListener((observable, oldScene, newScene) -> {
        if (newScene != null) {
            
            // 🔥 ¡AQUÍ ESTÁ EL CAMBIO CLAVE! 
            // Como la escena ya existe, ahora sí podemos obligar al juego a escuchar al teclado
            rootSpawner.requestFocus();
            
            rootSpawner.setOnMouseClicked(e -> rootSpawner.requestFocus());

            // Configuración de los eventos del teclado
            newScene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case W:     keyUp = true; break;
                    case S:   keyDown = true; break;
                    case SPACE:         
                        keySpace = true; 
                        event.consume(); // Evita que JavaFX use el espacio para activar botones
                        break;
                    default: break;
                }
            });

            newScene.setOnKeyReleased(event -> {
                switch (event.getCode()) {
                    case W: case UP:    keyUp = false; break;
                    case S: case DOWN:  keyDown = false; break;
                    case SPACE:         
                        keySpace = false; 
                        event.consume(); 
                        break;
                    default: break;
                }
            });
        }
    });
               
               
               gun.setFitWidth(0);
              gun.setFitHeight(0);
                imageGun2 = new Image(getClass().getResource("/cr/ac/una/proyecto/resources/Gun2.png").toExternalForm());
                 imageGunDisparando2 = new Image(getClass().getResource("/cr/ac/una/proyecto/resources/gun21.png").toExternalForm());
                
               imageGun = new Image(getClass().getResource("/cr/ac/una/proyecto/resources/gun4.png").toExternalForm());
               audioGun =new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/hitBase.wav").toExternalForm());
               imageGunDisparando = new Image(getClass().getResource("/cr/ac/una/proyecto/resources/gun61.png").toExternalForm());
     
               aplicarMejorasAlJugador();
         
               
    usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
    String estiloArma = "A";

if (usuarioActivo != null && usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
    var configGeneral = usuarioActivo.getUsersConfigList().get(0).getUsersConfigGeneral();
    if (configGeneral != null && configGeneral.getUserStylegun() != null) {
        estiloArma = configGeneral.getUserStylegun(); 
    }
}
if (estiloArma.equals("M")) {
  
    imagenAUsarDisparo = imageGunDisparando2;
    imagenAUsarNormal = imageGun2;
} else {

    imagenAUsarDisparo = imageGunDisparando;
    imagenAUsarNormal = imageGun;
}

               
               bulletGun = new Image(getClass().getResource("/cr/ac/una/proyecto/resources/bulletGun.png").toExternalForm());
               
            currentGun = new Gun(damageGun, speedGun, imagenAUsarNormal, audioGun);

        // 2. Asignar el sprite al ImageView
        gun.setImage(currentGun.getSprite());

             javafx.application.Platform.runLater(() -> {
        // Al presionar el clic (cualquier botón del mouse)
        gun.getScene().setOnMousePressed(event -> {
            isShooting = true;
        });

        // Al soltar el clic
        gun.getScene().setOnMouseReleased(event -> {
            isShooting = false;
        });
    });
      inicializarTipoDeControl(); 
    
    // 2. Ahora sí configuramos el mouse sabiendo qué control se eligió
    configurarSeguimientoMouse(); 
    
    // 3. Forzamos el foco del teclado (Obligatorio para que W, A, S, D funcionen)
    javafx.application.Platform.runLater(() -> {
        rootSpawner.requestFocus(); // Asegúrate de darle focus al contenedor de fondo principal
    });

        
        
        
         usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();

    if (usuarioActivo != null && usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
        var configGeneral = usuarioActivo.getUsersConfigList().get(0).getUsersConfigGeneral();
        if (configGeneral != null && configGeneral.getUserGamemode() != null) {
            this.modoJuegoActivo = configGeneral.getUserGamemode(); 
        }
    }

    // 3. 🔥 Aplicamos los cambios en las reglas del juego según el modo
    if (this.modoJuegoActivo.equals("E")) {
        // Aquí activas el Easy Mode o Modo Survival
        // Por ejemplo: bajarle vida a los enemigos, dar más munición, etc.
        System.out.println("LOG REGLAS: ¡Easy/Survival Mode Activado!");
    } else {
        // Reglas normales del juego
        System.out.println("LOG REGLAS: Jugando en Modo Normal.");
    }
        
        
        
        

 imagenIcono = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/skull.png"));
 imagenIconoDeath = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/skullRed.png"));

 icono = new ImageView(imagenIcono);
icono.setFitWidth(35);
icono.setFitHeight(35);
icono.setPreserveRatio(true);

// 2. 🔥 ALINEACIÓN DIRECTA EN TU ROOT (STACKPANE)
// Forzamos a la barra a centrarse y al icono a posicionarse arriba a la izquierda
StackPane.setAlignment(progressBar, Pos.CENTER);
StackPane.setAlignment(icono, Pos.TOP_LEFT);

// Le damos un margen superior negativo al icono para que flote justo por encima de la barra
StackPane.setMargin(icono, new javafx.geometry.Insets(0, 0, 0, 0));

// 3. AGREGAR EL ICONO AL ROOT
// Como ya tienes la barra ahí, solo inyectamos el icono encima
rootProgressbar.getChildren().add(icono); 

// 4. 🔥 LISTENER DE MOVIMIENTO (Matemática inversa de derecha a izquierda)
progressBar.progressProperty().addListener((obs, oldVal, newVal) -> {
    double progreso = newVal.doubleValue();
    if (progreso < 0) progreso = 0;
    if (progreso > 1) progreso = 1;

    // Obtenemos el ancho que tenga la barra en ese instante dentro del StackPane
    double anchoBarra = progressBar.getWidth();
    
    // Tu fórmula en reversa: empieza a la derecha y camina a la izquierda al bajar
    double desplazamientoX = anchoBarra - (anchoBarra * progreso) - 16;
    
    icono.setTranslateX(desplazamientoX);
});

// 5. LISTENER DE ESTIRAMIENTO
// Si el StackPane se estira (por ejemplo, al agrandar la ventana del juego),
// el icono se reajusta solo para no quedarse flotando en el lugar viejo
progressBar.widthProperty().addListener((obs, oldVal, newVal) -> {
    double progreso = progressBar.getProgress();
    if (progreso < 0) progreso = 0;
    if (progreso > 1) progreso = 1;
    
    double desplazamientoX = newVal.doubleValue() - (newVal.doubleValue() * progreso) - 16;
    icono.setTranslateX(desplazamientoX);
});

        
        cargarVolumenesGuardados();
           /*    Image gunImg = new Image(

    getClass().getResource(
        "/cr/ac/una/proyecto/resources/bomb.png"
    ).toExternalForm()
);

AudioClip gunSound =
        new AudioClip(

            getClass().getResource(
                "/cr/ac/una/proyecto/resources/Audios/hitBase.wav"
            ).toExternalForm()
        );

gunBase = new GunBase(

        30,
        250,

        gunImg,

        gunSound
);

gun.getChildren().add(
    gunBase.getSprite()
);
               
      /////////////////////////////////////////////////////////////////////////////////         
       */        
// Dentro de tu método aplicarMejorasAlJugador() o al iniciar la partida:


// Seteamos la propiedad local al máximo permitido por su mejora


// Iniciamos el bindeo de la barra
damageFueraImg= new Image(
    getClass()
    .getResource("/cr/ac/una/proyecto/resources/nuclearDesactivado.png")
    .toExternalForm()
);
               damageImg = new Image(
    getClass()
    .getResource("/cr/ac/una/proyecto/resources/nuclear.png")
    .toExternalForm()
);

               
               
               freezeFueraImg = new Image(
    getClass()
    .getResource("/cr/ac/una/proyecto/resources/hieloDesactivado.png")
    .toExternalForm()
);
freezeImg = new Image(
    getClass()
    .getResource("/cr/ac/una/proyecto/resources/hielo.png")
    .toExternalForm()
);


freezeCaer= new Image(
    getClass()
    .getResource("/cr/ac/una/proyecto/resources/snow.png")
    .toExternalForm()
);
damageCaer= new Image(
    getClass()
    .getResource("/cr/ac/una/proyecto/resources/bombCaer.png")
    .toExternalForm()
);



                
             SoundManager.initWalkSound();
                  // zombie
             //  zombieWalk = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/walk.wav").toExternalForm());
               zombieAttack = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/atackZombie.wav").toExternalForm());
               zombieDeath = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/deathZombie.wav").toExternalForm());
               zombieHit= new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/hitEnemy.wav").toExternalForm());
                 // rapido
             // fastWalk = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/walk.wav").toExternalForm());
               fastDeath = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/deathFast.wav").toExternalForm());
               fastAttack = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/atackFast.wav").toExternalForm());    
               // lento
             //   tankWalk = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/walk.wav").toExternalForm());
               tankDeath = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/deathTank.wav").toExternalForm());
               tankAttack = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/atackZombie.wav").toExternalForm());    
               //distancia
             //  distanceWalk = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/walk.wav").toExternalForm());
               distanceDeath = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/deathFast.wav").toExternalForm());
               distanceAttack = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/atackDistance.wav").toExternalForm());    
               //movedor
             //   moveWalk = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/walk.wav").toExternalForm());
               moveDeath = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/deathZombie.wav").toExternalForm());
               moveAttack = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/atackZombie.wav").toExternalForm()); 
               
               
               
               bombSound = new AudioClip(
    getClass().getResource(
        "/cr/ac/una/proyecto/resources/Audios/Explotion.wav"
    ).toExternalForm()
);

freezeSound = new AudioClip(
    getClass().getResource(
        "/cr/ac/una/proyecto/resources/Audios/snowball.wav"
    ).toExternalForm()
);
               
               waveSound = new AudioClip(

    getClass().getResource(
        "/cr/ac/una/proyecto/resources/Audios/round.wav"
    ).toExternalForm()
);

finalWaveSound = new AudioClip(

    getClass().getResource(
        "/cr/ac/una/proyecto/resources/Audios/finalRound.wav"
    ).toExternalForm()
);
               

        actualizarBarraDeVida();



      
         
         // Pon esto dentro de tu método bindearDatosUsuario()
if (usuarioActivo != null && usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
    
    var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
    
    // 🛡️ LIMPIEZA: Rompemos cualquier bindeo anterior para que no choque al reiniciar
    LblCoins.textProperty().unbind();
    
    // 🔥 EL BINDEO MÁGICO CORREGIDO:
    // Tu forma usando .asString() está perfecta. 
    LblCoins.textProperty().bind(configNivel.userPointsProperty().asString());
    
    System.out.println("LOG GAME: LblCoins bindeado con éxito a los puntos de la BD.");
}
         
           progressBar.setScaleX(-1);
         //Caminar
           monsterNormalImg = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Walk9.png"));
            monsterFastImg  = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Run.png"));
            monsterTankImg  = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Walk4.png"));
            monsterDistanceImg   = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Walk8.png"));
            monsterMoveImg  = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Run7.png"));
            //Atacar
            
            monsterNormalImg1 = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Attack.png"));
         monsterFastImg1 = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Attack_1.png"));
          monsterTankImg1= new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Attack4.png"));
           monsterMoveImg1  = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Attack_7.png"));
           monsterDistanceImg1= new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Shot_1.png"));
          //Morir
           monsterNormalImg3 = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Dead2.png"));
           monsterFastImg3 = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Dead1.png"));
          monsterTankImg3= new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Dead4.png"));
           monsterMoveImg3  = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Dead7.png"));
           monsterDistanceImg3= new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Dead8.png"));
          totalEnemigosNivel = 0;
         //Freeze
          zombieFreeze= new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/zombieF1.png"));
           fastFreeze= new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/fastFreeze.png"));
            tankFreeze= new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/tankFreeze.png"));
             distanceFreeze= new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/distanceFreeze.png"));
              moveFreeze= new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/moveFreeze.png"));
          ///Hit
          monsterNormalHitImg= new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/zombieHurt1.png"));
            monsterFastHitImg= new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/fastHurt.png"));
              monsterTankHitImg= new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/tankHurt1.png"));
                monsterDistanHitImg= new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/distanceHurt1.png"));
                  monsterMoveHitImg= new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/MoveHurt1.png"));
totalEnemigosNivel += MAX_WAVES * 3;

// oleadas
for (int i = 1; i <= MAX_WAVES; i++) {
    totalEnemigosNivel += 5;
}
        var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();      
   double elixirMaximo =  150 + configNivel.getUserLevelCapacityelixir() * 50;
        elixirBase = (int) elixirMaximo;
iniciarElixirLoop();





loop = new AnimationTimer() {
    @Override
    public void handle(long now) {
        
        double anguloActual = gun.getRotate();


        if (controlActivo.equals("K")) {
            // Aumentamos un poco la velocidad de apuntado para que se sienta responsivo (ej: 2.5 grados por frame)
            double VELOCIDAD_TECLADO = 0.5; 
            
            if (keyUp) {
                anguloActual -= VELOCIDAD_TECLADO;
            }
            if (keyDown) {
                anguloActual += VELOCIDAD_TECLADO;
            }
        }
        // ======================================================================
        // 🖱️ REGLA 2: CONTROL POR MOUSE ("M")
        // ======================================================================
        else {
            // El mouse guía el ángulo en tiempo real sin importar si presionas teclas
            double gunCenterX = gun.localToScene(gun.getLayoutBounds().getWidth() / 2, gun.getLayoutBounds().getHeight() / 2).getX();
            double gunCenterY = gun.localToScene(gun.getLayoutBounds().getWidth() / 2, gun.getLayoutBounds().getHeight() / 2).getY();

            double deltaX = mouseXActual - gunCenterX;
            double deltaY = mouseYActual - gunCenterY;
            
            if (deltaX != 0 || deltaY != 0) {
                anguloActual = Math.toDegrees(Math.atan2(deltaY, deltaX));
                
                // Si el mouse se pasa detrás del jugador (lado izquierdo), bloqueamos el ángulo mirando al frente
                if (deltaX < 0) {
                    anguloActual = (deltaY < 0) ? -85 : 85; 
                }
            }
        }

        // ======================================================================
        // 🔒 REGLA 3: LÍMITES ESTRICTOS Y RENDERIZADO (Para ambos controles)
        // ======================================================================
        if (anguloActual < -85) anguloActual = -85;
        else if (anguloActual > 85) anguloActual = 85;

        // Aplicamos la rotación final calculada
        gun.setRotate(anguloActual);

        // ======================================================================
        // 🔫 REGLA 4: SISTEMA DE DISPARO DINÁMICO
        // ======================================================================
        // Teclado dispara con Espacio, Mouse dispara manteniendo el Clic
        boolean quiereDisparar = controlActivo.equals("K") ? keySpace : isShooting;

        if (quiereDisparar) {
            double disparoIntervaloNs = 1_000_000_000.0 / currentGun.getAttackSpeed();
            if (now - lastShotTime >= disparoIntervaloNs) {
                disparar(); 
                lastShotTime = now;
            }
        }
    

        // 🎮 TU LIMITADOR FPS ORIGINAL
        if (now - lastUpdate < 20_000_000) {
            return;
        }

        lastUpdate = now;

            double limiteGun =
                    rootGun.getBoundsInParent().getMinX();

            Iterator<Enemy> it = enemy.iterator();
            boolean hayCaminando = false;
            while (it.hasNext()) {

                Enemy m = it.next();

                m.update(limiteGun);
if (!m.isAtacando()
        && !m.isDying()&& !m.isFroozen()) {

    hayCaminando = true;
}
                long currentTime =
                        System.currentTimeMillis();

                // ⚔️ ATAQUE
                if (m.isAtacando()) {

                    if (currentTime - m.lastAttackTime >= 1000) {

                        if (m.getDistanceAtack() > 0) {

                            Projectile p = m.shoot();

                            m.playAttackSound();

                            enemyProjectiles.add(p);

                            rootSpawner.getChildren()
                                    .add(p.getSprite());

                        } else {

                           healthBase =healthBase- m.getDamageSecond();
                           actualizarBarraDeVida();
                            m.playAttackSound();
                        }

                        m.lastAttackTime = currentTime;
                    }
                }

                // 💀 MUERTE
// Muerte
    if (m.isDying() && !m.isRewardGiven()) {

        // 1. Verificamos que tengamos un usuario activo y que tenga su lista de configuración
        if (usuarioActivo != null && usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            
            // 2. Obtenemos el DTO de la configuración de nivel (donde residen los puntos)
            var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
            
            // 3. Obtenemos los puntos actuales desde su propiedad de JavaFX
            long puntosActuales = configNivel.userPointsProperty().get();
            
            // 4. Sumamos los puntos directamente a la propiedad observable
            configNivel.userPointsProperty().set(puntosActuales + 5);
            
            System.out.println("LOG GAME: ¡Monstruo derrotado! +5 puntos añadidos a " + usuarioActivo.getUserName());
            
            // El brinquito de la moneda de recolección
            if (imageCoin != null) {
                // Creamos la animación de traslación (duración de 150 milisegundos)
                javafx.animation.TranslateTransition brinco = new javafx.animation.TranslateTransition(
                    javafx.util.Duration.millis(150), 
                    imageCoin
                );
                
                // Definimos el movimiento relativo en el eje Y (sube 15 píxeles)
                brinco.setByY(-15); 
                
                // Al activarse el auto-reverse y 2 ciclos, sube y vuelve a bajar automáticamente
                brinco.setAutoReverse(true);
                brinco.setCycleCount(2); 
                
                // Arrancamos el brinco de forma independiente al hilo principal del juego
                brinco.play();
            }
            
        } else {
            System.err.println("LOG GAME: No se pudieron otorgar los puntos porque no hay un usuario activo con configuración en memoria.");
        }

        // 5. Marcamos que el monstruo ya dio su recompensa para que no sume infinito
        m.setRewardGiven(true);
    }

                // 🗑 eliminar
                if (m.isDeadA()) {

                    rootSpawner.getChildren()
                            .remove(m.getView());

                    it.remove();
                }
            }
if (hayCaminando) {

    SoundManager.playWalk();

} else {

    SoundManager.stopWalk();
}
//////////////////////////////////////////////////
// 🔫 GUN BASE

        //////////////////////////////////////////////////

         // 1. EL BUCLE DE TUS ENEMIGOS (El que ya tenías)
Iterator<Projectile> pitEnemigos = enemyProjectiles.iterator();
while (pitEnemigos.hasNext()) {
    Projectile p = pitEnemigos.next();
    p.update();
    if (p.hitBase(limiteGun)) {
         p.playHitSound();
         healthBase = healthBase- p.getDamage();
         actualizarBarraDeVida();
         rootSpawner.getChildren().remove(p.getSprite());
         pitEnemigos.remove();
    }
}

// 2. EL BUCLE DE TU ARMA (Adaptado a tu estilo de Iterator)
Iterator<Projectile> pitJugador = playerProjectiles.iterator();
while (pitJugador.hasNext()) {
    Projectile p = pitJugador.next();
    p.update();

    boolean balaImpacto = false;
    
    // Obtenemos los límites de la bala en la escena global
    Bounds balaScene = p.getSprite().localToScene(p.getSprite().getBoundsInLocal());

    for (Enemy e : enemy) {
        if (e.isDying()) {
            continue;
        }

        // 🎯 Sincronización perfecta usando el método que ya te funciona:
        // Transformamos la hitbox del enemigo directamente al mundo de la Escena (Global)
        Bounds enemigoHitboxScene = e.getView().localToScene(e.getHitbox1());

        // Comparamos los dos objetos en el mismo plano global de la pantalla
        if (balaScene.intersects(enemigoHitboxScene)) {
            
            // 1. Aplicamos el daño y su knockback
            e.takeDamage(p.getDamage()); 
            
            // 2. Removemos la bala
            rootSpawner.getChildren().remove(p.getSprite());
            pitJugador.remove(); 
            
            balaImpacto = true;
            break; 
        }
    }

    if (balaImpacto) {
        continue;
    }

    if (p.getX() > rootSpawner.getWidth() + 100 || p.getX() < -400 || 
        p.getY() > rootSpawner.getHeight() + 100 || p.getY() < -100) {
        
        rootSpawner.getChildren().remove(p.getSprite());
        pitJugador.remove(); 
    }
}

      // 🏆 WIN
if (lblProgress.getText().equals("100%")
       && enemy.isEmpty()
       && healthBase > 0) {

   
    gameOver = true;
    loop.stop();

    if (spawnLoop != null)
        spawnLoop.stop();

    if (elixirLoop != null)
        elixirLoop.stop();

    // ======================================================================
    // 🔥 GUARDAR PROGRESO EN LA BASE DE DATOS (Nivel + 1 y Puntos Actualizados)
    // ======================================================================
    if (usuarioActivo != null && usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
        try {
            // 1. Extraemos el objeto de niveles del DTO
            UsersConfigLevelDto niveles = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();

            // 2. Sincronizamos nivel (Le sumamos 1 al nivel actual)
            Long nivelActual = niveles.getUserLevel();
            
            if(nivelActual>=100){ playWinAnimation(rootWin1,rootWinAnimation1);}
            else{ niveles.setUserLevel(nivelActual + 1);   playWinAnimation(rootWin,rootWinAnimation);}

            // 3. Sincronizamos puntos (IMPORTANTÍSIMO)
            // Extraemos el valor primitivo directamente del bindeo de la UI
            Long puntosActuales = niveles.userPointsProperty().getValue(); 
            niveles.setUserPoints(puntosActuales); 
            
            // Log de control para ver qué le estamos mandando al Service en consola
            System.out.println("LOG UI WIN: Enviando al Service -> Nivel Nuevo: " + (nivelActual + 1) + " | Puntos Ganados: " + puntosActuales);

            // 4. Enviamos al servicio modificado
            UsersService usersService = new UsersService(); 
            UsersDto usuarioGuardado = usersService.guardarUsuario(usuarioActivo);
            
            if (usuarioGuardado != null) {
                // 5. Actualizamos el contexto global con la respuesta fresca de la BD
                AppContext.getInstance().setUsuarioSeleccionado(usuarioGuardado);
                
                // Actualizamos nuestra variable local de la pantalla
                usuarioActivo = usuarioGuardado; 
                
                System.out.println("LOG GAME: ¡Nivel y puntos sincronizados con éxito en TOAD!");
            } else {
                System.err.println("LOG GAME: El servicio retornó null. Hubo un fallo en la transacción.");
            }
            
        } catch (Exception e) {
            System.err.println("LOG GAME: Error crítico al intentar guardar el progreso: " + e.getMessage());
            e.printStackTrace();
        }
    }
    // ======================================================================

   
    SoundManager.stopWalk();
    SoundManager.stopFondo1Sound2();
    SoundManager.playWin();
}

            // 💀 GAME OVER
            if (!gameOver
                    && healthBase <= 0) {
               forceStopDrag();
                healthBase=0;

                gameOver = true;

                loop.stop();

                if (spawnLoop != null)
                    spawnLoop.stop();

                if (elixirLoop != null)
                    elixirLoop.stop();
                
                playLoseAnimation();
                SoundManager.stopWalk();
                  
                  SoundManager.playLose();
            }
      //  }
    }
};

loop.start();

    iniciarNivel();
}
private void playWinAnimation(VBox rootWin,VBox rootAnimate) {

    rootWin.setVisible(true);

    // 🔥 empieza arriba
    rootAnimate.setTranslateY(-900);

    // ⬇️ caída principal
    TranslateTransition fall =
            new TranslateTransition(
                    Duration.seconds(0.7),
                    rootAnimate
            );

    fall.setToY(0);

    // 🔼 pequeño rebote arriba
    TranslateTransition bounceUp =
            new TranslateTransition(
                    Duration.seconds(0.2),
                    rootAnimate
            );

    bounceUp.setToY(-40);

    // ⬇️ vuelve al centro
    TranslateTransition bounceDown =
            new TranslateTransition(
                    Duration.seconds(0.15),
                    rootAnimate
            );

    bounceDown.setToY(0);

    SequentialTransition seq =
            new SequentialTransition(
                    fall,
                    bounceUp,
                    bounceDown
            );

    seq.play();
}
private void playLoseAnimation() {

    rootGameOver.setVisible(true);

    // 🔥 empieza abajo
    rootLoseAnimation.setTranslateY(800);

    // ⬆️ subida rígida
    TranslateTransition rise =
            new TranslateTransition(
                    Duration.seconds(0.4),
                    rootLoseAnimation
            );

    rise.setToY(0);

    // más rígido/seco
    rise.setInterpolator(
            Interpolator.LINEAR
    );

    rise.play();
}


    
 private void spawnMonsterNormal() {
     
     if(this.modoJuegoActivo.equals("E"))
    crearMonster(1,damageEnemies[0],speedEnemies[0], -5,false, monsterNormalImg,monsterNormalImg1,monsterNormalImg3,zombieFreeze,monsterNormalHitImg,zombieAttack,zombieDeath,zombieHit);
     else 
         crearMonster(getEnemyHealth(0),getEnemyDamage(0),getEnemySpeed(0), -5,false, monsterNormalImg,monsterNormalImg1,monsterNormalImg3,zombieFreeze,monsterNormalHitImg,zombieAttack,zombieDeath,zombieHit);
     
}

private void spawnMonsterFast() {
     if(this.modoJuegoActivo.equals("E"))
    crearMonster(1,damageEnemies[1],speedEnemies[1], 0,false, monsterFastImg,monsterFastImg1,monsterFastImg3,fastFreeze,monsterFastHitImg,fastAttack,fastDeath,zombieHit);
     else crearMonster(getEnemyHealth(1),getEnemyDamage(1),getEnemySpeed(1), 0,false, monsterFastImg,monsterFastImg1,monsterFastImg3,fastFreeze,monsterFastHitImg,fastAttack,fastDeath,zombieHit);
}

private void spawnMonsterTank() {
    if(this.modoJuegoActivo.equals("E"))
    crearMonster(1,damageEnemies[2],speedEnemies[2], -15,false, monsterTankImg,monsterTankImg1,monsterTankImg3,tankFreeze,monsterTankHitImg,tankAttack,tankDeath,zombieHit);
     else crearMonster(getEnemyHealth(2),getEnemyDamage(2),getEnemySpeed(2), -15,false, monsterTankImg,monsterTankImg1,monsterTankImg3,tankFreeze,monsterTankHitImg,tankAttack,tankDeath,zombieHit);
}
private void spawnMonsterAereo() {
    if(this.modoJuegoActivo.equals("E"))
    crearMonster(1,damageEnemies[3],speedEnemies[3], 700,false, monsterDistanceImg,monsterDistanceImg1,monsterDistanceImg3,distanceFreeze,monsterDistanHitImg,distanceAttack,distanceDeath,zombieHit);
     else crearMonster(getEnemyHealth(3),getEnemyDamage(3),getEnemySpeed(3), 700,false, monsterDistanceImg,monsterDistanceImg1,monsterDistanceImg3,distanceFreeze,monsterDistanHitImg,distanceAttack,distanceDeath,zombieHit);
}
private void spawnMonsterMove() {
    if(this.modoJuegoActivo.equals("E"))
    crearMonster(1,damageEnemies[4],speedEnemies[4], 0,true, monsterMoveImg,monsterMoveImg1,monsterMoveImg3,moveFreeze,monsterMoveHitImg,moveAttack,moveDeath,zombieHit);
     else crearMonster(getEnemyHealth(4),getEnemyDamage(4),getEnemySpeed(4), 0,true, monsterMoveImg,monsterMoveImg1,monsterMoveImg3,moveFreeze,monsterMoveHitImg,moveAttack,moveDeath,zombieHit);
}
    
    private void crearMonster(double health,double damageSecond, double speed, int distanceAttack,boolean movedY, Image img,Image img2, Image img3,Image img4,Image img5
    ,AudioClip attackSound, AudioClip deathSound,AudioClip hitSound) {
    double x = rootSpawner.getWidth() + 50;
    double y = Math.random() * (rootSpawner.getHeight() - 150);
    Enemy m = new Enemy(health,damageSecond, speed,distanceAttack,movedY, x, y, img,img2,img3,img4,img5,attackSound,deathSound,hitSound);
    enemy.add(m);
rootSpawner.getChildren().add(m.getView());
}
    private void crearMonster1(int tipo) {

         switch (tipo) {
    case 0:
        spawnMonsterNormal();
        break;
    case 1:
        spawnMonsterFast();
        break;
    case 2:
        spawnMonsterTank();
        break;
          case 3:
        spawnMonsterAereo();
        break;
            case 4:
        spawnMonsterMove();
        break;
}
    }

    
    
    
    
    
    
// Llámalo SIEMPRE al iniciar el nivel (por ejemplo, en tu método comenzarJuego())
public void iniciarNivel() {
    
     iniciarSecuencia();
    wave = 1; // Empezamos en la oleada 1
    enemigosGeneradosTotal = 0;
    calcularTotalNivel(); // <--- Primero calculamos el total exacto
    actualizarBarra();
   // PauseTransition contador = new PauseTransition(Duration.seconds(0));

// Definir la acción que se ejecutará al terminar los 2 segundos
//contador.setOnFinished(event -> {
    iniciarPreSpawn();
//});

// Iniciar el contador//
//contador.play();
    
    // <--- Arranca el juego con los 3 lentos
}

private void iniciarPreSpawn() {
    if (gameOver) return;

    enPreSpawn = true;
    enOleada = false;
    enemigosSpawneados = 0;

    spawnLoop = new Timeline(
        new KeyFrame(
            Duration.seconds(4), // ⏱️ Más lento (cada 2 segundos)
            e -> {
                int tipo = getTipoEnemigo();
                crearMonster1(tipo);
                
                enemigosGeneradosTotal++;
                actualizarBarra();
            }
        )
    );

    // 🎯 SIEMPRE 3 enemigos sin importar el nivel
    spawnLoop.setCycleCount(3);

    spawnLoop.setOnFinished(e -> {
        if (gameOver) return;
        iniciarOleada(); // Al terminar los 3, pasa a la oleada real
    });

    spawnLoop.play();
}

private void iniciarOleada() {
    if (gameOver) return;

    enPreSpawn = false;
    enOleada = true;
    enemigosSpawneados = 0;
    enemigosPorSpawn = getEnemigosPorWave(); // Cantidad según el nivel

    // Reproducir sonido de oleada normal o final
    if (wave >= getMaxWaves()) {
        finalWaveSound.play(SoundManager.getEnemyVolume());
         icono.setImage(imagenIconoDeath);
   } else {
    waveSound.play(SoundManager.getEnemyVolume());
    
    // 1. 🔥 PONEMOS LA PRIMERA IMAGEN (La que va a parpadear)
    icono.setImage(imagenIconoDeath);
    
    // 2. CONFIGURAMOS EL PARPADEO (FadeTransition)
    // Hacemos que cada ciclo dure 200 milisegundos (un parpadeo rápido)
    FadeTransition parpadeo = new FadeTransition(Duration.millis(200), icono);
    parpadeo.setFromValue(1.0);  // Totalmente visible
    parpadeo.setToValue(0.2);    // Casi invisible (opacidad baja)
    parpadeo.setCycleCount(10);  // 10 ciclos de 200ms = 2000ms (2 segundos exactos)
    parpadeo.setAutoReverse(true); // Va y viene (se apaga y se enciende)
    
    // 3. 🔥 CUANDO TERMINEN LOS 2 SEGUNDOS...
    parpadeo.setOnFinished(event -> {
        icono.setImage(imagenIcono); // Cambiamos a la otra imagen
        icono.setOpacity(1.0);       // Nos aseguramos de que vuelva a ser 100% visible
    });
    
    // 4. Arrancar la animación
    parpadeo.play();
}
    spawnLoop = new Timeline(
        new KeyFrame(
            Duration.seconds(getSpawnSpeed()), // ⏱️ ¡Aparecen más rápido! (Frecuencia mayor)
            e -> {
                int tipo = getTipoEnemigo();

                // 👾 Enemigo principal de la lista
                crearMonster1(tipo);
                enemigosSpawneados++;
                enemigosGeneradosTotal++;

                // 👾 Enemigos extras aleatorios (Bonus de dificultad)
                int extra = enemigosExtra();
                for (int i = 0; i < extra; i++) {
                    crearMonster1(tipo);
                    
                }

                actualizarBarra();
            }
        )
    );

    // El ciclo dura lo que indique la base de la oleada
    spawnLoop.setCycleCount(enemigosPorSpawn);

    spawnLoop.setOnFinished(e -> {
        if (gameOver) return;
        enOleada = false;
        siguienteFase(); // Revisa si va a otra oleada o termina el nivel
    });

    spawnLoop.play();
}

private void siguienteFase() {
    // Si aún quedan oleadas por jugar en este nivel
    if (wave < getMaxWaves()) {
        wave++; // Pasamos a la siguiente
        
        // Una pequeña pausa de 1.5 segundos entre oleada y el nuevo PreSpawn
        Timeline espera = new Timeline(
            new KeyFrame(Duration.millis(1500), e -> iniciarPreSpawn())
        );
        espera.setCycleCount(1); 
        espera.play();
    } else {
        // 🎉 ¡NIVEL COMPLETADO! Aquí pones tu lógica de victoria
        System.out.println("Has completado todas las oleadas de este nivel.");
    }
}

private int getEnemigosPorWave() {

    return 5 + (levelBind / 21);
}




private int getMaxWaves() {

    int level = levelBind;

    if (level < 21) {

        return 2;

    } else if (level < 41) {

        return 3;

    } else if (level < 61) {

        return 4;
    } else if (level < 81) {

        return 5;
    }

    return 6;
}


private double getSpawnSpeed() {
    int level = levelBind;

    if (level < 21) {
        return 2.5;
    } else if (level < 41) {
        return 2.0;
    } else if (level < 61) {
        return 1.5;
    }
    return 1.25; 
}
private int getTipoEnemigo() {
    int level = levelBind;

    if (level < 21) {
        return 0; 
    } else if (level < 41) {
        return (int)(Math.random() * 2);
    } else if (level < 61) {
        return (int)(Math.random() * 3);
    } else if (level < 81) {
        return (int)(Math.random() * 4); 
    }
    return (int)(Math.random() * 5); 
}


private double getEnemyHealth(int tipo) {

    int level = levelBind;

    double base = healthEnemies[tipo];

    if (level < 21) {

        return base;

    } else if (level < 41) {

        return base * 1.25;

    } else if (level < 61) {

        return base * 1.5;
    }   else if (level < 81) {

        return base * 1.75;
    }

    return base * 2;
}

private double getEnemyDamage(int tipo) {

    int level = levelBind;

    double base =
            damageEnemies[tipo];

   
    if (level < 21) {

        return base;

    } else if (level < 41) {

        return base * 1.15;

    } else if (level < 61) {

        return base * 1.25;
    }   else if (level < 81) {

        return base * 1.5;
    }

    return base * 2;

}
private double getEnemySpeed(int tipo) {

    int level = levelBind;

    double base =
            speedEnemies[tipo];

    if (level < 25) {

        return base;

    } else if (level < 50) {

        return base + 1;

    } else if (level < 75) {

        return base + 2;
    }

    return base + 3;
}


private void calcularTotalNivel() {
    totalEnemigosNivel = 0;
    int cantidadWaves = getMaxWaves();

    // Recorremos cada oleada que va a tener el nivel
    for (int i = 0; i < cantidadWaves; i++) {
        // 1. Sumamos los 3 fijos del PreSpawn de esta oleada
        totalEnemigosNivel += 3;

        // 2. Sumamos la base de la oleada
        int baseOleada = getEnemigosPorWave();
        totalEnemigosNivel += baseOleada;

        // 3. Simulamos matemáticamente cuántos extras van a salir por puro azar
     /*   for (int j = 0; j < baseOleada; j++) {
            totalEnemigosNivel += enemigosExtra();
        }*/
    }
}

private int enemigosExtra() {
    int level = levelBind;
    double chance;

  
    if (level < 21) chance = 0.05;
    else if (level < 41) chance = 0.10;
    else if (level < 61) chance = 0.15;
    else if (level < 81) chance = 0.20;
    else chance = 0.25;

    int extra = 0;
    if (Math.random() < chance) extra++;
    if (Math.random() < chance / 2) extra++;
    return extra;
}



private void actualizarBarra() {
    if (totalEnemigosNivel <= 0) return;

    double progreso = (double) enemigosGeneradosTotal / totalEnemigosNivel;

    // Tope de seguridad para que no rompa visualmente el 100%
    if (progreso > 1.0) progreso = 1.0;
    if (progreso < 0.0) progreso = 0.0;

    progressBar.setProgress(progreso);
    lblProgress.setText((int)(progreso * 100) + "%");
}











///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

@FXML
private void onActionReset(ActionEvent event) {
    rootNivel.setVisible(true);
    if (loop != null) loop.stop();
    if (spawnLoop != null) spawnLoop.stop();
    if (elixirLoop != null) elixirLoop.stop();

    // Reseteamos el estado del juego
    gameOver = false;
    enOleada = false;
    enPreSpawn = false;
    enemigosSpawneados = 0;
    enemigosGeneradosTotal = 0;
    wave = 0;

    // Limpieza de listas y del escenario gráfico
    enemy.clear();
    enemyProjectiles.clear();
    rootSpawner.getChildren().clear();
    progressBar.setProgress(0);
    lblProgress.setText(String.valueOf(0));

    // Recolectar datos actualizados de la base de datos
    refrescarUsuarioDesdeBaseDatos();
    cargarYVerificarUsuario();
    
    // Actualizar nivel de vida y elixir máximo
    if (usuarioActivo != null) {
        var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
        
        // Configuración de vida
        double vidaMaxima = configNivel.getUserLevelBaseheath() * 100;
        healthBase = (int) vidaMaxima;
        LblHP.setText(String.valueOf(healthBase));
        progressHealth.setProgress(1.0); 

        // Elixir empieza al máximo (ajusta el método según tu entidad de Oracle)
        double elixirMaximo =  150 + configNivel.getUserLevelCapacityelixir() * 50;
        elixirBase = (int) elixirMaximo;
        
        // Si tienes una barra visual o texto para el elixir, actualízalos aquí:
        // progressElixir.setProgress(1.0);
        // lblElixir.setText(String.valueOf(elixirBase));
    }

    // Ocultar menús y reiniciar sus valores visuales
    rootPause.setVisible(false);
    rootGameOver.setVisible(false);
    rootGameOver.setOpacity(1.0); 

    // Recalcular parámetros del nivel e iniciar de nuevo
    calcularTotalNivel();
    iniciarNivel();
    
    // Control de audio
    SoundManager.stopFondo1Sound2();
    SoundManager.playFondo1Sound2();

    // Reenlazar controles y volver a encender los loops
    reiniciarFocoYControles();
    loop.start();
    elixirLoop.play();
}

@FXML
private void onActionNextLevel(ActionEvent event) {
    rootNivel.setVisible(true);
    
    // 1. Detener absolutamente todos los loops antes de tocar variables
    if (loop != null) loop.stop();
    if (spawnLoop != null) spawnLoop.stop();
    if (elixirLoop != null) elixirLoop.stop(); 

    // 2. Recolectar datos actualizados (Sincronización con Oracle)
    refrescarUsuarioDesdeBaseDatos();
    cargarYVerificarUsuario();

    // 3. Reiniciar valores mecánicos
    gameOver = false;
    enOleada = false;
    enPreSpawn = false;
    enemigosSpawneados = 0;
    enemigosGeneradosTotal = 0;
    wave = 0;

    // 4. Actualizar nivel de vida y elixir con los datos nuevos de base de datos
    if (usuarioActivo != null) {
        var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
        
        // Actualización de vida
        double vidaMaxima = configNivel.getUserLevelBaseheath() * 100; 
        healthBase = (int) vidaMaxima;
        LblHP.setText(String.valueOf(healthBase));
        progressHealth.setProgress(1.0); 

        // Elixir empieza al máximo
        double elixirMaximo =  150 + configNivel.getUserLevelCapacityelixir() * 50;
        elixirBase = (int) elixirMaximo;
        
        // Si tienes una barra visual o texto para el elixir, actualízalos aquí:
        // progressElixir.setProgress(1.0);
        // lblElixir.setText(String.valueOf(elixirBase));

        System.out.println("LOG GAME: Siguiente nivel cargado. Vida: " + healthBase + " | Elixir Máximo: " + elixirBase);
    }

    // 5. Limpieza de escenario gráfico
    enemy.clear();
    enemyProjectiles.clear(); 
    rootSpawner.getChildren().clear();
    progressBar.setProgress(0);
    lblProgress.setText(String.valueOf(0));
    rootWin.setVisible(false);

    // 6. Recalcular parámetros del nuevo nivel e iniciar loops
    calcularTotalNivel();
    iniciarNivel();
    
    reiniciarFocoYControles();
    
    // Volvemos a encender los motores del juego limpios
    loop.start();
    elixirLoop.play(); 
}

    @FXML
    private void onActionPause(ActionEvent event) {
         
         if (loop != null) loop.stop();
        if (spawnLoop != null) spawnLoop.pause();
        if (elixirLoop != null) elixirLoop.pause();
        
        rootPause.setVisible(true);
        SoundManager.pauseFondo1Sound2();
        SoundManager.stopWalk();
    }

    @FXML
    private void onActionOut(ActionEvent event) {
          rootPause.setVisible(false);
          loop.start();
          spawnLoop.play();
          elixirLoop.play();
         SoundManager.playFondo1Sound2();
         SoundManager.playWalk();
    }

    /*private void addCoins(int amount) {
    coins += amount;
    LblCoins.setText(String.valueOf(coins));
      upgrades.setCoins(coins);
}*/

@FXML
private void onActionUpgrates(ActionEvent event) {
   
    if( rootPause.isVisible()){
   
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmación de salida");
    alert.setHeaderText("¿Estás seguro de ir a Mejoras?");
    alert.setContentText("Se perderá el progreso actual de la partida.");

    // Configurar botones en español
    ButtonType botonAceptar = new ButtonType("Salir");
    ButtonType botonCancelar = new ButtonType("Cancelar");
    alert.getButtonTypes().setAll(botonAceptar, botonCancelar);

    // 🔥 INYECTAR EL ESTILO CSS AL DIALOG PANE
    DialogPane dialogPane = alert.getDialogPane();
    try {
        dialogPane.getStylesheets().add(
            getClass().getResource("/cr/ac/una/proyecto/view/Style.css").toExternalForm()
        );
        dialogPane.getStyleClass().addAll("fx-FondoJuego11","label-usuario");
    } catch (Exception e) {
        System.out.println("No se pudo cargar el archivo CSS de la alerta, usando estilo por defecto.");
    }

    // 2. Mostrar y esperar respuesta
    Optional<ButtonType> result = alert.showAndWait();
     if (result.isEmpty() || result.get() == botonCancelar) {
        return; // Saca al método aquí, por lo que no se ejecuta nada de lo de abajo
    }
    }
    
          refrescarUsuarioDesdeBaseDatos();
cargarYVerificarUsuario();
   try {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/cr/ac/una/proyecto/view/UpgradesView.fxml")
    );

    Parent newView = loader.load();

  
    StackPane root = App.getRoot();

    newView.setTranslateY(-root.getHeight());

    root.getChildren().add(newView);

    TranslateTransition slide = new TranslateTransition(Duration.millis(800), newView);
    slide.setToY(0);

    slide.setOnFinished(e -> {
        root.getChildren().remove(0);
    });

    slide.play();
    SoundManager.stopFondo1Sound2();
} catch (IOException e) {
    e.printStackTrace();
}
}

    @FXML
    private void onActionHome(ActionEvent event) {  
         if( rootPause.isVisible()){
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmación de salida");
    alert.setHeaderText("¿Estás seguro de ir al menú principal?");
    alert.setContentText("Se perderá el progreso actual de la partida.");

    // Configurar botones en español
    ButtonType botonAceptar = new ButtonType("Salir");
    ButtonType botonCancelar = new ButtonType("Cancelar");
    alert.getButtonTypes().setAll(botonAceptar, botonCancelar);

    // 🔥 INYECTAR EL ESTILO CSS AL DIALOG PANE
    DialogPane dialogPane = alert.getDialogPane();
    try {
        dialogPane.getStylesheets().add(
            getClass().getResource("/cr/ac/una/proyecto/view/Style.css").toExternalForm()
        );
         dialogPane.getStyleClass().addAll("fx-FondoJuego11","label-usuario");
    } catch (Exception e) {
        System.out.println("No se pudo cargar el archivo CSS de la alerta, usando estilo por defecto.");
    }

    // 2. Mostrar y esperar respuesta
    Optional<ButtonType> result = alert.showAndWait();
     if (result.isEmpty() || result.get() == botonCancelar) {
        return; // Saca al método aquí, por lo que no se ejecuta nada de lo de abajo
    } 
        
         }
        
        
        
        
          refrescarUsuarioDesdeBaseDatos();
cargarYVerificarUsuario();
        try {
        Parent newView = App.loadFXML("HomeView");

        StackPane root = App.getRoot();

        newView.setTranslateX(-root.getWidth());

        root.getChildren().add(newView);

        TranslateTransition slide = new TranslateTransition(javafx.util.Duration.millis(800), newView);
        slide.setToX(0);

        slide.setOnFinished(e -> {
            root.getChildren().remove(0); // quita la anterior
        });

        slide.play();
      SoundManager.stopFondo1Sound2();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    
    
    
    
    
    
    
    private void startDrag(javafx.scene.input.MouseEvent event, String skill) {
       if (usuarioActivo == null || usuarioActivo.getUsersConfigList() == null || usuarioActivo.getUsersConfigList().isEmpty()) {
        return;
    }
    var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
    if (configNivel == null) return;
        
       if (this.elixirBase > 100
           && canUseSkill(skill)
) {
     
    // 1. Validaciones de seguridad del usuario y DTO


    // 2. 🔥 CONTROL DE ELIXIR: Cambia 'elixirActual' por el nombre de tu variable local de elixir de la partida
   
        
        currentSkill = skill;
        dragging = true;
        areaSkill = new ImageView();

        double radius = 0;

        // 3. 🔥 CÁLCULO DE RANGOS COINCIDENTES CON TU TIENDA Y APPLYSKILL
        if (skill.equals("damage")) {
            // Extraemos el nivel de rango de daño (ej: del 1 al 10)
            long nivelRangoDano = configNivel.userLevelPowerfreezerangeProperty().get();
            // Misma fórmula: 50px base + 15px por nivel
            radius = 50.0 + (nivelRangoDano * 8.0); 
            // iniciarAnimacionCooldown();
            
        } else {
            // Extraemos el nivel de rango de congelación
            long nivelRangoCongela = configNivel.userLevelPowerfreezerangeProperty().get();
            // Misma fórmula: 40px base + 12px por nivel
            radius = 40.0 + (nivelRangoCongela * 8.0);
            // iniciarAnimacionFreezeCooldown();
        }

        // El tamaño visual del ImageView es el diámetro (Radio * 2)
        double size = radius * 2;
        areaSkill.setFitWidth(size);
        areaSkill.setFitHeight(size);

        areaSkill.setMouseTransparent(true);
        areaSkill.setOpacity(0.5);

        // 4. Asignación de textura según la habilidad
        if (skill.equals("damage")) {
            areaSkill.setImage(damageImg);
        } else {
            areaSkill.setImage(freezeImg);
        }

        // Renderizado en el contenedor de JavaFX
        rootSpawner.getChildren().add(areaSkill);
        rootSpawner.setCursor(Cursor.NONE);

        moveCircle(event);

    } else {
        SoundManager.playErrorSound();
        if (canUseSkill(skill)) {
            Validations.showMessage(LblMessage, "Elixir insuficiente", rootValidations);
        } else {
            Validations.showMessage(LblMessage, "Recargando Poder", rootValidations);
        }
    }
}
    public void forceStopDrag() {
    // 1. Apagamos las variables de control
    this.dragging = false;
    this.currentSkill = null;

    // 2. Removemos el círculo visual del mapa si existe
    if (this.areaSkill != null && rootSpawner != null) {
        rootSpawner.getChildren().remove(this.areaSkill);
        this.areaSkill = null; // Liberamos memoria
    }

    // 3. Devolvemos el cursor normal del sistema a la pantalla
    if (rootSpawner != null) {
        rootSpawner.setCursor(javafx.scene.Cursor.DEFAULT);
    }
}
    private boolean canUseSkill(String skill) {
    long now = System.currentTimeMillis();

    if (skill.equals("damage")) {
        // Ahora depende 100% de si la animación terminó
        return visualDamageReady; 
    }

  if (skill.equals("freeze")) {
        // Ahora depende del estado de la animación de congelación
        return visualFreezeReady; 
    }

    return true;
}
private void applySkill(double xScene, double yScene) {
    // 1. Validamos que el usuario y la configuración del nivel estén cargados
    if (usuarioActivo == null || usuarioActivo.getUsersConfigList() == null || usuarioActivo.getUsersConfigList().isEmpty()) {
        System.err.println("LOG SKILLS: No se pueden aplicar habilidades sin datos de usuario.");
        return;
    }
    
    var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
    if (configNivel == null) return;

    double radius = 0;

    // 2. 🔥 CALCULO DE RANGOS DINÁMICOS BASADOS EN EL NIVEL DEL DTO
    if (currentSkill.equals("damage")) {
        // Extraemos el nivel de rango de daño de la bomba (ej: Nivel 1 al 10)
        long nivelRangoDano = configNivel.userLevelPowerfreezerangeProperty().get();
        // Fórmula: Rango base de 50px + 15px por cada nivel de mejora
        radius = 50.0 + (nivelRangoDano * 8.0); 
        
    } else if (currentSkill.equals("freeze")) {
        // Extraemos el nivel de rango de congelación
        long nivelRangoCongela = configNivel.userLevelPowerfreezerangeProperty().get();
        // Fórmula: Rango base de 40px + 12px por cada nivel de mejora
        radius = 40.0 + (nivelRangoCongela * 8.0);
    }

    // 3. RECORRIDO DE ENEMIGOS PARA DETECTAR ÁREA DE EFECTO (Hitbox)
    for (Enemy m : enemy) {

        Bounds b = m.getHitbox();
        Bounds sceneBounds = m.getView().localToScene(b);

        double closestX = clamp(xScene, sceneBounds.getMinX(), sceneBounds.getMaxX());
        double closestY = clamp(yScene, sceneBounds.getMinY(), sceneBounds.getMaxY());

        double dist = Math.hypot(xScene - closestX, yScene - closestY);

        // ¿El enemigo está dentro del círculo de la explosión/congelación?
        if (dist <= radius) {

            if (currentSkill.equals("damage")) {
           
                long nivelDanoBomba = configNivel.userLevelPowerbombdamageProperty().get();
            
                double danoRealBomba = 30.0 + (nivelDanoBomba * 15.0);
                
                m.takeDamage(danoRealBomba);
              
            }

            if (currentSkill.equals("freeze")) {
                // Activamos el estado congelado en el enemigo
               
                
                // Opcional: Si tu enemigo acepta un tiempo de congelación basado en el nivel:
                 long nivelTiempoFreeze = configNivel.userLevelPowerfreezedurationProperty().get();
                 double segundosCongelado = 1.0 + (nivelTiempoFreeze * 1.0); // 2s base + 1s por nivel
                 m.setFrozen(true,segundosCongelado);
                
               
            }
        }
    }
}
   

private void iniciarAnimacionCooldown() {
    // Ponemos la variable en false inmediatamente al disparar la habilidad
    visualDamageReady = false; 

    Arc cooldownArc = new Arc();
    double centroX = stackDamagePower.getWidth() / 2;
    double centroY = stackDamagePower.getHeight() / 2;
    double radio = Math.min(stackDamagePower.getWidth(), stackDamagePower.getHeight()) / 2;
    
    cooldownArc.setCenterX(centroX);
    cooldownArc.setCenterY(centroY);
    cooldownArc.setRadiusX(radio);
    cooldownArc.setRadiusY(radio);
    cooldownArc.setStartAngle(90); 
    cooldownArc.setLength(360); 
    cooldownArc.setType(ArcType.ROUND); 
    cooldownArc.setFill(Color.rgb(0, 0, 0, 0.6)); 
    cooldownArc.setManaged(false);
    cooldownArc.setMouseTransparent(true);

    stackDamagePower.getChildren().add(cooldownArc);

    Timeline timeline = new Timeline();
    KeyFrame kf = new KeyFrame(
        Duration.millis(DAMAGE_COOLDOWN),
        new KeyValue(cooldownArc.lengthProperty(), 0)
    );
    
    timeline.getKeyFrames().add(kf);
    
    // El secreto está aquí:
    timeline.setOnFinished(event -> {
        stackDamagePower.getChildren().remove(cooldownArc);
        
        // ¡JUSTO AQUÍ! En el instante exacto en que el círculo se borra,
        // la habilidad queda inmediatamente disponible para el próximo clic.
        visualDamageReady = true; 
    });

    timeline.play();
}
private void iniciarAnimacionFreezeCooldown() {
    // Bloqueamos la habilidad de inmediato
    visualFreezeReady = false; 

    Arc cooldownArc = new Arc();
    
    // Calculamos dimensiones basadas en el StackPane de Freeze
    double centroX = stackFreezePower.getWidth() / 2;
    double centroY = stackFreezePower.getHeight() / 2;
    double radio = Math.min(stackFreezePower.getWidth(), stackFreezePower.getHeight()) / 2;
    
    cooldownArc.setCenterX(centroX);
    cooldownArc.setCenterY(centroY);
    cooldownArc.setRadiusX(radio);
    cooldownArc.setRadiusY(radio);
    cooldownArc.setStartAngle(90); 
    cooldownArc.setLength(360); 
    cooldownArc.setType(ArcType.ROUND); 
    
    // Opcional: Un color azul oscuro semitransparente para que parezca congelado
    cooldownArc.setFill(Color.rgb(10, 40, 80, 0.6)); 
    
    cooldownArc.setManaged(false);
    cooldownArc.setMouseTransparent(true);

    // Añadir al contenedor de freeze
    stackFreezePower.getChildren().add(cooldownArc);

    // Animación (usa tu constante FREEZE_COOLDOWN)
    Timeline timeline = new Timeline();
    KeyFrame kf = new KeyFrame(
        Duration.millis(FREEZE_COOLDOWN),
        new KeyValue(cooldownArc.lengthProperty(), 0)
    );
    
    timeline.getKeyFrames().add(kf);
    
    // Desbloqueo en el milisegundo exacto en que termina
    timeline.setOnFinished(event -> {
        stackFreezePower.getChildren().remove(cooldownArc);
        visualFreezeReady = true; // ¡Disponible otra vez al instante!
    });

    timeline.play();
}



private void moveCircle(javafx.scene.input.MouseEvent event) {
    // 1. Validaciones obligatorias de seguridad del DTO
    if (usuarioActivo == null || usuarioActivo.getUsersConfigList() == null || usuarioActivo.getUsersConfigList().isEmpty()) {
        return;
    }
    var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
    if (configNivel == null) return;

    // Convertimos las coordenadas de la escena a locales del contenedor del mapa
    Point2D p = rootSpawner.sceneToLocal(event.getSceneX(), event.getSceneY());

    double radius = 0;

    // 2. 🔥 REPLICAMOS LA MATEMÁTICA EXACTA PARA EL RADIO
    if (currentSkill.equals("damage")) {
        // Extraemos el nivel de rango de daño de la bomba
        long nivelRangoDano = configNivel.userLevelPowerfreezerangeProperty().get();
        // Misma fórmula: 50px base + 15px por nivel
        radius = 50.0 + (nivelRangoDano  *8.0); 
        
    } else {
        // Extraemos el nivel de rango de congelación
        long nivelRangoCongela = configNivel.userLevelPowerfreezerangeProperty().get();
        // Misma fórmula: 40px base + 12px por nivel
        radius = 40.0 + (nivelRangoCongela * 8.0);
    }

    // 3. CENTRADO PERFECTO: Restamos el radio para que el cursor quede exactamente en el centro del círculo
    areaSkill.setLayoutX(p.getX() - radius);
    areaSkill.setLayoutY(p.getY() - radius);
}

    @FXML
    private void onMouseReleasedPane(javafx.scene.input.MouseEvent event) {
      
        if (!dragging) return;

    Point2D p = rootSpawner.sceneToLocal(event.getSceneX(), event.getSceneY());

    if (event.getButton() == MouseButton.PRIMARY) {

    applySkill(
        event.getSceneX(),
        event.getSceneY()
    );

   
  boolean dentro =
        p.getX() >= 0
        && p.getX() <= rootSpawner.getWidth()
        && p.getY() >= 0
        && p.getY() <= rootSpawner.getHeight();

    if (dentro) {
  this.elixirBase -= 100;
  
  
long now = System.currentTimeMillis();

if (currentSkill.equals("damage")) {
    iniciarAnimacionCooldown();
    
    // 🔥 CAMBIO: En lugar de explotar de golpe, inicia la caída desde el cielo
    lanzarPoderDesdeElCielo(p.getX(), p.getY(), "damage");
    
    lastDamageSkill = now;

} else if (currentSkill.equals("freeze")) {
    iniciarAnimacionFreezeCooldown();
    
    // 🔥 CAMBIO: Inicia la caída del meteorito de hielo
    lanzarPoderDesdeElCielo(p.getX(), p.getY(), "freeze");
    
    lastFreezeSkill = now;
}
    
    // 🔊 SONIDO
    if (currentSkill.equals("damage")) {

        bombSound.play(
        SoundManager.getEfectVolume()
    );

    } else {

         freezeSound.play(
        SoundManager.getEfectVolume()
    );
    }
    }else{}
}

    rootSpawner.getChildren().remove(areaSkill);
    dragging = false;
 
    rootSpawner.setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void onMouseDraggedPane(javafx.scene.input.MouseEvent event) {
            if (!dragging) return;

    Point2D p = rootSpawner.sceneToLocal(
        event.getSceneX(),
        event.getSceneY()
    );

    moveCircle(event);

    boolean dentro =
        p.getX() >= 0
        && p.getX() <= rootSpawner.getWidth()
        && p.getY() >= 0
        && p.getY() <= rootSpawner.getHeight();

    if (dentro) {

        if (currentSkill.equals("damage")) {

            areaSkill.setImage(damageImg);

        } else {

            areaSkill.setImage(freezeImg);
        }

    } else {
if (currentSkill.equals("damage")) {

            areaSkill.setImage(damageFueraImg);

        } else {

            areaSkill.setImage(freezeFueraImg);
        }

        
    }
    }

    @FXML
    private void onMousePressedDamage(javafx.scene.input.MouseEvent event) {
               if (event.getButton() == MouseButton.PRIMARY)
                          startDrag(event, "damage"); 
                       

         
    }
    private double clamp(double value, double min, double max) {
    return Math.max(min, Math.min(max, value));
}

    @FXML
    private void onMousePressedFreeze(javafx.scene.input.MouseEvent event) {
       //  if (event.getButton() == MouseButton.PRIMARY)
            startDrag(event, "freeze");
    }

  public void addProjectile(Projectile p) {
    enemyProjectiles.add(p);
    rootSpawner.getChildren().add(p.getSprite());
}
  
private void playExplosion(double x, double y) {

    Image explosionImg = new Image(
        getClass().getResource(
            "/cr/ac/una/proyecto/resources/explotion.png"
        ).toExternalForm()
    );

    ImageView explosion =
            new ImageView(explosionImg);

    int frameWidth = 550;

int frameHeight = 505;

int totalFrames = 9;

    explosion.setViewport(
        new Rectangle2D(
            0,
            0,
            frameWidth,
            frameHeight
        )
    );

    explosion.setFitWidth(250);

    explosion.setFitHeight(250);

    explosion.setLayoutX(x - 120);

    explosion.setLayoutY(y-130);

    rootSpawner.getChildren().add(explosion);

    Timeline timeline = new Timeline();

    for (int i = 0; i < totalFrames; i++) {

        final int frame = i;

        timeline.getKeyFrames().add(

            new KeyFrame(
                Duration.millis(i * 100),

                e -> {

                    explosion.setViewport(

                        new Rectangle2D(
                            frame * frameWidth,
                            0,
                            frameWidth,
                            frameHeight
                        )
                    );
                }
            )
        );
    }

    timeline.setOnFinished(e -> {

        rootSpawner.getChildren()
                .remove(explosion);
    });

    timeline.play();
}
private void playFreeze(double x, double y) {

    Image freezeImg = new Image(

        getClass().getResource(
            "/cr/ac/una/proyecto/resources/freeze.png"
        ).toExternalForm()
    );

    ImageView freeze =
            new ImageView(freezeImg);

    int frameWidth = 640;

    int frameHeight = 640;

    int totalFrames = 10;

    freeze.setViewport(

        new Rectangle2D(
            0,
            0,
            frameWidth,
            frameHeight
        )
    );

    freeze.setFitWidth(250);

    freeze.setFitHeight(250);

    freeze.setLayoutX(x - 120);

    freeze.setLayoutY(y - 120);

    rootSpawner.getChildren().add(freeze);

    Timeline timeline = new Timeline();

    for (int i = 0; i < totalFrames; i++) {

        final int frame = i;

        timeline.getKeyFrames().add(

            new KeyFrame(

                Duration.millis(i * 100),

                e -> {

                    freeze.setViewport(

                        new Rectangle2D(
                            frame * frameWidth,
                            0,
                            frameWidth,
                            frameHeight
                        )
                    );
                }
            )
        );
    }

    timeline.setOnFinished(e -> {

        rootSpawner.getChildren()
                .remove(freeze);
    });

    timeline.play();
}
private void lanzarPoderDesdeElCielo(double targetX, double targetY, String tipoSkill) {
    // 1. Crear el ImageView del proyectil que va a caer
    ImageView proyectilCae = new ImageView();
    proyectilCae.setFitWidth(80);  // Ajusta el tamaño de la imagen que cae como quieras
    proyectilCae.setFitHeight(80);
    
    // Asignamos el sprite correspondiente según lo que el usuario arrastró
    if (tipoSkill.equals("damage")) {
        proyectilCae.setImage(damageCaer); 
    } else {
        proyectilCae.setImage(freezeCaer); 
    }

    // 2. Posición inicial: Lo centramos en la X de destino, pero ARRIBA (fuera de la pantalla)
    proyectilCae.setLayoutX(targetX - (proyectilCae.getFitWidth() / 2));
    proyectilCae.setLayoutY(-100); // Empieza arriba en negativo para que parezca que cae del cielo

    // Lo agregamos temporalmente al mapa para que se vea la animación
    rootSpawner.getChildren().add(proyectilCae);

    // 3. Crear la animación de movimiento vertical (Caída)
    TranslateTransition caida = new TranslateTransition(Duration.millis(200), proyectilCae);
    caida.setFromY(0);
    // Calculamos el recorrido hasta la coordenada Y que seleccionó el jugador
    caida.setToY(targetY+90); 
    
    // Opcional: añade un efecto de aceleración realista para la gravedad
    caida.setInterpolator(javafx.animation.Interpolator.EASE_IN);

    // 4. 🔥 EL TRUCO CLAVE: Cuando termina de caer, explota
    caida.setOnFinished(event -> {
        // Removemos el proyectil limpio de la pantalla
        rootSpawner.getChildren().remove(proyectilCae);

        // Desactivamos la animación de la explosión o congelación en el punto exacto
        if (tipoSkill.equals("damage")) {
            playExplosion(targetX, targetY);
        } else {
            
            playFreeze(targetX, targetY);
        }
    });

    // ¡Arranca la caída!
    caida.play();
}










private void configurarSeguimientoMouse() {
    // 🔥 EL TRUCO: Si el control activo es Teclado ("K"), APAGAMOS por completo el mouse
    if (this.controlActivo.equals("K")) {
        System.out.println("LOG JUEGO: Desactivando listeners del mouse para evitar conflictos con el teclado.");
        
        // Limpiamos cualquier rastro de eventos del mouse para que no roben el foco
        rootSpawner.setOnMouseMoved(null);
        rootSpawner.setOnMouseDragged(null);
        rootGun.setOnMouseMoved(null);
        rootGun.setOnMouseDragged(null);
        
        return; // Nos salimos del método, no hay nada más que registrar aquí
    }

    // ======================================================================
    // 🖱️ SI ES MOUSE ("M"), REGISTRAMOS TODO NORMALMENTE ASÍ:
    // ======================================================================
    javafx.event.EventHandler<javafx.scene.input.MouseEvent> registrarPosicionMouse = event -> {
        mouseXActual = event.getSceneX();
        mouseYActual = event.getSceneY();
        mouseEnMovimiento = true;
    };

    rootSpawner.setOnMouseMoved(registrarPosicionMouse);
    rootSpawner.setOnMouseDragged(registrarPosicionMouse);
    rootGun.setOnMouseMoved(registrarPosicionMouse);
    rootGun.setOnMouseDragged(registrarPosicionMouse);
    
    System.out.println("LOG JUEGO: Listeners del mouse activados con éxito.");
}



private void disparar() {
    currentGun.playSound();

 


if (imagenAUsarDisparo != null) {

    gun.setImage(imagenAUsarDisparo);

    double anguloGradosRecoil = gun.getRotate();
    double anguloRadianesRecoil = Math.toRadians(anguloGradosRecoil);
    double fuerzaRetroceso = -15.0; 
    
    double recoilX = Math.cos(anguloRadianesRecoil) * fuerzaRetroceso;
    double recoilY = Math.sin(anguloRadianesRecoil) * fuerzaRetroceso;
    
    gun.setTranslateX(recoilX);
    gun.setTranslateY(recoilY);
    

    Timeline resetGunTimeline = new Timeline(
        new KeyFrame(Duration.millis(100), event -> {

            gun.setImage(imagenAUsarNormal);
            gun.setTranslateX(0); 
            gun.setTranslateY(0); 
        })
    );
    resetGunTimeline.play();
}


    double anguloGrados = gun.getRotate();
    double anguloRadianes = Math.toRadians(anguloGrados);


    double centroGunLocalX = imageGun2.getWidth() / 2;
    double centroGunLocalY = imageGun2.getHeight() / 2;

    Point2D gunCenterScene = gun.localToScene(centroGunLocalX, centroGunLocalY);

   double LARGO_CANON = 20.0; 
double ALTO_BOCA = -10.0;  

double offsetPuntaX = (LARGO_CANON * Math.cos(anguloRadianes)) + (ALTO_BOCA * Math.sin(anguloRadianes));
double offsetPuntaY = (LARGO_CANON * Math.sin(anguloRadianes)) - (ALTO_BOCA * Math.cos(anguloRadianes));


double puntaCanonSceneX = gunCenterScene.getX() + offsetPuntaX;
double puntaCanonSceneY = gunCenterScene.getY() + offsetPuntaY;


Point2D spawnPointFinal = rootSpawner.sceneToLocal(puntaCanonSceneX, puntaCanonSceneY);

// Ajuste del centro del sprite de la bala (Mitad de su tamaño de 15x15)
double spawnX = spawnPointFinal.getX() - 7.5;
double spawnY = spawnPointFinal.getY() - 7.5;

    // -------------------------------------------------------------------------

    // 6. Vector de dirección
    double dx = Math.cos(anguloRadianes);
    double dy = Math.sin(anguloRadianes);

    // 7. Instanciar el proyectil justo en la punta obtenida
    Projectile nuevoProyectil = new Projectile(
            spawnX, spawnY, 
            dx, dy, 
            40.5, // Velocidad
            (int) currentGun.getDamage(), 
            bulletGun, 
            currentGun.getShootSound(),
            15 // Tamaño
    );

    // Rotar el sprite del proyectil para que coincida con el cañón
if (nuevoProyectil.getSprite() != null) {
        nuevoProyectil.getSprite().setRotate(anguloGrados);
    }

    // 2. Registrar y meter en la pantalla
    playerProjectiles.add(nuevoProyectil); 

    // Agregamos únicamente el sprite de la bala al contenedor
    rootSpawner.getChildren().add(nuevoProyectil.getSprite());
}
private void reiniciarFocoYControles() {
    // 1. Obligar a los botones a perder el foco de nuevo por si se recrearon
    btnDamagePower.setFocusTraversable(false);
    btnFreezePower.setFocusTraversable(false);
    
    // 2. Volver a pedir el foco para el teclado en el contenedor principal
    rootSpawner.requestFocus();
    
    // 3. Resetear las posiciones del jugador para el nuevo nivel
    rootGun.setTranslateY(0);
    rootGun.setTranslateX(0);
    
    // 4. Forzar a que si el usuario hace clic en el mapa, el foco regrese inmediatamente
    rootSpawner.setOnMouseClicked(e -> rootSpawner.requestFocus());
    
    // 5. Volver a enlazar el mouse por si se borraron los listeners anteriores
    configurarSeguimientoMouse();
}
private void bindearDatosUsuario() {
    try {
        // Aseguramos que usuarioActivo exista antes de operar
        if (usuarioActivo == null) return;

        // 2. Accedemos a la tabla intermedia (Posición 0 de la lista)
        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            
            UsersConfigDto intermedia = usuarioActivo.getUsersConfigList().get(0);
            
            // 3. Accedemos al objeto que tiene los niveles reales
            UsersConfigLevelDto niveles = intermedia.getUsersConfigLevel();
            
            if (niveles != null) {
                
                // 🛡️ PASO EXTREMO: Desvincular bindeos viejos para evitar congelamientos en memoria
                LblLevel.textProperty().unbind();
                LblLevel1.textProperty().unbind();
                LblCoins.textProperty().unbind(); // <-- ¡Puntos sueltos!
                
                // 4. 🔥 LOS BINDS MAESTROS:
                // Sincronizamos tanto el Nivel como los Puntos (Coins) al nuevo DTO limpio de la BD
                LblLevel.textProperty().bind(niveles.userLevelProperty().asString());
                LblLevel1.textProperty().bind(niveles.userLevelProperty().asString());
                LblCoins.textProperty().bind(niveles.userPointsProperty().asString()); // <-- ¡Agregado aquí!
                
                System.out.println("LOG GAME: Bindeos refrescados en UI -> Nivel: " 
                        + niveles.getUserLevel() + " | Puntos Reales BD: " + niveles.getUserPoints());
            }
        }
    } catch (Exception e) {
        System.err.println("ERROR al bindear en el juego: " + e.getMessage());
        e.printStackTrace();
    }
}
private void cargarYVerificarUsuario() {
    usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();

    if (usuarioActivo != null) {
        // 🛡️ El escudo: Validamos si la lista tiene elementos
        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            
            // Si el DTO viene bien, extraemos el nivel real
            levelBind = (int) usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel().userLevelProperty().get();
            System.out.println("LOG GAME: Usuario cargado con éxito. Nivel real: " + levelBind);
            
        } else {
            // Si es un usuario nuevo recién creado o la lista falló, nivel 1 por defecto
            System.out.println("LOG GAME: Lista vacía en el DTO de usuario, asignando Nivel 1 por defecto.");
            levelBind = 1; 
        }
        
        // Se ejecuta el bindeo de datos de forma segura (aquí se enlaza el LblCoins)
        bindearDatosUsuario();
        
    } else {
        System.err.println("LOG GAME: ¡Error! No se encontró ningún usuario activo en el contexto.");
    }
}
private void refrescarUsuarioDesdeBaseDatos() {
    if (usuarioActivo != null && usuarioActivo.getUserId() != null) {
        try {
            UsersService usersService = new UsersService();
            
            // 1. Llamamos al servicio (el que tiene el em.refresh que modificamos)
            UsersDto usuarioLimpio = usersService.obtenerUsuario(usuarioActivo.getUserId());
            
            if (usuarioLimpio != null) {
                // 2. 🔥 REEMPLAZAMOS EL CONTEXTO GLOBAL CON EL LIMPIO
                AppContext.getInstance().setUsuarioSeleccionado(usuarioLimpio);
                
                // 3. 🔥 REEMPLAZAMOS LA VARIABLE LOCAL DEL JUEGO
                usuarioActivo = usuarioLimpio;
                
                System.out.println("LOG GAME: AppContext y variable local sincronizados con los datos puros de Oracle.");
            }
        } catch (Exception e) {
            System.err.println("LOG GAME: Error al refrescar usuario en el reset: " + e.getMessage());
        }
    }
}
private void aplicarMejorasAlJugador() {
    try {
        // 1. Traemos el usuario seleccionado con sus mejoras desde el AppContext
        usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();

        if (usuarioActivo != null && usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            
            var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();

            if (configNivel != null) {
                // 2. Extraemos los niveles puros (del 1 al 10) de la base de datos
                long nivelDanoArma = configNivel.userLevelGundamageProperty().get(); 
                long nivelVidaBase = configNivel.userLevelBaseheathProperty().get();
              //  long nivelCapacidadElixir = configNivel.userLevelCapacityelixirProperty().get();
                long nivelSpeedArma = configNivel.getUserLevelGunspeed();
                // 3. 🔥 MATEMÁTICA DEL JUEGO: Traducimos niveles a estadísticas reales
                // Daño Base = 10. Cada nivel extra suma +15 de daño.
this.damageGun = 25 + (int)((nivelDanoArma - 1) * 4); 

// Velocidad: Sube de 0.2 en 0.2 (por ejemplo, para que no sea un salto brusco de 1 en 1)
this.speedGun = 1 + (int)((nivelSpeedArma - 1) * 0.2); 
               this.healthBase = (int) ((nivelVidaBase * 100));
               
                // Ejemplo para la Vida Máxima del jugador (Si tienes una clase 'state' o 'player')
                // double maxHealth = 100.0 + (nivelVidaBase * 20.0);
                // state.setMaxHealth(maxHealth);
                // state.setHealth(maxHealth); // Inicia lleno

                System.out.println("LOG JUEGO: Estadísticas aplicadas -> Nivel Mejora Daño: " + nivelDanoArma + " | Daño Real de Balas: " + this.damageGun);
            }
        } else {
            // Escudo por si juegas sin iniciar sesión (valores por defecto)
            this.damageGun = 10; 
            System.out.println("LOG JUEGO: No hay usuario activo. Usando daño base por defecto (10.0).");
        }
    } catch (Exception e) {
        System.err.println("LOG JUEGO: Error al aplicar los efectos de las mejoras: " + e.getMessage());
        e.printStackTrace();
    }
}
private void actualizarBarraDeVida() {
    if (usuarioActivo != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
        var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
        
        // Conseguimos la vida máxima (el nivel guardado en Oracle)
        long vidaMaxima = configNivel.userLevelBaseheathProperty().get()*100;
        
        // Operación matemática simple con casteo a double para no perder decimales
        double progreso = (double) this.healthBase / vidaMaxima;
        
        // Limitadores de seguridad para que la barra no se desborde
        if (progreso > 1.0) progreso = 1.0;
        if (progreso < 0.0) progreso = 0.0;
        
        // Asignamos directamente el valor a la barra
        progressHealth.setProgress(progreso);
        LblHP.setText(String.valueOf(this.healthBase));
    }
}
 private void iniciarElixirLoop() {
    // 1. Validamos de seguridad básicas antes de arrancar el bucle
    if (usuarioActivo == null || usuarioActivo.getUsersConfigList() == null || usuarioActivo.getUsersConfigList().isEmpty()) {
        return;
    }
    
    var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
    if (configNivel == null) return;

    // 2. Definimos el bucle de tiempo (Cada 500ms)
    elixirLoop = new Timeline(
        new KeyFrame(Duration.millis(500), e -> {
            
            // 🔥 A. CALCULAR VELOCIDAD DE REGENERACIÓN DESDE EL DTO
            // Extraemos el nivel de velocidad de elixir (ej: del 1 al 10)
            long nivelVelocidadElixir = configNivel.userLevelSpeedelixirProperty().get();
            // Fórmula: Regeneración base de 1 punto + 1 punto extra por cada nivel de mejora
            int regenReal =  (int)(nivelVelocidadElixir);

            // 🔥 B. CALCULAR EL ELIXIR MÁXIMO DESDE EL DTO
            // Extraemos el nivel de capacidad/tamaño de elixir
            long nivelCapacidadElixir = configNivel.userLevelCapacityelixirProperty().get();
            // Fórmula: Capacidad base de 50 puntos + 10 puntos extra por nivel
            int maxElixirReal = 150 + ((int) nivelCapacidadElixir * 50);

            // C. APLICAMOS LA REGENERACIÓN A TU VARIABLE LOCAL
            int nuevoElixir = this.elixirBase + regenReal;

            // D. ESCUDO DE TOPE MÁXIMO
            if (nuevoElixir > maxElixirReal) {
                nuevoElixir = maxElixirReal;
            }

            // E. ACTUALIZAMOS NUESTRA VARIABLE DE PARTIDA
         
            this.elixirBase = nuevoElixir;
            
            // ======================================================================
            // 🚨 CONSEJO ADICIONAL: REFRESCAR LA INTERFAZ
            // ======================================================================
            // Si tienes un Label (ej: LblElixir) o una barra para el elixir en el juego,
            // debes actualizarlo aquí mismo para que se mueva en tiempo real:
             LblElixir.setText(this.elixirBase + " / " + maxElixirReal);
             progressElixir.setProgress((double) this.elixirBase / maxElixirReal);
            // ======================================================================
            
      if(this.elixirBase<100){lblElixirBomb.setVisible(true);lblElixirFreeze.setVisible(true); }
      else{lblElixirBomb.setVisible(false);lblElixirFreeze.setVisible(false);}
        })
    );
    
    
    // Ajustes de ciclo continuo del Timeline de JavaFX
    elixirLoop.setCycleCount(Timeline.INDEFINITE);
    elixirLoop.play();
}


    public void iniciarSecuencia() {
    
    // 1. Esperar los 1.5 segundos iniciales (según tu código de milisegundos)
    PauseTransition pausa = new PauseTransition(Duration.millis(1250));
    
    pausa.setOnFinished(event -> {
        
        // Deshabilitar rootNivel inmediatamente
       // rootNivel.setDisable(true);
        
        // 2. CALCULAR LA DISTANCIA HACIA LA IZQUIERDA
        // Obtenemos cuánto mide de ancho el panel que se va a mover
        double anchoAnimation = rootNIvelAnimation.getBoundsInParent().getWidth();
        
        // Obtenemos su posición X actual respecto a su contenedor
        double posX_Actual = rootNIvelAnimation.getLayoutX() + rootNIvelAnimation.getTranslateX();
        
        // Destino en negativo para ir a la izquierda
        double destinoIzquierda = -(posX_Actual + anchoAnimation);
        
        // 3. Configurar la animación de movimiento
        TranslateTransition animacion = new TranslateTransition(Duration.millis(1500), rootNIvelAnimation);
        animacion.setToX(destinoIzquierda); 
        
        // 4. Cuando termine de ocultarse por completo
        animacion.setOnFinished(animEvent -> {
            rootNivel.setVisible(false); 
            
            // ==========================================
            // 🔥 SOLUCIÓN AQUÍ: REINICIAR VALORES PREDETERMINADOS
            // ==========================================
            rootNIvelAnimation.setTranslateX(0); // Devuelve el nodo a su posición original oculta
        });
        
        // Arrancar la animación hacia la izquierda
        animacion.play();
    });

    // Iniciar el temporizador
    pausa.play();
}
    private void actualizarPosicionIcono(ProgressBar progressBar, ImageView icono) {
    double progreso = progressBar.getProgress();
    if (progreso < 0) progreso = 0;
    if (progreso > 1) progreso = 1;

    // Aquí obtenemos el ancho ya estirado de la barra en tiempo real
    double anchoBarraReal = progressBar.getWidth();

    // Tu fórmula matemática inversa de derecha a izquierda
    double desplazamientoX = anchoBarraReal - (anchoBarraReal * progreso) - 16;

    // Movemos el icono en caliente
    icono.setTranslateX(desplazamientoX);
}
    
    
    
    
    
    
    
    public void inicializarTipoDeControl() {
     usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();

    if (usuarioActivo != null && usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
        var configGeneral = usuarioActivo.getUsersConfigList().get(0).getUsersConfigGeneral();
        if (configGeneral != null && configGeneral.getUserController() != null) {
            this.controlActivo = configGeneral.getUserController(); // "K" o "M"
        }
    }
    System.out.println("LOG JUEGO: Sistema de control configurado en: " + this.controlActivo);
}
    public void cargarVolumenesGuardados() {
     usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
    
    if (usuarioActivo != null && usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
        var configGeneral = usuarioActivo.getUsersConfigList().get(0).getUsersConfigGeneral();
        
        if (configGeneral != null) {
            // 🔥 Sobreescribimos los valores iniciales de SoundManager con los reales de Oracle
            SoundManager.setmenuVolume(configGeneral.getUserAudiomenu());
            SoundManager.getEnemyVolumeProperty().set(configGeneral.getUserAudioenemys());
            SoundManager.getEfectVolumeProperty().set(configGeneral.getUserAudioefect());
            SoundManager.getGunVolumeProperty().set(configGeneral.getUserAudiogun());
            
            System.out.println("LOG SOUND: SoundManager inicializado con las preferencias reales de Oracle.");
        }
    }
}
}