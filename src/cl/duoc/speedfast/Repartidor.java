package cl.duoc.speedfast;

import java.util.List;
import java.util.Random;

/**
 * Representa a un repartidor de SpeedFast.
 *
 * Cada repartidor se ejecuta como una tarea independiente
 * mediante la interfaz Runnable y procesa secuencialmente
 * los pedidos que tiene asignados.
 *
 * @author Pablo Marquez
 * @version 4.0
 */
public class Repartidor implements Runnable {

    /** Nombre del repartidor. */
    private final String nombre;

    /** Lista de pedidos asignados al repartidor. */
    private final List<Pedido> pedidos;

    /** Generador utilizado para simular tiempos aleatorios. */
    private final Random random;

    /**
     * Constructor de la clase Repartidor.
     *
     * @param nombre nombre del repartidor
     * @param pedidos lista de pedidos asignados
     */
    public Repartidor(String nombre, List<Pedido> pedidos) {
        this.nombre = nombre;
        this.pedidos = pedidos;
        this.random = new Random();
    }

    /**
     * Ejecuta secuencialmente los pedidos asignados al repartidor.
     *
     * Cada pedido simula un tiempo de procesamiento utilizando
     * Thread.sleep() con una duración aleatoria.
     */
    @Override
    public void run() {

        System.out.println(
                nombre + " inició su jornada de entregas."
        );

        for (Pedido pedido : pedidos) {

            System.out.println();
            System.out.println(
                    "[" + nombre + "] Iniciando pedido #"
                            + pedido.getIdPedido()
            );

            pedido.mostrarResumen();

            System.out.println(
                    "[" + nombre + "] Tiempo estimado: "
                            + pedido.calcularTiempoEntrega()
                            + " minutos"
            );

            try {

                int tiempoSimulado =
                        random.nextInt(2000) + 1000;

                System.out.println(
                        "[" + nombre
                                + "] Pedido en camino..."
                );

                Thread.sleep(tiempoSimulado);

                pedido.despachar();

                System.out.println(
                        "[" + nombre
                                + "] Pedido #"
                                + pedido.getIdPedido()
                                + " entregado."
                );

            } catch (InterruptedException e) {

                System.out.println(
                        "[" + nombre
                                + "] Ejecución interrumpida."
                );

                Thread.currentThread().interrupt();

                return;
            }
        }

        System.out.println();
        System.out.println(
                nombre + " terminó todas sus entregas."
        );
    }
}