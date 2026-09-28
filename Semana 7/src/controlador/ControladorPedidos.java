package controlador;

import dao.PedidoDAO;
import modelo.Pedido;

import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones
 * relacionadas con los pedidos de SpeedFast.
 *
 * Durante la Semana 7 las operaciones se realizan
 * utilizando persistencia en MySQL mediante JDBC.
 *
 * @author Pablo Marquez
 * @version 7.0
 */
public class ControladorPedidos {

    private final PedidoDAO pedidoDAO;

    /**
     * Constructor del controlador.
     */
    public ControladorPedidos() {
        pedidoDAO = new PedidoDAO();
    }

    /**
     * Registra un pedido en la base de datos.
     *
     * @param pedido pedido que será almacenado
     * @return true si el pedido fue guardado correctamente
     */
    public boolean agregarPedido(Pedido pedido) {
        return pedidoDAO.guardar(pedido);
    }

    /**
     * Obtiene todos los pedidos almacenados
     * en la base de datos.
     *
     * @return lista de pedidos
     */
    public List<Pedido> obtenerPedidos() {
        return pedidoDAO.listarTodos();
    }
    /**
     * Obtiene los pedidos que todavía pueden
     * ser asignados a un repartidor.
     *
     * @return lista de pedidos disponibles
     */
    public List<Pedido> obtenerPedidosDisponibles() {
        return pedidoDAO.listarDisponibles();
    }
    /**
     * Comprueba si existe un pedido con el ID indicado.
     *
     * @param idPedido identificador del pedido
     * @return true si existe
     */
    public boolean existePedido(int idPedido) {

        for (Pedido pedido : obtenerPedidos()) {

            if (pedido.getIdPedido() == idPedido) {
                return true;
            }
        }

        return false;
    }
}