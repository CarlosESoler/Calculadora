package br.com.carlos.calculadora.visao;

import javax.swing.*;
import java.awt.*;

public class Botao extends JButton {

    public Botao(String texto, Color color) {
        setText(texto);
        setOpaque(true);
        setBackground(color);
        setForeground(Color.white);
        setFont(new Font("courier", Font.PLAIN, 25));
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
}
