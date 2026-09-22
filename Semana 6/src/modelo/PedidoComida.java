package modelo;

public class PedidoComida extends Pedido {

    public PedidoComida(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        return (int) (getDistanciaKm() * 3 + 5);
    }

    @Override
    public String getTipo() {
        return "Comida";
    }
}