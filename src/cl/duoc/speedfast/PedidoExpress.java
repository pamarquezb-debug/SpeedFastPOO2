package cl.duoc.speedfast;

/**
 * Representa un pedido de compra express de SpeedFast.
 * <p>
 * Este tipo de servicio corresponde principalmente a compras
 * realizadas en supermercados o farmacias y requiere asignar
 * al repartidor disponible más cercano.
 * </p>
 *
 * @author Pablo Marquez
 * @version 1.0
 */
public class PedidoExpress extends Pedido {

    /**
     * Constructor de un pedido express.
     *
     * @param idPedido identificador único del pedido
     * @param direccionEntrega dirección donde se realizará la entrega
     */
    public PedidoExpress(int idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Compra Express");
    }

    /**
     * Busca un repartidor para el pedido express.
     * <p>
     * Sobrescribe el método de la clase base para buscar
     * al repartidor más cercano con disponibilidad inmediata.
     * </p>
     */
    @Override
    public void asignarRepartidor() {
        System.out.println(
                "Buscando el repartidor más cercano "
                        + "con disponibilidad inmediata..."
        );
    }

    /**
     * Asigna un repartidor específico al pedido express.
     *
     * @param nombreRepartidor nombre del repartidor asignado
     */
    @Override
    public void asignarRepartidor(String nombreRepartidor) {

        System.out.println(
                "Repartidor " + nombreRepartidor
                        + " asignado a la compra express."
        );

        System.out.println(
                "Validación: repartidor cercano "
                        + "y disponible inmediatamente."
        );
    }
}