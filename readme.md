# SpeedFastPOO2

## Programación Orientada a Objetos II

Proyecto desarrollado en **Java** para la asignatura **Programación Orientada a Objetos II** de **Duoc UC**.

### Semana 3 — Actividad Sumativa

**"Diseñando un sistema orientado a objetos con clases abstractas, polimorfismo e interfaces"**

---

# Descripción del proyecto

**SpeedFast** es una empresa ficticia dedicada al reparto a domicilio que ofrece tres tipos de servicios:

* **Comida:** pedidos provenientes de restaurantes.
* **Encomiendas:** envío de documentos o paquetes.
* **Compra Express:** compras realizadas principalmente en supermercados o farmacias.

Durante las semanas anteriores se desarrollaron diferentes componentes del sistema aplicando conceptos de Programación Orientada a Objetos.

En esta tercera etapa se desarrolla una versión integral del sistema, incorporando conjuntamente:

* Abstracción.
* Herencia.
* Polimorfismo.
* Sobrecarga de métodos.
* Sobreescritura de métodos.
* Interfaces.
* Colecciones mediante `ArrayList`.
* Separación de responsabilidades.

---

# Objetivo

El objetivo de esta actividad es desarrollar una solución orientada a objetos que permita administrar diferentes tipos de pedidos de SpeedFast.

El sistema permite:

* Crear diferentes tipos de pedidos.
* Asignar repartidores automáticamente.
* Asignar repartidores manualmente.
* Calcular tiempos estimados de entrega.
* Despachar pedidos.
* Cancelar pedidos.
* Registrar entregas.
* Consultar el historial de pedidos procesados.

---

# Estructura general

El sistema utiliza una clase abstracta denominada `Pedido`.

De ella heredan tres clases especializadas:

```text
                     Pedido
                  <<abstracta>>
                       │
          ┌────────────┼────────────┐
          │            │            │
          ▼            ▼            ▼
   PedidoComida  PedidoEncomienda  PedidoExpress
```

Además, se utilizan las interfaces:

```text
Despachable
Cancelable
Rastreable
```

y la clase:

```text
ControladorDeEnvios
```

para administrar el historial de los pedidos.

---

# Clase abstracta Pedido

`Pedido` representa las características comunes de todos los pedidos administrados por SpeedFast.

La clase está declarada como:

```java
public abstract class Pedido implements Despachable, Cancelable
```

Sus principales atributos son:

```java
protected int idPedido;
protected String direccionEntrega;
protected double distanciaKm;
protected String repartidorAsignado;
protected String estado;
```

Estos atributos permiten almacenar:

* Identificador del pedido.
* Dirección de entrega.
* Distancia de entrega.
* Repartidor asignado.
* Estado actual del pedido.

---

# Abstracción

La clase `Pedido` es abstracta, por lo que no puede ser instanciada directamente.

No es posible realizar:

```java
Pedido pedido = new Pedido(...);
```

En cambio, deben utilizarse sus clases derivadas:

```java
PedidoComida comida =
        new PedidoComida(
                101,
                "Av. Providencia 1234",
                4.0
        );
```

La clase abstracta permite concentrar los atributos y comportamientos comunes evitando duplicar código en las clases especializadas.

---

# Método mostrarResumen()

La clase `Pedido` implementa el método:

```java
public void mostrarResumen()
```

Este método permite visualizar los datos principales del pedido:

```text
Pedido #101
Dirección: Av. Providencia 1234
Distancia: 4.0 km
Repartidor asignado: Luis Díaz
Estado: Pendiente
```

Debido a la herencia, todas las clases derivadas pueden reutilizar este método.

---

# Cálculo del tiempo de entrega

La clase abstracta `Pedido` declara:

```java
public abstract int calcularTiempoEntrega();
```

Cada clase derivada debe implementar este método utilizando su propia regla de negocio.

---

## PedidoComida

Los pedidos de comida utilizan la siguiente regla:

**15 minutos base + 2 minutos por cada kilómetro.**

Por ejemplo, para una distancia de 4 kilómetros:

```text
15 + (2 × 4) = 23 minutos
```

Implementación:

```java
@Override
public int calcularTiempoEntrega() {
    return (int) Math.round(
            15 + (2 * distanciaKm)
    );
}
```

---

## PedidoEncomienda

Las encomiendas utilizan:

**20 minutos base + 1.5 minutos por kilómetro.**

Por ejemplo, para una distancia de 7 kilómetros:

