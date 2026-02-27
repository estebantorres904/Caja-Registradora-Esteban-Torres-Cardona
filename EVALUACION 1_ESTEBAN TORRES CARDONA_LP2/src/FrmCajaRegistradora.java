import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class FrmCajaRegistradora extends JFrame {

    // variables globales
    private int[] denominaciones = {20000, 10000, 5000, 2000, 1000, 500, 200, 100, 50};
    private int[] existencia = new int[denominaciones.length];

    private JComboBox cmbDenominacion;
    private JTextField txtExistencia;
    private JTextField txtValor;
    private JTable tblResultado;

    // método constructor
    public FrmCajaRegistradora() {

        setTitle("Caja Registradora");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblDen = new JLabel("Denominación:");
        lblDen.setBounds(10, 10, 100, 25);
        add(lblDen);

        cmbDenominacion = new JComboBox();
        cmbDenominacion.setBounds(120, 10, 100, 25);
        add(cmbDenominacion);

        for (int i = 0; i < denominaciones.length; i++) {
            cmbDenominacion.addItem(denominaciones[i]);
        }

        JLabel lblExist = new JLabel("Existencia:");
        lblExist.setBounds(240, 10, 80, 25);
        add(lblExist);

        txtExistencia = new JTextField();
        txtExistencia.setBounds(320, 10, 80, 25);
        add(txtExistencia);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(420, 10, 100, 25);
        add(btnRegistrar);

        JLabel lblValor = new JLabel("Valor a devolver:");
        lblValor.setBounds(10, 50, 120, 25);
        add(lblValor);

        txtValor = new JTextField();
        txtValor.setBounds(130, 50, 100, 25);
        add(txtValor);

        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setBounds(250, 50, 100, 25);
        add(btnCalcular);

        tblResultado = new JTable();
        JScrollPane sp = new JScrollPane(tblResultado);
        sp.setBounds(10, 100, 560, 280);
        add(sp);

        // eventos
        btnRegistrar.addActionListener(e -> {
            registrarExistencia();
        });

        btnCalcular.addActionListener(e -> {
            calcularDevuelta();
        });
    }

    private void registrarExistencia() {

        int index = cmbDenominacion.getSelectedIndex();
        int cantidad = Integer.parseInt(txtExistencia.getText());

        existencia[index] = cantidad;

        JOptionPane.showMessageDialog(null, "Existencia registrada correctamente");
        txtExistencia.setText("");
    }

    private void calcularDevuelta() {

        int valor = Integer.parseInt(txtValor.getText());
        int valorOriginal = valor;
        int[] cantidad = new int[denominaciones.length];

        for (int i = 0; i < denominaciones.length; i++) {

            int necesarias = valor / denominaciones[i];

            if (necesarias > existencia[i]) {
                necesarias = existencia[i];
            }

            cantidad[i] = necesarias;
            valor = valor - (necesarias * denominaciones[i]);
        }

        if (valor > 0) {
            JOptionPane.showMessageDialog(null, "No se puede dar la devuelta exacta");
            return;
        }

        // Construcción del mensaje solicitado
        String mensaje = "La devuelta se compone de:\n\n";

        for (int i = 0; i < denominaciones.length; i++) {
            if (cantidad[i] > 0) {

                String tipo;
                if (denominaciones[i] >= 1000)
                    tipo = "billete";
                else
                    tipo = "moneda";

                if (cantidad[i] == 1) {
                    mensaje += cantidad[i] + " " + tipo + " de $ " + denominaciones[i] + "\n";
                } else {
                    mensaje += cantidad[i] + " " + tipo + "s de $ " + denominaciones[i] + "\n";
                }
            }
        }

        JOptionPane.showMessageDialog(null, mensaje);

        mostrarTabla(cantidad);
    }

    private void mostrarTabla(int[] cantidad) {

        String[] encabezados = {"Denominación", "Tipo", "Cantidad"};
        String[][] datos = new String[denominaciones.length][3];

        for (int i = 0; i < denominaciones.length; i++) {

            datos[i][0] = String.valueOf(denominaciones[i]);

            if (denominaciones[i] >= 1000)
                datos[i][1] = "Billete";
            else
                datos[i][1] = "Moneda";

            datos[i][2] = String.valueOf(cantidad[i]);
        }

        DefaultTableModel modelo = new DefaultTableModel(datos, encabezados);
        tblResultado.setModel(modelo);
    }
}