package br.ufg.inf.persistencia.saep.auxiliares2;

import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel;
import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import br.ufg.inf.es.saep.sandbox.dominio.Pontuacao;
import br.ufg.inf.es.saep.sandbox.dominio.Relato;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Classe utilizada para implementar um deserializador customizado.
 *
 * <p>E necessária, pois a classe Parecer implementa um atributo interface
 * que o gson original não consegue converter para objeto java pois não
 * consegue tomar a decisão para qual classe recupera a interface, logo, a
 * classe Deserializacao auxilia o gson a tomar a decisão.
 */
public class Deserializacao implements JsonDeserializer<Nota> {

    /**
     * Criação da classe Deserialização.
     *
     * @param json os dados JSON sendo desserializado.
     * @param typeOfT o tipo do objeto para desserializar.
     * @param context contexto para desserialização.
     * @return objeto java do tipo Nota.
     * @throws JsonParseException exceção caso exista problemas de análise.
     */
    @Override
    public Nota deserialize(JsonElement json, Type typeOfT,
                            JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject originalJson = (JsonObject)
                ((JsonObject) json).get("original");

        JsonObject novoJson = (JsonObject)
                ((JsonObject) json).get("novo");

        String justificativa =
                ((JsonObject) json).get("justificativa").getAsString();

        Avaliavel original = criarAvaliavel(originalJson);
        Avaliavel novo = criarAvaliavel(novoJson);

        return new Nota(original, novo, justificativa);
    }

    /**
     * Cria um objeto avaliavel.
     *
     * <p>A partir do @param é criado um objeto Avaliavel implementando
     * Relato ou Pontuação.
     *
     * @param jsonObject parâmetro que será analisado para a decisão da
     *                   implementação da interface.
     * @return objeto Relato ou Pontuacao
     */
    private Avaliavel criarAvaliavel(JsonObject jsonObject) {
        Gson gson = new Gson();

        if (jsonObject.has("tipo")) {
            return gson.fromJson(jsonObject, Relato.class);
        } else {
            return gson.fromJson(jsonObject, Pontuacao.class);
        }
    }
}