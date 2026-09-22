# SpeedFastPOO2

## Programación Orientada a Objetos II

### Semana 6 - Diseñando interfaces gráficas para aplicaciones en Java

Proyecto desarrollado como parte de la asignatura **Programación Orientada a Objetos II** de Duoc UC.

Durante esta sexta semana se continúa trabajando con el caso de la empresa **SpeedFast**, incorporando una interfaz gráfica de escritorio mediante **Java Swing**.

El objetivo de esta actividad es permitir que el usuario pueda interactuar directamente con los datos del sistema mediante ventanas, formularios, botones y tablas.

La información de los pedidos se mantiene temporalmente en memoria utilizando colecciones de Java, sin necesidad de utilizar una base de datos.

---

# Descripción del proyecto

SpeedFast es una empresa dedicada al reparto de diferentes tipos de pedidos:

- Pedidos de comida.
- Encomiendas.
- Pedidos express.

Durante las semanas anteriores se desarrolló una estructura orientada a objetos aplicando conceptos como:

- Clases.
- Objetos.
- Encapsulamiento.
- Herencia.
- Abstracción.
- Polimorfismo.
- Sobrecarga de métodos.
- Sobrescritura de métodos.
- Interfaces.
- Colecciones.
- Programación concurrente.

En la **Semana 6** se reutilizan estos conceptos para incorporar una interfaz gráfica utilizando **Java Swing**.

La aplicación permite registrar pedidos y posteriormente visualizarlos mediante una tabla.

---

# Objetivo

El objetivo principal de esta actividad es implementar una interfaz gráfica de escritorio para la gestión básica de pedidos de SpeedFast.

El sistema permite:

- Registrar nuevos pedidos.
- Ingresar la dirección de entrega.
- Ingresar la distancia de entrega.
- Seleccionar el tipo de pedido.
- Validar los datos ingresados.
- Evitar pedidos con ID duplicado.
- Visualizar los pedidos existentes mediante una tabla.
- Refrescar manualmente el listado de pedidos.
- Actualizar automáticamente el listado cada 20 segundos.
- Compartir los pedidos entre las diferentes ventanas.
- Mantener los datos temporalmente en memoria.
- Navegar entre las diferentes ventanas de la aplicación.

La ventana principal también incorpora la opción:

```text
Asignar repartidor / Iniciar entrega
```

de acuerdo con el diseño solicitado para la ventana principal de SpeedFast.

---

# Interfaz gráfica

La interfaz gráfica fue desarrollada utilizando la biblioteca:

```java
javax.swing
```

Entre los principales componentes utilizados se encuentran:

```text
JFrame
JPanel
JLabel
JTextField
JButton
JComboBox
JTable
JScrollPane
JOptionPane
DefaultTableModel
Timer
```

Para organizar los componentes dentro de las ventanas se utilizan administradores de diseño como:

```text
BorderLayout
GridLayout
```

---

# Estructura orientada a objetos

El sistema mantiene una jerarquía de pedidos basada en una clase abstracta.

```text
                    Pedido
                (Clase abstracta)
                       |
          ---------------------------
          |            |            |
          v            v            v
 PedidoComida   PedidoEncomienda  PedidoExpress
```

La clase `Pedido` contiene los atributos y comportamientos comunes de todos los pedidos.

Entre sus principales atributos se encuentran:

```java
private int idPedido;
private String direccionEntrega;
private double distanciaKm;
```

Además, define comportamientos que posteriormente son implementados por las clases derivadas.

---

# Clase Pedido

`Pedido` representa la clase base del modelo.

Se declara como una clase abstracta:

```java
public abstract class Pedido
```

Cada pedido contiene:

```text
ID
Dirección de entrega
Distancia en kilómetros
```

También define métodos abstractos como:

```java
calcularTiempoEntrega()
getTipo()
```

Esto permite que cada tipo de pedido implemente su propio comportamiento.

---

# Tipos de pedidos

## PedidoComida

La clase `PedidoComida` representa pedidos relacionados con reparto de comida.

Hereda de:

```java
Pedido
```

e implementa su propio cálculo del tiempo estimado de entrega.

---

## PedidoEncomienda

La clase `PedidoEncomienda` representa pedidos correspondientes al transporte de encomiendas.

También hereda de:

```java
Pedido
```

y proporciona su propia implementación para calcular el tiempo estimado.

---

## PedidoExpress

La clase `PedidoExpress` representa pedidos que requieren una entrega rápida.

Hereda de:

```java
Pedido
```

y aplica una regla propia para determinar el tiempo estimado de entrega.

---

# Polimorfismo

El sistema utiliza polimorfismo al registrar los diferentes tipos de pedidos.

Todos los objetos pueden ser almacenados utilizando una referencia del tipo:

```java
Pedido
```

Por ejemplo:

```java
Pedido pedido;

switch (tipo) {

    case "Comida":
        pedido = new PedidoComida(
                id,
                direccion,
                distancia
        );
        break;

    case "Encomienda":
        pedido = new PedidoEncomienda(
                id,
                direccion,
                distancia
        );
        break;

    case "Express":
        pedido = new PedidoExpress(
                id,
                direccion,
                distancia
        );
        break;

    default:
        throw new IllegalArgumentException(
                "Tipo de pedido no válido."
        );
}
```

De esta forma, la aplicación puede trabajar con diferentes tipos de pedidos mediante una referencia común.

---

# ControladorPedidos

La clase:

```text
ControladorPedidos
```

es responsable de administrar los pedidos registrados durante la ejecución de la aplicación.

Los pedidos son almacenados mediante:

```java
List<Pedido>
```

utilizando internamente:

```java
ArrayList
```

Entre sus principales operaciones se encuentran:

```java
agregarPedido()
obtenerPedidos()
existePedido()
```

La misma instancia de `ControladorPedidos` es compartida por las diferentes ventanas de la aplicación.

Esto permite que un pedido registrado desde una ventana pueda ser visualizado posteriormente desde otra.

---

# Almacenamiento en memoria

Durante esta etapa del proyecto no se utiliza una base de datos.

Los pedidos son almacenados temporalmente mediante:

```java
List<Pedido>
```

Los datos permanecen disponibles mientras la aplicación se encuentra ejecutándose.

Al cerrar completamente el programa, los pedidos almacenados se pierden.

Este comportamiento corresponde al almacenamiento básico en memoria solicitado para esta actividad.

---

# VentanaPrincipal

La clase:

```text
VentanaPrincipal
```

hereda de:

```java
JFrame
```

y corresponde a la ventana inicial de la aplicación.

Desde ella se puede acceder a las principales opciones del sistema:

```text
Registrar pedido
Listar pedidos
Asignar repartidor / Iniciar entrega
```

Los botones permiten navegar hacia las diferentes funcionalidades disponibles.

---

# VentanaRegistroPedido

La clase:

```text
VentanaRegistroPedido
```

también hereda de:

```java
JFrame
```

y contiene el formulario utilizado para registrar pedidos.

El formulario solicita:

```text
ID Pedido
Dirección
Distancia (km)
Tipo
```

El tipo de pedido se selecciona mediante un:

```java
JComboBox
```

que contiene las opciones:

```text
Comida
Encomienda
Express
```

Dependiendo de la opción seleccionada se crea una instancia de:

```java
PedidoComida
PedidoEncomienda
PedidoExpress
```

Una vez creado, el pedido es enviado al `ControladorPedidos`.

---

# Validación de datos

Antes de almacenar un pedido se realizan diferentes validaciones.

El sistema verifica que:

- Todos los campos estén completos.
- El ID sea un valor numérico.
- El ID sea mayor que cero.
- La distancia sea un valor numérico.
- La distancia sea mayor que cero.
- No exista previamente otro pedido con el mismo ID.

Los mensajes de confirmación, advertencia y error son mostrados mediante:

```java
JOptionPane
```

Por ejemplo, cuando un pedido se registra correctamente se muestra un mensaje de confirmación.

Posteriormente, los campos del formulario son limpiados para permitir el ingreso de un nuevo pedido.

---

# VentanaListaPedidos

La clase:

```text
VentanaListaPedidos
```

permite visualizar los pedidos almacenados en el controlador.

Para mostrar la información se utiliza:

```java
JTable
```

junto con:

```java
DefaultTableModel
```

La tabla muestra la siguiente información:

| Campo | Descripción |
|---|---|
| ID | Identificador único del pedido |
| Dirección | Dirección donde debe realizarse la entrega |
| Tipo | Comida, Encomienda o Express |
| Distancia | Distancia de entrega expresada en kilómetros |
| Tiempo estimado | Tiempo calculado para realizar la entrega |

Las celdas de la tabla están configuradas para impedir su edición directa.

---

# Actualización manual del listado

La ventana de pedidos dispone del botón:

```text
Refrescar
```

Al presionarlo se vuelve a consultar la lista almacenada en `ControladorPedidos` y se actualiza el contenido del `JTable`.

Esto permite visualizar inmediatamente los pedidos que hayan sido registrados desde otra ventana.

---

# Actualización automática cada 20 segundos

Además de la actualización manual, el listado implementa una actualización automática.

Para ello se utiliza:

```java
javax.swing.Timer
```

El temporizador ejecuta nuevamente la carga de los pedidos cada:

```text
20 segundos
```

Su implementación es:

```java
timerActualizacion = new Timer(
        20000,
        e -> cargarPedidos()
);

timerActualizacion.start();
```

Esto permite mantener abierta la ventana de listado mientras se registran nuevos pedidos.

