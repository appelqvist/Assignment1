package main;

/**
 * Created by Andréas Appelqvist on 2015-11-11.
 */
public class Start {
    public static void main(String[] args) {
        GUIFrame gui = new GUIFrame();
        new Controller(gui);
        gui.start();
    }
}
