package cl.duoc.speedfast;

/**
 * Representa un pedido correspondiente al envío de una encomienda.
 * <p>
 * Para este tipo de pedido se debe considerar el peso y
 * verificar que el embalaje sea adecuado para su transporte.
 * </p>
 *
 * @author Pablo Marquez
 * @version 1.0
 */
public class PedidoEncomienda extends Pedido {

    /** Peso de la encomienda expresado en kilogramos. */
    private double peso;

    /**
     * Constructor de un pedido de encomienda.
     *
     * @param idPedido identificador único del pedido
     * @param direccionEntrega dirección donde se entregará la encomienda
     * @param peso peso de la encomienda en kilogramos
     */
    public PedidoEncomienda(
            int idPedido,
            String direccionEntrega,
            double peso) {

        super(idPedido, direccionEntrega, "Encomienda");
        this.peso = peso;
    }

    /**
     * Asigna un repartidor para una encomienda.
     * <p>
     * Sobrescribe el comportamiento de la clase base indicando
     * que deben validarse el peso y el embalaje.
     * </p>
     */
    @Override
    public void asignarRepartidor() {
        System.out.println(
                "Buscando repartidor para encomienda..."
        );

        System.out.println(
                "Se debe validar el peso y el embalaje."
        );
    }

    /**
     * Asigna un repartidor específico a la encomienda y muestra
     * las validaciones requeridas.
     *
     * @param nombreRepartidor nombre del repartidor asignado
     */
    @Override
    public void asignarRepartidor(String nombreRepartidor) {

        System.out.println(
                "Repartidor " + nombreRepartidor
                        + " asignado a la encomienda."
        );

        System.out.println(
                "Peso de la encomienda: " + peso + " kg."
        );

        System.out.println(
                "Validación de peso y embalaje realizada."
        );
    }
}