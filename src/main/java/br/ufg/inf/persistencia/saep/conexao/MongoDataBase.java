package br.ufg.inf.persistencia.saep.conexao;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

/**
 * CRUD do banco de dados
 *
 * <p>Estes métodos serão os meios de acesso a informações nos bancos de daos.

 * <p>Para realizar qualquer operação no banco de dados, estes,
 * serão utilizados.
 */
public class MongoDataBase {

    private Gson gson = new Gson();
    private MongoDatabase mongo;

    /**
     * Instância a conexão com o banco de dados.
     */
    public MongoDataBase(MongoDatabase mongo) {
        this.mongo = mongo;
    }

    /**
     * Cria e grava um documento.
     *
     * <p>Persiste um documento no banco de dados.
     *
     * @param json a ser persistida no banco.
     * @param collectionName a collection em que fará a persistência dos
     *                       documentos.
     */
    public void create(String json, String collectionName) {
        MongoCollection<Document> collection =
                mongo.getCollection(collectionName);

        Document create = Document.parse(json);
        collection.insertOne(create);
    }

    /**
     * Método que realiza a leitura de um documento da collection.
     *
     * <p>Realiza busca e retorna o primeiro documento que satisfaz o filtro.
     *
     * @param filter campo que será utilizado para filtrar.
     * @param key chave que será utilizada para filtrar.
     * @param collectionName collection que fará a busca.
     * @return primeiro documento que satisfaz o filtro.
     */
    public Document read(String filter, String key, String collectionName) {
        MongoCollection<Document> collection =
                mongo.getCollection(collectionName);

        return collection.find(eq(filter,key)).first();
    }

    /**
     * Método que realiza a leitura de todas informações da collection.
     *
     * <p>Busca todos documentos de uma collection.
     *
     * @param collectionName collection que fará a leitura.
     * @return iterador de documentos.
     */
    public Iterable<Document> readAll(String collectionName) {
        MongoCollection<Document> collection =
                mongo.getCollection(collectionName);

        return collection.find();
    }

    /**
     * Método que realiza a leitura de uma ou mais informações da collection.
     *
     * <p>Realiza a busca e retorna todos documentos que satisfazem o filtro.
     *
     * <p>A diferença entre read e readAllByFilter é que read torna somente
     * um documento, enquanto readAllByFilter torna um ou mais documentos.
     * Também o filtro do método read e montado na conexão, enquanto o filtro
     * do método readAllByFilter é montado antes da função ser chamada.
     *
     * @param filter documento que será utilizado como query filtro.
     * @param collectionName collection em que será realizada a leitura.
     * @return iterador de documentos.
     */
    public Iterable<Document> readAllByFilter(Document filter,
                                              String collectionName) {
        MongoCollection<Document> collection =
                mongo.getCollection(collectionName);

        return collection.find(filter);
    }

    /**
     * Método que realiza atualização de um documento.
     *
     * <p>Realiza a busca e substituí um documento que satisfaz o filtro.
     *
     * @param filter campo que será utilizado para filtrar.
     * @param key chave que será utilizada para filtrar.
     * @param json que substituirá o documento atual.
     * @param collectionName collection que fará a busca.
     */
    public void update(String filter, String key, String json,
                       String collectionName) {
        MongoCollection<Document> collection =
                mongo.getCollection(collectionName);

        Document update = Document.parse(json);
        collection.replaceOne(eq(filter,key),update);
    }

    /**
     * Método que realiza a deleção de um documento.
     *
     * <p>Realiza a busca e deleta um documento que satisfaz o filtro.
     *
     * @param filter campo que será utilizado para filtrar.
     * @param key chave que será utilizada para filtrar.
     * @param collectionName collection que fará a busca.
     */
    public void delete(String filter, String key, String collectionName) {
        MongoCollection<Document> collection =
                mongo.getCollection(collectionName);

        collection.deleteOne(eq(filter,key));
    }
}