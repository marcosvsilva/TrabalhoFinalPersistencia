package Conexao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;

/**
 * Created by Marcos on 07/07/2016.
 */
public class InstanciaConexao
{
    private static MongoClient mongoClient = new MongoClient();
    private static MongoDatabase mongoDB = null;

    private static String getProperties() throws IOException
    {
        Properties properties = new Properties();
        FileInputStream file = new FileInputStream("./Configuracao/config.properties");
        properties.load(file);
        return properties.getProperty("DataBase");
    }

    public synchronized static MongoDatabase getConnection()
    {
        if (mongoDB == null)
        {
            try
            {
                String dataBase = getProperties();
                mongoDB = mongoClient.getDatabase(dataBase);
            } catch(IOException e) {System.out.println(e);}
        }
        return mongoDB;
    }
}
