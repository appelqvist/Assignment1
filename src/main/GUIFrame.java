
package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;
import javax.swing.border.*;

/**
 * The GUI for assignment 1
 */
public class GUIFrame {

    private Controller controller;

    private JFrame frame;        // The Main window
    private JButton btnOpen;    // Open sound file button
    private JButton btnPlay;    // Play selected file button
    private JButton btnStop;    // Stop music button
    private JButton btnDisplay;    // Start thread moving display
    private JButton btnDStop;    // Stop moving display thread
    private JButton btnTriangle;// Start moving graphics thread
    private JButton btnTStop;    // Stop moving graphics thread
    private JLabel lblPlaying;    // Hidden, shown after start of music
    private JLabel lblPlayURL;    // The sound file path
    private JLabel lblText; //The Text that are going to blip
    private JLabel lblPicture; //Picture
    private JPanel pnlMove;        // The panel to move display in
    private JPanel pnlRotate;    // The panel to move graphics in


    private ImageIcon icon;
    private int angle;
    /**
     * Constructor
     */

    public GUIFrame() {
    }

    /**
     * Starts the frame
     */
    public void start() {
        frame = new JFrame();
        frame.setBounds(0, 0, 494, 437);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("Multiple Thread Demonstrator");
        initializeGUI();  // Fill in components
        setListener();
        frame.setVisible(true);
        frame.setResizable(false);            // Prevent user from change size
        frame.setLocationRelativeTo(null);    // Start middle screen
    }

    /**
     * Sets up the GUI with components
     */
    private void initializeGUI() {
        // The play panel
        JPanel pnlSound = new JPanel();
        Border b1 = BorderFactory.createTitledBorder("Music Player");
        pnlSound.setBorder(b1);
        pnlSound.setBounds(12, 12, 450, 100);
        pnlSound.setLayout(null);

        // Add the buttons and labels to this panel
        btnOpen = new JButton("Open");
        btnOpen.setBounds(6, 71, 75, 23);
        pnlSound.add(btnOpen);

        btnPlay = new JButton("Play");
        btnPlay.setBounds(88, 71, 75, 23);
        pnlSound.add(btnPlay);

        btnStop = new JButton("Stop");
        btnStop.setBounds(169, 71, 75, 23);
        pnlSound.add(btnStop);

        lblPlaying = new JLabel("Now Playing...", JLabel.CENTER);
        lblPlaying.setFont(new Font("Serif", Font.BOLD, 20));
        lblPlaying.setBounds(128, 16, 170, 30);
        lblPlaying.setVisible(false);
        pnlSound.add(lblPlaying);

        lblPlayURL = new JLabel("Music url goes here");
        lblPlayURL.setBounds(10, 44, 400, 13);
        pnlSound.add(lblPlayURL);
        // Then add this to main window
        frame.add(pnlSound);

        // The moving display outer panel
        JPanel pnlDisplay = new JPanel();
        Border b2 = BorderFactory.createTitledBorder("Display Thread");
        pnlDisplay.setBorder(b2);
        pnlDisplay.setBounds(12, 118, 222, 269);
        pnlDisplay.setLayout(null);

        // Add buttons and drawing panel to this panel
        btnDisplay = new JButton("Start Display");
        btnDisplay.setBounds(10, 226, 121, 23);
        pnlDisplay.add(btnDisplay);

        btnDStop = new JButton("Stop");
        btnDStop.setBounds(135, 226, 75, 23);
        pnlDisplay.add(btnDStop);

        pnlMove = new JPanel();
        pnlMove.setBounds(10, 19, 200, 200);
        Border b21 = BorderFactory.createLineBorder(Color.black);
        pnlMove.setBorder(b21);

        lblText = new JLabel("This Text That Will Jump");
        lblText.setBounds(0, 0, 115, 13);
        pnlMove.add(lblText);

        pnlDisplay.add(pnlMove);
        // Then add this to main window
        frame.add(pnlDisplay);

        // The moving graphics outer panel
        JPanel pnlTriangle = new JPanel();
        Border b3 = BorderFactory.createTitledBorder("Triangle Thread");
        pnlTriangle.setBorder(b3);
        pnlTriangle.setBounds(240, 118, 222, 269);
        pnlTriangle.setLayout(null);

        // Add buttons and drawing panel to this panel
        btnTriangle = new JButton("Start Rotate");
        btnTriangle.setBounds(10, 226, 121, 23);
        pnlTriangle.add(btnTriangle);

        btnTStop = new JButton("Stop");
        btnTStop.setBounds(135, 226, 75, 23);
        pnlTriangle.add(btnTStop);

        pnlRotate = new JPanel();
        pnlRotate.setBounds(10, 19, 200, 200);
        Border b31 = BorderFactory.createLineBorder(Color.black);
        pnlRotate.setBorder(b31);
        pnlTriangle.add(pnlRotate);
        // Add this to main window

        pnlRotate.setLayout(null);
        icon = new ImageIcon("res/images/husvagn.jpg");
        lblPicture = new JLabel(icon, JLabel.CENTER);
        lblPicture.setBounds(0,0,200,200);
        pnlRotate.add(lblPicture);

        frame.add(pnlTriangle);
    }

