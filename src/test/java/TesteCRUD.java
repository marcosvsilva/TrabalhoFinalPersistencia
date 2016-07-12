import Auxiliares.Strings;
import Conexao.ConexaoBD;
import com.google.gson.Gson;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;

/*
    Teste da classe CRUD do banco de dados
    Create, Read, Update e Delete
*/

/**
 * Created by Marcos on 08/07/2016.
 */
public class TesteCRUD
{
    private static ConexaoBD conexao = new ConexaoBD();
    private static Gson gson = new Gson();

    static class TestCrud
    {
        private String id;
        private String informacao;


        public TestCrud(String id, String informacao)
        {
            this.id = id;
            this.informacao = informacao;
        }

        public String getId() { return id; }

        public String getInformacao() { return informacao; }

        public void setInformacao(String informacao) { this.informacao = informacao; }

    }

    public static void main(String[] args)
    {

        TestCrud[] testeCrud = new TestCrud[Strings.numeroMaximoTestes];

        for (int i = 0; i < Strings.numeroMaximoTestes; i++)
            testeCrud[i] = new TestCrud(Integer.toString(i), "Teste de Crud");

        // Teste de inserção de informações
        ArrayList<String> json = new ArrayList<>();

        for (int i = 0; i < Strings.numeroMaximoTestes; i++)
            json.add(gson.toJson(testeCrud[i]));

        for (int i = 0; i < json.size(); i++)
            create(json.get(i));

        System.out.println("Informações gravadas no banco");

        // Teste de atualização de informações
        Integer idUpdate = (int) (Math.random() * 5);

        testeCrud[idUpdate].setInformacao("Teste de Crud alterado");
        String jsonUpdate = gson.toJson(testeCrud[idUpdate]);

        update(Strings.ID,Integer.toString(idUpdate),jsonUpdate);

        // Teste leitura das informações uma a uma
        for (int i = 0; i < json.size(); i++)
        {
            TestCrud testCollectionParecer = readParecer(Strings.ID, Integer.toString(i));
            System.out.println("Collection Parecer, Id: " + testCollectionParecer.getId() + ", Informacao: " + testCollectionParecer.getInformacao());
        }

        for (int i = 0; i < json.size(); i++)
        {
            TestCrud testCollectionRadoc = readRadoc(Strings.ID, Integer.toString(i));
            System.out.println("Collection Radoc, Id: " + testCollectionRadoc.getId() + ", Informacao: " + testCollectionRadoc.getInformacao());
        }

        TestCrud[] testeCrudPareceAll = readParecerAll();
        TestCrud[] testeCrudRadocAll = readRadocAll();

        for (int i = 0; i < Strings.numeroMaximoTestes; i++)
            System.out.println("Collection Parecer, Id: " + testeCrudPareceAll[i].getId() + ", Informacao: " + testeCrudPareceAll[i].getInformacao());

        for (int i = 0; i < Strings.numeroMaximoTestes; i++)
            System.out.println("Collection Radoc, Id: " + testeCrudRadocAll[i].getId() + ", Informacao: " + testeCrudRadocAll[i].getInformacao());
    }

    private static void create(String json)
    {
        conexao.create(json, Strings.collectionParecer);
        conexao.create(json, Strings.collectionRadoc);
    }

    private static void update(String filtro, String key, String json)
    {
        conexao.update(filtro, key, json, Strings.collectionParecer);
        conexao.update(filtro, key, json, Strings.collectionRadoc);
    }

    private static TestCrud[] readParecerAll()
    {
        Iterable<Document> parecerAllDocument = conexao.readAll(Strings.collectionParecer);
        TestCrud testCrud[] = new TestCrud[Strings.numeroMaximoTestes];

        int i = 0;
        for (Document parecerDocument : parecerAllDocument)
        {
            String json = gson.toJson(parecerDocument);
            testCrud[i] = gson.fromJson(json ,TestCrud.class);
            i++;
        }

        return testCrud;
    }

    private static TestCrud readParecer(String filtro, String key)
    {
        Document parecerDocument = conexao.read(filtro, key, Strings.collectionParecer);
        TestCrud parecerObjeto = gson.fromJson(parecerDocument.toJson(), TestCrud.class);
        return parecerObjeto;
    }

    private static TestCrud[] readRadocAll()
    {
        Iterable<Document> radocDocumentAll = conexao.readAll(Strings.collectionRadoc);
        TestCrud testCrud[] = new TestCrud[Strings.numeroMaximoTestes];

        int i = 0;
        for (Document radocDocument : radocDocumentAll)
        {
            String json = gson.toJson(radocDocument);
            testCrud[i] = gson.fromJson(json ,TestCrud.class);
            i++;
        }

        return testCrud;
    }

    private static TestCrud readRadoc(String filtro, String key)
    {
        Document radocDocument = conexao.read(Strings.ID, key, Strings.collectionRadoc);
        TestCrud radocObjeto = gson.fromJson(radocDocument.toJson(), TestCrud.class);
        return radocObjeto;
    }

    private static void delete(String filtro, String key)
    {
        conexao.delete(filtro, key, Strings.collectionParecer);
        conexao.delete(filtro, key, Strings.collectionRadoc);
    }
}