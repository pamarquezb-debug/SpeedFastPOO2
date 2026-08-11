package cl.duoc.speedfast;

/**
 * Clase principal del sistema SpeedFast.
 * <p>
 * Permite comprobar el funcionamiento de la herencia,
 * sobreescritura y sobrecarga de métodos mediante los
 * diferentes tipos de pedidos.
 * </p>
 *
 * @author Pablo Marquez
 * @version 1.0
 */
public class Main {

    /**
     * Método principal de ejecución del programa.
     *
     * @param args argumentos recibidos desde la línea de comandos
     */
    public static void main(String[] args) {

        // Creación de un pedido de comida.
        PedidoComida comida =
                new PedidoComida(
                        1,
                        "Av. Providencia 1500"
                );

        // Creación de un pedido de encomienda.
        PedidoEncomienda encomienda =
                new PedidoEncomienda(
                        2,
                        "Av. Apoquindo 3200",
                        4.5
                );

        // Creación de un pedido express.
        PedidoExpress express =
                new PedidoExpress(
                        3,
                        "Gran Avenida 4500"
                );

        // Prueba del pedido de comida.
        System.out.println("===== PEDIDO DE COMIDA =====");

        comida.mostrarDatos();
        comida.asignarRepartidor();
        comida.asignarRepartidor("Carlos");

        System.out.println();

        // Prueba del pedido de encomienda.
        System.out.println("===== PEDIDO DE ENCOMIENDA =====");

        encomienda.mostrarDatos();
        encomienda.asignarRepartidor();
        encomienda.asignarRepartidor("María");

        System.out.println();

        // Prueba del pedido express.
        System.out.println("===== COMPRA EXPRESS =====");

        express.mostrarDatos();
        express.asignarRepartidor();
        express.asignarRepartidor("Pedro");
    }
}