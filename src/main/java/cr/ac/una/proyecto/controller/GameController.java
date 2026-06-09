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
import cr.ac.una.proyecto.util.Validations;
import java.io.IOException;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;


public class GameController implements Initializable {
    @FXML
    private Pane rootSpawner;
    @FXML
    private Pane rootProgressbar;
    @FXML
    private VBox rootPause;
    @FXML
    private VBox rootGameOver;
    @FXML
    private VBox rootWin;
    @FXML
    private VBox rootWin1;
    @FXML
    private VBox rootNivel;
    @FXML
    private VBox rootGun;
    @FXML
    private VBox rootValidations;
    //Capas de Animacion
    @FXML
    private VBox rootNIvelAnimation;
    @FXML
    private VBox rootWinAnimation;
    @FXML
    private VBox rootWinAnimation1;
    @FXML
    private VBox rootLoseAnimation;
    // Barras de Progreso
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ProgressBar progressHealth;
    @FXML
    private ProgressBar progressElixir;
    // Elementos de Imagen
    @FXML
    private ImageView gun;
    @FXML
    private ImageView imageCoin;
    private ImageView areaSkill;
    private ImageView icono;
    //Etiquetas de Texto
    @FXML
    private Label LblHP;
    @FXML
    private Label LblLevel;
    @FXML
    private Label LblLevel1;
    @FXML
    private Label LblCoins;
    @FXML
    private Label LblElixir;
    @FXML
    private Label lblProgress;
    @FXML
    private Label LblMessage;
    @FXML
    private Label lblElixirBomb;
    @FXML
    private Label lblElixirFreeze;
    // Textos Localizables
    @FXML
    private Label lblMonedas;
    @FXML
    private Label lblNivel;
    @FXML
    private Label lblRootNivel;
    @FXML
    private Label lblPausa;
    @FXML
    private Label lblGanaste;
    @FXML
    private Label lblPerder;
    // Botones Generales y Menú de Pausa
    @FXML
    private Button btnPausa;
    @FXML
    private Button btnPausaG;
    @FXML
    private Button btnPausaR;
    @FXML
    private Button btnPausaM;
    @FXML
    private Button btnPausaH;
    // Botones de la Pantalla de Victoria
    @FXML
    private Button btnGanasteS;
    @FXML
    private Button btnGanasteM;
    @FXML
    private Button btnGanasteH;
    // Botones de la Pantalla de Derrota 
    @FXML
    private Button btnPerderR;
    @FXML
    private Button btnPerderM;
    @FXML
    private Button btnPerderH;
    // Botones de Habilidades / Poderes
    @FXML
    private Button btnDamagePower;
    @FXML
    private Button btnFreezePower;
    @FXML
    private StackPane stackDamagePower;
    @FXML
    private StackPane stackFreezePower;
    private AnimationTimer loop;
    private Timeline spawnLoop;
    private Timeline elixirLoop;
    private long lastUpdate = 0;
    
    private List<Enemy> enemy = new ArrayList<>();
    private List<Projectile> playerProjectiles = new ArrayList<>();
    private List<Projectile> enemyProjectiles = new ArrayList<>();
    private Gun currentGun;
    // Enemigo Normal
    private Image monsterNormalImg;
    private Image monsterNormalImg1;
    private Image monsterNormalImg3;
    private Image monsterNormalHitImg;
    private Image zombieFreeze;
    // Enemigo Rápido 
    private Image monsterFastImg;
    private Image monsterFastImg1;
    private Image monsterFastImg3;
    private Image monsterFastHitImg;
    private Image fastFreeze;
    //  Enemigo Tanque 
    private Image monsterTankImg;
    private Image monsterTankImg1;
    private Image monsterTankImg3;
    private Image monsterTankHitImg;
    private Image tankFreeze;
    // Enemigo a Distancia
    private Image monsterDistanceImg;
    private Image monsterDistanceImg1;
    private Image monsterDistanceImg3;
    private Image monsterDistanHitImg;
    private Image distanceFreeze;
    // Enemigo Corredor
    private Image monsterMoveImg;
    private Image monsterMoveImg1;
    private Image monsterMoveImg3;
    private Image monsterMoveHitImg;
    private Image moveFreeze;
    //  Sprites y Texturas del Arma
    private Image imageGun;
    private Image imageGun2;
    private Image imageGunDisparando;
    private Image imageGunDisparando2;
    private Image imagenAUsarNormal;
    private Image imagenAUsarDisparo;
    private Image bulletGun;
    //  Texturas de Habilidades e Iconos 
    private Image damageImg;
    private Image damageFueraImg;
    private Image damageCaer;
    private Image freezeImg;
    private Image freezeFueraImg;
    private Image freezeCaer;
    private Image imagenIcono;
    private Image imagenIconoDeath;
    // Audio del Jugador
    private AudioClip audioGun;
    private AudioClip bombSound;
    private AudioClip freezeSound;
    private AudioClip waveSound;
    private AudioClip finalWaveSound;
    // Audios Enemigo Normal 
    private AudioClip zombieAttack;
    private AudioClip zombieHit;
    private AudioClip zombieDeath;
    // Audios Enemigo Rápido 
    private AudioClip fastAttack;
    private AudioClip fastDeath;
    // Audios Enemigo Tanque 
    private AudioClip tankAttack;
    private AudioClip tankDeath;
    // Audios Enemigo Distancia 
    private AudioClip distanceAttack;
    private AudioClip distanceDeath;
    // Audios Enemigo Movedor
    private AudioClip moveAttack;
    private AudioClip moveDeath;
    // Estados Generales de Partida 
    private boolean gameOver = false;
    private boolean enOleada = false;
    private boolean enPreSpawn = false;
    private String modoJuegoActivo;
    private String controlActivo;
    //  Sistema de Oleadas
    private int wave = 1;
    private final int MAX_WAVES = 3;
    private int enemigosPorSpawn = 0;
    private int enemigosSpawneados = 0;
    private int totalEnemigosNivel = 0;
    private int enemigosGeneradosTotal = 0;
    // Parámetros de Atributos
    private int healthBase;
    private int elixirBase;
    private int damageGun;
    private double speedGun;
    private int levelBind;
    private UsersDto usuarioActivo;
    // Configuración Balance de Enemigos
    private double[] healthEnemies = {50, 30, 100, 25, 50};
    private double[] speedEnemies = {5, 9, 3, 6, 7};
    private double[] damageEnemies = {4, 3, 7, 5, 4};
    // Mecánicas de Habilidades y Cooldowns 
    private boolean dragging = false;
    private String currentSkill = "";
    private boolean visualDamageReady = true;
    private boolean visualFreezeReady = true;
    private long lastDamageSkill = 0;
    private long lastFreezeSkill = 0;
    private final long DAMAGE_COOLDOWN = 5_000;
    private final long FREEZE_COOLDOWN = 5_000;
    //  Estados de Inputs
    private boolean keyUp = false;
    private boolean keyDown = false;
    private boolean keySpace = false;
    private boolean isShooting = false;
    private long lastShotTime = 0;
    // Estados de Input
    private double mouseXActual = 0;
    private double mouseYActual = 0;
    private boolean mouseEnMovimiento = false;

    private String elixir = "Elixir Insuficiente";
    private String tiempo = "Recargando Poder";
    // Modales de Confirmación 
    private String titulo1 = "Confirmación de salida";
    private String pricipal1 = "¿Estás seguro de ir a Mejoras?";
    private String texto1 = "Se perderá el progreso actual de la partida.";
    
    private String titulo2 = "Confirmación de salida";
    private String pricipal2 = "¿Estás seguro de ir al menú principal?";
    private String texto2 = "Se perderá el progreso actual de la partida.";
    
    private String btnSalir = "Salir";  
    private String btnCancelar = "Cancelar";
    
