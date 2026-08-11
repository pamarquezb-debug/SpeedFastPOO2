package cl.duoc.speedfast;

/**
 * Representa un pedido de comida realizado a través de SpeedFast.
 * <p>
 * Los pedidos de comida requieren que el repartidor disponga
 * de una mochila térmica para mantener la temperatura del producto.
 * </p>
 *
 * @author Pablo Marquez
 * @version 1.0
 */
public class PedidoComida extends Pedido {

    /**
     * Constructor de un pedido de comida.
     *
     * @param idPedido identificador único del pedido
     * @param direccionEntrega dirección donde se entregará el pedido
     */
    public PedidoComida(int idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Comida");
    }

    /**
     * Asigna un repartidor para un pedido de comida.
     * <p>
     * Sobrescribe el método definido en {@link Pedido} para
     * considerar el requisito de mochila térmica.
     * </p>
     */
    @Override
    public void asignarRepartidor() {
        System.out.println(
                "Buscando repartidor disponible con mochila térmica..."
        );
    }

    /**
     * Asigna un repartidor específico al pedido de comida.
     *
     * @param nombreRepartidor nombre del repartidor asignado
     */
    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println(
                "Repartidor " + nombreRepartidor
                        + " asignado al pedido de comida."
        );

        System.out.println(
                "Validación: el repartidor debe contar con mochila térmica."
        );
    }
}