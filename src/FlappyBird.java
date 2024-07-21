import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;


public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 1000;
    int boardHeight = 350;
    double score = 0;

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
    int velocityX = -3;
    float gravity = 1;

    // pipes 
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 240; 
    

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
    Random random = new Random();

    public void placePipes(){
        int randomPipeY = (int)(pipeY - pipeHeight/4- Math.random()*(pipeHeight/2));
        int openingSpace = boardHeight/3;
        Pipe top = new Pipe (topPipe);
        top.y = randomPipeY;

        Pipe bottom = new Pipe(bottomPipe);
        bottom.y = top.y + pipeHeight + openingSpace;
        pipes.add(top);
        pipes.add(bottom);
    }

    Bird bird;
    boolean gameOver = false;
    Timer placePipesTimer;
    Timer gameLoop;

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
        placePipesTimer = new Timer (1500, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                placePipes();
            }
        });
        placePipesTimer.start();
        //game timer 
        gameLoop = new Timer(1000/60, this);
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

        //score 
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver){
            g.drawString("Game Over! Scored:  "+  String.valueOf((int)score), 10, 35 );
        }
        else{
            g.drawString("Score: " + String.valueOf((int)score), 10, 35);
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

            if (!pipe.passed && bird.x > pipe.x + pipe.width){
                pipe.passed = true;
                score += 0.5;
            }
            if(collision(bird, pipe)){
                gameOver = true;
            }
        }
        
        if (bird.y > boardHeight){
            gameOver = true;
        }
    }

    //a's top left corner doesn't reach b's top right corner
    //a's top right corner passes b's top left corner
    //a's top left corner doesn't reach b's bottom left corner
    //a's bottom left corner passes b's top left corner

    public boolean collision(Bird b, Pipe p){
        return b.x < p.x + p.width && b.x + b.width > p.x &&
               b.y < p.y + p.height && b.y + b.height > p.y;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) { 
        move();
        repaint();
        if (gameOver){
            placePipesTimer.stop();
            gameLoop.stop();
        }
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


