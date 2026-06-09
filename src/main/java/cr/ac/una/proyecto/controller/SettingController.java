package cr.ac.una.proyecto.controller;
import cr.ac.una.proyecto.App;
import cr.ac.una.proyecto.model.UsersConfigDto;
import cr.ac.una.proyecto.model.UsersConfigLevelDto;
import cr.ac.una.proyecto.model.UsersDto;
import cr.ac.una.proyecto.service.UsersService;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.SoundManager;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextFormatter;

public class SettingController implements Initializable {

    @FXML private VBox root;
    @FXML private VBox rootUsers;
    @FXML private VBox rootHowPlay;

    @FXML private Tab tabJuego;
    @FXML private Tab tabUsuarios;
    @FXML private Tab tabAudio;
    @FXML private Tab tabInformacion;

    @FXML private Label lblTitulo;
    @FXML private Label lblControles;
    @FXML private Label lblPantalla;
    @FXML private Label lblIdioma;
    @FXML private Label lblGunD;
    @FXML private Label lblMenuV;
    @FXML private Label lblEnemigosV;
    @FXML private Label lblArmaV;
    @FXML private Label lblEfectoV;
    @FXML private Label lblCurso;
    @FXML private Label lblEstudiantes;
    @FXML private Label lblProfesor;
    @FXML private Label lblCiclo;
    @FXML private Label LblYear;
    @FXML private Label lblTutorial;
    @FXML private Label LblImage;

    @FXML private Button btnMenu;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnComoJugar;
    @FXML private Button BtnBack;
    @FXML private Button BtnNext;

    @FXML private CheckBox CheckEasyMode;
    @FXML private RadioButton radioCompleta;
    @FXML private RadioButton radioConBordes;
    @FXML private RadioButton radioA;
    @FXML private RadioButton radioM;
    @FXML private RadioButton radioKeybord;
    @FXML private RadioButton radioMouse;
    @FXML private RadioButton radioEspanol;
    @FXML private RadioButton radioIngles;
    @FXML private Slider SliderMenu;
    @FXML private Slider SliderEnemy;
    @FXML private Slider SliderGun;
    @FXML private Slider SliderEfect;

    @FXML private ToggleGroup controles;
    @FXML private ToggleGroup controles1;
    @FXML private ToggleGroup controles11;
    @FXML private ToggleGroup controles2;

    @FXML private ImageView ImageTutorial;

    private UsersDto usuarioActivo;
    private final UsersService usersService = new UsersService();
    private Label lblNombre;
    private ImageView imgAvatar;
    private boolean seleccionandoArchivo = false;
    private int indexTutorial = 0;
    private String nombreAnterior;

    private final java.util.List<String> rutasImagenes = java.util.Arrays.asList(
        "/cr/ac/una/proyecto/resources/tutorial4.png",
        "/cr/ac/una/proyecto/resources/tutorial2.png",
        "/cr/ac/una/proyecto/resources/tutorial1.png",
        "/cr/ac/una/proyecto/resources/tutorial3.png",
        "/cr/ac/una/proyecto/resources/tutorial5.png"
    );

    private java.util.List<String> textosTutorial = java.util.Arrays.asList(
        "Los enemigos se acercan en diferentes oleadas y tipos.",
        "La vida de la base disminuira con el ataque de enemigos y el elixir se recuperara con el tiempo.",
        "El Arma es la principal herramienta para eliminar a los enemigos, ademas de poder cambiar de estilo y controles.",
        "Los poderes permiten dañar o congelar a gran cantidad de enemigos en area.",
        "Las mejoras haran que todo el equipo pueda subir de nivel aumentando sus estadisticas"
    );


    private String aletaNombre = "Nombre Duplicado";
    private String alertaElejir = " ya existe, pruebe uno diferente.";
    private String aletaEliminar = "Eliminar Usuario";
    private String aletaAviso = "¿Estás seguro de eliminar a ";
    private String aletaInfo = "Esta acción eliminar todo el progreso del usuario";
    private String aletaBtnAceptar = "Entendido";
    private String aletaBtnEliminar = "Eliminar";
    private String aletaBtnCancelar = "Cancelar";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
        cargarDatosDesdeBD(); 
        inicializarComponentesBase();
        
