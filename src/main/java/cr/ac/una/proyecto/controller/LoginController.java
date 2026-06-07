package cr.ac.una.proyecto.controller;

import cr.ac.una.proyecto.App;
import cr.ac.una.proyecto.model.Users;
import cr.ac.una.proyecto.model.UsersConfigDto;
import cr.ac.una.proyecto.model.UsersConfigLevelDto;
import cr.ac.una.proyecto.model.UsersDto;
import cr.ac.una.proyecto.service.UsersService;
import cr.ac.una.proyecto.util.AppContext;
import cr.ac.una.proyecto.util.AssetManager;
import static cr.ac.una.proyecto.util.AssetManager.getAudio;
import cr.ac.una.proyecto.util.SoundManager;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController implements Initializable {


    // Variable para recordar la imagen que se seleccionó en el FileChooser
    private String nombreImagenSeleccionada = "default.png";
    // Instancia única de nuestro servicio genérico de persistencia
  private final UsersService usersService = new UsersService();

    private Label LblNombre; 

   // private HBox rootUsers;

    private TextField currentField;

    @FXML
    private Button btnNew;
private static final double DESPLAZAMIENTO_PASO = 0.25; 
    private Timeline animacionScroll;
  //  private ScrollPane scrollUsuarios;

    
    @FXML
    private VBox rootUsers;
    @FXML
    private ScrollPane scrollUsuarios;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         SoundManager.stopFondo1Sound();
        btnNew.setDefaultButton(false);
        btnNew.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                    if (event.getCode() == KeyCode.ENTER && currentField != null) {
                        currentField.fireEvent(new ActionEvent(currentField, currentField));
                        event.consume(); 
                    }
                });
            }
        });

        // 🔄 AL ARRANCAR: Cargamos todos los nombres que ya están en la base de datos
        cargarNombresDesdeBD();
        
           SoundManager.cargarEfectosError();
   
    }

    /**
     * Trae los usuarios de la base de datos y crea sus etiquetas visuales bindeadas
     */
private void cargarNombresDesdeBD() {
    // 1. Limpiamos el contenedor horizontal por si acaso
    rootUsers.getChildren().clear();

    List<Users> listaUsuarios = usersService.getUsers();
    
    if (listaUsuarios == null || listaUsuarios.isEmpty()) {
        System.out.println("LOG UI: No hay usuarios en la base de datos.");
        return;
    }

    // 2. Recorremos los usuarios de Oracle y los inyectamos en el HBox
    for (Users user : listaUsuarios) {
        UsersDto dto = new UsersDto(user);
        
        // Creamos la tarjeta vertical (VBox)
        HBox tarjeta = crearTarjetaUsuario(dto);
        
        // La agregamos al contenedor horizontal
        rootUsers.getChildren().add(tarjeta);
    }
    
    // Dejamos el scroll al puro inicio (izquierda)
    scrollUsuarios.setHvalue(0.0);
    System.out.println("LOG UI: Se cargaron " + listaUsuarios.size() + " usuarios en el carrusel HBox.");
}

    /**
     * Genera la fila visual (HBox) y vincula el Label al nombre del DTO mediante Binding
     */
