package br.ufg.inf.persistencia.saep.auxiliares;

/**
 * Classe auxiliar que contêm as strings utilizadas na aplicação.
 *
 * <p>Contêm todas strings fixas que são utilizadas pela aplicação.
 */
public class Strings
{

    /**
     * Endereço do arquivo de configuração.
     *
     * <p>Endereço do arquivo de configuração que contêm o
     * nome do banco de dados a ser utilizado na aplicação.
     */
    public static final String enderecoConfiguracao =
            "./Configuracao/config.properties";

    /**
     * Tag do banco de dados no arquivo de configuração.
     *
     * <p>Tag que identifica o atributo que se refere ao nome
     * do banco de dados que será utilizado na aplicação dentro
     * do arquivo de configuração.
     */
    public static final String tagDataBase = "DataBase";

    /**
     * Nome da collection parecer.
     */
    public static final String collectionParecer = "parecer";

    /**
     * Nome da collection radoc.
     */
    public static final String collectionRadoc = "radoc";

    /**
     * Nome da collectin resolução.
     */
    public static final String collectionResolucao = "resolucao";

    /**
     * Nome da collection tipo.
     */
    public static final String collectionTipo = "tipo";

    /**
     * Filtro Identificador.
     *
     * <p>Filtro para identificar documentos que serão armazenados
     * no banco de dados.
     */
    public static final String ID = "id";

    /**
     * Filtro para busca de Radocs.
     *
     * <p>Filtro para buscar radocs dentro da collection parecer.
     */
    public static final String filterRadoc = "radocs";

    /**
     * Filtro para busca de regras.
     *
     * <p>Filtro para buscar regras dentro da collection resolução.
     */
    public static final String filterRegra = "regras.tipoRelato";

    /**
     * Filtro nome.
     *
     * <p>Filtro nome de um documento no banco de dados.
     */
    public static final String filterNome = "nome";

    /**
     * Identificador para testes.
     *
     * <p>Identificador utilizados para gravação, atualização
     * e busca no banco de dados em casos de testes.
     */
    public static final String IDTeste = "0001";

    /**
     * Mensagem de teste.
     *
     * <p>Mensagem de teste a ser utilizada para preencher objetos
     * a critério de teste.
     */
    public static final String teste = "teste";

    /**
     * Numero de objetos para teste.
     *
     * <p>Número de documentos que serão armazenados no banco de dados
     * a modo de teste.
     */
    public static final Integer quantidadeMaximaTeste = 2;

    /**
     * Ano base para teste de radoc
     */
    public static final Integer anoBaseTestRadoc = 2010;
}