        // 2. Configurar el tamaño de la ventana
        Platform.runLater(() -> {
            if (root != null && root.getScene() != null) {
                Stage stageActual = (Stage) root.getScene().getWindow();
                cargarConfiguracionDePantalla(stageActual);
            }
        });

        cargarPreferenciasUsuario();
        configurarListenersDeAudio();    
        SoundManager.cargarEfectosError();
        
        SliderMenu.valueProperty().bindBidirectional(SoundManager.menuVolumeProperty());
        SliderEnemy.valueProperty().bindBidirectional(SoundManager.getEnemyVolumeProperty());
        SliderGun.valueProperty().bindBidirectional(SoundManager.getGunVolumeProperty());
        SliderEfect.valueProperty().bindBidirectional(SoundManager.getEfectVolumeProperty());
    }
    
    private void cargarPreferenciasUsuario() {
        if (usuarioActivo == null) {
            return;
        }
        if (usuarioActivo.getUsersConfigList() == null || usuarioActivo.getUsersConfigList().isEmpty()) {
            return;
        }

        // Configuracion exacta del juego
        UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
        var configGeneral = configuracionActual.getUsersConfigGeneral();

        if (configGeneral != null) {
            // Configura el estilo del arma
            if (configGeneral.getUserStylegun() != null) {
                String estiloArma = configGeneral.getUserStylegun();
                if (estiloArma.equals("A")) {
                    radioA.setSelected(true);
                } else if (estiloArma.equals("M")) {
                    radioM.setSelected(true);
                }
            }
            
            // Configura si esta en modo facil
            if (configGeneral.getUserGamemode() != null) {
                String modoJuego = configGeneral.getUserGamemode();
                if (modoJuego.equals("E")) {
                    CheckEasyMode.setSelected(true);
                } else {
                    CheckEasyMode.setSelected(false);
                }
            } else {
                CheckEasyMode.setSelected(false); 
            }    
            
            // Configura el tipo de control
            if (configGeneral.getUserController() != null) {
                String tipoControl = configGeneral.getUserController();
                if (tipoControl.equals("K")) {
                    radioKeybord.setSelected(true);
                } else if (tipoControl.equals("M")) {
                    radioMouse.setSelected(true);
                }
            } else {
                radioMouse.setSelected(true); 
            }
            
            // Configura barras de volumen
            SliderMenu.setValue(configGeneral.getUserAudiomenu());
            SliderEnemy.setValue(configGeneral.getUserAudioenemys());
            SliderEfect.setValue(configGeneral.getUserAudioefect());
            SliderGun.setValue(configGeneral.getUserAudiogun());
          
            // Configura el idioma
            if (configGeneral.getUserLanguage() != null) {
                String idioma = configGeneral.getUserLanguage();
                if (idioma.equals("S")) {
                    radioEspanol.setSelected(true);
                } else if (idioma.equals("I")) {
                    radioIngles.setSelected(true);
                }
            } else {
                radioEspanol.setSelected(true);
            }
        }
    }
    
    @FXML
    private void onActionHome(ActionEvent event) {
        try {
            Parent newView = App.loadFXML("HomeView");
            StackPane root = App.getRoot();

            newView.setTranslateX(-root.getWidth());
            root.getChildren().add(newView);
            // Animacion hacial el menu
            TranslateTransition slide = new TranslateTransition(javafx.util.Duration.millis(800), newView);
            slide.setToX(0);
            btnMenu.setDisable(true);
            
            slide.setOnFinished(e -> {
                root.getChildren().remove(0); 
                btnMenu.setDisable(false);
            });

            slide.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onActionHowPlay(ActionEvent event) {
        LblImage.setWrapText(true);
        indexTutorial = 0; 
        actualizarPasoTutorial();
        rootHowPlay.setVisible(true);
    }

    @FXML
    private void onActionBack(ActionEvent event) {
        if (indexTutorial > 0) {
            indexTutorial--;
            actualizarPasoTutorial();
        } else {
            SoundManager.playErrorSound(); // Alerta si intenta ir mas atras del paso 1
        }
    }

    @FXML
    private void onActionNext(ActionEvent event) {
        // Si llegamos al final de las imagenes disponibles, cerramos el tutorial
        if (indexTutorial >= rutasImagenes.size() - 1) {
            rootHowPlay.setVisible(false); 
        } else {
            indexTutorial++;
            actualizarPasoTutorial();
        }
    }

    private void actualizarPasoTutorial() {
        LblImage.setText(textosTutorial.get(indexTutorial));
        try {
            String ruta = rutasImagenes.get(indexTutorial);
            javafx.scene.image.Image nuevaImagen = new javafx.scene.image.Image(getClass().getResourceAsStream(ruta));
            ImageTutorial.setImage(nuevaImagen);
        } catch (Exception e) {
        }
    }

    @FXML
    private void onActionModificUser(ActionEvent event) {
        if (usuarioActivo == null) {
            return;
        }
        seleccionandoArchivo = false; 

        // 1. Configura el campo de texto
        TextField txtModificarNombre = new TextField(usuarioActivo.getUserName());
        txtModificarNombre.getStyleClass().add("label-usuario"); 
        txtModificarNombre.setStyle(
            "-fx-background-color: #3a3a3a; -fx-text-fill: white; " +
            "-fx-background-radius: 5; -fx-padding: 5 10 5 10;"
        );

        // Control de caracteres permitidos
        txtModificarNombre.setTextFormatter(new TextFormatter<>(change -> {
            String nuevoTexto = change.getControlNewText();
            if (nuevoTexto.matches("^[a-zA-Z0-9]*$") && nuevoTexto.length() <= 12) {
                return change; 
            }
            return null; 
        }));

        // Reemplazamos temporalmente el nombre por el campo de texto
        int indiceLabel = rootUsers.getChildren().indexOf(lblNombre);
        if (indiceLabel != -1) {
            rootUsers.getChildren().set(indiceLabel, txtModificarNombre);
            txtModificarNombre.requestFocus();
        }

        //  Eventos del mouse para cambiar la imagen de avatar
        imgAvatar.setStyle("-fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, #ffcc00, 10, 0, 0, 0);"); 
        
        imgAvatar.setOnMousePressed(mouseEvent -> {
            // Levantamos el escudo antes de que el TextField pierda el foco por completo
            seleccionandoArchivo = true; 
        });

        imgAvatar.setOnMouseClicked(mouseEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar Imagen de Usuario");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
            );
            File archivoSeleccionado = fileChooser.showOpenDialog(rootUsers.getScene().getWindow());
            if (archivoSeleccionado != null) {
                try {
                    byte[] bytesImagen = java.nio.file.Files.readAllBytes(archivoSeleccionado.toPath());
                    usuarioActivo.setUserAvatar(bytesImagen);
                    
                    java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(bytesImagen);
                    imgAvatar.setImage(new Image(bais));
                    
                    // Guardamos la foto en la base de datos inmediatamente
                    UsersService srv = new UsersService();
                    UsersDto usuarioGuardado = srv.guardarUsuario(usuarioActivo);
                    if (usuarioGuardado != null) {
                        AppContext.getInstance().setUsuarioSeleccionado(usuarioGuardado);
                    }
                } catch (Exception e) {
                }
            }
            seleccionandoArchivo = false;
            txtModificarNombre.requestFocus();
        });

        // Listener para guardar el nombre automaticamente al hacer clic afuera
        txtModificarNombre.focusedProperty().addListener((observable, estabaEnfocado, estaEnfocado) -> {
            if (!estaEnfocado) {
                javafx.application.Platform.runLater(() -> {  
                    if (seleccionandoArchivo) {
                        return; 
                    }
                    String nuevoNombre = txtModificarNombre.getText().trim();
                    if (nuevoNombre.isEmpty()) {
                        finalizarModificacion(indiceLabel, txtModificarNombre);
                        return;
                    }
                   try {
         nombreAnterior = usuarioActivo.getUserName();
        UsersService srv = new UsersService();
        
        // asignamos temporalmente el nuevo nombre para pasarlo al backend
        usuarioActivo.setUserName(nuevoNombre);
        
        // invocamos el servicio. si hay duplicado, saltará directo al catch de abajo
        UsersDto usuarioGuardado = srv.guardarUsuario(usuarioActivo);
        
        // si llegó aquí es porque el backend procesó el cambio sin errores
        AppContext.getInstance().setUsuarioSeleccionado(usuarioGuardado);
        lblNombre.textProperty().unbind();
        lblNombre.textProperty().bind(usuarioGuardado.userNameProperty()); 
        System.out.println("LOG UI: Nombre de usuario actualizado con éxito.");

    } catch (IllegalArgumentException ex) {
        // 🛡️ el escudo del duplicado: revierte los cambios locales si saltó la excepción
        if ("NOMBRE_DUPLICADO".equals(ex.getMessage())) {
            usuarioActivo.setUserName(nombreAnterior); // devolvemos el nombre original al objeto gestionado
            
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alerta.setTitle(aletaNombre); // variable corregida
            alerta.setHeaderText(null);
            alerta.setContentText(nuevoNombre + alertaElejir);

            javafx.scene.control.ButtonType botonEntendido = new javafx.scene.control.ButtonType(aletaBtnAceptar);
            alerta.getButtonTypes().setAll(botonEntendido);

            javafx.scene.control.DialogPane dialogPane = alerta.getDialogPane();
            try {
                dialogPane.getStylesheets().add(getClass().getResource("/cr/ac/una/proyecto/view/Style.css").toExternalForm());
                dialogPane.getStyleClass().addAll("fx-FondoJuego11", "label-usuario");
            } catch (Exception e) {
                System.err.println("LOG UI: No se pudo inyectar el archivo css a la alerta.");
            }

            alerta.showAndWait();
        } else {
            System.err.println("LOG UI: Error de validación inesperado: " + ex.getMessage());
        }
    } catch (Exception e) {
        System.err.println("ERROR crítico del sistema al modificar el usuario: " + e.getMessage());
        e.printStackTrace();
    } finally {
        finalizarModificacion(indiceLabel, txtModificarNombre);
    }
                });
            }
        });
    }

