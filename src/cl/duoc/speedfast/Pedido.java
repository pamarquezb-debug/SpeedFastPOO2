package cl.duoc.speedfast;

/**
 * Clase abstracta que representa un pedido genérico
 * dentro del sistema SpeedFast.
 *
 * Contiene los atributos y comportamientos comunes
 * para todos los tipos de pedidos.
 *
 * @author Pablo Marquez
 * @version 2.0
 */
public abstract class Pedido {

    /** Identificador único del pedido. */
    protected int idPedido;

    /** Dirección donde se realizará la entrega. */
    protected String direccionEntrega;

    /** Distancia del pedido expresada en kilómetros. */
    protected double distanciaKm;

    /**
     * Constructor de la clase Pedido.
     *
     * @param idPedido identificador único del pedido
     * @param direccionEntrega dirección donde se realizará la entrega
     * @param distanciaKm distancia de entrega expresada en kilómetros
     */
    public Pedido(int idPedido, String direccionEntrega, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
    }

    /**
     * Muestra en consola los datos básicos del pedido.
     */
    public void mostrarResumen() {
        System.out.println("ID Pedido: " + idPedido);
        System.out.println("Dirección de entrega: " + direccionEntrega);
        System.out.println("Distancia: " + distanciaKm + " km");
    }

    /**
     * Calcula el tiempo estimado de entrega del pedido.
     *
     * Cada clase derivada debe implementar este método
     * según sus propias reglas de negocio.
     *
     * @return tiempo estimado de entrega expresado en minutos
     */
    public abstract int calcularTiempoEntrega();
}