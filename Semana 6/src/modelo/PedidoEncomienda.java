package modelo;

public class PedidoEncomienda extends Pedido {

    public PedidoEncomienda(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        return (int) (getDistanciaKm() * 4 + 8);
    }

    @Override
    public String getTipo() {
        return "Encomienda";
    }
}