    /**
     * Sets up Listeners
     */
    private void setListener() {
        btnOpen.addActionListener(new ClickListener());
        btnPlay.addActionListener(new ClickListener());
        btnStop.addActionListener(new ClickListener());
        btnDisplay.addActionListener(new ClickListener());
        btnDStop.addActionListener(new ClickListener());
        btnTriangle.addActionListener(new ClickListener());
        btnTStop.addActionListener(new ClickListener());

    }

    /**
     * Byter plats på texten till vald x,y
     * @param x
     * @param y
     */
    public void updateTextPosition(int x, int y) {
        lblText.setBounds(x,y,160,40);
    }

    /**
     * Sätter controller
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Uppdaterar texten "now playing"
     * @param playing
     */
    public void updateNowPlaying(boolean playing) {
        lblPlaying.setVisible(playing);
    }

    /**
     * Uppdatera sångpathen till låten.
     * @param path
     */
    public void updateMusicPath(File path) {
        lblPlayURL.setText("Vald fil: "+path.toString());
    }

    /**
     * Roterar bilden med angle grader.
     */
    public void updatePicAngle(int angle){
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();

        int type = BufferedImage.TYPE_INT_RGB;  // other options, see api
        BufferedImage image = new BufferedImage(h, w, type);
        Graphics2D g2 = image.createGraphics();

        double x = (h - w)/2.0;
        double y = (w - h)/2.0;

        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        at.rotate(Math.toRadians(angle), w/2.0, h/2.0);
        g2.drawImage(icon.getImage(), at, lblPicture);
        g2.dispose();

        icon = new ImageIcon(image);
        lblPicture.setIcon(icon);
    }

    /**
     * Clicklisteners
     */
    private class ClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnOpen) {
                System.out.println("Open Browser");
                controller.selectWAV();
            } else if (e.getSource() == btnPlay) {
                System.out.println("Play Music");
                controller.playMusicPlayer();
            } else if (e.getSource() == btnStop) {
                System.out.println("Stop Music");
                controller.stopMusicPlayer();
            }else if(e.getSource() == btnDisplay){
                System.out.println("Start Text");
                controller.startTextJumping();
            }else if(e.getSource() == btnDStop){
                System.out.println("Stop Text");
                controller.stopTextJumping();
            }else if(e.getSource() == btnTriangle){
                System.out.println("Start Triangle");
                controller.startPicRot();
            }else if(e.getSource() == btnTStop){
                System.out.println("Stop Triangle");
                controller.stopPicRot();
            }
        }
    }
}
