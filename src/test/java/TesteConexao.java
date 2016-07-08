import Conexao.InstanciaConexao;
import com.mongodb.client.MongoDatabase;

/*
    Classe para realizar o Padrão Singleton de conexão,
    foi criado 5 conexões e instanciadas pela classe de instância de conexão
    e monitorado.

    Das 5 conexões criadas, o mongoDB permanece ativa somente 1 conexão.
    Teste realizado e concluído com sucesso.
 */

/**
 * Created by marco_000 on 07/07/2016.
 */
public class TesteConexao
{
    private static MongoDatabase mongoDB1; //1ª conexão
    private static MongoDatabase mongoDB2; //2º conexão
    private static MongoDatabase mongoDB3; //3ª conexão
    private static MongoDatabase mongoDB4; //4ª conexão
    private static MongoDatabase mongoDB5; //5ª conexão

    public static void main(String[] args)
    {
        mongoDB1 = InstanciaConexao.getConnection(); //fazendo a instânca da 1ª conexão
        mongoDB2 = InstanciaConexao.getConnection(); //fazendo a instânca da 2ª conexão
        mongoDB3 = InstanciaConexao.getConnection(); //fazendo a instânca da 3ª conexão
        mongoDB4 = InstanciaConexao.getConnection(); //fazendo a instânca da 4ª conexão
        mongoDB5 = InstanciaConexao.getConnection(); //fazendo a instânca da 5ª conexão
    }
}
