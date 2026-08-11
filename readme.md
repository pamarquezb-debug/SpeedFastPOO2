# SpeedFastPOO2

## Programación Orientada a Objetos II

Proyecto desarrollado en **Java** para la asignatura **Programación Orientada a Objetos II**.

La actividad corresponde a la Semana 1:

**"Explorando la sobrecarga y sobreescritura en clases derivadas"**

---

## Descripción del proyecto

SpeedFast es una empresa ficticia dedicada al reparto a domicilio que ofrece tres tipos de servicios:

- **Comida:** pedidos provenientes de restaurantes.
- **Encomiendas:** envío de documentos o paquetes.
- **Compra Express:** compras realizadas principalmente en supermercados o farmacias.

Cada tipo de pedido posee diferentes criterios para realizar la asignación de un repartidor.

El objetivo de este proyecto es representar estos distintos tipos de pedidos mediante una jerarquía de clases y aplicar conceptos fundamentales de la **Programación Orientada a Objetos**, especialmente:

- Herencia.
- Polimorfismo.
- Sobrecarga de métodos.
- Sobreescritura de métodos.

---

## Reglas de asignación

Cada tipo de pedido posee requisitos diferentes para la asignación del repartidor.

### Pedido de Comida

El repartidor debe disponer de una **mochila térmica**, necesaria para mantener los alimentos en condiciones adecuadas durante el transporte.

### Pedido de Encomienda

Antes de realizar la asignación se debe considerar la **validación del peso y del embalaje** del paquete o documento.

### Pedido Express

Se debe buscar al **repartidor más cercano que posea disponibilidad inmediata**.

---

## Estructura de clases

El sistema utiliza una clase base denominada `Pedido`, desde la cual se derivan tres clases especializadas.

```text
Pedido
│
├── PedidoComida
├── PedidoEncomienda
└── PedidoExpress
```

La clase `Pedido` contiene los atributos y comportamientos comunes, mientras que las clases derivadas implementan el comportamiento específico requerido por cada tipo de servicio.

---

## Clase Pedido

`Pedido` corresponde a la clase base de la jerarquía.

Contiene los siguientes atributos:

```java
protected int idPedido;
protected String direccionEntrega;
protected String tipoPedido;
```

Estos atributos representan la información común para todos los pedidos registrados en SpeedFast.

La clase también define los métodos:

```java
public void asignarRepartidor()

public void asignarRepartidor(String nombreRepartidor)

public void mostrarDatos()
```

El método `asignarRepartidor()` proporciona un comportamiento general que posteriormente puede ser sobrescrito por las clases derivadas.

---

## Herencia

Las clases:

```text
PedidoComida
PedidoEncomienda
PedidoExpress
```

heredan de la clase base `Pedido`.

Ejemplo:

```java
public class PedidoComida extends Pedido {
    // Implementación específica
}
```

Gracias a la herencia, las clases especializadas pueden reutilizar los atributos y métodos definidos en `Pedido`, además de incorporar o modificar sus propios comportamientos.

---

## Sobrecarga de métodos

La **sobrecarga** se implementa mediante dos métodos que poseen el mismo nombre, pero distinta firma.

```java
public void asignarRepartidor()
```

y:

```java
public void asignarRepartidor(String nombreRepartidor)
```

El primer método realiza una asignación general, mientras que el segundo permite proporcionar el nombre del repartidor que será asignado.

La diferencia en sus parámetros permite que Java determine qué versión del método debe ejecutar.

---

## Sobreescritura de métodos

Las clases derivadas sobrescriben el método `asignarRepartidor()` definido originalmente en la clase `Pedido`.

Para ello se utiliza la anotación:

```java
@Override
```

Por ejemplo, en `PedidoComida`:

```java
@Override
public void asignarRepartidor() {
    System.out.println(
        "Buscando repartidor disponible con mochila térmica..."
    );
}
```

Esto permite que cada clase derivada proporcione su propia implementación del método según las características del pedido.

También se sobrescribe la versión:

```java
asignarRepartidor(String nombreRepartidor)
```

