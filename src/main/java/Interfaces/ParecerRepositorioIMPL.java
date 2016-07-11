package Interfaces;

import Conexao.ConexaoBD;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;
import org.bson.Document;
import Auxiliares.Strings;

import java.util.List;

/**
 * Created by marco_000 on 05/07/2016.
 */
public class ParecerRepositorioIMPL implements ParecerRepository
{

    private static Gson gson;
    private ConexaoBD conexao;

    public ParecerRepositorioIMPL(ConexaoBD conexao)
    {
        this.conexao = conexao;
    }

    @Override
    public void adicionaNota(String s, Nota nota)
    {
        Parecer parecer = byId(s);

        if (parecer != null)
        {
            List<Nota> notas = parecer.getNotas();
            notas.add(nota);

            Parecer parecerUpdate = new Parecer(
                    parecer.getId(),
                    parecer.getResolucao(),
                    parecer.getRadocs(),
                    parecer.getPontuacoes(),
                    parecer.getFundamentacao(),
                    notas
            );

            String parecerJson = gson.toJson(parecerUpdate);
            conexao.update(Strings.ID,s,parecerJson,Strings.collectionParecer);
        }  else
        {
            throw new IdentificadorDesconhecido(s);
        }

    }

    @Override
    public void removeNota(String s, Avaliavel avaliavel)
    {
        Parecer parecer = byId(s);

    }

    @Override
    public void persisteParecer(Parecer parecer)
    {
        Parecer existeParecer = byId(parecer.getId());

        if (existeParecer == null)
        {
            String parecerJson = gson.toJson(parecer);
            conexao.create(parecerJson, Strings.collectionParecer);
        }
    }

    @Override
    public void atualizaFundamentacao(String s, String s1)
    {
        Parecer parecer = byId(s);

        if (parecer != null)
        {
            Parecer parecerUpdate = new Parecer(
                    parecer.getId(),
                    parecer.getResolucao(),
                    parecer.getRadocs(),
                    parecer.getPontuacoes(),
                    s1,
                    parecer.getNotas()
            );


            String parecerJson = gson.toJson(parecerUpdate);
            conexao.update(Strings.ID,s,parecerJson,Strings.collectionParecer);
        }
    }

    @Override
    public Parecer byId(String s)
    {
        Document parecerDocument = conexao.read(Strings.ID, s, Strings.collectionParecer);

        if (parecerDocument == null)
            return null;

        String parecerJson = gson.toJson(parecerDocument);
        return gson.fromJson(parecerJson,Parecer.class);
    }

    @Override
    public void removeParecer(String s)
    {
        conexao.delete(Strings.ID, s, Strings.collectionParecer);
    }

    @Override
    public Radoc radocById(String s)
    {
        Document radocDocument = conexao.read(Strings.ID, s, Strings.collectionRadoc);

        if (radocDocument == null)
            return null;

        String radocJson = gson.toJson(radocDocument);
        return gson.fromJson(radocJson,Radoc.class);
    }

    @Override
    public String persisteRadoc(Radoc radoc)
    {
        Radoc existeRadoc = radocById(radoc.getId());

        if (existeRadoc == null)
        {
            String radocJson = gson.toJson(radoc);
            conexao.create(radocJson, Strings.collectionRadoc);
        }

        return null;
    }

    @Override
    public void removeRadoc(String s)
    {
        conexao.delete(Strings.ID, s, Strings.collectionRadoc);
    }
}
