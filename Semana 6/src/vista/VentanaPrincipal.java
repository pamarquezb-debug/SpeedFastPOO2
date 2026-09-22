package vista;

import controlador.ControladorPedidos;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private final ControladorPedidos controlador;

    public VentanaPrincipal(ControladorPedidos controlador) {

        this.controlador = controlador;

        setTitle("SpeedFast - Gestión de Entregas");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        crearInterfaz();
    }

    private void crearInterfaz() {

        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel(
                "SPEEDFAST - GESTIÓN DE ENTREGAS",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnRegistrar = new JButton("Registrar pedido");
        JButton btnListar = new JButton("Listar pedidos");
        JButton btnEntrega = new JButton("Asignar repartidor / Iniciar entrega");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnListar);
        panelBotones.add(btnEntrega);

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBorder(
                BorderFactory.createEmptyBorder(30, 60, 30, 60)
        );

        contenedor.add(panelBotones, BorderLayout.CENTER);

        add(contenedor, BorderLayout.CENTER);

        // Abrir ventana para registrar pedido
        btnRegistrar.addActionListener(e -> {

            VentanaRegistroPedido ventana =
                    new VentanaRegistroPedido(controlador);

            ventana.setVisible(true);
        });

        // Abrir listado
        btnListar.addActionListener(e -> {

            VentanaListaPedidos ventana =
                    new VentanaListaPedidos(controlador);

            ventana.setVisible(true);
        });

        // Lo implementaremos posteriormente
        btnEntrega.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "La asignación de repartidores será implementada a continuación.",
                    "SpeedFast",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
    }
}