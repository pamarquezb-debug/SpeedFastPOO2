package cl.duoc.speedfast;

/**
 * Representa un pedido de comida dentro del sistema SpeedFast.
 *
 * El tiempo estimado de entrega corresponde a 15 minutos
 * base más 2 minutos por cada kilómetro de distancia.
 *
 * @author Pablo Marquez
 * @version 2.0
 */
public class PedidoComida extends Pedido {

    /**
     * Constructor de un pedido de comida.
     *
     * @param idPedido identificador único del pedido
     * @param direccionEntrega dirección de entrega
     * @param distanciaKm distancia de entrega en kilómetros
     */
    public PedidoComida(
            int idPedido,
            String direccionEntrega,
            double distanciaKm) {

        super(idPedido, direccionEntrega, distanciaKm);
    }

    /**
     * Calcula el tiempo estimado de entrega para un pedido de comida.
     *
     * Fórmula:
     * 15 minutos base + 2 minutos por kilómetro.
     *
     * @return tiempo estimado en minutos
     */
    @Override
    public int calcularTiempoEntrega() {

        return (int) Math.round(
                15 + (2 * distanciaKm)
        );
    }
}