package Interfaces;

import Auxiliares.Strings;
import Conexao.ConexaoBD;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;
import org.bson.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco_000 on 05/07/2016.
 */
public class ResolucaoRepositorioIMPL implements ResolucaoRepository
{
    private static Gson gson;
    private ConexaoBD conexao;

    public ResolucaoRepositorioIMPL(ConexaoBD conexao) { this.conexao = conexao; }

    @Override
    public Resolucao byId(String id)
    {
        Document resolucaoDocument = conexao.read(Strings.ID, id, Strings.collectionResolucao);

        if (resolucaoDocument == null)
            return null;

        String resolucaoJson = gson.toJson(resolucaoDocument);
        return gson.fromJson(resolucaoJson,Resolucao.class);
    }

    @Override
    public String persiste(Resolucao resolucao)
    {
        if (resolucao.getId() == null || resolucao.getId().equals(""))
            throw new CampoExigidoNaoFornecido(Strings.ID);

        Resolucao existeResolucao = byId(resolucao.getId());

        if (existeResolucao == null)
        {
            String resolucaoJson = gson.toJson(resolucao);
            conexao.create(resolucaoJson, Strings.collectionResolucao);
        }
        else { throw new IdentificadorExistente(existeResolucao.getId()); }

        Resolucao verificaInsercao = byId(resolucao.getId());

        if (verificaInsercao != null) { return verificaInsercao.getId(); }
        else { return null; }
    }

    @Override
    public boolean remove(String id)
    {
        conexao.delete(Strings.ID,id,Strings.collectionResolucao);

        Resolucao verificaRemocao = byId(id);

        if (verificaRemocao == null) { return true; }
        else {return false; }
    }

    @Override
    public List<String> resolucoes()
    {
        /*

        ArrayList<String> resolucoes = new ArrayList<>();
        Iterable<Document> listaResolucoes = dbHelper.findAll(resolucaoCollection);

        String idResolucao;
        for (Document documentResolucao : listaResolucoes) {
            idResolucao = documentResolucao.getString("id");
            listaIdsResolucoes.add(idResolucao);
        }

        return listaIdsResolucoes;
                 */
        return null;
    }

    @Override
    public void persisteTipo(Tipo tipo)
    {

    }

    @Override
    public void removeTipo(String s)
    {

    }

    @Override
    public Tipo tipoPeloCodigo(String s)
    {
        return null;
    }

    @Override
    public List<Tipo> tiposPeloNome(String s)
    {
        return null;
    }
}
