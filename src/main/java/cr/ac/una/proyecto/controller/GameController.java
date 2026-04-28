package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.App;
import cr.ac.una.proyecto.model.GameState;
import cr.ac.una.proyecto.util.Enemy;
import cr.ac.una.proyecto.util.Upgrades;
import java.io.IOException;
import static java.lang.invoke.MethodHandles.loop;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


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
        
    private Image monsterNormalImg;
    private Image monsterFastImg;
    private Image monsterTankImg;
    private Image monsterFlyImg;
    private final Upgrades upgrades = new Upgrades();
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
GameState state = GameState.getInstance();
    @FXML
    private Label LblElixir;
    @FXML
    private ProgressBar progressHealth;
    @FXML
    private ProgressBar progressElixir;
    long lastTime = System.currentTimeMillis();
    
          @Override
           public void initialize(URL url, ResourceBundle rb) {
           LblLevel.setText(String.valueOf(level));
progressHealth.progressProperty().bind(
    state.healthProperty().multiply(1.0).divide(
        state.baseHealthProperty())
    
);

progressElixir.progressProperty().bind(
    state.elixirProperty().divide(state.maxElixirProperty())
);
progressElixir.progressProperty().bind(
    state.elixirProperty().multiply(1.0).divide(
        state.maxElixirProperty())
    
);
LblElixir.textProperty().bind(
    state.elixirProperty().asString("%.0f")
        .concat(" / ")
        .concat(state.maxElixirProperty().asString("%.0f"))
);


         LblHP.textProperty().bind(state.healthProperty().asString());
         LblCoins.textProperty().bind(state.coinsProperty().asString());
           progressBar.setScaleX(-1);
            monsterNormalImg = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Mounstro.png"));
            monsterFastImg   = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Robot.png"));
            monsterTankImg   = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/soldado.png"));
            monsterFlyImg   = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/Volador.png"));

            totalEnemigosNivel = 0;


totalEnemigosNivel += MAX_WAVES * 3;

// oleadas
for (int i = 1; i <= MAX_WAVES; i++) {
    totalEnemigosNivel += 5;
}
            




  elixirLoop = new Timeline(
        new KeyFrame(Duration.millis(500), e -> {

            double nuevo = state.getElixir() + state.getElixirRegen();

            if (nuevo > state.getMaxElixir()) {
                nuevo = state.getMaxElixir();
            }

            state.setElixir(nuevo);
        })
    );

    elixirLoop.setCycleCount(Timeline.INDEFINITE);
    elixirLoop.play();


            // Incio del loop
    loop = new AnimationTimer() {
    @Override
    public void handle(long now) {

        double limiteGun = rootGun.getBoundsInParent().getMinX();

        Iterator<Enemy> it = enemy.iterator();

        while (it.hasNext()) {
            Enemy m = it.next();

            m.update(limiteGun);

            // ATAQUE
            if (m.isAtacando()) {

                long currentTime = System.currentTimeMillis();

                if (currentTime - m.lastAttackTime >= 1000) {

                   state.setHealth(state.getHealth() - m.getDamageSecond());
                    m.lastAttackTime = currentTime;

                       state.setHealth(state.getHealth() - m.getDamageSecond());
                }
            }

            // MUERTE
            if (m.getHealth() <= 0) {

              state.setCoins(state.getCoins() + 10);

                rootSpawner.getChildren().remove(m.getSprite());
                it.remove(); // 🔥 eliminar correctamente
            }
    
            }
   
        
        // GAME WIN
        if (!gameOver && wave == MAX_WAVES && !enOleada && !enPreSpawn && enemy.isEmpty() && state.getHealth() > 0) {

    gameOver = true; 

    loop.stop();
    if (spawnLoop != null) spawnLoop.stop();

    System.out.println("WIN");

    rootWin.setVisible(true);
}

        // GAME OVER
        if (!gameOver && state.getHealth() <= 0) {

            gameOver = true;

            loop.stop();
            if (spawnLoop != null) spawnLoop.stop();

            rootGameOver.setVisible(true);
        }
    }  
};

loop.start();

    iniciarPreSpawn();
}

@FXML
private void onActionSpaw(ActionEvent event) {

    Iterator<Enemy> it = enemy.iterator();

    while (it.hasNext()) {
        Enemy m = it.next();

       state.setCoins(state.getCoins() + 10); 

        rootSpawner.getChildren().remove(m.getSprite());
        it.remove();
    }
}

    
 private void spawnMonsterNormal() {
    crearMonster(100,5,0, 2, monsterNormalImg);
}

private void spawnMonsterFast() {
    crearMonster(50,2, 0,4, monsterFastImg);
}

