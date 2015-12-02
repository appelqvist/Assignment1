package main;


import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Andréas Appelqvist on 2015-11-11.
 *
 * Musikspelare som tar hand om en spelare.
 */
public class MusicPlayer {
    private File filePath;
    private Player player;
    private Controller controller;
    private AudioClip music;

    /**
     * Initiserar filepath och controller
     * @param filePath
     * @param controller
     */
    public MusicPlayer(File filePath, Controller controller) {
        this.filePath = filePath;
        this.controller = controller;
    }

    /**
     * Uppdaterar filvägen.
     * @param filePath
     */
    public void newFilePath(File filePath) {
        if (player != null) {
            stop();
            player = null;
            this.filePath = filePath;
        }
    }

    /**
     * Stänger av musiken som spelas
     */
    public void stop() {
        if (player != null) {
            music.stop();
            player.interrupt();
            player = null;
            controller.setGUINowPlaying(false);
        }
    }

    /**
     * Startar musiken med vald path
     */
    public void start() {
        if (player == null) {
            if (filePath != null) {
                player = new Player();
                player.run();
                controller.setGUINowPlaying(true);
            } else {
                System.err.println("Det finns ingen fil vald");
            }
        }else{
            System.err.println("Finns redan en låt som spelas");
        }
    }

    /**
     * Spelaren (tråd) som sköter uppspelningen
     */
    private class Player extends Thread{
        public void run() {
            super.run();
            try {
                URL url = filePath.toURI().toURL();
                music = Applet.newAudioClip(url);
                music.loop();

            } catch (MalformedURLException e) {
                System.out.println(e);
            }
        }
    }
}