permitiendo mostrar las validaciones específicas de cada servicio.

---

## Polimorfismo

El polimorfismo permite que un mismo método presente diferentes comportamientos dependiendo del tipo de objeto que lo ejecuta.

En este proyecto, el método:

```java
asignarRepartidor()
```

se comporta de manera diferente en:

- `PedidoComida`
- `PedidoEncomienda`
- `PedidoExpress`

Cada clase implementa las reglas correspondientes al servicio que representa.

---

## Clase Main

La clase `Main` es utilizada para comprobar el funcionamiento del sistema.

En ella se crea al menos un objeto de cada clase derivada.

```java
PedidoComida comida =
        new PedidoComida(
                1,
                "Av. Providencia 1500"
        );

PedidoEncomienda encomienda =
        new PedidoEncomienda(
                2,
                "Av. Apoquindo 3200",
                4.5
        );

PedidoExpress express =
        new PedidoExpress(
                3,
                "Gran Avenida 4500"
        );
```

Posteriormente se ejecutan las distintas versiones del método `asignarRepartidor()`.

Ejemplo:

```java
comida.asignarRepartidor();

comida.asignarRepartidor("Carlos");
```

Esto permite comprobar tanto la **sobreescritura** como la **sobrecarga de métodos**.

---

## Ejemplo de ejecución

Al ejecutar la aplicación se obtiene una salida similar a la siguiente:

```text
===== PEDIDO DE COMIDA =====
ID Pedido: 1
Dirección: Av. Providencia 1500
Tipo de pedido: Comida
Buscando repartidor disponible con mochila térmica...
Repartidor Carlos asignado al pedido de comida.
Validación: el repartidor debe contar con mochila térmica.

===== PEDIDO DE ENCOMIENDA =====
ID Pedido: 2
Dirección: Av. Apoquindo 3200
Tipo de pedido: Encomienda
Buscando repartidor para encomienda...
Se debe validar el peso y el embalaje.
Repartidor María asignado a la encomienda.
Peso de la encomienda: 4.5 kg.
Validación de peso y embalaje realizada.

===== COMPRA EXPRESS =====
ID Pedido: 3
Dirección: Gran Avenida 4500
Tipo de pedido: Compra Express
Buscando el repartidor más cercano con disponibilidad inmediata...
Repartidor Pedro asignado a la compra express.
Validación: repartidor cercano y disponible inmediatamente.
```

---

## Estructura del proyecto

El código fuente se encuentra organizado dentro del paquete:

```text
cl.duoc.speedfast
```

La estructura principal es:

```text
SpeedFastPOO2
│
├── src
│   └── cl.duoc.speedfast
│       ├── Main.java
│       ├── Pedido.java
│       ├── PedidoComida.java
│       ├── PedidoEncomienda.java
│       └── PedidoExpress.java
│
├── README.md
├── .gitignore
└── SpeedFastPOO2.iml
```

---

## Tecnologías utilizadas

- Java
- IntelliJ IDEA
- Git
- GitHub

---

## Ejecución

Para ejecutar el proyecto:

1. Abrir el proyecto `SpeedFastPOO2` en IntelliJ IDEA.
2. Verificar que se encuentre configurado un JDK de Java.
3. Abrir la clase `Main`.
4. Ejecutar el método `main()`.
5. Revisar los resultados en la consola de IntelliJ IDEA.

---

## Objetivos alcanzados

Mediante el desarrollo de esta actividad se aplicaron los siguientes conceptos:

- Creación de clases y objetos.
- Uso de atributos y constructores.
- Creación de una jerarquía de clases.
- Herencia mediante `extends`.
- Sobrecarga de métodos.
- Sobreescritura mediante `@Override`.
- Aplicación de polimorfismo.
- Uso de JavaDoc para documentar las clases y métodos.
- Prueba de las clases mediante una clase principal `Main`.

---

## Autor

**Pablo Márquez**

Proyecto académico desarrollado para la asignatura:

**Programación Orientada a Objetos II**

Duoc UC