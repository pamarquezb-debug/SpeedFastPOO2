package cl.duoc.speedfast;

/**
 * Define el comportamiento necesario para cancelar un pedido.
 *
 * @author Pablo Marquez
 * @version 3.0
 */
public interface Cancelable {

    /**
     * Cancela el pedido.
     */
    void cancelar();
}