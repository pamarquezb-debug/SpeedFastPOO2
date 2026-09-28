package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Representa una entrega realizada por SpeedFast.
 *
 * Relaciona un pedido con un repartidor e incluye
 * la fecha y hora de la entrega.
 *
 * @author Pablo Marquez
 * @version 7.0
 */
public class Entrega {

    private int id;
    private int idPedido;
    private int idRepartidor;
    private LocalDate fecha;
    private LocalTime hora;

    /**
     * Constructor para crear una nueva entrega.
     *
     * @param idPedido identificador del pedido
     * @param idRepartidor identificador del repartidor
     * @param fecha fecha de la entrega
     * @param hora hora de la entrega
     */
    public Entrega(
            int idPedido,
            int idRepartidor,
            LocalDate fecha,
            LocalTime hora) {

        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    /**
     * Constructor utilizado cuando la entrega
     * ya existe en la base de datos.
     */
    public Entrega(
            int id,
            int idPedido,
            int idRepartidor,
            LocalDate fecha,
            LocalTime hora) {

        this.id = id;
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(int idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Entrega{" +
                "id=" + id +
                ", idPedido=" + idPedido +
                ", idRepartidor=" + idRepartidor +
                ", fecha=" + fecha +
                ", hora=" + hora +
                '}';
    }
}