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
import javafx.beans.binding.Bindings;
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
import javafx.scene.media.AudioClip;

public class UpgradesController implements Initializable {
  
    @FXML
    private Label LblLevelHealth;
    @FXML
    private Label LblNumberHealth;
    @FXML
    private ProgressBar progressHealth;
    @FXML
    private Label LblHealthCost;
    @FXML
    private Label LblLevelElixirSize;
    @FXML
    private Label LblNumberElixirSize;
    @FXML
    private Label LblElixirSizeCost;
    @FXML
    private ProgressBar progressElixirSize;
    @FXML
    private Label LblLevelElixirSpeed;
    @FXML
    private Label LblNumberElixirSpeed;
    @FXML
    private ProgressBar progressElixirSpeed;
    @FXML
    private Label LblElixirSipeedCost;
    @FXML
    private Label LblLevelPowerDamage;
    @FXML
    private Label LblNumberPowerDamage;
    @FXML
    private ProgressBar progressPowerDamage;
    @FXML
    private Label LblPowerDamageCost;
    @FXML
    private Label LblLevelPowerFreeze;
    @FXML
    private Label LblNumberPowerFreeze;
    @FXML
    private ProgressBar progressPowerFreeze;
    @FXML
    private Label LblPowerFreezeCost;
    @FXML
    private VBox rootValidations;
    @FXML
    private Label LblMessage;
      String mensage = "Monedas insuficiente";
    @FXML
    private Label LblLevelPowerDamageRange;
      @FXML
    private Label LblNumberPowerDamageRange;
    @FXML
    private Label LblPowerFreezeCostRange;
    @FXML
    private Label LblLevelPowerFreezeRange;
    @FXML
    private Label LblNumberPowerFreezeRange;
    @FXML
    private ProgressBar progressPowerDamageRange;
    @FXML
    private Label LblPowerDamageCostRange;
    @FXML
    private ProgressBar progressPowerFreezeRange;
    @FXML
    private Button btnPowerFreezeRange;
    @FXML
    private Button btnPowerDamageRange;
    @FXML
    private Button btnElixirSize;
    @FXML
    private Button btnElixirSpeed;
    @FXML
    private Button btnHealth;
    @FXML
    private Button btnPowerFreeze;
    @FXML
    private Button btnPowerDamage;
    @FXML
    private Label LblLevelDamageGun;
    @FXML
    private Label LblNumberDamageGun;
    @FXML
    private ProgressBar progressDamageGun;
    @FXML
    private Button btnDamageGun;
    @FXML
    private Label LblLevelSpeedGun;
    @FXML
    private Label LblNumberSpeedGun;
    @FXML
    private ProgressBar progressSpeedGun;
    @FXML
    private Label LblSpeedGunCost;
    @FXML
    private Label LblDamageGunCost;
    @FXML
    private Button btnSpeedGun;
    @FXML
    private Label LblPoints;
private UsersConfigLevelDto configNivelActivo;
private UsersDto usuarioActivo;
    @FXML
    private Label LblActualPowerDamage;
    @FXML
    private Label LblActualPowerDamageRange;
    @FXML
    private Label LblActualPowerFreeze;
    @FXML
    private Label LblActualPowerFreezeRange;
    @FXML
    private Label LblActualDamageGun;
    @FXML
    private Label LblActualSpeedGun;
    @FXML
    private Label LblActualHealth;
    @FXML
    private Label LblActualElixirSize;
    @FXML
    private Label LblActualElixirSpeed;
  private String audioUp;
  private String audioError;
    @FXML
    private Tab tabPoder;
    @FXML
    private Tab tabArma;
    @FXML
    private Tab tabBase;
    @FXML
    private Label lblVida;
    private Label lblNivel;
    @FXML
    private Label lblVidaActual;
    @FXML
    private Label lblVidaAumenta;
    private Label lblCost;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblVidaNivel;
    @FXML
    private Label lblVidaCost;
    @FXML
    private Label lblCapacidad;
    @FXML
    private Label lblCapacidadNivel;
    @FXML
    private Label lblCapacidadAcutual;
    @FXML
    private Label lblCapacidadAumenta;
    @FXML
    private Label lblCapacidadCosto;
    @FXML
    private Label lblVelocidad;
    @FXML
    private Label lblVelocidadNivel;
    @FXML
    private Label lblVelocidadActual;
    @FXML
    private Label lblVelocidadAumenta;
    @FXML
    private Label lblVelocidadCosto;
    @FXML
    private Label lblBombaD;
    @FXML
    private Label lblBombaDNivel;
    @FXML
    private Label lblBombaDActual;
    @FXML
    private Label lblBombaDAumenta;
    @FXML
    private Label lblBombaDCosto;
    @FXML
    private Label lblBombaR;
    @FXML
    private Label lblBombaRNivel;
    @FXML
    private Label lblBombaRActual;
    @FXML
    private Label lblBombaRAumenta;
    @FXML
    private Label lblBombaRCosto;
    @FXML
    private Label lblHieloD;
    @FXML
    private Label lblHieloDNivel;
    @FXML
    private Label lblHieloDActual;
    @FXML
    private Label lblHieloDAumenta;
    @FXML
    private Label lblHieloDCosto;
    @FXML
    private Label lblHieloR;
    @FXML
    private Label lblHieloRNIvel;
    @FXML
    private Label lblHieloRActual;
    @FXML
    private Label lblHieloRAumenta;
    @FXML
    private Label lblHieloRCosto;
    @FXML
    private Label lblGunD;
    @FXML
    private Label lblGunDNivel;
    @FXML
    private Label lblGunDActual;
    @FXML
    private Label lblGunDAumenta;
    @FXML
    private Label lblGunDCosto;
    @FXML
    private Label lblGunS;
    @FXML
    private Label lblGunSNivel;
    @FXML
    private Label lblGunSActual;
    @FXML
    private Label lblGunSAumenta;
    @FXML
    private Label lblGunSCosto;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnJugar;
    @FXML
    private Label lblPointsD;
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

