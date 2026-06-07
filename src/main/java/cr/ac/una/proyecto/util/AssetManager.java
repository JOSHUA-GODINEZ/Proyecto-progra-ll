package cr.ac.una.proyecto.util;

import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.media.AudioClip;

public class AssetManager {

   
    private static final Map<String, Image> imagenes = new HashMap<>();
    private static final Map<String, AudioClip> audios = new HashMap<>();

    public static void precargarTodo() {

        try {
           /* imagenes.put("avatar_defecto", new Image(AssetManager.class.getResourceAsStream("/cr/ac/una/proyecto/resources/default.png")));
            imagenes.put("tutorial_1", new Image(AssetManager.class.getResourceAsStream("/cr/ac/una/proyecto/resources/Tutorial/paso1.png")));
            imagenes.put("tutorial_2", new Image(AssetManager.class.getResourceAsStream("/cr/ac/una/proyecto/resources/Tutorial/paso2.png")));
            imagenes.put("tutorial_3", new Image(AssetManager.class.getResourceAsStream("/cr/ac/una/proyecto/resources/Tutorial/paso3.png")));
            imagenes.put("tutorial_4", new Image(AssetManager.class.getResourceAsStream("/cr/ac/una/proyecto/resources/Tutorial/paso4.png")));
            imagenes.put("tutorial_5", new Image(AssetManager.class.getResourceAsStream("/cr/ac/una/proyecto/resources/Tutorial/paso5.png")));
*/
         
            audios.put("audioFondo1", new AudioClip(AssetManager.class.getResource("/cr/ac/una/proyecto/resources/Audios/MusicBackground2.wav").toExternalForm()));
            //audios.put("error_alerta", new AudioClip(AssetManager.class.getResource("/cr/ac/una/proyecto/resources/Audios/Error.wav").toExternalForm()));

         
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Image getImagen(String clave) {
        return imagenes.get(clave);
    }

    public static AudioClip getAudio(String clave) {
        return audios.get(clave);
    }
}