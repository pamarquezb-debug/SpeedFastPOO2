package cl.duoc.speedfast;

/**
 * Representa un pedido dentro del sistema de
 * distribución de SpeedFast.
 *
 * Cada pedido posee un identificador, una dirección
 * de entrega y un estado asociado.
 *
 * @author Pablo Marquez
 * @version 5.0
 */
public class Pedido {

    /** Identificador único del pedido. */
    private int id;

    /** Dirección donde debe realizarse la entrega. */
    private String direccionEntrega;

    /** Estado actual del pedido. */
    private EstadoPedido estado;

    /**
     * Constructor de la clase Pedido.
     *
     * Todo nuevo pedido comienza inicialmente
     * con estado PENDIENTE.
     *
     * @param id identificador del pedido
     * @param direccionEntrega dirección de entrega
     */
    public Pedido(int id, String direccionEntrega) {

        this.id = id;
        this.direccionEntrega = direccionEntrega;
        this.estado = EstadoPedido.PENDIENTE;
    }

    /**
     * Retorna el identificador del pedido.
     *
     * @return identificador del pedido
     */
    public int getId() {
        return id;
    }

    /**
     * Modifica el identificador del pedido.
     *
     * @param id nuevo identificador
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna la dirección de entrega.
     *
     * @return dirección de entrega
     */
    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    /**
     * Modifica la dirección de entrega.
     *
     * @param direccionEntrega nueva dirección
     */
    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    /**
     * Retorna el estado actual del pedido.
     *
     * @return estado del pedido
     */
    public EstadoPedido getEstado() {
        return estado;
    }

    /**
     * Modifica el estado utilizando directamente
     * un valor del enum EstadoPedido.
     *
     * @param estado nuevo estado
     */
    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    /**
     * Modifica el estado utilizando una cadena de texto.
     *
     * Este método se incluye para cumplir con el
     * requerimiento de la actividad.
     *
     * @param nuevoEstado nuevo estado del pedido
     */
    public void setEstado(String nuevoEstado) {

        this.estado = EstadoPedido.valueOf(
                nuevoEstado.toUpperCase()
        );
    }

    /**
     * Retorna una representación textual del pedido.
     *
     * @return información del pedido
     */
    @Override
    public String toString() {

        return "Pedido #" + id
                + " | Dirección: " + direccionEntrega
                + " | Estado: " + estado;
    }
}