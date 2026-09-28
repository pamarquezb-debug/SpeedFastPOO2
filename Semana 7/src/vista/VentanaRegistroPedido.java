package vista;

import controlador.ControladorPedidos;
import modelo.*;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana utilizada para registrar pedidos
 * en el sistema SpeedFast.
 *
 * Durante la Semana 7 los pedidos son almacenados
 * de forma persistente en una base de datos MySQL
 * mediante JDBC.
 *
 * @author Pablo Marquez
 * @version 7.0
 */
public class VentanaRegistroPedido extends JFrame {

    private final ControladorPedidos controlador;

    private JTextField txtDireccion;
    private JTextField txtDistancia;
    private JComboBox<String> cmbTipo;

    /**
     * Constructor de la ventana.
     *
     * @param controlador controlador principal de pedidos
     */
    public VentanaRegistroPedido(ControladorPedidos controlador) {

        this.controlador = controlador;

        setTitle("SpeedFast - Registrar Pedido");
        setSize(450, 280);
        setLocationRelativeTo(null);
        setResizable(false);

        crearInterfaz();
    }

    /**
     * Construye los componentes gráficos de la ventana.
     */
    private void crearInterfaz() {

        JPanel panelFormulario = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        panelFormulario.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        txtDireccion = new JTextField();
        txtDistancia = new JTextField();

        cmbTipo = new JComboBox<>(
                new String[]{
                        "Comida",
                        "Encomienda",
                        "Express"
                }
        );

        JButton btnGuardar =
                new JButton("Guardar");

        JButton btnCancelar =
                new JButton("Cancelar");

        panelFormulario.add(
                new JLabel("Dirección:")
        );

        panelFormulario.add(
                txtDireccion
        );

        panelFormulario.add(
                new JLabel("Distancia (km):")
        );

        panelFormulario.add(
                txtDistancia
        );

        panelFormulario.add(
                new JLabel("Tipo:")
        );

        panelFormulario.add(
                cmbTipo
        );

        panelFormulario.add(
                btnGuardar
        );

        panelFormulario.add(
                btnCancelar
        );

        add(panelFormulario);

        btnGuardar.addActionListener(
                e -> guardarPedido()
        );

        btnCancelar.addActionListener(
                e -> dispose()
        );
    }

    /**
     * Valida los datos ingresados, crea el tipo
     * correspondiente de pedido y solicita al
     * controlador almacenarlo en MySQL.
     */
    private void guardarPedido() {

        try {

            if (txtDireccion.getText().trim().isEmpty()
                    || txtDistancia.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe completar todos los campos.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String direccion =
                    txtDireccion.getText().trim();

            double distancia =
                    Double.parseDouble(
                            txtDistancia.getText().trim()
                    );

            if (distancia <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "La distancia debe ser mayor que cero.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String tipo =
                    cmbTipo.getSelectedItem().toString();

            /*
             * El ID se establece inicialmente en 0
             * porque MySQL genera el ID real mediante
             * AUTO_INCREMENT.
             */
            Pedido pedido;

            switch (tipo) {

                case "Comida":
                    pedido = new PedidoComida(
                            0,
                            direccion,
                            distancia
                    );
                    break;

                case "Encomienda":
                    pedido = new PedidoEncomienda(
                            0,
                            direccion,
                            distancia
                    );
                    break;

                case "Express":
                    pedido = new PedidoExpress(
                            0,
                            direccion,
                            distancia
                    );
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Tipo de pedido no válido."
                    );
            }

            /*
             * El controlador realiza el registro
             * persistente utilizando PedidoDAO.
             */
            boolean guardado =
                    controlador.agregarPedido(pedido);

            if (guardado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Pedido registrado correctamente en la base de datos.",
                        "SpeedFast",
                        JOptionPane.INFORMATION_MESSAGE
                );

                limpiarCampos();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No fue posible registrar el pedido.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "La distancia debe ser un valor numérico.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Limpia los componentes del formulario
     * después de registrar un pedido.
     */
    private void limpiarCampos() {

        txtDireccion.setText("");
        txtDistancia.setText("");

        cmbTipo.setSelectedIndex(0);

        txtDireccion.requestFocus();
    }
}