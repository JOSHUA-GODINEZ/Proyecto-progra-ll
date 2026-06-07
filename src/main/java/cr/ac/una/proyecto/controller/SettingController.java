package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.App;
import cr.ac.una.proyecto.model.Users;
import cr.ac.una.proyecto.model.UsersConfig;
import cr.ac.una.proyecto.model.UsersConfigDto;
import cr.ac.una.proyecto.model.UsersConfigLevel;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;

public class SettingController implements Initializable {
private UsersDto usuarioActivo;
private Label lblNombre;
private ImageView imgAvatar;
    @FXML
    private VBox rootHowPlay;
    @FXML
    private CheckBox CheckEasyMode;
    @FXML
    private ImageView ImageTutorial;
    private UsersConfigLevelDto configNivelActivo;
// Contenedores e hilos del tutorial
private int indexTutorial = 0;
private final java.util.List<String> rutasImagenes = java.util.Arrays.asList(
     "/cr/ac/una/proyecto/resources/tutorial4.png",
    "/cr/ac/una/proyecto/resources/tutorial2.png",
    "/cr/ac/una/proyecto/resources/tutorial1.png",
    "/cr/ac/una/proyecto/resources/tutorial3.png",
    "/cr/ac/una/proyecto/resources/tutorial5.png"
);

private java.util.List<String> textosTutorial = java.util.Arrays.asList(
    "¡Bienvenid@! Los enemigos se acercan en diferentes oleadas y tipos.",
    "La vida de la base disminuira con el ataque de enemigos y el elixir se recuperara con el tiempo.",
    "El Arma es la principal herramienta para eliminar a los enemigos, ademas de poder cambiar de estilo y controles.",
    "Los poderes permiten dañar o congelar a gran cantidad de enemigos en area.",
    "Las mejoras haran que todo el equipo pueda subir de nivel aumentando sus estadisticas"
);
    @FXML
    private Button BtnBack;
    private ImageView imageSkin;
    @FXML
    private Label LblImage;
  

    @FXML
    private VBox rootUsers;

    @FXML
    private ToggleGroup controles;
    @FXML
    private ToggleGroup controles1;
    @FXML
    private ToggleGroup controles11;
    @FXML
    private ToggleGroup controles2;
    private final UsersService usersService = new UsersService();
    @FXML
    private VBox root;
    @FXML
    private RadioButton radioCompleta;
    @FXML
    private RadioButton radioConBordes;
    @FXML
    private RadioButton radioA;
    @FXML
    private RadioButton radioM;
    @FXML
    private RadioButton radioKeybord;
    @FXML
    private RadioButton radioMouse;
      @FXML
    private Slider SliderMenu;
    @FXML
    private Slider SliderEnemy;
    @FXML
    private Slider SliderGun;
    @FXML
    private Slider SliderEfect;
    @FXML
    private RadioButton radioEspanol;
    @FXML
    private RadioButton radioIngles;
    private boolean seleccionandoArchivo = false;
    @FXML
    private Button btnMenu;
    @FXML
    private Label lblTitulo;
    @FXML
    private Tab tabJuego;
    @FXML
    private Label lblGunD;
    @FXML
    private Label lblControles;
    @FXML
    private Label lblPantalla;
    @FXML
    private Label lblIdioma;
    @FXML
    private Tab tabUsuarios;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Tab tabAudio;
    @FXML
    private Label lblMenuV;
    @FXML
    private Label lblEnemigosV;
    @FXML
    private Label lblArmaV;
    @FXML
    private Label lblEfectoV;
    @FXML
    private Tab tabInformacion;
    @FXML
    private Button btnComoJugar;
    @FXML
    private Label lblCurso;
    @FXML
    private Label lblEstudiantes;
    @FXML
    private Label lblProfesor;
    @FXML
    private Label lblCiclo;
    @FXML
    private Label LblYear;
    @FXML
    private Label lblTutorial;
    @FXML
    private Button BtnNext;
    private String aletaNombre= "Nombre Duplicado";
    private String alertaElejir = " ya existe, pruebe uno diferente.";
     private String aletaEliminar= "Eliminar Usuario";
      private String aletaAviso= "¿Estás seguro de eliminar a ";
       private String aletaInfo= "Esta acción eliminar todo el progreso del usuario";
       private String aletaBtnAceptar= "Entendido";
       private String aletaBtnEliminar= "Eliminar";
       private String aletaBtnCancelar= "Cancelar";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
cargarDatosDesdeBD();
        // 2. Si hay un usuario seleccionado (doble clic previo), lo montamos de inmediato
        if (usuarioActivo != null) {
            inicializarComponentesBase();
        } else {
            System.err.println("LOG: No se encontró ningún usuario activo para cargar en el initialize.");
        }
        
      
        
