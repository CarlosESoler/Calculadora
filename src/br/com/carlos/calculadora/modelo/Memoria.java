package br.com.carlos.calculadora.modelo;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
    private enum TipoComando {
        ZERAR, NUMERO, DIVISAO, MULTIPLICACAO, SOMA, SUBTRACAO, IGUAL, VIRGULA, ALTERAROPERACAO;
    }
    private static final Memoria instancia = new Memoria();
    private String textoAtual = "";
    private String textoBuffer = "";
    private boolean substituir = false;
    private TipoComando ultimaOperacao = null;

    private final List<MemoriaObservador> observadorList = new ArrayList<>();

    private Memoria() {
    }
    public static Memoria getInstancia() {
        return instancia;
    }
    public void adicionarObservador(MemoriaObservador o){
        observadorList.add(o);
    }
    public String getTextoAtual() {
        return textoAtual.isEmpty() ? "0" : textoAtual;
    }

    public void processarComando(String valor) {
        TipoComando tipoComando = detectarTipoComando(valor);
        if(tipoComando == null) {
            return;
        }
        else if(tipoComando == TipoComando.ALTERAROPERACAO && textoAtual.contains("-")) {
            textoAtual = textoAtual.substring(1);
        }
        else if(tipoComando == TipoComando.ALTERAROPERACAO && !textoAtual.contains("-")) {
            textoAtual = "-" + textoAtual;
        }
        else if(tipoComando == TipoComando.ZERAR) {
            textoAtual = "";
            textoBuffer = "";
            substituir = false;
            ultimaOperacao = null;
        }

        else if(tipoComando == TipoComando.NUMERO || tipoComando == TipoComando.VIRGULA) {
            textoAtual = substituir ? valor : textoAtual + valor;
            substituir = false;
        }
        else {
            substituir = true;
            textoAtual = obterResultadoOperacao();

            textoBuffer = textoAtual;
            ultimaOperacao = tipoComando;
        }
        observadorList.forEach(o -> o.valorAlterado(getTextoAtual()));
    }

    private String obterResultadoOperacao() {
        if(ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL) {
         return textoAtual;
        }
        double numeroBuffer = Double.parseDouble(textoBuffer.replace("," , "."));

        double numeroAtual = Double.parseDouble(textoAtual.replace("," , "."));

        double resultado = 0;

        if(ultimaOperacao == TipoComando.SOMA) {
            resultado = numeroBuffer + numeroAtual;
        }
        else if(ultimaOperacao == TipoComando.SUBTRACAO) {
            resultado = numeroBuffer - numeroAtual;
        }
        else if(ultimaOperacao == TipoComando.MULTIPLICACAO) {
            resultado = numeroBuffer * numeroAtual;
        }
        else if(ultimaOperacao == TipoComando.DIVISAO) {
            resultado = numeroBuffer / numeroAtual;
        }
        String resultadoTexto = Double.toString(resultado).replace("." , ",");

        boolean resultadoInteiro = resultadoTexto.endsWith(",0");
        return resultadoInteiro ? resultadoTexto.replace(",0" , "") : resultadoTexto;
    }
    private TipoComando detectarTipoComando(String texto) {
        try {
            Integer.parseInt(texto);
            return TipoComando.NUMERO;
        }
        catch (NumberFormatException e) {
            if(texto.equals("AC")) {
                return TipoComando.ZERAR;
            }
            else if("/".equals(texto)) {
                return TipoComando.DIVISAO;
            }
            else if("*".equals(texto)) {
                return TipoComando.MULTIPLICACAO;
            }
            else if("+".equals(texto)) {
                return TipoComando.SOMA;
            }
            else if("-".equals(texto)) {
                return TipoComando.SUBTRACAO;
            }
            else if("=".equals(texto)) {
                return TipoComando.IGUAL;
            }
            else if("+/-".equals(texto)) {
                return TipoComando.ALTERAROPERACAO;
            }
            else if(",".equals(texto) && !textoAtual.contains(",")) {
                return TipoComando.VIRGULA;
            }
            return null;
        }
    }
}
