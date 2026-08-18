package cl.duoc.speedfast;

/**
 * Representa un pedido de compra express.
 *
 * El tiempo base es de 10 minutos.
 * Si la distancia supera los 5 kilómetros,
 * se agregan 5 minutos adicionales.
 *
 * @author Pablo Marquez
 * @version 2.0
 */
public class PedidoExpress extends Pedido {

    /**
     * Constructor de un pedido express.
     *
     * @param idPedido identificador único del pedido
     * @param direccionEntrega dirección de entrega
     * @param distanciaKm distancia de entrega en kilómetros
     */
    public PedidoExpress(
            int idPedido,
            String direccionEntrega,
            double distanciaKm) {

        super(idPedido, direccionEntrega, distanciaKm);
    }

    /**
     * Calcula el tiempo estimado de entrega del pedido express.
     *
     * El tiempo base es de 10 minutos.
     * Si la distancia es mayor a 5 kilómetros,
     * se agregan 5 minutos adicionales.
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