        Platform.runLater(() -> {
            if (root != null && root.getScene() != null) {
                // Obtenemos el Stage actual usando el ID 'root'
                Stage stageActual = (Stage) root.getScene().getWindow();
                
                // Aplicamos tu método de carga de pantalla
                cargarConfiguracionDePantalla(stageActual);
            }
        });
    //    imageSkin.setImage(imageSkin1);
        
         
            
            
    
    if (usuarioActivo != null) {
        // 2. Validamos que la lista de configuraciones no esté vacía
        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            
            // Extraemos la configuración de la posición 0
            UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
            
            
            if (configuracionActual.getUsersConfigGeneral().getUserStylegun() != null) {
    String estiloArma = configuracionActual.getUsersConfigGeneral().getUserStylegun();
    
    if (estiloArma.equals("A")) {
        radioA.setSelected(true); // Selecciona el predeterminado
    } else if (estiloArma.equals("M")) {
        radioM.setSelected(true); // Selecciona el otro
    }
}
            
        if (configuracionActual.getUsersConfigGeneral() != null && configuracionActual.getUsersConfigGeneral().getUserGamemode() != null) {
            String modoJuego = configuracionActual.getUsersConfigGeneral().getUserGamemode();
            
            if (modoJuego.equals("E")) {
                CheckEasyMode.setSelected(true);  // Si es "S", activa el Easy Mode
            } else {
                CheckEasyMode.setSelected(false); // Si es "N" o cualquier otro, se desmarca
            }
        } else {
            CheckEasyMode.setSelected(false); // Respaldo por defecto (Modo Normal)
        }    
            
        
        
        
        if (configuracionActual.getUsersConfigGeneral() != null && configuracionActual.getUsersConfigGeneral().getUserController() != null) {
    String tipoControl = configuracionActual.getUsersConfigGeneral().getUserController();
    
    if (tipoControl.equals("K")) {
        radioKeybord.setSelected(true); // Selecciona Teclado
    } else if (tipoControl.equals("M")) {
        radioMouse.setSelected(true);    // Selecciona Mouse
    }
} else {
    radioMouse.setSelected(true); // Respaldo por defecto si viene vacío (Mouse)
}
     
      
    
     configuracionActual = usuarioActivo.getUsersConfigList().get(0);
    var configGeneral = configuracionActual.getUsersConfigGeneral();
    
    if (configGeneral != null) {
        // 🎚️ Cargamos los valores de Oracle directo a los Sliders de la interfaz
        SliderMenu.setValue(configGeneral.getUserAudiomenu());
        SliderEnemy.setValue(configGeneral.getUserAudioenemys());
        SliderEfect.setValue(configGeneral.getUserAudioefect());
        SliderGun.setValue(configGeneral.getUserAudiogun());
        
        System.out.println("LOG AUDIO: Sliders de volumen sincronizados con la base de datos.");
    }

    if (configuracionActual.getUsersConfigGeneral() != null && configuracionActual.getUsersConfigGeneral().getUserLanguage() != null) {
    String idioma = configuracionActual.getUsersConfigGeneral().getUserLanguage();
    
    if (idioma.equals("S")) {
        radioEspanol.setSelected(true); // Selecciona Español
    } else if (idioma.equals("I")) {
        radioIngles.setSelected(true);  // Selecciona Inglés
    }
} else {
    radioEspanol.setSelected(true); // Respaldo por defecto si viene vacío (Español)
}
        }    
    
    }
