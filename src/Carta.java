import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Carta {

    private int indice;

    // método constructor
    public Carta(Random r) {
        indice = r.nextInt(52) + 1;
    }

    public void mostrar(JPanel pnl, int x, int y) {
        // cargar la imagen
        String rutaImagen = "imagenes/CARTA" + indice + ".JPG";
        ImageIcon imgCarta;
        try {
            imgCarta = new ImageIcon(getClass().getResource(rutaImagen));
        } catch (Exception e) {
            imgCarta = new ImageIcon();
        }

        // mostrar la imagen mediante un JLABEL
        JLabel lblCarta = new JLabel(imgCarta);
        lblCarta.setBounds(x, y, imgCarta.getIconWidth(), imgCarta.getIconHeight());
        pnl.add(lblCarta);

        // evento para mostrar la identidad de la carta (nombre y pinta)
        lblCarta.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evento) {
                JOptionPane.showMessageDialog(null, getNombre() + " de " + getPinta());
            }
        });
    }

    // getters

    public Pinta getPinta() {
        if (indice <= 13) {
            return Pinta.TREBOL;
        } else if (indice <= 26) {
            return Pinta.PICA;
        } else if (indice <= 39) {
            return Pinta.CORAZON;
        }
        return Pinta.DIAMANTE;
    }

    public NombreCarta getNombre() {
        int residuo = indice % 13;
        if (residuo == 0)
            residuo = 13;
        return NombreCarta.values()[residuo - 1];
    }

    // Método para calcular el valor de cada carta (Punto 4)
    public int getValor() {
        int residuo = indice % 13;
        if (residuo == 0)
            residuo = 13;
        
        // As (1), J (11), Q (12) y K (13) valen 10
        if (residuo == 1 || residuo >= 11) {
            return 10;
        } else {
            return residuo;
        }
    }
}