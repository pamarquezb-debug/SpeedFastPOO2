# SpeedFastPOO2

## Programación Orientada a Objetos II

### Semana 4 - Ejecutando tareas en paralelo con hilos en Java

Proyecto desarrollado como parte de la asignatura **Programación Orientada a Objetos II**.

Durante esta semana se continúa trabajando con el caso de la empresa **SpeedFast**, incorporando programación concurrente mediante el uso de `Runnable`, `Thread.sleep()` y `ExecutorService`.

El objetivo principal es simular varios repartidores realizando entregas simultáneamente, donde cada repartidor funciona como una tarea independiente que procesa secuencialmente los pedidos que tiene asignados.

---

# Descripción del proyecto

SpeedFast es una empresa dedicada al reparto de diferentes tipos de pedidos:

- Pedidos de comida.
- Encomiendas.
- Pedidos express.

Durante las semanas anteriores se desarrolló una estructura orientada a objetos utilizando:

- Clases abstractas.
- Herencia.
- Polimorfismo.
- Sobrecarga de métodos.
- Sobrescritura de métodos.
- Interfaces.

En la **Semana 4** se reutiliza esta estructura y se incorpora **programación concurrente**, permitiendo que varios repartidores puedan realizar entregas al mismo tiempo.

Cada repartidor posee una lista de pedidos y se ejecuta como una tarea independiente mediante la interfaz `Runnable`.

---

# Objetivo

El objetivo de esta actividad es implementar programación multihilo en Java para simular un entorno de entregas concurrentes.

El sistema permite:

- Crear distintos tipos de pedidos.
- Crear repartidores.
- Asignar múltiples pedidos a cada repartidor.
- Ejecutar cada repartidor como una tarea independiente.
- Procesar los pedidos de cada repartidor secuencialmente.
- Ejecutar varios repartidores simultáneamente.
- Simular el tiempo de entrega utilizando pausas aleatorias.
- Mostrar el progreso de las entregas mediante la consola.
- Esperar la finalización de todos los repartidores antes de terminar el programa.

---

# Estructura orientada a objetos

El sistema mantiene la jerarquía desarrollada durante las semanas anteriores.

```text
                    Pedido
                (Clase abstracta)
                       |
        --------------------------------
        |              |               |
 PedidoComida   PedidoEncomienda   PedidoExpress
```

La clase abstracta `Pedido` contiene los atributos y comportamientos comunes de todos los pedidos.

Entre sus principales atributos se encuentran:

```java
protected int idPedido;
protected String direccionEntrega;
protected double distanciaKm;
protected String repartidorAsignado;
protected String estado;
```

Además, define métodos como:

```java
mostrarResumen()
asignarRepartidor()
asignarRepartidor(String nombre)
calcularTiempoEntrega()
despachar()
cancelar()
```

---

# Clase abstracta Pedido

`Pedido` es la clase base del sistema.

Se declara como:

```java
public abstract class Pedido implements Despachable, Cancelable
```

Esto permite centralizar los atributos y comportamientos comunes y obligar a las clases derivadas a implementar comportamientos específicos.

Los métodos abstractos son:

```java
public abstract void asignarRepartidor();

public abstract int calcularTiempoEntrega();
```

Cada tipo de pedido implementa estos métodos según sus propias reglas.

---

# Tipos de pedidos

## PedidoComida

Representa pedidos asociados al reparto de comida.

El tiempo estimado se calcula considerando un tiempo base más un tiempo adicional según la distancia recorrida.

Ejemplo:

```text
Distancia: 4 km
Tiempo estimado: 23 minutos
```

---

## PedidoEncomienda

Representa el transporte de encomiendas.

Utiliza una regla de cálculo diferente para determinar el tiempo estimado de entrega.

Ejemplo:

```text
Distancia: 5 km
Tiempo estimado: 28 minutos
```

---

## PedidoExpress

Representa pedidos que requieren una entrega rápida.

Posee un tiempo base y agrega tiempo adicional cuando la distancia supera los 5 kilómetros.

Ejemplo:

```text
Distancia: 8 km
Tiempo estimado: 15 minutos
```

