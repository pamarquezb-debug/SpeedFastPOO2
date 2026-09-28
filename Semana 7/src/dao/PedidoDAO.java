package dao;

import modelo.Pedido;
import modelo.PedidoComida;
import modelo.PedidoEncomienda;
import modelo.PedidoExpress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona las operaciones de base de datos
 * relacionadas con los pedidos de SpeedFast.
 *
 * Permite registrar pedidos, consultar todos los
 * pedidos y obtener solamente aquellos que se
 * encuentran disponibles para ser asignados
 * a un repartidor.
 *
 * @author Pablo Marquez
 * @version 7.0
 */
public class PedidoDAO {

    /**
     * Guarda un nuevo pedido en la base de datos.
     *
     * Todo pedido nuevo se registra inicialmente
     * con estado PENDIENTE.
     *
     * @param pedido pedido que será almacenado
     * @return true si fue guardado correctamente
     */
    public boolean guardar(Pedido pedido) {

        String sql =
                "INSERT INTO pedido "
                        + "(direccion, distancia, tipo, estado) "
                        + "VALUES (?, ?, ?, ?)";

        try (Connection conexion =
                     ConexionBD.conectar();

             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setString(
                    1,
                    pedido.getDireccionEntrega()
            );

            ps.setDouble(
                    2,
                    pedido.getDistanciaKm()
            );

            ps.setString(
                    3,
                    pedido.getTipo()
            );

            ps.setString(
                    4,
                    "PENDIENTE"
            );

            int filasAfectadas =
                    ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al guardar pedido: "
                            + e.getMessage()
            );

            return false;
        }
    }

    /**
     * Obtiene todos los pedidos registrados
     * en la base de datos.
     *
     * @return lista de pedidos
     */
    public List<Pedido> listarTodos() {

        List<Pedido> pedidos =
                new ArrayList<>();

        String sql =
                "SELECT id, direccion, distancia, tipo, estado "
                        + "FROM pedido "
                        + "ORDER BY id";

        try (Connection conexion =
                     ConexionBD.conectar();

             PreparedStatement ps =
                     conexion.prepareStatement(sql);

             ResultSet rs =
                     ps.executeQuery()) {

            while (rs.next()) {

                int id =
                        rs.getInt("id");

                String direccion =
                        rs.getString("direccion");

                double distancia =
                        rs.getDouble("distancia");

                String tipo =
                        rs.getString("tipo");

                String estado =
                        rs.getString("estado");

                Pedido pedido =
                        crearPedido(
                                id,
                                direccion,
                                distancia,
                                tipo
                        );

                if (pedido != null) {

                    /*
                     * Se asigna al objeto el estado
                     * almacenado en MySQL.
                     */
                    pedido.setEstado(estado);

                    pedidos.add(pedido);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar pedidos: "
                            + e.getMessage()
            );
        }

        return pedidos;
    }

    /**
     * Obtiene solamente los pedidos que todavía
     * pueden ser asignados a un repartidor.
     *
     * Para estar disponible:
     *
     * 1. El pedido debe encontrarse PENDIENTE.
     * 2. No debe existir una entrega asociada
     *    previamente a ese pedido.
     *
     * @return lista de pedidos disponibles
     */
    public List<Pedido> listarDisponibles() {

        List<Pedido> pedidos =
                new ArrayList<>();

        String sql =
                "SELECT p.id, p.direccion, p.distancia, "
                        + "p.tipo, p.estado "
                        + "FROM pedido p "
                        + "WHERE p.estado = 'PENDIENTE' "
                        + "AND NOT EXISTS ("
                        + "SELECT 1 "
                        + "FROM entrega e "
                        + "WHERE e.id_pedido = p.id"
                        + ") "
                        + "ORDER BY p.id";

        try (Connection conexion =
                     ConexionBD.conectar();

             PreparedStatement ps =
                     conexion.prepareStatement(sql);

             ResultSet rs =
                     ps.executeQuery()) {

            while (rs.next()) {

                int id =
                        rs.getInt("id");

                String direccion =
                        rs.getString("direccion");

                double distancia =
                        rs.getDouble("distancia");

                String tipo =
                        rs.getString("tipo");

                String estado =
                        rs.getString("estado");

                Pedido pedido =
                        crearPedido(
                                id,
                                direccion,
                                distancia,
                                tipo
                        );

                if (pedido != null) {

                    /*
                     * Aunque los pedidos de esta consulta
                     * normalmente serán PENDIENTE,
                     * mantenemos el estado real obtenido
                     * desde la base de datos.
                     */
                    pedido.setEstado(estado);

                    pedidos.add(pedido);
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar pedidos disponibles: "
                            + e.getMessage()
            );
        }

        return pedidos;
    }

    /**
     * Reconstruye el objeto correspondiente
     * según el tipo almacenado en MySQL.
     *
     * @param id identificador del pedido
     * @param direccion dirección de entrega
     * @param distancia distancia en kilómetros
     * @param tipo tipo de pedido
     * @return objeto Pedido correspondiente
     */
    private Pedido crearPedido(
            int id,
            String direccion,
            double distancia,
            String tipo) {

        if (tipo == null) {
            return null;
        }

        switch (tipo.toUpperCase()) {

            case "COMIDA":

                return new PedidoComida(
                        id,
                        direccion,
                        distancia
                );

            case "ENCOMIENDA":

                return new PedidoEncomienda(
                        id,
                        direccion,
                        distancia
                );

            case "EXPRESS":

                return new PedidoExpress(
                        id,
                        direccion,
                        distancia
                );

            default:

                System.err.println(
                        "Tipo de pedido desconocido: "
                                + tipo
                );

                return null;
        }
    }
}