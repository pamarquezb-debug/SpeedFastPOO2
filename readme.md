# SpeedFastPOO2

## Programación Orientada a Objetos II

Proyecto desarrollado en **Java** para la asignatura **Programación Orientada a Objetos II** de **Duoc UC**.

El proyecto utiliza el caso de la empresa ficticia **SpeedFast**, dedicada al reparto a domicilio, para aplicar progresivamente distintos conceptos de Programación Orientada a Objetos.

Durante la **Semana 1** se trabajó principalmente con herencia, polimorfismo, sobrecarga y sobreescritura de métodos.

En la **Semana 2** el proyecto evoluciona incorporando una **clase abstracta**, métodos abstractos y reutilización de comportamiento mediante herencia.

---

# Descripción del proyecto

SpeedFast es una empresa ficticia dedicada al reparto a domicilio que ofrece tres tipos de servicios:

* **Comida:** pedidos provenientes de restaurantes.
* **Encomiendas:** envío de documentos o paquetes.
* **Compra Express:** compras realizadas principalmente en supermercados o farmacias.

Cada tipo de pedido posee características particulares que afectan el tiempo estimado necesario para realizar la entrega.

El sistema utiliza una jerarquía de clases que permite representar los diferentes tipos de pedidos y reutilizar los atributos y comportamientos que poseen en común.

---

# Semana 1 — Sobrecarga y sobreescritura

Durante la primera semana se desarrolló la estructura inicial del sistema SpeedFast.

Los principales conceptos aplicados fueron:

* Clases y objetos.
* Herencia.
* Polimorfismo.
* Sobrecarga de métodos.
* Sobreescritura de métodos.
* Uso de `@Override`.

Se creó una clase base `Pedido` y tres clases derivadas:

```text
Pedido
│
├── PedidoComida
├── PedidoEncomienda
└── PedidoExpress
```

Cada clase derivada implementó su propio comportamiento para la asignación de repartidores.

La sobrecarga se realizó mediante dos versiones del método:

```java
asignarRepartidor()
```

y:

```java
asignarRepartidor(String nombreRepartidor)
```

De esta manera se utilizaron métodos con el mismo nombre, pero con diferentes firmas.

Las clases derivadas también sobrescribieron los métodos heredados para adaptar su comportamiento a cada tipo de pedido.

---

# Semana 2 — Definiendo una clase abstracta y su jerarquía

Durante la segunda semana se modifica la estructura del sistema para desarrollar una solución más robusta y reutilizable.

La clase `Pedido` pasa a ser una **clase abstracta**:

```java
public abstract class Pedido
```

Esta clase representa las características comunes de todos los pedidos de SpeedFast.

---

## Clase abstracta Pedido

La clase `Pedido` contiene los siguientes atributos comunes:

```java
protected int idPedido;
protected String direccionEntrega;
protected double distanciaKm;
```

Estos atributos representan:

* `idPedido`: identificador único del pedido.
* `direccionEntrega`: dirección donde debe realizarse la entrega.
* `distanciaKm`: distancia de la entrega expresada en kilómetros.

La clase también contiene un método implementado:

```java
public void mostrarResumen()
```

Este método permite mostrar los datos básicos de cualquier pedido.

Además, se declara el método abstracto:

```java
public abstract int calcularTiempoEntrega();
```

Este método no posee implementación en la clase `Pedido`.

Cada clase derivada debe implementar su propia versión de `calcularTiempoEntrega()` de acuerdo con las reglas correspondientes al tipo de pedido.

---

# Jerarquía de clases

La estructura de clases utilizada durante la Semana 2 es:

```text
                 Pedido
             <<abstracta>>
                    │
        ┌───────────┼───────────┐
        │           │           │
        ▼           ▼           ▼
 PedidoComida  PedidoEncomienda  PedidoExpress
```

Las clases:

* `PedidoComida`
* `PedidoEncomienda`
* `PedidoExpress`

heredan los atributos y métodos comunes definidos en `Pedido`.

Cada una implementa de forma diferente:

```java
calcularTiempoEntrega()
```

---

# PedidoComida

La clase `PedidoComida` representa pedidos provenientes de restaurantes.

