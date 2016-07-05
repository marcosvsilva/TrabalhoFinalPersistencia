package Layout;


import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.async.client.MongoDatabase;
import org.bson.BsonString;
import org.bson.Document;

/**
 * Created by marco_000 on 05/07/2016.
 */
public class DataBaseSAEP
{
    private MongoClient dataBaseClient = (MongoClient) new Object();
    private MongoDatabase dataBase = dataBaseClient.getDatabase("SAEP");

    public void create(String nomeDoc, String collection)
    {
        Document docCreate = new Document(nomeDoc,null)
        dataBase.getCollection(collection).insertOne(docCreate,null);
    }

    public String read(String nomeCollection, Integer ID)
    {
        BsonString readDoc = dataBase.getCollection(nomeCollection).findOne("_id"+ID,1);
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
        DBCollection collection = (DBCollection) dataBase.getCollection(nomeCollection);
        DBObject deleteDoc = collection.findOne(eq("id",ID));
        collection.remove(deleteDoc);
    }
}
