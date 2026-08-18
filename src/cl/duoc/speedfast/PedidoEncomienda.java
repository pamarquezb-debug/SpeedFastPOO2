package cl.duoc.speedfast;

/**
 * Representa un pedido de encomienda dentro del sistema SpeedFast.
 *
 * El tiempo estimado corresponde a 20 minutos base
 * más 1.5 minutos por cada kilómetro recorrido.
 *
 * @author Pablo Marquez
 * @version 2.0
 */
public class PedidoEncomienda extends Pedido {

    /**
     * Constructor de un pedido de encomienda.
     *
     * @param idPedido identificador único del pedido
     * @param direccionEntrega dirección de entrega
     * @param distanciaKm distancia de entrega en kilómetros
     */
    public PedidoEncomienda(
            int idPedido,
            String direccionEntrega,
            double distanciaKm) {

        super(idPedido, direccionEntrega, distanciaKm);
    }

    /**
     * Calcula el tiempo estimado de entrega para una encomienda.
     *
     * Fórmula:
     * 20 minutos base + 1.5 minutos por kilómetro.
     *
     * El resultado se redondea a un valor entero.
     *
     * @return tiempo estimado en minutos
     */
    @Override
    public int calcularTiempoEntrega() {

        return (int) Math.round(
                20 + (1.5 * distanciaKm)
        );
    }
}