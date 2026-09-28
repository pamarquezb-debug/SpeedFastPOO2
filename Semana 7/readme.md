# SpeedFast - Semana 7

## Conectando aplicaciones Java con bases de datos mediante JDBC

Proyecto desarrollado para la asignatura **Programación Orientada a Objetos II**.

Durante esta séptima semana se incorporó persistencia de datos al sistema **SpeedFast**, desarrollado durante las semanas anteriores, conectando la aplicación Java con una base de datos **MySQL mediante JDBC**.

---

# Descripción del proyecto

SpeedFast es una aplicación Java orientada a la gestión de pedidos y entregas.

El proyecto utiliza Programación Orientada a Objetos, interfaces gráficas desarrolladas con **Java Swing**, acceso a datos mediante clases **DAO** y conexión a una base de datos relacional MySQL utilizando **JDBC**.

La incorporación de persistencia permite que los pedidos, repartidores y entregas permanezcan almacenados en la base de datos incluso después de cerrar la aplicación.

---

# Funcionalidades principales

La aplicación permite:

- Registrar nuevos pedidos.
- Seleccionar el tipo de pedido:
  - Comida.
  - Encomienda.
  - Express.
- Registrar la dirección de entrega.
- Registrar la distancia de entrega.
- Almacenar los pedidos en MySQL mediante JDBC.
- Consultar los pedidos almacenados.
- Mostrar los pedidos mediante un `JTable`.
- Consultar los repartidores registrados.
- Asignar un repartidor a un pedido.
- Registrar una entrega con fecha y hora.
- Visualizar el estado actual del pedido.
- Actualizar manualmente el listado de pedidos.
- Actualizar automáticamente el listado cada 20 segundos.

---

# Base de datos

La aplicación utiliza la base de datos MySQL:

```text
speedfast_db
```

La estructura está compuesta por tres tablas relacionadas:

```text
speedfast_db
│
├── repartidor
│   ├── id
│   └── nombre
│
├── pedido
│   ├── id
│   ├── direccion
│   ├── distancia
│   ├── tipo
│   └── estado
│
└── entrega
    ├── id
    ├── id_pedido
    ├── id_repartidor
    ├── fecha
    └── hora
```

---

## Script de creación de la base de datos

Para crear la base de datos y las tablas necesarias para ejecutar SpeedFast se utiliza el siguiente script:

```sql
CREATE DATABASE IF NOT EXISTS speedfast_db;

USE speedfast_db;

CREATE TABLE repartidor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    direccion VARCHAR(150) NOT NULL,
    distancia DECIMAL(10,2) NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    estado VARCHAR(20) NOT NULL
);

CREATE TABLE entrega (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL UNIQUE,
    id_repartidor INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,

    CONSTRAINT fk_entrega_pedido
        FOREIGN KEY (id_pedido)
        REFERENCES pedido(id),

    CONSTRAINT fk_entrega_repartidor
        FOREIGN KEY (id_repartidor)
        REFERENCES repartidor(id)
);

INSERT INTO repartidor (nombre) VALUES
    ('Camila Soto'),
    ('Daniela Tapia'),
    ('Luis Díaz');

SHOW TABLES;

SELECT * FROM repartidor;
```

---

## Relaciones de la base de datos

La tabla `entrega` relaciona los pedidos con los repartidores.

La relación entre pedido y entrega se establece mediante:

```text
pedido.id
    ↑
    │
entrega.id_pedido
```

La relación entre repartidor y entrega se establece mediante:

```text
repartidor.id
    ↑
    │
entrega.id_repartidor
```

De esta manera, cada entrega permite determinar qué pedido fue asignado a qué repartidor y en qué fecha y hora se realizó la asignación.

---

# Conexión JDBC

La conexión entre Java y MySQL es administrada mediante:

```text
dao/ConexionBD.java
```

Para establecer la conexión se utiliza:

```java
DriverManager.getConnection()
```

La URL utilizada durante el desarrollo es:

```text
jdbc:mysql://localhost:3306/speedfast_db
```

La aplicación utiliza **MySQL Connector/J** como controlador JDBC.

Ejemplo de configuración:

```java
private static final String URL =
        "jdbc:mysql://localhost:3306/speedfast_db"
                + "?useSSL=false"
                + "&serverTimezone=America/Santiago";

private static final String USER = "root";
private static final String PASSWORD = "";
```

La contraseña debe modificarse según la configuración de MySQL del equipo donde se ejecute el proyecto.

---

# Acceso a datos mediante DAO

Para separar la lógica de acceso a datos del resto de la aplicación se implementaron las siguientes clases:

```text
dao
├── ConexionBD.java
├── PedidoDAO.java
├── RepartidorDAO.java
└── EntregaDAO.java
```

---

## PedidoDAO

La clase `PedidoDAO` administra las operaciones relacionadas con los pedidos.

Entre sus métodos se encuentran:

```text
guardar(Pedido)
listarTodos()
listarDisponibles()
```

El registro de pedidos se realiza utilizando `PreparedStatement`.

Los pedidos recuperados desde MySQL son reconstruidos según su tipo:

```text
COMIDA       → PedidoComida
ENCOMIENDA   → PedidoEncomienda
EXPRESS      → PedidoExpress
```

---

## RepartidorDAO

La clase `RepartidorDAO` administra las operaciones relacionadas con los repartidores.

Incluye:

```text
guardar(Repartidor)
listarTodos()
```

El método `listarTodos()` utiliza `ResultSet` para recuperar los registros almacenados en MySQL y transformarlos nuevamente en objetos `Repartidor`.

---

## EntregaDAO

La clase `EntregaDAO` permite registrar la asignación de un repartidor a un pedido.

Cada entrega almacena:

```text
Pedido
Repartidor
Fecha
Hora
```

Además, el registro de una entrega actualiza el estado correspondiente del pedido.

---

# Integración con Swing

La interfaz gráfica desarrollada durante la Semana 6 fue integrada con la base de datos MySQL.

El flujo utilizado para registrar un pedido es:

```text
VentanaRegistroPedido
        ↓
ControladorPedidos
        ↓
PedidoDAO
        ↓
ConexionBD
        ↓
JDBC
        ↓
MySQL
```

El flujo para consultar los pedidos es:

```text
MySQL
   ↓
PedidoDAO
   ↓
ControladorPedidos
   ↓
VentanaListaPedidos
   ↓
JTable
```

De esta manera, los registros mostrados en la aplicación corresponden directamente a la información almacenada en la base de datos.

---

# Estados de los pedidos

El campo `estado` definido para los pedidos permite conocer la situación actual de cada registro.

Los pedidos nuevos son almacenados inicialmente como:

```text
PENDIENTE
```

Cuando un repartidor es asignado correctamente, el pedido cambia a:

```text
EN_REPARTO
```

El estado se muestra en la ventana de listado de pedidos junto con el resto de la información.

Ejemplo:

```text
ID | Dirección          | Tipo    | Distancia | Tiempo | Estado
-----------------------------------------------------------------
1  | Providencia 1234   | Comida  | 5.0 km    | 25 min | PENDIENTE
2  | Apoquindo 3200     | Express | 3.5 km    | 15 min | EN_REPARTO
```

---

# Mejoras adicionales implementadas

Además de los requerimientos principales establecidos para la actividad, se incorporaron mejoras para mantener la consistencia del sistema y aumentar su funcionalidad.

---

## 1. Persistencia de la distancia del pedido

El modelo orientado a objetos desarrollado durante las semanas anteriores utiliza el atributo:

```java
private double distanciaKm;
```

Para mantener esta información al incorporar persistencia, se agregó a la tabla `pedido`:

```sql
distancia DECIMAL(10,2) NOT NULL
```

Esto permite almacenar y recuperar la distancia real del pedido desde MySQL.