configurarListenersDeAudio();    
   SoundManager.cargarEfectosError();
   SliderMenu.valueProperty().bindBidirectional(SoundManager.menuVolumeProperty());
   SliderEnemy.valueProperty().bindBidirectional(SoundManager.getEnemyVolumeProperty());
   SliderGun.valueProperty().bindBidirectional(SoundManager.getGunVolumeProperty());
   SliderEfect.valueProperty().bindBidirectional(SoundManager.getEfectVolumeProperty());
   
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
    
    // Hacemos el panel visible
    rootHowPlay.setVisible(true);
    System.out.println("LOG UI: Panel de cómo jugar abierto.");
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
    // Si estamos en la última imagen (índice 4) y presiona Siguiente, se cierra el tutorial
    if (indexTutorial >= rutasImagenes.size() - 1) {
     
        rootHowPlay.setVisible(false); // 🔥 ¡Se oculta todo el root!
        System.out.println("LOG UI: Fin del tutorial. Panel ocultado.");
    } else {
        // De lo contrario, avanza al siguiente paso normalmente
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
    
    
    
@FXML
private void onActionModificUser(ActionEvent event) {
    if (usuarioActivo == null) return;

    System.out.println("LOG: Modo edición activado.");
    seleccionandoArchivo = false; // Inicializamos el escudo

    // ======================================================================
    // 1. EL TEXTFIELD INTERACTIVO
    // ======================================================================
    TextField txtModificarNombre = new TextField(usuarioActivo.getUserName());
    txtModificarNombre.getStyleClass().add("label-usuario"); 
    txtModificarNombre.setStyle(
        "-fx-background-color: #3a3a3a; -fx-text-fill: white; " +
        "-fx-background-radius: 5; -fx-padding: 5 10 5 10;"
    );

    txtModificarNombre.setTextFormatter(new TextFormatter<>(change -> {
        String nuevoTexto = change.getControlNewText();
        if (nuevoTexto.matches("^[a-zA-Z0-9]*$") && nuevoTexto.length() <= 12) {
            return change; 
        }
      
        return null; 
    }));

    int indiceLabel = rootUsers.getChildren().indexOf(lblNombre);
    if (indiceLabel != -1) {
        rootUsers.getChildren().set(indiceLabel, txtModificarNombre);
        txtModificarNombre.requestFocus();
    }

    // ======================================================================
    // 2. ACTIVAR CLIC EN EL IMAGEVIEW (Con escudo protector)
    // ======================================================================
    imgAvatar.setStyle("-fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, #ffcc00, 10, 0, 0, 0);"); 
    
    imgAvatar.setOnMousePressed(mouseEvent -> {
        // 🔥 Levantamos el escudo en el instante en que el mouse baja, ANTES de que el TextField procese la pérdida de foco
        seleccionandoArchivo = true; 
    });

    imgAvatar.setOnMouseClicked(mouseEvent -> {
        System.out.println("LOG: Clic confirmado en Avatar. Abriendo buscador de archivos...");
        
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
                
                // Guardamos la foto de inmediato
                UsersService usersService = new UsersService();
                UsersDto usuarioGuardado = usersService.guardarUsuario(usuarioActivo);
                if (usuarioGuardado != null) {
                    AppContext.getInstance().setUsuarioSeleccionado(usuarioGuardado);
                    System.out.println("LOG: Nueva foto guardada con éxito en Oracle.");
                }
            } catch (Exception e) {
                System.err.println("LOG: Error al procesar la imagen: " + e.getMessage());
            }
        }
        
        // 🔥 El buscador ya se cerró. Bajamos el escudo y le devolvemos el foco al texto para que el usuario termine de editar
        seleccionandoArchivo = false;
        txtModificarNombre.requestFocus();
    });

    // ======================================================================
    // 3. GUARDADO AUTOMÁTICO AL PERDER EL FOCO (Respeta el Escudo)
    // ======================================================================
    txtModificarNombre.focusedProperty().addListener((observable, estabaEnfocado, estaEnfocado) -> {
        if (!estaEnfocado) {
            // Le damos una milésima de segundo para verificar si el escudo se levantó
            javafx.application.Platform.runLater(() -> {
                
                // 🛡️ SI EL ESCUDO ESTÁ ACTIVO: El usuario está usando el buscador de archivos. ¡No guardamos nada aún!
                if (seleccionandoArchivo) {
                    System.out.println("LOG: Pérdida de foco ignorada temporalmente. El explorador de archivos está activo.");
                    return; 
                }

                String nuevoNombre = txtModificarNombre.getText().trim();

                if (nuevoNombre.isEmpty()) {
                    finalizarModificacion(indiceLabel, txtModificarNombre);
                    return;
                }

                try {
                    String nombreAnterior = usuarioActivo.getUserName();
                    usuarioActivo.setUserName(nuevoNombre);
                    
                    UsersService usersService = new UsersService();
                    UsersDto usuarioGuardado = usersService.guardarUsuario(usuarioActivo);
                    
                    if (usuarioGuardado != null) {
                        AppContext.getInstance().setUsuarioSeleccionado(usuarioGuardado);
                        lblNombre.textProperty().unbind();
                        lblNombre.textProperty().bind(usuarioGuardado.userNameProperty());
                        System.out.println("LOG: Nombre actualizado con éxito.");
                    } else {
                       usuarioActivo.setUserName(nombreAnterior);

javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
alerta.setTitle(aletaNombre);
alerta.setHeaderText(null);
alerta.setContentText(nuevoNombre + alertaElejir);

// Configurar botón personalizado en español (Opcional, pero recomendado para mantener tu diseño)
javafx.scene.control.ButtonType botonEntendido = new javafx.scene.control.ButtonType(aletaBtnAceptar);
alerta.getButtonTypes().setAll(botonEntendido);

// 🔥 INYECTAR EL ESTILO CSS AL DIALOG PANE DE LA ALERTA
javafx.scene.control.DialogPane dialogPane = alerta.getDialogPane();
try {
    dialogPane.getStylesheets().add(
        getClass().getResource("/cr/ac/una/proyecto/view/Style.css").toExternalForm()
    );
    // Le aplicamos tus clases CSS para el fondo y las letras
    dialogPane.getStyleClass().addAll("fx-FondoJuego11", "label-usuario");
} catch (Exception e) {
    System.out.println("No se pudo cargar el archivo CSS de la alerta, usando estilo por defecto.");
}

// Mostrar la alerta estilizada en pantalla
alerta.showAndWait();
                    }
                } catch (Exception e) {
                    System.err.println("Error en actualización: " + e.getMessage());
                } finally {
                    finalizarModificacion(indiceLabel, txtModificarNombre);
                }
            });
        }
    });
}

