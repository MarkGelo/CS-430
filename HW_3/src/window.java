import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Objects;

public class window extends JPanel {
    //declaring gui stuff as instance variables for easy referencing
    JFrame frame;
    JButton start;
    JButton stop;
    JCheckBox compare;
    JComboBox algoBox;
    JTextField numberField;
    int windowWidth = 1000;
    int windowHeight = 600;
    //init algo object which initializes arrays
    algorithms algo = new algorithms();
    int[] currentArr = algo.origArray.clone();
    //arraylist for the animation
    ArrayList<int[]> sorted;
    //for the animation
    boolean stopBool = false;
    int count = 0;
    int swaps = 0;
    boolean comparing;
    Timer tm = new Timer(1, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            if(stopBool){
                ((Timer)e.getSource()).stop();
                start.setEnabled(true);
                stop.setEnabled(false);
            }else if(count == sorted.size()){
                count = 0;
                start.setEnabled(true);
                stop.setEnabled(false);
                algoBox.setEnabled(true);
                numberField.setEnabled(true);
                tm.stop();
            }else{
                currentArr = sorted.get(count).clone();
                swaps += 1;
                count += 1;
            }
            repaint();
        }
    });

    public void setup(){
        //sets the frame and rules
        frame = new JFrame();
        frame.setSize(windowWidth, windowHeight);
        frame.setResizable(false); //user cant resize
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //when exit on window, stops the program
        frame.setTitle("Homework - CS 430"); //sets the window title

        //add a drop down box for the algorithms
        String[] algorithmsBox = {"Insertion", "Merge", "Heap", "Quick"};
        algoBox = new JComboBox(algorithmsBox);
        add(algoBox);

        //compare checkbox
        // keeps the arrays the same for each one to compare
        compare = new JCheckBox("Compare");
        compare.setFocusPainted(false); //looks ugly without
        add(compare);

        JLabel numberToSort = new JLabel("N: "); //description text
        add(numberToSort);

        numberField = new JTextField("", 5); //text field for user input for amount
        numberField.setToolTipText("Enter the amount of numbers you want to sort");
        add(numberField);

        //button to start sorting
        start = new JButton("Sort");
        start.setFocusPainted(false); //remove the ugly border around the text
        start.setToolTipText("Click to start sorting");
        add(start);

        start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(e.getSource() == start){
                    //first few if/else statements are for stop button and for comparing
                    //if comparing then keeps the arrays the same
                    if(compare.isSelected()){
                        comparing = true;
                    }else{
                        comparing = false;
                    }
                    if(stopBool){ //for if user clicked stop, then sort, starts animation again
                        stopBool = false;
                        start.setEnabled(false);
                        tm.start();
                        // with the numberfield so it doesnt make a new array when starting animation each time
                    }else if(isNumber(numberField.getText()) && !comparing){ // non default n
                        algo.setSize(Integer.parseInt(numberField.getText()));
                    }else if(isNumber(numberField.getText()) && comparing){ // compare selected and number
                        if(algo.n != Integer.parseInt(numberField.getText())){ //if same n, dont shuffle cuz should keep same
                            // if not same n, then makes a new n array and shuffle
                            algo.setSize(Integer.parseInt(numberField.getText()));
                        }
                        numberField.setEnabled(false);
                    }
                    else if(!comparing){ // default n and not comparing
                        //for when the user input non default but then removed so back to default
                        algo.setSize(100);
                        comparing = false;
                    }else{ //comparing, doesnt do setsize cuz setsize shuffles - keeps array the same
                        //does nothing
                    }

                    sorted = algo.sort((String) Objects.requireNonNull(algoBox.getSelectedItem()));
                    // dont want null
                    //for the buttons and stuff to be disabled/enabled when clicked sort
                    stop.setEnabled(true);
                    start.setEnabled(false);
                    numberField.setEnabled(false);
                    algoBox.setEnabled(false);
                    swaps = 0; //restarts # of swap
                    tm.start();
                }
            }
        });

        //button to stop the sorting
        stop = new JButton("Stop");
        stop.setFocusPainted(false); //remove the ugly border around the text
        stop.setToolTipText("Stop sorting");
        add(stop);
        stop.setEnabled(false);

        //stop action listnere
        stop.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //opposite of what it was, so stops
                stopBool = !stopBool;
            }
        });

        //adds all of the things added above to the frame.
        frame.add(this);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {//for the paintin and for the animation
        super.paintComponent(g);
        Graphics2D main = (Graphics2D) g;//for the double, makes it easier
        double width = (double)(windowWidth - 100) / algo.origArray.length;
        //for the number of swaps to show
        g.drawString((String) algoBox.getSelectedItem() + " Swaps: ", 25,25);
        g.drawString(Integer.toString(swaps), 125,25);
        //paints the array as bar graphs
        for(int i = 0; i < currentArr.length; i++) {
            double height = currentArr[i] * ((double) (windowHeight - 100) / maxArray(algo.origArray.clone()));
            double xPos = 50 + width * i;
            double yPos = 50;
            main.setColor(Color.BLACK);
            main.fill(new Rectangle2D.Double(xPos, yPos, width, height));
        }
    }
    public static int maxArray(int[] arr){//gets the maximum of the array
        //used for calculating the max height of the array in painting
        int max = 0;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] > max){
                max = arr[i];
            }
        }
        return max;
    }
    public boolean isNumber(String in){//for the nubmberfield, checks if the string is a number
        try{
            int test = Integer.parseInt(in);
            return true;
        }catch(Exception ie){
            return false;
        }
    }
    public static void main(String[] args){
        //starts the window
        window init = new window();
        init.setup();
    }
}