Cuando transcurren 20 segundos, la tabla vuelve a cargar automáticamente la información existente en el controlador.

El usuario puede seguir utilizando el botón **Refrescar** cuando necesite una actualización inmediata.

---

# Navegación entre ventanas

La navegación entre las ventanas se realiza desde `VentanaPrincipal`.

Conceptualmente, la aplicación funciona de la siguiente manera:

```text
                    Main
                      |
                      v
              VentanaPrincipal
                      |
            ---------------------
            |                   |
            v                   v
 VentanaRegistroPedido   VentanaListaPedidos
            |                   |
            |                   |
            -----------  --------
                      |  |
                      v  v
               ControladorPedidos
                      |
                      v
                 List<Pedido>
```

Todas las ventanas utilizan la misma instancia de `ControladorPedidos`.

Esto permite compartir los pedidos durante toda la ejecución del programa.

---

# Estructura del proyecto - Semana 6

La estructura utilizada para esta actividad es:

```text
SpeedFastPOO2
│
└── Semana 6
    │
    └── src
        │
        ├── controlador
        │   └── ControladorPedidos.java
        │
        ├── modelo
        │   ├── Pedido.java
        │   ├── PedidoComida.java
        │   ├── PedidoEncomienda.java
        │   ├── PedidoExpress.java
        │   └── Repartidor.java
        │
        ├── vista
        │   ├── VentanaPrincipal.java
        │   ├── VentanaRegistroPedido.java
        │   └── VentanaListaPedidos.java
        │
        └── Main.java
```

---

# Clases principales

| Clase | Responsabilidad |
|---|---|
| `Pedido` | Clase abstracta base para los pedidos |
| `PedidoComida` | Representa pedidos de comida |
| `PedidoEncomienda` | Representa pedidos de encomienda |
| `PedidoExpress` | Representa pedidos express |
| `Repartidor` | Representa un repartidor de SpeedFast |
| `ControladorPedidos` | Mantiene y administra la lista de pedidos |
| `VentanaPrincipal` | Presenta las opciones principales de la aplicación |
| `VentanaRegistroPedido` | Permite registrar nuevos pedidos |
| `VentanaListaPedidos` | Muestra los pedidos mediante un JTable |
| `Main` | Inicia la aplicación |

---

# Clase Main

La aplicación se inicia desde:

```text
Main.java
```

En esta clase se crea una única instancia de:

```java
ControladorPedidos
```

que posteriormente es entregada a `VentanaPrincipal`.

La interfaz gráfica se inicia mediante:

```java
SwingUtilities.invokeLater(() -> {

    ControladorPedidos controlador =
            new ControladorPedidos();

    VentanaPrincipal ventana =
            new VentanaPrincipal(controlador);

    ventana.setVisible(true);
});
```

El uso de:

```java
SwingUtilities.invokeLater()
```

permite iniciar la interfaz gráfica dentro del hilo de eventos de Swing.

---

# Tecnologías utilizadas

Para el desarrollo de esta actividad se utilizaron:

- Java.
- Java Swing.
- IntelliJ IDEA.
- Git.
- GitHub.
- Programación Orientada a Objetos.
- Colecciones `List` y `ArrayList`.
- `JFrame`.
- `JTable`.
- `DefaultTableModel`.
- `JComboBox`.
- `JOptionPane`.
- `javax.swing.Timer`.

---

# Conceptos aplicados

Durante el desarrollo de la Semana 6 se aplican los siguientes conceptos:

- Clases.
- Objetos.
- Encapsulamiento.
- Herencia.
- Abstracción.
- Polimorfismo.
- Sobrescritura de métodos.
- Colecciones.
- Separación entre modelo, vista y controlador.
- Interfaces gráficas.
- Manejo de eventos.
- Formularios.
- Tablas.
- Validación de datos.
- Actualización periódica de la interfaz.

---

# Requisitos

Para ejecutar el proyecto se necesita:

- Java JDK instalado.
- IntelliJ IDEA o cualquier IDE compatible con Java.
- Git, en caso de clonar el proyecto desde GitHub.

No se necesita:

- Base de datos.
- Servidor externo.
- Librerías gráficas adicionales.

La interfaz utiliza únicamente componentes incluidos en Java Swing.

---

# Clonar el proyecto desde GitHub

El proyecto puede ser descargado utilizando Git.

Desde una terminal, PowerShell o Git Bash ejecutar:

```bash
git clone https://github.com/pamarquezb-debug/SpeedFastPOO2.git
```

Luego ingresar al directorio:

```bash
cd SpeedFastPOO2
```

La actividad correspondiente a esta entrega se encuentra dentro de:

```text
Semana 6
```

También es posible descargar el repositorio directamente desde GitHub utilizando:

```text
Code
   ↓
Download ZIP
```

---

# Repositorio GitHub