private void finalizarModificacion(int indiceLabel, TextField txtModificarNombre) {
    // Si el escudo está arriba, bajo ninguna circunstancia cerramos la edición
    if (seleccionandoArchivo) return;

    if (indiceLabel != -1 && rootUsers.getChildren().contains(txtModificarNombre)) {
        imgAvatar.setOnMousePressed(null);
        imgAvatar.setOnMouseClicked(null);
        imgAvatar.setStyle(null);
        rootUsers.getChildren().set(indiceLabel, lblNombre);
        System.out.println("LOG: Modo edición cerrado limpiamente.");
    }
}
 

/*public void cargarUsuarioEnVista(UsersDto dto) {
    this.usuarioActivo = dto;
    
    rootUsers.getChildren().clear();
    rootUsers.setStyle("-fx-alignment: CENTER_LEFT; -fx-spacing: 15; -fx-padding: 10;");
    
    // 2. Construimos el ImageView del Avatar
    imgAvatar = new ImageView();
    
    // 🔥 EL TRUCO: Extraemos los bytes del DTO
    byte[] bytesImagen = usuarioActivo.getUserAvatar();
    
    if (bytesImagen != null && bytesImagen.length > 0) {
        // Si hay bytes en Oracle, los cargamos desde la memoria
        ByteArrayInputStream bais = new ByteArrayInputStream(bytesImagen);
        imgAvatar.setImage(new Image(bais));
    } else {
        // Respaldo por defecto desde los recursos internos
        imgAvatar.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/default.png")));
    }
    
    imgAvatar.setFitWidth(500);
    imgAvatar.setFitHeight(500);
   // imgAvatar.setPreserveRatio(true); // Te recomiendo desmarcar el comentario de esta línea para que no se deforme la foto

    // 3. Construimos el Label del Nombre con tus estilos grandes
    lblNombre = new Label();
    lblNombre.textProperty().bind(usuarioActivo.userNameProperty());
    lblNombre.getStyleClass().add("label-usuario"); 

    // 4. Los metemos al HBox
    rootUsers.getChildren().addAll(imgAvatar, lblNombre);
}*/



    private void inicializarComponentesBase() {
    // Limpiamos el HBox por seguridad y aplicamos espaciado
    rootUsers.getChildren().clear();
    rootUsers.setStyle("-fx-alignment: CENTER; -fx-spacing: 15; -fx-padding: 10;");

    // 1. Construcción del ImageView del Avatar
    imgAvatar = new ImageView();
    
    // 🔥 EL TRUCO: Extraemos los bytes del DTO
    byte[] bytesImagen = usuarioActivo.getUserAvatar();
    
    if (bytesImagen != null && bytesImagen.length > 0) {
        // Cargamos los píxeles directamente de la base de datos
        ByteArrayInputStream bais = new ByteArrayInputStream(bytesImagen);
        imgAvatar.setImage(new Image(bais));
    } else {
        // Imagen por defecto si no tiene avatar asignado
        imgAvatar.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/default.png")));
    }
    
    imgAvatar.setFitWidth(250);
    imgAvatar.setFitHeight(250);
    //imgAvatar.setPreserveRatio(true);

    // 2. Construcción del Label del Nombre
    lblNombre = new Label();
    lblNombre.textProperty().bind(usuarioActivo.userNameProperty());
    lblNombre.getStyleClass().add("label-usuario1"); 

    // 3. Inyectamos los componentes al contenedor visual
    rootUsers.getChildren().addAll(imgAvatar, lblNombre);
    
    System.out.println("LOG: Vista inicializada con el usuario: " + usuarioActivo.getUserName());
}
    
    
      @FXML
