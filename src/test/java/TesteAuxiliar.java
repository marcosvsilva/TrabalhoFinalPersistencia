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
                Strings.fundamentacaoTeste,  // fundamentacao
                notas                        // notas
        );

        return parecer;
    }

    public ArrayList<String> createStrinsAleatorias()
    {
        int quantidadePosicoes = (int) (Math.random() * 9) + 1; //define a quantidade de strings aleatoriamente

        ArrayList<String> stringsAleatorias = new ArrayList<>(); //criação do ArrayList

        for (int i = 0; i < quantidadePosicoes; i++)
            stringsAleatorias.add(UUID.randomUUID().toString()); //populando o ArrayList com strings aleatórias

        return stringsAleatorias;
    }

    public List<Pontuacao> createPontuacoesAleatorias()
    {
        int quantidadePontuacao = (int) (Math.random() * 9) + 1; //define a quantidade de strings aleatoriamente

        List<Pontuacao> pontuacoes = new ArrayList<>(); //criação do ArrayList

        for (int i = 0; i < quantidadePontuacao; i++)
            pontuacoes.add(new Pontuacao(UUID.randomUUID().toString(),new Valor(UUID.randomUUID().toString())));

        return pontuacoes;
    }

    public List<Nota> createNotasAleatorias()
    {
        int quantidadePosicoes = (int) (Math.random() * 9) + 1; //define a quantidade de strings aleatoriamente

        ArrayList<Nota> notas = new ArrayList<>();

        for (int i = 0; i < quantidadePosicoes; i++)
            notas.add(createNotaAleatoria());

        return notas;
    }

    public Nota createNotaAleatoria()
    {
        Relato relatoOrigem = createRelatoAleatorio();
        Relato relatoDestino = createRelatoAleatorio();

        Nota nota = new Nota(relatoOrigem,relatoDestino,UUID.randomUUID().toString());

        return nota;
    }

    public Relato createRelatoAleatorio()
    {
        Map<String,Valor> relato = new HashMap<>();
        relato.put(Strings.teste,new Valor(Strings.teste));
        return new Relato(UUID.randomUUID().toString(),relato);
    }


}