       @Override
    public void initialize(URL url, ResourceBundle rb) {
 
        SoundManager.stopFondo1Sound();
        SoundManager.playFondo1Sound2();
        SoundManager.cargarEfectosLose();
        SoundManager.cargarEfectosWin();
        SoundManager.initWalkSound();
        inicializarAudioClips();

        cargarDatosDesdeBD();
        cargarYVerificarUsuario();
        usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
        aplicarMejorasAlJugador();
        
        inicializarRecursosGraficos();
        inicializarModoYEstiloJuego();

        btnDamagePower.setFocusTraversable(false);
        btnFreezePower.setFocusTraversable(false);
        progressBar.setScaleX(-1); 
        
        configurarIconoProgreso();
        cargarVolumenesGuardados();
        actualizarBarraDeVida();
        configurarBindeoMonedas();

        gun.setFitWidth(0);
        gun.setFitHeight(0);

        if (this.damageGun <= 0) this.damageGun = 10;
        if (this.speedGun <= 0) this.speedGun = 1.5;

        currentGun = new Gun(damageGun, speedGun, imagenAUsarNormal, audioGun);
        gun.setImage(currentGun.getSprite());

        rootSpawner.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnMousePressed(event -> isShooting = true);
                newScene.setOnMouseReleased(event -> isShooting = false);
            }
        });

        inicializarTipoDeControl(); 
        configurarSeguimientoMouse(); 
        configurarEventosEscenaTeclado();

        inicializarContadoresEnemigos();
        iniciarElixirLoop();
        configurarBuclePrincipalJuego();
        
        loop.start();
        iniciarNivel();
    }

    private void inicializarAudioClips() {
        audioGun = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/hitBase.wav").toExternalForm());
        zombieAttack = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/atackZombie.wav").toExternalForm());
        zombieDeath = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/deathZombie.wav").toExternalForm());
        zombieHit = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/hitEnemy.wav").toExternalForm());
        fastDeath = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/deathFast.wav").toExternalForm());
        fastAttack = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/atackFast.wav").toExternalForm());    
        tankDeath = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/deathTank.wav").toExternalForm());
        tankAttack = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/atackZombie.wav").toExternalForm());    
        distanceDeath = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/deathFast.wav").toExternalForm());
        distanceAttack = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/atackDistance.wav").toExternalForm());    
        moveDeath = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/deathZombie.wav").toExternalForm());
        moveAttack = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/atackZombie.wav").toExternalForm()); 
        bombSound = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/Explotion.wav").toExternalForm());
        freezeSound = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/snowball.wav").toExternalForm());
        waveSound = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/round.wav").toExternalForm());
        finalWaveSound = new AudioClip(getClass().getResource("/cr/ac/una/proyecto/resources/Audios/finalRound.wav").toExternalForm());
    }

    private void inicializarRecursosGraficos() {
        imageGun = new Image(getClass().getResource("/cr/ac/una/proyecto/resources/gun4.png").toExternalForm());
        imageGun2 = new Image(getClass().getResource("/cr/ac/una/proyecto/resources/Gun2.png").toExternalForm());
        imageGunDisparando = new Image(getClass().getResource("/cr/ac/una/proyecto/resources/gun61.png").toExternalForm());
        imageGunDisparando2 = new Image(getClass().getResource("/cr/ac/una/proyecto/resources/gun21.png").toExternalForm());
        bulletGun = new Image(getClass().getResource("/cr/ac/una/proyecto/resources/bulletGun.png").toExternalForm());

        damageFueraImg = new Image(getClass().getResource("/cr/ac/una/proyecto/resources/nuclearDesactivado.png").toExternalForm());
        damageImg = new Image(getClass().getResource("/cr/ac/una/proyecto/resources/nuclear.png").toExternalForm());
        freezeFueraImg = new Image(getClass().getResource("/cr/ac/una/proyecto/resources/hieloDesactivado.png").toExternalForm());
        freezeImg = new Image(getClass().getResource("/cr/ac/una/proyecto/resources/hielo.png").toExternalForm());
        freezeCaer = new Image(getClass().getResource("/cr/ac/una/proyecto/resources/snow.png").toExternalForm());
        damageCaer = new Image(getClass().getResource("/cr/ac/una/proyecto/resources/bombCaer.png").toExternalForm());

        monsterNormalImg = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Walk9.png"));
        monsterFastImg = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Run.png"));
        monsterTankImg = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Walk4.png"));
        monsterDistanceImg = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Walk8.png"));
        monsterMoveImg = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Run7.png"));
        monsterNormalImg1 = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Attack.png"));
        monsterFastImg1 = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Attack_1.png"));
        monsterTankImg1 = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Attack4.png"));
        monsterMoveImg1 = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Attack_7.png"));
        monsterDistanceImg1 = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Shot_1.png"));
        monsterNormalImg3 = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Dead2.png"));
        monsterFastImg3 = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Dead1.png"));
        monsterTankImg3 = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Dead4.png"));
        monsterMoveImg3 = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Dead7.png"));
        monsterDistanceImg3 = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Dead8.png"));
        zombieFreeze = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/zombieF1.png"));
        fastFreeze = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/fastFreeze.png"));
        tankFreeze = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/tankFreeze.png"));
        distanceFreeze = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/distanceFreeze.png"));
        moveFreeze = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/moveFreeze.png"));
        monsterNormalHitImg = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/zombieHurt1.png"));
        monsterFastHitImg = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/fastHurt.png"));
        monsterTankHitImg = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/tankHurt1.png"));
        monsterDistanHitImg = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/distanceHurt1.png"));
        monsterMoveHitImg = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/MoveHurt1.png"));
    }

    private void inicializarModoYEstiloJuego() {
        String estiloArma = "A";
        this.modoJuegoActivo = "N";

        if (usuarioActivo != null && usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            var configGeneral = usuarioActivo.getUsersConfigList().get(0).getUsersConfigGeneral();
            if (configGeneral != null) {
                if (configGeneral.getUserStylegun() != null) estiloArma = configGeneral.getUserStylegun();
                if (configGeneral.getUserGamemode() != null) this.modoJuegoActivo = configGeneral.getUserGamemode();
            }
        }

        if (estiloArma.equals("M")) {
            imagenAUsarDisparo = imageGunDisparando2;
            imagenAUsarNormal = imageGun2;
        } else {
            imagenAUsarDisparo = imageGunDisparando;
            imagenAUsarNormal = imageGun;
        }

        if (this.modoJuegoActivo.equals("E")) {
        } else {
        }
    }

    private void inicializarContadoresEnemigos() {
        totalEnemigosNivel = 0;
        totalEnemigosNivel += MAX_WAVES * 3;
        for (int i = 1; i <= MAX_WAVES; i++) {
            totalEnemigosNivel += 5;
        }

        if (usuarioActivo != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();      
            elixirBase = (int) (150 + configNivel.getUserLevelCapacityelixir() * 50);
        }
    }

    private void configurarBindeoMonedas() {
        if (usuarioActivo != null && usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
            LblCoins.textProperty().unbind();
            LblCoins.textProperty().bind(configNivel.userPointsProperty().asString());
        }
    }

    private void configurarIconoProgreso() {
        imagenIcono = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/skull.png"));
        imagenIconoDeath = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/skullRed.png"));

        icono = new ImageView(imagenIcono);
        icono.setFitWidth(35);
        icono.setFitHeight(35);
        icono.setPreserveRatio(true);

        StackPane.setAlignment(progressBar, Pos.CENTER);
        StackPane.setAlignment(icono, Pos.TOP_LEFT);
        StackPane.setMargin(icono, new javafx.geometry.Insets(0, 0, 0, 0));
        rootProgressbar.getChildren().add(icono); 
         // bindea la imagen con la posicion de el progreso
        progressBar.progressProperty().addListener((obs, oldVal, newVal) -> {
            double progreso = Math.max(0, Math.min(1, newVal.doubleValue()));
            double desplazamientoX = progressBar.getWidth() - (progressBar.getWidth() * progreso) - 16;
            icono.setTranslateX(desplazamientoX);
        });

        progressBar.widthProperty().addListener((obs, oldVal, newVal) -> {
            double progreso = Math.max(0, Math.min(1, progressBar.getProgress()));
            double desplazamientoX = newVal.doubleValue() - (newVal.doubleValue() * progreso) - 16;
            icono.setTranslateX(desplazamientoX);
        });
    }

    private void configurarEventosEscenaTeclado() {
        rootSpawner.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene == null) return;
            
            rootSpawner.requestFocus();
            rootSpawner.setOnMouseClicked(e -> rootSpawner.requestFocus());

            newScene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case W:  keyUp = true; break;
                    case S:  keyDown = true; break;
                    case SPACE:         
                        keySpace = true; 
                        event.consume();
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
        });

        javafx.application.Platform.runLater(() -> rootSpawner.requestFocus());
    }

    private void configurarBuclePrincipalJuego() {
        loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double anguloActual = gun.getRotate();

                // Apuntado por Teclado o Mouse
                if (controlActivo.equals("K")) {
                    double VELOCIDAD_TECLADO = 0.5; 
                    if (keyUp) anguloActual -= VELOCIDAD_TECLADO;
                    if (keyDown) anguloActual += VELOCIDAD_TECLADO;
                } else {
                    double gunCenterX = gun.localToScene(gun.getLayoutBounds().getWidth() / 2, gun.getLayoutBounds().getHeight() / 2).getX();
                    double gunCenterY = gun.localToScene(gun.getLayoutBounds().getWidth() / 2, gun.getLayoutBounds().getHeight() / 2).getY();
                    double deltaX = mouseXActual - gunCenterX;
                    double deltaY = mouseYActual - gunCenterY;
                    
                    if (deltaX != 0 || deltaY != 0) {
                        anguloActual = Math.toDegrees(Math.atan2(deltaY, deltaX));
                        if (deltaX < 0) anguloActual = (deltaY < 0) ? -85 : 85; 
                    }
                }
            // para que apunte para atras
                anguloActual = Math.max(-85, Math.min(85, anguloActual));
                gun.setRotate(anguloActual);

                // Sistema de Disparos
              double disparoIntervaloNs = 1_000_000_000.0 / currentGun.getAttackSpeed();
                      boolean quiereDisparar = false;

                   if (controlActivo.equals("K")) {
                    quiereDisparar = keySpace;
                   } else {
                     quiereDisparar = isShooting;
                          }

                if (quiereDisparar) {
                 if (now - lastShotTime >= disparoIntervaloNs) {
                    disparar(); 
                    lastShotTime = now;
                 }
                }

                if (now - lastUpdate < 20_000_000) return;
                lastUpdate = now;

                double limiteGun = rootGun.getBoundsInParent().getMinX();
                Iterator<Enemy> it = enemy.iterator();
                boolean hayCaminando = false;
                long currentTime = System.currentTimeMillis();

                while (it.hasNext()) {
                    Enemy m = it.next();
                    m.update(limiteGun);

                    if (!m.isAtacando() && !m.isDying() && !m.isFroozen()) {
                        hayCaminando = true;
                    }

                    // Transacción de Ataques del Enemigo
                    if (m.isAtacando() && (currentTime - m.lastAttackTime >= 1000)) {
                        if (m.getDistanceAtack() > 0) {
                            Projectile p = m.shoot();
                            m.playAttackSound();
                            enemyProjectiles.add(p);
                            rootSpawner.getChildren().add(p.getSprite());
                        } else {
                            healthBase -= m.getDamageSecond();
                            actualizarBarraDeVida();
                            m.playAttackSound();
                        }
                        m.lastAttackTime = currentTime;
                    }

                    // Gestión de monedas
                    if (m.isDying() && !m.isRewardGiven()) {
                        if (usuarioActivo != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
                            var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
                            configNivel.userPointsProperty().set(configNivel.userPointsProperty().get() + 5);
                            
                            if (imageCoin != null) {
                                javafx.animation.TranslateTransition brinco = new javafx.animation.TranslateTransition(javafx.util.Duration.millis(150), imageCoin);
                                brinco.setByY(-15); 
                                brinco.setAutoReverse(true);
                                brinco.setCycleCount(2); 
                                brinco.play();
                            }
                        } else {
                        }
                        m.setRewardGiven(true);
                    }

                    // Elimina el enemigo
                    if (m.isDeadA()) {
                        rootSpawner.getChildren().remove(m.getView());
                        it.remove();
                    }
                }

                if (hayCaminando) SoundManager.playWalk();
                else SoundManager.stopWalk();

                // Colisiones de Proyectiles Enemigos
                Iterator<Projectile> pitEnemigos = enemyProjectiles.iterator();
                while (pitEnemigos.hasNext()) {
                    Projectile p = pitEnemigos.next();
                    p.update();
                    if (p.hitBase(limiteGun)) {
                        p.playHitSound();
                        healthBase -= p.getDamage();
                        actualizarBarraDeVida();
                        rootSpawner.getChildren().remove(p.getSprite());
                        pitEnemigos.remove();
                    }
                }

                //Colisiones de Proyectiles del Jugador
                Iterator<Projectile> pitJugador = playerProjectiles.iterator();
                while (pitJugador.hasNext()) {
                    Projectile p = pitJugador.next();
                    p.update();
                    boolean balaImpacto = false;
                    Bounds balaScene = p.getSprite().localToScene(p.getSprite().getBoundsInLocal());

                    for (Enemy e : enemy) {
                        if (e.isDying()) continue;

                        Bounds enemigoHitboxScene = e.getView().localToScene(e.getHitbox1());
                        if (balaScene.intersects(enemigoHitboxScene)) {
                            e.takeDamage(p.getDamage()); 
                            rootSpawner.getChildren().remove(p.getSprite());
                            pitJugador.remove(); 
                            balaImpacto = true;
                            break; 
                        }
                    }

                    if (balaImpacto) continue;

                    // Destrucción de balas fuera de los márgenes
                    if (p.getX() > rootSpawner.getWidth() + 100 || p.getX() < -400 || 
                        p.getY() > rootSpawner.getHeight() + 100 || p.getY() < -100) {
                        rootSpawner.getChildren().remove(p.getSprite());
                        pitJugador.remove(); 
                    }
                }

                // Condicion de victoria
                if (lblProgress.getText().equals("100%") && enemy.isEmpty() && healthBase > 0) {
                    gameOver = true;
                    loop.stop();
                    if (spawnLoop != null) spawnLoop.stop();
                    if (elixirLoop != null) elixirLoop.stop();

                    if (usuarioActivo != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
                        try {
                            UsersConfigLevelDto niveles = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
                            Long nivelActual = niveles.getUserLevel();
                            
                            if (nivelActual >= 100) { 
                                playWinAnimation(rootWin1, rootWinAnimation1);
                            } else { 
                                niveles.setUserLevel(nivelActual + 1);   
                                playWinAnimation(rootWin, rootWinAnimation);
                            }
                            Long puntosActuales = niveles.userPointsProperty().getValue(); 
                            niveles.setUserPoints(puntosActuales); 

                            UsersService usersService = new UsersService(); 
                            UsersDto usuarioGuardado = usersService.guardarUsuario(usuarioActivo);
                            if (usuarioGuardado != null) {
                                AppContext.getInstance().setUsuarioSeleccionado(usuarioGuardado);
                                usuarioActivo = usuarioGuardado; 
                            }
                        } catch (Exception e) {
                        }
                    }
                    SoundManager.stopWalk();
                    SoundManager.stopFondo1Sound2();
                    SoundManager.playWin();
                }

                // Coindicion de derrota
                if (!gameOver && healthBase <= 0) {
                    forceStopDrag();
                    healthBase = 0;
                    gameOver = true;
                    loop.stop();

                    if (spawnLoop != null) spawnLoop.stop();
                    if (elixirLoop != null) elixirLoop.stop();
                    
                    playLoseAnimation();
                    SoundManager.stopWalk();
                      SoundManager.stopFondo1Sound2();
                    SoundManager.playLose();
                }
            }
        };
    }
    
    
    
 private void playWinAnimation(VBox rootWin, VBox rootAnimate) {
        rootWin.setVisible(true);
 
        //Animacion de caida del rootVictoria
        rootAnimate.setTranslateY(-900);

        TranslateTransition fall = new TranslateTransition(Duration.seconds(0.7), rootAnimate);
        fall.setToY(0);

        TranslateTransition bounceUp = new TranslateTransition(Duration.seconds(0.2), rootAnimate);
        bounceUp.setToY(-40);

        TranslateTransition bounceDown = new TranslateTransition(Duration.seconds(0.15), rootAnimate);
        bounceDown.setToY(0);

        SequentialTransition seq = new SequentialTransition(fall, bounceUp, bounceDown);
        seq.play();
    }

    private void playLoseAnimation() {
        rootGameOver.setVisible(true);

        //Animacion de el root de derrota
        rootLoseAnimation.setTranslateY(800);

        TranslateTransition rise = new TranslateTransition(Duration.seconds(0.4), rootLoseAnimation);
        rise.setToY(0);
        rise.setInterpolator(Interpolator.LINEAR);
        
        rise.play();
    }
    
   // Creacion de enemmigos y valida el modo de revicion
    private void spawnMonsterNormal() {
        if (this.modoJuegoActivo.equals("E")) {
            crearMonster(1, damageEnemies[0], speedEnemies[0], -5, false, monsterNormalImg, monsterNormalImg1, monsterNormalImg3, zombieFreeze, monsterNormalHitImg, zombieAttack, zombieDeath, zombieHit);
        } else {
            crearMonster(getEnemyHealth(0), getEnemyDamage(0), getEnemySpeed(0), -5, false, monsterNormalImg, monsterNormalImg1, monsterNormalImg3, zombieFreeze, monsterNormalHitImg, zombieAttack, zombieDeath, zombieHit);
        }
    }
    
    private void spawnMonsterFast() {
        if (this.modoJuegoActivo.equals("E")) {
            crearMonster(1, damageEnemies[1], speedEnemies[1], 0, false, monsterFastImg, monsterFastImg1, monsterFastImg3, fastFreeze, monsterFastHitImg, fastAttack, fastDeath, zombieHit);
        } else {
            crearMonster(getEnemyHealth(1), getEnemyDamage(1), getEnemySpeed(1), 0, false, monsterFastImg, monsterFastImg1, monsterFastImg3, fastFreeze, monsterFastHitImg, fastAttack, fastDeath, zombieHit);
        }
    }

    private void spawnMonsterTank() {
        if (this.modoJuegoActivo.equals("E")) {
            crearMonster(1, damageEnemies[2], speedEnemies[2], -15, false, monsterTankImg, monsterTankImg1, monsterTankImg3, tankFreeze, monsterTankHitImg, tankAttack, tankDeath, zombieHit);
        } else {
            crearMonster(getEnemyHealth(2), getEnemyDamage(2), getEnemySpeed(2), -15, false, monsterTankImg, monsterTankImg1, monsterTankImg3, tankFreeze, monsterTankHitImg, tankAttack, tankDeath, zombieHit);
        }
    }

    private void spawnMonsterAereo() {
        if (this.modoJuegoActivo.equals("E")) {
            crearMonster(1, damageEnemies[3], speedEnemies[3], 700, false, monsterDistanceImg, monsterDistanceImg1, monsterDistanceImg3, distanceFreeze, monsterDistanHitImg, distanceAttack, distanceDeath, zombieHit);
        } else {
            crearMonster(getEnemyHealth(3), getEnemyDamage(3), getEnemySpeed(3), 700, false, monsterDistanceImg, monsterDistanceImg1, monsterDistanceImg3, distanceFreeze, monsterDistanHitImg, distanceAttack, distanceDeath, zombieHit);
        }
    }

    private void spawnMonsterMove() {
        if (this.modoJuegoActivo.equals("E")) {
            crearMonster(1, damageEnemies[4], speedEnemies[4], 0, true, monsterMoveImg, monsterMoveImg1, monsterMoveImg3, moveFreeze, monsterMoveHitImg, moveAttack, moveDeath, zombieHit);
        } else {
            crearMonster(getEnemyHealth(4), getEnemyDamage(4), getEnemySpeed(4), 0, true, monsterMoveImg, monsterMoveImg1, monsterMoveImg3, moveFreeze, monsterMoveHitImg, moveAttack, moveDeath, zombieHit);
        }
    }
    // Crea los enemgos de su Contructor y asigna la posicion de aparicion
    private void crearMonster(double health, double damageSecond, double speed, int distanceAttack, boolean movedY, 
                               Image img, Image img2, Image img3, Image img4, Image img5,
                               AudioClip attackSound, AudioClip deathSound, AudioClip hitSound) {
        
        double x = rootSpawner.getWidth() + 50;
        double y = Math.random() * (rootSpawner.getHeight() - 150);
        
        Enemy m = new Enemy(health, damageSecond, speed, distanceAttack, movedY, x, y, img, img2, img3, img4, img5, attackSound, deathSound, hitSound);
        
        enemy.add(m);
        rootSpawner.getChildren().add(m.getView());
    }
     // Para poder crearlos de manera aleatoria
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
            default:
                break;
        }
    }
    
    public void iniciarNivel() {
        iniciarSecuencia();
        wave = 1; 
        enemigosGeneradosTotal = 0;

        calcularTotalNivel(); 
        actualizarBarra();
        iniciarPreSpawn();
    }

    private void iniciarPreSpawn() {
        if (gameOver) return;

        enPreSpawn = true;
        enOleada = false;
        enemigosSpawneados = 0;
       // genera enemigos cada 4 segundos
        spawnLoop = new Timeline(
            new KeyFrame(
                Duration.seconds(4),
                e -> {
                    int tipo = getTipoEnemigo();
                    crearMonster1(tipo);
                    
                    enemigosGeneradosTotal++;
                    actualizarBarra();
                }
            )
        );
        // Genera 3 en total
        spawnLoop.setCycleCount(3);

        // al terminar el calentamiento, salta a la oleada principal
        spawnLoop.setOnFinished(e -> {
            if (gameOver) return;
            iniciarOleada(); 
        });
        spawnLoop.play();
    }

    private void iniciarOleada() {
        if (gameOver) return;

        enPreSpawn = false;
        enOleada = true;
        enemigosSpawneados = 0;
        enemigosPorSpawn = getEnemigosPorWave(); // cantidad según el nivel actual

        // gestión audios y efectos visuales de hordas
        if (wave >= getMaxWaves()) {
            finalWaveSound.play(SoundManager.getEnemyVolume());
            icono.setImage(imagenIconoDeath);
        } else {
            waveSound.play(SoundManager.getEnemyVolume());
            
            // efecto visual de parpadeo rápido
            icono.setImage(imagenIconoDeath);
            FadeTransition parpadeo = new FadeTransition(Duration.millis(200), icono);
            parpadeo.setFromValue(1.0);  
            parpadeo.setToValue(0.2);    
            parpadeo.setCycleCount(10);
            parpadeo.setAutoReverse(true); 
            
            parpadeo.setOnFinished(event -> {
                icono.setImage(imagenIcono); 
                icono.setOpacity(1.0);       
            });
            parpadeo.play();
        }

        // bucle principal de aparición de enemigos
        spawnLoop = new Timeline(
            new KeyFrame(
                Duration.seconds(getSpawnSpeed()),
                e -> {
                    int tipo = getTipoEnemigo();
                    crearMonster1(tipo);
                    enemigosSpawneados++;
                    enemigosGeneradosTotal++;

                    // Agrega enemigos adicionales
                    int extra = enemigosExtra();
                    for (int i = 0; i < extra; i++) {
                        crearMonster1(tipo);
                    }
                    actualizarBarra();
                }
            )
        );

        spawnLoop.setCycleCount(enemigosPorSpawn);
        spawnLoop.setOnFinished(e -> {
            if (gameOver) return;
            enOleada = false;
            siguienteFase();
        });
        spawnLoop.play();
    }

    private void siguienteFase() {
        if (wave < getMaxWaves()) {
            wave++; 

            // intervalo de enfriamiento de 1.5 segundos entre hordas
            Timeline espera = new Timeline(
                new KeyFrame(Duration.millis(1500), e -> iniciarPreSpawn())
            );
            espera.setCycleCount(1); 
            espera.play();
        } else {
        }
    }
