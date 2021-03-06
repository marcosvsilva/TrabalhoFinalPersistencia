package br.ufg.inf.persistencia.saep.interfaces;

import br.ufg.inf.persistencia.saep.auxiliares.Strings;
import br.ufg.inf.persistencia.saep.conexao.MongoDataBase;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Implmentação da interface do repositório Resolução.
 *
 * Essa implementação de persistência utiliza banco de dados noSQL
 * MongoDB versão 3.2.7
 */
public class ResolucaoRepositorioMongoDataBase implements ResolucaoRepository {

    private static Gson gson = new Gson();
    private MongoDataBase conexao;

    public ResolucaoRepositorioMongoDataBase(MongoDataBase conexao) {
        this.conexao = conexao;
    }

    @Override
    public Resolucao byId(String id) {
        Document resolucaoDocument =
                conexao.read(Strings.ID, id, Strings.collectionResolucao);

        if (resolucaoDocument == null) {
            return null;
        }

        String resolucaoJson = gson.toJson(resolucaoDocument);

        return gson.fromJson(resolucaoJson,Resolucao.class);
    }

    @Override
    public String persiste(Resolucao resolucao) {
        if(resolucao.getId() == null || resolucao.getId().equals("")) {
            throw new CampoExigidoNaoFornecido(Strings.ID);
        }

        Resolucao existeResolucao = byId(resolucao.getId());

        if (existeResolucao == null) {
            String resolucaoJson = gson.toJson(resolucao);
            conexao.create(resolucaoJson, Strings.collectionResolucao);
        }
        else {
            throw new IdentificadorExistente(existeResolucao.getId());
        }

        Resolucao verificaInsercao = byId(resolucao.getId());

        if (verificaInsercao != null) {
            return verificaInsercao.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean remove(String id) {
        conexao.delete(Strings.ID,id,Strings.collectionResolucao);

        Resolucao verificaRemocao = byId(id);

        if (verificaRemocao == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> resolucoes() {
        Iterable<Document> resolucoes =
                conexao.readAll(Strings.collectionResolucao);

        List<String> idsResolucoes = new ArrayList<>();

        for (Document resolucao: resolucoes) {
            String json = gson.toJson(resolucao);
            Resolucao resolucaoObject = gson.fromJson(json,Resolucao.class);
            idsResolucoes.add(resolucaoObject.getId());
        }

        return idsResolucoes;
    }

    @Override
    public void persisteTipo(Tipo tipo) {
        if (tipo.getId() == null || tipo.getId().equals("")) {
            throw new CampoExigidoNaoFornecido(Strings.ID);
        }

        Resolucao existeTipo = byId(tipo.getId());

        if (existeTipo == null) {
            String tipoJson = gson.toJson(tipo);
            conexao.create(tipoJson, Strings.collectionTipo);
        }
        else {
            throw new IdentificadorExistente(tipo.getId());
        }
    }

    @Override
    public void removeTipo(String codigo) {
        Document existeRegra =
                conexao.read(Strings.filterRegra,
                        codigo,Strings.collectionResolucao);

        if (existeRegra == null) {
            conexao.delete(Strings.ID, codigo, Strings.collectionTipo);
        } else {
            throw new ResolucaoUsaTipoException(codigo);
        }
    }

    @Override
    public Tipo tipoPeloCodigo(String codigo) {
        Document tipoDocument =
                conexao.read(Strings.ID,codigo,Strings.collectionTipo);

        String json = gson.toJson(tipoDocument);

        return gson.fromJson(json,Tipo.class);
    }

    @Override
    public List<Tipo> tiposPeloNome(String nome) {
        Document filter =
                new Document(Strings.filterNome, Pattern.compile(nome));

        Iterable<Document> tiposDocument =
                conexao.readAllByFilter(filter,Strings.collectionTipo);

        List<Tipo> tipos = new ArrayList<>();

        for (Document tipo: tiposDocument) {
            String json = gson.toJson(tipo);
            tipos.add(gson.fromJson(json,Tipo.class));
        }

        return tipos;
    }
}