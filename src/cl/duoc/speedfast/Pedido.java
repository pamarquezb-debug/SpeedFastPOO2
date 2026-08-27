package cl.duoc.speedfast;

/**
 * Clase abstracta que representa un pedido dentro
 * del sistema de entregas SpeedFast.
 *
 * Contiene los atributos y comportamientos comunes
 * de todos los tipos de pedidos.
 *
 * Implementa las interfaces Despachable y Cancelable.
 *
 * @author Pablo Marquez
 * @version 3.0
 */
public abstract class Pedido implements Despachable, Cancelable {

    /** Identificador único del pedido. */
    protected int idPedido;

    /** Dirección de entrega del pedido. */
    protected String direccionEntrega;

    /** Distancia de entrega expresada en kilómetros. */
    protected double distanciaKm;

    /** Nombre del repartidor asignado. */
    protected String repartidorAsignado;

    /** Estado actual del pedido. */
    protected String estado;

    /**
     * Constructor de la clase Pedido.
     *
     * @param idPedido identificador único del pedido
     * @param direccionEntrega dirección donde se realizará la entrega
     * @param distanciaKm distancia de entrega en kilómetros
     */
    public Pedido(
            int idPedido,
            String direccionEntrega,
            double distanciaKm) {

        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
        this.repartidorAsignado = "Sin asignar";
        this.estado = "Pendiente";
    }

    /**
     * Muestra un resumen con los datos principales del pedido.
     */
    public void mostrarResumen() {

        System.out.println("Pedido #" + idPedido);
        System.out.println("Dirección: " + direccionEntrega);
        System.out.println("Distancia: " + distanciaKm + " km");
        System.out.println("Repartidor asignado: " + repartidorAsignado);
        System.out.println("Estado: " + estado);
    }

    /**
     * Asigna automáticamente un repartidor.
     *
     * Cada subclase debe implementar su propia lógica.
     */
    public abstract void asignarRepartidor();

    /**
     * Sobrecarga del método asignarRepartidor.
     *
     * Permite realizar una asignación manual indicando
     * directamente el nombre del repartidor.
     *
     * @param nombre nombre del repartidor
     */
    public void asignarRepartidor(String nombre) {

        this.repartidorAsignado = nombre;

        System.out.println(
                "Repartidor asignado manualmente: " + nombre
        );
    }

    /**
     * Calcula el tiempo estimado de entrega.
     *
     * Cada clase derivada debe implementar su propia regla.
     *
     * @return tiempo estimado en minutos
     */
    public abstract int calcularTiempoEntrega();

    /**
     * Despacha el pedido.
     */
    @Override
    public void despachar() {

        if (estado.equals("Cancelado")) {

            System.out.println(
                    "El pedido #" + idPedido
                            + " no puede ser despachado porque está cancelado."
            );

            return;
        }

        estado = "Despachado";

        System.out.println(
                "Pedido #" + idPedido
                        + " despachado correctamente."
        );
    }

    /**
     * Cancela el pedido.
     */
    @Override
    public void cancelar() {

        if (estado.equals("Despachado")) {

            System.out.println(
                    "El pedido #" + idPedido
                            + " ya fue despachado y no puede cancelarse."
            );

            return;
        }

        estado = "Cancelado";

        System.out.println(
                "Pedido #" + idPedido
                        + " cancelado exitosamente."
        );
    }

    /**
     * Retorna el identificador del pedido.
     *
     * @return identificador del pedido
     */
    public int getIdPedido() {
        return idPedido;
    }

    /**
     * Retorna el nombre del repartidor asignado.
     *
     * @return nombre del repartidor
     */
    public String getRepartidorAsignado() {
        return repartidorAsignado;
    }

    /**
     * Retorna el estado actual del pedido.
     *
     * @return estado del pedido
     */
    public String getEstado() {
        return estado;
    }
}