private int getEnemigosPorWave() {
        return 5 + (levelBind / 20);
    }

    private int getMaxWaves() {
        int level = levelBind;
        if (level < 21) return 2;
        if (level < 41) return 3;
        if (level < 61) return 4;
        if (level < 81) return 5;
        return 6;
    }

    private double getSpawnSpeed() {
        int level = levelBind;
        if (level < 21) return 2.5;
        if (level < 41) return 2.0;
        if (level < 61) return 1.5;
        return 1.25; 
    }

    private int getTipoEnemigo() {
        int level = levelBind;
        if (level < 21) return 0; 
        if (level < 41) return (int)(Math.random() * 2);
        if (level < 61) return (int)(Math.random() * 3);
        if (level < 81) return (int)(Math.random() * 4); 
        return (int)(Math.random() * 5); 
    }

    private double getEnemyHealth(int tipo) {
        int level = levelBind;
        double base = healthEnemies[tipo];
        if (level < 21) return base;
        if (level < 41) return base * 1.25;
        if (level < 61) return base * 1.5;
        if (level < 81) return base * 1.75;
        return base * 2;
    }

    private double getEnemyDamage(int tipo) {
        int level = levelBind;
        double base = damageEnemies[tipo];
        if (level < 21) return base;
        if (level < 41) return base * 1.15;
        if (level < 61) return base * 1.25;
        if (level < 81) return base * 1.5;
        return base * 2;
    }

    private double getEnemySpeed(int tipo) {
        int level = levelBind;
        double base = speedEnemies[tipo];
        if (level < 25) return base;
        if (level < 50) return base + 1;
        if (level < 75) return base + 2;
        return base + 3;
    }

    private int enemigosExtra() {
        int level = levelBind;
        double chance;
        if (level < 21) chance = 0.15;
        else if (level < 41) chance = 0.20;
        else if (level < 61) chance = 0.25;
        else if (level < 81) chance = 0.30;
        else chance = 0.35;

        int extra = 0;
        if (Math.random() < chance) extra++;
        if (Math.random() < (chance / 2)) extra++;
        return extra;
    }

     private void calcularTotalNivel() {
        totalEnemigosNivel = 0;
        int cantidadWaves = getMaxWaves();
   
        for (int i = 0; i < cantidadWaves; i++) {
            // suma los monstruos  del pre-spawn
            totalEnemigosNivel += 3;
            // suma enemigos estandar de la oleada
            int baseOleada = getEnemigosPorWave();
            totalEnemigosNivel += baseOleada;
        }
    }

    private void actualizarBarra() {
        if (totalEnemigosNivel <= 0) return;
        double progreso = (double) enemigosGeneradosTotal / totalEnemigosNivel;

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

    // Resetea el estado del juego
    gameOver = false;
    enOleada = false;
    enPreSpawn = false;
    enemigosSpawneados = 0;
    enemigosGeneradosTotal = 0;
    wave = 0;

    // Limpieza de listas
    enemy.clear();
    enemyProjectiles.clear();
    rootSpawner.getChildren().clear();
    progressBar.setProgress(0);
    lblProgress.setText(String.valueOf(0));

    // datos actualizados de la base de datos
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

        // Elixir empieza al máximo 
        double elixirMaximo =  150 + configNivel.getUserLevelCapacityelixir() * 50;
        elixirBase = (int) elixirMaximo;
    }
    // Ocultar menu
    rootPause.setVisible(false);
    rootGameOver.setVisible(false);
    rootGameOver.setOpacity(1.0); 

    calcularTotalNivel();
    iniciarNivel();

    SoundManager.stopFondo1Sound2();
    SoundManager.playFondo1Sound2();

    reiniciarFocoYControles();
    loop.start();
    elixirLoop.play();
}

