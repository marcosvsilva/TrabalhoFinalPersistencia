import Conexao.ConexaoBD;
import com.google.gson.Gson;
import org.bson.Document;

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

        TesteCRUD.TestCrud testCreate = new TesteCRUD.TestCrud("1","Teste de CRUD");

        // Teste de inserção de informações
        String json = gson.toJson(testCreate);
        create(json);
        System.out.println("Informações gravadas no banco");
        System.out.println("id = " + testCreate.getId());
        System.out.println("informacao = " + testCreate.getInformacao());

        // Teste de atualização de informações
        testCreate.setInformacao("Teste de CRUD alterado");
        json = gson.toJson(testCreate);
        update("id","1",json);
        System.out.println("Informações alteradas");

        TestCrud testCollectionParecer = readParecer("id","1");
        TestCrud testCollectionRadoc = readRadoc("id","1");

        System.out.println("Informacoes recuperadas da collecion Parecer");
        System.out.println("id = " + testCollectionParecer.getId());
        System.out.println("informacao = " + testCollectionParecer.getInformacao());

        System.out.println("Informacoes recuperadas da collecion Radoc");
        System.out.println("id = " + testCollectionRadoc.getId());
        System.out.println("informacao = " + testCollectionRadoc.getInformacao());

        delete("id","1");
    }

    private static void create(String json)
    {
        conexao.create(json,"parecer");
        conexao.create(json,"radoc");
    }

    private static void update(String filtro, String key, String json)
    {
        conexao.update(filtro,key,json,"parecer");
        conexao.update(filtro,key,json,"radoc");
    }

    private static TestCrud readParecer(String filtro, String key)
    {
        Document parecerDocument = conexao.read("id","1","parecer");
        TestCrud parecerObjeto = gson.fromJson(parecerDocument.toJson(), TestCrud.class);
        return parecerObjeto;
    }

    private static TestCrud readRadoc(String filtro, String key)
    {
        Document radocDocument = conexao.read("id","1","radoc");
        TestCrud radocObjeto = gson.fromJson(radocDocument.toJson(), TestCrud.class);
        return radocObjeto;
    }

    private static void delete(String filtro, String key)
    {
        conexao.delete(filtro,key,"parecer");
        conexao.delete(filtro,key,"radoc");
    }
}