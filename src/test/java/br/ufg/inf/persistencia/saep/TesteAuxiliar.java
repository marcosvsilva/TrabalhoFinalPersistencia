package br.ufg.inf.persistencia.saep;

import br.ufg.inf.persistencia.saep.auxiliares.Strings;
import br.ufg.inf.es.saep.sandbox.dominio.*;
import org.junit.Test;

import java.util.*;

/**
 * Classe auxiliar de teste.
 *
 * <p>Auxilia os testes implementando tudo que há necessario para
 * a realização dos testes.
 */
public class TesteAuxiliar
{

    public Parecer createParecer(String idParecer){
        ArrayList<String> radocs = createStrings();

        List<Pontuacao> pontuacao = createPontuacoes();

        List<Nota> notas = createNotas();

        Parecer parecer = new Parecer(
                idParecer,
                Strings.IDTeste,
                radocs,
                pontuacao,
                Strings.teste,
                notas
        );

        return parecer;
    }

    public Radoc createRadoc(String idRadoc){
        Radoc radoc = new Radoc(
                idRadoc,
                Strings.anoBaseTestRadoc,
                createRelatos()
        );

        return radoc;
    }

    public Resolucao createResolucao(String idResolucao){
        Resolucao resolucao = new Resolucao(
                idResolucao,
                Strings.teste,
                Strings.teste,
                new Date(),
                createRegras()
        );

        return resolucao;
    }

    public Tipo createTipo(String idTipo){
        Tipo tipo = new Tipo(
                idTipo,
                Strings.teste,
                Strings.teste,
                createAtributos()
        );

        return tipo;
    }

    public Set<Atributo> createAtributos() {
        Set<Atributo> atributos = new LinkedHashSet<>();

        Atributo atributo = new Atributo(
                Strings.teste,
                Strings.teste,
                Strings.tipoTesteRegra
        );

        atributos.add(atributo);
        return atributos;
    }

    public List<Regra> createRegras() {
        List<String> dependencias = new ArrayList<>();
        List<Regra> regras = new ArrayList<>();

        for (int i = 0; i < Strings.quantidadeMaximaTeste; i++) {
            dependencias.add(Strings.teste);
        }

        Regra regra = new Regra(
                Strings.variavelTesteRegra,
                Strings.tipoTesteRegra,
                Strings.teste,
                Strings.valorMinimoTesteRegra,
                Strings.valorMaximoTesteRegra,
                Strings.expressaoTesteRegra,
                Strings.entaoTesteRegra,
                Strings.senaoTesteRegra,
                Strings.teste,
                Strings.pontoPorItemTesteRegra,
                dependencias
        );

        for (int i = 0; i < Strings.quantidadeMaximaTeste; i++) {
            regras.add(regra);
        }

        return regras;
    }

    public List<Relato> createRelatos(){
        List<Relato> relatos = new ArrayList<>();

        for (int i = 0; i < Strings.quantidadeMaximaTeste; i++) {
            relatos.add(createRelato());
        }

        return relatos;
    }

    public ArrayList<String> createStrings() {
        ArrayList<String> stringsAleatorias = new ArrayList<>();

        for (int i = 0; i < Strings.quantidadeMaximaTeste; i++) {
            stringsAleatorias.add(Strings.teste);
        }

        return stringsAleatorias;
    }

    public List<Pontuacao> createPontuacoes() {
        List<Pontuacao> pontuacoes = new ArrayList<>();

        for (int i = 0; i < Strings.quantidadeMaximaTeste; i++) {
            pontuacoes.add(new Pontuacao(Strings.teste,
                    new Valor(Strings.teste)));
        }

        return pontuacoes;
    }

    public List<Nota> createNotas() {
        List<Nota> notas = new ArrayList<>();

        for (int i = 0; i < Strings.quantidadeMaximaTeste; i++) {
            notas.add(createNota());
        }

        return notas;
    }

    public Nota createNota() {
        Relato relatoOrigem = createRelato();
        Relato relatoDestino = createRelato();

        Nota nota = new Nota(relatoOrigem,relatoDestino,Strings.teste);

        return nota;
    }

    public Relato createRelato() {
        Map<String,Valor> relato = new HashMap<>();
        relato.put(Strings.teste,new Valor(Strings.teste));
        return new Relato(Strings.teste,relato);
    }
}