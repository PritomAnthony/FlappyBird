
import javax.swing.JFrame;



public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 1400;
        int boardHeight = 350;
        
        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();  // just to keep the bg  -within the frame dimenson
        frame.setVisible(true);

    }
}