/**
     * Genera la fila visual (HBox) leyendo la imagen directamente desde la ruta fija del disco duro.
     */private HBox crearTarjetaUsuario(UsersDto dto) {
    // 1. 🔥 Cambiamos el contenedor principal a VBox con un espaciado vertical de 10px
    HBox container = new HBox(10); 
    container.getStyleClass().addAll("fx-FondoJuego11","fx-gameButton");
    
    // Dimensiones fijas estilo tarjeta de consola para que se alineen bien en el carrusel
 container.setMaxSize(Double.MAX_VALUE,30);
 container.setSpacing(30);
    container.setStyle("-fx-alignment: CENTER; -fx-padding: 15;"); // Centra todo vertical y horizontalmente
    
    // --- CÓDIGO DEL AVATAR (Hacerlo más grande estilo PlayStation) ---
    ImageView avatar = new ImageView();
    try {
        byte[] bytesImagen = dto.getUserAvatar(); 
        
        if (bytesImagen != null && bytesImagen.length > 0) {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytesImagen);
            avatar.setImage(new Image(bais));
        } else {
            avatar.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/default.png")));
        }
        
        // 🔥 Aumentamos el tamaño para que sea el protagonista arriba en la tarjeta
        avatar.setFitWidth(70);
        avatar.setFitHeight(70);
        avatar.setPreserveRatio(true);
        
        // Opcional: Recorte circular para que se vea más moderno
     //   javafx.scene.shape.Circle clipCircular = new javafx.scene.shape.Circle(50, 50, 50);
     //   avatar.setClip(clipCircular);
        
    } catch (Exception ex) {
        System.err.println("ERROR al cargar la imagen en la tarjeta: " + ex.getMessage());
        avatar.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/default.png")));
    }
    
    // --- CÓDIGO DEL NOMBRE (Justo abajo del avatar) ---
    Label lblName = new Label();
    lblName.textProperty().bind(dto.userNameProperty());
    lblName.getStyleClass().add("label-usuario"); 
    //lblName.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: white;");
    
    // --- CÓDIGO DEL NIVEL (Abajo del nombre) ---
    // Usamos un HBox pequeño solo para poner la palabra "Nivel:" a la par del número de forma centrada
    HBox seccionNivel = new HBox(5);
    seccionNivel.setStyle("-fx-alignment: CENTER;"); // Centrado debajo del nombre
    
    Label lblTextoNivel = new Label("Nivel: ");
    lblTextoNivel.getStyleClass().add("label-usuario"); 
   // lblTextoNivel.setStyle("-fx-opacity: 0.7; -fx-font-size: 13px;"); 
    
    Label lblNumeroNivel = new Label();
    lblNumeroNivel.getStyleClass().add("label-usuario"); 
    lblNumeroNivel.setStyle("-fx-text-fill: #ffcc00"); 
    
    // Vinculamos el userLevel real de la base de datos
    if (dto.getUsersConfigList() != null && !dto.getUsersConfigList().isEmpty()) {
        UsersConfigLevelDto nivelDto = dto.getUsersConfigList().get(0).getUsersConfigLevel();
        if (nivelDto != null) {
            lblNumeroNivel.textProperty().bind(nivelDto.userLevelProperty().asString());
        }
    } else {
        lblNumeroNivel.setText("1");
    }
    
    seccionNivel.getChildren().addAll(lblTextoNivel, lblNumeroNivel);
    
    // --- ENSAMBLAJE FINAL (En orden estricto de arriba hacia abajo) ---
    container.getChildren().addAll(avatar, lblName, seccionNivel);
    
    // --- EFECTO DE SELECCIÓN DINÁMICA (Brillo PlayStation al pasar el mouse) ---
    container.setOnMouseEntered(mouseEvent -> {

        container.setScaleX(1.05); // Crece un 5%
        container.setScaleY(1.05);
        container.setCursor(javafx.scene.Cursor.HAND);
    });
    
    container.setOnMouseExited(mouseEvent -> {
        container.setStyle("-fx-alignment: CENTER; -fx-padding: 15;");
        container.setScaleX(1.0); // Regresa a tamaño normal
        container.setScaleY(1.0);
    });
    
    // --- EVENTOS DE CLIC ---
    container.setOnMouseClicked(event -> {
        if (event.getClickCount() == 1) {
            if (LblNombre != null) {
                LblNombre.textProperty().unbind();
                LblNombre.textProperty().bind(dto.userNameProperty());
            }
        } else if (event.getClickCount() == 2) {
            AppContext.getInstance().setUsuarioSeleccionado(dto);
            System.out.println("LOG: Entrando al juego con el usuario: " + dto.getUserName());
            irAHomeConAnimacion();
        }
    });

    return container;
}


