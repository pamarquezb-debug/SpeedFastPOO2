package modelo;

/**
 * Clase abstracta que representa un pedido de SpeedFast.
 *
 * Contiene la información común de todos los tipos
 * de pedidos utilizados por el sistema.
 *
 * @author Pablo Marquez
 * @version 7.0
 */
public abstract class Pedido {

    private int idPedido;
    private String direccionEntrega;
    private double distanciaKm;
    private String estado;

    /**
     * Constructor de Pedido.
     *
     * @param idPedido identificador del pedido
     * @param direccionEntrega dirección donde se realizará la entrega
     * @param distanciaKm distancia de la entrega en kilómetros
     */
    public Pedido(
            int idPedido,
            String direccionEntrega,
            double distanciaKm) {

        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;

        /*
         * Todo pedido nuevo comienza pendiente.
         * Si viene desde MySQL, PedidoDAO reemplazará
         * este valor mediante setEstado().
         */
        this.estado = "PENDIENTE";
    }

    /**
     * Obtiene el identificador del pedido.
     *
     * @return identificador del pedido
     */
    public int getIdPedido() {
        return idPedido;
    }

    /**
     * Obtiene la dirección de entrega.
     *
     * @return dirección de entrega
     */
    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    /**
     * Obtiene la distancia de entrega.
     *
     * @return distancia en kilómetros
     */
    public double getDistanciaKm() {
        return distanciaKm;
    }

    /**
     * Obtiene el estado actual del pedido.
     *
     * @return estado del pedido
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Modifica el estado del pedido.
     *
     * @param estado nuevo estado del pedido
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Calcula el tiempo estimado de entrega.
     *
     * Cada tipo de pedido implementa
     * su propio cálculo.
     *
     * @return tiempo estimado en minutos
     */
    public abstract int calcularTiempoEntrega();

    /**
     * Obtiene el tipo de pedido.
     *
     * @return tipo de pedido
     */
    public abstract String getTipo();

    /**
     * Genera un resumen con la información
     * principal del pedido.
     *
     * @return resumen del pedido
     */
    public String mostrarResumen() {

        return "Pedido #" + idPedido
                + " | Dirección: " + direccionEntrega
                + " | Distancia: " + distanciaKm + " km"
                + " | Tipo: " + getTipo()
                + " | Estado: " + estado;
    }

    /**
     * Representación utilizada por componentes
     * gráficos como JComboBox.
     *
     * @return descripción breve del pedido
     */
    @Override
    public String toString() {

        return "#" + idPedido
                + " - " + direccionEntrega
                + " - " + distanciaKm + " km"
                + " (" + getTipo() + ")";
    }
}