---

# Interfaces

El proyecto reutiliza las interfaces desarrolladas anteriormente.

## Despachable

Define el comportamiento necesario para despachar un pedido.

```java
public interface Despachable {

    void despachar();
}
```

---

## Cancelable

Define el comportamiento necesario para cancelar un pedido.

```java
public interface Cancelable {

    void cancelar();
}
```

---

## Rastreable

Define el comportamiento necesario para consultar el historial de pedidos.

```java
public interface Rastreable {

    void verHistorial();
}
```

---

# Programación concurrente

Durante la Semana 4 se incorpora programación concurrente para permitir que múltiples repartidores realicen entregas simultáneamente.

Para esto se utilizan principalmente:

```text
Runnable
Thread.sleep()
ExecutorService
Executors
```

---

# Clase Repartidor

La clase `Repartidor` representa a un trabajador encargado de realizar entregas.

Cada repartidor posee:

```java
private final String nombre;
private final List<Pedido> pedidos;
```

La clase implementa la interfaz:

```java
Runnable
```

Por lo tanto, su declaración es:

```java
public class Repartidor implements Runnable
```

Esto permite que cada repartidor sea ejecutado como una tarea independiente.

---

# Método run()

La interfaz `Runnable` obliga a implementar:

```java
@Override
public void run()
```

Dentro de este método, cada repartidor recorre secuencialmente su lista de pedidos.

Conceptualmente:

```java
for (Pedido pedido : pedidos) {

    // Asignar repartidor

    // Mostrar pedido

    // Calcular tiempo estimado

    // Simular entrega

    // Despachar pedido
}
```

Aunque los pedidos de un mismo repartidor se procesan secuencialmente, varios repartidores pueden ejecutar sus listas simultáneamente.

---

# Asignación del repartidor

Antes de mostrar el resumen de cada pedido se asigna el nombre del repartidor responsable:

```java
pedido.setRepartidorAsignado(nombre);
```

De esta forma la información mostrada en consola corresponde al repartidor que está ejecutando realmente la entrega.

Ejemplo:

```text
Pedido #101
Dirección: Av. Providencia 1234
Distancia: 4.0 km
Repartidor asignado: Luis Díaz
Estado: Pendiente
```

---

# Simulación del tiempo de entrega

Para representar que una entrega demora cierto tiempo se utiliza:

```java
Thread.sleep()
```

El tiempo utilizado para la simulación se genera aleatoriamente.

Ejemplo:

```java
int tiempoSimulado =
        random.nextInt(2000) + 1000;

Thread.sleep(tiempoSimulado);
```

Esto genera una pausa aproximada de entre **1 y 3 segundos**.

La pausa representa de forma simplificada el tiempo que demora un repartidor en completar una entrega.

---

# Manejo de InterruptedException

`Thread.sleep()` puede generar una excepción `InterruptedException`.

Por esta razón se utiliza:

```java
try {

    Thread.sleep(tiempoSimulado);

} catch (InterruptedException e) {

    Thread.currentThread().interrupt();

    return;
}
```

De esta manera se controla correctamente una posible interrupción del hilo.

---

# ExecutorService

Para administrar la ejecución concurrente de los repartidores se utiliza:

```java
ExecutorService
```

En la clase `Main` se crea un grupo de tres hilos:

```java
ExecutorService executor =
        Executors.newFixedThreadPool(3);
```

Esto permite ejecutar hasta tres tareas simultáneamente.

---

# Ejecución de los repartidores

Se crean al menos tres repartidores:

```java
Repartidor repartidor1 =
        new Repartidor("Luis Díaz", pedidosLuis);

Repartidor repartidor2 =
        new Repartidor("Daniela Tapia", pedidosDaniela);

Repartidor repartidor3 =
        new Repartidor("Camila Soto", pedidosCamila);
```

Cada repartidor posee dos pedidos asignados.

Posteriormente se envían las tareas al `ExecutorService`:

```java
executor.execute(repartidor1);
executor.execute(repartidor2);
executor.execute(repartidor3);
```