@FXML
private void onActionNew(ActionEvent event) {
    if (currentField != null) {
        currentField.requestFocus();
        return;
    }

    // 1. Contenedor principal con diseño de tarjeta inmediato
    HBox container = new HBox(15);
    container.getStyleClass().addAll("fx-FondoJuego11","fx-gameButton"); 
    container.setStyle("-fx-alignment: CENTER;"); 

    // 2. CREAMOS EL IMAGEVIEW INTERACTIVO
    ImageView ivCargarImagen = new ImageView();
    ivCargarImagen.setFitWidth(70);  
    ivCargarImagen.setFitHeight(70);
    ivCargarImagen.setPreserveRatio(true);
    ivCargarImagen.setCursor(Cursor.HAND); 
    ivCargarImagen.setPickOnBounds(true); 

    // Cargamos la imagen por defecto desde los recursos
    try {
        Image imgCamara = new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/default.png"));
        ivCargarImagen.setImage(imgCamara);
    } catch (Exception e) {
        System.err.println("No se pudo cargar el icono por defecto.");
    }

    // Variable local para almacenar temporalmente los bytes de la nueva imagen
    final byte[][] bytesNuevaImagen = { null };

    // Acción al hacerle clic al ImageView (Abrir buscador de archivos)
    ivCargarImagen.setOnMouseClicked(app -> {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen de Perfil");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        
        // Abre el buscador en la pantalla actual de forma libre
        File archivo = fileChooser.showOpenDialog(ivCargarImagen.getScene().getWindow());
        
        if (archivo != null) {
            try {
                // Convertimos el archivo seleccionado a byte[] inmediatamente
                bytesNuevaImagen[0] = java.nio.file.Files.readAllBytes(archivo.toPath());
                
                // Previsualizamos usando los bytes para asegurar que todo esté correcto
                ByteArrayInputStream bais = new ByteArrayInputStream(bytesNuevaImagen[0]);
                ivCargarImagen.setImage(new Image(bais));
                
                System.out.println("LOG: Imagen cargada en memoria correctamente.");
            } catch (Exception ex) {
                System.err.println("Error al leer los bytes de la imagen: " + ex.getMessage());
            }
        }
    });

    // 3. Crear el campo de texto para el nombre
    TextField name = new TextField();
    name.setPromptText("Nombre del usuario");
    name.setFont(Font.font("Arial", FontWeight.BOLD, 18));
    name.getStyleClass().add("text-field-edicion");
    name.setMaxSize(Double.MAX_VALUE, 50);
    
    name.setTextFormatter(new TextFormatter<>(change -> {
        String nuevoTexto = change.getControlNewText();
        if (nuevoTexto.matches("^[a-zA-Z0-9]*$") && nuevoTexto.length() <= 12) {
            return change; 
        }
        return null; 
    }));

    currentField = name;

    // 4. Armamos la estructura
    container.getChildren().addAll(ivCargarImagen, name);
    rootUsers.getChildren().add(container);
    name.requestFocus();

    // Guardar al presionar ENTER
    name.setOnAction(e -> {
        String text = name.getText().trim();

        if (text.isEmpty()) {
            rootUsers.getChildren().remove(container);
            currentField = null;
        } else {
            UsersDto nuevoDto = new UsersDto();
            nuevoDto.setUserName(text);
            nuevoDto.setUserVersion(1L);
            
            // Inyectamos los bytes reales del arreglo en lugar del String
            if (bytesNuevaImagen[0] != null) {
                nuevoDto.setUserAvatar(bytesNuevaImagen[0]);
            } else {
                nuevoDto.setUserAvatar(null); 
            }

            try {
                // 💾 INTENTO DE GUARDADO EN LA BASE DE DATOS
                // Si el nombre está duplicado en Oracle, el Service lanzará el "throw" aquí
                UsersDto resultadoDto = usersService.guardarUsuario(nuevoDto);

                System.out.println("Controlador: Guardado con éxito en Oracle.");
                
                // Llenar la lista del DTO en la UI antes de ir al juego
                if (resultadoDto.getUsersConfigList() != null && resultadoDto.getUsersConfigList().isEmpty()) {
                    UsersConfigLevelDto nuevoNivelDto = new UsersConfigLevelDto();
                    nuevoNivelDto.setUserLevel(1L);
                    nuevoNivelDto.setUserPoints(0L);
                    nuevoNivelDto.setUserLevelBaseheath(100L);
                    nuevoNivelDto.setUserLevelGundamage(10L);
                    nuevoNivelDto.setUserLevelGunspeed(5L);
                    
                    UsersConfigDto nuevaIntermediaDto = new UsersConfigDto();
                    nuevaIntermediaDto.setUsersConfigLevel(nuevoNivelDto); 
                    
                    resultadoDto.getUsersConfigList().add(nuevaIntermediaDto);
                    System.out.println("Controlador: Lista de configuración inyectada manualmente.");
                }
                
                // Seteamos el usuario listo y armado en el contexto global
                AppContext.getInstance().setUsuarioSeleccionado(resultadoDto);
                
                HBox tarjetaFinal = crearTarjetaUsuario(resultadoDto);
                int index = rootUsers.getChildren().indexOf(container);
                if (index != -1) {
                    rootUsers.getChildren().set(index, tarjetaFinal);
                }
                
                if (LblNombre != null) {
                    LblNombre.textProperty().unbind();
                    LblNombre.textProperty().bind(resultadoDto.userNameProperty());
                }

                currentField = null; // Liberamos el foco con éxito

          } catch (IllegalArgumentException ex) {
    // 1. El sonido se ejecuta de inmediato
    SoundManager.playErrorSound(); 
} catch (Exception exGeneral) {
                // Por si se cae la red o falla Oracle de forma crítica
                System.err.println("Error crítico del sistema: " + exGeneral.getMessage());
                name.setStyle("-fx-text-inner-color: red; -fx-border-color: red;");
                return;
            }
        }
        e.consume(); 
    });

    // Cancelar con ESCAPE
    name.setOnKeyPressed(e -> {
        if (e.getCode() == javafx.scene.input.KeyCode.ESCAPE) {
            rootUsers.getChildren().remove(container);
            currentField = null;
            e.consume();
        }
    });
}




