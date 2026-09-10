package cl.duoc.speedfast;

/**
 * Representa los posibles estados de un pedido
 * dentro del sistema SpeedFast.
 *
 * @author Pablo Marquez
 * @version 5.0
 */
public enum EstadoPedido {

    /**
     * Pedido disponible en la zona de carga.
     */
    PENDIENTE,

    /**
     * Pedido retirado por un repartidor
     * y actualmente en proceso de entrega.
     */
    EN_REPARTO,

    /**
     * Pedido entregado correctamente.
     */
    ENTREGADO
}