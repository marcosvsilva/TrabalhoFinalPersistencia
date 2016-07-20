package br.ufg.inf.persistencia.saep.auxiliares;

/**
 * Exceção disparada quando a conexão não foi realizada por algum motivo.
 */
public class ConexaoSemSucessoException extends RuntimeException  {

    public ConexaoSemSucessoException(String message) {
        super(message);
    }
}
