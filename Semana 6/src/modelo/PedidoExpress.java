package modelo;

public class PedidoExpress extends modelo.Pedido {

    public PedidoExpress(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        return (int) (getDistanciaKm() * 2 + 5);
    }

    @Override
    public String getTipo() {
        return "Express";
    }
}