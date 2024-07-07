package joguinho;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TelaJogo extends JFrame {
    private JogoDaForca jogo;
    private JLabel labelTamanho;
    private JLabel labelDica;
    private JTextField textFieldLetra;
    private JButton buttonAdivinhar;
    private JLabel labelMensagem;
    private JLabel labelPalavraAdivinhada;
    private JLabel labelAcertos;
    private JLabel labelPenalidade;
    private JButton buttonIniciar;

    public TelaJogo() {
        super("Jogo da Forca");
        configurarJanela();
        adicionarComponentes();
        configurarEventos();
    }

    private void configurarJanela() {
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 1));
    }

    private void adicionarComponentes() {
        buttonIniciar = new JButton("Iniciar Jogo");
        labelTamanho = new JLabel("Tamanho da palavra: ");
        labelDica = new JLabel("Dica: ");
        textFieldLetra = new JTextField();
        buttonAdivinhar = new JButton("Adivinhar Letra");
        labelMensagem = new JLabel("");
        labelPalavraAdivinhada = new JLabel("Palavra: ");
        labelAcertos = new JLabel("Acertos: ");
        labelPenalidade = new JLabel("Penalidade: ");

        add(buttonIniciar);
        add(labelTamanho);
        add(labelDica);
        add(new JLabel("Digite uma letra:"));
        add(textFieldLetra);
        add(buttonAdivinhar);
        add(labelMensagem);
        add(labelPalavraAdivinhada);
        add(labelAcertos);
        add(labelPenalidade);
    }

    private void configurarEventos() {
        buttonIniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    jogo = new JogoDaForca();
                    jogo.iniciar();
                    labelTamanho.setText("Tamanho da palavra: " + jogo.getTamanho());
                    labelDica.setText("Dica: " + jogo.getDica());
                    labelMensagem.setText("");
                    labelPalavraAdivinhada.setText("Palavra: " + jogo.getPalavraAdivinhada());
                    labelAcertos.setText("Acertos: " + jogo.getAcertos());
                    labelPenalidade.setText("Penalidade: " + jogo.getNomePenalidade());
                } catch (Exception ex) {
                    labelMensagem.setText("Erro: " + ex.getMessage());
                }
            }
        });

        buttonAdivinhar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String letra = textFieldLetra.getText();
                    ArrayList<Integer> ocorrencias = jogo.tentarLetra(letra);
                    if (ocorrencias.isEmpty()) {
                        labelMensagem.setText("Letra não encontrada");
                    } else {
                        labelMensagem.setText("Letra encontrada nas posições: " + ocorrencias);
                    }
                    labelPalavraAdivinhada.setText("Palavra: " + jogo.getPalavraAdivinhada());
                    labelAcertos.setText("Acertos: " + jogo.getAcertos());
                    labelPenalidade.setText("Penalidade: " + jogo.getNomePenalidade());

                    if (jogo.terminou()) {
                        labelMensagem.setText(jogo.getResultado());
                        buttonAdivinhar.setEnabled(false);
                    }
                } catch (Exception ex) {
                    labelMensagem.setText("Erro: " + ex.getMessage());
                }
                textFieldLetra.setText("");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TelaJogo().setVisible(true);
            }
        });
    }
}