```text
20 + (1.5 × 7) = 30.5
```

El resultado se ajusta a un número entero utilizando `Math.round()`:

```text
31 minutos
```

Implementación:

```java
@Override
public int calcularTiempoEntrega() {
    return (int) Math.round(
            20 + (1.5 * distanciaKm)
    );
}
```

---

## PedidoExpress

Los pedidos Express utilizan un tiempo base de:

```text
10 minutos
```

Si la distancia es superior a **5 kilómetros**, se agregan **5 minutos adicionales**.

Ejemplo para una distancia de 8 kilómetros:

```text
10 + 5 = 15 minutos
```

Implementación:

```java
@Override
public int calcularTiempoEntrega() {

    int tiempo = 10;

    if (distanciaKm > 5) {
        tiempo += 5;
    }

    return tiempo;
}
```

---

# Polimorfismo

El sistema aplica polimorfismo mediante métodos que presentan comportamientos diferentes dependiendo del tipo de pedido.

Uno de ellos es:

```java
asignarRepartidor()
```

Las clases:

```text
PedidoComida
PedidoEncomienda
PedidoExpress
```

sobrescriben este método utilizando `@Override`.

---

# Sobreescritura

Cada clase derivada implementa su propia lógica para realizar una asignación automática.

Por ejemplo, un pedido de comida requiere un repartidor que disponga de mochila térmica:

```java
@Override
public void asignarRepartidor() {

    repartidorAsignado = "Luis Díaz";

    System.out.println(
            "Asignación automática para Pedido Comida."
    );

    System.out.println(
            "Repartidor: " + repartidorAsignado
    );

    System.out.println(
            "Validación: repartidor con mochila térmica."
    );
}
```

En una encomienda se considera la validación del peso y embalaje.

En una compra Express se considera al repartidor más cercano con disponibilidad inmediata.

---

# Sobrecarga

También se implementa una segunda versión del método:

```java
public void asignarRepartidor(String nombre)
```

Esta versión permite asignar manualmente un repartidor.

Ejemplo:

```java
encomienda.asignarRepartidor(
        "Daniela Tapia"
);
```

Por lo tanto, existen dos métodos con el mismo nombre:

```java
asignarRepartidor()
```

y:

```java
asignarRepartidor(String nombre)
```

Esto corresponde a **sobrecarga de métodos**, ya que poseen el mismo nombre pero diferente firma.

---

# Interfaces

Para desacoplar responsabilidades se utilizan tres interfaces.

---

## Interface Despachable

Define la operación:

```java
void despachar();
```

Su responsabilidad es establecer que un objeto puede ser despachado.

```java
public interface Despachable {

    void despachar();
}
```

La clase abstracta `Pedido` implementa esta interfaz.

---

## Interface Cancelable

Define:

```java
void cancelar();
```

Su objetivo es establecer el comportamiento requerido para cancelar un pedido.

```java
public interface Cancelable {

    void cancelar();
}
```

También es implementada por la clase `Pedido`.

---

## Interface Rastreable

Define:

```java
void verHistorial();
```

Esta interfaz permite separar la responsabilidad relacionada con el seguimiento e historial de los pedidos.

```java
public interface Rastreable {

    void verHistorial();
}
```

Es implementada por:

```java
ControladorDeEnvios
```

---

# ControladorDeEnvios

La clase `ControladorDeEnvios` administra el historial de pedidos procesados.

Está declarada como:

```java
public class ControladorDeEnvios implements Rastreable
```

Para almacenar el historial se utiliza:

```java
ArrayList<String>
```

Ejemplo:

```java
private final ArrayList<String> historial;
```

Los pedidos pueden registrarse mediante:

```java
registrarEntrega(Pedido pedido)
```

Posteriormente, el historial puede visualizarse utilizando:

```java
verHistorial()
```

---

# Uso de ArrayList

El historial se almacena utilizando una colección dinámica:

```java
ArrayList<String>
```

Esta colección permite agregar nuevos registros sin definir previamente una cantidad máxima de elementos.

Ejemplo de información almacenada:

```text
PedidoComida #101 - Despachado por Luis Díaz
PedidoEncomienda #102 - Despachado por Daniela Tapia
PedidoExpress #103 - Cancelado por Camila Soto
```

---

# Clase Main

La clase `Main` contiene la simulación general solicitada para la actividad.

Durante la ejecución se realizan las siguientes operaciones:

