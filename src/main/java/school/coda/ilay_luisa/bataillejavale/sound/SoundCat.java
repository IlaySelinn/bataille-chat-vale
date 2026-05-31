package school.coda.ilay_luisa.bataillejavale.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import java.util.Optional;

public class SoundCat {

    public static void missed() {
        playSound("ronron.wav");
    }

    public static void hit() {
        playSound("miaou.wav");
    }

    public static void sunk() {
        playSound("miaou_aigue.wav");
    }

    private static void playSound(String soundResourcePath) {
        try {
            // 🚨 Les sons ne sont pas disponibles dans le jar
            // Pour qu'ils le soient, ils doivent se trouver dans les resources
            var url = loadResource(soundResourcePath);
            if (url.isPresent()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(url.get());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start(); // Joue le son une fois
            } else {
                System.out.println("Fichier son introuvable : " + soundResourcePath);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la lecture du son : " + e.getMessage());
        }
    }

    private static Optional<URL> loadResource(String fileName) {
        String resourcePath = "/sound/" + fileName;
        java.net.URL url = SoundCat.class.getResource(resourcePath);
        if (url == null) {
            System.err.println("Pas de son: " + resourcePath);
            return Optional.empty();
        }
        return Optional.of(url);
    }
}