Gracias a esta modificación, aunque la aplicación sea cerrada y posteriormente ejecutada nuevamente, la distancia permanece almacenada y puede seguir utilizándose para calcular el tiempo estimado de entrega.

---

## 2. Actualización automática del estado del pedido

El campo `estado` forma parte de la estructura solicitada para la tabla `pedido`.

Como mejora adicional se implementó la lógica necesaria para actualizarlo automáticamente durante el proceso de asignación.

El flujo utilizado es:

```text
PENDIENTE
    ↓
Asignación de repartidor
    ↓
Registro de entrega
    ↓
EN_REPARTO
```

De esta manera, la aplicación puede identificar automáticamente los pedidos que todavía se encuentran disponibles para asignación.

---

## 3. Prevención de asignaciones duplicadas

Se implementaron controles para impedir que un mismo pedido pueda ser asignado más de una vez.

En la tabla `entrega` se agregó:

```sql
id_pedido INT NOT NULL UNIQUE
```

La restricción `UNIQUE` evita directamente desde MySQL que existan dos entregas asociadas al mismo pedido.

Además, la aplicación utiliza una consulta que solamente recupera pedidos que cumplen ambas condiciones:

```text
estado = PENDIENTE
        +
sin entrega previamente registrada
```

Por esta razón, después de asignar un repartidor, el pedido deja de aparecer en el `JComboBox` de pedidos disponibles.

La validación se realiza tanto desde Java como desde MySQL.

---

## 4. Transacciones JDBC

El proceso de asignación de un repartidor utiliza una transacción JDBC.

El procedimiento es:

```text
Inicio de transacción
        ↓
Actualizar pedido a EN_REPARTO
        ↓
Registrar entrega
        ↓
COMMIT
```

Si alguna de las operaciones falla se ejecuta:

```text
ROLLBACK
```

Esto evita inconsistencias, por ejemplo, que un pedido quede marcado como `EN_REPARTO` sin que exista una entrega correctamente registrada.

---

## 5. Actualización automática del JTable

La ventana de consulta de pedidos utiliza:

```java
javax.swing.Timer
```

para consultar nuevamente la base de datos cada:

```text
20 segundos
```

De esta manera, los cambios realizados en los pedidos pueden visualizarse automáticamente.

También se mantiene un botón:

```text
Refrescar
```

para realizar la consulta manualmente cuando sea necesario.

---

## 6. Presentación mejorada en JComboBox

Para mejorar la visualización de los objetos dentro de los componentes Swing se sobrescribió el método:

```java
toString()
```

Los pedidos se muestran, por ejemplo, como:

```text
#5 - Av. Providencia 1234 - 1.5 km (Comida)
```

en lugar de utilizar la representación predeterminada de objetos Java.

Los repartidores se muestran directamente mediante su nombre:

```text
Camila Soto
Daniela Tapia
Luis Díaz
```

---

# Prevención de doble asignación

Cuando se abre la ventana de asignación, la aplicación no carga todos los pedidos.

Solamente consulta aquellos que se encuentran disponibles:

```text
Pedido PENDIENTE
        ↓
¿Existe en entrega?
        │
     ┌──┴──┐
     │     │
    NO     SÍ
     │     │
     ↓     ↓
 Mostrar  No mostrar
```

Esto evita que un pedido que ya tiene un repartidor vuelva a aparecer como disponible.

---

# Estructura del proyecto

La organización principal del código es:

```text
src
│
├── controlador
│   └── ControladorPedidos.java
│
├── dao
│   ├── ConexionBD.java
│   ├── PedidoDAO.java
│   ├── RepartidorDAO.java
│   └── EntregaDAO.java
│
├── modelo
│   ├── Entrega.java
│   ├── Pedido.java
│   ├── PedidoComida.java
│   ├── PedidoEncomienda.java
│   ├── PedidoExpress.java
│   └── Repartidor.java
│
├── vista
│   ├── VentanaAsignarEntrega.java
│   ├── VentanaListaPedidos.java
│   ├── VentanaPrincipal.java
│   └── VentanaRegistroPedido.java
│
└── Main.java
```

