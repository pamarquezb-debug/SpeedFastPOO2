package vista;

import controlador.ControladorPedidos;
import modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaListaPedidos extends JFrame {

    private final ControladorPedidos controlador;

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

    // Temporizador para actualizar automáticamente la tabla
    private Timer timerActualizacion;

    public VentanaListaPedidos(ControladorPedidos controlador) {

        this.controlador = controlador;

        setTitle("SpeedFast - Lista de Pedidos");
        setSize(750, 400);
        setLocationRelativeTo(null);

        crearInterfaz();

        // Carga inicial
        cargarPedidos();

        // Actualización automática cada 20 segundos
        iniciarActualizacionAutomatica();
    }

    private void crearInterfaz() {

        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel(
                "PEDIDOS REGISTRADOS",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        add(titulo, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Dirección",
                        "Tipo",
                        "Distancia",
                        "Tiempo estimado"
                },
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPedidos = new JTable(modeloTabla);

        JScrollPane scroll =
                new JScrollPane(tablaPedidos);

        add(scroll, BorderLayout.CENTER);

        JButton btnRefrescar =
                new JButton("Refrescar");

        JButton btnCerrar =
                new JButton("Cerrar");

        JPanel panelBotones = new JPanel();

        panelBotones.add(btnRefrescar);
        panelBotones.add(btnCerrar);

        add(panelBotones, BorderLayout.SOUTH);

        // Refresco manual
        btnRefrescar.addActionListener(
                e -> cargarPedidos()
        );

        // Cerrar ventana
        btnCerrar.addActionListener(e -> {
            detenerTimer();
            dispose();
        });
    }

    /**
     * Carga nuevamente los pedidos existentes
     * en el controlador.
     */
    private void cargarPedidos() {

        modeloTabla.setRowCount(0);

        for (Pedido pedido : controlador.obtenerPedidos()) {

            modeloTabla.addRow(
                    new Object[]{
                            pedido.getIdPedido(),
                            pedido.getDireccionEntrega(),
                            pedido.getTipo(),
                            pedido.getDistanciaKm() + " km",
                            pedido.calcularTiempoEntrega() + " min"
                    }
            );
        }
    }

    /**
     * Actualiza automáticamente la tabla
     * cada 20 segundos.
     */
    private void iniciarActualizacionAutomatica() {

        timerActualizacion = new Timer(
                20000,
                e -> cargarPedidos()
        );

        timerActualizacion.start();
    }

    /**
     * Detiene el temporizador.
     */
    private void detenerTimer() {

        if (timerActualizacion != null) {
            timerActualizacion.stop();
        }
    }
}