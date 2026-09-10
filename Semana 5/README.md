# SpeedFastPOO2

## Programación Orientada a Objetos II

### Semana 5 - Sincronizando procesos en sistemas concurrentes

Proyecto desarrollado como parte de la asignatura **Programación Orientada a Objetos II**.

Durante esta quinta semana se continúa trabajando con el caso de la empresa **SpeedFast**, incorporando mecanismos de sincronización para controlar el acceso concurrente de múltiples repartidores a una zona de carga compartida.

El objetivo principal es evitar condiciones de carrera y garantizar que cada pedido sea retirado y entregado por un único repartidor.

---

# Descripción de la actividad

En las semanas anteriores se desarrolló un sistema concurrente para SpeedFast, donde múltiples repartidores podían realizar entregas en paralelo.

En esta etapa se incorpora un nuevo problema: varios repartidores pueden intentar acceder simultáneamente a una misma zona de carga para retirar pedidos.

Sin un mecanismo de sincronización podrían producirse problemas como:

- Retiro duplicado de pedidos.
- Modificación simultánea de datos.
- Inconsistencias en los estados.
- Condiciones de carrera.
- Entregas duplicadas.

Para solucionar este problema se implementa una **ZonaDeCarga compartida**, cuyo acceso está protegido mediante métodos `synchronized`.

---

# Objetivo

El objetivo de esta actividad es aplicar mecanismos de sincronización en Java para coordinar múltiples tareas concurrentes que acceden a un recurso compartido.

El sistema permite:

- Crear pedidos.
- Mantener estados mediante un `enum`.
- Agregar pedidos a una zona de carga común.
- Ejecutar múltiples repartidores concurrentemente.
- Retirar pedidos de manera sincronizada.
- Evitar que dos repartidores retiren el mismo pedido.
- Cambiar el estado del pedido durante su ciclo de entrega.
- Simular el tiempo de entrega mediante `Thread.sleep()`.
- Esperar que todos los repartidores finalicen utilizando `ExecutorService`.

---

# Flujo de estados

Cada pedido comienza con estado:

```text
PENDIENTE
```

Cuando un repartidor lo retira de la zona de carga:

```text
PENDIENTE
    |
    v
EN_REPARTO
```

Una vez terminada la entrega:

```text
PENDIENTE
    |
    v
EN_REPARTO
    |
    v
ENTREGADO
```

Esto permite controlar claramente el ciclo de vida de cada pedido.

---

# EstadoPedido

Para representar los estados de los pedidos se utiliza un `enum`:

```java
public enum EstadoPedido {

    PENDIENTE,
    EN_REPARTO,
    ENTREGADO
}
```

El uso de un `enum` mejora la seguridad y legibilidad del código, evitando errores producidos por escribir manualmente los estados mediante cadenas de texto.

Por ejemplo, en lugar de:

```java
estado = "ENTREGADO";
```

se utiliza:

```java
estado = EstadoPedido.ENTREGADO;
```

---

# Clase Pedido

La clase `Pedido` representa una encomienda que debe ser entregada por SpeedFast.

Sus principales atributos son:

```java
private int id;
private String direccionEntrega;
private EstadoPedido estado;
```

Cada nuevo pedido comienza automáticamente con estado:

```java
EstadoPedido.PENDIENTE
```

Ejemplo:

```java
Pedido pedido =
        new Pedido(
                101,
                "Av. Providencia 1234"
        );
```

El pedido también posee:

- Constructor.
- Getters.
- Setters.
- `toString()`.
- Control del estado.

Se implementa además el método solicitado:

```java
public void setEstado(String nuevoEstado)
```

y una versión que utiliza directamente el `enum`:

```java
public void setEstado(EstadoPedido estado)
```

---

# ZonaDeCarga

La clase `ZonaDeCarga` representa el **recurso compartido** del sistema.

Todos los repartidores acceden a la misma instancia.

Internamente se utiliza una colección:

```java
private final List<Pedido> pedidos;
```

Los pedidos pueden ser agregados mediante:

```java
public synchronized void agregarPedido(Pedido pedido)
```

y retirados mediante:

```java
public synchronized Pedido retirarPedido()
```

---

# Sincronización

La palabra clave:

```java
synchronized
```

permite controlar el acceso simultáneo a un recurso compartido.

En este proyecto, el método:

```java
public synchronized Pedido retirarPedido()
```

constituye una **sección crítica**.

Esto significa que solamente un hilo puede ejecutar ese método sobre la misma instancia de `ZonaDeCarga` en un momento determinado.

Conceptualmente:

```text
             ZONA DE CARGA
                   |
            retirarPedido()
                   |
             synchronized
                   |
        -----------------------
        |          |          |
       Luis     Daniela     Camila
```

Si Luis está retirando un pedido, Daniela y Camila deben esperar hasta que Luis abandone la sección sincronizada.

---

# Prevención del retiro duplicado

Cuando un repartidor encuentra un pedido disponible, este se elimina inmediatamente de la zona de carga:

```java
pedidos.remove(i);
```

