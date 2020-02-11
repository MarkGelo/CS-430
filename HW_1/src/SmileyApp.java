import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.Random;
public class SmileyApp extends JPanel implements ActionListener {
    int[] array = new int[]{1,2,3,4,5,6,7,8,9,10};
    Timer tm = new Timer (100, this);
    Random rand = new Random();
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("works");
        Graphics2D main = (Graphics2D) g;
        System.out.println("test");
        for(int i = 0; i < array.length; i++){
            int height = this.getHeight()/array.length;
            int width = array[i] * 20;
            int xPos = 0;
            int yPos = height * i;

            int red = (int) (Math.random() * 256);
            int green = (int) (Math.random() * 256);
            int blue = (int) (Math.random() * 256);
            int alpha = (int) (Math.random() * 256);

            main.setColor(new Color(red, green, blue, alpha));
            main.fill(new Rectangle(xPos, yPos, width, height));

            tm.start();
        }
    }
    public static void main(String[] args) {
        SmileyApp smiley = new SmileyApp();
        JFrame app = new JFrame("Smiley App");
        app.add(smiley, BorderLayout.CENTER);
        app.setSize(300, 300);
        app.setLocationRelativeTo(null);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 1; i < array.length; i++){
            int current = array[i];
            int j = i - 1;
            while(j >= 0 && array[j] > current){
                array[j + 1] = array[j];
                repaint();
                j -= 1;
            }
            array[j + 1] = current;
            repaint();
        }
    }
}
