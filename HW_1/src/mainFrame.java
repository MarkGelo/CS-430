import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.spi.BreakIteratorProvider;
import java.util.Random;
import java.util.Collections;

public class mainFrame extends JPanel implements ActionListener{
    JFrame frame;
    JButton start;
    JTextField numberField;
    JCheckBox insertionSort;
    JCheckBox mergeSort;
    static int windowWidth = 1000;
    static int windowHeight = 600;
    Timer tm = new Timer(1000, this);
    int[] array;
    Random rand = new Random();
    int n = 200; // default size of array to sort is 100
    //TODO why tf does it have some missing rectangles when n = 500
    public void setup(){
        randomArray(n);
        frame = new JFrame();
        frame.setSize(windowWidth, windowHeight);
        frame.setResizable(false); //user cant resize
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //when exit on window, stops the program
        frame.setTitle("Homework 1 - CS 430"); //sets the window title

        //sets the panels/info/input

        //checkboxes for algorithms
        insertionSort = new JCheckBox("Insertion Sort");
        insertionSort.setFocusPainted(false); //remove the ugly border around the text
        mergeSort = new JCheckBox("Merge Sort");
        mergeSort.setFocusPainted(false); //remove the ugly border around the text
        add(insertionSort);
        add(mergeSort);

        JLabel numberToSort = new JLabel("N: "); //description text
        add(numberToSort);

        numberField = new JTextField("", 5); //text field for user input for amount
        numberField.setToolTipText("Enter the amount of numbers you want to sort");
        add(numberField);

        start = new JButton("Sort");
        start.setFocusPainted(false); //remove the ugly border around the text
        start.setToolTipText("Click to start sorting");
        add(start);

        //listens for inputs
        start.addActionListener(this);
        //dont need keylistener cuz only want what the user typed
        //which you can just get with .getText()

        frame.add(this);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == start){
            System.out.println(numberField.getText());
            if(insertionSort.isSelected() && mergeSort.isSelected()){ //side by side comparison
                System.out.println("both");
            }else if(insertionSort.isSelected()){ //just insertion sort
                randomArray(n);
                repaint();
                //insertionSort(array);
                for(int i = 0; i < 20; i++){
                    repaint();
                }
            }else if(mergeSort.isSelected()){ //just merge sort
                System.out.println("merge");
            }else{ //nothing cuz no checkbox checked
                randomArray(n);
                repaint();
                System.out.println("nothing");

            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D main = (Graphics2D) g;

        //TODO make this shit scalable, the height, width, and colors
        double width = (double)(windowWidth - 100) / array.length;
        for(int i = 0; i < array.length; i++){
            double height = array[i] * ((double)(windowHeight - 100) / maxArray(array));
            double xPos = 50 + width * i;
            double yPos = 50;

            /* if wanna use this then make the rectangles keep its color whenever its repainted
            //otherwise the color is based on when the array is instantiated, not the order of array
            float red = i * ((float)0.9 / array.length) + (float)0.005;
            float green = ((float)i/(float)1.5) * ((float)0.9 / array.length) + (float)0.005;
            float blue = ((float)i/(float)1.5) * ((float)0.9 / array.length) + (float)0.005;
            float alpha = (float)0.777;

            main.setColor(new Color(red, green, blue, alpha));
             */

            int red = (int) (Math.random() * 256);
            int green = (int) (Math.random() * 256);
            int blue = (int) (Math.random() * 256);
            int alpha = (int) (Math.random() * 256);

            main.setColor(new Color(red, green, blue, alpha));
            //main.setColor(Color.BLACK);
            main.fill(new Rectangle2D.Double(xPos, yPos, width, height));

            tm.start();
        }
    }
    public void randomArray(int size){
        array = new int[size];
        for(int i = 0; i < array.length; i++){
            array[i] = rand.nextInt(array.length) + 1;
        }
    }
    public static int maxArray(int[] arr){
        int max = 0;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] > max){
                max = arr[i];
            }
        }
        return max;
    }

    public void insertionSort(int[] arr){
        for(int i = 1; i < arr.length; i++){
            int current = arr[i];
            int j = i - 1;
            while(j >= 0 && arr[j] > current){
                arr[j + 1] = arr[j];
                repaint();
                j -= 1;
            }
            arr[j + 1] = current;
            repaint();
        }
    }
    public static void main(String[] args){
        mainFrame go = new mainFrame();
        go.setup();
    }
}
