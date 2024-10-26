package Windows;

import Jogo.Janela;
import Jogo.Som;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class EscolherCarros {

    //vetor para guardar os carros escolhidos pelos jogadores
    private int[] escolha = new int[2];

    public EscolherCarros(){
    }

    //janela para que o jogador um escolha o carro
    public void Jogador1(){

        //componentes
        JFrame janelaJogador1 = new JFrame();
        JPanel panel = new JPanel();
        JLabel label = new JLabel();

        //vetor para guardar as referencias das locais das imagens nos diretorios
        String[] referenciaImagens = new String[]{
                "./src/Img/Jogador1/carro_verde.png",
                "./src/Img/Jogador1/carro_azul.png",
                "./src/Img/Jogador1/carro_amarelo.png"
        };

        int[] index = {0};

        //configurando a janela
        janelaJogador1.setTitle("Escolher Carros");
        //parar quando fechar a janela
        janelaJogador1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        janelaJogador1.setSize(800,500);
        //definindo a estrtura da janela
        janelaJogador1.setLayout(new BorderLayout());

        //configurando o painel
        //atribuindo um estrutura a janela
        panel.setLayout(new BorderLayout());
        //centralizando a imagem
        label.setVerticalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        AtualizarImagem(label,referenciaImagens,index[0]);

        //botao de carro/imagem anterior
        JButton anterior = new JButton("Anterior");
        anterior.setBackground(Color.white);
        anterior.setBorderPainted(false);
        anterior.setFocusPainted(false);
        anterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //som do botao
                Som som = new Som();
                som.Som("./src/Sons/anterior_proximo.wav");

                if(index[0]==0){}else{
                    index[0]--;
                }
                //atualiza a imagem do label
                AtualizarImagem(label,referenciaImagens,index[0]);
            }
        });

        //botao de carro/imagem posterior
        JButton proximo = new JButton("Próximo");
        proximo.setBackground(Color.white);
        proximo.setBorderPainted(false);
        proximo.setFocusPainted(false);
        proximo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //som do botao
                Som som = new Som();
                som.Som("./src/Sons/anterior_proximo.wav");

                if(index[0]==2){}else{
                    index[0]++;
                }
                //atualiza a imagem do label
                AtualizarImagem(label,referenciaImagens,index[0]);
            }
        });

        //botao de carro/imagem posterior
        JButton escolher = new JButton("Escolher");
        escolher.setBorderPainted(false);
        escolher.setFocusPainted(false);
        escolher.setBackground(Color.white);
        escolher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //som do botao
                Som som = new Som();
                som.Som("./src/Sons/anterior_proximo.wav");

                escolha[0] = index[0];
                janelaJogador1.dispose();

                // apagar o registro antigo
                String caminhoArquivo = "carroJogador1.txt"; // Nome do arquivo

                try (FileWriter writer = new FileWriter(caminhoArquivo, false)) {
                    // O modo "false" sobrescreve o arquivo, apagando seu conteúdo
                    writer.write(""); // Escreve uma string vazia para apagar o conteúdo
                    System.out.println("Conteúdo do arquivo apagado com sucesso.");
                } catch (Exception exception) {
                    System.err.println("Ocorreu um erro ao apagar o conteúdo do arquivo: ");
                }

                //salvar em arquivo a escolha do jogador 1
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("carroJogador1.txt"))) {
                    writer.write(String.valueOf(escolha[0]));
                    System.out.println("salvo com sucesso");
                } catch (Exception exception) {
                    System.err.println("Ocorreu um erro ao salvar o arquivo: ");
                }

                Jogador2();
            }
        });

        //colocando os componentes na janela
        //adicionando a imagem no centro
        panel.add(label,BorderLayout.CENTER);

        //criando um painel para os botoes
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(anterior);
        painelBotoes.add(escolher);
        painelBotoes.add(proximo);

        //colocar o painel de botoes no final da pagina
        panel.add(painelBotoes,BorderLayout.SOUTH);

        janelaJogador1.add(panel);
        janelaJogador1.setVisible(true);



    }



    public void AtualizarImagem(JLabel label,String[] referenciasImagen,int indice){
        //cria a imagem
        ImageIcon imageIcon = new ImageIcon(referenciasImagen[indice]);
        //define imagem do icon
        label.setIcon(imageIcon);
        //repinta na tela
        label.repaint();
    }



    //janela para que o jogador dois escolha o carro
    public void Jogador2(){
        //componentes
        JFrame janelaJogador2 = new JFrame();
        JPanel panel = new JPanel();
        JLabel label = new JLabel();

        //vetor para guardar as referencias das locais das imagens nos diretorios
        String[] referenciaImagens = new String[]{
                "./src/Img/Jogador2/carro_verde.png",
                "./src/Img/Jogador2/carro_azul.png",
                "./src/Img/Jogador2/carro_amarelo.png"
        };

        int[] index = {0};

        //configurando a janela
        janelaJogador2.setTitle("Escolher Carros");
        //parar quando fechar a janela
        janelaJogador2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        janelaJogador2.setSize(800,500);
        //definindo a estrtura da janela
        janelaJogador2.setLayout(new BorderLayout());

        //configurando o painel
        //atribuindo um estrutura a janela
        panel.setLayout(new BorderLayout());
        //centralizando a imagem
        label.setVerticalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        AtualizarImagem(label,referenciaImagens,index[0]);

        //botao de carro/imagem anterior
        JButton anterior = new JButton("Anterior");
        anterior.setBorderPainted(false);
        anterior.setFocusPainted(false);
        anterior.setBackground(Color.white);
        anterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //som do botao
                Som som = new Som();
                som.Som("./src/Sons/anterior_proximo.wav");

                if(index[0]==0){}else{
                    index[0]--;
                }
                //atualiza a imagem do label
                AtualizarImagem(label,referenciaImagens,index[0]);
            }
        });

        //botao de carro/imagem posterior
        JButton proximo = new JButton("Próximo");
        proximo.setBackground(Color.white);
        proximo.setBorderPainted(false);
        proximo.setFocusPainted(false);
        proximo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //som do botao
                Som som = new Som();
                som.Som("./src/Sons/anterior_proximo.wav");

                if(index[0]==2){}else{
                    index[0]++;
                }
                //atualiza a imagem do label
                AtualizarImagem(label,referenciaImagens,index[0]);
            }
        });

        //botao de carro/imagem posterior
        JButton escolher = new JButton("Escolher");
        escolher.setBackground(Color.white);
        escolher.setBorderPainted(false);
        escolher.setFocusPainted(false);
        escolher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //som do botao
                Som som = new Som();
                som.Som("./src/Sons/anterior_proximo.wav");

                escolha[0] = index[0];
                System.out.println("Jogador 2 escolheu o carro >"+escolha[0]);
                janelaJogador2.dispose();


                //trecho para apagar o registro antigo
                String caminhoArquivo = "carroJogador2.txt"; // Nome do arquivo

                try (FileWriter writer = new FileWriter(caminhoArquivo, false)) {
                    // O modo "false" sobrescreve o arquivo, apagando seu conteúdo
                    writer.write(""); // Escreve uma string vazia para apagar o conteúdo
                    System.out.println("Conteúdo do arquivo apagado com sucesso.");
                } catch (Exception exception) {
                    System.err.println("Ocorreu um erro ao apagar o conteúdo do arquivo: ");
                }

                //trecho para salvar em arquivo a escolha do jogador 2
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("carroJogador2.txt"))) {
                    writer.write(String.valueOf(escolha[0]));
                    System.out.println("salvo com sucesso");
                } catch (Exception exception) {
                    System.err.println("Ocorreu um erro ao salvar o arquivo: ");
                }


                Janela janela = new Janela();
            }
        });

        //colocando os componentes na janela
        //adicionando a imagem no centro
        panel.add(label,BorderLayout.CENTER);

        //criando um painel para os botoes
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(anterior);
        painelBotoes.add(escolher);
        painelBotoes.add(proximo);

        //colocar o painel de botoes no final da pagina
        panel.add(painelBotoes,BorderLayout.SOUTH);

        janelaJogador2.add(panel);
        janelaJogador2.setVisible(true);

    }
}
