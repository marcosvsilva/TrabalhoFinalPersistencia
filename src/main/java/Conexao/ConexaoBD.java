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

        C = Create
        R = Read
        I = Update
        D = Delete

        Estes métodos serão os meios de acesso a informações nos bancos de daos.
        Para realizar qualquer operação no banco de dados, estes, serão utilizados.
    */


    /*
        Método que realiza a persistência de um objetos no banco em formato String json, UM A UM
        Recebe como parâmetro uma String em formato jSon para persistência e também
        a collection em que fará a persistência dos objetos.
    */
    public void create(String json, String collectionName)
    {
        MongoCollection<Document> collection = mongo.getCollection(collectionName);
        Document create = Document.parse(json);
        collection.insertOne(create);
    }


    /*
        Método que realiza a leitura de uma informações da collection que satisfaz um filtro passado como parâmetro.
        Para o filtro o método recebe uma string filter e uma string key para reazliar o filtro
        por exemplo "id = 1";.
        Também recebe a collection que fará a leitura.
    */
    public Document read(String filter, String key, String collectionName)
    {
        MongoCollection<Document> collection = mongo.getCollection(collectionName);
        return collection.find(eq(filter,key)).first();
    }

    /*
        Método que realiza a leitura de todas informações da collection.
        Recebe como parâmetro somente o nome da collection que fará a leitura.
    */
    public Iterable<Document> readAll(String collectionName)
    {
        MongoCollection<Document> collection = mongo.getCollection(collectionName);
        return collection.find();
    }

    /*
        Método que realiza a leitura de uma ou mais informações da collection que satisfaz um filtro passado como
        parâmetro, o filtro já é passado em Document, este Document contém uma string filter e uma string key
        para reazliar o filtro, por exemplo "id = 1";.
        Também recebe a collection que fará a leitura.
        PS: A diferença entre read e readAllByFilter é que read torna somente um documento, enquanto readAllByFilter
        torna um ou mais documentos. Também o filtro do método read e montado na conexão, enquanto o filtro do método
        readAllByFilter é montado antes da função ser chamada.
    */
    public Iterable<Document> readAllByFilter(Document filter, String collectionName)
    {
        MongoCollection<Document> collection = mongo.getCollection(collectionName);
        return collection.find(filter);
    }

    /*
        Método que realiza atualização de um objeto do banco de dados.
        Recebe como parâmetro um filter e uma key, para que seja localizado o objeto de interesse que será atualizado.
        Exemplo "id(filtro) = 1(key)".
        Recebe também um documento para substituir o arquivo existente e recebe ainda,
        a collection em que fará a atualização.
    */
    public void updateByFilter(String filter, String key, Document filterDocument, String collectionName)
    {
        MongoCollection<Document> collection = mongo.getCollection(collectionName);
        collection.updateOne(eq(filter,key),filterDocument);
    }

    /*
        Método que realiza atualização de um objeto do banco de dados.
        Recebe como parâmetro um filter e uma key, para que seja localizado o objeto de interesse que será atualizado.
        Exemplo "id(filtro) = 1(key)".
        Também recebe uma string json que substituíra o objeto que satisfaz o critério de filtro e recebe ainda,
        a collection em que fará a atualização.
    */
    public void update(String filter, String key, String json, String collectionName)
    {
        MongoCollection<Document> collection = mongo.getCollection(collectionName);
        Document update = Document.parse(json);
        collection.replaceOne(eq(filter,key),update);
    }

    /*
        Método que realiza a deleção de uma informação que satisfaz um filtro.
        Para o filtro o método recebe uma string filter e uma string chave para reazliar o filtro, por exemplo
        "nome = Marcos", e também recebe a collection em que fará a deleção.
    */
    public void delete(String filter, String key, String collectionName)
    {
        MongoCollection<Document> collection = mongo.getCollection(collectionName);
        collection.deleteOne(eq(filter,key));
    }

}