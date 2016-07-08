package Interfaces;

import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;

import java.util.List;

/**
 * Created by marco_000 on 05/07/2016.
 */
public class ResolucaoRepositorioIMPL implements ResolucaoRepository
{
    @Override
    public Resolucao byId(String s)
    {
        return null;
    }

    @Override
    public String persiste(Resolucao resolucao)
    {
        return null;
    }

    @Override
    public boolean remove(String s)
    {
        return false;
    }

    @Override
    public List<String> resolucoes()
    {
        return null;
    }

    @Override
    public void persisteTipo(Tipo tipo)
    {

    }

    @Override
    public void removeTipo(String s)
    {

    }

    @Override
    public Tipo tipoPeloCodigo(String s)
    {
        return null;
    }

    @Override
    public List<Tipo> tiposPeloNome(String s)
    {
        return null;
    }
}
