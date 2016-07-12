package Auxiliares;

import br.ufg.inf.es.saep.sandbox.dominio.Avaliavel;
import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import br.ufg.inf.es.saep.sandbox.dominio.Pontuacao;
import br.ufg.inf.es.saep.sandbox.dominio.Relato;
import com.google.gson.*;

import java.lang.reflect.Type;

public class Deserializacao implements JsonDeserializer<Nota>
{
    @Override
    public Nota deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject originalJson = (JsonObject) ((JsonObject) json).get("original");
        JsonObject novoJson = (JsonObject) ((JsonObject) json).get("novo");
        String justificativa = ((JsonObject) json).get("justificativa").getAsString();

        Avaliavel original = criarAvaliavel(originalJson);
        Avaliavel novo = criarAvaliavel(novoJson);

        return new Nota(
                original,
                novo,
                justificativa
        );
    }

    private Avaliavel criarAvaliavel(JsonObject jsonObject)
    {
        Gson gson = new Gson();

        if (jsonObject.has("tipo")) { return gson.fromJson(jsonObject, Relato.class); }
        else { return gson.fromJson(jsonObject, Pontuacao.class); }
    }
}