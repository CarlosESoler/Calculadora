package br.com.carlos.calculadora.visao;

import javax.swing.*;
import java.awt.*;

public class Calculadora extends JFrame {
    public Calculadora() {
        organizarLayout();
        setVisible(true);
        setTitle("Calculadora");
        setSize(232, 322);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void organizarLayout() {
        setLayout(new BorderLayout());

        Display display = new Display();
        add(display, BorderLayout.NORTH);
        display.setPreferredSize(new Dimension(236, 60));

        Teclado teclado = new Teclado();
        add(teclado, BorderLayout.CENTER);
    }
    public static void main(String[] args) {
        new Calculadora();
    }
}
