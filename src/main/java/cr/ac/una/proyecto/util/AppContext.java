package cr.ac.una.proyecto.util;
import cr.ac.una.proyecto.model.UsersDto;


//  Obtiene un usuario selecionado para modificar sus datos
public class AppContext {
    private static final AppContext instance = new AppContext();
    private UsersDto usuarioSeleccionado;

    private AppContext() {}

    public static AppContext getInstance() {
        return instance;
    }

    public UsersDto getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(UsersDto usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }
}