y cambia su estado:

```java
pedido.setEstado(
        EstadoPedido.EN_REPARTO
);
```

Ambas operaciones se realizan dentro del método sincronizado.

De esta forma se evita una situación como:

```text
Luis    -> Pedido #101
Daniela -> Pedido #101
```

El comportamiento correcto será:

```text
Luis    -> Pedido #101
Daniela -> Pedido #102
Camila  -> Pedido #103
```

Cada pedido puede ser retirado solamente una vez.

---

# Condición de carrera

Una **condición de carrera** ocurre cuando dos o más hilos intentan acceder o modificar simultáneamente un recurso compartido y el resultado depende del orden en que se ejecutan.

En SpeedFast, la zona de carga es el recurso compartido.

Sin sincronización:

```text
Repartidor 1 ----\
                  \
                   > ZonaDeCarga
                  /
Repartidor 2 ----/
```

ambos podrían intentar acceder al mismo pedido.

Con sincronización:

```text
Repartidor 1
      |
      v
[ synchronized ]
      |
      v
 ZonaDeCarga

Repartidor 2
      |
    ESPERA
```

De esta manera se protege la integridad de los datos.

---

# Clase Repartidor

La clase `Repartidor` representa a cada trabajador encargado de realizar las entregas.

Posee los atributos:

```java
private final String nombre;
private final ZonaDeCarga zonaDeCarga;
```

Todos los repartidores reciben una referencia a la **misma zona de carga**.

La clase implementa:

```java
Runnable
```

por lo tanto:

```java
public class Repartidor implements Runnable
```

Cada repartidor puede ser ejecutado como una tarea independiente.

---

# Método run()

La lógica principal de cada repartidor se encuentra en:

```java
@Override
public void run()
```

El repartidor realiza repetidamente las siguientes operaciones:

```text
Retirar pedido
      |
      v
EN_REPARTO
      |
      v
Simular entrega
      |
      v
ENTREGADO
      |
      v
Buscar otro pedido
```

Cuando `retirarPedido()` retorna `null`, significa que ya no quedan pedidos disponibles y el repartidor termina su jornada.

---

# Simulación de entrega

Para representar el tiempo necesario para realizar una entrega se utiliza:

```java
Thread.sleep()
```

El tiempo se genera aleatoriamente:

```java
int tiempoEntrega =
        random.nextInt(2000) + 1000;

Thread.sleep(tiempoEntrega);
```

Esto genera una pausa aproximada de entre **1 y 3 segundos**.

Durante esta pausa los otros repartidores pueden continuar ejecutando sus propias entregas.

---

# Manejo de InterruptedException

Debido a que `Thread.sleep()` puede ser interrumpido, se utiliza:

```java
try {

    Thread.sleep(tiempoEntrega);

} catch (InterruptedException e) {

    Thread.currentThread().interrupt();

    return;
}
```

Esto permite controlar correctamente una posible interrupción del hilo.

---

# Clase Main

La clase `Main` se encarga de iniciar la simulación.

Primero se crea una única zona de carga:

```java
ZonaDeCarga zonaDeCarga =
        new ZonaDeCarga();
```

Luego se agregan los pedidos:

```java
zonaDeCarga.agregarPedido(
        new Pedido(
                101,
                "Av. Providencia 1234"
        )
);
```

Se incorporan al menos cinco pedidos al sistema.

---

# Creación de repartidores

Se crean tres repartidores:

```java
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
```

Es importante observar que los tres reciben:

```java
zonaDeCarga
```

Es decir, todos comparten **el mismo recurso**.

---

# ExecutorService

Para administrar los hilos se utiliza:

```java
ExecutorService
```

Se crea un pool de tres hilos:

```java
ExecutorService executor =
        Executors.newFixedThreadPool(3);
```

Los repartidores se ejecutan mediante:

```java
executor.execute(luis);
executor.execute(daniela);
executor.execute(camila);
```

De esta manera los tres repartidores pueden trabajar concurrentemente.

---

# Finalización de los hilos

Después de iniciar las tareas se ejecuta:

```java
executor.shutdown();
```

Esto indica que no se recibirán nuevas tareas, pero permite que las existentes terminen.

Posteriormente:

```java
executor.awaitTermination(
        1,
        TimeUnit.MINUTES
);
```

permite que el hilo principal espere hasta que los repartidores finalicen.

Al terminar correctamente se muestra:

```text
Todos los pedidos han sido entregados correctamente
```

---

# Ejemplo de funcionamiento

Una ejecución puede mostrar:

