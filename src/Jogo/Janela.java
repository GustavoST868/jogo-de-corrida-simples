package Jogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;

public class Janela extends JFrame implements KeyListener {

    //coordenadas da posicao
    private int x1 = 100, y1 = 50;
    private int x2 = 100, y2 = 120;

    //controle de voltas dos jogadores
    int numeroVoltasJogador1 = 0;
    int numeroVoltasJogador2 = 0;

    //variaveis para o controle do precionar das teclas
    private boolean wPressed, aPressed, sPressed, dPressed;
    private boolean upPressed, downPressed, leftPressed, rightPressed;

    //usar um buffer de imagens para que o video pareca mais fluido
    private BufferedImage backgroundImage;
    private BufferedImage offscreenImage;

    //variavel para salvar o carros que cada jogador escolheu
    private int carroJogador1;
    private int carroJogador2;

    // imagens do personagem 1
    private Image personagem1Up;
    private Image personagem1Left;
    private Image personagem1Down;
    private Image personagem1Right;

    // imagens do personagem 2
    private Image personagem2Up;
    private Image personagem2Left;
    private Image personagem2Down;
    private Image personagem2Right;

    // imagem atual dos personagens, que muda para que apareca na tela
    private Image personagem1Atual;
    private Image personagem2Atual;

    // variaveis que controlam a velocidade e a aceleracao do carros na saida e nas colisoes
    private float velocidade = 0;
    private float velocidadeMaxima = 7;
    private float incremento = 0.3f;
    private boolean aumentarVelocidade = true;
    private Thread threadVelocidade;

    ImageIcon backgroundIcon = new ImageIcon("./src/Img/Pista/pista.png");

    //comecar a tocar a musica de fundo
    Som som = new Som();

    public Janela() {
        // Som de fundo do jogo

        som.Som("./src/Sons/musicaFundo.wav");

        // configurações básicas da janela
        setSize(1100, 700);
        setTitle("Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // carregar a imagem de fundo

        backgroundImage = new BufferedImage(backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = backgroundImage.getGraphics();
        g.drawImage(backgroundIcon.getImage(), 0, 0, null);

        // Ler o carro do jogador 1
        lerCarro("carroJogador1.txt", true);
        // Ler o carro do jogador 2
        lerCarro("carroJogador2.txt", false);

        // metodos para carregar as imagens referentes ao carros escolhido pelo jogador 1
        carregarImagensJogador1();
        // metodos para carregar as imagens referentes ao carros escolhido pelo jogador 2
        carregarImagensJogador2();

        // para ouvir o teclado
        addKeyListener(this);
        setFocusable(true);

        // buffer de renderizacao , usado para reajuste e melhor qualidade de imagem
        offscreenImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        //tread para controle da velocidade na saida
        iniciarThreadVelocidade();



        // loop que atualiza o movimento e a imagem
        Timer timer = new Timer(16, e -> {
            moverPersonagens();
            repaint();
        });
        timer.start();

        setVisible(true);
    }



    //  ler o carro a partir de um arquivo, retorna o numero do carro [0,1,2]
    private void lerCarro(String nomeArquivo, boolean isJogador1) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha = br.readLine();
            if (linha != null) {
                if (isJogador1) {
                    carroJogador1 = Integer.parseInt(linha);
                } else {
                    carroJogador2 = Integer.parseInt(linha);
                }
            } else {
                System.out.println("O arquivo está vazio.");
            }
        } catch (Exception exception) {
            System.out.println("Erro ao ler o arquivo: " + exception);
        }
    }

    // atribui as variaveis de controle de imagem/teclado os icones dos personagens
    private void carregarImagensJogador1() {
        // verde
        if (carroJogador1 == 0) {
            personagem1Up = new ImageIcon("./src/Img/2D/verde_para_cima.png").getImage();
            personagem1Left = new ImageIcon("./src/Img/2D/verde_para_esquerda.png").getImage();
            personagem1Down = new ImageIcon("./src/Img/2D/verde_para_baixo.png ").getImage();
            personagem1Right = new ImageIcon("./src/Img/2D/verde_para_direita.png").getImage();
            personagem1Atual = personagem1Right;
        }

        // azul
        if (carroJogador1 == 1) {
            personagem1Up = new ImageIcon("./src/Img/2D/azul_para_cima.png").getImage();
            personagem1Left = new ImageIcon("./src/Img/2D/azul_para_esquerda.png").getImage();
            personagem1Down = new ImageIcon("./src/Img/2D/azul_para_baixo.png").getImage();
            personagem1Right = new ImageIcon("./src/Img/2D/azul_para_direita.png").getImage();
            personagem1Atual = personagem1Right;
        }

        // amarelo
        if (carroJogador1 == 2) {
            personagem1Up = new ImageIcon("./src/Img/2D/amarelo_para_cima.png").getImage();
            personagem1Left = new ImageIcon("./src/Img/2D/amarelo_para_esquerda.png").getImage();
            personagem1Down = new ImageIcon("./src/Img/2D/amarelo_para_baixo.png").getImage();
            personagem1Right = new ImageIcon("./src/Img/2D/amarelo_para_direita.png").getImage();
            personagem1Atual = personagem1Right;
        }
    }

