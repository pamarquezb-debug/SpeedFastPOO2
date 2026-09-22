import controlador.ControladorPedidos;
import vista.VentanaPrincipal;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ControladorPedidos controlador =
                    new ControladorPedidos();

            VentanaPrincipal ventana =
                    new VentanaPrincipal(controlador);

            ventana.setVisible(true);
        });
    }
}