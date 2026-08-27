package cl.duoc.speedfast;

/**
 * Representa un pedido de comida de SpeedFast.
 *
 * @author Pablo Marquez
 * @version 3.0
 */
public class PedidoComida extends Pedido {

    /**
     * Constructor de PedidoComida.
     *
     * @param idPedido identificador del pedido
     * @param direccionEntrega dirección de entrega
     * @param distanciaKm distancia en kilómetros
     */
    public PedidoComida(
            int idPedido,
            String direccionEntrega,
            double distanciaKm) {

        super(idPedido, direccionEntrega, distanciaKm);
    }

    /**
     * Realiza la asignación automática de un repartidor
     * considerando que debe utilizar mochila térmica.
     */
    @Override
    public void asignarRepartidor() {

        repartidorAsignado = "Luis Díaz";

        System.out.println(
                "Asignación automática para Pedido Comida."
        );

        System.out.println(
                "Repartidor: " + repartidorAsignado
        );

        System.out.println(
                "Validación: repartidor con mochila térmica."
        );
    }

    /**
     * Calcula el tiempo estimado de entrega.
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