@FXML
private void onActionNextLevel(ActionEvent event) {
    rootNivel.setVisible(true);
    if (loop != null) loop.stop();
    if (spawnLoop != null) spawnLoop.stop();
    if (elixirLoop != null) elixirLoop.stop(); 

    // datos actualizados
    refrescarUsuarioDesdeBaseDatos();
    cargarYVerificarUsuario();

    // Reiniciar valores 
    gameOver = false;
    enOleada = false;
    enPreSpawn = false;
    enemigosSpawneados = 0;
    enemigosGeneradosTotal = 0;
    wave = 0;

    //  Actualizar nivel de vida y elixir con los datos nuevos de base de datos
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
    }

    // Limpieza
    enemy.clear();
    enemyProjectiles.clear(); 
    rootSpawner.getChildren().clear();
    progressBar.setProgress(0);
    lblProgress.setText(String.valueOf(0));
    rootWin.setVisible(false);

    // nuevo nivel e iniciar loops
    calcularTotalNivel();
    iniciarNivel();
    reiniciarFocoYControles();
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


@FXML
private void onActionUpgrates(ActionEvent event) {
    if( rootPause.isVisible()){
   
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(titulo1);
    alert.setHeaderText(pricipal1);
    alert.setContentText(texto1);

    ButtonType botonAceptar = new ButtonType(btnSalir);
    ButtonType botonCancelar = new ButtonType(btnCancelar);
    alert.getButtonTypes().setAll(botonAceptar, botonCancelar);
    DialogPane dialogPane = alert.getDialogPane();
    try {
        dialogPane.getStylesheets().add(
            getClass().getResource("/cr/ac/una/proyecto/view/Style.css").toExternalForm()
        );
        dialogPane.getStyleClass().addAll("fx-FondoJuego11","label-usuario");
    } catch (Exception e) {
    }

    Optional<ButtonType> result = alert.showAndWait();
     if (result.isEmpty() || result.get() == botonCancelar) {
        return; // no se ejecuta nada de lo de abajo
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
    alert.setTitle(titulo2);
    alert.setHeaderText(pricipal2);
    alert.setContentText(texto2);

    ButtonType botonAceptar = new ButtonType(btnSalir);
    ButtonType botonCancelar = new ButtonType(btnCancelar);
    alert.getButtonTypes().setAll(botonAceptar, botonCancelar);

    DialogPane dialogPane = alert.getDialogPane();
    try {
        dialogPane.getStylesheets().add(
            getClass().getResource("/cr/ac/una/proyecto/view/Style.css").toExternalForm()
        );
         dialogPane.getStyleClass().addAll("fx-FondoJuego11","label-usuario");
    } catch (Exception e) {
    }

    Optional<ButtonType> result = alert.showAndWait();
     if (result.isEmpty() || result.get() == botonCancelar) {
        return; //no se ejecuta nada de lo de abajo
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
            root.getChildren().remove(0);
        });

        slide.play();
      SoundManager.stopFondo1Sound2();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    //Botones de poderes
@FXML
    private void onMousePressedDamage(javafx.scene.input.MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            startDrag(event, "damage"); 
        }
    }

    @FXML
    private void onMousePressedFreeze(javafx.scene.input.MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            startDrag(event, "freeze");
        }
    }

    
    private void startDrag(javafx.scene.input.MouseEvent event, String skill) {
        if (usuarioActivo == null || usuarioActivo.getUsersConfigList() == null || usuarioActivo.getUsersConfigList().isEmpty()) {
            return;
        }
        var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
        if (configNivel == null) return;
            
        if (this.elixirBase > 100 && canUseSkill(skill)) {
            currentSkill = skill;
            dragging = true;
            areaSkill = new ImageView();

            double radius = 0;

            // cálculo de rangos 
            if (skill.equals("damage")) {
                long nivelRangoDano = configNivel.userLevelPowerfreezerangeProperty().get();
                radius = 50.0 + (nivelRangoDano * 8.0); 
            } else {
                long nivelRangoCongela = configNivel.userLevelPowerfreezerangeProperty().get();
                radius = 40.0 + (nivelRangoCongela * 8.0);
            }

            // el tamaño visual del imageview corresponde al diámetro (radio * 2)
            double size = radius * 2;
            areaSkill.setFitWidth(size);
            areaSkill.setFitHeight(size);
            areaSkill.setMouseTransparent(true);
            areaSkill.setOpacity(0.5);

            // asignación de textura según la habilidad seleccionada
            if (skill.equals("damage")) {
                areaSkill.setImage(damageImg);
            } else {
                areaSkill.setImage(freezeImg);
            }

            // renderizado en el contenedor
            rootSpawner.getChildren().add(areaSkill);
            rootSpawner.setCursor(Cursor.NONE);
            moveCircle(event);
        } else {
            SoundManager.playErrorSound();
            if (canUseSkill(skill)) {
                //NO tiene elixir
                Validations.showMessage(LblMessage, elixir, rootValidations);
            } else {
                // Se esta recargando
                Validations.showMessage(LblMessage, tiempo, rootValidations);
            }
        }
    }

    @FXML
    private void onMouseDraggedPane(javafx.scene.input.MouseEvent event) {
        if (!dragging) return;

        Point2D p = rootSpawner.sceneToLocal(event.getSceneX(), event.getSceneY());
        moveCircle(event);

        boolean dentro = p.getX() >= 0 && p.getX() <= rootSpawner.getWidth() && p.getY() >= 0 && p.getY() <= rootSpawner.getHeight();

        // cambia la textura visual si el cursor sale de los límites del mapa
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
    private void onMouseReleasedPane(javafx.scene.input.MouseEvent event) {
        if (!dragging) return;
        Point2D p = rootSpawner.sceneToLocal(event.getSceneX(), event.getSceneY());

        if (event.getButton() == MouseButton.PRIMARY) {
            applySkill(event.getSceneX(), event.getSceneY());

            boolean dentro = p.getX() >= 0 && p.getX() <= rootSpawner.getWidth() && p.getY() >= 0 && p.getY() <= rootSpawner.getHeight();

            if (dentro) {
                this.elixirBase -= 100;
                // Obtiene cuando se exactamente se lanza para des pues comparar con el siguiente
                long now = System.currentTimeMillis();

                if (currentSkill.equals("damage")) {
                    iniciarAnimacionCooldown();
                    lanzarPoderDesdeElCielo(p.getX(), p.getY(), "damage");
                    lastDamageSkill = now;
                    bombSound.play(SoundManager.getEfectVolume());
                } else if (currentSkill.equals("freeze")) {
                    iniciarAnimacionFreezeCooldown();
                    lanzarPoderDesdeElCielo(p.getX(), p.getY(), "freeze");
                    lastFreezeSkill = now;
                    freezeSound.play(SoundManager.getEfectVolume());
                }
            }
        }
        rootSpawner.getChildren().remove(areaSkill);
        dragging = false;
        rootSpawner.setCursor(Cursor.DEFAULT);
    }

    private void moveCircle(javafx.scene.input.MouseEvent event) {
        if (usuarioActivo == null || usuarioActivo.getUsersConfigList() == null || usuarioActivo.getUsersConfigList().isEmpty()) {
            return;
        }
        var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
        if (configNivel == null) return;

        Point2D p = rootSpawner.sceneToLocal(event.getSceneX(), event.getSceneY());
        double radius = 0;

        if (currentSkill.equals("damage")) {
            long nivelRangoDano = configNivel.userLevelPowerfreezerangeProperty().get();
            radius = 50.0 + (nivelRangoDano * 8.0); 
        } else {
            long nivelRangoCongela = configNivel.userLevelPowerfreezerangeProperty().get();
            radius = 40.0 + (nivelRangoCongela * 8.0);
        }

        areaSkill.setLayoutX(p.getX() - radius);
        areaSkill.setLayoutY(p.getY() - radius);
    }

    public void forceStopDrag() {
        this.dragging = false;
        this.currentSkill = null;

        if (this.areaSkill != null && rootSpawner != null) {
            rootSpawner.getChildren().remove(this.areaSkill);
            this.areaSkill = null;
        }

        if (rootSpawner != null) {
            rootSpawner.setCursor(javafx.scene.Cursor.DEFAULT);
        }
    }
    
    
    private boolean canUseSkill(String skill) {
        if (skill.equals("damage")) {
            return visualDamageReady; 
        }
        if (skill.equals("freeze")) {
            return visualFreezeReady; 
        }
        return true;
    }

    private void applySkill(double xScene, double yScene) {
        if (usuarioActivo == null || usuarioActivo.getUsersConfigList() == null || usuarioActivo.getUsersConfigList().isEmpty()) {
            return;
        }
        
        var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
        if (configNivel == null) return;

        double radius = 0;

        if (currentSkill.equals("damage")) {
            long nivelRangoDano = configNivel.userLevelPowerfreezerangeProperty().get();
            radius = 50.0 + (nivelRangoDano * 8.0); 
        } else if (currentSkill.equals("freeze")) {
            long nivelRangoCongela = configNivel.userLevelPowerfreezerangeProperty().get();
            radius = 40.0 + (nivelRangoCongela * 8.0);
        }

        // procesamiento de área circular sobre las hitboxes de los enemigos
        for (Enemy m : enemy) {
            Bounds b = m.getHitbox();
            Bounds sceneBounds = m.getView().localToScene(b);

            double closestX = clamp(xScene, sceneBounds.getMinX(), sceneBounds.getMaxX());
            double closestY = clamp(yScene, sceneBounds.getMinY(), sceneBounds.getMaxY());
            double dist = Math.hypot(xScene - closestX, yScene - closestY);

            if (dist <= radius) {
                if (currentSkill.equals("damage")) {
                    long nivelDanoBomba = configNivel.userLevelPowerbombdamageProperty().get();
                    double danoRealBomba = 30.0 + (nivelDanoBomba * 20.0);
                    m.takeDamage(danoRealBomba);
                }

                if (currentSkill.equals("freeze")) {
                    long nivelTiempoFreeze = configNivel.userLevelPowerfreezedurationProperty().get();
                    double segundosCongelado = 1.0 + (nivelTiempoFreeze * 1.0); 
                    m.setFrozen(true, segundosCongelado);
                }
            }
        }
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
    
    
    private void iniciarAnimacionCooldown() {
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
        timeline.setOnFinished(event -> {
            stackDamagePower.getChildren().remove(cooldownArc);
            visualDamageReady = true; 
        });

        timeline.play();
    }

    private void iniciarAnimacionFreezeCooldown() {
        visualFreezeReady = false; 

        Arc cooldownArc = new Arc();
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
        cooldownArc.setFill(Color.rgb(10, 40, 80, 0.6)); 
        cooldownArc.setManaged(false);
        cooldownArc.setMouseTransparent(true);

        stackFreezePower.getChildren().add(cooldownArc);

        Timeline timeline = new Timeline();
        KeyFrame kf = new KeyFrame(
            Duration.millis(FREEZE_COOLDOWN),
            new KeyValue(cooldownArc.lengthProperty(), 0)
        );
        
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event -> {
            stackFreezePower.getChildren().remove(cooldownArc);
            visualFreezeReady = true; 
        });

        timeline.play();
    }
    
    private void lanzarPoderDesdeElCielo(double targetX, double targetY, String tipoSkill) {
        ImageView proyectilCae = new ImageView();
        proyectilCae.setFitWidth(80);  
        proyectilCae.setFitHeight(80);
        
        if (tipoSkill.equals("damage")) {
            proyectilCae.setImage(damageCaer); 
        } else {
            proyectilCae.setImage(freezeCaer); 
        }

        // punto de spawn del proyectil por encima
        proyectilCae.setLayoutX(targetX - (proyectilCae.getFitWidth() / 2));
        proyectilCae.setLayoutY(-100); 

        rootSpawner.getChildren().add(proyectilCae);

        TranslateTransition caida = new TranslateTransition(Duration.millis(200), proyectilCae);
        caida.setFromY(0);
        caida.setToY(targetY + 90); 
        caida.setInterpolator(javafx.animation.Interpolator.EASE_IN);

        caida.setOnFinished(event -> {
            rootSpawner.getChildren().remove(proyectilCae);
            if (tipoSkill.equals("damage")) {
                playExplosion(targetX, targetY);
            } else {
                playFreeze(targetX, targetY);
            }
        });

        caida.play();
    }

    private void playExplosion(double x, double y) {
        Image explosionImg = new Image(getClass().getResource("/cr/ac/una/proyecto/resources/explotion.png").toExternalForm());
        ImageView explosion = new ImageView(explosionImg);

        int frameWidth = 550;
        int frameHeight = 505;
        int totalFrames = 9;

        explosion.setViewport(new Rectangle2D(0, 0, frameWidth, frameHeight));
        explosion.setFitWidth(250);
        explosion.setFitHeight(250);
        explosion.setLayoutX(x - 120);
        explosion.setLayoutY(y - 130);

        rootSpawner.getChildren().add(explosion);
 // Animacion de explocion
        Timeline timeline = new Timeline();
        for (int i = 0; i < totalFrames; i++) {
            final int frame = i;
            timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(i * 100), e -> {
                    explosion.setViewport(new Rectangle2D(frame * frameWidth, 0, frameWidth, frameHeight));
                })
            );
        }
        timeline.setOnFinished(e -> rootSpawner.getChildren().remove(explosion));
        timeline.play();
    }

    private void playFreeze(double x, double y) {
        Image freezeImg = new Image(getClass().getResource("/cr/ac/una/proyecto/resources/freeze.png").toExternalForm());
        ImageView freeze = new ImageView(freezeImg);

        int frameWidth = 640;
        int frameHeight = 640;
        int totalFrames = 10;

        freeze.setViewport(new Rectangle2D(0, 0, frameWidth, frameHeight));
        freeze.setFitWidth(250);
        freeze.setFitHeight(250);
        freeze.setLayoutX(x - 120);
        freeze.setLayoutY(y - 120);

        rootSpawner.getChildren().add(freeze);

        Timeline timeline = new Timeline();
        for (int i = 0; i < totalFrames; i++) {
            final int frame = i;
            timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(i * 100), e -> {
                    freeze.setViewport(new Rectangle2D(frame * frameWidth, 0, frameWidth, frameHeight));
                })
            );
        }

        timeline.setOnFinished(e -> rootSpawner.getChildren().remove(freeze));
        timeline.play();
    }

    public void addProjectile(Projectile p) {
        enemyProjectiles.add(p);
        rootSpawner.getChildren().add(p.getSprite());
    }