private void finalizarModificacion(int indiceLabel, TextField txtModificarNombre) {
    if (seleccionandoArchivo) return;

    if (indiceLabel != -1 && rootUsers.getChildren().contains(txtModificarNombre)) {
        imgAvatar.setOnMousePressed(null);
        imgAvatar.setOnMouseClicked(null);
        imgAvatar.setStyle(null);
        rootUsers.getChildren().set(indiceLabel, lblNombre);
    }
}

    private void inicializarComponentesBase() {
    rootUsers.getChildren().clear();
    rootUsers.setStyle("-fx-alignment: CENTER; -fx-spacing: 15; -fx-padding: 10;");

    imgAvatar = new ImageView();

    byte[] bytesImagen = usuarioActivo.getUserAvatar();
    if (bytesImagen != null && bytesImagen.length > 0) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytesImagen);
        imgAvatar.setImage(new Image(bais));
    } else {
        imgAvatar.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/default.png")));
    }
    imgAvatar.setFitWidth(250);
    imgAvatar.setFitHeight(250);

    lblNombre = new Label();
    lblNombre.textProperty().bind(usuarioActivo.userNameProperty());
    lblNombre.getStyleClass().add("label-usuario1"); 
    rootUsers.getChildren().addAll(imgAvatar, lblNombre);
}
    
    
    @FXML
    private void onActionDeleteUser(ActionEvent event) {
        if (usuarioActivo == null) {
            return;
        }

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(aletaEliminar);
        alerta.setHeaderText(aletaAviso + usuarioActivo.getUserName() + "?");
        alerta.setContentText(aletaInfo);

        ButtonType btnSi = new ButtonType(aletaBtnEliminar);
        ButtonType btnNo = new ButtonType(aletaBtnCancelar);
        alerta.getButtonTypes().setAll(btnSi, btnNo);

        DialogPane dialogPane = alerta.getDialogPane();
        try {
            dialogPane.getStylesheets().add(getClass().getResource("/cr/ac/una/proyecto/view/Style.css").toExternalForm());
            dialogPane.getStyleClass().addAll("fx-FondoJuego11", "label-usuario");
        } catch (Exception e) {

        }
        alerta.getDialogPane().sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                javafx.application.Platform.runLater(() -> {
                    javafx.stage.Stage stage = (javafx.stage.Stage) newScene.getWindow();
                    stage.sizeToScene();
                });
            }
        });
        Optional<ButtonType> resultado = alerta.showAndWait();
        
        if (resultado.isPresent() && resultado.get() == btnSi) {
            try {
                // Borrado definitivo en la base de datos
                UsersService usersService = new UsersService();
                boolean eliminadoExitosamente = usersService.eliminarUsuarioCompleto(usuarioActivo);
                
                if (eliminadoExitosamente) {
                    AppContext.getInstance().setUsuarioSeleccionado(null);
                    this.usuarioActivo = null;
                    rootUsers.getChildren().clear();
                    volverAUsersConAnimacion();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void volverAUsersConAnimacion() {
        try {
            StackPane root = App.getRoot();
            double ancho = root.getWidth();
            double alto = root.getHeight();

            Parent newView = App.loadFXML("LoginView");
            root.getChildren().add(0, newView); 

            // Tomar una "captura de pantalla" a la nueva vista ya calculada
            SnapshotParameters parametros = new SnapshotParameters();
            parametros.setFill(Color.TRANSPARENT);
            WritableImage snapshotUsers = newView.snapshot(parametros, null);

            // Crear las "puertas" partiendo la captura a la mitad
            ImageView mitadIzq = new ImageView(snapshotUsers);
            mitadIzq.setViewport(new Rectangle2D(0, 0, ancho / 2, alto));
            
            ImageView mitadDer = new ImageView(snapshotUsers);
            mitadDer.setViewport(new Rectangle2D(ancho / 2, 0, ancho / 2, alto));

            mitadIzq.setCache(true);
            mitadDer.setCache(true);
            mitadIzq.setCacheHint(CacheHint.SPEED);
            mitadDer.setCacheHint(CacheHint.SPEED);

            Pane capaPuertas = new Pane(mitadIzq, mitadDer);
            root.getChildren().add(capaPuertas); 

            Duration duracion = Duration.millis(600);            
            TranslateTransition slideIzq = new TranslateTransition(duracion, mitadIzq);
            slideIzq.setFromX(-ancho / 2); 
            slideIzq.setToX(0); 
            slideIzq.setInterpolator(Interpolator.EASE_OUT);

            TranslateTransition slideDer = new TranslateTransition(duracion, mitadDer);
            slideDer.setFromX(ancho); 
            slideDer.setToX(ancho / 2); 
            slideDer.setInterpolator(Interpolator.EASE_OUT);

            ParallelTransition transicionTotal = new ParallelTransition(slideIzq, slideDer);
            
            transicionTotal.setOnFinished(e -> {
                root.getChildren().remove(1); 
                root.getChildren().remove(capaPuertas);
                mitadIzq.setImage(null);
                mitadDer.setImage(null);
            });
            transicionTotal.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onActionCompleta(ActionEvent event) {
        javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        if (!stage.isFullScreen()) {
            stage.setFullScreen(true);
            stage.setFullScreenExitHint(""); 
        }
        usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
        if (usuarioActivo == null) return;
        
        List<UsersConfigDto> listaConfigs = usuarioActivo.getUsersConfigList();
        if (listaConfigs != null && !listaConfigs.isEmpty()) {
            UsersConfigDto configuracionActual = listaConfigs.get(0);
            
            if (configuracionActual.getUsersConfigGeneral() != null) {
                configuracionActual.getUsersConfigGeneral().setUserScreen("C");
                usersService.guardarUsuario(usuarioActivo); 
            }
        }
    }

    @FXML
    private void onActionConBordes(ActionEvent event) {
        javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setFullScreen(false);
        stage.setMaximized(true);

        usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
        if (usuarioActivo == null) return;

        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
            
            if (configuracionActual.getUsersConfigGeneral() != null) {
                configuracionActual.getUsersConfigGeneral().setUserScreen("B");
                usersService.guardarUsuario(usuarioActivo); 
            }
        }
    }

    public void cargarConfiguracionDePantalla(javafx.stage.Stage stage) {
        UsersDto usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
        if (usuarioActivo == null) return;

        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
            
            if (configuracionActual.getUsersConfigGeneral() != null) {
                String tipoPantalla = configuracionActual.getUsersConfigGeneral().getUserScreen();
                
                if (tipoPantalla == null) {
                    tipoPantalla = "B";
                }
                // Sincronización de los controles
                if (tipoPantalla.equals("C")) {
                    radioCompleta.setSelected(true);
                } else {
                    radioConBordes.setSelected(true);
                }

                if (stage != null) {
                    if (tipoPantalla.equals("C")) {
                        stage.setFullScreen(true);
                        stage.setFullScreenExitHint("");
                    } else {
                        stage.setFullScreen(false);
                        stage.setMaximized(true);
                    }
                }
            }
        }
    }

    @FXML
    private void onActionGun1Sprite(ActionEvent event) {
        usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
        if (usuarioActivo == null) return;

        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
            
            if (configuracionActual.getUsersConfigGeneral() != null) {
                configuracionActual.getUsersConfigGeneral().setUserStylegun("A");
                usersService.guardarUsuario(usuarioActivo);
            }
        }
    }

    @FXML
    private void onActionGun2Sprite(ActionEvent event) {
        usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
        if (usuarioActivo == null) return;

        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
            
            if (configuracionActual.getUsersConfigGeneral() != null) {
                configuracionActual.getUsersConfigGeneral().setUserStylegun("M");
                usersService.guardarUsuario(usuarioActivo);
            }
        }
    }

    @FXML
    private void onActionEasyMode(ActionEvent event) {
        usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
        if (usuarioActivo == null) return;
        
        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
            
            if (configuracionActual.getUsersConfigGeneral() != null) {
                if (CheckEasyMode.isSelected()) {
                    configuracionActual.getUsersConfigGeneral().setUserGamemode("E");
                } else {
                    configuracionActual.getUsersConfigGeneral().setUserGamemode("N");
                }
                usersService.guardarUsuario(usuarioActivo);
            }
        }
    }

    @FXML
    private void onActionKeyBoard(ActionEvent event) {
        usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
        if (usuarioActivo == null) return;

        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
            
            if (configuracionActual.getUsersConfigGeneral() != null) {
                configuracionActual.getUsersConfigGeneral().setUserController("K");
                usersService.guardarUsuario(usuarioActivo);
            }
        }
    }

    @FXML
    private void onActionMouse(ActionEvent event) {
        usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
        if (usuarioActivo == null) return;

        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
            
            if (configuracionActual.getUsersConfigGeneral() != null) {
                configuracionActual.getUsersConfigGeneral().setUserController("M");
                usersService.guardarUsuario(usuarioActivo);
            }
        }
    }

    
    private void configurarListenersDeAudio() {
        usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
        if (usuarioActivo == null || usuarioActivo.getUsersConfigList().isEmpty()) {
            return;
        }
        
        var configGeneral = usuarioActivo.getUsersConfigList().get(0).getUsersConfigGeneral();
        if (configGeneral == null) {
            return;
        }

        // 1. Audio Menu
        SliderMenu.valueChangingProperty().addListener((obs, antesCambiaba, ahoraCambia) -> {
            SoundManager.setmenuVolume(SliderMenu.getValue());
            
            if (!ahoraCambia) { 
                configGeneral.setUserAudiomenu(SliderMenu.getValue());
                usersService.guardarUsuario(usuarioActivo);
            }
        });

        // 2. Audio Enemigos
        SliderEnemy.valueChangingProperty().addListener((obs, antesCambiaba, ahoraCambia) -> {
            SoundManager.getEnemyVolumeProperty().set(SliderEnemy.getValue());
            
            if (!ahoraCambia) {
                configGeneral.setUserAudioenemys(SliderEnemy.getValue());
                usersService.guardarUsuario(usuarioActivo);
            }
        });

        // 3. Audio Efectos
        SliderEfect.valueChangingProperty().addListener((obs, antesCambiaba, ahoraCambia) -> {
            SoundManager.getEfectVolumeProperty().set(SliderEfect.getValue());
            
            if (!ahoraCambia) {
                configGeneral.setUserAudioefect(SliderEfect.getValue());
                usersService.guardarUsuario(usuarioActivo);
            }
        });

        // 4. Audio Arma
        SliderGun.valueChangingProperty().addListener((obs, antesCambiaba, ahoraCambia) -> {
            SoundManager.getGunVolumeProperty().set(SliderGun.getValue());
            
            if (!ahoraCambia) {
                configGeneral.setUserAudiogun(SliderGun.getValue());
                usersService.guardarUsuario(usuarioActivo);
            }
        });
    }

    @FXML
    private void onActionEspanol(ActionEvent event) {
        usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
        if (usuarioActivo == null) return;
        
        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
            
            if (configuracionActual.getUsersConfigGeneral() != null) {
                configuracionActual.getUsersConfigGeneral().setUserLanguage("S");
                usersService.guardarUsuario(usuarioActivo);
            }
        }
        
        aplicarIdiomaEnPantalla("S");
    }

    @FXML
    private void onActionIngles(ActionEvent event) {
        usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
        if (usuarioActivo == null) return;
        
        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
            
            if (configuracionActual.getUsersConfigGeneral() != null) {
                configuracionActual.getUsersConfigGeneral().setUserLanguage("I");
                usersService.guardarUsuario(usuarioActivo);
            }
        }
        
        aplicarIdiomaEnPantalla("I");
    }
    
