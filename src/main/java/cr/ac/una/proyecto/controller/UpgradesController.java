package cr.ac.una.proyecto.controller;
import cr.ac.una.proyecto.App;
import cr.ac.una.proyecto.model.UsersConfigLevelDto;
import cr.ac.una.proyecto.model.UsersDto;
import cr.ac.una.proyecto.service.UsersService;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.SoundManager;
import cr.ac.una.proyecto.util.Validations;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.beans.property.LongProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class UpgradesController implements Initializable {
    @FXML
    private VBox rootValidations;
    @FXML
    private Tab tabBase;
    @FXML
    private Tab tabPoder;
    @FXML
    private Tab tabArma;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label LblPoints;
    @FXML
    private Label lblPointsD;
    @FXML
    private Label LblMessage;

    @FXML
    private Button btnHome;
    @FXML
    private Button btnJugar;

    // Vida
    @FXML
    private Label lblVida;
    @FXML
    private Label lblVidaNivel;
    @FXML
    private Label LblLevelHealth;
    @FXML
    private Label LblNumberHealth;
    @FXML
    private Label lblVidaActual;
    @FXML
    private Label LblActualHealth;
    @FXML
    private Label lblVidaAumenta;
    @FXML
    private Label lblVidaCost;
    @FXML
    private Label LblHealthCost;
    @FXML
    private ProgressBar progressHealth;
    @FXML
    private Button btnHealth;

    // Capacidad de Elixir
    @FXML
    private Label lblCapacidad;
    @FXML
    private Label lblCapacidadNivel;
    @FXML
    private Label LblLevelElixirSize;
    @FXML
    private Label LblNumberElixirSize;
    @FXML
    private Label lblCapacidadAcutual;
    @FXML
    private Label LblActualElixirSize;
    @FXML
    private Label lblCapacidadAumenta;
    @FXML
    private Label lblCapacidadCosto;
    @FXML
    private Label LblElixirSizeCost;
    @FXML
    private ProgressBar progressElixirSize;
    @FXML
    private Button btnElixirSize;

    // Velocidad de Elixir
    @FXML
    private Label lblVelocidad;
    @FXML
    private Label lblVelocidadNivel;
    @FXML
    private Label LblLevelElixirSpeed;
    @FXML
    private Label LblNumberElixirSpeed;
    @FXML
    private Label lblVelocidadActual;
    @FXML
    private Label LblActualElixirSpeed;
    @FXML
    private Label lblVelocidadAumenta;
    @FXML
    private Label lblVelocidadCosto;
    @FXML
    private Label LblElixirSipeedCost;
    @FXML
    private ProgressBar progressElixirSpeed;
    @FXML
    private Button btnElixirSpeed;

    // Daño de Bomba
    @FXML
    private Label lblBombaD;
    @FXML
    private Label lblBombaDNivel;
    @FXML
    private Label LblLevelPowerDamage;
    @FXML
    private Label LblNumberPowerDamage;
    @FXML
    private Label lblBombaDActual;
    @FXML
    private Label LblActualPowerDamage;
    @FXML
    private Label lblBombaDAumenta;
    @FXML
    private Label lblBombaDCosto;
    @FXML
    private Label LblPowerDamageCost;
    @FXML
    private ProgressBar progressPowerDamage;
    @FXML
    private Button btnPowerDamage;

    // Rango de Daño de Bomba
    @FXML
    private Label lblBombaR;
    @FXML
    private Label lblBombaRNivel;
    @FXML
    private Label LblLevelPowerDamageRange;
    @FXML
    private Label LblNumberPowerDamageRange;
    @FXML
    private Label lblBombaRActual;
    @FXML
    private Label LblActualPowerDamageRange;
    @FXML
    private Label lblBombaRAumenta;
    @FXML
    private Label lblBombaRCosto;
    @FXML
    private Label LblPowerDamageCostRange;
    @FXML
    private ProgressBar progressPowerDamageRange;
    @FXML
    private Button btnPowerDamageRange;

    //Congelación de Bomba
    @FXML
    private Label lblHieloD;
    @FXML
    private Label lblHieloDNivel;
    @FXML
    private Label LblLevelPowerFreeze;
    @FXML
    private Label LblNumberPowerFreeze;
    @FXML
    private Label lblHieloDActual;
    @FXML
    private Label LblActualPowerFreeze;
    @FXML
    private Label lblHieloDAumenta;
    @FXML
    private Label lblHieloDCosto;
    @FXML
    private Label LblPowerFreezeCost;
    @FXML
    private ProgressBar progressPowerFreeze;
    @FXML
    private Button btnPowerFreeze;

    // Rango de Congelación
    @FXML
    private Label lblHieloR;
    @FXML
    private Label lblHieloRNIvel;
    @FXML
    private Label LblLevelPowerFreezeRange;
    @FXML
    private Label LblNumberPowerFreezeRange;
    @FXML
    private Label lblHieloRActual;
    @FXML
    private Label LblActualPowerFreezeRange;
    @FXML
    private Label lblHieloRAumenta;
    @FXML
    private Label lblHieloRCosto;
    @FXML
    private Label LblPowerFreezeCostRange;
    @FXML
    private ProgressBar progressPowerFreezeRange;
    @FXML
    private Button btnPowerFreezeRange;

    //Daño del Arma
    @FXML
    private Label lblGunD;
    @FXML
    private Label lblGunDNivel;
    @FXML
    private Label LblLevelDamageGun;
    @FXML
    private Label LblNumberDamageGun;
    @FXML
    private Label lblGunDActual;
    @FXML
    private Label LblActualDamageGun;
    @FXML
    private Label lblGunDAumenta;
    @FXML
    private Label lblGunDCosto;
    @FXML
    private Label LblDamageGunCost;
    @FXML
    private ProgressBar progressDamageGun;
    @FXML
    private Button btnDamageGun;

    // Velocidad del Arma
    @FXML
    private Label lblGunS;
    @FXML
    private Label lblGunSNivel;
    @FXML
    private Label LblLevelSpeedGun;
    @FXML
    private Label LblNumberSpeedGun;
    @FXML
    private Label lblGunSActual;
    @FXML
    private Label LblActualSpeedGun;
    @FXML
    private Label lblGunSAumenta;
    @FXML
    private Label lblGunSCosto;
    @FXML
    private Label LblSpeedGunCost;
    @FXML
    private ProgressBar progressSpeedGun;
    @FXML
    private Button btnSpeedGun;

    private UsersDto usuarioActivo;
    private UsersConfigLevelDto configNivelActivo;

    private String mensage = "Monedas insuficiente";
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     SoundManager.playFondo1Sound();
     SoundManager.cargarEfectosBotones();
     SoundManager.cargarEfectosError();
     cargarDatosDesdeBD();
     LblHealthCost.setText(String.valueOf(configNivelActivo.userLevelBaseheathProperty().get() * 3));
     calcularDetallesMejoras();
    }    

    @FXML
    private void onActionGame(ActionEvent event) {
        enviarDatosABD();
        try {
            Parent newView = App.loadFXML("GameView");
            StackPane root = App.getRoot();

            newView.setTranslateX(root.getWidth());
            root.getChildren().add(newView);

            btnJugar.setDisable(true);

            // Configurar animación de deslizamiento
            TranslateTransition slide = new TranslateTransition(javafx.util.Duration.millis(800), newView);
            slide.setToX(0);
            
            slide.setOnFinished(e -> {
                root.getChildren().remove(0);
                btnJugar.setDisable(false); 
            });

            slide.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onActionHome(ActionEvent event) {
        enviarDatosABD();
        try {
            Parent newView = App.loadFXML("HomeView");
            StackPane root = App.getRoot();

            newView.setTranslateX(-root.getWidth());
            root.getChildren().add(newView);
            
            btnHome.setDisable(true);

            // Configurar animación de deslizamiento
            TranslateTransition slide = new TranslateTransition(javafx.util.Duration.millis(800), newView);
            slide.setToX(0);

            slide.setOnFinished(e -> {
                root.getChildren().remove(0);
                btnHome.setDisable(false); 
            });

            slide.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
@FXML // Botones de mejora
private void onActionHealth(ActionEvent event) {

procesarCompraMejora(configNivelActivo.userLevelBaseheathProperty(), 50, () -> {
        configNivelActivo.setUserLevelBaseheath(configNivelActivo.userLevelBaseheathProperty().get());
    });
}
    @FXML
    private void onActionElixirSize(ActionEvent event) {
    procesarCompraMejora(configNivelActivo.userLevelCapacityelixirProperty(), 50, () -> {
        configNivelActivo.setUserLevelCapacityelixir(configNivelActivo.userLevelCapacityelixirProperty().get());
    });
    }

    @FXML
    private void onActionElixirSpeed(ActionEvent event) {
       procesarCompraMejora(configNivelActivo.userLevelSpeedelixirProperty(), 50, () -> {
        configNivelActivo.setUserLevelSpeedelixir(configNivelActivo.userLevelSpeedelixirProperty().get());
    });
    }

    @FXML
    private void onActionPowerDamage(ActionEvent event) {
    procesarCompraMejora(configNivelActivo.userLevelPowerbombdamageProperty(), 50, () -> {
        configNivelActivo.setUserLevelPowerbombdamage(configNivelActivo.userLevelPowerbombdamageProperty().get());
    });
    }

    @FXML
    private void onActionPowerFreeze(ActionEvent event) {
    procesarCompraMejora(configNivelActivo.userLevelPowerfreezedurationProperty(), 50, () -> {
        configNivelActivo.setUserLevelPowerfreezeduration(configNivelActivo.userLevelPowerfreezedurationProperty().get());
    });
    }

    @FXML
    private void onActionPowerBombRange(ActionEvent event) {
      procesarCompraMejora(configNivelActivo.userLevelPowerbombrangeProperty(), 50, () -> {
        configNivelActivo.setUserLevelPowerbombrange(configNivelActivo.userLevelPowerbombrangeProperty().get());
    });
    }

    @FXML
    private void onActionPowerFreezeRange(ActionEvent event) {
       procesarCompraMejora(configNivelActivo.userLevelPowerfreezerangeProperty(), 50, () -> {
        configNivelActivo.setUserLevelPowerfreezerange(configNivelActivo.userLevelPowerfreezerangeProperty().get());
    });
    }

    @FXML
    private void onActionDamageGun(ActionEvent event) {
     procesarCompraMejora(configNivelActivo.userLevelGundamageProperty(), 50, () -> {
        configNivelActivo.setUserLevelGundamage(configNivelActivo.userLevelGundamageProperty().get());
    });
    }
    @FXML
    private void onActionSpeedGun(ActionEvent event) {
        procesarCompraMejora(configNivelActivo.userLevelGunspeedProperty(), 50, () -> {
            configNivelActivo.setUserLevelGunspeed(configNivelActivo.userLevelGunspeedProperty().get());
        });
    }

    private void cargarDatosDesdeBD() {
        try {
            usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
            if (usuarioActivo == null || usuarioActivo.getUserId() == null) { 
                return;
            }

            UsersService usersService = new UsersService();
            UsersDto usuarioFresco = usersService.obtenerUsuario(usuarioActivo.getUserId());
            
            if (usuarioFresco != null && usuarioFresco.getUsersConfigList() != null && !usuarioFresco.getUsersConfigList().isEmpty()) {

                AppContext.getInstance().setUsuarioSeleccionado(usuarioFresco);
                this.usuarioActivo = usuarioFresco;
                this.configNivelActivo = usuarioFresco.getUsersConfigList().get(0).getUsersConfigLevel();      
                
                bindearComponentesUI();
                calcularDetallesMejoras();
                
                var configGeneral = usuarioFresco.getUsersConfigList().get(0).getUsersConfigGeneral();
                if (configGeneral != null) {
                    String idioma = configGeneral.getUserLanguage();
                    if (idioma != null) {
                        aplicarIdiomaEnPantalla(idioma);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enviarDatosABD() {
        if (usuarioActivo == null || configNivelActivo == null) return;
        
        try {  
            configNivelActivo.setUserLevelBaseheath(configNivelActivo.userLevelBaseheathProperty().get());
            configNivelActivo.setUserLevelCapacityelixir(configNivelActivo.userLevelCapacityelixirProperty().get());
            configNivelActivo.setUserLevelSpeedelixir(configNivelActivo.userLevelSpeedelixirProperty().get());
            configNivelActivo.setUserLevelGundamage(configNivelActivo.userLevelGundamageProperty().get());
            configNivelActivo.setUserLevelGunspeed(configNivelActivo.userLevelGunspeedProperty().get());
            configNivelActivo.setUserLevelPowerbombdamage(configNivelActivo.userLevelPowerbombdamageProperty().get());
            configNivelActivo.setUserLevelPowerbombrange(configNivelActivo.userLevelPowerbombrangeProperty().get());
            configNivelActivo.setUserLevelPowerfreezeduration(configNivelActivo.userLevelPowerfreezedurationProperty().get());
            configNivelActivo.setUserLevelPowerfreezerange(configNivelActivo.userLevelPowerfreezerangeProperty().get());
            
            UsersService usersService = new UsersService();
            UsersDto usuarioGuardado = usersService.guardarUsuario(usuarioActivo);
            
            if (usuarioGuardado == null) {
                return;
            }

            // Seteamos la respuesta fresca devuelta por Oracle en el contexto global
            AppContext.getInstance().setUsuarioSeleccionado(usuarioGuardado);
            this.usuarioActivo = usuarioGuardado;
            this.configNivelActivo = usuarioGuardado.getUsersConfigList().get(0).getUsersConfigLevel();    
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindearComponentesUI() {
        if (configNivelActivo == null) return; 
        try {
            LblLevelHealth.textProperty().unbind();
            LblLevelElixirSize.textProperty().unbind();
            LblLevelElixirSpeed.textProperty().unbind();
            LblLevelDamageGun.textProperty().unbind();
            LblLevelSpeedGun.textProperty().unbind();
            LblLevelPowerDamage.textProperty().unbind();
            LblLevelPowerDamageRange.textProperty().unbind();
            LblLevelPowerFreeze.textProperty().unbind();
            LblLevelPowerFreezeRange.textProperty().unbind();
            
        } catch (Exception e) {
        }
    }
        
    private void calcularDetallesMejoras() {
        if (configNivelActivo == null || usuarioActivo == null) return;

        //  MEJORA: Salud Costo base: 50, Efecto: +100 HP por nivel
        actualizarFilaMejora(configNivelActivo.userLevelBaseheathProperty().get(), 50, 100, 
            LblLevelHealth, LblHealthCost, LblActualHealth, LblNumberHealth, progressHealth, btnHealth);

        //  MEJORA: Tamaño Elixir Costo base: 50, Efecto: +10 de capacidad
        actualizarFilaMejora(configNivelActivo.userLevelCapacityelixirProperty().get(), 50, 10, 
            LblLevelElixirSize, LblElixirSizeCost, LblActualElixirSize, LblNumberElixirSize, progressElixirSize, btnElixirSize);

        //  MEJORA: Velocidad Elixir  Costo base: 50, Efecto: +1 de recarga
        actualizarFilaMejora(configNivelActivo.userLevelSpeedelixirProperty().get(), 50, 1, 
            LblLevelElixirSpeed, LblElixirSipeedCost, LblActualElixirSpeed, LblNumberElixirSpeed, progressElixirSpeed, btnElixirSpeed);

        //  MEJORA: Daño de Arma  Costo base: 50, Efecto: +25 de daño
        actualizarFilaMejora(configNivelActivo.userLevelGundamageProperty().get(), 50, 25, 
            LblLevelDamageGun, LblDamageGunCost, LblActualDamageGun, LblNumberDamageGun, progressDamageGun, btnDamageGun);

        // MEJORA: Velocidad de Disparo  Costo base: 50, Efecto: +1 de velocidad
        actualizarFilaMejora(configNivelActivo.userLevelGunspeedProperty().get(), 50, 1, 
            LblLevelSpeedGun, LblSpeedGunCost, LblActualSpeedGun, LblNumberSpeedGun, progressSpeedGun, btnSpeedGun);

        //  MEJORA: Poder de Daño  Costo base: 50, Efecto: +20 daño especial
        actualizarFilaMejora(configNivelActivo.userLevelPowerbombdamageProperty().get(), 50, 20, 
            LblLevelPowerDamage, LblPowerDamageCost, LblActualPowerDamage, LblNumberPowerDamage, progressPowerDamage, btnPowerDamage);

        //  MEJORA: Rango de Daño  Costo base: 50, Efecto: +5 de rango
        actualizarFilaMejora(configNivelActivo.userLevelPowerbombrangeProperty().get(), 50, 5, 
            LblLevelPowerDamageRange, LblPowerDamageCostRange, LblActualPowerDamageRange, LblNumberPowerDamageRange, progressPowerDamageRange, btnPowerDamageRange);

        //  MEJORA: Congelación  Costo base: 50, Efecto: +1 segundo extra
        actualizarFilaMejora(configNivelActivo.userLevelPowerfreezedurationProperty().get(), 50, 1, 
            LblLevelPowerFreeze, LblPowerFreezeCost, LblActualPowerFreeze, LblNumberPowerFreeze, progressPowerFreeze, btnPowerFreeze);

        //  MEJORA: Rango de Congelación  Costo base: 50, Efecto: +5 área
        actualizarFilaMejora(configNivelActivo.userLevelPowerfreezerangeProperty().get(), 50, 5, 
            LblLevelPowerFreezeRange, LblPowerFreezeCostRange, LblActualPowerFreezeRange, LblNumberPowerFreezeRange, progressPowerFreezeRange, btnPowerFreezeRange);
    }
        
    private void actualizarFilaMejora(long nivelMejora, long costoBase, long multiplicadorEfecto, Label lblNivel, Label lblCosto, Label lblActualEfecto, Label lblEfecto, ProgressBar progressBar, Button btnMejorar) {
        lblNivel.setText(String.valueOf(nivelMejora));
        
        long monedasActuales = configNivelActivo.userPointsProperty().get();
        long costoTotal = nivelMejora * costoBase;
        long efectoActual = nivelMejora * multiplicadorEfecto;
        long efectoSiguiente = (nivelMejora + 1) * multiplicadorEfecto;
        
        if (nivelMejora > 10) {
            lblNivel.setText("(MAX)");
            lblCosto.setText("Nivel Máximo");
            lblCosto.setStyle("-fx-text-fill: #f39c12; -fx-font-weight: bold;"); 
            lblEfecto.setText(String.valueOf(0));
            lblActualEfecto.setText(String.valueOf(efectoActual));
            progressBar.setProgress(1.0); 
            
            btnMejorar.setVisible(false);
            btnMejorar.setMaxSize(0, 0);
            btnMejorar.setMinSize(0, 0); 
            return; 
        }

        btnMejorar.setVisible(true);
        btnMejorar.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 

        double progreso = (double) monedasActuales / costoTotal;
        if (progreso > 1.0) progreso = 1.0;
        if (progreso < 0.0) progreso = 0.0;
        
        lblCosto.setText(monedasActuales + " / " + costoTotal);
        LblPoints.setText(String.valueOf(monedasActuales));
        lblActualEfecto.setText(String.valueOf(efectoActual));
        lblEfecto.setText(String.valueOf(efectoSiguiente - efectoActual));
        progressBar.setProgress(progreso);

        if (monedasActuales < costoTotal) {
            lblCosto.setStyle("-fx-text-fill: #ff4d4d;"); 
        } else {
            lblCosto.setStyle("-fx-text-fill: #2ecc71;"); 
        }
    }
        
    private void procesarCompraMejora(LongProperty propiedadNivel, long costoBase, Runnable actualizarSetterPrimitivo) {
        if (configNivelActivo == null) return;

        try {
            long nivelActual = propiedadNivel.get();
            long costoTotal = nivelActual * costoBase;
            long monedasActuales = configNivelActivo.userPointsProperty().get();

            if (monedasActuales < costoTotal) {
                Validations.showMessage(LblMessage, mensage, rootValidations);
                SoundManager.playErrorSound();
                return;
            }
                
            configNivelActivo.userPointsProperty().set(monedasActuales - costoTotal);
            propiedadNivel.set(nivelActual + 1);

            configNivelActivo.setUserPoints(configNivelActivo.userPointsProperty().get());

            actualizarSetterPrimitivo.run();

            UsersService usersService = new UsersService();
            UsersDto usuarioGuardado = usersService.guardarUsuario(usuarioActivo);

            if (usuarioGuardado == null) {
                return;
            }

            AppContext.getInstance().setUsuarioSeleccionado(usuarioGuardado);
            this.usuarioActivo = usuarioGuardado;
            this.configNivelActivo = usuarioGuardado.getUsersConfigList().get(0).getUsersConfigLevel();

            // Redibujamos por completo los valores visuales de la tienda
            bindearComponentesUI(); 
            calcularDetallesMejoras(); 
            SoundManager.playBotonSound();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // CAmbia el idioma de todos los Labes
  private void aplicarIdiomaEnPantalla(String idioma) {
    if ("I".equalsIgnoreCase(idioma)) {
       lblTitulo.setText("Upgrades");
        lblPointsD.setText("Available Points");
        tabPoder.setText("Powers");
        lblBombaD.setText("Bomb Damage");
        lblBombaDNivel.setText("Level");
        lblBombaDActual.setText("Current Damage");
        lblBombaDAumenta.setText("Damage Increases");
        lblBombaDCosto.setText("Cost");
        btnPowerDamage.setText("Improve");
        
        lblBombaR.setText("Bomb Range");
        lblBombaRNivel.setText("Level");
        lblBombaRActual.setText("Current Range");
        lblBombaRAumenta.setText("Range Increases");
        lblBombaRCosto.setText("Cost");
        btnPowerDamageRange.setText("Improve");
        
        lblHieloD.setText("Freezing Duration");
        lblHieloDNivel.setText("Level");
        lblHieloDActual.setText("Current Duration");
        lblHieloDAumenta.setText("Duration Increases");
        lblHieloDCosto.setText("Cost");
        btnPowerFreeze.setText("Improve");
        
        lblHieloR.setText("Freezing Range");
        lblHieloRNIvel.setText("Level");
        lblHieloRActual.setText("Current Range");
        lblHieloRAumenta.setText("Range Increases");
        lblHieloRCosto.setText("Cost");
        btnPowerFreezeRange.setText("Improve");
        
        tabArma.setText("Gun");
        lblGunD.setText("Weapon Damage");
        lblGunDNivel.setText("Level");
        lblGunDActual.setText("Current Damage");
        lblGunDAumenta.setText("Damage Increases");
        lblGunDCosto.setText("Cost");
        btnDamageGun.setText("Improve");
        
        lblGunS.setText("Speed ​​Weapon");
        lblGunSNivel.setText("Level");
        lblGunSActual.setText("Current Speed");
        lblGunSAumenta.setText("Speed ​​Increases");
        lblGunSCosto.setText("Cost");
        btnSpeedGun.setText("Improve");
        
        tabBase.setText("Base");
        lblVida.setText("Health");
        lblVidaActual.setText("Current Health");
        lblVidaAumenta.setText("Health Increases");
        lblVidaNivel.setText("Level");
        lblVidaCost.setText("Cost");
        btnHealth.setText("Improve");
      
        lblCapacidad.setText("Elixir Capacity");
        lblCapacidadNivel.setText("Level");
        lblCapacidadAcutual.setText("Current Capacity");
        lblCapacidadAumenta.setText("Capacity Increases");
        lblCapacidadCosto.setText("Cost");
        btnElixirSize.setText("Improve");
        
        lblVelocidad.setText("Speed ​​Elixir");
        lblVelocidadNivel.setText("Level");
        lblVelocidadActual.setText("Current Speed");
        lblVelocidadAumenta.setText("Speed ​​Increases");
        lblVelocidadCosto.setText("Cost");
        
        mensage="Insufficient Coins";
        btnJugar.setText("PLay");
        btnHome.setText("Main Menu");
    }
}
}