private void irAHomeConAnimacion() {
    try {
        // 1. Obtener el StackPane principal de tu aplicación
        StackPane root = App.getRoot();
        double ancho = root.getWidth();
        double alto = root.getHeight();

        // 2. Tomar la "foto" de la vista actual (Users) antes de cambiar nada
        SnapshotParameters parametros = new SnapshotParameters();
        parametros.setFill(Color.TRANSPARENT);
        WritableImage snapshot = root.snapshot(parametros, null);

        // Mitad Izquierda (Foto de la vista que se va)
        ImageView mitadIzq = new ImageView(snapshot);
        mitadIzq.setViewport(new Rectangle2D(0, 0, ancho / 2, alto));
        
        // Mitad Derecha (Foto de la vista que se va)
        ImageView mitadDer = new ImageView(snapshot);
        mitadDer.setViewport(new Rectangle2D(ancho / 2, 0, ancho / 2, alto));
        mitadDer.setTranslateX(ancho / 2);

        // Forzar la aceleración por tarjeta de video
        mitadIzq.setCache(true);
        mitadDer.setCache(true);
        mitadIzq.setCacheHint(CacheHint.SPEED);
        mitadDer.setCacheHint(CacheHint.SPEED);

        // Contenedor temporal para las dos fotos
        Pane capaPuertas = new Pane(mitadIzq, mitadDer);

        // 3. Cargar la vista nueva (HomeView) usando tu método estático de App
        Parent newView = App.loadFXML("HomeView");

        // 4. LA MAGIA DEL ORDEN EN EL STACKPANE
        root.getChildren().remove(0); // Quita la vista de Users activa
        root.getChildren().add(newView); // Agrega HomeView al fondo (oculta por ahora)
        root.getChildren().add(capaPuertas); // Ponemos las "puertas" encima de todo

        // 5. Configurar la animación de apertura lateral (Puertas Corredizas)
        Duration duracion = Duration.millis(600);
        
        TranslateTransition slideIzq = new TranslateTransition(duracion, mitadIzq);
        slideIzq.setToX(-ancho / 2); // Se va para la izquierda
        slideIzq.setInterpolator(Interpolator.EASE_OUT);

        TranslateTransition slideDer = new TranslateTransition(duracion, mitadDer);
        slideDer.setToX(ancho); // Se va para la derecha
        slideDer.setInterpolator(Interpolator.EASE_OUT);

        // Sincronizar ambas puertas
        ParallelTransition transicionTotal = new ParallelTransition(slideIzq, slideDer);
        
        // Al terminar, limpiamos la capa de fotos y ACTIVAMOS EL AUDIO
        transicionTotal.setOnFinished(e -> {
            root.getChildren().remove(capaPuertas);
            mitadIzq.setImage(null);
            mitadDer.setImage(null);
            
            // 🔥 REPRODUCIR AUDIO ASÍNCRONAMENTE AL TERMINAR LA ANIMACIÓN
          
            
            System.out.println("LOG: Transición a Home limpia y música iniciada.");
        });

        transicionTotal.play();

    } catch (IOException e) {
        System.err.println("Error al cargar la vista o procesar la animación: " + e.getMessage());
        e.printStackTrace();
    }
}

}

