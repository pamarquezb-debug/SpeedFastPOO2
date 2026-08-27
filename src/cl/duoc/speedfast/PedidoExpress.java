package cl.duoc.speedfast;

/**
 * Representa un pedido de compra express.
 *
 * @author Pablo Marquez
 * @version 3.0
 */
public class PedidoExpress extends Pedido {

    /**
     * Constructor de PedidoExpress.
     *
     * @param idPedido identificador del pedido
     * @param direccionEntrega dirección de entrega
     * @param distanciaKm distancia en kilómetros
     */
    public PedidoExpress(
            int idPedido,
            String direccionEntrega,
            double distanciaKm) {

        super(idPedido, direccionEntrega, distanciaKm);
    }

    /**
     * Asigna automáticamente el repartidor disponible
     * más cercano.
     */
    @Override
    public void asignarRepartidor() {

        repartidorAsignado = "Camila Soto";

        System.out.println(
                "Asignación automática para Pedido Express."
        );

        System.out.println(
                "Repartidor más cercano: "
                        + repartidorAsignado
        );

        System.out.println(
                "Validación: disponibilidad inmediata."
        );
    }

    /**
     * Calcula el tiempo estimado para un pedido express.
     *
     * El tiempo base corresponde a 10 minutos.
     * Si la distancia supera los 5 km se agregan
     * 5 minutos adicionales.
     *
     * @return tiempo estimado en minutos
     */
    @Override
    public int calcularTiempoEntrega() {

        int tiempo = 10;

        if (distanciaKm > 5) {
            tiempo += 5;
        }

        return tiempo;
    }
}