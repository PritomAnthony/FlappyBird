import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;


public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 1000;
    int boardHeight = 350;

    // Images 
    Image background;
    Image birdImage;
    Image topPipe;
    Image bottomPipe;

    // Bird 
    int birdX = boardWidth / 4;
    int birdY = boardHeight / 2;
    int birdWidth = 40;
    int birdHeight = 30;
    int velocityY = 0;
    int velocityX = -4;
    float gravity = 1;

    // pipes 
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 200; 
    

    class Bird{
        int x  = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img){
            this.img = img;
        }

    }


    class Pipe{
        int x  = pipeX;
        int y = pipeY;
        int width  = pipeWidth;
        int height  = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe (Image img){
            this.img  = img;

        }
    }
    ArrayList<Pipe> pipes;
    public void placePipes(){
        Pipe top = new Pipe (topPipe);
        // Pipe bottomPipe = new Pipe(bottomPipe);
        pipes.add(top);
    }


    Bird bird;
    // Constructor
    FlappyBird(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        
        // load images
        background = new ImageIcon(getClass().getResource("./images/bg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./images/flappybird.png")).getImage();
        topPipe = new ImageIcon(getClass().getResource("./images/toppipe.png")).getImage();
        bottomPipe = new ImageIcon(getClass().getResource("./images/bottompipe.png")).getImage();
        
        bird = new Bird(birdImage);
        pipes = new ArrayList<Pipe>();

        // place pipes timer 
        Timer placePipesTimer = new Timer (1500, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                placePipes();
            }
        });
        placePipesTimer.start();

        //game timer 
        Timer gameLoop = new Timer(1000/60, this);
        gameLoop.start();
        
        
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
        addKeyListener(this);
    }

    public void draw(Graphics g){
        // x and y are starting position( top left corner)
        g.drawImage(background, 0, 0, boardWidth, boardHeight, null);
        // bird 
        g.drawImage(birdImage, bird.x, bird.y, birdWidth, birdHeight, null);

        //pipes 
        for(int i = 0 ; i <pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width,pipe.height, null );
        }
    }

    public void move(){
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);
        // move pipes

        for (int i  = 0 ; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;
        }
    }





    @Override
    public void actionPerformed(ActionEvent e) {
        
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            velocityY = -6;
           }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyReleased(KeyEvent e){}

}


