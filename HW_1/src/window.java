import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.spi.BreakIteratorProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class window extends JPanel{
    //TODO add swap, array accesses, count as data for the algorithm
    JFrame frame;
    JButton start;
    JButton stop;
    JTextField numberField;
    JCheckBox insertionSort;
    JCheckBox mergeSort;
    int insertionSwap = 0;
    int mergeSwap = 0;
    static int windowWidth = 1000;
    static int windowHeight = 600;
    int count = 0;
    int mergedCount = 0;
    boolean stopBool = false;
    boolean doMerging = false;
    boolean doInsertion = false;
    boolean mergingAgainCuzIDKwatswrong = false;
    int[] mergedArrayUnordered;
    Timer tm = new Timer(5, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            stop.setEnabled(true);
            numberField.setEnabled(false);
            if(doMerging && doInsertion){
                if (stopBool) {
                    ((Timer) e.getSource()).stop();
                    start.setEnabled(true);
                    stop.setEnabled(false);
                } else if (count == arrayL.size() && mergedCount == mergedL.size()) {
                    ((Timer) e.getSource()).stop();
                    count = 0;
                    mergedCount = 0;
                    arrayL.clear();
                    mergedL.clear();
                    start.setEnabled(true);
                    stop.setEnabled(false);
                    merging = false;
                    numberField.setEnabled(true);
                    doMerging = false;
                    doInsertion = false;
                    tm.stop();
                } else {
                    if(mergedL.size() < arrayL.size()){ //just in case if merging takes more steps
                        if(count < mergedL.size()){
                            mergedArray = mergedL.get(mergedCount).clone();
                            mergedCount += 1;
                            mergeSwap += 1;
                        }
                        array = arrayL.get(count).clone();
                        count += 1;
                        insertionSwap += 1;
                    }else{
                        if(mergedCount < arrayL.size()){
                            array = arrayL.get(count).clone();
                            count += 1;
                            insertionSwap += 1;
                        }
                        mergedArray = mergedL.get(mergedCount).clone();
                        mergedCount += 1;
                        mergeSwap += 1;
                    }

                }
            }
            else if(doMerging){
                if (stopBool) {
                    ((Timer) e.getSource()).stop();
                    start.setEnabled(true);
                    stop.setEnabled(false);
                } else if (mergedCount == mergedL.size()) {
                    ((Timer) e.getSource()).stop();
                    mergedCount = 0;
                    mergedL.clear();
                    start.setEnabled(true);
                    stop.setEnabled(false);
                    numberField.setEnabled(true);
                    doMerging = false;
                    tm.stop();
                } else {
                    mergedArray = mergedL.get(mergedCount).clone();
                    mergedCount += 1;
                    mergeSwap += 1;
                }
            }
            else if(doInsertion){
                if (stopBool) {
                    ((Timer) e.getSource()).stop();
                    start.setEnabled(true);
                    stop.setEnabled(false);
                } else if (count == arrayL.size()) {
                    ((Timer) e.getSource()).stop();
                    count = 0;
                    arrayL.clear();
                    start.setEnabled(true);
                    stop.setEnabled(false);
                    numberField.setEnabled(true);
                    doInsertion = false;
                    tm.stop();
                } else {
                    array = arrayL.get(count).clone();
                    count += 1;
                    insertionSwap += 1;
                }
            }
            repaint();
        }
    });
    int[] array = new int[]{1,2,3};
    int[] unOrdered = array;
    Random rand = new Random();
    int n = 100; // default size of array to sort is 100
    ArrayList<int[]> arrayL = new ArrayList<int[]>(n);
    ArrayList<int[]> mergedL = new ArrayList<int[]>(n);
    int[] mergedArray = new int[]{1,2,3};//TODO make a new isntance var just for merging algo
    boolean merging;
    boolean compare = false;
    //TODO why tf does it have some missing rectangles when n = 500
    public void setup(){
        randomArray(n);
        randomMergedArray(n);
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
                        if(mergeSort.isSelected() && insertionSort.isSelected()){
                            randomMergedArray(Integer.parseInt(numberField.getText()));
                            randomArray(Integer.parseInt(numberField.getText()));
                        }else if(mergeSort.isSelected()) {
                            randomMergedArray(Integer.parseInt(numberField.getText()));
                        }else{
                            randomArray(Integer.parseInt(numberField.getText()));
                        }
                    } else {
                        if(mergeSort.isSelected() && insertionSort.isSelected()){
                            randomMergedArray(n);
                            randomArray(n);
                        }else if(mergeSort.isSelected()){
                            randomMergedArray(n);
                        }else{
                            n = 100;
                            randomArray(n);
                        }
                    }
                    unOrdered = array.clone();
                    repaint();
                    if(stopBool){
                        stopBool = !stopBool;
                        start.setEnabled(false);
                        tm.start();
                    }else {
                        if (insertionSort.isSelected() && mergeSort.isSelected()) { //side by side comparison
                            insertionSwap = 0;
                            mergeSwap = 0;
                            doInsertion = true;
                            doMerging = true;
                            insertionSortAlgo(array);
                            mergeSortAlgo(mergedArray, mergedArray.length);
                            compare = true;
                            merging = true;
                            start.setEnabled(false);
                            tm.start();
                        } else if (insertionSort.isSelected()) { //just insertion sort
                            insertionSwap = 0;
                            mergeSwap = 0;
                            compare = false;
                            doInsertion = true;
                            mergingAgainCuzIDKwatswrong = false;
                            insertionSortAlgo(array);
                            start.setEnabled(false);
                            tm.start();
                        } else if (mergeSort.isSelected()) { //just merge sort
                            insertionSwap = 0;
                            mergeSwap = 0;
                            compare = false;
                            doMerging = true;
                            mergingAgainCuzIDKwatswrong = true;
                            mergeSortAlgo(mergedArray, mergedArray.length);
                            start.setEnabled(false);
                            tm.start();
                            //printArrayList();
                        } else { //nothing cuz no checkbox checked
                            compare = false;
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
        double width;
        if(!compare){ //if not comparing then checks if either doing insertion or merging
        //TODO make this shit scalable, the height, width, and colors
        if(mergingAgainCuzIDKwatswrong){
            g.drawString("Merge Swaps: ", 25,25);
            g.drawString(Integer.toString(mergeSwap), 125,25);
            width = (double)(windowWidth - 100) / mergedArrayUnordered.length;
            for(int i = 0; i < mergedArray.length; i++) {
                double height = mergedArray[i] * ((double) (windowHeight - 100) / maxArray(mergedArrayUnordered));
                double xPos = 50 + width * i;
                double yPos = 50;

                main.setColor(Color.BLACK);
                main.fill(new Rectangle2D.Double(xPos, yPos, width, height));
            }
        }else{
            g.drawString("Insertion Swaps: ", 25,25);
            g.drawString(Integer.toString(insertionSwap), 125,25);
            width = (double)(windowWidth - 100) / array.length;
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
        }
        }else {
            g.drawString("Insertion Swaps: ", 25,25);
            g.drawString(Integer.toString(insertionSwap), 125,25);
            //for insertion
            width = (double)((windowWidth / 2) - 50) / array.length;
            //same length for merge and insertion anyways
            for(int i = 0; i < array.length; i++) {
                double height = array[i] * ((double) (windowHeight - 100) / maxArray(array));
                double xPos = 25 + width * i;
                double yPos = 50;
                main.setColor(Color.BLACK);
                main.fill(new Rectangle2D.Double(xPos, yPos, width, height));
            }
            g.drawString("Merge Swaps: ", windowWidth - 150,25);
            g.drawString(Integer.toString(mergeSwap), windowWidth - 50,25);
            //for merge
            int testing = 0;
            for(int i = mergedArray.length - 1; i >= 0; i--) {
                double height = mergedArray[i] * ((double) (windowHeight - 100) / maxArray(mergedArrayUnordered));
                double xPos = (width/2 + 25 + width * mergedArrayUnordered.length) + width * testing;
                double yPos = 50;
                main.setColor(Color.BLACK);
                main.fill(new Rectangle2D.Double(xPos, yPos, width, height));
                testing += 1;
            }
        }
    }
    public void randomArray(int size){ //fisher yates shuffle
        int[] arrayNew = new int[size];
        n = size;
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
    public void randomMergedArray(int size){
        int[] arrayNew = new int[size];
        n = size;
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
        mergedArray = arrayNew.clone();
        mergedArrayUnordered = arrayNew.clone();
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

    public void mergeSortAlgo(int[] arr, int len){ //TODO mix these up, write ur own, comment it, understand this
        if(len < 2){
            return;
        }
        int mid = len / 2; // round down
        int[] left = new int[mid];
        int[] right = new int[n - mid];

        for(int i = 0; i < mid; i++){
            left[i] = arr[i];
        }
        for(int i = mid ; i < len; i++){
            right[i - mid] = arr[i];
        }
        mergeSortAlgo(left, mid);
        mergeSortAlgo(right, len - mid);
        merge(arr, left, right, mid, len - mid);
        array = unOrdered.clone();
    }
    //TODO animate it correctly
    public void merge(int[] arr, int[] left, int[] right, int lidx, int ridx){
        int i = 0;
        int j = 0;
        int k = 0;
        while(i < lidx && j < ridx){
            if(left[i] <= right[j]){
                arr[k++] = left[i++];
                //arrayL.add(arr.clone());
                mergeToList(arr, lidx, ridx);
            }else{
                arr[k++] = right[j++];
                //arrayL.add(arr.clone());
                mergeToList(arr, lidx, ridx);
            }
        }
        while(i < lidx){
            arr[k++] = left[i++];
            //arrayL.add(arr.clone());
            mergeToList(arr, lidx, ridx);
        }
        while(j < ridx){
            arr[k++] = right[j++];
            //arrayL.add(arr.clone());
            mergeToList(arr, lidx, ridx);
        }
    }
    public void mergeToList(int[] arr, int lidx, int ridx){
        /*
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
         */

        mergedL.add(arr.clone());
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
        window go = new window();
        go.setup();
    }
}
