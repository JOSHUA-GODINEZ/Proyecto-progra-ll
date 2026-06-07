
package cr.ac.una.proyecto.util;

import javafx.scene.image.Image;

class Animation {
    Image image;
    int frames;
    double frameWidth;
    double frameHeight;
    long frameSpeed;

    //  Maneja las animaciones de los enemigos
    public Animation(Image img, int frames, long frameSpeed) {
        this.image = img;
        this.frames = frames;
        this.frameSpeed = frameSpeed;
        
        // Adapata segun tamaño de la imagen
        this.frameWidth = img.getWidth() / frames;
        this.frameHeight = img.getHeight();
    }
}
