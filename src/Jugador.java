import java.util.*;
import javax.swing.*;

public class Jugador {
    private Random r;
    private Carta[] cartas;
    private Grupo[] grupos;
    private NombreCarta[] cartasGrupo;

    public Jugador() {
        r = new Random();
    }

    public void repartir() {
        cartas = new Carta[10];
        for (int i = 0; i < 10; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl, boolean tapada) {
        pnl.removeAll();
        if (cartas != null) {
            for (int i = 0; i < cartas.length; i++) {
                cartas[i].mostrar(pnl, 5 + i * 40, 5);
            }
        }
        pnl.repaint();
    }

    public String obtenerGrupos() {
        grupos = null;
        int[] contadores = new int[13];

        // Contar la cantidad de apariciones de cada nombre de carta
        for (int i = 0; i < 10; i++) {
            contadores[cartas[i].getNombre().ordinal()]++;
        }

        int totalGrupos = 0;
        for (int i = 0; i < 13; i++) {
            if (contadores[i] >= 2) {
                totalGrupos++;
            }
        }

        if (totalGrupos > 0) {
            grupos = new Grupo[totalGrupos];
            cartasGrupo = new NombreCarta[totalGrupos];
            totalGrupos = 0;
            for (int i = 0; i < 13; i++) {
                if (contadores[i] >= 2) {
                    grupos[totalGrupos] = Grupo.values()[contadores[i]];
                    cartasGrupo[totalGrupos] = NombreCarta.values()[i];
                    totalGrupos++;
                }
            }
        }

        String mensaje = "";
        if (grupos == null) {
            mensaje = "No hay grupos por nombre de carta.\n";
        } else {
            mensaje = "El jugador tiene los siguientes grupos:\n";
            for (int i = 0; i < grupos.length; i++) {
                mensaje += grupos[i].name() + " de " + cartasGrupo[i].toString() + "\n";
            }
        }

        // --- PUNTO 4: ESCALERAS DE LA MISMA PINTA ---
        String mensajeEscaleras = obtenerEscaleras();
        mensaje += "\n" + mensajeEscaleras;

        // --- PUNTO 4: CÁLCULO DEL PUNTAJE SOBRANTE ---
        int puntaje = calcularPuntaje();
        mensaje += "\n\nPuntaje de cartas no agrupadas: " + puntaje;

        return mensaje;
    }

    // Método para obtener escaleras de la misma pinta (Punto 4)
    public String obtenerEscaleras() {
        List<List<Integer>> cartasPorPinta = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            cartasPorPinta.add(new ArrayList<>());
        }

        for (Carta c : cartas) {
            int pintaIdx = c.getPinta().ordinal();
            if (pintaIdx >= 0 && pintaIdx < 4) {
                int num = c.getNombre().ordinal() + 1;
                if (!cartasPorPinta.get(pintaIdx).contains(num)) {
                    cartasPorPinta.get(pintaIdx).add(num);
                }
            }
        }

        StringBuilder resultado = new StringBuilder();
        boolean hayEscalera = false;

        for (int i = 0; i < 4; i++) {
            List<Integer> lista = cartasPorPinta.get(i);
            Collections.sort(lista);

            int contador = 1;
            for (int j = 0; j < lista.size() - 1; j++) {
                if (lista.get(j + 1) == lista.get(j) + 1) {
                    contador++;
                } else {
                    if (contador >= 3) {
                        hayEscalera = true;
                        resultado.append("Escalera de ").append(Pinta.values()[i])
                                 .append(" de ").append(contador).append(" cartas\n");
                    }
                    contador = 1;
                }
            }
            if (contador >= 3) {
                hayEscalera = true;
                resultado.append("Escalera de ").append(Pinta.values()[i])
                         .append(" de ").append(contador).append(" cartas\n");
            }
        }

        if (!hayEscalera) {
            return "No hay escaleras de la misma pinta.";
        }
        return "Escaleras encontradas:\n" + resultado.toString();
    }

    // Método para calcular el puntaje de las cartas que no forman grupos (Punto 4)
    public int calcularPuntaje() {
        int[] contadores = new int[13];
        for (Carta c : cartas) {
            contadores[c.getNombre().ordinal()]++;
        }

        int puntajeTotal = 0;
        for (Carta c : cartas) {
            int idx = c.getNombre().ordinal();
            if (contadores[idx] < 2) {
                puntajeTotal += c.getValor();
            }
        }
        return puntajeTotal;
    }
}