    // carregar as imagens do jogador 2
    private void carregarImagensJogador2() {
        // verde
        if (carroJogador2 == 0) {
            personagem2Up = new ImageIcon("./src/Img/2D/verde_para_cima.png").getImage();
            personagem2Left = new ImageIcon("./src/Img/2D/verde_para_esquerda.png").getImage();
            personagem2Down = new ImageIcon("./src/Img/2D/verde_para_baixo.png").getImage();
            personagem2Right = new ImageIcon("./src/Img/2D/verde_para_direita.png").getImage();
            personagem2Atual = personagem2Right;
        }

        // azul
        if (carroJogador2 == 1) {
            personagem2Up = new ImageIcon("./src/Img/2D/azul_para_cima.png").getImage();
            personagem2Left = new ImageIcon("./src/Img/2D/azul_para_esquerda.png").getImage();
            personagem2Down = new ImageIcon("./src/Img/2D/azul_para_baixo.png").getImage();
            personagem2Right = new ImageIcon("./src/Img/2D/azul_para_direita.png").getImage();
            personagem2Atual = personagem2Right;
        }

        // amarelo
        if (carroJogador2 == 2) {
            personagem2Up = new ImageIcon("./src/Img/2D/amarelo_para_cima.png").getImage();
            personagem2Left = new ImageIcon("./src/Img/2D/amarelo_para_esquerda.png").getImage();
            personagem2Down = new ImageIcon("./src/Img/2D/amarelo_para_baixo.png").getImage();
            personagem2Right = new ImageIcon("./src/Img/2D/amarelo_para_direita.png").getImage();
            personagem2Atual = personagem2Right;
        }
    }

    //estava usando para detectar colisao porem usei o que foi recomendado no documento do trabalho(Rectangue)
    private float calcularDistancia(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }



