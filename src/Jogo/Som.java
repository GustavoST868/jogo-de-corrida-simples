package Jogo;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Som {

    public  Som(){

    }

    public  void Som(String caminho){
        try{
            File arquivo = new File(caminho);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(arquivo);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

}
