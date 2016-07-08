package Conexao;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Marcos on 05/07/2016.
 */

public class ConexaoBD
{
    private Gson gson = new Gson();
    private MongoDatabase mongo;

    public ConexaoBD()
    {
        this.mongo = InstanciaConexao.getConnection();
    }

    /*
        CRUD do banco de dados
    */

    public void create(String json, String collectionName)
    {
        MongoCollection<Document> collection = mongo.getCollection(collectionName);
        Document create = Document.parse(json);
        collection.insertOne(create);
    }

    public Document read(String filter, String key, String collectionName)
    {
        MongoCollection<Document> collection = mongo.getCollection(collectionName);
        return collection.find(eq(filter,key)).first();
    }

    public void update(String filter, String key, String json, String collectionName)
    {
        MongoCollection<Document> collection = mongo.getCollection(collectionName);
        Document update = Document.parse(json);
        collection.replaceOne(eq(filter,key),update);
    }

    public void delete(String filter, String key, String json, String collectionName)
    {
        MongoCollection<Document> collection = mongo.getCollection(collectionName);
        collection.deleteOne(eq(filter,key));
    }

}