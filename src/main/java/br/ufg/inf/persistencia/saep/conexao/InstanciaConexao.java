package br.ufg.inf.persistencia.saep.conexao;

import br.ufg.inf.persistencia.saep.auxiliares.ConexaoSemSucessoException;
import br.ufg.inf.persistencia.saep.auxiliares.Strings;
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
    private static MongoClient mongoClient = null;

    /**
     * Banco de daos a ser utilizado pale aplicação.
     */
    private static MongoDatabase mongoDB = null;

    /**
     * Faz a leitura do arquivo de configuração.
     *
     * <p>O arquivo de configuração contém todas informações importantes
     * para a conexão da aplicação com o banco de dados.
     *
     * @return arquivo de configurações
     * @throws IOException caso ocorra um erro de leitura do arquivo.
     */
    private static Properties getProperties() {
        Properties properties = new Properties();

        FileInputStream file = null;
        try {
            file = new
                    FileInputStream(Strings.enderecoConfiguracao);

            properties.load(file);
        } catch (IOException f) {
            throw new ConexaoSemSucessoException(f.getMessage());
        }

        return properties;
    }

    /**
     * Recupera do arquivo de configuração o nome do banco de dados.
     *
     * @return nome do banco de dados.
     */
    private static String getNameDataBase() {
        Properties properties = getProperties();
        return properties.getProperty(Strings.tagDataBase);
    }

    /**
     * Recupera do arquivo de configuração o endereço.
     *
     * <p>Endereço em que o banco de dados está rodando.
     *
     * @return endereço do banco de dados.
     */
    private static String getAdressDataBase() {
        Properties properties = getProperties();
        return properties.getProperty(Strings.tagEndereco);
    }

    /**
     * Recupera do arquivo de configuração a porta.
     *
     * <p>Porta em que o banco de dados "escuta".
     *
     * @return porta que o banco de dados "escuta".
     */
    private static Integer getPortDataBase() {
        Properties properties = getProperties();
        return Integer.parseInt(properties.getProperty(Strings.tagPorta));
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
     * Criação da conexão com o banco de dados.
     *
     * <p>Método que é executado uma única vez, na leitura da classe.
     * Teste se a conexão já existe, se existir, retorna está conexão, caso
     * não exista, é criada uma conexão e retornada está nova conexão.
     */
    static {
        try {
            mongoClient = new MongoClient(getAdressDataBase(),getPortDataBase());
            mongoDB = mongoClient.getDatabase(getNameDataBase());
            createCollections(Strings.collectionParecer);
            createCollections(Strings.collectionRadoc);
            createCollections(Strings.collectionResolucao);
            createCollections(Strings.collectionTipo);
        } catch (IOException e) {
            throw new ConexaoSemSucessoException(e.getMessage());
        }
    }

    /**
     * Busca a conexão existente.
     *
     * @return conexão com o banco de dados.
     */
    public static MongoDatabase getConnection() {
        return mongoDB;
    }
}