package vista;

import controlador.ControladorPedidos;
import modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Ventana que muestra los pedidos registrados
 * en la base de datos de SpeedFast.
 *
 * Los datos son obtenidos mediante el controlador,
 * el cual utiliza PedidoDAO para consultar MySQL.
 *
 * La tabla se actualiza automáticamente cada
 * 20 segundos.
 *
 * @author Pablo Marquez
 * @version 7.0
 */
public class VentanaListaPedidos extends JFrame {

    private final ControladorPedidos controlador;

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

    private Timer timerActualizacion;

    /**
     * Constructor de la ventana.
     *
     * @param controlador controlador de pedidos
     */
    public VentanaListaPedidos(ControladorPedidos controlador) {

        this.controlador = controlador;

        setTitle("SpeedFast - Lista de Pedidos");
        setSize(850, 400);
        setLocationRelativeTo(null);

        crearInterfaz();

        // Primera consulta a MySQL
        cargarPedidos();

        // Actualización automática cada 20 segundos
        iniciarActualizacionAutomatica();

        /*
         * Detiene el temporizador cuando la ventana
         * es cerrada utilizando la X.
         */
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                detenerTimer();
            }
        });
    }

    /**
     * Construye los componentes gráficos.
     */
    private void crearInterfaz() {

        setLayout(
                new BorderLayout(10, 10)
        );

        JLabel titulo = new JLabel(
                "PEDIDOS REGISTRADOS",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        add(
                titulo,
                BorderLayout.NORTH
        );

        modeloTabla = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Dirección",
                        "Tipo",
                        "Distancia",
                        "Tiempo estimado",
                        "Estado"
                },
                0
        ) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {

                return false;
            }
        };

        tablaPedidos =
                new JTable(modeloTabla);

        JScrollPane scroll =
                new JScrollPane(tablaPedidos);

        add(
                scroll,
                BorderLayout.CENTER
        );

        JButton btnRefrescar =
                new JButton("Refrescar");

        JButton btnCerrar =
                new JButton("Cerrar");

        JPanel panelBotones =
                new JPanel();

        panelBotones.add(
                btnRefrescar
        );

        panelBotones.add(
                btnCerrar
        );

        add(
                panelBotones,
                BorderLayout.SOUTH
        );

        /*
         * Permite consultar inmediatamente
         * los pedidos almacenados en MySQL.
         */
        btnRefrescar.addActionListener(
                e -> cargarPedidos()
        );

        btnCerrar.addActionListener(e -> {

            detenerTimer();

            dispose();
        });
    }

    /**
     * Consulta los pedidos almacenados en MySQL
     * y actualiza el contenido del JTable.
     */
    private void cargarPedidos() {

        modeloTabla.setRowCount(0);

        for (Pedido pedido :
                controlador.obtenerPedidos()) {

            modeloTabla.addRow(
                    new Object[]{
                            pedido.getIdPedido(),
                            pedido.getDireccionEntrega(),
                            pedido.getTipo(),
                            pedido.getDistanciaKm() + " km",
                            pedido.calcularTiempoEntrega() + " min",
                            pedido.getEstado()
                    }
            );
        }
    }

    /**
     * Actualiza automáticamente la información
     * cada 20 segundos.
     */
    private void iniciarActualizacionAutomatica() {

        timerActualizacion =
                new Timer(
                        20000,
                        e -> cargarPedidos()
                );

        timerActualizacion.start();
    }

    /**
     * Detiene el temporizador utilizado
     * para actualizar la tabla.
     */
    private void detenerTimer() {

        if (timerActualizacion != null) {
            timerActualizacion.stop();
        }
    }
}