package joguinho;

import java.util.ArrayList;
import java.util.Random;
import java.nio.file.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JogoDaForca {
    private String palavra;
    private String dica;
    private StringBuilder palavraAdivinhada;
    private int penalidade;
    private int acertos;
    private Set<String> letrasTentadas;

    private static final String[] PENALIDADES = {
            "Tá suave!",
            "Eita! Perdeu primeira perna",
            "Oxe! Perdeu segunda perna",
            "Égua! Perdeu primeiro braço",
            "Vixe! Perdeu segundo braço",
            "Misericórdia! Perdeu tronco",
            "Tá'ca moléstia! Perdeu cabeça"
    };

    public JogoDaForca() throws IOException {
        letrasTentadas = new HashSet<>();
        carregarPalavras();
    }

    private List<String> palavrasEDicas;

    private void carregarPalavras() throws IOException {
        Path path = Paths.get("src/joguinho/palavras.txt");
        //System.out.println("Caminho do arquivo: " + path.toAbsolutePath());
        if (!Files.exists(path)) {
            throw new IOException("arquivo de palavras inexistente");
        }
        palavrasEDicas = Files.readAllLines(path);
    }

    public void iniciar() {
        Random random = new Random();
        int indice = random.nextInt(palavrasEDicas.size());
        String linha = palavrasEDicas.get(indice);
        String[] partes = linha.split(";");
        palavra = partes[0].toLowerCase();
        dica = partes[1];
        palavraAdivinhada = new StringBuilder("*".repeat(palavra.length()));
        penalidade = 0;
        acertos = 0;
        letrasTentadas.clear();
    }

    public String getDica() {
        return dica;
    }

    public int getTamanho() {
        return palavra.length();
    }

    public ArrayList<Integer> tentarLetra(String letra) throws Exception {
        if (letra == null || letra.length() != 1 || letrasTentadas.contains(letra.toLowerCase())) {
            throw new Exception("Letra inválida ou já tentada");
        }

        ArrayList<Integer> ocorrencias = new ArrayList<>();
        letra = letra.toLowerCase();
        letrasTentadas.add(letra);

        for (int i = 0; i < palavra.length(); i++) {
            if (palavra.charAt(i) == letra.charAt(0)) {
                ocorrencias.add(i + 1);
                palavraAdivinhada.setCharAt(i, letra.charAt(0));
                acertos++;
            }
        }

        if (ocorrencias.isEmpty()) {
            penalidade++;
        }

        return ocorrencias;
    }

    public boolean terminou() {
        return penalidade >= 6 || acertos == palavra.length();
    }

    public String getPalavraAdivinhada() {
        return palavraAdivinhada.toString();
    }

    public int getAcertos() {
        return acertos;
    }

    public int getNumeroPenalidade() {
        return penalidade;
    }

    public String getNomePenalidade() {
        return PENALIDADES[penalidade];
    }

    public String getResultado() {
        if (penalidade >= 6) {
            return "você foi enforcado";
        } else if (acertos == palavra.length()) {
            return "você venceu";
        } else {
            return "jogo em andamento";
        }
    }
}

