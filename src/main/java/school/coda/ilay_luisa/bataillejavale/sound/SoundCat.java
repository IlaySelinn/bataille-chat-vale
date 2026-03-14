package school.coda.ilay_luisa.bataillejavale.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundCat {


    public static void playSound(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            if (soundFile.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start(); // Joue le son une fois
            } else {
                System.out.println("Fichier son introuvable : " + soundFilePath);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture du son : " + e.getMessage());
        }
    }
}