package cl.duoc.speedfast;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa la zona de carga compartida
 * por todos los repartidores de SpeedFast.
 *
 * Los métodos de ingreso y retiro de pedidos
 * están sincronizados para evitar condiciones
 * de carrera.
 *
 * @author Pablo Marquez
 * @version 5.0
 */
public class ZonaDeCarga {

    /**
     * Lista compartida de pedidos.
     */
    private final List<Pedido> pedidos;

    /**
     * Constructor de ZonaDeCarga.
     */
    public ZonaDeCarga() {
        pedidos = new ArrayList<>();
    }

    /**
     * Agrega un pedido a la zona de carga.
     *
     * synchronized garantiza que solamente un hilo
     * pueda modificar la lista en un momento determinado.
     *
     * @param pedido pedido que será agregado
     */
    public synchronized void agregarPedido(Pedido pedido) {

        pedidos.add(pedido);

        System.out.println(
                "Pedido #" + pedido.getId()
                        + " agregado a la zona de carga."
        );
    }

    /**
     * Retira de forma segura un pedido pendiente.
     *
     * Debido a que el método está sincronizado,
     * solamente un repartidor puede acceder a esta
     * sección crítica al mismo tiempo.
     *
     * El pedido es retirado de la lista y cambia
     * inmediatamente de PENDIENTE a EN_REPARTO.
     *
     * @return pedido disponible o null si no quedan pedidos
     */
    public synchronized Pedido retirarPedido() {

        for (int i = 0; i < pedidos.size(); i++) {

            Pedido pedido = pedidos.get(i);

            if (pedido.getEstado() == EstadoPedido.PENDIENTE) {

                /*
                 * Se elimina inmediatamente de la zona
                 * de carga para impedir que otro hilo
                 * pueda retirar el mismo pedido.
                 */
                pedidos.remove(i);

                /*
                 * El cambio de estado ocurre dentro
                 * de la misma sección sincronizada.
                 */
                pedido.setEstado(EstadoPedido.EN_REPARTO);

                return pedido;
            }
        }

        return null;
    }

    /**
     * Retorna la cantidad de pedidos que todavía
     * permanecen en la zona de carga.
     *
     * @return cantidad de pedidos pendientes
     */
    public synchronized int cantidadPedidos() {
        return pedidos.size();
    }
}