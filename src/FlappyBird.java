import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.*;


public class FlappyBird extends JPanel {
    int boardWidth = 1400;
    int boardHeight = 350;

    // Images 
    Image background;
    Image birdImage;
    Image topPipe;
    Image bottomPipe;


    // Constructor
    FlappyBird(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        // load images
        background = new ImageIcon(getClass().getResource("./images/bg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./images/flappy-bird-tap-bird-2d-basic-flappy-angry-birds.jpg")).getImage();
        topPipe = new ImageIcon(getClass().getResource("./images/toppipe.png")).getImage();
        topPipe = new ImageIcon(getClass().getResource("./images/bottompipe.png")).getImage();

    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        // x and y are starting position( top left corner)
        g.drawImage(background, 0, 0, boardWidth, boardHeight, null);
    }
}

