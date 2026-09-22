package controlador;

import modelo.Pedido;

import java.util.ArrayList;
import java.util.List;

public class ControladorPedidos {

    private final List<Pedido> pedidos;

    public ControladorPedidos() {
        pedidos = new ArrayList<>();
    }

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public List<Pedido> obtenerPedidos() {
        return pedidos;
    }

    public boolean existePedido(int idPedido) {

        for (Pedido pedido : pedidos) {
            if (pedido.getIdPedido() == idPedido) {
                return true;
            }
        }

        return false;
    }
}