private void onActionDeleteUser(ActionEvent event) {
    if (usuarioActivo == null) {
        System.err.println("LOG: No hay ningún usuario activo seleccionado para eliminar.");
        return;
    }

Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
alerta.setTitle(aletaEliminar);
alerta.setHeaderText(aletaAviso + usuarioActivo.getUserName() + "?");
alerta.setContentText(aletaInfo);

// Configurar botones manuales para forzar un mejor tamaño
ButtonType btnSi = new ButtonType(aletaBtnEliminar);
ButtonType btnNo = new ButtonType(aletaBtnCancelar);
alerta.getButtonTypes().setAll(btnSi, btnNo);

DialogPane dialogPane = alerta.getDialogPane();
try {
    dialogPane.getStylesheets().add(getClass().getResource("/cr/ac/una/proyecto/view/Style.css").toExternalForm());
    dialogPane.getStyleClass().addAll("fx-FondoJuego11", "label-usuario");
} catch (Exception e) {
    System.out.println("Error con CSS");
}

// OBLIGAMOS a ajustar el tamaño real tras procesar el CSS
alerta.getDialogPane().sceneProperty().addListener((obs, oldScene, newScene) -> {
    if (newScene != null) {
        javafx.application.Platform.runLater(() -> {
            javafx.stage.Stage stage = (javafx.stage.Stage) newScene.getWindow();
            stage.sizeToScene(); // Ajuste dinámico impecable
        });
    }
});

Optional<ButtonType> resultado = alerta.showAndWait();
    
    
    // Si el usuario presiona "OK", procedemos al borrado definitivo
   if (resultado.isPresent() && resultado.get() == btnSi) {
        try {
            // 2. Llamamos al servicio para purgar Oracle
            UsersService usersService = new UsersService();
            boolean eliminadoExitosamente = usersService.eliminarUsuarioCompleto(usuarioActivo);
            
            if (eliminadoExitosamente) {
                System.out.println("LOG: El usuario se eliminó correctamente en Oracle.");
                
                // 3. LIMPIEZA DE MEMORIA: Quitamos el usuario de las variables locales y del contexto global
                AppContext.getInstance().setUsuarioSeleccionado(null);
                this.usuarioActivo = null;
                
                // Vaciamos el HBox visual por seguridad
                rootUsers.getChildren().clear();
                
                // 4. 🔥 REGRESAR A LOGINVIEW CON TU ANIMACIÓN ASÍNCRONA DE PUERTAS
                volverAUsersConAnimacion();
                
            } else {
                System.err.println("LOG: El servicio no pudo completar el borrado.");
            }
            
        } catch (Exception e) {
            System.err.println("Error en el flujo de borrado: " + e.getMessage());
            e.printStackTrace();
        }
    } else {
        System.out.println("LOG: Borrado cancelado por el usuario.");
    }
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


// 🚨 Recuerda instanciar tu servicio al inicio de la clase del controlador:
// private UsersService usersService = new UsersService();

@FXML
private void onActionCompleta(ActionEvent event) {
    javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
    
    if (!stage.isFullScreen()) {
        stage.setFullScreen(true);
        stage.setFullScreenExitHint(""); 
    }
    
    // 1. Jalamos el usuario activo del Singleton
     usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
    
    if (usuarioActivo != null) {
        // 2. 🚨 CAPTURA LA LISTA: Conseguimos la lista de configuraciones del DTO
        // Revisa si en tu UsersDto se llama getUsersConfigList() o parecido
        List<UsersConfigDto> listaConfigs = usuarioActivo.getUsersConfigList();
        
        // 3. Validamos que la lista exista y no esté vacía
        if (listaConfigs != null && !listaConfigs.isEmpty()) {
            
            // 4. 🔥 EXTRAEMOS EL PRIMER REGISTRO (Posición 0)
            UsersConfigDto configuracionActual = listaConfigs.get(0);
            
            if (configuracionActual.getUsersConfigGeneral()!= null) {
                
                // 5. Seteamos la 'C' en el DTO general
                configuracionActual.getUsersConfigGeneral().setUserScreen("C");
                
                // 6. Guardamos el usuario completo con su lista actualizada
                usersService.guardarUsuario(usuarioActivo); 
                System.out.println("✅ Guardada 'C' en la lista de configuración de: " + usuarioActivo.getUserName());
            } else {
                System.err.println("❌ El objeto general dentro de la configuración es null.");
            }
        } else {
            System.err.println("❌ La lista de configuraciones del usuario está vacía o es null.");
        }
    } else {
        System.err.println("❌ No hay ningún usuario seleccionado en el AppContext.");
    }
}

@FXML
private void onActionConBordes(ActionEvent event) {
    javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
    
    // 1. Cambiamos físicamente la ventana a modo normal con bordes y maximizada
    stage.setFullScreen(false);
    stage.setMaximized(true);
    
    // 2. Recuperamos el usuario activo desde tu AppContext
     usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
    
    if (usuarioActivo != null) {
        // 🔥 CORRECCIÓN AQUÍ: Validamos la lista y usamos .get(0)
        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            
            // Sacamos la configuración de la posición 0 de la lista
            UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
            
            if (configuracionActual.getUsersConfigGeneral()!= null) {
                // Seteamos la 'B' de bordes
                configuracionActual.getUsersConfigGeneral().setUserScreen("B");
                
                // Guardamos el cambio de inmediato en Oracle pasando el usuario completo
                usersService.guardarUsuario(usuarioActivo); 
                System.out.println("LOG PANTALLA: Se guardó la 'B' para el usuario: " + usuarioActivo.getUserName());
            } else {
                System.err.println("LOG PANTALLA: El objeto general dentro de la configuración es null.");
            }
        } else {
            System.err.println("LOG PANTALLA: La lista de configuraciones está vacía o es null.");
        }
    } else {
        System.err.println("LOG PANTALLA: No hay ningún usuario activo en el AppContext.");
    }
}