El tiempo estimado de entrega se calcula considerando:

**15 minutos base + 2 minutos por cada kilómetro.**

Por ejemplo, para una distancia de 4 kilómetros:

```text
15 + (2 × 4) = 23 minutos
```

La implementación es:

```java
@Override
public int calcularTiempoEntrega() {
    return (int) Math.round(
            15 + (2 * distanciaKm)
    );
}
```

---

# PedidoEncomienda

La clase `PedidoEncomienda` representa el envío de documentos o paquetes.

El tiempo estimado se calcula considerando:

**20 minutos base + 1.5 minutos por cada kilómetro.**

El resultado se ajusta a un número entero.

Por ejemplo, para una distancia de 6 kilómetros:

```text
20 + (1.5 × 6) = 29 minutos
```

La implementación es:

```java
@Override
public int calcularTiempoEntrega() {
    return (int) Math.round(
            20 + (1.5 * distanciaKm)
    );
}
```

---

# PedidoExpress

La clase `PedidoExpress` representa compras realizadas principalmente en supermercados o farmacias.

El tiempo base de entrega es de:

```text
10 minutos
```

Si la distancia de entrega es superior a **5 kilómetros**, se agregan **5 minutos adicionales**.

Por ejemplo, para una distancia de 7 kilómetros:

```text
10 + 5 = 15 minutos
```

La implementación es:

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

# Uso de abstracción

La clase `Pedido` está declarada como abstracta, por lo que no puede ser instanciada directamente.

Por ejemplo, no es posible realizar:

```java
Pedido pedido = new Pedido(...);
```

En cambio, se deben crear objetos pertenecientes a las clases concretas:

```java
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
```

De esta forma, `Pedido` establece una estructura común y las clases derivadas implementan los comportamientos específicos.

---

# Método mostrarResumen()

El método `mostrarResumen()` se encuentra implementado directamente en la clase abstracta `Pedido`.

```java
public void mostrarResumen() {
    System.out.println("ID Pedido: " + idPedido);
    System.out.println("Dirección de entrega: " + direccionEntrega);
    System.out.println("Distancia: " + distanciaKm + " km");
}
```

Debido a la herencia, todas las clases derivadas pueden utilizar este método sin necesidad de volver a implementarlo.

---

# Método calcularTiempoEntrega()

El método:

```java
public abstract int calcularTiempoEntrega();
```

es declarado en la clase abstracta `Pedido`.

Cada clase derivada debe proporcionar obligatoriamente su propia implementación mediante `@Override`.

Por ejemplo:

```java
@Override
public int calcularTiempoEntrega() {
    // Cálculo específico según el tipo de pedido
}
```

Esto permite utilizar una misma operación para todos los pedidos, pero con comportamientos diferentes según la clase que la implemente.

---

# Clase Main

La clase `Main` contiene el punto de entrada de la aplicación.

En ella se crea al menos un objeto correspondiente a cada tipo de pedido:

```java
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
```

Para cada objeto se ejecutan los métodos:

```java
mostrarResumen();
calcularTiempoEntrega();
```

Finalmente, los tiempos obtenidos son mostrados de forma comparativa en la consola.

---

# Ejemplo de ejecución

Al ejecutar la aplicación se obtiene una salida similar a:

```text
===== PEDIDO DE COMIDA =====
ID Pedido: 1
Dirección de entrega: Av. Providencia 1500
Distancia: 4.0 km
Tiempo estimado: 23 minutos

===== PEDIDO DE ENCOMIENDA =====
ID Pedido: 2
Dirección de entrega: Av. Apoquindo 3200
Distancia: 6.0 km
Tiempo estimado: 29 minutos

===== PEDIDO EXPRESS =====
ID Pedido: 3
Dirección de entrega: Gran Avenida 4500
Distancia: 7.0 km
Tiempo estimado: 15 minutos

===== COMPARACIÓN DE TIEMPOS =====
Comida: 23 minutos
Encomienda: 29 minutos
Express: 15 minutos
```

---

# Estructura del proyecto

El código fuente está organizado dentro del paquete:

```text
cl.duoc.speedfast
```

