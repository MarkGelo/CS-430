import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.spi.BreakIteratorProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GUI extends JPanel{
    //TODO add swap, array accesses, count as data for the algorithm
    JFrame frame;
    JButton start;
    JButton stop;
    JTextField numberField;
    JCheckBox insertionSort;
    JCheckBox mergeSort;
    static int windowWidth = 1000;
    static int windowHeight = 600;
    int count = 0;
    boolean stopBool = false;
    Timer tm = new Timer(5, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            stop.setEnabled(true);
            numberField.setEnabled(false);
            if(stopBool){
                ((Timer)e.getSource()).stop();
                start.setEnabled(true);
                stop.setEnabled(false);
            }else if (count == arrayL.size()) {
                ((Timer) e.getSource()).stop();
                count = 0;
                arrayL.clear();
                start.setEnabled(true);
                stop.setEnabled(false);
                numberField.setEnabled(true);
            } else {
                array = arrayL.get(count).clone();
                count += 1;
            }
            repaint();
        }
    });
    int[] array = new int[]{1,2,3};
    int[] unOrdered = array;
    Random rand = new Random();
    int n = 50; // default size of array to sort is 100
    ArrayList<int[]> arrayL = new ArrayList<int[]>(n * n);
    int[] mergedArray;//TODO make a new isntance var just for merging algo
    boolean compare = false;
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

        stop = new JButton("Stop");
        stop.setFocusPainted(false); //remove the ugly border around the text
        stop.setToolTipText("Stop sorting");
        add(stop);
        stop.setEnabled(false);

        //listens for inputs
        start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == start){
                    if (isNumber(numberField.getText())) {
                        randomArray(Integer.parseInt(numberField.getText()));
                    } else {
                        randomArray(n);
                    }
                    unOrdered = array.clone();
                    repaint();
                    if(stopBool){
                        stopBool = !stopBool;
                        start.setEnabled(false);
                        tm.start();
                    }else {
                        if (insertionSort.isSelected() && mergeSort.isSelected()) { //side by side comparison
                            System.out.println("both");
                            compare = true;
                        } else if (insertionSort.isSelected()) { //just insertion sort
                            insertionSortAlgo(array);
                            start.setEnabled(false);
                            tm.start();
                        } else if (mergeSort.isSelected()) { //just merge sort
                            array = new int[]{15,14,13,12,11,10,9,8,7,6,5,4,3,2,1};
                            for(int i = 0; i < array.length; i++){
                                System.out.print(array[i] + " ");
                            }
                            System.out.println();
                            mergeSortAlgo(array, 0, array.length - 1);
                            start.setEnabled(false);
                            tm.start();
                            printArrayList();
                        } else { //nothing cuz no checkbox checked
                            randomArray(n);
                            repaint();
                        }
                    }
                }
            }
        });
        stop.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                stopBool = !stopBool;
            }
        });
        //dont need keylistener cuz only want what the user typed
        //which you can just get with .getText()

        frame.add(this);
        frame.setVisible(true);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D main = (Graphics2D) g;

        if(!compare){
            //TODO make this shit scalable, the height, width, and colors
            double width = (double)(windowWidth - 100) / array.length;
            for(int i = 0; i < array.length; i++) {
                double height = array[i] * ((double) (windowHeight - 100) / maxArray(array));
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
                main.setColor(Color.BLACK);
                main.fill(new Rectangle2D.Double(xPos, yPos, width, height));
            }
        }else {//TODO check if this is correct
            double width = (double)((windowWidth / 2) - 50) / array.length;
            for(int i = 0; i < array.length; i++) {
                double height = array[i] * ((double) (windowHeight - 100) / maxArray(array));
                double xPos = 25 + width * i;
                double yPos = 50;
                main.setColor(Color.BLACK);
                main.fill(new Rectangle2D.Double(xPos, yPos, width, height));
            }
            int testing = 0;
            for(int i = array.length - 1; i >= 0; i--) {
                double height = array[i] * ((double) (windowHeight - 100) / maxArray(array));
                double xPos = (width + 25 + width * array.length) + width * testing;
                double yPos = 50;
                main.setColor(Color.BLACK);
                main.fill(new Rectangle2D.Double(xPos, yPos, width, height));
                testing += 1;
            }
        }
    }
    public void randomArray(int size){ //fisher yates shuffle
        int[] arrayNew = new int[size];
        for(int i = 0; i < arrayNew.length; i++){
            arrayNew[i] = i + 1;
        }
        for(int i = 0; i < arrayNew.length; i++){
            //random index past current
            int ridx = i + rand.nextInt(arrayNew.length - i);
            //swap values
            int temp = arrayNew[ridx];
            arrayNew[ridx] = arrayNew[i];
            arrayNew[i] = temp;
        }
        array = arrayNew.clone();
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

    public void insertionSortAlgo(int[] arr){
        for(int i = 1; i < arr.length; i++){
            int j = i;
            while(j > 0 && arr[j] < arr[j - 1]){
                int temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                arrayL.add(arr.clone());
                j -= 1;
            }
        }
        array = unOrdered.clone(); //restarts array otherwise its gonna paint ordered at first frame
    }

    public void mergeSortAlgo(int[] arr, int start, int end){ //TODO mix these up, write ur own, comment it, understand this
        //keeps dividing by half each recursion
        int mid = (start + end) / 2;
        if(start < end){
            mergeSortAlgo(arr, start, mid);
            mergeSortAlgo(arr, mid + 1, end);
        }
        // merge solved pieces to get solution to original problem
        int i = 0, first = start, last = mid + 1;
        int[] tmp = new int[end - start + 1];

        while (first <= mid && last <= end) {
            tmp[i++] = array[first] < array[last] ? array[first++] : array[last++];
        }
        while (first <= mid) {
            tmp[i++] = array[first++];
        }
        while (last <= end) {
            tmp[i++] = array[last++];
        }
        i = 0;
        while (start <= end) {
            array[start++] = tmp[i++];
            arrayL.add(array);
        }
    }
    public void mergeToList(int[] arr, int lidx, int ridx){
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        arrayL.add(arr.clone());
    }
    public void printArrayList(){
        for (int[] cur : arrayL) {
            for (int value : cur) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
    public boolean isNumber(String in){
        try{
            int test = Integer.parseInt(in);
            return true;
        }catch(Exception ie){
            return false;
        }
    }
    public void printArr(int[] ar){
        for (int value : ar) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
    public void compareScreen(){

    }
    public void oneScreen(){
    }
    public static void main(String[] args){
        GUI go = new GUI();
        go.setup();
    }
}
