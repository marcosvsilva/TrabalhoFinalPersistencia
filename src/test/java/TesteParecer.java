import Auxiliares.Strings;
import Conexao.ConexaoBD;
import Interfaces.ParecerRepositorioIMPL;
import br.ufg.inf.es.saep.sandbox.dominio.Nota;
import br.ufg.inf.es.saep.sandbox.dominio.Parecer;
import br.ufg.inf.es.saep.sandbox.dominio.ParecerRepository;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Marcos on 05/07/2016.
 */
public class TesteParecer
{
    private static ParecerRepository parecerRepository; //interface parecer para realizar os testes
    private static ConexaoBD conexao = new ConexaoBD(); //conexão com o banco de dados
    private static TesteAuxiliar auxiliar = new TesteAuxiliar();

    @BeforeClass
    public static void setup() { parecerRepository = new ParecerRepositorioIMPL(conexao); }

    @Test
    public void adicionaParecer()
    {
        Parecer parecer = auxiliar.createParecerAleatorio();
        parecerRepository.persisteParecer(parecer);
    }

    @Test
    public void adicionaNota()
    {
        Nota nota = auxiliar.createNotaAleatoria();
        parecerRepository.adicionaNota(Strings.IDTeste,nota);
    }

    @Test
    public void removeNora()
    {
        Nota nota = auxiliar.createNotaAleatoria();
        parecerRepository.removeNota(Strings.IDTeste,nota.getItemOriginal());
    }
}