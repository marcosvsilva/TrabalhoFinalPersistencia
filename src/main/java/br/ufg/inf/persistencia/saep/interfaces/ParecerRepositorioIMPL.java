package br.ufg.inf.persistencia.saep.interfaces;

import br.ufg.inf.persistencia.saep.auxiliares.Deserializacao;
import br.ufg.inf.persistencia.saep.conexao.ConexaoBD;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.Document;
import br.ufg.inf.persistencia.saep.auxiliares.Strings;

import java.util.List;

/**
 * Implmentação da interface do repositório Parecer.
 */
public class ParecerRepositorioIMPL implements ParecerRepository {

    private static Gson gson;
    private ConexaoBD conexao;

    public ParecerRepositorioIMPL(ConexaoBD conexao) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Nota.class, new Deserializacao());
        gson = gsonBuilder.create();
        this.conexao = conexao;
    }

    @Override
    public void adicionaNota(String id, Nota nota) {
        Parecer parecer = byId(id);

        if (parecer != null) {
            List<Nota> notas = parecer.getNotas();
            removeNota(id,nota.getItemOriginal());

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
            conexao.update(Strings.ID,id,parecerJson,Strings.collectionParecer);
        }
        else {
            throw new IdentificadorDesconhecido(id);
        }
    }

    @Override
    public void removeNota(String id, Avaliavel avaliavel) {
        Parecer parecer = byId(id);

        if (parecer != null) {
            String avaliavelJson = gson.toJson(avaliavel);

            List<Nota> notas = parecer.getNotas();

            for (int i = 0; i < notas.size(); i++) {
                String avaliavelTest =
                        gson.toJson(notas.get(i).getItemOriginal());

                if (avaliavelJson.equals(avaliavelTest)) {
                    notas.remove(i);
                    break;
                }
            }

            Parecer parecerUpdate = new Parecer(
                    parecer.getId(),
                    parecer.getResolucao(),
                    parecer.getRadocs(),
                    parecer.getPontuacoes(),
                    parecer.getFundamentacao(),
                    notas
            );

            String parecerJson = gson.toJson(parecerUpdate);
            conexao.update(Strings.ID,id,parecerJson,Strings.collectionParecer);
        } else {
            throw new IdentificadorDesconhecido(id);
        }
    }

    @Override
    public void persisteParecer(Parecer parecer) {
        if (parecer.getId() == null || parecer.getId().equals("")){
            throw new CampoExigidoNaoFornecido(Strings.ID);
        }

        Parecer existeParecer = byId(parecer.getId());

        if (existeParecer == null) {
            String parecerJson = gson.toJson(parecer);
            conexao.create(parecerJson, Strings.collectionParecer);
        } else {
            throw new IdentificadorExistente(parecer.getId());
        }
    }

    @Override
    public void atualizaFundamentacao(String id, String fundamentacao) {
        Parecer parecer = byId(id);

        if (parecer != null) {
            Parecer parecerUpdate = new Parecer(
                    parecer.getId(),
                    parecer.getResolucao(),
                    parecer.getRadocs(),
                    parecer.getPontuacoes(),
                    fundamentacao,
                    parecer.getNotas()
            );

            String parecerJson = gson.toJson(parecerUpdate);
            conexao.update(Strings.ID,id,parecerJson,Strings.collectionParecer);
        } else {
            throw new IdentificadorDesconhecido(id);
        }
    }

    @Override
    public Parecer byId(String id) {
        Document parecerDocument =
                conexao.read(Strings.ID, id, Strings.collectionParecer);

        if (parecerDocument == null) {
            return null;
        }

        String parecerJson = gson.toJson(parecerDocument);

        return gson.fromJson(parecerJson,Parecer.class);
    }

    @Override
    public void removeParecer(String id) {
        conexao.delete(Strings.ID, id, Strings.collectionParecer);
    }

    @Override
    public Radoc radocById(String id) {
        Document radocDocument =
                conexao.read(Strings.ID, id, Strings.collectionRadoc);

        if (radocDocument == null) {
            return null;
        }

        String radocJson = gson.toJson(radocDocument);

        return gson.fromJson(radocJson,Radoc.class);
    }

    @Override
    public String persisteRadoc(Radoc radoc) {
        if (radoc.getId() == null || radoc.getId().equals("")) {
            throw new CampoExigidoNaoFornecido(Strings.ID);
        }

        Radoc existeRadoc = radocById(radoc.getId());

        if (existeRadoc == null) {
            String radocJson = gson.toJson(radoc);
            conexao.create(radocJson, Strings.collectionRadoc);
        } else {
            throw new IdentificadorExistente(radoc.getId());
        }

        Radoc verificaInsercao = radocById(radoc.getId());

        if (verificaInsercao != null) {
            return verificaInsercao.getId();
        } else {
            return null;
        }
    }

    @Override
    public void removeRadoc(String id) {
        Document filter = new Document(Strings.filterRadoc,id);

        Document radocReferenciado =
                conexao.read(Strings.filterRadoc, id, Strings.collectionParecer);

        if (radocReferenciado == null) {
            conexao.delete(Strings.ID, id, Strings.collectionRadoc);
        } else {
            throw new ExisteParecerReferenciandoRadoc(id);
        }
    }
}