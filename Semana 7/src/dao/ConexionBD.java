package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión JDBC con la base de datos
 * MySQL utilizada por SpeedFast.
 *
 * @author Pablo Marquez
 * @version 7.0
 */
public class ConexionBD {

    /**
     * URL de conexión a la base de datos SpeedFast.
     */


    private static final String URL =
            "jdbc:mysql://localhost:3306/speedfast_db"
                    + "?useSSL=false"
                    + "&serverTimezone=America/Santiago";

    /**
     * Usuario de MySQL.
     */
    private static final String USER = "root";

    /**
     * Contraseña de MySQL.
     *
     * Cambiar por la contraseña configurada
     * en el servidor MySQL local.
     */
    private static final String PASSWORD = "";

    /**
     * Evita crear instancias de esta clase.
     */
    private ConexionBD() {
    }

    /**
     * Obtiene una nueva conexión con MySQL.
     *
     * @return conexión activa con speedfast_db
     * @throws SQLException si ocurre un error de conexión
     */
    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
        );
    }
}