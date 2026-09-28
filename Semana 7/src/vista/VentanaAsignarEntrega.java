package vista;

import controlador.ControladorPedidos;
import dao.EntregaDAO;
import dao.RepartidorDAO;
import modelo.Entrega;
import modelo.Pedido;
import modelo.Repartidor;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Ventana que permite asignar un repartidor
 * a un pedido e iniciar una entrega.
 *
 * @author Pablo Marquez
 * @version 7.0
 */
public class VentanaAsignarEntrega extends JFrame {

    private final ControladorPedidos controlador;

    private JComboBox<Pedido> cmbPedidos;
    private JComboBox<Repartidor> cmbRepartidores;

    /**
     * Constructor de la ventana.
     *
     * @param controlador controlador de pedidos
     */
    public VentanaAsignarEntrega(
            ControladorPedidos controlador) {

        this.controlador = controlador;

        setTitle("SpeedFast - Asignar Repartidor");
        setSize(500, 250);
        setLocationRelativeTo(null);
        setResizable(false);

        crearInterfaz();
        cargarDatos();
    }

    /**
     * Construye la interfaz gráfica.
     */
    private void crearInterfaz() {

        JPanel panel = new JPanel(
                new GridLayout(3, 2, 10, 10)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        25,
                        25,
                        25
                )
        );

        cmbPedidos = new JComboBox<>();
        cmbRepartidores = new JComboBox<>();

        cmbPedidos.setPreferredSize(
                new Dimension(220, 30)
        );

        cmbRepartidores.setPreferredSize(
                new Dimension(220, 30)
        );


        JButton btnAsignar =
                new JButton("Iniciar entrega");

        JButton btnCancelar =
                new JButton("Cancelar");

        panel.add(
                new JLabel("Pedido:")
        );

        panel.add(cmbPedidos);

        panel.add(
                new JLabel("Repartidor:")
        );

        panel.add(cmbRepartidores);

        panel.add(btnAsignar);
        panel.add(btnCancelar);

        add(panel);

        btnAsignar.addActionListener(
                e -> iniciarEntrega()
        );

        btnCancelar.addActionListener(
                e -> dispose()
        );
    }

    /**
     * Carga los pedidos y repartidores
     * almacenados en MySQL.
     */
    private void cargarDatos() {

        // Limpiar los combos antes de cargarlos
        cmbPedidos.removeAllItems();
        cmbRepartidores.removeAllItems();

        // Cargar pedidos disponibles
        List<Pedido> pedidos =
                controlador.obtenerPedidosDisponibles();

        for (Pedido pedido : pedidos) {
            cmbPedidos.addItem(pedido);
        }

        // Cargar repartidores
        RepartidorDAO repartidorDAO =
                new RepartidorDAO();

        List<Repartidor> repartidores =
                repartidorDAO.listarTodos();

        for (Repartidor repartidor : repartidores) {
            cmbRepartidores.addItem(repartidor);
        }
    }
    /**
     * Registra una nueva entrega en MySQL.
     */
    private void iniciarEntrega() {

        Pedido pedido =
                (Pedido) cmbPedidos.getSelectedItem();

        Repartidor repartidor =
                (Repartidor)
                        cmbRepartidores.getSelectedItem();

        if (pedido == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "No existen pedidos disponibles.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (repartidor == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "No existen repartidores disponibles.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Entrega entrega =
                new Entrega(
                        pedido.getIdPedido(),
                        repartidor.getId(),
                        LocalDate.now(),
                        LocalTime.now()
                );

        EntregaDAO entregaDAO =
                new EntregaDAO();

        boolean guardado =
                entregaDAO.guardar(entrega);

        if (guardado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega iniciada correctamente.\n\n"
                            + "Pedido: "
                            + pedido.getIdPedido()
                            + "\nRepartidor: "
                            + repartidor.getNombre(),
                    "SpeedFast",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible registrar la entrega.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}