// CAmbia todos los Labes por ingles o español
  private void aplicarIdiomaEnPantalla(String idioma) {
    if ("I".equalsIgnoreCase(idioma)) {
        lblTitulo.setText("Options: ");
        btnMenu.setText("Main Menu");
        tabJuego.setText("Game");
        lblGunD.setText("Weapon Design");
        lblControles.setText("Weapon Controls");
        lblPantalla.setText("Screen Size");
        lblIdioma.setText("Language");
        CheckEasyMode.setText("Easy Mode");
       
        tabUsuarios.setText("Users");
        btnEditar.setText("Edit");
        btnEliminar.setText("Delete");
        
        tabAudio.setText("Audio");
        lblMenuV.setText("Menus");
        lblEnemigosV.setText("Enemys");
        lblEfectoV.setText("Effects");
        lblArmaV.setText("Gun");
        
        tabInformacion.setText("information");
        lblCurso.setText("COURSE: PROGRAMMING II");
        lblEstudiantes.setText("Students: Joshua Godínez Barrios, Brandon Quiros Arroyo");
        lblProfesor.setText("PROFESSOR: CARLOS CARRANZA BLANCO");
        lblCiclo.setText("Academic Year: CYCLE I");
        LblYear.setText("Year: 2026");
        BtnBack.setText("Previous");
        BtnNext.setText("Next"); 
      lblTutorial.setText("Tutorial");
         btnComoJugar.setText("How to Play");
        
        textosTutorial = java.util.Arrays.asList(
    "The enemies approach in different waves and types.",
    "The base's life will decrease with enemy attacks, and the elixir will recover over time.",
    "The weapon is the main tool for eliminating enemies, and you can also change its style and controls.",
    "The powers allow you to damage or freeze a large number of enemies in an area.",
    "The upgrades allow the entire objects to level up, increasing their stats."
);
            aletaNombre="Duplicate Name";
    alertaElejir=" it already exists, try a different one.";
    aletaEliminar="Delete User";
    aletaAviso="Are you sure you want to delete ";
    aletaInfo="This action will delete all user progress.";
    aletaBtnAceptar="Agree";
    aletaBtnEliminar ="Delete";     
    aletaBtnCancelar= "Cancel"; 
    radioA.setText("Blue");
    radioM.setText("Yellow");
    radioKeybord.setText("Keyboard");
    radioMouse.setText("Mouse");
    radioCompleta.setText("Complete");
    radioConBordes.setText("With Borders");
    radioIngles.setText("English");
    radioEspanol.setText("Spanish");
    
    } else {
        lblTitulo.setText("Opciones: ");
        btnMenu.setText("Menu Principal");
        tabJuego.setText("Juego");
        lblGunD.setText("Diseño de Arma");
        lblControles.setText("Controles del Arma");
        lblPantalla.setText("Tamaño de Pantalla");
        lblIdioma.setText("Idioma");
        CheckEasyMode.setText("Modo Facil");
       
        tabUsuarios.setText("Usuarios");
        btnEditar.setText("Editar");
        btnEliminar.setText("Eliminar");
        
        tabAudio.setText("Audio");
        lblMenuV.setText("Menus");
        lblEnemigosV.setText("Enemigos");
        lblEfectoV.setText("Efectos");
        lblGunD.setText("Diseño del Arma:");
        
        tabInformacion.setText("Información");
        lblCurso.setText("CURSO: PROGRAMACIÓN II");
        lblEstudiantes.setText("Estiduantes: Joshua Godínez Barrios, Brandon Quiros Arroyo");
        lblProfesor.setText("PROFESOR: CARLOS CARRANZA BLANCO");
        lblCiclo.setText("Ciclo Lectivo: I CICLO");
        LblYear.setText("Año: 2026");
        BtnBack.setText("Anterior");
        BtnNext.setText("Siguiente"); 
      lblTutorial.setText("Tutorial");
        btnComoJugar.setText("Como Jugar");
        
       textosTutorial = java.util.Arrays.asList(
    "Los enemigos se acercan en diferentes oleadas y tipos.",
    "La vida de la base disminuira con el ataque de enemigos y el elixir se recuperara con el tiempo.",
    "El Arma es la principal herramienta para eliminar a los enemigos, ademas de poder cambiar de estilo y controles.",
    "Los poderes permiten dañar o congelar a gran cantidad de enemigos en area.",
    "Las mejoras haran que todo tu objetos pueda subir de nivel aumentando sus estadisticas"
);

     radioA.setText("Azul");
    radioM.setText("Amarillo");
    radioKeybord.setText("Taclado");
    radioMouse.setText("Raton");
    radioCompleta.setText("Completa");
    radioConBordes.setText("Con Bordes");
    radioIngles.setText("Ingles");
    radioEspanol.setText("Español");    
    }
    
   
}
private void cargarDatosDesdeBD() {
        try {
            usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
            if (usuarioActivo == null || usuarioActivo.getUserId() == null) {
                return;
            }
            UsersService srv = new UsersService();
            UsersDto usuarioFresco = srv.obtenerUsuario(usuarioActivo.getUserId());
            
            if (usuarioFresco != null && usuarioFresco.getUsersConfigList() != null && !usuarioFresco.getUsersConfigList().isEmpty()) {
                AppContext.getInstance().setUsuarioSeleccionado(usuarioFresco);
                this.usuarioActivo = usuarioFresco;
                
                UsersConfigLevelDto configNivelActivo = usuarioFresco.getUsersConfigList().get(0).getUsersConfigLevel();             
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
}
