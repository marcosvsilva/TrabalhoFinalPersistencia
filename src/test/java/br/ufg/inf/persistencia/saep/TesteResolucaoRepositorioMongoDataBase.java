package br.ufg.inf.persistencia.saep;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import br.ufg.inf.persistencia.saep.auxiliares.Strings;
import br.ufg.inf.persistencia.saep.conexao.*;
import br.ufg.inf.persistencia.saep.interfaces.ResolucaoRepositorioMongoDataBase;
import com.google.gson.Gson;
import org.bson.Document;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Testes da implementação do repositório Resoluçao.
 */
public class TesteResolucaoRepositorioMongoDataBase
{

    private static ResolucaoRepository resolucaoRepository;
    private static MongoDataBase conexao = new MongoDataBase(
            InstanciaConexao.getConnection());
    private static TesteAuxiliar auxiliar = new TesteAuxiliar();
    private static Gson gson = new Gson();

    @BeforeClass
    public static void setup() {
        resolucaoRepository = new ResolucaoRepositorioMongoDataBase(conexao);
    }

    @Test
    public void byId() {
        resolucaoRepository.remove(Strings.IDTeste);

        Resolucao resolucao = auxiliar.createResolucao(Strings.IDTeste);
        resolucaoRepository.persiste(resolucao);

        Resolucao resolucaoById = resolucaoRepository.byId(Strings.IDTeste);
        String resolucaoByIdJson = gson.toJson(resolucaoById);

        Document resolucaoByConexaoDocument = conexao.read(Strings.ID,
                Strings.IDTeste,Strings.collectionResolucao);
        String resolucaoByConexaoJson = gson.toJson(resolucaoByConexaoDocument);
        Resolucao resolucaoByConexao = gson.fromJson(resolucaoByConexaoJson,
                Resolucao.class);
        String resolucaoByConexaoJsonTest = gson.toJson(resolucaoByConexao);

        Assert.assertEquals(resolucaoByIdJson,resolucaoByConexaoJsonTest);
    }

    @Test
    public void byIdIdentificadorInexistente() {
        String idBusca = "";
        Resolucao resolucao = resolucaoRepository.byId(idBusca);
        Assert.assertNull(resolucao);
    }

    @Test
    public void adicionaResolucaoSucesso() {
        resolucaoRepository.remove(Strings.IDTeste);
        Resolucao resolucao = auxiliar.createResolucao(Strings.IDTeste);
        String resultado = resolucaoRepository.persiste(resolucao);
        Assert.assertEquals(resolucao.getId(),resultado);
    }

    @Test
    public void adicionaERemoveResolucao(){
        resolucaoRepository.remove(Strings.IDTeste);

        Resolucao resolucao = auxiliar.createResolucao(Strings.IDTeste);
        resolucaoRepository.persiste(resolucao);
        String resolucaoJson = gson.toJson(resolucao);

        Resolucao resolucaoGravado = resolucaoRepository.byId(Strings.IDTeste);
        String resolucaoGravadoJson = gson.toJson(resolucaoGravado);

        Assert.assertEquals(resolucaoJson,resolucaoGravadoJson);

        resolucaoRepository.remove(Strings.IDTeste);
        Resolucao resolucaoAposDelecao =
                resolucaoRepository.byId(Strings.IDTeste);

        Assert.assertNull(resolucaoAposDelecao);
    }

    @Test(expected = CampoExigidoNaoFornecido.class)
    public void adicionaResolucaoExcecaoCampoExigidoNaoFornecido() {
        Resolucao resolucao = auxiliar.createResolucao("");
        resolucaoRepository.persiste(resolucao);
    }

    @Test(expected = IdentificadorExistente.class)
    public void adicionaResolucaoExcecaoIdentificadorExistente() {
        Resolucao resolucao = auxiliar.createResolucao(Strings.IDTeste);
        resolucaoRepository.persiste(resolucao);
        resolucaoRepository.persiste(resolucao);
    }

    @Test
    public void buscaResolucoes(){
        Iterable<Document> resolucoes =
                conexao.readAll(Strings.collectionResolucao);

        List<String> idsResolucoesByConexao = new ArrayList<>();

        for (Document resolucao: resolucoes) {
            String json = gson.toJson(resolucao);
            Resolucao resolucaoObject = gson.fromJson(json,Resolucao.class);
            idsResolucoesByConexao.add(resolucaoObject.getId());
        }

        List<String> idsResolucesByClasse = resolucaoRepository.resolucoes();

        Assert.assertEquals(idsResolucesByClasse,idsResolucoesByConexao);
    }

    @Test
    public void tipoByCodigo() {
        resolucaoRepository.remove(Strings.IDTeste);
        resolucaoRepository.removeTipo(Strings.IDTeste);

        Tipo tipo = auxiliar.createTipo(Strings.IDTeste);
        resolucaoRepository.persisteTipo(tipo);

        Tipo tipoByCodigo = resolucaoRepository.tipoPeloCodigo(Strings.IDTeste);
        String tipoByCodigoJson = gson.toJson(tipoByCodigo);

        Document tipoByConexaoDocument = conexao.read(Strings.ID,
                Strings.IDTeste,Strings.collectionTipo);
        String tipoByConexaoJson = gson.toJson(tipoByConexaoDocument);
        Tipo tipoByConexao = gson.fromJson(tipoByConexaoJson, Tipo.class);
        String tipoByConexaoJsonTest = gson.toJson(tipoByConexao);

        Assert.assertEquals(tipoByCodigoJson,tipoByConexaoJsonTest);
    }

    @Test
    public void adionaERemoveTipo(){
        resolucaoRepository.remove(Strings.IDTeste);
        resolucaoRepository.removeTipo(Strings.IDTeste);

        Tipo tipo = auxiliar.createTipo(Strings.IDTeste);
        resolucaoRepository.persisteTipo(tipo);
        String tipoGravadoJson = gson.toJson(tipo);

        Tipo tipoRetornado = resolucaoRepository.tipoPeloCodigo(Strings.IDTeste);
        String tipoRetornadoJson = gson.toJson(tipoRetornado);

        Assert.assertEquals(tipoGravadoJson,tipoRetornadoJson);
    }

    @Test
    public void buscaTipoByNome(){
        List<Tipo> tiposByNomes =
                resolucaoRepository.tiposPeloNome(Strings.teste);

        List<String> tiposIdByNome = new ArrayList<>();

        for (Tipo tipo: tiposByNomes) {
            tiposIdByNome.add(tipo.getId());
        }

        Document filter =
                new Document(Strings.filterNome, Pattern.compile(Strings.teste));

        Iterable<Document> tiposDocument =
                conexao.readAllByFilter(filter,Strings.collectionTipo);

        List<Tipo> tiposByConexao = new ArrayList<>();

        List<String> tiposIdByConexao = new ArrayList<>();

        for (Document tipo: tiposDocument) {
                String json = gson.toJson(tipo);
                tiposIdByConexao.add(gson.fromJson(json,Tipo.class).getId());
        }

        Assert.assertEquals(tiposIdByNome,tiposIdByConexao);
    }
}