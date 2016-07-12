package Conexao;

import Auxiliares.Strings;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;

/*
    Essa classe implementa as boas práticas de singleton
    fazendo somente uma conexão com o banco de dados para toda aplicação.

    Está classe além de criar a conexão única, também usa o arquivo config.properties para saber o nome
    do banco a qual a aplicação trabalhará. Logo, para alterar o banco de dados não é necessário alteração do
    código fonte da aplicação, comente mudar o parâmetro DataBase do arquivo de configuração.

    Além da conexão única essa classe também cria as 2 collections que serão utilizadas por está, que são
    a collection parecer e collection  radoc, caso as duas collectons já existam, não há efeitos colateris.

    Para criação das collections a aplicação testa se existe dados nas collections de interesse, se não houver,
    a aplicação cria deleta a collections e cria uma nova collections.
 */


/**
 * Created by Marcos on 07/07/2016.
 */
public class InstanciaConexao
{
    private static MongoClient mongoClient = new MongoClient();
    private static MongoDatabase mongoDB = null;

    /*
        Faz a leitura do arquivo de configuração passando como retorno o nome do banco
        setado no arquivo de configuração.
    */
    private static String getProperties() throws IOException
    {
        Properties properties = new Properties();
        FileInputStream file = new FileInputStream(Strings.enderecoConfiguracao);
        properties.load(file);
        return properties.getProperty(Strings.tagDataBase);
    }

    /*
        Cria a collecion de nome passado no parâmetro da função, testando se a collection existe dados.
        Se existir ela permanece sem alteração.
        Caso não exista, ela é deletada e criada novamente.
    */
    private static void createCollections(String collectionName) throws IOException
    {
        MongoCollection<Document> testaExistencia = mongoDB.getCollection(collectionName);
        if (testaExistencia.find().first() == null)
        {
            mongoDB.getCollection(collectionName).drop();
            mongoDB.createCollection(collectionName);
        }
    }

    public synchronized static MongoDatabase getConnection()
    {
        /*
            Confere se a conexão já existe, se existir simplismente retorna a conexão.
            Se não existir cria a conexão.
        */
        if (mongoDB == null)
        {
            try
            {
                String dataBase = getProperties();                  //busca do nome do banco
                mongoDB = mongoClient.getDatabase(dataBase);        //criação da conexão
                createCollections(Strings.collectionParecer);       //cria a collection parecer
                createCollections(Strings.collectionRadoc);         //cria a collection radoc
                createCollections(Strings.collectionResolucao);     //cria a collection resolucao
                createCollections(Strings.collectionTipo);          //cria a collection tipo
            } catch(IOException e) {System.out.println(e);}         //caso houver falhas, imprimi a falha.
        }
        return mongoDB;                                             //retorno da conexão se já criada.
    }
}