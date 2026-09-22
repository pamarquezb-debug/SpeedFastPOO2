package vista;

import controlador.ControladorPedidos;
import modelo.*;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistroPedido extends JFrame {

    private final ControladorPedidos controlador;

    private JTextField txtId;
    private JTextField txtDireccion;
    private JTextField txtDistancia;
    private JComboBox<String> cmbTipo;

    public VentanaRegistroPedido(ControladorPedidos controlador) {

        this.controlador = controlador;

        setTitle("SpeedFast - Registrar Pedido");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setResizable(false);

        crearInterfaz();
    }

    private void crearInterfaz() {

        JPanel panelFormulario = new JPanel(
                new GridLayout(5, 2, 10, 10)
        );

        panelFormulario.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        txtId = new JTextField();
        txtDireccion = new JTextField();
        txtDistancia = new JTextField();

        cmbTipo = new JComboBox<>(
                new String[]{"Comida", "Encomienda", "Express"}
        );

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        panelFormulario.add(new JLabel("ID Pedido:"));
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Dirección:"));
        panelFormulario.add(txtDireccion);

        panelFormulario.add(new JLabel("Distancia (km):"));
        panelFormulario.add(txtDistancia);

        panelFormulario.add(new JLabel("Tipo:"));
        panelFormulario.add(cmbTipo);

        panelFormulario.add(btnGuardar);
        panelFormulario.add(btnCancelar);

        add(panelFormulario);

        btnGuardar.addActionListener(e -> guardarPedido());

        btnCancelar.addActionListener(e -> dispose());
    }

    private void guardarPedido() {

        try {

            if (txtId.getText().trim().isEmpty()
                    || txtDireccion.getText().trim().isEmpty()
                    || txtDistancia.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe completar todos los campos.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            int id = Integer.parseInt(txtId.getText().trim());

            double distancia =
                    Double.parseDouble(txtDistancia.getText().trim());

            String direccion = txtDireccion.getText().trim();

            if (id <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "El ID debe ser mayor que cero.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (distancia <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "La distancia debe ser mayor que cero.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (controlador.existePedido(id)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ya existe un pedido con el ID " + id,
                        "Pedido duplicado",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String tipo =
                    cmbTipo.getSelectedItem().toString();

            Pedido pedido;

            switch (tipo) {

                case "Comida":
                    pedido = new PedidoComida(
                            id,
                            direccion,
                            distancia
                    );
                    break;

                case "Encomienda":
                    pedido = new PedidoEncomienda(
                            id,
                            direccion,
                            distancia
                    );
                    break;

                case "Express":
                    pedido = new PedidoExpress(
                            id,
                            direccion,
                            distancia
                    );
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Tipo de pedido no válido."
                    );
            }

            controlador.agregarPedido(pedido);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido registrado correctamente.",
                    "SpeedFast",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limpiarCampos();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "ID y distancia deben ser valores numéricos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limpiarCampos() {

        txtId.setText("");
        txtDireccion.setText("");
        txtDistancia.setText("");

        cmbTipo.setSelectedIndex(0);

        txtId.requestFocus();
    }
}