        TranslateTransition slide = new TranslateTransition(javafx.util.Duration.millis(800), newView);
        slide.setToX(0);
         btnJugar.setDisable(true);
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
        TranslateTransition slide = new TranslateTransition(javafx.util.Duration.millis(800), newView);
        slide.setToX(0);

        slide.setOnFinished(e -> {
            root.getChildren().remove(0); 
             btnHome.setDisable(true);
        });

        slide.play();

    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    
    
@FXML
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
       procesarCompraMejora(configNivelActivo.userLevelPowerfreezerangeProperty(), 5, () -> {
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
        
        if (usuarioActivo != null && usuarioActivo.getUserId() != null) {
            UsersService usersService = new UsersService();
            
            // 1. Forzamos la descarga limpia desde Oracle eliminando cachés
            UsersDto usuarioFresco = usersService.obtenerUsuario(usuarioActivo.getUserId());
            
            if (usuarioFresco != null && usuarioFresco.getUsersConfigList() != null 
                    && !usuarioFresco.getUsersConfigList().isEmpty()) {
                
                // 2. Sincronizamos las variables del controlador y el contexto global
                AppContext.getInstance().setUsuarioSeleccionado(usuarioFresco);
                this.usuarioActivo = usuarioFresco;
                this.configNivelActivo = usuarioFresco.getUsersConfigList().get(0).getUsersConfigLevel();
                
                System.out.println("LOG MEJORAS: Datos de mejoras descargados con éxito de Oracle.");
                
                // 3. Mandamos a bindear inmediatamente después de cargar
                bindearComponentesUI();
                calcularDetallesMejoras();
                var configGeneral=usuarioFresco.getUsersConfigList().get(0).getUsersConfigGeneral();
                if (configGeneral != null) {
                  String idioma = configGeneral.getUserLanguage(); // 💡 Cambia por tu método real
                    if (idioma != null) {
                        aplicarIdiomaEnPantalla(idioma);
                    }
                }
            }
        } else {
            System.err.println("LOG MEJORAS: No se encontró un usuario activo en el contexto.");
        }
    } catch (Exception e) {
        System.err.println("LOG MEJORAS: Error al cargar datos: " + e.getMessage());
        e.printStackTrace();
    }
}
    private void bindearComponentesUI() {
    if (configNivelActivo == null) return;
    
    try {
        // 1. 🛡️ LIMPIEZA EXTREMA: Rompemos bindeos previos para evitar duplicados en memoria
        LblLevelHealth.textProperty().unbind();
        LblLevelElixirSize.textProperty().unbind();
        LblLevelElixirSpeed.textProperty().unbind();
        LblLevelDamageGun.textProperty().unbind();
        LblLevelSpeedGun.textProperty().unbind();
        LblLevelPowerDamage.textProperty().unbind();
        LblLevelPowerDamageRange.textProperty().unbind();
        LblLevelPowerFreeze.textProperty().unbind();
        LblLevelPowerFreezeRange.textProperty().unbind();
        
       
        
        System.out.println("LOG MEJORAS: Todos los Labels de mejoras han sido bindeados a la UI.");
    } catch (Exception e) {
        System.err.println("LOG MEJORAS: Error al aplicar bindeos en la UI: " + e.getMessage());
    }
}
    