El repositorio del proyecto es:

```text
https://github.com/pamarquezb-debug/SpeedFastPOO2
```

La implementación correspondiente a esta actividad se encuentra en:

```text
SpeedFastPOO2/Semana 6
```

---

# Abrir el proyecto en IntelliJ IDEA

Para abrir el proyecto:

1. Iniciar **IntelliJ IDEA**.
2. Seleccionar **Open**.
3. Buscar la carpeta donde fue clonado o descargado el repositorio.
4. Abrir `SpeedFastPOO2`.
5. Localizar la carpeta:

```text
Semana 6
```

6. Dentro de ella localizar:

```text
src
```

La estructura principal será:

```text
Semana 6
└── src
    ├── controlador
    ├── modelo
    ├── vista
    └── Main.java
```

---

# Ejecución en IntelliJ IDEA

Para ejecutar la aplicación:

1. Abrir el proyecto `SpeedFastPOO2`.
2. Localizar:

```text
Semana 6/src/Main.java
```

3. Abrir `Main.java`.
4. Verificar que IntelliJ IDEA tenga configurado correctamente un **Java JDK**.
5. Localizar:

```java
public static void main(String[] args)
```

6. Presionar el botón verde **Run** que aparece junto al método `main`.

También se puede hacer clic derecho sobre:

```text
Main.java
```

y seleccionar:

```text
Run 'Main.main()'
```

Al ejecutar el programa aparecerá la ventana principal de:

```text
SPEEDFAST - GESTIÓN DE ENTREGAS
```

---

# Prueba de funcionamiento

Para comprobar el funcionamiento de la aplicación se puede realizar la siguiente prueba.

## 1. Ejecutar la aplicación

Ejecutar:

```text
Main.java
```

Aparecerá la ventana principal.

---

## 2. Registrar un pedido

Seleccionar:

```text
Registrar pedido
```

Ingresar, por ejemplo:

```text
ID Pedido:       101
Dirección:       Av. Providencia 1234
Distancia:       8
Tipo:            Comida
```

Presionar:

```text
Guardar
```

El sistema mostrará un mensaje indicando que el pedido fue registrado correctamente.

---

## 3. Visualizar los pedidos

Desde la ventana principal seleccionar:

```text
Listar pedidos
```

La tabla mostrará el pedido registrado.

Por ejemplo:

```text
ID    Dirección               Tipo       Distancia    Tiempo estimado

101   Av. Providencia 1234    Comida     8.0 km       XX min
```

El tiempo estimado dependerá de la implementación del tipo de pedido correspondiente.

---

## 4. Registrar otro pedido

Es posible mantener abierta la ventana de listado y regresar al formulario de registro.

Por ejemplo:

```text
ID Pedido:       102
Dirección:       Av. Apoquindo 6000
Distancia:       4
Tipo:            Express
```

Después de guardar el pedido existen dos formas de actualizar el listado.

### Actualización manual

Presionar:

```text
Refrescar
```

### Actualización automática

Esperar un máximo aproximado de:

```text
20 segundos
```

El `javax.swing.Timer` volverá a cargar automáticamente los pedidos existentes.

---

# Resultado

La implementación de la Semana 6 permite que el sistema SpeedFast disponga de una interfaz gráfica de escritorio para administrar pedidos.

La aplicación permite registrar diferentes tipos de pedidos utilizando un formulario gráfico y posteriormente visualizar la información mediante una tabla.

Los pedidos son administrados mediante `ControladorPedidos` y almacenados temporalmente en una colección `List<Pedido>`.

La utilización de una única instancia del controlador permite compartir los datos entre las diferentes ventanas.

La ventana de listado incorpora tanto actualización manual como actualización automática cada 20 segundos mediante `javax.swing.Timer`.

De esta forma se integran los conceptos de Programación Orientada a Objetos desarrollados anteriormente con la creación de interfaces gráficas utilizando Java Swing.

---

# Conclusión

Durante la Semana 6 se incorporó una interfaz gráfica al sistema SpeedFast utilizando Java Swing.

La separación entre las clases del modelo, el controlador y las ventanas permite mantener una estructura organizada y facilita la interacción entre los diferentes componentes del sistema.

La utilización de `JFrame`, `JTextField`, `JComboBox`, `JButton`, `JTable`, `DefaultTableModel` y `JOptionPane` permite implementar las principales operaciones solicitadas para la gestión visual de pedidos.

Además, el uso de `javax.swing.Timer` permite actualizar periódicamente la tabla de pedidos sin requerir la intervención del usuario.

La actividad demuestra cómo una estructura orientada a objetos puede integrarse con una interfaz gráfica para construir una aplicación de escritorio funcional y organizada.

---

# Autor

**Pablo Márquez**

Programación Orientada a Objetos II  
Duoc UC  
Semana 6 - 2026