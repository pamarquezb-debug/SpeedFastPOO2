package cl.duoc.speedfast;

import java.util.Random;

/**
 * Representa un repartidor de SpeedFast.
 *
 * Cada repartidor funciona como una tarea Runnable
 * independiente y accede a una ZonaDeCarga compartida.
 *
 * @author Pablo Marquez
 * @version 5.0
 */
public class Repartidor implements Runnable {

    /** Nombre del repartidor. */
    private final String nombre;

    /** Zona de carga compartida. */
    private final ZonaDeCarga zonaDeCarga;

    /** Generador de tiempos aleatorios. */
    private final Random random;

    /**
     * Constructor de la clase Repartidor.
     *
     * @param nombre nombre del repartidor
     * @param zonaDeCarga zona de carga compartida
     */
    public Repartidor(
            String nombre,
            ZonaDeCarga zonaDeCarga) {

        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
        this.random = new Random();
    }

    /**
     * Ejecuta el trabajo del repartidor.
     *
     * El repartidor intenta retirar pedidos de la
     * zona de carga hasta que ya no quedan pedidos
     * disponibles.
     */
    @Override
    public void run() {

        System.out.println(
                nombre + " inició su jornada."
        );

        while (true) {

            /*
             * El acceso a la zona de carga está protegido
             * internamente mediante synchronized.
             */
            Pedido pedido =
                    zonaDeCarga.retirarPedido();

            /*
             * Si no quedan pedidos disponibles,
             * el repartidor termina su jornada.
             */
            if (pedido == null) {
                break;
            }

            System.out.println();

            System.out.println(
                    "[" + nombre + "] retiró Pedido #"
                            + pedido.getId()
            );

            System.out.println(
                    "[" + nombre + "] Estado: "
                            + pedido.getEstado()
            );

            System.out.println(
                    "[" + nombre + "] Dirección: "
                            + pedido.getDireccionEntrega()
            );

            try {

                /*
                 * Se genera un tiempo aleatorio entre
                 * 1 y 3 segundos para simular la entrega.
                 */
                int tiempoEntrega =
                        random.nextInt(2000) + 1000;

                System.out.println(
                        "[" + nombre
                                + "] Pedido #"
                                + pedido.getId()
                                + " en reparto..."
                );

                Thread.sleep(tiempoEntrega);

                /*
                 * Una vez finalizada la simulación,
                 * el pedido se marca como entregado.
                 */
                pedido.setEstado(
                        EstadoPedido.ENTREGADO
                );

                System.out.println(
                        "[" + nombre
                                + "] Pedido #"
                                + pedido.getId()
                                + " ENTREGADO correctamente."
                );

            } catch (InterruptedException e) {

                System.out.println(
                        "[" + nombre
                                + "] Proceso interrumpido."
                );

                Thread.currentThread().interrupt();

                return;
            }
        }

        System.out.println();
        System.out.println(
                nombre + " terminó su jornada."
        );
    }
}