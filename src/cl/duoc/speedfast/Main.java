package cl.duoc.speedfast;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Clase principal de SpeedFast para la Semana 4.
 *
 * Simula múltiples repartidores realizando entregas
 * concurrentemente mediante ExecutorService.
 *
 * @author Pablo Marquez
 * @version 4.0
 */
public class Main {

    /**
     * Método principal de ejecución.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {

        /*
         * Pedidos del repartidor 1
         */
        List<Pedido> pedidosLuis = Arrays.asList(

                new PedidoComida(
                        101,
                        "Av. Providencia 1234",
                        4.0
                ),

                new PedidoExpress(
                        102,
                        "Av. Las Condes 4500",
                        7.0
                )
        );

        /*
         * Pedidos del repartidor 2
         */
        List<Pedido> pedidosDaniela = Arrays.asList(

                new PedidoEncomienda(
                        103,
                        "Av. Santa Rosa 567",
                        5.0
                ),

                new PedidoComida(
                        104,
                        "Gran Avenida 3200",
                        6.0
                )
        );

        /*
         * Pedidos del repartidor 3
         */
        List<Pedido> pedidosCamila = Arrays.asList(

                new PedidoExpress(
                        105,
                        "Av. Apoquindo 6000",
                        8.0
                ),

                new PedidoEncomienda(
                        106,
                        "Av. Grecia 1500",
                        3.0
                )
        );

        /*
         * Creación de repartidores.
         */
        Repartidor repartidor1 =
                new Repartidor(
                        "Luis Díaz",
                        pedidosLuis
                );

        Repartidor repartidor2 =
                new Repartidor(
                        "Daniela Tapia",
                        pedidosDaniela
                );

        Repartidor repartidor3 =
                new Repartidor(
                        "Camila Soto",
                        pedidosCamila
                );

        /*
         * ExecutorService con tres hilos.
         *
         * Cada hilo ejecutará un repartidor.
         */
        ExecutorService executor =
                Executors.newFixedThreadPool(3);

        System.out.println(
                "===== INICIO SIMULACIÓN SPEEDFAST ====="
        );

        /*
         * Los tres repartidores comienzan a trabajar
         * concurrentemente.
         */
        executor.execute(repartidor1);
        executor.execute(repartidor2);
        executor.execute(repartidor3);

        /*
         * No se aceptarán nuevas tareas.
         */
        executor.shutdown();

        try {

            /*
             * Espera hasta que todos los repartidores
             * terminen sus entregas.
             */
            boolean terminado =
                    executor.awaitTermination(
                            1,
                            TimeUnit.MINUTES
                    );

            if (terminado) {

                System.out.println();
                System.out.println(
                        "===== TODAS LAS ENTREGAS FINALIZARON ====="
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