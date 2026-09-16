import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class FrmJuego extends JFrame {

    private JButton btnRepartir;
    private JButton btnVerificar;
    private JTabbedPane tpJugadores;
    private JPanel pnlJugador1;
    private JPanel pnlJugador2;

    private Jugador[] jugadores = new Jugador[2];

    public FrmJuego() {
        setSize(600, 300);
        setTitle("Juguemos al Apuntado!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Inicializar jugadores
        for (int i = 0; i < jugadores.length; i++) {
            jugadores[i] = new Jugador();
        }

        // Botón Repartir
        btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(20, 10, 100, 30);
        add(btnRepartir);

        // Botón Verificar
        btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(130, 10, 100, 30);
        add(btnVerificar);

        // Paneles para cada jugador
        pnlJugador1 = new JPanel();
        pnlJugador1.setLayout(null);
        pnlJugador1.setBackground(new Color(0, 128, 0));

        pnlJugador2 = new JPanel();
        pnlJugador2.setLayout(null);
        pnlJugador2.setBackground(new Color(0, 128, 0));

        // Pestañas
        tpJugadores = new JTabbedPane();
        tpJugadores.setBounds(20, 50, 540, 180);
        tpJugadores.addTab("Martín Estrada Contreras", pnlJugador1);
        tpJugadores.addTab("Raúl Vidal", pnlJugador2);
        add(tpJugadores);

        // Eventos
        btnRepartir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnRepartirActionPerformed(evt);
            }
        });

        btnVerificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                btnVerificarActionPerformed(evt);
            }
        });
    }

    private void btnRepartirActionPerformed(ActionEvent evt) {
        for (int i = 0; i < jugadores.length; i++) {
            jugadores[i].repartir();
        }
        jugadores[0].mostrar(pnlJugador1, false);
        jugadores[1].mostrar(pnlJugador2, false);
    }

    private void btnVerificarActionPerformed(ActionEvent evt) {
        int pestaña = tpJugadores.getSelectedIndex();
        JOptionPane.showMessageDialog(this, jugadores[pestaña].obtenerGrupos());
    }

    public static void main(String[] args) {
        FrmJuego juego = new FrmJuego();
        juego.setVisible(true);
    }
}