    private void enviarDatosABD() {
    if (usuarioActivo == null || configNivelActivo == null) return;
    
    try {
        System.out.println("LOG MEJORAS: Sincronizando estados numéricos antes de guardar...");
        
        // 1. Extraemos los valores actuales de las propiedades JavaFX y los consolidamos en los atributos simples
        configNivelActivo.setUserLevelBaseheath(configNivelActivo.userLevelBaseheathProperty().get());
        configNivelActivo.setUserLevelCapacityelixir(configNivelActivo.userLevelCapacityelixirProperty().get());
        configNivelActivo.setUserLevelSpeedelixir(configNivelActivo.userLevelSpeedelixirProperty().get());
        configNivelActivo.setUserLevelGundamage(configNivelActivo.userLevelGundamageProperty().get());
        configNivelActivo.setUserLevelGunspeed(configNivelActivo.userLevelGunspeedProperty().get());
        configNivelActivo.setUserLevelPowerbombdamage(configNivelActivo.userLevelPowerbombdamageProperty().get());
        configNivelActivo.setUserLevelPowerbombrange(configNivelActivo.userLevelPowerbombrangeProperty().get());
        configNivelActivo.setUserLevelPowerfreezeduration(configNivelActivo.userLevelPowerfreezedurationProperty().get());
        configNivelActivo.setUserLevelPowerfreezerange(configNivelActivo.userLevelPowerfreezerangeProperty().get());
        
        // 2. Enviamos el DTO principal completo al Service para que actualice la BD
        UsersService usersService = new UsersService();
        UsersDto usuarioGuardado = usersService.guardarUsuario(usuarioActivo);
        
        if (usuarioGuardado != null) {
            // 3. Seteamos la respuesta fresca devuelta por Oracle en el contexto global
            AppContext.getInstance().setUsuarioSeleccionado(usuarioGuardado);
            this.usuarioActivo = usuarioGuardado;
            this.configNivelActivo = usuarioGuardado.getUsersConfigList().get(0).getUsersConfigLevel();
            
            System.out.println("LOG MEJORAS: ¡Tienda de mejoras guardada con éxito en Oracle y TOAD!");
        } else {
            System.err.println("LOG MEJORAS: Error al intentar guardar las mejoras. El servicio retornó null.");
        }
    } catch (Exception e) {
        System.err.println("LOG MEJORAS: Error crítico al enviar datos a la base de datos: " + e.getMessage());
        e.printStackTrace();
    }
}
    