```text
===== SPEEDFAST - SEMANA 5 =====

Pedido #101 agregado a la zona de carga.
Pedido #102 agregado a la zona de carga.
Pedido #103 agregado a la zona de carga.
Pedido #104 agregado a la zona de carga.
Pedido #105 agregado a la zona de carga.
Pedido #106 agregado a la zona de carga.

===== INICIO DE REPARTIDORES =====

Luis Díaz inició su jornada.
Daniela Tapia inició su jornada.
Camila Soto inició su jornada.

[Luis Díaz] retiró Pedido #101
[Luis Díaz] Estado: EN_REPARTO
[Luis Díaz] Pedido #101 en reparto...

[Daniela Tapia] retiró Pedido #102
[Daniela Tapia] Estado: EN_REPARTO
[Daniela Tapia] Pedido #102 en reparto...

[Camila Soto] retiró Pedido #103
[Camila Soto] Estado: EN_REPARTO
[Camila Soto] Pedido #103 en reparto...

[Luis Díaz] Pedido #101 ENTREGADO correctamente.

[Luis Díaz] retiró Pedido #104
[Luis Díaz] Estado: EN_REPARTO

[Camila Soto] Pedido #103 ENTREGADO correctamente.

[Daniela Tapia] Pedido #102 ENTREGADO correctamente.

...

Luis Díaz terminó su jornada.
Daniela Tapia terminó su jornada.
Camila Soto terminó su jornada.

==========================================
Todos los pedidos han sido entregados correctamente
==========================================
```

El orden puede variar entre ejecuciones debido a la programación concurrente.

Esto es un comportamiento normal del sistema.

---

# Estructura de Semana 5

La entrega correspondiente a esta semana se encuentra separada dentro del proyecto principal:

```text
SpeedFastPOO2
│
├── Semana 5
│   │
│   ├── src
│   │   └── cl.duoc.speedfast
│   │       ├── EstadoPedido.java
│   │       ├── Main.java
│   │       ├── Pedido.java
│   │       ├── Repartidor.java
│   │       └── ZonaDeCarga.java
│   │
│   ├── README.md
│   └── Semana 5.iml
│
└── src
    └── cl.duoc.speedfast
        └── Código desarrollado en semanas anteriores
```

Esta organización permite mantener la implementación de Semana 5 separada de las versiones desarrolladas anteriormente.

---

# Responsabilidad de las clases

| Clase | Responsabilidad |
|---|---|
| `Pedido` | Representa cada pedido del sistema |
| `EstadoPedido` | Define los estados válidos de los pedidos |
| `ZonaDeCarga` | Administra de forma sincronizada el recurso compartido |
| `Repartidor` | Retira y entrega pedidos mediante una tarea `Runnable` |
| `Main` | Crea los pedidos, repartidores y administra la ejecución concurrente |

---

# Conceptos aplicados

En esta actividad se aplican los siguientes conceptos:

- Programación Orientada a Objetos.
- Clases y objetos.
- Encapsulamiento.
- Enumeraciones (`enum`).
- Interfaces.
- `Runnable`.
- Hilos.
- Programación concurrente.
- Recursos compartidos.
- Secciones críticas.
- Condiciones de carrera.
- Sincronización.
- `synchronized`.
- `Thread.sleep()`.
- `ExecutorService`.
- `Executors.newFixedThreadPool()`.
- `shutdown()`.
- `awaitTermination()`.
- Manejo de `InterruptedException`.

---

# Tecnologías utilizadas

- Java
- IntelliJ IDEA
- JavaDoc
- Git
- GitHub
- API de concurrencia de Java

---

# Ejecución

Para ejecutar la actividad:

1. Abrir el proyecto en IntelliJ IDEA.
2. Abrir el módulo `Semana 5`.
3. Verificar que el JDK esté configurado correctamente.
4. Abrir la clase:

```text
https://github.com/pamarquezb-debug/SpeedFastPOO2/tree/master/Semana%205/src/cl/duoc/speedfast/Main.java
```

5. Ejecutar:

```java
public static void main(String[] args)
```

6. Observar en consola cómo los repartidores retiran y entregan los pedidos concurrentemente.

---

# Resultado esperado

El sistema debe garantizar que:

- Todos los pedidos comiencen como `PENDIENTE`.
- Cada pedido sea retirado una sola vez.
- Al retirarlo cambie a `EN_REPARTO`.
- Cada repartidor trabaje de forma independiente.
- Los repartidores puedan realizar entregas concurrentemente.
- Al finalizar una entrega, el pedido cambie a `ENTREGADO`.
- Ningún pedido sea entregado por más de un repartidor.
- Todos los pedidos terminen correctamente.

Al finalizar la simulación debe aparecer:

```text
Todos los pedidos han sido entregados correctamente
```

---

# Conclusión

La implementación de la Semana 5 permite aplicar mecanismos de sincronización sobre un sistema concurrente.

La clase `ZonaDeCarga` funciona como un recurso compartido al que acceden múltiples repartidores. Mediante el uso de métodos `synchronized`, se garantiza que solamente un hilo pueda retirar un pedido en un momento determinado.

Esto evita condiciones de carrera y asegura que cada pedido sea procesado exclusivamente por un repartidor.

El uso conjunto de `Runnable`, `Thread.sleep()`, `ExecutorService`, `enum` y `synchronized` permite representar un escenario concurrente controlado, manteniendo la integridad de los datos durante toda la simulación.

---

# Autor

**Pablo Márquez**

Programación Orientada a Objetos II  
Duoc UC  
Semana 5
