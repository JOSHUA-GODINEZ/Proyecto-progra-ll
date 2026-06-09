package cr.ac.una.proyecto.util;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class SoundManager {

//Volumen
    private static DoubleProperty menuVolume = new SimpleDoubleProperty(0.15);
    private static DoubleProperty enemyVolume = new SimpleDoubleProperty(0.10);
    private static DoubleProperty gunVolume = new SimpleDoubleProperty(0.25);
    private static DoubleProperty efectVolume = new SimpleDoubleProperty(0.5);

 //Audios
    private static MediaPlayer fondo1Player;
    private static MediaPlayer fondo1Player2;
    private static AudioClip walkClip;
    private static AudioClip botonEfecto; 
    private static AudioClip errorEfecto;
    private static AudioClip winEfecto;
    private static AudioClip loseEfecto;
    private static boolean walking = false;
   

   //Listener de barra de volumen
    static {
        // Escucha cambios en la música de fondo
        menuVolume.addListener((obs, oldV, newV) -> {
        
            if (fondo1Player != null) { // 🔥 Sincroniza el volumen del MediaPlayer de fondo
                fondo1Player.setVolume(newV.doubleValue());
            }
              if (fondo1Player2 != null) { // 🔥 Sincroniza el volumen del MediaPlayer de fondo
                fondo1Player2.setVolume(newV.doubleValue());
            }
        });
        
        // Escucha cambios en los enemigos
        enemyVolume.addListener((obs, oldV, newV) -> {
            if (walkClip != null && walking) {
                walkClip.setVolume(newV.doubleValue());
            }
        });

        // Escucha cambios en los efectos 
        efectVolume.addListener((obs, oldV, newV) -> {
            if (botonEfecto != null) {
                botonEfecto.setVolume(newV.doubleValue());
            }
            if (errorEfecto != null) { 
                errorEfecto.setVolume(newV.doubleValue());
            }
            if (winEfecto != null) {
                winEfecto.setVolume(newV.doubleValue());
            }
            if (loseEfecto != null) { 
                loseEfecto.setVolume(newV.doubleValue());
            }
        });
    }

    //Metodos para inicializar los audios
    public static void initWalkSound() {
        try {
            walkClip = new AudioClip(
                SoundManager.class.getResource("/cr/ac/una/proyecto/resources/Audios/walk.wav").toExternalForm()
            );
        } catch (Exception e) {
        }
    }
    
        public static void playWalk() {
        if (!walking && walkClip != null) {
            walkClip.setCycleCount(AudioClip.INDEFINITE);
            walkClip.play(getWalkVolume());
            walking = true;
        }
    }

    public static void stopWalk() {
        if (walking && walkClip != null) {
            walkClip.stop();
            walking = false;
        }
    }

    public static void cargarEfectosBotones() {
        try {
            URL url = SoundManager.class.getResource("/cr/ac/una/proyecto/resources/Audios/LevelUp.wav");
            if (url != null) {
                botonEfecto = new AudioClip(url.toExternalForm());
                botonEfecto.setVolume(getEfectVolume()); 
            } 
        } catch (Exception e) {
        }
    }

    public static void cargarEfectosError() {
        try {
            URL url = SoundManager.class.getResource("/cr/ac/una/proyecto/resources/Audios/error.wav");
            if (url != null) {
                errorEfecto = new AudioClip(url.toExternalForm());
                errorEfecto.setVolume(getEfectVolume()); 
            }
        } catch (Exception e) {
        }
    }

    public static void cargarEfectosWin() {
        try {
            URL url = SoundManager.class.getResource("/cr/ac/una/proyecto/resources/Audios/win.wav");
            if (url != null) {
                winEfecto = new AudioClip(url.toExternalForm());
                winEfecto.setVolume(getEfectVolume()); 
            }
        } catch (Exception e) {    
        }
    }

    public static void cargarEfectosLose() {
        try {
            URL url = SoundManager.class.getResource("/cr/ac/una/proyecto/resources/Audios/lose.wav");
            if (url != null) {
                loseEfecto = new AudioClip(url.toExternalForm());
                loseEfecto.setVolume(getEfectVolume()); 
            } 
        } catch (Exception e) {        
        }
    }

// Musica del menu
public static void playFondo1Sound() {
        if (fondo1Player == null) {
            try {
                URL url = SoundManager.class.getResource("/cr/ac/una/proyecto/resources/Audios/MusicBackground2.wav");
                if (url != null) {
                    Media media = new Media(url.toExternalForm());
                    fondo1Player = new MediaPlayer(media);
                    fondo1Player.setCycleCount(MediaPlayer.INDEFINITE); 
                } 
            } catch (Exception e) {   
                return;
            }
        }
        fondo1Player.setVolume(getMenuVolume());

        if (fondo1Player.getStatus() != MediaPlayer.Status.PLAYING) {       
            fondo1Player.play();
        }
    }

    public static void stopFondo1Sound() {
        if (fondo1Player != null) {
            fondo1Player.stop(); 
         // reinicia el audio
            fondo1Player.seek(javafx.util.Duration.ZERO);           
        }
    }

    public static void pauseFondo1Sound() {
        if (fondo1Player != null && fondo1Player.getStatus() == MediaPlayer.Status.PLAYING) {
            fondo1Player.pause(); 
        }
    }

    
  public static void playFondo1Sound2() {
        if (fondo1Player2 == null) {
            try {
                URL url = SoundManager.class.getResource("/cr/ac/una/proyecto/resources/Audios/MusicBackground1.wav");
                if (url != null) {
                    Media media = new Media(url.toExternalForm());
                    fondo1Player2 = new MediaPlayer(media);
                    fondo1Player2.setCycleCount(MediaPlayer.INDEFINITE); 
                }
            } catch (Exception e) {      
                return;
            }
        }
        fondo1Player2.setVolume(getMenuVolume());
        if (fondo1Player2.getStatus() != MediaPlayer.Status.PLAYING) {
            fondo1Player2.play();
        }
    }

    public static void stopFondo1Sound2() {
        if (fondo1Player2 != null) {
            fondo1Player2.stop(); 
          //reinicia el audio 
            fondo1Player2.seek(javafx.util.Duration.ZERO);  
        }
    }

    public static void pauseFondo1Sound2() {
        if (fondo1Player2 != null && fondo1Player2.getStatus() == MediaPlayer.Status.PLAYING) {
            fondo1Player2.pause();    
        }
    }
    
    
  //Reproduce los audios
    public static void playBotonSound() {
        if (botonEfecto != null) {
            botonEfecto.play(); 
        }
    }

    public static void playErrorSound() {
        if (errorEfecto != null) {
            errorEfecto.play(); 
        }
    }

    public static void playWin() {
        if (winEfecto != null) {
            winEfecto.play(); 
        }
    }

    public static void playLose() {
        if (loseEfecto != null) {
            loseEfecto.play(); 
        }
    }

    // Getter y Setters con property
    public static DoubleProperty menuVolumeProperty() { return menuVolume; }
    public static double getMenuVolume() { return menuVolume.get(); }
    public static void setmenuVolume(double v) { menuVolume.set(v); }

    public static DoubleProperty getEnemyVolumeProperty() { return enemyVolume; }
    public static Double getEnemyVolume() { return enemyVolume.get(); }
    public static void setEnemyVolume(DoubleProperty enemyVolume) { SoundManager.enemyVolume = enemyVolume; }

    public static DoubleProperty getGunVolumeProperty() { return gunVolume; }
    public static Double getGunVolume() { return gunVolume.get(); }
    public static void setGunVolume(DoubleProperty gunVolume) { SoundManager.gunVolume = gunVolume; }

    public static DoubleProperty getEfectVolumeProperty() { return efectVolume; }
    public static Double getEfectVolume() { return efectVolume.get(); }
    public static void setEfectVolume(DoubleProperty efectVolume) { SoundManager.efectVolume = efectVolume; }

    public static double getWalkVolume() { return enemyVolume.get()/3; }
    public static DoubleProperty walkVolumeProperty() { return enemyVolume; }
}