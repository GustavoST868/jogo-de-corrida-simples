package Windows;

import Jogo.Som;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaInicial extends JFrame {
    public JanelaInicial() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 450);
        setTitle("Car Racing");

        // Carregar a imagem de fundo e definir no JLabel
        JLabel backgroundLabel = new JLabel(new ImageIcon("./src/Img/JanelaInicial/pagina_inicial.png"));
        backgroundLabel.setSize(800, 500);
        setContentPane(backgroundLabel);
        backgroundLabel.setLayout(null);

        // Botão "Jogar"
        JButton button = new JButton("Jogar");
        button.setBounds(350, 320, 150, 50);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(Color.lightGray);

        // Ação ao clicar no botão jogar
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                // Som do botão
                Som som = new Som();
                som.Som("./src/Sons/clique.wav");

                // Abrir a janela de escolha de carros
                EscolherCarros escolherCarros = new EscolherCarros();
                escolherCarros.Jogador1();
            }
        });

        backgroundLabel.add(button);

        setVisible(true);
    }
}
