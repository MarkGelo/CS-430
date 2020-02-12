import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class window extends JPanel{
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
    //Timer for the animation
    //just made the made the actionlistener here cuz didnt want to clutter, and also had another actionlistner
    //later on in the class
    Timer tm = new Timer(1, new ActionListener(){
        public void actionPerformed(ActionEvent e){
            //makes it so the stop button can be used
            stop.setEnabled(true);
            numberField.setEnabled(false); //cant use the N:
            if(doMerging && doInsertion){//if comparing
                if (stopBool) {//if user clicked stop button
                    ((Timer) e.getSource()).stop();
                    start.setEnabled(true);
                    stop.setEnabled(false);
                } else if (count == arrayL.size() && mergedCount == mergedL.size()) {//once both are finished
                    ((Timer) e.getSource()).stop();
                    //basically resets all the variables the keep count/or those that disable buttons and stuff
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
                    //stops the time
                    tm.stop();
                } else {
                    if(mergedL.size() < arrayL.size()){ //just in case if merging takes more steps
                        if(count < mergedL.size()){//while the insertion count is less than the size of basically swaps
                            mergedArray = mergedL.get(mergedCount).clone();//gets the array from arraylist
                            mergedCount += 1;
                            mergeSwap += 1;
                        }
                        array = arrayL.get(count).clone();//gets array from arraylist
                        count += 1;
                        insertionSwap += 1;
                    }else{
                        if(mergedCount < arrayL.size()){//while mergedcount is less than size
                            //iterates over the insertion arraylists
                            array = arrayL.get(count).clone();
                            count += 1;
                            insertionSwap += 1;
                        }
                        mergedArray = mergedL.get(mergedCount).clone(); //gets the array
                        mergedCount += 1;
                        mergeSwap += 1;
                    }

                }
            }
            else if(doMerging){//if only merge sort
                if (stopBool) {//if user clicked stop
                    ((Timer) e.getSource()).stop();
                    start.setEnabled(true);
                    stop.setEnabled(false);
                } else if (mergedCount == mergedL.size()) {//if finished animating
                    //resets the variable and stops the time
                    ((Timer) e.getSource()).stop();
                    mergedCount = 0;
                    mergedL.clear();
                    start.setEnabled(true);
                    stop.setEnabled(false);
                    numberField.setEnabled(true);
                    doMerging = false;
                    tm.stop();
                } else {
                    //basically iterates over the arraylist -> animation
                    mergedArray = mergedL.get(mergedCount).clone();
                    mergedCount += 1;
                    mergeSwap += 1;
                }
            }
            else if(doInsertion){//if only doing insertion
                if (stopBool) {//if user clicked stop
                    ((Timer) e.getSource()).stop();
                    start.setEnabled(true);
                    stop.setEnabled(false);
                } else if (count == arrayL.size()) {//if finished animating
                    //if gone through all the arraylist
                    //resets the vars
                    ((Timer) e.getSource()).stop();
                    count = 0;
                    arrayL.clear();
                    start.setEnabled(true);
                    stop.setEnabled(false);
                    numberField.setEnabled(true);
                    doInsertion = false;
                    tm.stop();
                } else {
                    //grabs the array from arraylist and increment by one - essentially iterating over it
                    array = arrayL.get(count).clone();
                    count += 1;
                    insertionSwap += 1;
                }
            }
            repaint();//repaints the board
        }
    });
    int[] array = new int[]{1,2,3};
    int[] unOrdered = array;
    Random rand = new Random();
    int n = 100; // default size of array to sort is 100
    ArrayList<int[]> arrayL = new ArrayList<int[]>(n);//arraylist for keeping tract of the changes
    ArrayList<int[]> mergedL = new ArrayList<int[]>(n);//just makes it easier
    int[] mergedArray = new int[]{1,2,3};
    boolean merging;
    boolean compare = false;
    public void setup(){//setups the gui
        //intializes random arrays for insertion and merge - all in background tho
        randomArray(n);
        randomMergedArray(n);

        //sets the frame and rules
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

        //button to start sorting
        start = new JButton("Sort");
        start.setFocusPainted(false); //remove the ugly border around the text
        start.setToolTipText("Click to start sorting");
        add(start);

        //button to stop the sorting
        stop = new JButton("Stop");
        stop.setFocusPainted(false); //remove the ugly border around the text
        stop.setToolTipText("Stop sorting");
        add(stop);
        stop.setEnabled(false);

        //listens for inputs
        start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == start){//if user clicked sort
                    if (isNumber(numberField.getText())) {//if the user inputted numbers in the text field
                        if(mergeSort.isSelected() && insertionSort.isSelected()){
                            //if both are selected then random array for both accordign to the number input
                            randomMergedArray(Integer.parseInt(numberField.getText()));
                            randomArray(Integer.parseInt(numberField.getText()));
                        }else if(mergeSort.isSelected()) {
                            //if only merge is selected
                            randomMergedArray(Integer.parseInt(numberField.getText()));
                        }else{
                            //if insertion is selected
                            randomArray(Integer.parseInt(numberField.getText()));
                        }
                    } else {
                        //if no user input on length of array
                        if(mergeSort.isSelected() && insertionSort.isSelected()){
                            //make n to 100 again for default
                            n = 100;
                            //merge and insertion default amount
                            randomMergedArray(n);
                            randomArray(n);
                        }else if(mergeSort.isSelected()){//just merge
                            //make n to 100 again for default
                            n = 100;
                            randomMergedArray(n);
                        }else{
                            //make n to 100 again for default
                            n = 100;
                            randomArray(n);
                        }
                    }
                    unOrdered = array.clone();
                    repaint();
                    if(stopBool){//if user clicked stop
                        stopBool = !stopBool;
                        start.setEnabled(false);
                        tm.start();
                    }else {
                        //if user wants to compare
                        if (insertionSort.isSelected() && mergeSort.isSelected()) { //side by side comparison
                            //intializes variables
                            insertionSwap = 0;
                            mergeSwap = 0;
                            doInsertion = true;
                            doMerging = true;
                            //calls the algorithms
                            insertionSortAlgo(array);
                            mergeSortAlgo(mergedArray, 0, mergedArray.length - 1);
                            compare = true;
                            merging = true;
                            //disables the sort button, cuz starts sorting
                            start.setEnabled(false);
                            tm.start();
                        } else if (insertionSort.isSelected()) { //just insertion sort
                            //init vars
                            insertionSwap = 0;
                            mergeSwap = 0;
                            compare = false;
                            doInsertion = true;
                            mergingAgainCuzIDKwatswrong = false;
                            //cals insertion
                            insertionSortAlgo(array);
                            start.setEnabled(false);
                            tm.start();
                        } else if (mergeSort.isSelected()) { //just merge sort
                            //init vars
                            insertionSwap = 0;
                            mergeSwap = 0;
                            compare = false;
                            doMerging = true;
                            mergingAgainCuzIDKwatswrong = true;
                            //cals merge
                            mergeSortAlgo(mergedArray, 0, mergedArray.length - 1);
                            start.setEnabled(false);
                            tm.start();
                            //printArrayList();
                        } else { //nothing cuz no checkbox checked
                            //just makes a new array and repaints so shows new random array
                            compare = false;
                            randomArray(n);
                            repaint();
                        }
                    }
                }
            }
        });
        stop.addActionListener(new ActionListener(){//if user clicked stop
            public void actionPerformed(ActionEvent e){
                //just makes stopBool opposite of what it already was
                stopBool = !stopBool;
            }
        });
        //dont need keylistener cuz only want what the user typed
        //which you can just get with .getText()

        //adds all of the things added above to the frame.
        frame.add(this);
        frame.setVisible(true);
    }
    @Override
    public void paintComponent(Graphics g){//for the paintin and for the animation
        super.paintComponent(g);
        Graphics2D main = (Graphics2D) g;//for the double, makes it easier
        double width;
        if(!compare){ //if not comparing then checks if either doing insertion or merging
            if(mergingAgainCuzIDKwatswrong){
                //adds information like the amount of swaps
                g.drawString("Merge Swaps: ", 25,25);
                g.drawString(Integer.toString(mergeSwap), 125,25);
                width = (double)(windowWidth - 100) / mergedArrayUnordered.length;
                //paints the array as bar graphs
                for(int i = 0; i < mergedArray.length; i++) {
                    double height = mergedArray[i] * ((double) (windowHeight - 100) / maxArray(mergedArrayUnordered));
                    double xPos = 50 + width * i;
                    double yPos = 50;

                    main.setColor(Color.BLACK);
                    main.fill(new Rectangle2D.Double(xPos, yPos, width, height));
                }
            }else{
                //adds info of amount of swaps
                g.drawString("Insertion Swaps: ", 25,25);
                g.drawString(Integer.toString(insertionSwap), 125,25);
                width = (double)(windowWidth - 100) / array.length;
                //paints the array
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
        }else {//if user wants to compare
            //ads info
            g.drawString("Insertion Swaps: ", 25,25);
            g.drawString(Integer.toString(insertionSwap), 125,25);
            //for insertion
            width = (double)((windowWidth / 2) - 50) / array.length;
            //same length for merge and insertion anyways
            //paints the insertion sort array
            for(int i = 0; i < array.length; i++) {
                double height = array[i] * ((double) (windowHeight - 100) / maxArray(array));
                double xPos = 25 + width * i;
                double yPos = 50;
                main.setColor(Color.BLACK);
                main.fill(new Rectangle2D.Double(xPos, yPos, width, height));
            }
            //ads info
            g.drawString("Merge Swaps: ", windowWidth - 150,25);
            g.drawString(Integer.toString(mergeSwap), windowWidth - 50,25);
            //for merge
            //paints the merge sort array
            int testing = 0;
            for(int i = mergedArray.length - 1; i >= 0; i--) {
                double height = mergedArray[i] * ((double) (windowHeight - 100) / maxArray(mergedArrayUnordered));
                double xPos = (width/2 + 25 + width * mergedArrayUnordered.length) + width * testing;
                double yPos = 50;
                main.setColor(Color.DARK_GRAY);
                main.fill(new Rectangle2D.Double(xPos, yPos, width, height));
                testing += 1;
            }
        }
    }
    public void randomArray(int size){ //fisher yates shuffle
        //makes a new array
        int[] arrayNew = new int[size];
        //updates n
        n = size;
        for(int i = 0; i < arrayNew.length; i++){
            arrayNew[i] = i + 1;
        }//init the array with 0 to n;
        for(int i = 0; i < arrayNew.length; i++){
            //random index past current -> thats why random
            int ridx = i + rand.nextInt(arrayNew.length - i);
            //swap values
            int temp = arrayNew[ridx];
            arrayNew[ridx] = arrayNew[i];
            arrayNew[i] = temp;
        }
        array = arrayNew.clone();
    }
    public void randomMergedArray(int size){
        //same as above but for the merged array
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

    public void insertionSortAlgo(int[] arr){//insertion sot algorithm

        for(int i = 1; i < arr.length; i++){//iterates over the array
            int j = i;//makes j as the current index
            while(j > 0 && arr[j] < arr[j - 1]){//while elements on the left are less than the current, swaps
                int temp = arr[j];
                //swaps
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                //adds current array to list -> for the animation
                //its like frames, each swap i add to list
                arrayL.add(arr.clone());
                j -= 1;
            }
        }
        array = unOrdered.clone(); //restarts array otherwise its gonna paint ordered at first frame
    }

    //merge sort
    public void merge(int[] arr, int lidx, int midx, int ridx){
        //lenght of the left and right arrays
        int leftn = midx - lidx + 1;
        int rightn = ridx - midx;

        //instantiating the left and right arrays
        int Left[] = new int [leftn];
        int Right[] = new int [rightn];

        //copies the data from original to the left and right
        for (int i=0; i < leftn; ++i) {
            Left[i] = arr[lidx + i];
        }
        for (int j=0; j < rightn; ++j) {
            Right[j] = arr[midx + 1 + j];
        }

        //merges the two arrays and updates the original array
        //initializes leftIndex and rightIndex to iterate over the left and right arrays
        //if it is added to the original array, then where it came from, it would increment
        int leftIndex = 0;
        int rightIndex = 0;
        int k = lidx; //to properly align where the elements to go, left
        //System.out.println(k);
        //while it hasnt gone through all the elements in the left and right array
        //between the two arrays check which one is lowest, and then updates original array
        while (leftIndex < leftn && rightIndex < rightn)
        {
            if (Left[leftIndex] <= Right[rightIndex])
            {
                arr[k] = Left[leftIndex];
                mergedL.add(arr.clone());//adds current array to list
                leftIndex++;
            }
            else
            {
                arr[k] = Right[rightIndex];
                mergedL.add(arr.clone());//adds current array to list
                rightIndex++;
            }
            k++;
        }
        //if there are some left on the left array then just adds to original array
        //possible because all the elements in right array have been added
        //all thats left is the sorted elements from the left array
        while (leftIndex < leftn)
        {
            arr[k] = Left[leftIndex];
            mergedL.add(arr.clone());//adds current array to list
            leftIndex++;
            k++;
        }
        //same explanation as above but with the right array
        while (rightIndex < rightn)
        {
            arr[k] = Right[rightIndex];
            mergedL.add(arr.clone());//adds current array to list
            rightIndex++;
            k++;
        }
    }

    //main method of merge sort
    public void mergeSortAlgo(int[] arr, int lidx, int ridx) {
        if (lidx < ridx)
        {
            //middle to split the array in half
            int mid = (lidx + ridx) / 2;

            //recursive, sort left and right array
            mergeSortAlgo(arr, lidx, mid);
            //left is from lidx to middle
            mergeSortAlgo(arr , mid + 1, ridx);
            //right is from mid to ridx

            //merge together
            merge(arr, lidx, mid, ridx);
        }
    }

    public boolean isNumber(String in){//for the nubmberfield, checks if the string is a number
        try{
            int test = Integer.parseInt(in);
            return true;
        }catch(Exception ie){
            return false;
        }
    }

    public static void main(String[] args){//main method
        window go = new window();
        go.setup();
    }

}