@FXML
private void onActionGun1Sprite(ActionEvent event) {
    // 1. Recuperamos el usuario activo del AppContext
     usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
    
    if (usuarioActivo != null) {
        // 2. Validamos que la lista de configuraciones no esté vacía
        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            
            // Extraemos la configuración de la posición 0
            UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
            
            if (configuracionActual.getUsersConfigGeneral() != null) {
                // 3. 🔥 Seteamos "A" (Predeterminado) en tu campo real
                configuracionActual.getUsersConfigGeneral().setUserStylegun("A");
                
                // 4. Guardamos el cambio de inmediato en Oracle
                usersService.guardarUsuario(usuarioActivo);
                System.out.println("LOG JUEGO: Se guardó el estilo 'A' (Predeterminado) para: " + usuarioActivo.getUserName());
            } else {
                System.err.println("LOG JUEGO: El objeto general de configuración es null.");
            }
        } else {
            System.err.println("LOG JUEGO: La lista de configuraciones del usuario está vacía.");
        }
    } else {
        System.err.println("LOG JUEGO: No hay ningún usuario seleccionado.");
    }
}

@FXML
private void onActionGun2Sprite(ActionEvent event) {

     usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
    
    if (usuarioActivo != null) {

        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {

            UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
            
            if (configuracionActual.getUsersConfigGeneral()!= null) {

                configuracionActual.getUsersConfigGeneral().setUserStylegun("M");

                usersService.guardarUsuario(usuarioActivo);
                System.out.println("LOG JUEGO: Se guardó el estilo 'M' para: " + usuarioActivo.getUserName());
            } else {
                System.err.println("LOG JUEGO: El objeto general de configuración es null.");
            }
        } else {
            System.err.println("LOG JUEGO: La lista de configuraciones del usuario está vacía.");
        }
    } else {
        System.err.println("LOG JUEGO: No hay ningún usuario seleccionado.");
    }
}
    
    
    
    
    
    
    
    
    
public void cargarConfiguracionDePantalla(javafx.stage.Stage stage) {
    UsersDto usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
    
    if (usuarioActivo != null && usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
        
        UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
        
        if (configuracionActual.getUsersConfigGeneral()!= null) {
            String tipoPantalla = configuracionActual.getUsersConfigGeneral().getUserScreen();
            
            // Si es null, por defecto es Bordes
            if (tipoPantalla == null) tipoPantalla = "B";

            // --- LÓGICA DE LOS RADIO BUTTONS (UI) ---
            if (tipoPantalla.equals("C")) {
                radioCompleta.setSelected(true);
            } else {
                radioConBordes.setSelected(true);
            }

            // --- LÓGICA DE LA VENTANA (STAGE) ---
            if (stage != null) { // Validamos por si el stage aún no carga
                if (tipoPantalla.equals("C")) {
                    stage.setFullScreen(true);
                    stage.setFullScreenExitHint("");
                } else {
                    stage.setFullScreen(false);
                    stage.setMaximized(true);
                }
            }
            
            System.out.println("LOG UI: RadioButtons sincronizados con la letra: " + tipoPantalla);
        }
    }
}