A partir de este momento los repartidores comienzan a procesar sus pedidos concurrentemente.

---

# Finalización del ExecutorService

Después de enviar las tareas se ejecuta:

```java
executor.shutdown();
```

Este método indica que no se aceptarán nuevas tareas, pero permite que las tareas que ya están ejecutándose terminen normalmente.

Posteriormente se utiliza:

```java
executor.awaitTermination(
        1,
        TimeUnit.MINUTES
);
```

Esto permite que el programa principal espere hasta que todos los repartidores hayan terminado sus entregas.

---

# Ejemplo de ejecución

Debido a que los repartidores trabajan concurrentemente, el orden de los mensajes puede cambiar en cada ejecución.

Ejemplo:

```text
===== INICIO SIMULACIÓN SPEEDFAST =====

Camila Soto inició su jornada de entregas.
Daniela Tapia inició su jornada de entregas.
Luis Díaz inició su jornada de entregas.

[Luis Díaz] Iniciando pedido #101
[Camila Soto] Iniciando pedido #105
[Daniela Tapia] Iniciando pedido #103

Pedido #101
Dirección: Av. Providencia 1234
Distancia: 4.0 km
Repartidor asignado: Luis Díaz
Estado: Pendiente

Pedido #105
Dirección: Av. Apoquindo 6000
Distancia: 8.0 km
Repartidor asignado: Camila Soto
Estado: Pendiente

Pedido #103
Dirección: Av. Santa Rosa 567
Distancia: 5.0 km
Repartidor asignado: Daniela Tapia
Estado: Pendiente

[Luis Díaz] Pedido en camino...
[Camila Soto] Pedido en camino...
[Daniela Tapia] Pedido en camino...

Pedido #101 despachado correctamente.
[Luis Díaz] Pedido #101 entregado.

Pedido #105 despachado correctamente.
[Camila Soto] Pedido #105 entregado.

Pedido #103 despachado correctamente.
[Daniela Tapia] Pedido #103 entregado.

...

Luis Díaz terminó todas sus entregas.
Daniela Tapia terminó todas sus entregas.
Camila Soto terminó todas sus entregas.

===== TODAS LAS ENTREGAS FINALIZARON =====
```

El orden de ejecución no es necesariamente siempre el mismo, ya que los hilos son administrados concurrentemente por Java.

---

# Concurrencia del sistema

La ejecución puede representarse conceptualmente de la siguiente manera:

```text
                       Main
                         |
                         |
                 ExecutorService
                         |
          -------------------------------
          |              |              |
          v              v              v
     Repartidor      Repartidor      Repartidor
     Luis Díaz      Daniela Tapia    Camila Soto
          |              |              |
          v              v              v
     Pedido 101      Pedido 103      Pedido 105
          |              |              |
          v              v              v
     Pedido 102      Pedido 104      Pedido 106
```

Cada columna procesa sus pedidos secuencialmente.

Sin embargo, las tres columnas pueden ejecutarse simultáneamente.

---

# Estructura del proyecto

```text
SpeedFastPOO2
│
├── src
│   └── cl.duoc.speedfast
│       ├── Main.java
│       ├── Pedido.java
│       ├── PedidoComida.java
│       ├── PedidoEncomienda.java
│       ├── PedidoExpress.java
│       ├── Repartidor.java
│       ├── Despachable.java
│       ├── Cancelable.java
│       ├── Rastreable.java
│       └── ControladorDeEnvios.java
│
├── readme.md
├── .gitignore
└── SpeedFastPOO2.iml
```

> IntelliJ IDEA puede mostrar el paquete `cl.duoc.speedfast` de forma compacta en el panel del proyecto.

---

# Clases principales

| Clase | Responsabilidad |
|---|---|
| `Pedido` | Clase abstracta base para todos los pedidos |
| `PedidoComida` | Implementa las reglas de pedidos de comida |
| `PedidoEncomienda` | Implementa las reglas de encomiendas |
| `PedidoExpress` | Implementa las reglas de pedidos express |
| `Repartidor` | Procesa una lista de pedidos mediante `Runnable` |
| `ControladorDeEnvios` | Administra el historial de pedidos |
| `Main` | Crea pedidos, repartidores y administra la ejecución concurrente |

