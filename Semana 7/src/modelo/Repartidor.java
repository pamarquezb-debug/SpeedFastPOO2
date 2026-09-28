package modelo;

/**
 * Representa un repartidor de SpeedFast.
 *
 * @author Pablo Marquez
 * @version 7.0
 */
public class Repartidor {

    private int id;
    private String nombre;

    /**
     * Constructor para registrar un nuevo repartidor.
     *
     * @param nombre nombre del repartidor
     */
    public Repartidor(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Constructor utilizado para recuperar
     * repartidores desde MySQL.
     *
     * @param id identificador del repartidor
     * @param nombre nombre del repartidor
     */
    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}