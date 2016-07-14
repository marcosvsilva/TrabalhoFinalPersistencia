package br.ufg.inf.persistencia.saep;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import br.ufg.inf.persistencia.saep.auxiliares.Deserializacao;
import br.ufg.inf.persistencia.saep.auxiliares.Strings;
import br.ufg.inf.persistencia.saep.conexao.ConexaoBD;
import br.ufg.inf.persistencia.saep.interfaces.ParecerRepositorioIMPL;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.UUID;

/**
 * Testes da implementação do repositório Parecer.
 */
public class TesteParecer {

    private static ParecerRepository parecerRepository;
    private static ConexaoBD conexao = new ConexaoBD();
    private static TesteAuxiliar auxiliar = new TesteAuxiliar();
    private static Gson gson;

    @BeforeClass
    public static void setup() {
        parecerRepository = new ParecerRepositorioIMPL(conexao);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Nota.class, new Deserializacao());
        gson = gsonBuilder.create();
    }

    @Test
    public void byId() {
        Parecer parecer = auxiliar.createParecer(true);
        parecerRepository.removeParecer(Strings.IDTeste);
        parecerRepository.persisteParecer(parecer);

        Parecer parecerGravadoByID = parecerRepository.byId(Strings.IDTeste);
        String parecerGravadoByIDJson = gson.toJson(parecerGravadoByID);

        Document parecerGravadoByConexaoDocument =
                conexao.read(Strings.ID,Strings.IDTeste,
                        Strings.collectionParecer);

        String parecerGravadoByConexaoJson =
                gson.toJson(parecerGravadoByConexaoDocument);

        Parecer parecerGravadoByConexao =
                gson.fromJson(parecerGravadoByConexaoJson,Parecer.class);

        String parecerGravadoByConexaoJsonTeste =
                gson.toJson(parecerGravadoByConexao);

        Assert.assertEquals(parecerGravadoByIDJson,parecerGravadoByConexaoJsonTeste);
    }

    @Test
    public void byIdIdentificadorInexistente() {
        String idBusca = "";
        Parecer parecer = parecerRepository.byId(idBusca);
        Assert.assertNull(parecer);
    }

    @Test
    public void adicionaERemoveParecer() {
        parecerRepository.removeParecer(Strings.IDTeste);

        Parecer parecer = auxiliar.createParecer(true);
        parecerRepository.persisteParecer(parecer);
        String parecerJson = gson.toJson(parecer);

        Parecer parecerGravado = parecerRepository.byId(Strings.IDTeste);
        String parecerGravadoJson = gson.toJson(parecerGravado);

        Assert.assertEquals(parecerJson,parecerGravadoJson);

        parecerRepository.removeParecer(Strings.IDTeste);
        Parecer parecerAposDelecao = parecerRepository.byId(Strings.IDTeste);

        Assert.assertNull(parecerAposDelecao);
    }

    @Test(expected = CampoExigidoNaoFornecido.class)
    public void adicionaParecerExecesaoCampoExigidoNaoFornecido() {
        Parecer parecer = auxiliar.createParecer(false);
        parecerRepository.persisteParecer(parecer);
    }

    @Test(expected = IdentificadorExistente.class)
    public void adicionaParecerExecesaoIdentificadorExistente() {
        Parecer parecer = auxiliar.createParecer(true);
        parecerRepository.persisteParecer(parecer);
        parecerRepository.persisteParecer(parecer);
    }

    @Test
    public void removeEAdicionaNota() {
        Parecer parecer = auxiliar.createParecer(true);
        parecerRepository.persisteParecer(parecer);

        Parecer parecerOriginal = parecerRepository.byId(Strings.IDTeste);
        String parecerOriginalJson = gson.toJson(parecerOriginal);

        Nota nota = auxiliar.createNota();
        parecerRepository.adicionaNota(Strings.IDTeste,nota);
        Parecer parecerDepoisInsercao = parecerRepository.byId(Strings.IDTeste);
        String parecerDepoisInsercaoJson = gson.toJson(parecerDepoisInsercao);

        Assert.assertNotEquals(parecerOriginalJson,parecerDepoisInsercaoJson);

        parecerRepository.removeNota(Strings.IDTeste,nota.getItemOriginal());
        Parecer parecerAposDelecao = parecerRepository.byId(Strings.IDTeste);
        String parecerAposDelecaoJson = gson.toJson(parecerAposDelecao);

        Assert.assertEquals(parecerOriginalJson,parecerAposDelecaoJson);
    }

    @Test (expected = IdentificadorDesconhecido.class)
    public void adicionaNotaExecaoIdentificadorDesconhecido(){
        Nota nota = auxiliar.createNota();
        parecerRepository.adicionaNota("",nota);
    }

    @Test (expected = IdentificadorDesconhecido.class)
    public void removeNotaExecaoIdentificadorDesconhecido(){
        Nota nota = auxiliar.createNota();
        parecerRepository.removeNota("",nota.getItemOriginal());
    }

    @Test
    public void atualizaFundamentacao() {
        Parecer parecer = auxiliar.createParecer(true);
        parecerRepository.persisteParecer(parecer);

        Parecer parecerOriginal = parecerRepository.byId(Strings.IDTeste);
        String fundamentacaoNova = UUID.randomUUID().toString();

        if (!fundamentacaoNova.equals(parecerOriginal.getFundamentacao())) {
            parecerRepository.
                    atualizaFundamentacao(Strings.IDTeste,fundamentacaoNova);
        }

        Parecer parecerAposAtualizacao =
                parecerRepository.byId(Strings.IDTeste);

        Assert.assertEquals(fundamentacaoNova,
                parecerAposAtualizacao.getFundamentacao());
    }

    @Test
    public void adicionaERemoveRadoc() {
        Radoc radoc = auxiliar.createRadoc();

        parecerRepository.persisteRadoc(radoc);
    }

}