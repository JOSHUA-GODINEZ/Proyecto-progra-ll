package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.App;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomeController implements Initializable {


    @FXML
    private VBox rootTopUsers;
    @FXML
    private ImageView ImageUser;
    @FXML
    private Label nameUser;
    @FXML
    private Label levelUser;
    @FXML
    private Label pointsUser;

private UsersDto usuarioActivo;
 private UsersConfigLevelDto configNivelActivo;
    @FXML
    private VBox rootHowPlay;
    @FXML
    private ImageView ImageTutorial;
    @FXML
    private Label LblImage;
    @FXML
    private Button BtnBack;
private int indexTutorial = 0;
private final java.util.List<String> rutasImagenes = java.util.Arrays.asList(
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
    private Label lblTopUsers;
    Label lblNivel= new Label("");
    @FXML
    private Label lblTutorial;
    @FXML
    private Button BtnNext;
          private String aletaAviso= "Cerrar Juego";
       private String aletaInfo= "¿Estás seguro de que deseas salir del juego?";
       private String aletaBtnSalir= "Salir";
       private String aletaBtnCancelar= "Cancelar";
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
 private void cargarDatosDesdeBD() {
    try {
        usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
        
        if (usuarioActivo != null && usuarioActivo.getUserId() != null) {
            UsersService usersService = new UsersService();
            
            // 1. Forzamos la descarga limpia desde Oracle
            UsersDto usuarioFresco = usersService.obtenerUsuario(usuarioActivo.getUserId());
            
            if (usuarioFresco != null && usuarioFresco.getUsersConfigList() != null 
                    && !usuarioFresco.getUsersConfigList().isEmpty()) {
                
                // 2. Sincronizamos las variables del controlador y el contexto global
                AppContext.getInstance().setUsuarioSeleccionado(usuarioFresco);
                this.usuarioActivo = usuarioFresco;
                this.configNivelActivo = usuarioFresco.getUsersConfigList().get(0).getUsersConfigLevel();
                
                System.out.println("LOG MEJORAS: Datos de mejoras descargados con éxito de Oracle.");

            // ======================================================================
                // 🔥 4. VALIDACIÓN DESDE USER_CONFIG_GENERAL (Tutorial, Pantalla e Idioma)
                // ======================================================================
                var configGeneral = usuarioFresco.getUsersConfigList().get(0).getUsersConfigGeneral();
                
                if (configGeneral != null) {
                    
                    // A) VERIFICACIÓN DE PRIMERA VEZ (TUTORIAL)
                    if ("S".equalsIgnoreCase(configGeneral.getUserFirsttime())) {
                        System.out.println("LOG UI: Primera vez detectada. Abriendo tutorial...");
                        indexTutorial = 0; 
                        actualizarPasoTutorial(); 
                        rootHowPlay.setVisible(true);
                    } else {
                        rootHowPlay.setVisible(false);
                    }

              // B) VERIFICACIÓN DE PANTALLA COMPLETA
                    // Creamos una función pequeña que se encarga de aplicar el cambio
                    Runnable aplicarPantallaCompleta = () -> {
                        if (rootHome.getScene() != null && rootHome.getScene().getWindow() != null) {
                            javafx.stage.Stage stage = (javafx.stage.Stage) rootHome.getScene().getWindow();
                            
                            System.out.println("LOG SCREEN: Evaluando valor de BD -> [" + configGeneral.getUserScreen() + "]");
                            
                            if ("C".equalsIgnoreCase(configGeneral.getUserScreen())) {
                                System.out.println("LOG SCREEN: ¡Activando Pantalla Completa con éxito!");
                                stage.setFullScreen(true);
                            } else {
                                System.out.println("LOG SCREEN: Modo Ventana activado.");
                                stage.setFullScreen(false);
                            }
                        }
                    };

                    // 🕵️‍♂️ REVISIÓN MÁGICA:
                    if (rootHome.getScene() == null) {
                        // Si es null, esperamos pacientemente a que JavaFX conecte la escena
                        System.out.println("LOG SCREEN: La escena es null temporalmente. Agregando escucha...");
                        rootHome.sceneProperty().addListener((observable, oldScene, newScene) -> {
                            if (newScene != null) {
                                // En el instante en que aparece la escena, usamos runLater para evitar bloqueos
                                javafx.application.Platform.runLater(aplicarPantallaCompleta);
                            }
                        });
                    } else {
                        // Si por el contrario la escena ya existía, se aplica de inmediato
                        javafx.application.Platform.runLater(aplicarPantallaCompleta);
                    }

                    // C) VERIFICACIÓN DE IDIOMA
                    // Supongamos que devuelve "ES" o "EN"
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
private void cargarDatosUsuarioEnPantalla() {
    // 1. Obtenemos el usuario que está actualmente seleccionado en el juego
    UsersDto usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();

    if (usuarioActivo != null) {
        try {
            // 2. Cargamos los datos básicos directamente del DTO principal
            nameUser.setText(usuarioActivo.getUserName());
            
            // 3. 🔥 NUEVO PASO 3: Cargamos la imagen directamente desde los bytes (BLOB) de Oracle
            byte[] bytesImagen = usuarioActivo.getUserAvatar();
            
            if (bytesImagen != null && bytesImagen.length > 0) {
                // El truco: Transformamos el arreglo de bytes en un flujo que JavaFX lee en memoria
                ByteArrayInputStream bais = new ByteArrayInputStream(bytesImagen);
                Image img = new Image(bais);
                ImageUser.setImage(img);
                System.out.println("LOG UI: Avatar cargado con éxito desde la Base de Datos.");
            } else {
                // Imagen de respaldo por defecto si el usuario no tiene ninguna guardada en Oracle
                String rutaDefecto = "/cr/ac/una/proyecto/resources/default.png"; // Asegúrate de que esta ruta exista
                if (getClass().getResource(rutaDefecto) != null) {
                    Image imgDefecto = new Image(getClass().getResourceAsStream(rutaDefecto));
                    ImageUser.setImage(imgDefecto);
                    System.out.println("LOG UI: El usuario no tiene avatar en BD, se cargó el avatar por defecto.");
                } else {
                    System.err.println("LOG UI: No se encontró ni la imagen por defecto en la ruta: " + rutaDefecto);
                }
            }

            // 4. Accedemos a la lista de configuración para extraer el nivel y los puntos
            if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
                
                var configNivel = usuarioActivo.getUsersConfigList().get(0).getUsersConfigLevel();
                
                if (configNivel != null) {
                    // Seteamos los textos usando String.valueOf() o bindeándolos directamente
                    levelUser.setText(String.valueOf(configNivel.getUserLevel()));
                    pointsUser.setText(String.valueOf(configNivel.getUserPoints()));
                    
                    System.out.println("LOG UI: Datos de nivel cargados con éxito para " + usuarioActivo.getUserName());
                }
            } else {
                // Escudo protector si la lista viene vacía (ej: usuario recién creado sin inyectar)
                levelUser.setText("Nivel: 1");
                pointsUser.setText("Puntos: 0");
                System.out.println("LOG UI: Lista vacía, seteando valores por defecto (Nivel 1, 0 pts).");
            }

        } catch (Exception e) {
            System.err.println("LOG UI: Error crítico al renderizar los componentes del usuario: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.err.println("LOG UI: ¡Error! No hay ningún usuario activo en el AppContext.");
    }
}
    @FXML
    private void onActionGame(ActionEvent event) {
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
    //  SoundManager.stop();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void onActionUpgrades(ActionEvent event) {
            try {
        Parent newView = App.loadFXML("UpgradesView");

        StackPane root = App.getRoot();

        newView.setTranslateY(-root.getHeight());

        root.getChildren().add(newView);
        btnMejoras.setDisable(true);
         TranslateTransition slide = new TranslateTransition(Duration.millis(800), newView);
        slide.setToY(0);

        slide.setOnFinished(e -> {
            root.getChildren().remove(0);
             btnMejoras.setDisable(false);
        });

        slide.play();

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void onActionSetting(ActionEvent event) {
         try {
        Parent newView = App.loadFXML("SettingView");

        StackPane root = App.getRoot();

        newView.setTranslateY(root.getHeight());

        root.getChildren().add(newView);

        TranslateTransition slide = new TranslateTransition(Duration.millis(800), newView);
        slide.setToY(0);
        btnOpciones.setDisable(true);
        slide.setOnFinished(e -> {
            root.getChildren().remove(0);
                btnOpciones.setDisable(false);
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
        // 1. Obtener el StackPane principal de tu aplicación
        StackPane root = App.getRoot();
        double ancho = root.getWidth();
        double alto = root.getHeight();

        // 2. Opcional: Detener música del Home
        try {
            // SoundManager.stopMusic(); 
        } catch (Exception ex) {
            System.err.println("No se pudo detener la música: " + ex.getMessage());
        }

        // 3. 🔥 EL TRUCO DE RENDERIZADO ASÍNCRONO:
        // Cargamos la vista de LoginView
        Parent newView = App.loadFXML("LoginView");

        // Lo metemos en el índice 0 (AL FONDO). 
        // Como el HomeView actual está encima, el usuario NO ve ningún parpadeo,
        // pero obligamos a JavaFX a renderizar los componentes de Users en memoria.
        root.getChildren().add(0, newView);

        // 4. Tomar la "foto" de la vista de LoginView (que ya está calculada en el fondo)
        SnapshotParameters parametros = new SnapshotParameters();
        parametros.setFill(Color.TRANSPARENT);
        WritableImage snapshotUsers = newView.snapshot(parametros, null);

        // 5. CREAR LAS PUERTAS CON LA FOTO DE USERS (Inician AFUERA de la pantalla)
        ImageView mitadIzq = new ImageView(snapshotUsers);
        mitadIzq.setViewport(new Rectangle2D(0, 0, ancho / 2, alto));
        
        ImageView mitadDer = new ImageView(snapshotUsers);
        mitadDer.setViewport(new Rectangle2D(ancho / 2, 0, ancho / 2, alto));

        // Forzar la aceleración por tarjeta de video (GPU)
        mitadIzq.setCache(true);
        mitadDer.setCache(true);
        mitadIzq.setCacheHint(CacheHint.SPEED);
        mitadDer.setCacheHint(CacheHint.SPEED);

        // Contenedor de las fotos de Users abiertas a los lados
        Pane capaPuertas = new Pane(mitadIzq, mitadDer);
        
        // Las añadimos encima de todo el StackPane (Arriba del HomeView)
        root.getChildren().add(capaPuertas);

        // 6. CONFIGURAR LA ANIMACIÓN DE CIERRE (Users viene desde afuera hacia el centro)
        Duration duracion = Duration.millis(600);
        
        // Puerta Izquierda de Users: Viene desde -ancho/2 hacia el 0
        TranslateTransition slideIzq = new TranslateTransition(duracion, mitadIzq);
        slideIzq.setFromX(-ancho / 2); 
        slideIzq.setToX(0); 
        slideIzq.setInterpolator(Interpolator.EASE_OUT);

        // Puerta Derecha de Users: Viene desde ancho hacia el centro (ancho / 2)
        TranslateTransition slideDer = new TranslateTransition(duracion, mitadDer);
        slideDer.setFromX(ancho); 
        slideDer.setToX(ancho / 2); 
        slideDer.setInterpolator(Interpolator.EASE_OUT);

        // Sincronizar el cierre de ambas puertas
        ParallelTransition transicionTotal = new ParallelTransition(slideIzq, slideDer);
        
        // 7. LIMPIEZA FINAL AL TERMINAR EL CIERRE
        transicionTotal.setOnFinished(e -> {
            // El LoginView ya estaba en el índice 0 desde el principio,
            // así que ahora solo removemos el HomeView viejo (que quedó atrapado en el medio)
            // Como tu StackPane ahora tiene [LoginView, HomeView, capaPuertas]:
            root.getChildren().remove(1); // Quita el HomeView viejo del medio
            
            // Quitamos la capa de fotos del frente para liberar memoria
            root.getChildren().remove(capaPuertas);
            mitadIzq.setImage(null);
            mitadDer.setImage(null);
            
            System.out.println("LOG: Las puertas de Users se cerraron perfectamente sobre el Home.");
        });

        // ¡Arrancamos el cierre!
        transicionTotal.play();

    } catch (IOException e) {
        System.err.println("Error al regresar a la vista de Users: " + e.getMessage());
        e.printStackTrace();
    }
}
public void cargarTopUsuarios() {
    try {
        // 1. Limpiamos el VBox para que no duplique datos al recargar
        rootTopUsers.getChildren().clear();
        

        // 2. Traemos todos los usuarios desde tu servicio
        UsersService service = new UsersService();
        List<UsersDto> listaUsuarios = service.obtenerUsuarios(); // O el método que uses para listar todo

        if (listaUsuarios == null || listaUsuarios.isEmpty()) {
            return;
        }

        // 3. 🔥 EL TRUCO DE ORDENAMIENTO (De Mayor a Menor Nivel)
        listaUsuarios.sort((u1, u2) -> {
            Long n1 = (u1.getUsersConfigList() != null && !u1.getUsersConfigList().isEmpty()) 
                    ? u1.getUsersConfigList().get(0).getUsersConfigLevel().getUserLevel() : 1L;
            Long n2 = (u2.getUsersConfigList() != null && !u2.getUsersConfigList().isEmpty()) 
                    ? u2.getUsersConfigList().get(0).getUsersConfigLevel().getUserLevel() : 1L;
            return n2.compareTo(n1); // Orden inverso (mayor a menor)
        });

        // 4. GENERAMOS LOS HBOX DE ARRIBA HACIA ABAJO
        int posicion = 1;
        for (UsersDto dto : listaUsuarios) {
            
            // Contenedor horizontal de la fila
            HBox filaUser = new HBox(15);
            filaUser.setAlignment(Pos.CENTER_LEFT);
            filaUser.getStyleClass().addAll("fx-FondoJuego11","label-usuario"); // Reutiliza tus estilos de tarjeta oscura
            filaUser.setStyle(filaUser.getStyle() + "; -fx-padding: 10 15 10 15;");

            // A) POSTRAR LA POSICIÓN / PODIO (Estilo medallas para los 3 primeros)
            Label lblPosicion = new Label();
            lblPosicion.getStyleClass().add("label-usuario");
            lblPosicion.setStyle("-fx-font-weight: bold; -fx-min-width: 30; -fx-alignment: center;");
            
            if (posicion == 1) {
                lblPosicion.setText("#"+ posicion); // Primer lugar
                lblPosicion.setStyle(lblPosicion.getStyle() + "; -fx-text-fill: #ffcc00; -fx-font-size: 22px;");
            } else if (posicion == 2) {
                lblPosicion.setText("#"+ posicion); // Segundo lugar
                lblPosicion.setStyle(lblPosicion.getStyle() + "; -fx-text-fill: #ffcc00; -fx-font-size: 20px;");
            } else if (posicion == 3) {
                lblPosicion.setText("#"+ posicion); // Tercer lugar
                lblPosicion.setStyle(lblPosicion.getStyle() + "; -fx-text-fill: #ffcc00; -fx-font-size: 18px;");
            } else {
                lblPosicion.setText("#" + posicion); // Del 4 en adelante
                lblPosicion.setStyle(lblPosicion.getStyle() + "; -fx-text-fill: #888888; -fx-font-size: 16px;");
            }

        // B) IMAGEN DEL AVATAR
ImageView avatar = new ImageView();

// 1. Extraemos el arreglo de bytes del DTO
byte[] bytesImagen = dto.getUserAvatar();

// 2. Evaluamos si el arreglo binario tiene contenido
if (bytesImagen != null && bytesImagen.length > 0) {
    // Si tiene bytes, los transformamos en flujo de memoria para el ImageView
    java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(bytesImagen);
    avatar.setImage(new Image(bais));
} else {
    // Si viene vacío o nulo, cargamos la imagen por defecto desde tus recursos internos
    avatar.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/default.png")));
}

// 3. Configuraciones de tamaño (se mantienen idénticas)
avatar.setFitWidth(35);
avatar.setFitHeight(35);
avatar.setPreserveRatio(true);

            // C) NOMBRE DEL JUGADOR
            Label lblNombre = new Label();
            lblNombre.textProperty().bind(dto.userNameProperty());
            lblNombre.getStyleClass().add("label-usuario");
            lblNombre.setStyle(lblNombre.getStyle() + "; -fx-font-size: 16px;");

            // D) EL ESPACIADOR (Para mandar el nivel pegado a la derecha)
            Region espaciador = new Region();
            HBox.setHgrow(espaciador, Priority.ALWAYS);

            // E) TEXTO DEL NIVEL
          Long nivelActual = (dto.getUsersConfigList() != null && !dto.getUsersConfigList().isEmpty()) 
                               ? dto.getUsersConfigList().get(0).getUsersConfigLevel().getUserLevel() : 1L;
            
            // 🌍 1. CONSEGUIMOS EL IDIOMA ACTUAL DEL JUEGO
            // Buscamos el usuario seleccionado en el AppContext para ver qué idioma tiene guardado
            String textoNivel = "Nivel: "; // Por defecto en español
            
            var usuarioLogueado = AppContext.getInstance().getUsuarioSeleccionado();
            if (usuarioLogueado != null && usuarioLogueado.getUsersConfigList() != null && !usuarioLogueado.getUsersConfigList().isEmpty()) {
                var configGen = usuarioLogueado.getUsersConfigList().get(0).getUsersConfigGeneral();
                if (configGen != null && "I".equalsIgnoreCase(configGen.getUserLanguage())) {
                    textoNivel = "Level: "; // Si está en inglés, cambiamos el prefijo
                }
            }

            // 🏷️ 2. CREAMOS EL LABEL CON EL IDIOMA CORRECTO Y EL NÚMERO
            Label lblNivel1 = new Label(textoNivel + String.valueOf(nivelActual));
            
            // lblNivel.getStyleClass().add("label-usuario");
            lblNivel1.setStyle(lblNivel.getStyle() + "; -fx-text-fill: #ffcc00; -fx-font-weight: bold; -fx-font-size: 16px;");
            
            // 5. ENSAMBLAMOS LA FILA EN EL ORDEN SOLICITADO
            filaUser.getChildren().addAll(lblPosicion, avatar, lblNombre, espaciador, lblNivel1);

            // Metemos la fila al contenedor vertical principal
            rootTopUsers.getChildren().add(filaUser);
            
            posicion++; // Avanzamos al siguiente puesto
        }

    } catch (Exception e) {
        System.err.println("Error al cargar el Top de Usuarios: " + e.getMessage());
        e.printStackTrace();
    }
}

    @FXML
    private void onActionOut(ActionEvent event) {
Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(aletaAviso);
    alert.setHeaderText(aletaInfo);
   // alert.setContentText("Todo el progreso no guardado se perderá.");

    // Configurar botones en español
    ButtonType botonAceptar = new ButtonType(aletaBtnSalir);
    ButtonType botonCancelar = new ButtonType(aletaBtnCancelar);
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

    // Si presiona Salir, cerramos todo de forma segura
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
        SoundManager.playErrorSound(); // 🎵 Alerta si intenta ir hacia atrás en el paso 1
    }
}

@FXML
private void onActionNext(ActionEvent event) {
    // Si llegamos al final del tutorial (paso 4)
    if (indexTutorial >= rutasImagenes.size() - 1) {
        SoundManager.playBotonSound(); 
        
        rootHowPlay.setVisible(false); // Ocultamos el panel
        
        if (usuarioActivo != null && usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            
            var configGeneral = usuarioActivo.getUsersConfigList().get(0).getUsersConfigGeneral();
            
            if (configGeneral != null && "S".equalsIgnoreCase(configGeneral.getUserFirsttime())) {
                // 🧠 Cambiamos el estado a 'N' en la memoria RAM del controlador
                configGeneral.setUserFirsttime("N");
                
                // 💾 Guardamos los cambios completos del usuario en Oracle
                try {
                    UsersService usersService = new UsersService();
                    usersService.guardarUsuario(usuarioActivo); 
                    System.out.println("LOG DATABASE: 'N' guardado con éxito en UserConfigGeneral dentro de Oracle.");
                } catch (Exception e) {
                    System.err.println("LOG ERROR: No se pudo actualizar la configuración general: " + e.getMessage());
                }
            }
        }
    } else {
        // Avanzar normalmente por las diapositivas
        SoundManager.playBotonSound();
        indexTutorial++;
        actualizarPasoTutorial();
    }
}
  private void actualizarPasoTutorial() {
    // 1. Cambiamos el texto descriptivo
    LblImage.setText(textosTutorial.get(indexTutorial));

    // 2. Cambiamos la imagen de forma segura
    try {
        String ruta = rutasImagenes.get(indexTutorial);
        javafx.scene.image.Image nuevaImagen = new javafx.scene.image.Image(getClass().getResourceAsStream(ruta));
        ImageTutorial.setImage(nuevaImagen);
    } catch (Exception e) {
        System.err.println("LOG ERROR: No se pudo cargar la imagen del tutorial en el paso " + indexTutorial);
    }
}
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
        
        
        textosTutorial = java.util.Arrays.asList(
    "The enemies approach in different waves and types.",
    "The base's life will decrease with enemy attacks, and the elixir will recover over time.",
    "The weapon is the main tool for eliminating enemies, and you can also change its style and controls.",
    "The powers allow you to damage or freeze a large number of enemies in an area.",
    "The upgrades allow the entire team to level up, increasing their stats."
);
 
    aletaAviso="Close Game";
    aletaInfo="Are you sure you want to quit the game?";
    aletaBtnSalir="Leave";
    aletaBtnCancelar ="Cencel";     
 
    } else {

   
    }
}
}