También se incluye el script SQL utilizado para crear la base de datos:

```text
speedfast_db.sql
```

---

# Tecnologías utilizadas

- Java
- Programación Orientada a Objetos
- Java Swing
- JDBC
- MySQL
- MySQL Connector/J
- IntelliJ IDEA
- Git
- GitHub

---

# Requisitos para ejecutar el proyecto

Para ejecutar SpeedFast se requiere:

- JDK instalado.
- MySQL Server.
- MySQL Connector/J.
- IntelliJ IDEA o un IDE compatible con Java.
- Base de datos `speedfast_db`.

---

# Configuración de la base de datos

Antes de ejecutar la aplicación se debe crear la base de datos:

```text
speedfast_db
```

utilizando el script SQL incluido en el proyecto:

```text
speedfast_db.sql
```

El script crea las tablas:

```text
repartidor
pedido
entrega
```

y registra inicialmente los repartidores:

```text
Camila Soto
Daniela Tapia
Luis Díaz
```

Luego se deben verificar los parámetros de conexión definidos en:

```text
src/dao/ConexionBD.java
```

Especialmente:

```text
URL
USER
PASSWORD
```

Estos valores deben corresponder a la configuración local del servidor MySQL.

---

# Ejecución del proyecto

## 1. Clonar el repositorio

Desde una terminal ejecutar:

```bash
git clone [URL_DEL_REPOSITORIO](https://github.com/pamarquezb-debug/SpeedFastPOO2/edit/master/Semana%207)
```

Luego ingresar al directorio del proyecto.

---

## 2. Crear la base de datos

Ejecutar:

```text
speedfast_db.sql
```

desde MySQL, MySQL Workbench, phpMyAdmin u otra herramienta compatible.

---

## 3. Configurar JDBC

Verificar que **MySQL Connector/J** se encuentre agregado a las dependencias del proyecto.

También se debe revisar:

```text
src/dao/ConexionBD.java
```

y configurar las credenciales correspondientes al servidor MySQL.

---

## 4. Ejecutar SpeedFast

Abrir el proyecto en IntelliJ IDEA y ejecutar:

```text
Main.java
```

Se abrirá la ventana principal:

```text
SPEEDFAST - GESTIÓN DE ENTREGAS
```

---

# Flujo general de funcionamiento

El flujo principal de la aplicación es:

```text
Registrar pedido
        ↓
Guardar mediante PedidoDAO
        ↓
MySQL
        ↓
Estado PENDIENTE
        ↓
Consultar pedidos
        ↓
Mostrar en JTable
        ↓
Asignar repartidor
        ↓
Registrar entrega
        ↓
Estado EN_REPARTO
        ↓
Pedido deja de estar disponible
para una nueva asignación
```

---

# Manejo de recursos JDBC

Las operaciones de acceso a datos utilizan manejo de excepciones mediante:

```java
try
catch
```

y, cuando corresponde, `try-with-resources` para cerrar correctamente:

```text
Connection
PreparedStatement
ResultSet
```

Esto permite liberar los recursos utilizados durante las operaciones con la base de datos.

---

# Conclusión

La implementación de JDBC permite que SpeedFast evolucione desde una aplicación que mantenía la información temporalmente en memoria hacia una aplicación con persistencia real utilizando MySQL.

La separación entre interfaz gráfica, controlador, modelo y clases DAO permite mantener organizada la aplicación y separar las responsabilidades de cada componente.

Adicionalmente, se incorporaron controles para evitar asignaciones duplicadas, persistencia de la distancia, actualización automática del estado, transacciones JDBC y actualización periódica de la interfaz.

---

# Autor

**Pablo Marquez**

Proyecto desarrollado para:

**Programación Orientada a Objetos II**

**Duoc UC - 2026**