---

# Interfaces utilizadas

| Interfaz | Responsabilidad |
|---|---|
| `Despachable` | Define la operación para despachar pedidos |
| `Cancelable` | Define la operación para cancelar pedidos |
| `Rastreable` | Define la operación para consultar historial |

---

# Conceptos aplicados

Durante el desarrollo de esta actividad se aplican los siguientes conceptos de Programación Orientada a Objetos y programación concurrente:

- Clases.
- Objetos.
- Encapsulamiento.
- Herencia.
- Abstracción.
- Polimorfismo.
- Sobrescritura de métodos.
- Sobrecarga de métodos.
- Interfaces.
- Colecciones mediante `List`.
- Implementación de `Runnable`.
- Hilos.
- Programación concurrente.
- `Thread.sleep()`.
- `ExecutorService`.
- `Executors.newFixedThreadPool()`.
- `shutdown()`.
- `awaitTermination()`.
- Manejo de `InterruptedException`.

---

# Diferencia entre ejecución secuencial y concurrente

En una ejecución secuencial, un repartidor tendría que terminar todos sus pedidos antes de comenzar el siguiente repartidor:

```text
Luis
 |
Pedido 101
 |
Pedido 102
 |
Daniela
 |
Pedido 103
 |
Pedido 104
 |
Camila
 |
Pedido 105
 |
Pedido 106
```

Con programación concurrente:

```text
Luis          Daniela         Camila
 |               |              |
Pedido 101    Pedido 103     Pedido 105
 |               |              |
Pedido 102    Pedido 104     Pedido 106
```

Los repartidores pueden avanzar simultáneamente.

---

# Tecnologías utilizadas

- Java
- IntelliJ IDEA
- Git
- GitHub
- JavaDoc
- API de concurrencia de Java
- `Runnable`
- `Thread`
- `ExecutorService`
- `ArrayList` / `List`

---

# Requisitos

Para ejecutar el proyecto se necesita:

- Java JDK instalado.
- IntelliJ IDEA o cualquier IDE compatible con Java.
- Git, en caso de clonar el proyecto desde GitHub.

---

# Clonar el proyecto

Desde una terminal:

```bash
git clone https://github.com/USUARIO/SpeedFastPOO2.git
```

Ingresar al proyecto:

```bash
cd SpeedFastPOO2
```

Luego abrir la carpeta desde IntelliJ IDEA.

---

# Ejecución en IntelliJ IDEA

1. Abrir el proyecto `SpeedFastPOO2`.
2. Verificar que el JDK esté correctamente configurado.
3. Abrir:

```text
src/cl/duoc/speedfast/Main.java
```

4. Ejecutar el método:

```java
public static void main(String[] args)
```

5. Observar la ejecución concurrente de los repartidores en la consola.

---

# Resultado

El sistema permite simular un escenario donde varios repartidores de SpeedFast realizan entregas al mismo tiempo.

Cada repartidor procesa sus propios pedidos secuencialmente, mientras que `ExecutorService` permite que múltiples repartidores sean ejecutados en paralelo.

El uso de `Thread.sleep()` permite representar de manera simplificada el tiempo necesario para realizar cada entrega.

Finalmente, `shutdown()` y `awaitTermination()` permiten controlar correctamente la finalización de todas las tareas antes de terminar la aplicación.

---

# Conclusión

La implementación desarrollada durante la Semana 4 permite extender el sistema SpeedFast incorporando programación concurrente sin eliminar la estructura orientada a objetos construida anteriormente.

La interfaz `Runnable` permite representar cada repartidor como una tarea independiente, mientras que `ExecutorService` facilita la administración de múltiples hilos.

De esta forma, el sistema puede simular varios repartidores realizando entregas simultáneamente, demostrando cómo la concurrencia puede mejorar el procesamiento de tareas independientes dentro de una aplicación.

---

# Autor

**Pablo Márquez**

Programación Orientada a Objetos II  
Duoc UC  
Semana 4