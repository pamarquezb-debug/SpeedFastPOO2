package cl.duoc.speedfast;

/**
 * Clase principal del sistema SpeedFast.
 *
 * Permite comprobar el funcionamiento de la clase abstracta
 * Pedido y la implementación del método abstracto
 * calcularTiempoEntrega() en sus clases derivadas.
 *
 * @author Pablo Marquez
 * @version 2.0
 */
public class Main {

    /**
     * Método principal de ejecución del programa.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {

        PedidoComida comida =
                new PedidoComida(
                        1,
                        "Av. Providencia 1500",
                        4.0
                );

        PedidoEncomienda encomienda =
                new PedidoEncomienda(
                        2,
                        "Av. Apoquindo 3200",
                        6.0
                );

        PedidoExpress express =
                new PedidoExpress(
                        3,
                        "Gran Avenida 4500",
                        7.0
                );

        System.out.println("===== PEDIDO DE COMIDA =====");
        comida.mostrarResumen();
        System.out.println(
                "Tiempo estimado: "
                        + comida.calcularTiempoEntrega()
                        + " minutos"
        );

        System.out.println();

        System.out.println("===== PEDIDO DE ENCOMIENDA =====");
        encomienda.mostrarResumen();
        System.out.println(
                "Tiempo estimado: "
                        + encomienda.calcularTiempoEntrega()
                        + " minutos"
        );

        System.out.println();

        System.out.println("===== PEDIDO EXPRESS =====");
        express.mostrarResumen();
        System.out.println(
                "Tiempo estimado: "
                        + express.calcularTiempoEntrega()
                        + " minutos"
        );

        System.out.println();
        System.out.println("===== COMPARACIÓN DE TIEMPOS =====");

        System.out.println(
                "Comida: "
                        + comida.calcularTiempoEntrega()
                        + " minutos"
        );

        System.out.println(
                "Encomienda: "
                        + encomienda.calcularTiempoEntrega()
                        + " minutos"
        );

        System.out.println(
                "Express: "
                        + express.calcularTiempoEntrega()
                        + " minutos"
        );
    }
}