1. Creación de pedidos.
2. Asignación automática de repartidores.
3. Asignación manual de un repartidor.
4. Visualización de información.
5. Cálculo del tiempo estimado.
6. Despacho de pedidos.
7. Cancelación de un pedido.
8. Registro de los pedidos.
9. Visualización del historial.

---

# Ejemplo de ejecución

Una ejecución del sistema puede producir una salida similar a:

```text
===== PEDIDO COMIDA =====
Asignación automática para Pedido Comida.
Repartidor: Luis Díaz
Validación: repartidor con mochila térmica.
Pedido #101
Dirección: Av. Providencia 1234
Distancia: 4.0 km
Repartidor asignado: Luis Díaz
Estado: Pendiente
Tiempo estimado: 23 minutos
Pedido #101 despachado correctamente.

===== PEDIDO ENCOMIENDA =====
Repartidor asignado manualmente: Daniela Tapia
Pedido #102
Dirección: Av. Santa Rosa 567
Distancia: 7.0 km
Repartidor asignado: Daniela Tapia
Estado: Pendiente
Tiempo estimado: 31 minutos
Pedido #102 despachado correctamente.

===== PEDIDO EXPRESS =====
Asignación automática para Pedido Express.
Repartidor más cercano: Camila Soto
Validación: disponibilidad inmediata.
Pedido #103
Dirección: Av. Apoquindo 3200
Distancia: 8.0 km
Repartidor asignado: Camila Soto
Estado: Pendiente
Tiempo estimado: 15 minutos
Cancelando Pedido Express #103...
Pedido #103 cancelado exitosamente.

===== HISTORIAL =====
- PedidoComida #101 - Despachado por Luis Díaz
- PedidoEncomienda #102 - Despachado por Daniela Tapia
- PedidoExpress #103 - Cancelado por Camila Soto
```

---

# Estructura del proyecto

El código fuente está organizado dentro del paquete:

```text
cl.duoc.speedfast
```

La estructura de la **Semana 3** es:

```text
semana3
│
├── src
│   └── cl.duoc.speedfast
│       ├── Main.java
│       ├── Pedido.java
│       ├── PedidoComida.java
│       ├── PedidoEncomienda.java
│       ├── PedidoExpress.java
│       ├── Despachable.java
│       ├── Cancelable.java
│       ├── Rastreable.java
│       └── ControladorDeEnvios.java
│
└── README.md
```

> IntelliJ IDEA puede mostrar `cl.duoc.speedfast` como un único elemento debido a la visualización compacta de paquetes.

---

# Diagrama conceptual de clases

La relación principal entre las clases e interfaces puede representarse de manera simplificada de la siguiente forma:

```text
              ┌─────────────────────┐
              │     Despachable     │
              │    <<interface>>    │
              │     despachar()     │
              └──────────▲──────────┘
                         │
                         │ implementa
                         │
              ┌──────────┴──────────┐
              │                     │
              │       Pedido        │
              │     <<abstract>>    │
              │                     │
              │ - idPedido          │
              │ - direccionEntrega  │
              │ - distanciaKm       │
              │ - repartidor        │
              │ - estado            │
              │                     │
              │ + mostrarResumen()  │
              │ + asignarRepartidor │
              │ + calcularTiempo()  │
              │ + despachar()       │
              │ + cancelar()        │
              └──────────┬──────────┘
                         │
        ┌────────────────┼────────────────┐
        │                │                │
        ▼                ▼                ▼
 PedidoComida    PedidoEncomienda   PedidoExpress


              ┌─────────────────────┐
              │      Cancelable     │
              │    <<interface>>    │
              │      cancelar()     │
              └──────────▲──────────┘
                         │
                         │ implementada
                         │ por Pedido


              ┌─────────────────────┐
              │      Rastreable     │
              │    <<interface>>    │
              │    verHistorial()   │
              └──────────▲──────────┘
                         │
                         │ implementa
                         │
              ┌──────────┴──────────┐
              │ ControladorDeEnvios │
              │                     │
              │ - historial         │
              │ + registrarEntrega()│
              │ + verHistorial()    │
              └─────────────────────┘
```

---

# Escalabilidad

La estructura propuesta facilita la escalabilidad del sistema.

Si en el futuro SpeedFast incorpora un nuevo servicio, por ejemplo:

```text
PedidoFarmacia
```

se puede crear una nueva clase que herede de `Pedido`:

```java
public class PedidoFarmacia extends Pedido {
    // Implementación específica
}
```

