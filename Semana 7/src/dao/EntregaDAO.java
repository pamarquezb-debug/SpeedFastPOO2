package dao;

import modelo.Entrega;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

/**
 * Gestiona las operaciones relacionadas
 * con las entregas de SpeedFast.
 *
 * @author Pablo Marquez
 * @version 7.0
 */
public class EntregaDAO {

    /**
     * Registra una entrega y cambia el estado
     * del pedido a EN_REPARTO.
     *
     * Ambas operaciones se realizan dentro
     * de una misma transacción.
     *
     * @param entrega entrega que será registrada
     * @return true si la operación fue exitosa
     */
    public boolean guardar(Entrega entrega) {

        String sqlEntrega =
                "INSERT INTO entrega "
                        + "(id_pedido, id_repartidor, fecha, hora) "
                        + "VALUES (?, ?, ?, ?)";

        String sqlEstado =
                "UPDATE pedido "
                        + "SET estado = 'EN_REPARTO' "
                        + "WHERE id = ? "
                        + "AND estado = 'PENDIENTE'";

        Connection conexion = null;

        try {

            conexion = ConexionBD.conectar();

            // Inicia la transacción
            conexion.setAutoCommit(false);

            /*
             * Primero cambiamos el pedido de
             * PENDIENTE a EN_REPARTO.
             */
            try (PreparedStatement psEstado =
                         conexion.prepareStatement(sqlEstado)) {

                psEstado.setInt(
                        1,
                        entrega.getIdPedido()
                );

                int actualizados =
                        psEstado.executeUpdate();

                /*
                 * Si no se actualizó ninguna fila,
                 * el pedido ya no estaba pendiente.
                 */
                if (actualizados != 1) {

                    conexion.rollback();

                    System.err.println(
                            "El pedido ya no está disponible."
                    );

                    return false;
                }
            }

            /*
             * Registra la asignación del repartidor.
             */
            try (PreparedStatement psEntrega =
                         conexion.prepareStatement(sqlEntrega)) {

                psEntrega.setInt(
                        1,
                        entrega.getIdPedido()
                );

                psEntrega.setInt(
                        2,
                        entrega.getIdRepartidor()
                );

                psEntrega.setDate(
                        3,
                        Date.valueOf(
                                entrega.getFecha()
                        )
                );

                psEntrega.setTime(
                        4,
                        Time.valueOf(
                                entrega.getHora()
                        )
                );

                int insertados =
                        psEntrega.executeUpdate();

                if (insertados != 1) {

                    conexion.rollback();

                    return false;
                }
            }

            // Todo salió correctamente
            conexion.commit();

            return true;

        } catch (SQLException e) {

            if (conexion != null) {

                try {
                    conexion.rollback();

                } catch (SQLException rollbackError) {

                    System.err.println(
                            "Error al realizar rollback: "
                                    + rollbackError.getMessage()
                    );
                }
            }

            System.err.println(
                    "Error al registrar entrega: "
                            + e.getMessage()
            );

            return false;

        } finally {

            if (conexion != null) {

                try {

                    conexion.setAutoCommit(true);
                    conexion.close();

                } catch (SQLException e) {

                    System.err.println(
                            "Error al cerrar conexión: "
                                    + e.getMessage()
                    );
                }
            }
        }
    }
}