package cl.duoc.speedfast;

/**
 * Representa un pedido de encomienda.
 *
 * @author Pablo Marquez
 * @version 3.0
 */
public class PedidoEncomienda extends Pedido {

    /**
     * Constructor de PedidoEncomienda.
     *
     * @param idPedido identificador del pedido
     * @param direccionEntrega dirección de entrega
     * @param distanciaKm distancia en kilómetros
     */
    public PedidoEncomienda(
            int idPedido,
            String direccionEntrega,
            double distanciaKm) {

        super(idPedido, direccionEntrega, distanciaKm);
    }

    /**
     * Asigna automáticamente un repartidor para
     * una encomienda.
     */
    @Override
    public void asignarRepartidor() {

        repartidorAsignado = "Daniela Tapia";

        System.out.println(
                "Asignación automática para Pedido Encomienda."
        );

        System.out.println(
                "Repartidor: " + repartidorAsignado
        );

        System.out.println(
                "Validación: peso y embalaje revisados."
        );
    }

    /**
     * Calcula el tiempo estimado de entrega.
     *
     * Fórmula:
     * 20 minutos base + 1.5 minutos por kilómetro.
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