    // que aumenta gradativamente a velocidade dos carros
    private void iniciarThreadVelocidade() {
        threadVelocidade = new Thread(new Runnable() {
            @Override
            public void run() {
                while (aumentarVelocidade) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    velocidade = Math.min(velocidade + incremento, velocidadeMaxima);
                }
            }
        });
        threadVelocidade.start();
    }

    /*
    Tinha tentado implementar a colisao assim, porem estava dando muito erro kk

     // Verificar se os carros colidem
    if (carro1.intersects(carro2)) {
        velocidade = 1;
    } else {
        // Colisão do carro 1
        if ((y1 > 20 && y1 < 180 && x1 > 65 && x1 < 1358) ||  // Área 1
            (y1 > 20 && y1 < 733 && x1 > 90 && x1 < 270) ||  // Área 2
            (y1 > 550 && y1 < 729 && x1 > 90 && x1 < 1358) ||  // Área 3
            (y1 > 20 && y1 < 729 && x1 > 1160 && x1 < 1358)) {  // Área 4
            if (aPressed) {
                x1 -= velocidade;
                personagem1Atual = personagem1Left;
            }
        } else {
            velocidade = 1;
        }

        if ((y1 > 20 && y1 < 180 && x1 > 20 && x1 < 990) ||  // Área 1
            (y1 > 20 && y1 < 490 && x1 > 30 && x1 < 182) ||  // Área 2
            (y1 > 480 && y1 < 729 && x1 > 30 && x1 < 990) ||  // Área 3
            (y1 > 20 && y1 < 729 && x1 > 1150 && x1 < 990)) {  // Área 4
            if (dPressed) {
                x1 += velocidade;
                personagem1Atual = personagem1Right;
            }
        } else {
            velocidade = 1;
        }

        if ((x1 > 30 && x1 < 990 && y1 > 25 && y1 < 190) ||
            (x1 > 30 && x1 < 260 && y1 > 25 && y1 < 480) ||
            (x1 > 30 && x1 < 990 && y1 > 580 && y1 < 644) ||
            (x1 > 130 && x1 < 990 && y1 > 25 && y1 < 644)) {
            if (wPressed) {
                y1 -= velocidade;
                personagem1Atual = personagem1Up;
            }
        } else {
            velocidade = 1;
        }

        if ((x1 > 30 && x1 < 990 && y1 > 15 && y1 < 170) ||
            (x1 > 30 && x1 < 210 && y1 > 20 && y1 < 644) ||
            (x1 > 30 && x1 < 990 && y1 > 550 && y1 < 644) ||
            (x1 > 850 && x1 < 990 && y1 > 20 && y1 < 644)) {
            if (sPressed) {
                y1 += velocidade;
                personagem1Atual = personagem1Down;
            }
        } else {
            velocidade = 1;
        }

        // Colisão do carro 2
        if ((y2 > 20 && y2 < 180 && x2 > 90 && x2 < 1358) ||
            (y2 > 20 && y2 < 733 && x2 > 90 && x2 < 270) ||
            (y2 > 550 && y2 < 729 && x2 > 90 && x2 < 1358) ||
            (y2 > 20 && y2 < 729 && x2 > 1160 && x2 < 1358)) {
            if (leftPressed) {
                x2 -= velocidade;
                personagem2Atual = personagem2Left;
            }
        } else {
            velocidade = 1;
        }

        if ((y2 > 20 && y2 < 190 && x2 > 20 && x2 < 1348) ||
            (y2 > 20 && y2 < 733 && x2 > 30 && x2 < 245) ||
            (y2 > 550 && y2 < 729 && x2 > 30 && x2 < 1348) ||
            (y2 > 20 && y2 < 729 && x2 > 1150 && x2 < 1348)) {
            if (rightPressed) {
                x2 += velocidade;
                personagem2Atual = personagem2Right;
            }
        } else {
            velocidade = 1;
        }

        if ((x2 > 30 && x2 < 1348 && y2 > 30 && y2 < 190) ||
            (x2 > 30 && x2 < 260 && y2 > 20 && y2 < 733) ||
            (x2 > 30 && x2 < 1348 && y2 > 20 && y2 > 565 && y2 < 729) ||
            (x2 > 1150 && x2 < 1348 && y2 > 20 && y2 < 729)) {
            if (upPressed) {
                y2 -= velocidade;
                personagem2Atual = personagem2Up;
            }
        } else {
            velocidade = 1;
        }

        if ((x2 > 30 && x2 < 1348 && y2 > 15 && y2 < 180)
            (x2 > 30 && x2 < 260 && y2 > 20 && y2 < 720)
            (x2 > 30 && x2 < 1348 && y2 > 550 && y2 < 722)
            (x2 > 1150 && x2 < 1348 && y2 > 20 && y2 < 720)) {
            if (downPressed) {
                y2 += velocidade;
                personagem2Atual = personagem2Down;
            }
        } else {
            velocidade = 1;
        }
    }
}
     */

    // parar a thread de velocidade
    private void pararThreadVelocidade() {
        aumentarVelocidade = false;
        try {
            threadVelocidade.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    //  mover os personagens
    private void moverPersonagens() {
        //colisao do carro
        Rectangle carro1 = new Rectangle(x1,y1,50,30);
        Rectangle carro2 = new Rectangle(x2,y2,50,30);

        //colisao com a  parte interna da pista
        Rectangle interno = new Rectangle(230,200,624,290);

        Rectangle linhaDeChegada = new Rectangle(520,500,1,180);


        Thread threadLinhaDeChegada = new Thread(new Runnable() {
            boolean jogador1PassouPelaLinha = false; // Controle se o jogador 1 passou pela linha
            boolean jogador2PassouPelaLinha = false; // Controle se o jogador 2 passou pela linha

            @Override
            public void run() {
                while (true) {
                    try {
                        // Verifica o carro 1
                        if (carro1.intersects(linhaDeChegada) && !jogador1PassouPelaLinha) {
                            numeroVoltasJogador1++;
                            System.out.println("Jogador 1 completou " + numeroVoltasJogador1 / 7 + " voltas!");
                            jogador1PassouPelaLinha = true; // Marcar que o jogador passou pela linha
                        } else if (!carro1.intersects(linhaDeChegada)) {
                            // Resetar o flag quando o carro sair da linha
                            jogador1PassouPelaLinha = false;
                        }

                        // Verifica o carro 2
                        if (carro2.intersects(linhaDeChegada) && !jogador2PassouPelaLinha) {
                            numeroVoltasJogador2++;
                            System.out.println("Jogador 2 completou " + numeroVoltasJogador2 / 7 + " voltas!");
                            jogador2PassouPelaLinha = true; // Marcar que o jogador passou pela linha
                        } else if (!carro2.intersects(linhaDeChegada)) {
                            // Resetar o flag quando o carro sair da linha
                            jogador2PassouPelaLinha = false;
                        }

                        Thread.sleep(1000); // Tempo entre as verificações, ajustável
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });

        threadLinhaDeChegada.start();




        if(numeroVoltasJogador1/7 == 3){
            VitoriaJogador1();
        }

        if(numeroVoltasJogador2/7 == 3){
            VitoriaJogador2();
        }

        //controle de movimento do carro 2 e colisao
        if(carro1.intersects(interno)){
            VitoriaJogador2();
        }else {

            if(x1<65||x1>990||y1<26||y1>650){
                VitoriaJogador2();
            }

            if(x2<65||x2>990||y2<26||y2>650){
                VitoriaJogador1();
            }


            if (aPressed) {
                x1 -= velocidade;
                personagem1Atual = personagem1Left;
            }



            if (dPressed) {
                x1 += velocidade;
                personagem1Atual = personagem1Right;
            }



            if (wPressed) {
                y1 -= velocidade;
                personagem1Atual = personagem1Up;
            }


            if (sPressed) {
                y1 += velocidade;
                personagem1Atual = personagem1Down;
            }

        }

        //controle de movimento do carro 2 e colisao
        if (carro2.intersects(interno)){
            VitoriaJogador1();
        }else {
            if (leftPressed) {
                x2 -= velocidade;
                personagem2Atual = personagem2Left;
            }



            if (rightPressed) {
                x2 += velocidade;
                personagem2Atual = personagem2Right;
            }


            if (upPressed) {
                y2 -= velocidade;
                personagem2Atual = personagem2Up;
            }



            if (downPressed) {
                y2 += velocidade;
                personagem2Atual = personagem2Down;
            }

        }

        if(carro1.intersects(carro2)){
            velocidade = 1;
        }

        if(carro2.intersects(carro1)){
            velocidade = 1;
        }
    }

    private void VitoriaJogador1() {
        JOptionPane.showMessageDialog(this, "Vitória do Jogador 1!", "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);

    }

    private void VitoriaJogador2() {
        JOptionPane.showMessageDialog(this, "Vitória do Jogador 2!", "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);

    }



    // desenhar o fundo e os personagens
    @Override
    public void paint(Graphics g) {
        // como estou utilizando uma buffer de imagens, desenho nele primeiro para depois mostrar, isso da melhor nitidez
        Graphics offscreenGraphics = offscreenImage.getGraphics();
        offscreenGraphics.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        offscreenGraphics.drawImage(personagem1Atual, x1, y1, 50, 30, this);
        offscreenGraphics.drawImage(personagem2Atual, x2, y2, 50, 30, this);
        offscreenGraphics.dispose();

        // desenha na tela o que esta no buffer
        g.drawImage(offscreenImage, 0, 0, this);
    }

    // metodos para controlar as teclas
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Controles do personagem 1 (WASD)
            case KeyEvent.VK_W -> wPressed = true;
            case KeyEvent.VK_A -> aPressed = true;
            case KeyEvent.VK_S -> sPressed = true;
            case KeyEvent.VK_D -> dPressed = true;

            // Controles do personagem 2 (Setas direcionais)
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_LEFT -> leftPressed = true;
            case KeyEvent.VK_DOWN -> downPressed = true;
            case KeyEvent.VK_RIGHT -> rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Controles do personagem 1 (WASD)
            case KeyEvent.VK_W -> wPressed = false;
            case KeyEvent.VK_A -> aPressed = false;
            case KeyEvent.VK_S -> sPressed = false;
            case KeyEvent.VK_D -> dPressed = false;

            // Controles do personagem 2 (Setas direcionais)
            case KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_LEFT -> leftPressed = false;
            case KeyEvent.VK_DOWN -> downPressed = false;
            case KeyEvent.VK_RIGHT -> rightPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        new Janela();
    }
}