package modelo;

public class Repartidor {

    private String nombre;
    private Pedido pedidoAsignado;

    public Repartidor(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Pedido getPedidoAsignado() {
        return pedidoAsignado;
    }

    public void asignarPedido(Pedido pedido) {
        this.pedidoAsignado = pedido;
    }

    @Override
    public String toString() {
        return nombre;
    }
}