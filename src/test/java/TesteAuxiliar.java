import Auxiliares.Strings;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import java.util.*;

/**
 * Created by Marcos on 12/07/2016.
 */
public class TesteAuxiliar
{
    public Parecer createParecerAleatorio()
    {
        //Resolução aleatória
        String resolucao = UUID.randomUUID().toString();

        //Criando Radocs aleatórios
        ArrayList<String> radocs = createStrinsAleatorias();

        //Criando Pontuação Aleatória
        List<Pontuacao> pontuacao = createPontuacoesAleatorias();

        //Criando Notas Aleatorias
        List<Nota> notas = createNotasAleatorias();

        Parecer parecer = new Parecer(
                Strings.IDTeste,             // identificador do parecer
                Strings.IDTeste,             // identificador de resolucao
                radocs,                      // radocs
                pontuacao,                   // pontuação
                Strings.teste,               // fundamentacao
                notas                        // notas
        );

        return parecer;
    }

    public ArrayList<String> createStrinsAleatorias()
    {
        ArrayList<String> stringsAleatorias = new ArrayList<>(); //criação do ArrayList

        for (int i = 0; i < Strings.quantidadeMaximaTeste; i++)
            stringsAleatorias.add(Strings.teste); //populando o ArrayList com strings aleatórias

        return stringsAleatorias;
    }

    public List<Pontuacao> createPontuacoesAleatorias()
    {
        List<Pontuacao> pontuacoes = new ArrayList<>(); //criação do ArrayList

        for (int i = 0; i < Strings.quantidadeMaximaTeste; i++)
            pontuacoes.add(new Pontuacao(Strings.teste,new Valor(Strings.teste))); //populando pontuações

        return pontuacoes;
    }

    public List<Nota> createNotasAleatorias()
    {
        ArrayList<Nota> notas = new ArrayList<>();

        for (int i = 0; i < Strings.quantidadeMaximaTeste; i++)
            notas.add(createNotaAleatoria());

        return notas;
    }

    public Nota createNotaAleatoria()
    {
        Relato relatoOrigem = createRelatoAleatorio();
        Relato relatoDestino = createRelatoAleatorio();

        Nota nota = new Nota(relatoOrigem,relatoDestino,Strings.teste);

        return nota;
    }

    public Relato createRelatoAleatorio()
    {
        Map<String,Valor> relato = new HashMap<>();
        relato.put(Strings.teste,new Valor(Strings.teste));
        return new Relato(Strings.teste,relato);
    }
}
