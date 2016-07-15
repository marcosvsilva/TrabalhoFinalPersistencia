package br.ufg.inf.persistencia.saep.conexao2;

import br.ufg.inf.persistencia.saep.auxiliares2.Strings;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;

/**
 * Cria uma única conexão com o banco de dados.
 *
 * <p>Implementa as boas práticas de singleton
 * fazendo somente uma conexão com o banco de
 * dados para toda aplicação.
 */
public class InstanciaConexao {
    /**
     * Cliente do banco de dados.
     */
    private static MongoClient mongoClient = new MongoClient();

    /**
     * Banco de daos a ser utilizado pale aplicação.
     */
    private static MongoDatabase mongoDB = null;

    /**
     * Faz a leitura do arquivo de configuração.
     *
     * <p>O arquivo de configuração contém o nome do banco de dados
     * a ser utilizado pela aplicação.
     *
     * @return nome do banco de dados a ser utilizado.
     * @throws IOException caso ocorra um erro de leitura do arquivo.
     */
    private static String getProperties() throws IOException {
        Properties properties = new Properties();

        FileInputStream file = new
                FileInputStream(Strings.enderecoConfiguracao);

        properties.load(file);

        return properties.getProperty(Strings.tagDataBase);
    }

    /**
     * Cria collections.
     *
     * <p>Se existir a collection ela permanece sem alteração,
     * caso não exista, ela é deletada e criada novamente.
     *
     * @param collectionName nome da collection.
     * @throws IOException caso ocorra um erro de leitura ou criação.
     */
    private static void createCollections(String collectionName)
            throws IOException {
        MongoCollection<Document> testaExistencia =
                mongoDB.getCollection(collectionName);

        if (testaExistencia.find().first() == null) {
            mongoDB.getCollection(collectionName).drop();
            mongoDB.createCollection(collectionName);
        }
    }

    /**
     * Cria conexão
     *
     * <p>Método síncrono, que permite só um acesso por vez.
     *
     * @param dataBase dataBase nome do banco de dados a ser criada a conexão.
     * @return conexão com o banco de dados.
     */
    private static synchronized MongoDatabase createConnectio(String dataBase) {
        return mongoClient.getDatabase(dataBase);
    }

    /**
     * Busca a conexão.
     *
     * <p>Teste se a conexão já existe, se existir, retorna está conexão, caso
     * não exista, é criada uma conexão e retornada está nova conexão.
     *
     * @return conexão com o banco de dados.
     */
    public static MongoDatabase getConnection() {
        if (mongoDB == null) {
            try {
                String dataBase = getProperties();
                mongoDB = createConnectio(dataBase);
                createCollections(Strings.collectionParecer);
                createCollections(Strings.collectionRadoc);
                createCollections(Strings.collectionResolucao);
                createCollections(Strings.collectionTipo);
            } catch(IOException e) {
                System.out.println(e);
            }
        }

        return mongoDB;
    }
}