  private void calcularDetallesMejoras() {
    if (configNivelActivo == null || usuarioActivo == null) return;

    // 1. MEJORA: Salud (Health) -> Costo base: 150, Efecto: +20 HP por nivel
    actualizarFilaMejora(
        configNivelActivo.userLevelBaseheathProperty().get(), 50, 100, 
        LblLevelHealth,LblHealthCost,LblActualHealth, LblNumberHealth, progressHealth,btnHealth
    );

    // 2. MEJORA: Tamaño Elixir -> Costo base: 200, Efecto: +10 de capacidad
    actualizarFilaMejora(
        configNivelActivo.userLevelCapacityelixirProperty().get(), 50, 10, 
       LblLevelElixirSize,LblElixirSizeCost,LblActualElixirSize, LblNumberElixirSize, progressElixirSize,btnElixirSize
    );

    // 3. MEJORA: Velocidad Elixir -> Costo base: 250, Efecto: +5 de recarga
    actualizarFilaMejora(
        configNivelActivo.userLevelSpeedelixirProperty().get(), 50, 1, 
        LblLevelElixirSpeed,LblElixirSipeedCost,LblActualElixirSpeed, LblNumberElixirSpeed, progressElixirSpeed,btnElixirSpeed
    );

    // 4. MEJORA: Daño de Arma -> Costo base: 300, Efecto: +15 de daño
    actualizarFilaMejora(
        configNivelActivo.userLevelGundamageProperty().get(), 50, 25, 
        LblLevelDamageGun,LblDamageGunCost,LblActualDamageGun, LblNumberDamageGun, progressDamageGun,btnDamageGun
    );

    // 5. MEJORA: Velocidad de Disparo -> Costo base: 300, Efecto: +2 de velocidad
    actualizarFilaMejora(
        configNivelActivo.userLevelGunspeedProperty().get(), 50, 1, 
        LblLevelSpeedGun,LblSpeedGunCost,LblActualSpeedGun, LblNumberSpeedGun, progressSpeedGun,btnSpeedGun
    );

    // 6. MEJORA: Poder de Daño -> Costo base: 400, Efecto: +2s5 daño especial
    actualizarFilaMejora(
        configNivelActivo.userLevelPowerbombdamageProperty().get(), 50, 20, 
        LblLevelPowerDamage,LblPowerDamageCost,LblActualPowerDamage, LblNumberPowerDamage, progressPowerDamage,btnPowerDamage
    );

    // 7. MEJORA: Rango de Daño -> Costo base: 350, Efecto: +12 de rango
    actualizarFilaMejora(
        configNivelActivo.userLevelPowerbombrangeProperty().get(), 50, 5, 
        LblLevelPowerDamageRange,LblPowerDamageCostRange,LblActualPowerDamageRange, LblNumberPowerDamageRange, progressPowerDamageRange,btnPowerDamageRange
    );

    // 8. MEJORA: Congelación -> Costo base: 500, Efecto: +1 segundo extra
    actualizarFilaMejora(
        configNivelActivo.userLevelPowerfreezedurationProperty().get(), 50, 1, 
        LblLevelPowerFreeze,LblPowerFreezeCost,LblActualPowerFreeze, LblNumberPowerFreeze, progressPowerFreeze,btnPowerFreeze
    );

    // 9. MEJORA: Rango de Congelación -> Costo base: 450, Efecto: +10 área
    actualizarFilaMejora(
        configNivelActivo.userLevelPowerfreezerangeProperty().get(), 50, 5, 
        LblLevelPowerFreezeRange,LblPowerFreezeCostRange,LblActualPowerFreezeRange, LblNumberPowerFreezeRange, progressPowerFreezeRange,btnPowerFreezeRange
    );
}
    private void actualizarFilaMejora(
        long nivelMejora, 
        long costoBase, 
        long multiplicadorEfecto, 
        Label lblNivel,     
        Label lblCosto,
        Label lblActualEfecto,
        Label lblEfecto, 
        ProgressBar progressBar,
        Button btnMejorar) {
    
    // 1. Seteamos el número de nivel actual inmediatamente
    lblNivel.setText(String.valueOf(nivelMejora));
    
    long monedasActuales = configNivelActivo.userPointsProperty().get();
    long costoTotal = nivelMejora * costoBase;
    long efectoActual = nivelMejora * multiplicadorEfecto;
    long efectoSiguiente = (nivelMejora + 1) * multiplicadorEfecto;
    
    // 2. 👑 CONTROL DE NIVEL MÁXIMO (Nivel 10)
    if (nivelMejora > 10) {
        lblNivel.setText("(MAX)"); // Aseguramos que pinte el 10
        lblCosto.setText("Nivel Máximo");
        lblCosto.setStyle("-fx-text-fill: #f39c12; -fx-font-weight: bold;"); 
        lblEfecto.setText(String.valueOf(0));
         lblActualEfecto.setText(String.valueOf(efectoActual));
        progressBar.setProgress(1.0); 
        
        // Desaparecemos y encogemos el botón por completo
        btnMejorar.setVisible(false);
        btnMejorar.setMaxSize(0, 0);
        btnMejorar.setMinSize(0, 0); 
        return; 
    }
    
    // 3. Si el nivel es menor a 10, el botón sigue visible y operando normal
    btnMejorar.setVisible(true);
    btnMejorar.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
    
    // 4. Progreso de la barra
    double progreso = (double) monedasActuales / costoTotal;
    if (progreso > 1.0) progreso = 1.0;
    if (progreso < 0.0) progreso = 0.0;
    
    // 5. Renderizado de textos ordinarios
    lblCosto.setText(monedasActuales + " / " + costoTotal);
     LblPoints.setText(String.valueOf(monedasActuales));
   lblActualEfecto.setText(String.valueOf(efectoActual));
    lblEfecto.setText(String.valueOf(efectoSiguiente - efectoActual));
    progressBar.setProgress(progreso);
    
    // 6. Validación de color de texto
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

        // 1. Escudo de nivel máximo
       

        // 2. Validación de dinero
        if (monedasActuales >= costoTotal) {
            
            // 🔄 PASO A: Modificamos las propiedades de JavaFX
            configNivelActivo.userPointsProperty().set(monedasActuales - costoTotal);
            propiedadNivel.set(nivelActual + 1);

            // 🔄 PASO B: Sincronizamos las monedas en el atributo primitivo
            configNivelActivo.setUserPoints(configNivelActivo.userPointsProperty().get());
            
            // 🔄 PASO C: Ejecutamos el truco mágico para el setter primitivo que nos pida cada botón
            actualizarSetterPrimitivo.run();

            // 3. Guardamos en la base de datos de Oracle
            UsersService usersService = new UsersService();
            UsersDto usuarioGuardado = usersService.guardarUsuario(usuarioActivo);

            if (usuarioGuardado != null) {
                // 4. Refrescamos el contexto global y las variables locales
                AppContext.getInstance().setUsuarioSeleccionado(usuarioGuardado);
                this.usuarioActivo = usuarioGuardado;
                this.configNivelActivo = usuarioGuardado.getUsersConfigList().get(0).getUsersConfigLevel();

                // 5. Redibujamos la tienda completa de un solo golpe
                bindearComponentesUI(); // Unbind por seguridad
                calcularDetallesMejoras(); // Pintar textos nuevos
                   SoundManager.playBotonSound();
                System.out.println("LOG TIENDA: Mejora guardada y procesada con éxito.");
            } else {
                System.err.println("LOG TIENDA: El servicio retornó null al guardar.");
            }
        } else {
              Validations.showMessage(LblMessage, mensage, rootValidations);
               SoundManager.playErrorSound();
        }
    } catch (Exception e) {
        System.err.println("LOG TIENDA: Error en la transacción: " + e.getMessage());
        e.printStackTrace();
    }
}
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
    } else {

   
    }
}
}