@FXML
private void onActionEasyMode(ActionEvent event) {
    
    // 1. Recuperamos el usuario activo (igual a tu método)
    usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
    
    if (usuarioActivo != null) {
        
        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            
            UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
            
            // 🔥 CORRECCIÓN: Cambiado 'getUsersConfigGeneral()' por 'getUserConfigGeneral()'
            if (configuracionActual.getUsersConfigGeneral()!= null) {
                
                // 2. Evaluamos el estado del CheckBox para asignar "S" o "N"
                if (CheckEasyMode.isSelected()) {
                    configuracionActual.getUsersConfigGeneral().setUserGamemode("E");
                    System.out.println("LOG JUEGO: Se seleccionó Easy Mode ('S')");
                } else {
                    configuracionActual.getUsersConfigGeneral().setUserGamemode("N");
                    System.out.println("LOG JUEGO: Se deseleccionó Easy Mode, volviendo a Normal ('N')");
                }
                
                // 3. Guardamos en la base de datos (igual a tu método)
                usersService.guardarUsuario(usuarioActivo);
                System.out.println("LOG JUEGO: Se guardó la dificultad para: " + usuarioActivo.getUserName());
                
            } else {
                System.err.println("LOG JUEGO: El objeto general de configuración es null.");
            }
        } else {
            System.err.println("LOG JUEGO: La lista de configuraciones del usuario está vacía.");
        }
    } else {
        System.err.println("LOG JUEGO: No hay ningún usuario seleccionado.");
    }
}

@FXML
private void onActionKeyBoard(ActionEvent event) {
    // 1. Recuperamos el usuario activo
    usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
    
    if (usuarioActivo != null) {
        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            
            UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
            
            if (configuracionActual.getUsersConfigGeneral() != null) {
                // 2. 🔥 Seteamos "K" (Keyboard)
                configuracionActual.getUsersConfigGeneral().setUserController("K");
                
                // 3. Guardamos en Oracle
                usersService.guardarUsuario(usuarioActivo);
                System.out.println("LOG JUEGO: Se guardó el control por Teclado ('K') para: " + usuarioActivo.getUserName());
            } else {
                System.err.println("LOG JUEGO: El objeto general de configuración es null.");
            }
        }
    } else {
        System.err.println("LOG JUEGO: No hay ningún usuario seleccionado.");
    }
}

@FXML
private void onActionMouse(ActionEvent event) {
    // 1. Recuperamos el usuario activo
    usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
    
    if (usuarioActivo != null) {
        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            
            UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
            
            if (configuracionActual.getUsersConfigGeneral() != null) {
                // 2. 🔥 Seteamos "M" (Mouse)
                configuracionActual.getUsersConfigGeneral().setUserController("M");
                
                // 3. Guardamos en Oracle
                usersService.guardarUsuario(usuarioActivo);
                System.out.println("LOG JUEGO: Se guardó el control por Mouse ('M') para: " + usuarioActivo.getUserName());
            } else {
                System.err.println("LOG JUEGO: El objeto general de configuración es null.");
            }
        }
    } else {
        System.err.println("LOG JUEGO: No hay ningún usuario seleccionado.");
    }
}



