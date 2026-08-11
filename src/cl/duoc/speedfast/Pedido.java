package cl.duoc.speedfast;

/**
 * Clase base que representa un pedido dentro del sistema SpeedFast.
 * <p>
 * Contiene la información común de todos los tipos de pedidos y define
 * los métodos utilizados para realizar la asignación de repartidores.
 * </p>
 *
 * @author Pablo Marquez
 * @version 1.0
 */
public class Pedido {

    /** Identificador único del pedido. */
    protected int idPedido;

    /** Dirección donde debe ser entregado el pedido. */
    protected String direccionEntrega;

    /** Tipo de pedido registrado en el sistema. */
    protected String tipoPedido;

    /**
     * Constructor de la clase Pedido.
     *
     * @param idPedido identificador único del pedido
     * @param direccionEntrega dirección donde se realizará la entrega
     * @param tipoPedido tipo de pedido
     */
    public Pedido(int idPedido, String direccionEntrega, String tipoPedido) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
    }

    /**
     * Realiza una asignación genérica de repartidor.
     * <p>
     * Este método puede ser sobrescrito por las clases derivadas
     * para implementar un comportamiento específico.
     * </p>
     */
    public void asignarRepartidor() {
        System.out.println("Asignando un repartidor al pedido...");
    }

    /**
     * Asigna un repartidor utilizando su nombre.
     * <p>
     * Este método corresponde a una sobrecarga de
     * {@link #asignarRepartidor()}.
     * </p>
     *
     * @param nombreRepartidor nombre del repartidor que será asignado
     */
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Repartidor asignado: " + nombreRepartidor);
    }

    /**
     * Muestra en consola los datos principales del pedido.
     */
    public void mostrarDatos() {
        System.out.println("ID Pedido: " + idPedido);
        System.out.println("Dirección: " + direccionEntrega);
        System.out.println("Tipo de pedido: " + tipoPedido);
    }
}