private void spawnMonsterTank() {
    crearMonster(200,10, 0,1, monsterTankImg);
}
private void spawnMonsterAereo() {
    crearMonster(50,8, 700,2, monsterFlyImg);
}
    
    private void crearMonster(int health,int damageSecond, int distanceAttack, double speed, Image img) {
    double x = rootSpawner.getWidth() + 50;
    double y = Math.random() * (rootSpawner.getHeight() - 60);
    Enemy m = new Enemy(health,damageSecond, speed,distanceAttack, x, y, img);
    enemy.add(m);
    rootSpawner.getChildren().add(m.getSprite());
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
}
    }

    @FXML
    private void onActionReset(ActionEvent event) {

    if (loop != null) loop.stop();
    if (spawnLoop != null) spawnLoop.stop();

    enemy.clear();
    rootSpawner.getChildren().clear();
    enemigosGeneradosTotal = 0;
    progressBar.setProgress(0);
    wave = 1;
    enOleada = false;
    enPreSpawn = false;
    enemigosSpawneados = 0;
    coins=0;
   LblCoins.textProperty().bind(state.coinsProperty().asString());
    gameOver = false;
     state.setHealth(state.getMaxHealth());
   // state.setHealth(2000);
      // LblHP.textProperty().bind(state.baseHealthProperty().asString());
    lblProgress.setText(String.valueOf(0));
    rootGameOver.setVisible(false);
    rootPause.setVisible(false);
    loop.start();
    iniciarPreSpawn();
    }
    
    
    
    
    
    
private void iniciarOleada() {
    enPreSpawn = false;
    enOleada = true;

    enemigosSpawneados = 0;
    enemigosPorSpawn = 5;

  //  progressBar.setProgress(0);

    spawnLoop = new Timeline(
        new KeyFrame(Duration.seconds(1), e -> {

            if (enemigosSpawneados < enemigosPorSpawn) {

              
                int tipo = (int)(Math.random() * 4);

             crearMonster1(tipo);

              enemigosSpawneados++;          
            enemigosGeneradosTotal++;

actualizarBarra();

            } else {

                spawnLoop.stop();
                enOleada = false;
                siguienteFase();

            }

        })
    );

    spawnLoop.setCycleCount(Timeline.INDEFINITE);
    spawnLoop.play();
}

private void esperarSiguienteOleada() {
           wave++;
            iniciarOleada();
}

private void iniciarPreSpawn() {

    enPreSpawn = true;
    enOleada = false;

    spawnLoop = new Timeline(
        new KeyFrame(Duration.seconds(2), e -> {

            int tipo = (int)(Math.random() * 4);
            crearMonster1(tipo);


              enemigosSpawneados++;          
            enemigosGeneradosTotal++;

actualizarBarra();

        })
    );
    spawnLoop.setCycleCount(3);

    spawnLoop.setOnFinished(e -> {
        iniciarOleada();
    });

    spawnLoop.play();
}

private void actualizarBarra() {

    double progreso = (double) enemigosGeneradosTotal / totalEnemigosNivel;

        progressBar.setProgress(progreso);
        lblProgress.setText((int)(progreso * 100) + "%");
}

private void siguienteFase() {
    if (wave < MAX_WAVES) {
        wave++;
        Timeline espera = new Timeline(
            new KeyFrame(Duration.millis(500), e -> iniciarPreSpawn())
        );
        espera.setCycleCount(1);
        espera.play();
    }
}

    @FXML
private void onActionNextLevel(ActionEvent event) {
    level++;
    LblLevel.setText(String.valueOf(level));

    if (loop != null) loop.stop();
    if (spawnLoop != null) spawnLoop.stop();

    enemy.clear();
    rootSpawner.getChildren().clear();

    wave = 1;
    enOleada = false;
    enPreSpawn = false;
    enemigosSpawneados = 0;
    enemigosGeneradosTotal = 0;
    progressBar.setProgress(0);
    gameOver = false;

    state.setHealth(2000);
       LblHP.textProperty().bind(state.healthProperty().asString());

    rootWin.setVisible(false);

    loop.start();
    iniciarPreSpawn();
}

    @FXML
    private void onActionPause(ActionEvent event) {
         if (loop != null) loop.stop();
        if (spawnLoop != null) spawnLoop.pause();
        if (elixirLoop != null) elixirLoop.pause();
        
        rootPause.setVisible(true);
        
    }

    @FXML
    private void onActionOut(ActionEvent event) {
          rootPause.setVisible(false);
          loop.start();
          spawnLoop.play();
          elixirLoop.play();
    }

    private void addCoins(int amount) {
    coins += amount;
    LblCoins.setText(String.valueOf(coins));
      upgrades.setCoins(coins);
}

@FXML
private void onActionUpgrates(ActionEvent event) {

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
    System.out.println(state.getHealth());
} catch (IOException e) {
    e.printStackTrace();
}
}

    @FXML
    private void onActionHome(ActionEvent event) {
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

    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}

