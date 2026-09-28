package dao;

import modelo.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona las operaciones de base de datos
 * relacionadas con los repartidores de SpeedFast.
 *
 * @author Pablo Marquez
 * @version 7.0
 */
public class RepartidorDAO {

    /**
     * Guarda un nuevo repartidor en la base de datos.
     *
     * @param repartidor repartidor que será almacenado
     * @return true si fue guardado correctamente
     */
    public boolean guardar(Repartidor repartidor) {

        String sql =
                "INSERT INTO repartidor (nombre) "
                        + "VALUES (?)";

        try (Connection conexion =
                     ConexionBD.conectar();

             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setString(
                    1,
                    repartidor.getNombre()
            );

            int filasAfectadas =
                    ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Error al guardar repartidor: "
                            + e.getMessage()
            );

            return false;
        }
    }

    /**
     * Obtiene todos los repartidores almacenados
     * en la base de datos.
     *
     * @return lista de repartidores
     */
    public List<Repartidor> listarTodos() {

        List<Repartidor> repartidores =
                new ArrayList<>();

        String sql =
                "SELECT id, nombre "
                        + "FROM repartidor "
                        + "ORDER BY nombre";

        try (Connection conexion =
                     ConexionBD.conectar();

             PreparedStatement ps =
                     conexion.prepareStatement(sql);

             ResultSet rs =
                     ps.executeQuery()) {

            while (rs.next()) {

                int id =
                        rs.getInt("id");

                String nombre =
                        rs.getString("nombre");

                Repartidor repartidor =
                        new Repartidor(
                                id,
                                nombre
                        );

                repartidores.add(repartidor);
            }

        } catch (SQLException e) {

            System.err.println(
                    "Error al listar repartidores: "
                            + e.getMessage()
            );
        }

        return repartidores;
    }
}