La nueva clase deberá implementar los comportamientos abstractos requeridos sin modificar significativamente las clases existentes.

Esto permite ampliar el sistema de manera controlada.

---

# Reutilización

La clase abstracta `Pedido` concentra atributos y comportamientos compartidos por todos los tipos de pedidos.

Por ejemplo:

```text
idPedido
direccionEntrega
distanciaKm
repartidorAsignado
estado
```

y operaciones como:

```text
mostrarResumen()
despachar()
cancelar()
```

se implementan una sola vez y posteriormente son reutilizadas por las clases derivadas.

Esto evita duplicación innecesaria de código.

---

# Mantenibilidad

Las interfaces permiten separar las responsabilidades del sistema.

Por ejemplo:

* `Despachable` define exclusivamente la capacidad de despachar.
* `Cancelable` define la capacidad de cancelar.
* `Rastreable` define la capacidad de consultar un historial.

Esta separación permite modificar una funcionalidad específica sin afectar innecesariamente otras partes del sistema.

La combinación de clases abstractas, herencia, polimorfismo e interfaces facilita que el código sea más organizado y mantenible.

---

# Tecnologías utilizadas

* Java
* IntelliJ IDEA
* Git
* GitHub
* JavaDoc
* `ArrayList`

---

# Requisitos

Para ejecutar el proyecto se requiere:

* Java JDK instalado.
* IntelliJ IDEA o un IDE compatible con Java.
* Git para clonar el repositorio.

---

# Clonar el repositorio

Para obtener una copia local del proyecto:

```bash
git clone https://github.com/USUARIO/SpeedFastPOO2.git
```

Luego ingresar al directorio correspondiente:

```bash
cd SpeedFastPOO2
```

> Se debe reemplazar `USUARIO` por el nombre de usuario correspondiente en GitHub.

---

# Abrir en IntelliJ IDEA

1. Abrir **IntelliJ IDEA**.
2. Seleccionar **Open**.
3. Buscar el proyecto descargado.
4. Abrir la carpeta correspondiente a la Semana 3.
5. Esperar que IntelliJ IDEA cargue el proyecto.
6. Verificar que exista un JDK configurado.

---

# Ejecutar el proyecto

Abrir la clase:

```text
Main.java
```

y ejecutar:

```java
public static void main(String[] args)
```

La simulación completa será mostrada en la consola de IntelliJ IDEA.

---

# Conceptos aplicados

Durante el desarrollo de SpeedFast se aplicaron los siguientes conceptos:

### Abstracción

Implementada mediante la clase abstracta:

```text
Pedido
```

### Herencia

Implementada mediante:

```text
Pedido
   │
   ├── PedidoComida
   ├── PedidoEncomienda
   └── PedidoExpress
```

### Polimorfismo

Aplicado mediante la implementación diferente de métodos como:

```text
asignarRepartidor()
calcularTiempoEntrega()
```

### Sobrecarga

Aplicada mediante:

```java
asignarRepartidor()
```

y:

```java
asignarRepartidor(String nombre)
```

### Sobreescritura

Aplicada mediante `@Override` en las clases derivadas.

### Interfaces

Implementadas mediante:

```text
Despachable
Cancelable
Rastreable
```

### Colecciones

Utilización de:

```java
ArrayList<String>
```

para almacenar el historial de pedidos.

---

# Documentación

Las clases, interfaces, constructores y métodos principales se encuentran documentados utilizando **JavaDoc**.

Esto facilita la comprensión del código y permite identificar claramente la responsabilidad de cada componente.

---

# Conclusión

La versión integral de SpeedFast demuestra la aplicación conjunta de los principales conceptos estudiados durante las primeras semanas de Programación Orientada a Objetos II.

La utilización de una clase abstracta permite centralizar las características comunes de los pedidos, mientras que el polimorfismo permite implementar comportamientos específicos para cada tipo de entrega.

Las interfaces permiten desacoplar responsabilidades funcionales como despacho, cancelación y rastreo.

Finalmente, la utilización de un controlador independiente para gestionar el historial permite mantener separada la responsabilidad de seguimiento de las responsabilidades propias de cada pedido.

Esta estructura favorece la **escalabilidad, reutilización y mantenibilidad** del sistema.

---

# Autor

**Pablo Márquez**

Proyecto académico desarrollado para:

**Programación Orientada a Objetos II**

**Duoc UC — Semana 3**
