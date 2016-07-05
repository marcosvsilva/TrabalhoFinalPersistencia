package Conexao;

import Layout.DataBaseSAEP;
import com.google.gson.Gson;

/**
 * Created by Marcos on 05/07/2016.
 */

public abstract class ConexaoBD<param>
{
    private Gson gson = new Gson();
    private DataBaseSAEP dataBase = new DataBaseSAEP();

    public void cadastrarGrupoRelatos(Object relatos)
    {
        //
    }

    public void cadastrarItemAvaliado(Object itens)
    {
        //
    }

    public void cadastrarRegra(Object regra)
    {
        //
    }

    public void cadastrarParecer(Object parecer)
    {
        //
    }

    public Object buscarGrupoRelatos(Integer id) {
        //
    }

    public Object buscarItemAvaliado(Integer id)
    {
        //
    }

    public Object buscarRegra(Integer id)
    {
        //
    }

    public Object buscarParecer(Integer id)
    {
        //
    }

    public void editarGrupoRelatos(Integer id, Object campo)
    {
        //
    }

    public void editarItemAvaliado(Integer id, Object campo)
    {
        //
    }

    public void editarRegra(Integer id, Object campo)
    {
        //
    }

    public void editarParecer(Integer id, Object campo)
    {
        //
    }

    public void excluirGrupoRelatos(Integer id)
    {
        //
    }

    public void excluirItemAvaliado(Integer id)
    {
        //
    }

    public void excluirRegra(Integer id)
    {
        //
    }

    public void excluirParecer(Integer id)
    {
        //
    }
}