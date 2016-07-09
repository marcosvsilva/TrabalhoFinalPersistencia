package Interfaces;

import Conexao.ConexaoBD;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;
import org.bson.Document;
import Auxiliares.Strings;

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

    }

    @Override
    public void removeNota(Avaliavel avaliavel)
    {

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
        Radoc existeRadoc = byId(radoc.getId());

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

    }
}