La estructura del proyecto visualizada en IntelliJ IDEA es:

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

Las clases `PedidoComida`, `PedidoEncomienda` y `PedidoExpress` heredan de la clase abstracta `Pedido`.

La clase `Main` contiene el método principal utilizado para ejecutar y comprobar el funcionamiento del sistema.

---

# Tecnologías utilizadas

Para el desarrollo del proyecto se utilizaron las siguientes tecnologías y herramientas:

* **Java**
* **IntelliJ IDEA**
* **Git**
* **GitHub**

---

# Requisitos

Para compilar y ejecutar el proyecto se requiere:

* Java JDK instalado.
* IntelliJ IDEA o cualquier IDE compatible con Java.
* Git, en caso de querer clonar el repositorio desde GitHub.

---

# Clonar el proyecto

Para obtener una copia local del proyecto se debe ejecutar:

```bash
git clone https://github.com/USUARIO/SpeedFastPOO2.git
```

Luego ingresar al directorio:

```bash
cd SpeedFastPOO2
```

> Reemplazar `USUARIO` por el nombre de usuario correspondiente en GitHub.

---

# Abrir el proyecto en IntelliJ IDEA

1. Abrir **IntelliJ IDEA**.
2. Seleccionar la opción **Open**.
3. Buscar la carpeta `SpeedFastPOO2`.
4. Seleccionar la carpeta del proyecto.
5. Presionar **Open**.
6. Esperar que IntelliJ IDEA cargue el proyecto.
7. Verificar que exista un JDK configurado.

---

# Ejecutar el proyecto desde IntelliJ IDEA

Dentro de IntelliJ IDEA:

1. Abrir el paquete `cl.duoc.speedfast`.
2. Abrir la clase `Main.java`.
3. Ejecutar el método `main()`.
4. Revisar los resultados mostrados en la consola.

---

# Compilar y ejecutar desde consola

El proyecto también puede ser compilado utilizando la terminal.

Desde la raíz de `SpeedFastPOO2` ejecutar:

```bash
javac -d out src/cl/duoc/speedfast/*.java
```

Luego ejecutar la aplicación:

```bash
java -cp out cl.duoc.speedfast.Main
```

Los resultados serán mostrados directamente en la consola.

---

# Conceptos aplicados

## Semana 1

Durante la primera semana se aplicaron:

* Creación de clases y objetos.
* Constructores.
* Herencia.
* Polimorfismo.
* Sobrecarga de métodos.
* Sobreescritura de métodos.
* Uso de `@Override`.

## Semana 2

Durante la segunda semana se incorporaron:

* Clases abstractas.
* Métodos abstractos.
* Herencia desde una clase abstracta.
* Reutilización de atributos y métodos.
* Especialización del comportamiento.
* Implementación de métodos abstractos mediante `@Override`.
* Polimorfismo.
* Cálculo de tiempos de entrega.
* Uso de `Math.round()` para ajustar resultados a valores enteros.

---

# Documentación JavaDoc

Las clases del proyecto se encuentran documentadas utilizando **JavaDoc**.

La documentación permite describir:

* Propósito de cada clase.
* Responsabilidad de cada método.
* Parámetros recibidos por los constructores y métodos.
* Valores retornados.
* Comportamiento específico de las clases derivadas.

Ejemplo:

```java
/**
 * Calcula el tiempo estimado de entrega.
 *
 * @return tiempo estimado de entrega expresado en minutos
 */
public abstract int calcularTiempoEntrega();
```

---

# Evolución del proyecto

El proyecto SpeedFast se desarrolla de manera incremental.

La **Semana 1** estableció la primera jerarquía de clases y permitió aplicar sobrecarga y sobreescritura.

La **Semana 2** modifica y mejora esta estructura mediante una clase abstracta `Pedido`, centralizando los atributos y comportamientos comunes y obligando a cada tipo de pedido a implementar su propio cálculo de tiempo de entrega.

El repositorio mantiene el historial de estos cambios mediante commits de Git.

---

# Autor

**Pablo Márquez**

Proyecto académico desarrollado para la asignatura:

**Programación Orientada a Objetos II**

**Duoc UC**
