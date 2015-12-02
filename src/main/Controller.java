package main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 *
 * Controller som sköter mestadels av logiken i programmet.
 *
 * Created by Andréas Appelqvist on 2015-11-11.
 */
public class Controller {

    private GUIFrame guiFrame;
    private MusicPlayer musicPlayer;
    private TextJumper textJumper;
    private PictureRotator picRot;


    public Controller(GUIFrame gui) {
        this.guiFrame = gui;
        gui.setController(this);
    }

    /**
     * Sätter att de spelas musik
     * @param playing
     */
    public void setGUINowPlaying(boolean playing) {
        guiFrame.updateNowPlaying(playing);
    }

    /**
     * Skickar pathen till låten
     * @param path
     */
    public void setGUIPath(File path) {
        guiFrame.updateMusicPath(path);
    }


    /**
     * Startar musik
     */
    public void playMusicPlayer() {
        if(musicPlayer != null)
            musicPlayer.start();
    }

    /**
     * Stoppar musiken
     */
    public void stopMusicPlayer() {
        if(musicPlayer != null)
            musicPlayer.stop();
    }

    /**
     * Öppnaer en browser
     * Där man kan välja en wav-fil.
     */
    public void selectWAV() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("wav", "WAV");
        chooser.setFileFilter(filter);

        int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            System.out.println(selectedFile);
            if (musicPlayer == null) {
                musicPlayer = new MusicPlayer(selectedFile, this);
            } else {
                musicPlayer.newFilePath(selectedFile);
            }
            setGUIPath(selectedFile);
        } else {
            System.err.println("fel format");
        }
    }

    /**
     * Startar en PictureRotator-tråd
     */
    public void startPicRot(){
        if(picRot != null){
            System.err.println("Det är redan en rot igång");
        }else{
            picRot = new PictureRotator();
            picRot.start();
        }
    }

    /**
     * Stoppar PicturePotator-tråd
     */
    public void stopPicRot(){
        if(picRot != null){
            picRot.interrupt();
            picRot = null;
        }else{
            System.err.println("Finns inget att avbryta");
        }
    }

    /**
     * Tråd som roterar bilden i triangelfönstret.
     * roterar bilden 45grader var 500ms
     */
    private class PictureRotator extends Thread{
        @Override
        public void run() {
            super.run();
            while(!interrupted()){
                guiFrame.updatePicAngle(45);
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }


    /**
     * Startar TextJumping-tråden
     */
    public void startTextJumping() {
        if (textJumper == null) {
            textJumper = new TextJumper();
            textJumper.start();
        }else{
            System.err.println("Finns redan en jumper");
        }
    }

    /**
     * Stoppar TextJumping-tråden
     */
    public void stopTextJumping() {
        if(textJumper != null) {
            textJumper.interrupt();
            textJumper = null;
        }
        else{
            System.err.println("Finns ingen jumper att stoppa");
        }
    }

    /**
     * Tråd som uppdaterar texten i text-rutan till en
     * slumpvald position var 500ms
     */
    private class TextJumper extends Thread {
        int x = 0, y = 0;

        public void run() {
            while (!interrupted()) {
                x = (int) (Math.random() * 170 + 1);
                y = (int) (Math.random() * 170 + 1);
                guiFrame.updateTextPosition(x, y);

                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
