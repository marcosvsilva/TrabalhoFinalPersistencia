import Auxiliares.Strings;
import Conexao.ConexaoBD;
import com.google.gson.Gson;
import org.bson.Document;

import java.util.ArrayList;
import java.util.regex.Pattern;

/*
    Teste da classe CRUD do banco de dados
    Create, Read, Update e Delete

    Testes realizados sob todos métodos da classe de conexão com o banco de dados
    são elas: create, read, readAll, readAllByFilter, update e delete

    Foi instânciada uma classe de teste, denomidada TestCrud, onde possúi 2 atributos, id e informação e métodos
    get's e set's para esses atributos

    O teste consistia em cria vários objetos desta classe, realizar a gravação, alteração, leitura e deleção dessas
    informações utilizando a classe de acesso ao banco ConexãoDB, para certificar seu perfeito funcionamento.

    Com base nos testes, os resultados foram significativos, certificando a classe de Cenexão apta a aplicação.
*/

/**
 * Created by Marcos on 08/07/2016.
 */
public class TesteCRUD
{
    private static ConexaoBD conexao = new ConexaoBD(); //conexão com o banco de dados
    private static Gson gson = new Gson();              //gson para manipulação de Strings json

    private static String informacao = "Teste de CRUD";                     //informações a serem armazandas
    private static String informacaoAlterada = "Teste de CRUD Alterado";    //informação a ser alterada
    private static String collection = Strings.collectionTipo;           //collection onde será realizada os testes
    private static Integer numeroObjetosTestes = 5;                          //numero máximo de objetos a ser instanciado para testes


    /*
        Classe que viabilizara o teste
        id : identificador do objeto
        informação : informação a ser armazanda pelo objeto
    */
    static class TestCrud
    {
        private String id;
        private String informacao;


        public TestCrud(String id, String informacao)
        {
            this.id = id;
            this.informacao = informacao;
        }

        public String getId() { return id; } //metódos gets e set's

        public String getInformacao() { return informacao; } ////metódos gets e set's

        public void setInformacao(String informacao) { this.informacao = informacao; } ////metódos gets e set's

    }

    /*
        Método main que realizará todos os testes
    */

    public static void main(String[] args)
    {

        // Criação dos objetos a serem armazenados no banco
        TestCrud[] testeCrud = new TestCrud[Strings.numeroObjetosTestes];

        for (int i = 0; i < Strings.numeroObjetosTestes; i++)
            testeCrud[i] = new TestCrud(Integer.toString(i), informacao);

        // Teste de inserção dos objetos
        ArrayList<String> json = new ArrayList<>();

        for (int i = 0; i < Strings.numeroObjetosTestes; i++)
            json.add(gson.toJson(testeCrud[i]));

        for (int i = 0; i < json.size(); i++)
            create(json.get(i));

        System.out.println("Informações gravadas no banco");

        // Teste de atualização de informações
        Integer idUpdate = (int) (Math.random() * 5); //Escolhendo aleatoriamente o objeto a ser atualizado

        testeCrud[idUpdate].setInformacao(informacaoAlterada);
        String jsonUpdate = gson.toJson(testeCrud[idUpdate]);

        update(Strings.ID,Integer.toString(idUpdate),jsonUpdate);

        // Teste leitura das informações uma a uma
        for (int i = 0; i < json.size(); i++)
        {
            TestCrud testeReadOneToOne = read(Strings.ID, Integer.toString(i));
            System.out.println("Collection " + collection + ", leitura única, Id: " + testeReadOneToOne.getId() + ", Informacao: " + testeReadOneToOne.getInformacao());
        }

        // Teste leitura de todas informações em somente uma leitura
        ArrayList<TestCrud> testeCrudReadAll = readAll();

        for (int i = 0; i < testeCrudReadAll.size(); i++)
            System.out.println("Collection " + collection + ", leitura de todos elementos da collection, Id: " + testeCrudReadAll.get(i).getId() + ", Informacao: " + testeCrudReadAll.get(i).getInformacao());

        // Teste leitura de todas informações que contêm a String informacaoAlterada
        ArrayList<TestCrud> testeCrudPareceNomeAll = readAllByName(informacaoAlterada);

        for (int i = 0; i < testeCrudPareceNomeAll.size(); i++)
            System.out.println("Collection " + collection + ", leitura dos elementos que contêm Teste de Crud alterado, Id: " + testeCrudPareceNomeAll.get(i).getId() + ", Informacao: " + testeCrudPareceNomeAll.get(i).getInformacao());

        // Deleção de todas informações que constam no banco de dados
        for (int i = 0; i < Strings.numeroObjetosTestes; i++)
            delete(Strings.ID,Integer.toString(i));
    }

