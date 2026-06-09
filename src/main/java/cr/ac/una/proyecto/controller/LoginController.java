package cr.ac.una.proyecto.controller;
import cr.ac.una.proyecto.App;
import cr.ac.una.proyecto.model.Users;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class LoginController implements Initializable {
  private final UsersService usersService = new UsersService();

  private Label LblNombre; 
    private TextField currentField;

    @FXML
    private VBox rootUsers;
    @FXML
    private ScrollPane scrollUsuarios;
    @FXML
    private Button btnNew;
        
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
        cargarNombresDesdeBD();
       SoundManager.cargarEfectosError();

    }
    
    //Carga de la base de datos y los agrega en los hbox
private void cargarNombresDesdeBD() {
    rootUsers.getChildren().clear();
    List<Users> listaUsuarios = usersService.getUsers();
    
    if (listaUsuarios == null || listaUsuarios.isEmpty()) {
        return;
    }

    for (Users user : listaUsuarios) {
        UsersDto dto = new UsersDto(user);
        HBox tarjeta = crearTarjetaUsuario(dto);
        rootUsers.getChildren().add(tarjeta);
    }
    scrollUsuarios.setHvalue(0.0);
   
}
     // Crea los HBox en los cuales van los usuarios
     private HBox crearTarjetaUsuario(UsersDto dto) {
    HBox container = new HBox(10); 
    container.getStyleClass().addAll("fx-FondoJuego11","fx-gameButton");

 container.setMaxSize(Double.MAX_VALUE,30);
 container.setSpacing(30);
 container.setStyle("-fx-alignment: CENTER; -fx-padding: 15;"); 

    ImageView avatar = new ImageView();
    try {
        // La imagen en bytes para guardar en base de datos
        byte[] bytesImagen = dto.getUserAvatar(); 
        
        if (bytesImagen != null && bytesImagen.length > 0) {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytesImagen);
            avatar.setImage(new Image(bais));
        } else {
            avatar.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/default.png")));
        }
        
        avatar.setFitWidth(70);
        avatar.setFitHeight(70);
        avatar.setPreserveRatio(true);
        
    } catch (Exception ex) {
        avatar.setImage(new Image(getClass().getResourceAsStream("/cr/ac/una/proyecto/resources/default.png")));
    }

    Label lblName = new Label();
    lblName.textProperty().bind(dto.userNameProperty());
    lblName.getStyleClass().add("label-usuario"); 
   
    HBox seccionNivel = new HBox(5);
    seccionNivel.setStyle("-fx-alignment: CENTER;"); 
    Label lblTextoNivel = new Label("Nivel: ");
    lblTextoNivel.getStyleClass().add("label-usuario"); 
  
    
    Label lblNumeroNivel = new Label();
    lblNumeroNivel.getStyleClass().add("label-usuario"); 
    lblNumeroNivel.setStyle("-fx-text-fill: #ffcc00"); 
    
    if (dto.getUsersConfigList() != null && !dto.getUsersConfigList().isEmpty()) {
        UsersConfigLevelDto nivelDto = dto.getUsersConfigList().get(0).getUsersConfigLevel();
        if (nivelDto != null) {
            lblNumeroNivel.textProperty().bind(nivelDto.userLevelProperty().asString());
        }
    } else {
        lblNumeroNivel.setText("1");
    } 
    seccionNivel.getChildren().addAll(lblTextoNivel, lblNumeroNivel);
    container.getChildren().addAll(avatar, lblName, seccionNivel);
    
    container.setOnMouseEntered(mouseEvent -> {
        container.setScaleX(1.05);
        container.setScaleY(1.05);
        container.setCursor(javafx.scene.Cursor.HAND);
    });
    
    container.setOnMouseExited(mouseEvent -> {
        container.setStyle("-fx-alignment: CENTER; -fx-padding: 15;");
        container.setScaleX(1.0);
        container.setScaleY(1.0);
    });
    
    container.setOnMouseClicked(event -> {
        if (event.getClickCount() == 1) {
            if (LblNombre != null) {
                LblNombre.textProperty().unbind();
                LblNombre.textProperty().bind(dto.userNameProperty());
            }
        } else if (event.getClickCount() == 2) {
            // Al dar doble click se agrega el usuario selecionado
            AppContext.getInstance().setUsuarioSeleccionado(dto);   
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

    HBox container = new HBox(15);
    container.getStyleClass().addAll("fx-FondoJuego11","fx-gameButton"); 
    container.setStyle("-fx-alignment: CENTER;"); 

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
    }

    final byte[][] bytesNuevaImagen = { null };

    ivCargarImagen.setOnMouseClicked(app -> {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen de Perfil");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        
       
        File archivo = fileChooser.showOpenDialog(ivCargarImagen.getScene().getWindow());
        if (archivo != null) {
            try {
                bytesNuevaImagen[0] = java.nio.file.Files.readAllBytes(archivo.toPath());
                ByteArrayInputStream bais = new ByteArrayInputStream(bytesNuevaImagen[0]);
                ivCargarImagen.setImage(new Image(bais));

            } catch (Exception ex) {
            }
        }
    });

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

            if (bytesNuevaImagen[0] != null) {
                nuevoDto.setUserAvatar(bytesNuevaImagen[0]);
            } else {
                nuevoDto.setUserAvatar(null); 
            }

            try {        
                // Llena los datos 
                UsersDto resultadoDto = usersService.guardarUsuario(nuevoDto);

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
                }
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
                currentField = null; 

       } catch (IllegalArgumentException ex) {
            SoundManager.playErrorSound();
            name.setStyle("-fx-text-inner-color: red; -fx-border-color: red;");
            
            mostrarAlerta(Alert.AlertType.ERROR, "Nombre No Válido", 
                "¡Ese nombre ya existe! Por favor, elige un nombre de usuario diferente.","Aceptar");
            return;

        } catch (IllegalStateException ex) { 
            SoundManager.playErrorSound();
            name.setStyle("-fx-text-inner-color: red; -fx-border-color: red;");
            
            mostrarAlerta(Alert.AlertType.WARNING, "Nombre Duplicado", 
                "El nombre ingresado no es válido. Asegúrate de no usar caracteres especiales o dejarlo vacío.","Aceptar");
            return;

        } catch (Exception exGeneral) {
            SoundManager.playErrorSound();
            exGeneral.printStackTrace();
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
        StackPane root = App.getRoot();
        double ancho = root.getWidth();
        double alto = root.getHeight();

        SnapshotParameters parametros = new SnapshotParameters();
        parametros.setFill(Color.TRANSPARENT);
        WritableImage snapshot = root.snapshot(parametros, null);

        ImageView mitadIzq = new ImageView(snapshot);
        mitadIzq.setViewport(new Rectangle2D(0, 0, ancho / 2, alto));
        
        ImageView mitadDer = new ImageView(snapshot);
        mitadDer.setViewport(new Rectangle2D(ancho / 2, 0, ancho / 2, alto));
        mitadDer.setTranslateX(ancho / 2);

        mitadIzq.setCache(true);
        mitadDer.setCache(true);
        mitadIzq.setCacheHint(CacheHint.SPEED);
        mitadDer.setCacheHint(CacheHint.SPEED);

        Pane capaPuertas = new Pane(mitadIzq, mitadDer);

        Parent newView = App.loadFXML("HomeView");

        root.getChildren().remove(0); 
        root.getChildren().add(newView);
        root.getChildren().add(capaPuertas); 

        Duration duracion = Duration.millis(600);
        
        TranslateTransition slideIzq = new TranslateTransition(duracion, mitadIzq);
        slideIzq.setToX(-ancho / 2);
        slideIzq.setInterpolator(Interpolator.EASE_OUT);

        TranslateTransition slideDer = new TranslateTransition(duracion, mitadDer);
        slideDer.setToX(ancho); 
        slideDer.setInterpolator(Interpolator.EASE_OUT);

        ParallelTransition transicionTotal = new ParallelTransition(slideIzq, slideDer);

        transicionTotal.setOnFinished(e -> {
            root.getChildren().remove(capaPuertas);
            mitadIzq.setImage(null);
            mitadDer.setImage(null);
        });
        transicionTotal.play();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
private boolean mostrarAlerta(Alert.AlertType tipoAlerta, String titulo, String encabezado, String textoBotonAceptar) {
    Alert alert = new Alert(tipoAlerta);
    alert.setTitle(titulo);
    alert.setHeaderText(encabezado);

    ButtonType botonAceptar = new ButtonType(textoBotonAceptar);
    alert.getButtonTypes().setAll(botonAceptar);

    DialogPane dialogPane = alert.getDialogPane();
    try {
        dialogPane.getStylesheets().add(getClass().getResource("/cr/ac/una/proyecto/view/Style.css").toExternalForm());
        dialogPane.getStyleClass().addAll("fx-FondoJuego11", "label-usuario");
    } catch (Exception e) {
       
    }

    Optional<ButtonType> result = alert.showAndWait();

    return result.isPresent() && result.get() == botonAceptar;
}
}

