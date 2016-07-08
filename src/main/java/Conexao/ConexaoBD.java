package Conexao;

import Layout.DataBaseSAEP;
import com.google.gson.Gson;

/**
 * Created by Marcos on 05/07/2016.
 */

public abstract class ConexaoBD<param>
{
    /*
    private Gson gson = new Gson();
    private DataBaseSAEP dataBase = new DataBaseSAEP();
    */

    /*
        Criação dos collections relatos, itens, regras e parecer
    */

    /*
    public void createRelatos(Object relatos)
    {
        String jason = gson.toJson(relatos);
        dataBase.create(jason,"relatos");
    }

    public void createItens(Object itens)
    {
        String jason = gson.toJson(itens);
        dataBase.create(jason,"itens_avaliados");
    }

    public void createRegras(Object regras)
    {
        String jason = gson.toJson(regras);
        dataBase.create(jason,"regras");
    }

    public void createParecer(Object parecer)
    {
        String jason = gson.toJson(parecer);
        dataBase.create(jason,"parecer");
    }

    /*
        Busca das collections relatos, itens, regras e parecer
    */

    /*
    public Object readRelatos(Integer ID)
    {
        String gsonRelatos = dataBase.read("relatos",ID);
        Object relatos = gson.fromJson(gsonRelatos,Object.class);
        return relatos;
    }

    public Object readItens(Integer ID)
    {
        String gsonItens = dataBase.read("itens",ID);
        Object itens = gson.fromJson(gsonItens,Object.class);
        return itens;
    }

    public Object readRegras(Integer ID)
    {
        String gsonRegras = dataBase.read("regras",ID);
        Object regras = gson.fromJson(gsonRegras,Object.class);
        return regras;
    }

    public Object readParecer(Integer ID)
    {
        String gsonParecer = dataBase.read("regras",ID);
        Object parecer = gson.fromJson(gsonParecer,Object.class);
        return parecer;
    }

    /*
        Update das collections relatos, itens, regras e parecer
    */

    /*
    public void updateRelatos(Integer ID, Object campo)
    {
        //
    }

    public void updateItens(Integer ID, Object campo)
    {
        //
    }

    public void updateRegras(Integer ID, Object campo)
    {
        //
    }

    public void updateParecer(Integer ID, Object campo)
    {
        //
    }

    /*
        Delete das collections relatos, itens, regras e parecer
    */

    /*
    public void deleteRelatos(Integer ID)
    {
        //
    }

    public void deleteItens(Integer ID)
    {
        //
    }

    public void deleteRegras(Integer ID)
    {
        //
    }

    public void deleteParecer(Integer ID)
    {
        //
    }
    */
}