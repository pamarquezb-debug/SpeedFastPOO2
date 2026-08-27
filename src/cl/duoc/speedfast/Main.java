package cl.duoc.speedfast;

/**
 * Clase principal del sistema SpeedFast.
 *
 * Demuestra el uso de abstracción, herencia,
 * polimorfismo, sobrecarga, sobrescritura e interfaces.
 *
 * @author Pablo Marquez
 * @version 3.0
 */
public class Main {

    /**
     * Método principal del programa.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {

        ControladorDeEnvios controlador =
                new ControladorDeEnvios();

        PedidoComida comida =
                new PedidoComida(
                        101,
                        "Av. Providencia 1234",
                        4.0
                );

        PedidoEncomienda encomienda =
                new PedidoEncomienda(
                        102,
                        "Av. Santa Rosa 567",
                        7.0
                );

        PedidoExpress express =
                new PedidoExpress(
                        103,
                        "Av. Apoquindo 3200",
                        8.0
                );

        /*
         * PEDIDO DE COMIDA
         */
        System.out.println(
                "===== PEDIDO COMIDA ====="
        );

        comida.asignarRepartidor();

        comida.mostrarResumen();

        System.out.println(
                "Tiempo estimado: "
                        + comida.calcularTiempoEntrega()
                        + " minutos"
        );

        comida.despachar();

        controlador.registrarEntrega(comida);

        System.out.println();


        /*
         * PEDIDO DE ENCOMIENDA
         */
        System.out.println(
                "===== PEDIDO ENCOMIENDA ====="
        );

        // Asignación manual:
        // demuestra sobrecarga del método.
        encomienda.asignarRepartidor(
                "Daniela Tapia"
        );

        encomienda.mostrarResumen();

        System.out.println(
                "Tiempo estimado: "
                        + encomienda.calcularTiempoEntrega()
                        + " minutos"
        );

        encomienda.despachar();

        controlador.registrarEntrega(encomienda);

        System.out.println();


        /*
         * PEDIDO EXPRESS
         */
        System.out.println(
                "===== PEDIDO EXPRESS ====="
        );

        express.asignarRepartidor();

        express.mostrarResumen();

        System.out.println(
                "Tiempo estimado: "
                        + express.calcularTiempoEntrega()
                        + " minutos"
        );

        System.out.println(
                "Cancelando Pedido Express #"
                        + express.getIdPedido()
                        + "..."
        );

        express.cancelar();

        controlador.registrarEntrega(express);


        /*
         * HISTORIAL
         */
        controlador.verHistorial();
    }
}