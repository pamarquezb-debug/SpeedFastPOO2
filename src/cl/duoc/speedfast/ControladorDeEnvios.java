package cl.duoc.speedfast;

import java.util.ArrayList;

/**
 * Controla el historial de entregas realizadas
 * en el sistema SpeedFast.
 *
 * Implementa la interfaz Rastreable.
 *
 * @author Pablo Marquez
 * @version 3.0
 */
public class ControladorDeEnvios implements Rastreable {

    /** Historial de pedidos procesados. */
    private final ArrayList<String> historial;

    /**
     * Constructor del controlador.
     */
    public ControladorDeEnvios() {
        historial = new ArrayList<>();
    }

    /**
     * Registra un pedido dentro del historial.
     *
     * @param pedido pedido que será registrado
     */
    public void registrarEntrega(Pedido pedido) {

        String registro =
                pedido.getClass().getSimpleName()
                        + " #" + pedido.getIdPedido()
                        + " - " + pedido.getEstado()
                        + " por "
                        + pedido.getRepartidorAsignado();

        historial.add(registro);
    }

    /**
     * Muestra el historial de entregas registradas.
     */
    @Override
    public void verHistorial() {

        System.out.println();
        System.out.println("===== HISTORIAL =====");

        if (historial.isEmpty()) {

            System.out.println(
                    "No existen pedidos registrados."
            );

            return;
        }

        for (String registro : historial) {
            System.out.println("- " + registro);
        }
    }
}