    /*
        Método que realiza a gravação dos objetos no banco uma a uma
        Recebe como parâmetro uma String em formato jSon para persistência no banco e também
        (em variável estática) a collection em que fará a persistência dos objetos.
    */
    private static void create(String json) { conexao.create(json, collection); }

    /*
        Método que realiza atualização de um objeto do banco de dados
        Recebe como parâmetro um filtro e uma key, para que seja localizado o objeto de interesse que será atualizado.
        Exemplo "id(filtro) = 1(key)"
        Também recebe uma string json que substituíra o objeto que satisfaz o critério de filtro e também
        (em variável estática) a collection que ferá a atualização.
    */
    private static void update(String filtro, String key, String json) { conexao.update(filtro, key, json, collection); }


    /*
        Método que realiza a leitura de todas as informações da collection que está sendo trabalhada.
        Recebe como parâmetro (em variável estática) a collection que ferá a leitura.
    */
    private static ArrayList<TestCrud> readAll()
    {
        Iterable<Document> radocDocumentAll = conexao.readAll(collection);
        ArrayList<TestCrud> testCrud = new ArrayList<>();

        for (Document radocDocument : radocDocumentAll)
        {
            String json = gson.toJson(radocDocument);
            testCrud.add(gson.fromJson(json ,TestCrud.class));
        }

        return testCrud;
    }

    /*
        Método que realiza a leitura de todas as informações da collection que satisfaz um filtro predefinido.
        O filtro é recebido como parâmetro em documento, este documento contém um filtro e uma chave, por exemplo
        "nome = Marcos", e também recebe (em variável estática) a collection que ferá a leitura.
    */
    private static ArrayList<TestCrud> readAllByName(String key)
    {
        Document filter = new Document("informacao", Pattern.compile(key));
        Iterable<Document> radocAllDocument = conexao.readAllByFilter(filter,collection);
        ArrayList<TestCrud> testCrud = new ArrayList<>();

        for (Document radocDocument : radocAllDocument)
        {
            String json = gson.toJson(radocDocument);
            testCrud.add(gson.fromJson(json ,TestCrud.class));
        }

        return testCrud;
    }

    /*
        Método que realiza a leitura de uma informação da collection que satisfaz um filtro passado como parâmetro.
        Para o filtro o método recebe uma string filtro e uma string chave para realizar o filtro, por exemplo
        "nome = Marcos", e também recebe (em variável estática) a collection em que fará a atualização.
    */
    private static TestCrud read(String filtro, String key)
    {
        Document radocDocument = conexao.read(filtro, key, collection);
        TestCrud radocObjeto = gson.fromJson(radocDocument.toJson(), TestCrud.class);
        return radocObjeto;
    }

    /*
        Método que realiza a deleção de uma informação que satisfaz um filtro.
        Para o filtro o método recebe uma string filtro e uma string chave para realizar o filtro, por exemplo
        "nome = Marcos", e também recebe (em variável estática) a collection em que fará a deleção.
    */
    private static void delete(String filtro, String key) { conexao.delete(filtro, key, collection); }
}