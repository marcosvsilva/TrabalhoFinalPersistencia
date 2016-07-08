package Layout;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Created by marco_000 on 05/07/2016.
 */
public class DataBaseSAEP
{
    private MongoClient dataBaseClient = (MongoClient) new Object();
    //private MongoDatabase dataBase = (MongoDatabase) dataBaseClient.getDatabaseNames("SAEP");

    /*
    CRUDE do banco de dados
    */

    /*
    public void create(String json, String collection)
    {
        Document document = new Document("document",json);
        dataBase.getCollection(collection).insertOne(document,null);
    }

    public String read(String nomeCollection, Integer ID)
    {
        BonString readDoc = dataBase.getCollection(nomeCollection).findOne("_id"+ID,1);
        String result = readDoc.toString();
        return result;
    }

    public void update(String nomeCollection, Integer ID, String arg)
    {
        DBCollection collection = (DBCollection) dataBase.getCollection(nomeCollection);
        DBObject updateDoc = collection.findOne(eq("id",ID));
        updateDoc.put(arg);
        collection.update(updateDoc);
    }

    public void delete(String nomeCollection, Integer ID)
    {
        Document filter = new Document("id",ID);
        dataBase.getCollection(nomeCollection).findOneAndDelete(filter);
    }
    */
}