package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.App;
import cr.ac.una.proyecto.model.UsersConfigLevelDto;
import cr.ac.una.proyecto.model.UsersDto;
import cr.ac.una.proyecto.service.UsersService;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.SoundManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class HomeController implements Initializable {
    @FXML
    private StackPane rootHome;
    @FXML
    private Label level;
    @FXML
    private Label points;
    @FXML
    private Button btnJugar;
    @FXML
    private Button btnMejoras;
    @FXML
    private Button btnOpciones;
    @FXML
    private Button btnUsuarios;
    @FXML
    private Button btnSalir;

    @FXML
    private VBox rootTopUsers;
    @FXML
    private Label lblTopUsers;
    @FXML
    private ImageView ImageUser;
    @FXML
    private Label nameUser;
    @FXML
    private Label levelUser;
    @FXML
    private Label pointsUser;

    @FXML
    private VBox rootHowPlay;
    @FXML
    private Label lblTutorial;
    @FXML
    private ImageView ImageTutorial;
    @FXML
    private Label LblImage;
    @FXML
    private Button BtnBack;
    @FXML
    private Button BtnNext;

    private UsersDto usuarioActivo;
    private UsersConfigLevelDto configNivelActivo;
    private Label lblNivel = new Label("");

    private int indexTutorial = 0;

    private java.util.List<String> rutasImagenes = java.util.Arrays.asList(
        "/cr/ac/una/proyecto/resources/tutorial4.png",
        "/cr/ac/una/proyecto/resources/tutorial2.png",
        "/cr/ac/una/proyecto/resources/tutorial1.png",
        "/cr/ac/una/proyecto/resources/tutorial3.png",
        "/cr/ac/una/proyecto/resources/tutorial5.png"
    );

    private java.util.List<String> textosTutorial = java.util.Arrays.asList(
        "Los enemigos se acercan en diferentes oleadas y tipos.",
        "La vida de la base disminuye con el ataque de enemigos y el elixir se recuperara con el tiempo.",
        "El Arma es la principal herramienta para eliminar a los enemigos, ademas de poder cambiar de estilo y controles.",
        "Los poderes permiten dañar o congelar a gran cantidad de enemigos en area.",
        "Las mejoras hacen que todo el equipo puede subir de nivel aumentando sus estadisticas"
    );

    private String aletaAviso = "Cerrar Juego";
    private String aletaInfo = "¿Estás seguro de que deseas salir del juego?";
    private String aletaBtnSalir = "Salir";
    private String aletaBtnCancelar = "Cancelar";
    private String prefijoNivel= "Nivel: ";
  @Override
    public void initialize(URL url, ResourceBundle rb) {
        SoundManager.stopFondo1Sound2();
        SoundManager.playFondo1Sound();
        cargarTopUsuarios();
        cargarDatosUsuarioEnPantalla();
        cargarDatosDesdeBD();
        LblImage.setWrapText(true);
        cargarVolumenesGuardados();
    }    
    
    public void cargarVolumenesGuardados() {
        // El usuario activo se inicializa con el de login
        usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
        
        if (usuarioActivo != null && usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            var configGeneral = usuarioActivo.getUsersConfigList().get(0).getUsersConfigGeneral();
            
            if (configGeneral != null) {
                SoundManager.setmenuVolume(configGeneral.getUserAudiomenu());
                SoundManager.getEnemyVolumeProperty().set(configGeneral.getUserAudioenemys());
                SoundManager.getEfectVolumeProperty().set(configGeneral.getUserAudioefect());
                SoundManager.getGunVolumeProperty().set(configGeneral.getUserAudiogun());

            }
        }
    }
       // Carga los datos de pantalla y idioma
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
              
                var configGeneral = usuarioFresco.getUsersConfigList().get(0).getUsersConfigGeneral();
                if (configGeneral != null) {

                    if ("S".equalsIgnoreCase(configGeneral.getUserFirsttime())) {
                        indexTutorial = 0; 
                        actualizarPasoTutorial(); 
                        rootHowPlay.setVisible(true);
                    } else {
                        rootHowPlay.setVisible(false);
                    }
     // Verificacion del tamaño de la pantalla
                  if (rootHome.getScene() == null) {
    rootHome.sceneProperty().addListener((observable, oldScene, newScene) -> {
        //cuanto entre en el menu
        if (newScene != null && newScene.getWindow() != null) {
            javafx.stage.Stage stage = (javafx.stage.Stage) newScene.getWindow();

            if ("C".equalsIgnoreCase(configGeneral.getUserScreen())) {
                stage.setFullScreen(true);
            } else {
                stage.setFullScreen(false);
            }
        }
    });
       } else {
            if (rootHome.getScene().getWindow() != null) {
        javafx.stage.Stage stage = (javafx.stage.Stage) rootHome.getScene().getWindow();
           if ("C".equalsIgnoreCase(configGeneral.getUserScreen())) {
            stage.setFullScreen(true);
          } else {
            stage.setFullScreen(false);
         }
           }
        }
                    // Verificacion de idioma
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

    private void cargarDatosUsuarioEnPantalla() {
         usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();

        if (usuarioActivo == null) {
            return;
        }
        try {
            nameUser.setText(usuarioActivo.getUserName());
            byte[] bytesImagen = usuarioActivo.getUserAvatar();
            
            if (bytesImagen != null && bytesImagen.length > 0) {
                ByteArrayInputStream bais = new ByteArrayInputStream(bytesImagen);
                ImageUser.setImage(new Image(bais));
            } else {
                String rutaDefecto = "/cr/ac/una/proyecto/resources/default.png";
                if (getClass().getResource(rutaDefecto) != null) {
                    ImageUser.setImage(new Image(getClass().getResourceAsStream(rutaDefecto)));
                } else {
                }
            }

            if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
                var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
                if (configNivel != null) {
                    levelUser.setText(String.valueOf(configNivel.getUserLevel()));
                    pointsUser.setText(String.valueOf(configNivel.getUserPoints()));
                }
            } else {
                levelUser.setText("Nivel: 1");
                pointsUser.setText("Puntos: 0");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onActionGame(ActionEvent event) {
        ejecutarTransicionPantalla("GameView", btnJugar, true, false);
    }

    @FXML
    private void onActionUpgrades(ActionEvent event) {
        ejecutarTransicionPantalla("UpgradesView", btnMejoras, false, true);
    }

    @FXML
    private void onActionSetting(ActionEvent event) {
        ejecutarTransicionPantalla("SettingView", btnOpciones, false, false);
    }

    // Metodo auxiliar para unificar las tres transiciones repetitivas en una sola funcion limpia
    private void ejecutarTransicionPantalla(String fxml, Button boton, boolean EjeX, boolean esNegativo) {
        try {
            Parent newView = App.loadFXML(fxml);
            StackPane root = App.getRoot();
            boton.setDisable(true);

            if (EjeX) {
                newView.setTranslateX(root.getWidth());
            } else {
              
                if (esNegativo) {
                newView.setTranslateY(-root.getHeight());
           } else {
                 newView.setTranslateY(root.getHeight());
                  }
            }

            root.getChildren().add(newView);

            TranslateTransition slide = new TranslateTransition(javafx.util.Duration.millis(800), newView);
            if (EjeX) slide.setToX(0); else slide.setToY(0);

            slide.setOnFinished(e -> {
                root.getChildren().remove(0); 
                boton.setDisable(false);
            });

            slide.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   @FXML
    private void onActionUsers(ActionEvent event) throws IOException {
        volverAUsersConAnimacion();
    }
     
    private void volverAUsersConAnimacion() {
        try {
            StackPane root = App.getRoot();
            double ancho = root.getWidth();
            double alto = root.getHeight();

            Parent newView = App.loadFXML("LoginView");
            root.getChildren().add(0, newView);

            // Animacion para que se una la pantalla
            SnapshotParameters parametros = new SnapshotParameters();
            parametros.setFill(Color.TRANSPARENT);
            WritableImage snapshotUsers = newView.snapshot(parametros, null);

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
            
            // Controla la animación
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

public void cargarTopUsuarios() {
        try {
            rootTopUsers.getChildren().clear(); 
            UsersService service = new UsersService();
            List<UsersDto> listaUsuarios = service.obtenerUsuarios();

            if (listaUsuarios == null || listaUsuarios.isEmpty()) {
                return;
            }

            // Ordena la lista de usuarios de mayor a menor nivel
            listaUsuarios.sort((u1, u2) -> {
                Long n1;
                Long n2;
                // Buscamos el nivel del primer usuario
                if (u1.getUsersConfigList() != null && !u1.getUsersConfigList().isEmpty()) {
                    n1 = u1.getUsersConfigList().get(0).getUsersConfigLevel().getUserLevel();
                } else {
                    n1 = 1L;
                }
                // Buscamos el nivel del segundo usuario
                if (u2.getUsersConfigList() != null && !u2.getUsersConfigList().isEmpty()) {
                    n2 = u2.getUsersConfigList().get(0).getUsersConfigLevel().getUserLevel();
                } else {
                    n2 = 1L;
                }
                // Comparamos al reves (n2 contra n1) para lograr el orden descendente
                return n2.compareTo(n1); 
            });

            int posicion = 1;
            String rutaDefecto = "/cr/ac/una/proyecto/resources/default.png";


            // Crea las filas del Top de jugadores en la pantalla
            for (UsersDto dto : listaUsuarios) {
                HBox filaUser = new HBox(15);
                filaUser.setAlignment(Pos.CENTER_LEFT);
                filaUser.getStyleClass().addAll("fx-FondoJuego11", "label-usuario");
                filaUser.setStyle(filaUser.getStyle() + "; -fx-padding: 10 15 10 15;");

                Label lblPosicion = new Label("#" + posicion);
                lblPosicion.getStyleClass().add("label-usuario");
                
                // Si esta en el top 3 se aplica un diseño mas grande
                if (posicion <= 3) {
                    lblPosicion.setStyle("-fx-font-weight: bold; -fx-min-width: 30; -fx-alignment: center; -fx-text-fill: #ffcc00; -fx-font-size: " + (24 - (posicion * 2)) + "px;");
                } else {
                    lblPosicion.setStyle("-fx-font-weight: bold; -fx-min-width: 30; -fx-alignment: center; -fx-text-fill: #888888; -fx-font-size: 16px;");
                }

                // Cargamos el avatar desde los bytes de Oracle o la imagen por defecto
                ImageView avatar = new ImageView();
                byte[] bytesImagen = dto.getUserAvatar();

                if (bytesImagen != null && bytesImagen.length > 0) {
                    avatar.setImage(new Image(new ByteArrayInputStream(bytesImagen)));
                } else {
                    avatar.setImage(new Image(getClass().getResourceAsStream(rutaDefecto)));
                }

                avatar.setFitWidth(35);
                avatar.setFitHeight(35);
                avatar.setPreserveRatio(true);

                Label lblNombre = new Label();
                lblNombre.textProperty().bind(dto.userNameProperty());
                lblNombre.getStyleClass().add("label-usuario");
                lblNombre.setStyle(lblNombre.getStyle() + "; -fx-font-size: 16px;");

                Region espaciador = new Region();
                HBox.setHgrow(espaciador, Priority.ALWAYS);

                Long nivelActual;
                if (dto.getUsersConfigList() != null && !dto.getUsersConfigList().isEmpty()) {
                    nivelActual = dto.getUsersConfigList().get(0).getUsersConfigLevel().getUserLevel();
                } else {
                    nivelActual = 1L;
                }

                Label lblNivelItem = new Label(prefijoNivel + nivelActual);
                lblNivelItem.setStyle("-fx-text-fill: #ffcc00; -fx-font-weight: bold; -fx-font-size: 16px;");
                
                // Armamos la estructura de la fila horizontal y la metemos al panel vertical
                filaUser.getChildren().addAll(lblPosicion, avatar, lblNombre, espaciador, lblNivelItem);
                rootTopUsers.getChildren().add(filaUser);
                
                posicion++;
            }
        } catch (Exception e) {
            System.err.println("error al cargar el top de usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }
      // Boton salir con Alerta
    @FXML
    private void onActionOut(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(aletaAviso);
        alert.setHeaderText(aletaInfo);

        ButtonType botonAceptar = new ButtonType(aletaBtnSalir);
        ButtonType botonCancelar = new ButtonType(aletaBtnCancelar);
        alert.getButtonTypes().setAll(botonAceptar, botonCancelar);

        DialogPane dialogPane = alert.getDialogPane();
        try {
            dialogPane.getStylesheets().add(getClass().getResource("/cr/ac/una/proyecto/view/Style.css").toExternalForm());
            dialogPane.getStyleClass().addAll("fx-FondoJuego11", "label-usuario");
        } catch (Exception e) {
        }

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == botonAceptar) {
            javafx.application.Platform.exit();
            System.exit(0);
        }
    }

    @FXML
    private void onActionBack(ActionEvent event) {
        if (indexTutorial > 0) {
            indexTutorial--;
            actualizarPasoTutorial();
        } else {
            SoundManager.playErrorSound(); 
        }
    }
      // Boton siguente del tuturial guarda N en la base de datos
    @FXML
    private void onActionNext(ActionEvent event) {
        if (indexTutorial >= rutasImagenes.size() - 1) {
            rootHowPlay.setVisible(false); 
            
            if (usuarioActivo != null && usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
                var configGeneral = usuarioActivo.getUsersConfigList().get(0).getUsersConfigGeneral();
                
                if (configGeneral != null && "S".equalsIgnoreCase(configGeneral.getUserFirsttime())) {
                    configGeneral.setUserFirsttime("N");
                    
                    try {
                        UsersService usersService = new UsersService();
                        usersService.guardarUsuario(usuarioActivo); 
                    } catch (Exception e) {
                    }
                }
            }
        } else {
            SoundManager.playBotonSound();
            indexTutorial++;
            actualizarPasoTutorial();
        }
    }

    private void actualizarPasoTutorial() {
        LblImage.setText(textosTutorial.get(indexTutorial));
        try {
            String ruta = rutasImagenes.get(indexTutorial);
            ImageTutorial.setImage(new Image(getClass().getResourceAsStream(ruta)));
        } catch (Exception e) {
        }
    }
 // Cambia todos lo labes de idioma
    private void aplicarIdiomaEnPantalla(String idioma) {
        if ("I".equalsIgnoreCase(idioma)) {
            level.setText("Level: ");
            points.setText("Points: ");
            btnJugar.setText("Play");
            btnMejoras.setText("Upgrades");
            btnOpciones.setText("Options");
            btnUsuarios.setText("Users");
            btnSalir.setText("Leave");
            lblTopUsers.setText("Top Players");
            BtnBack.setText("Previous");
            BtnNext.setText("Next"); 
            lblTutorial.setText("Welcome! Tutorial");
            
            textosTutorial.set(0, "The enemies approach in different waves and types.");
            textosTutorial.set(1, "The base's life will decrease with enemy attacks, and the elixir will recover over time.");
            textosTutorial.set(2, "The weapon is the main tool for eliminating enemies, and you can also change its style and controls.");
            textosTutorial.set(3, "The powers allow you to damage or freeze a large number of enemies in an area.");
            textosTutorial.set(4, "The upgrades allow the entire team to level up, increasing their stats.");
     
            aletaAviso = "Close Game";
            aletaInfo = "Are you sure you want to quit the game?";
            aletaBtnSalir = "Leave";
            aletaBtnCancelar = "Cancel"; 
            prefijoNivel= "Level: "; 
        }
    }
}