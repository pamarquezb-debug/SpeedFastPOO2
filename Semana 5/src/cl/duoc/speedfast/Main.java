package cl.duoc.speedfast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Clase principal del sistema SpeedFast.
 *
 * Simula el acceso concurrente de varios repartidores
 * a una zona de carga compartida utilizando mecanismos
 * de sincronización.
 *
 * @author Pablo Marquez
 * @version 5.0
 */
public class Main {

    /**
     * Método principal.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {

        System.out.println(
                "===== SPEEDFAST - SEMANA 5 ====="
        );

        System.out.println();

        /*
         * Se crea una única ZonaDeCarga.
         *
         * Esta instancia será compartida por
         * todos los repartidores.
         */
        ZonaDeCarga zonaDeCarga =
                new ZonaDeCarga();

        /*
         * Se agregan pedidos al recurso compartido.
         */
        zonaDeCarga.agregarPedido(
                new Pedido(
                        101,
                        "Av. Providencia 1234"
                )
        );

        zonaDeCarga.agregarPedido(
                new Pedido(
                        102,
                        "Av. Las Condes 4500"
                )
        );

        zonaDeCarga.agregarPedido(
                new Pedido(
                        103,
                        "Av. Santa Rosa 567"
                )
        );

        zonaDeCarga.agregarPedido(
                new Pedido(
                        104,
                        "Gran Avenida 3200"
                )
        );

        zonaDeCarga.agregarPedido(
                new Pedido(
                        105,
                        "Av. Apoquindo 6000"
                )
        );

        zonaDeCarga.agregarPedido(
                new Pedido(
                        106,
                        "Av. Grecia 1500"
                )
        );

        System.out.println();

        System.out.println(
                "===== INICIO DE REPARTIDORES ====="
        );

        System.out.println();

        /*
         * Los tres repartidores reciben la misma
         * instancia de ZonaDeCarga.
         */
        Repartidor luis =
                new Repartidor(
                        "Luis Díaz",
                        zonaDeCarga
                );

        Repartidor daniela =
                new Repartidor(
                        "Daniela Tapia",
                        zonaDeCarga
                );

        Repartidor camila =
                new Repartidor(
                        "Camila Soto",
                        zonaDeCarga
                );

        /*
         * Pool con tres hilos.
         */
        ExecutorService executor =
                Executors.newFixedThreadPool(3);

        /*
         * Los tres repartidores comienzan
         * concurrentemente.
         */
        executor.execute(luis);
        executor.execute(daniela);
        executor.execute(camila);

        /*
         * No se aceptarán nuevas tareas.
         */
        executor.shutdown();

        try {

            /*
             * El hilo principal espera hasta que todos
             * los repartidores hayan terminado.
             */
            boolean terminado =
                    executor.awaitTermination(
                            1,
                            TimeUnit.MINUTES
                    );

            if (terminado) {

                System.out.println();

                System.out.println(
                        "=========================================="
                );

                System.out.println(
                        "Todos los pedidos han sido entregados correctamente"
                );

                System.out.println(
                        "=========================================="
                );

            } else {

                System.out.println(
                        "La simulación superó el tiempo máximo."
                );

                executor.shutdownNow();
            }

        } catch (InterruptedException e) {

            executor.shutdownNow();

            Thread.currentThread().interrupt();

            System.out.println(
                    "La ejecución principal fue interrumpida."
            );
        }
    }
}