private void configurarSeguimientoMouse() {
        if (this.controlActivo.equals("K")) {
            rootSpawner.setOnMouseMoved(null);
            rootSpawner.setOnMouseDragged(null);
            rootGun.setOnMouseMoved(null);
            rootGun.setOnMouseDragged(null);
            return; 
        }

        javafx.event.EventHandler<javafx.scene.input.MouseEvent> registrarPosicionMouse = event -> {
            mouseXActual = event.getSceneX();
            mouseYActual = event.getSceneY();
            mouseEnMovimiento = true;
        };
        rootSpawner.setOnMouseMoved(registrarPosicionMouse);
        rootSpawner.setOnMouseDragged(registrarPosicionMouse);
        rootGun.setOnMouseMoved(registrarPosicionMouse);
        rootGun.setOnMouseDragged(registrarPosicionMouse);
   
    }

    public void inicializarTipoDeControl() {
        usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
        if (usuarioActivo != null && usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            var configGeneral = usuarioActivo.getUsersConfigList().get(0).getUsersConfigGeneral();
            if (configGeneral != null && configGeneral.getUserController() != null) {
                this.controlActivo = configGeneral.getUserController(); 
            }
        }
    }

    private void reiniciarFocoYControles() {
        btnDamagePower.setFocusTraversable(false);
        btnFreezePower.setFocusTraversable(false);
        
        rootSpawner.requestFocus();
        
        rootGun.setTranslateY(0);
        rootGun.setTranslateX(0);
        rootSpawner.setOnMouseClicked(e -> rootSpawner.requestFocus());
        
        configurarSeguimientoMouse();
    }   
    
    private void disparar() {
        currentGun.playSound();

        // cálculo y renderizado visual del efecto de retroceso (recoil) de la estructura
        if (imagenAUsarDisparo != null) {
            gun.setImage(imagenAUsarDisparo);

            double anguloGradosRecoil = gun.getRotate();
            double anguloRadianesRecoil = Math.toRadians(anguloGradosRecoil);
            double fuerzaRetroceso = -15.0; 
            
            double recoilX = Math.cos(anguloRadianesRecoil) * fuerzaRetroceso;
            double recoilY = Math.sin(anguloRadianesRecoil) * fuerzaRetroceso;
            
            gun.setTranslateX(recoilX);
            gun.setTranslateY(recoilY);

            // temporizador para normalizar la posición y el sprite
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

        // desplazamiento para fijar el spawn en la punta exterior del arma
        double LARGO_CANON = 20.0; 
        double ALTO_BOCA = -10.0;  
        double offsetPuntaX = (LARGO_CANON * Math.cos(anguloRadianes)) + (ALTO_BOCA * Math.sin(anguloRadianes));
        double offsetPuntaY = (LARGO_CANON * Math.sin(anguloRadianes)) - (ALTO_BOCA * Math.cos(anguloRadianes));
        double puntaCanonSceneX = gunCenterScene.getX() + offsetPuntaX;
        double puntaCanonSceneY = gunCenterScene.getY() + offsetPuntaY;

        Point2D spawnPointFinal = rootSpawner.sceneToLocal(puntaCanonSceneX, puntaCanonSceneY);

        double spawnX = spawnPointFinal.getX() - 7.5;
        double spawnY = spawnPointFinal.getY() - 7.5;

        double dx = Math.cos(anguloRadianes);
        double dy = Math.sin(anguloRadianes);
        //  Crea el poryectil
        Projectile nuevoProyectil = new Projectile(
                spawnX, spawnY, 
                dx, dy, 
                40.5, 
                (int) currentGun.getDamage(), 
                bulletGun, 
                currentGun.getShootSound(),
                15 
        );
        // orienta la bala según el ángulo de tiro
        if (nuevoProyectil.getSprite() != null) {
            nuevoProyectil.getSprite().setRotate(anguloGrados);
        }
        playerProjectiles.add(nuevoProyectil); 
        rootSpawner.getChildren().add(nuevoProyectil.getSprite());
    }
    
    private void cargarDatosDesdeBD() {
        try {
            usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
            if (usuarioActivo != null && usuarioActivo.getUserId() != null) {
                UsersService usersService = new UsersService();
                
                UsersDto usuarioFresco = usersService.obtenerUsuario(usuarioActivo.getUserId());
                
                if (usuarioFresco != null && usuarioFresco.getUsersConfigList() != null && !usuarioFresco.getUsersConfigList().isEmpty()) {
                    
                    AppContext.getInstance().setUsuarioSeleccionado(usuarioFresco);
                    this.usuarioActivo = usuarioFresco;
                    
                    var configGeneral = usuarioFresco.getUsersConfigList().get(0).getUsersConfigGeneral();
                    if (configGeneral != null) {
                        String idioma = configGeneral.getUserLanguage(); 
                        if (idioma != null) {
                            aplicarIdiomaEnPantalla(idioma);
                        }
                    }
                }
            } else {
            }
        } catch (Exception e) {       
            e.printStackTrace();
        }
    }

    private void refrescarUsuarioDesdeBaseDatos() {
        if (usuarioActivo != null && usuarioActivo.getUserId() != null) {
            try {
                UsersService usersService = new UsersService();
                UsersDto usuarioLimpio = usersService.obtenerUsuario(usuarioActivo.getUserId());
                
                if (usuarioLimpio != null) {
                    AppContext.getInstance().setUsuarioSeleccionado(usuarioLimpio);
                    usuarioActivo = usuarioLimpio;
                }
            } catch (Exception e) {
            }
        }
    }

    private void cargarYVerificarUsuario() {
        usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();

        if (usuarioActivo != null) {
            if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
                levelBind = (int) usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel().userLevelProperty().get();
            } else {
                levelBind = 1; 
            }
            
            bindearDatosUsuario();
        } else {
        }
    }
    private void bindearDatosUsuario() {
        try {
            if (usuarioActivo == null) return;

            if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
                UsersConfigDto intermedia = usuarioActivo.getUsersConfigList().get(0);
                UsersConfigLevelDto niveles = intermedia.getUsersConfigLevel();
                
                if (niveles != null) {
                    LblLevel.textProperty().unbind();
                    LblLevel1.textProperty().unbind();
                    LblCoins.textProperty().unbind(); 
                    LblLevel.textProperty().bind(niveles.userLevelProperty().asString());
                    LblLevel1.textProperty().bind(niveles.userLevelProperty().asString());
                    LblCoins.textProperty().bind(niveles.userPointsProperty().asString());  
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void aplicarMejorasAlJugador() {
        try {
            usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();

            if (usuarioActivo != null && usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
                var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();

                if (configNivel != null) {
                    long nivelDanoArma = configNivel.userLevelGundamageProperty().get(); 
                    long nivelVidaBase = configNivel.userLevelBaseheathProperty().get();
                    long nivelSpeedArma = configNivel.getUserLevelGunspeed();

                    // escalado algorítmico estadístico basado en los niveles de compra
                    this.damageGun = 25 + (int)((nivelDanoArma - 1) * 4); 
                    this.speedGun = 1 + (int)((nivelSpeedArma - 1) * 0.2); 
                    this.healthBase = (int) (nivelVidaBase * 100);
                }
            } else {
                this.damageGun = 10; 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void iniciarElixirLoop() {
        if (usuarioActivo == null || usuarioActivo.getUsersConfigList() == null || usuarioActivo.getUsersConfigList().isEmpty()) {
            return;
        }
        var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
        if (configNivel == null) return;

        // actualización de elixir cada 500 milisegundos
        elixirLoop = new Timeline(
            new KeyFrame(Duration.millis(500), e -> {
                long nivelVelocidadElixir = configNivel.userLevelSpeedelixirProperty().get();
                int regenReal = (int)(nivelVelocidadElixir);

                long nivelCapacidadElixir = configNivel.userLevelCapacityelixirProperty().get(); 
                int maxElixirReal = 150 + ((int) nivelCapacidadElixir * 50);

                int nuevoElixir = this.elixirBase + regenReal;

                if (nuevoElixir > maxElixirReal) {
                    nuevoElixir = maxElixirReal;
                }
                this.elixirBase = nuevoElixir;
                LblElixir.setText(this.elixirBase + " / " + maxElixirReal);
                progressElixir.setProgress((double) this.elixirBase / maxElixirReal);
                
                // advierte visualmente si los recursos actuales impiden arrojar habilidades
                boolean bajoRecurso = this.elixirBase < 100;
                lblElixirBomb.setVisible(bajoRecurso);
                lblElixirFreeze.setVisible(bajoRecurso);
            })
        );
        elixirLoop.setCycleCount(Timeline.INDEFINITE);
        elixirLoop.play();
    }

    private void actualizarBarraDeVida() {
        if (usuarioActivo != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
            
            long vidaMaxima = configNivel.userLevelBaseheathProperty().get() * 100;
            double progreso = (double) this.healthBase / vidaMaxima;
            
            if (progreso > 1.0) progreso = 1.0;
            if (progreso < 0.0) progreso = 0.0;
            
            progressHealth.setProgress(progreso);
            LblHP.setText(String.valueOf(this.healthBase));
        }
    }
    // Muestra la animacion de inicio 
    public void iniciarSecuencia() {
        PauseTransition pausa = new PauseTransition(Duration.millis(1250));
       
        pausa.setOnFinished(event -> {
            double anchoAnimation = rootNIvelAnimation.getBoundsInParent().getWidth();
            double posX_Actual = rootNIvelAnimation.getLayoutX() + rootNIvelAnimation.getTranslateX();
            double destinoIzquierda = -(posX_Actual + anchoAnimation);
            
            TranslateTransition animacion = new TranslateTransition(Duration.millis(1500), rootNIvelAnimation);
            animacion.setToX(destinoIzquierda); 
            
            animacion.setOnFinished(animEvent -> {
                rootNivel.setVisible(false); 
                rootNIvelAnimation.setTranslateX(0);
            });
            
            animacion.play();
        });
        pausa.play();
    }
public void cargarVolumenesGuardados() {
        usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
        if (usuarioActivo != null && usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            var configGeneral = usuarioActivo.getUsersConfigList().get(0).getUsersConfigGeneral();
            
            if (configGeneral != null) {
                // inyección y sincronización de preferencias de audio nativas desde oracle hacia el soundmanager
                SoundManager.setmenuVolume(configGeneral.getUserAudiomenu());
                SoundManager.getEnemyVolumeProperty().set(configGeneral.getUserAudioenemys());
                SoundManager.getEfectVolumeProperty().set(configGeneral.getUserAudioefect());
                SoundManager.getGunVolumeProperty().set(configGeneral.getUserAudiogun());
            }
        }
    }
    private void aplicarIdiomaEnPantalla(String idioma) {
        if ("I".equalsIgnoreCase(idioma)) {
            lblNivel.setText("Level: ");
            lblMonedas.setText("Coins: ");
            btnPausa.setText("Pause");
            lblElixirBomb.setText("Low Elixir");
            lblElixirFreeze.setText("Low Elixir");
        
            lblPerder.setText("You Lose");
            btnPerderR.setText("Reset");
            btnPerderM.setText("Upgrades");
            btnPerderH.setText("Main Menu");
            
            lblGanaste.setText("You Win");
            btnGanasteH.setText("Main Menu");
            btnGanasteM.setText("Upgrades");
            btnGanasteS.setText("Next Level");
            
            lblPausa.setText("Pause");
            btnPausaG.setText("Resume");
            btnPausaH.setText("Main Menu");
            btnPausaM.setText("Upgrades");
            btnPausaR.setText("Reset");
            
            elixir = "Insufficient Elixir";
            tiempo = "Recharging Power";
            lblRootNivel.setText("Level: ");
            
            titulo1 = "Departure confirmation";
            pricipal1 = "Are you sure you want to go to Upgrades?";
            texto1 = "The current game progress will be lost.";
            titulo2 = "Departure confirmation";
            pricipal2 = "Are you sure you want to go to the Main Menu?";   
            texto2 = "The current game progress will be lost.";
            btnSalir = "Leave";
            btnCancelar = "Cancel";
        }
    }
}