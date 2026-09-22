package modelo;

/**
 * Clase abstracta que representa un pedido de SpeedFast.
 */
public abstract class Pedido {

    private int idPedido;
    private String direccionEntrega;
    private double distanciaKm;

    public Pedido(int idPedido, String direccionEntrega, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public abstract int calcularTiempoEntrega();

    public abstract String getTipo();

    public String mostrarResumen() {
        return "Pedido #" + idPedido +
                " | Dirección: " + direccionEntrega +
                " | Distancia: " + distanciaKm + " km" +
                " | Tipo: " + getTipo();
    }
}