private void configurarListenersDeAudio() {
    usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
    if (usuarioActivo == null || usuarioActivo.getUsersConfigList().isEmpty()) return;
    
    var configGeneral = usuarioActivo.getUsersConfigList().get(0).getUsersConfigGeneral();
    if (configGeneral == null) return;

    // 1. 🎵 AUDIO MENÚ / MÚSICA DE FONDO
    SliderMenu.valueChangingProperty().addListener((obs, antesCambiaba, ahoraCambia) -> {
        // En caliente (mientras arrastra el slider), actualizamos el sonido de la app
        SoundManager.setmenuVolume(SliderMenu.getValue());
        
        if (!ahoraCambia) { // Cuando el usuario SOLTÓ el clic, guardamos en Oracle
            configGeneral.setUserAudiomenu(SliderMenu.getValue());
            usersService.guardarUsuario(usuarioActivo);
            System.out.println("LOG AUDIO: Sincronizado Menú en Oracle y SoundManager: " + SliderMenu.getValue());
        }
    });

    // 2. 🧟 AUDIO ENEMIGOS
    SliderEnemy.valueChangingProperty().addListener((obs, antesCambiaba, ahoraCambia) -> {
        // En caliente actualizamos la propiedad estática
        SoundManager.getEnemyVolumeProperty().set(SliderEnemy.getValue());
        
        if (!ahoraCambia) {
            configGeneral.setUserAudioenemys(SliderEnemy.getValue());
            usersService.guardarUsuario(usuarioActivo);
            System.out.println("LOG AUDIO: Sincronizado Enemigos en Oracle y SoundManager: " + SliderEnemy.getValue());
        }
    });

    // 3. 💥 AUDIO EFECTOS GENERALES
    SliderEfect.valueChangingProperty().addListener((obs, antesCambiaba, ahoraCambia) -> {
        // En caliente actualizamos la propiedad estática
        SoundManager.getEfectVolumeProperty().set(SliderEfect.getValue());
        
        if (!ahoraCambia) {
            configGeneral.setUserAudioefect(SliderEfect.getValue());
            usersService.guardarUsuario(usuarioActivo);
            System.out.println("LOG AUDIO: Sincronizado Efectos en Oracle y SoundManager: " + SliderEfect.getValue());
        }
    });

    // 4. 🔫 AUDIO ARMA / DISPAROS
    SliderGun.valueChangingProperty().addListener((obs, antesCambiaba, ahoraCambia) -> {
        // En caliente actualizamos la propiedad estática
        SoundManager.getGunVolumeProperty().set(SliderGun.getValue());
        
        if (!ahoraCambia) {
            configGeneral.setUserAudiogun(SliderGun.getValue());
            usersService.guardarUsuario(usuarioActivo);
            System.out.println("LOG AUDIO: Sincronizado Arma en Oracle y SoundManager: " + SliderGun.getValue());
        }
    });
}

 @FXML
private void onActionEspanol(ActionEvent event) {

    usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
    
    if (usuarioActivo != null) {
        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            
            UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
            
            if (configuracionActual.getUsersConfigGeneral() != null) {
                // 2. 🔥 Seteamos "S" (Spanish / Español)
                configuracionActual.getUsersConfigGeneral().setUserLanguage("S");
                
                // 3. Guardamos en Oracle
                usersService.guardarUsuario(usuarioActivo);
                System.out.println("LOG JUEGO: Idioma guardado en Español ('S') para: " + usuarioActivo.getUserName());
            } else {
                System.err.println("LOG JUEGO: El objeto general de configuración es null.");
            }
        }
    } else {
        System.err.println("LOG JUEGO: No hay ningún usuario seleccionado.");
    }
 aplicarIdiomaEnPantalla("E");
}

@FXML
private void onActionIngles(ActionEvent event) {

    usuarioActivo = AppContext.getInstance().getUsuarioSeleccionado();
    
    if (usuarioActivo != null) {
        if (usuarioActivo.getUsersConfigList() != null && !usuarioActivo.getUsersConfigList().isEmpty()) {
            
            UsersConfigDto configuracionActual = usuarioActivo.getUsersConfigList().get(0);
            
            if (configuracionActual.getUsersConfigGeneral()!= null) {
                // 2. 🔥 Seteamos "I" (English / Inglés)
                configuracionActual.getUsersConfigGeneral().setUserLanguage("I");
                
                // 3. Guardamos en Oracle
                usersService.guardarUsuario(usuarioActivo);
                System.out.println("LOG JUEGO: Idioma guardado en Inglés ('I') para: " + usuarioActivo.getUserName());
            } else {
                System.err.println("LOG JUEGO: El objeto general de configuración es null.");
            }
        }
    } else {
        System.err.println("LOG JUEGO: No hay ningún usuario seleccionado.");
    }
   aplicarIdiomaEnPantalla("I");
}

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
        
        
        textosTutorial = java.util.Arrays.asList(
    "The enemies approach in different waves and types.",
    "The base's life will decrease with enemy attacks, and the elixir will recover over time.",
    "The weapon is the main tool for eliminating enemies, and you can also change its style and controls.",
    "The powers allow you to damage or freeze a large number of enemies in an area.",
    "The upgrades allow the entire team to level up, increasing their stats."
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
        
        
       textosTutorial = java.util.Arrays.asList(
    "¡Bienvenid@! Los enemigos se acercan en diferentes oleadas y tipos.",
    "La vida de la base disminuira con el ataque de enemigos y el elixir se recuperara con el tiempo.",
    "El Arma es la principal herramienta para eliminar a los enemigos, ademas de poder cambiar de estilo y controles.",
    "Los poderes permiten dañar o congelar a gran cantidad de enemigos en area.",
    "Las mejoras haran que todo el equipo pueda subir de nivel aumentando sus estadisticas"
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
             //   bindearComponentesUI();
              //  calcularDetallesMejoras();
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
}
