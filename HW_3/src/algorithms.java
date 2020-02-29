import java.util.ArrayList;
import java.util.Random;

public class algorithms {
    int n = 100; // default n size 100
    //instance variables
    int[] origArray = new int[n]; // to keep orig array in case user wants to compare
    int[] toSort; // the array to be sorted and updated
    Random rand = new Random(); // for the shuffling method
    //ararylist for the animation
    private ArrayList<int[]> sorting = new ArrayList<int[]>(n * n);

    public algorithms(){ //inits arrays and algos
        randomArray(n);
        toSort = origArray.clone(); // leaves origarray alone just in case user wants to compare
        // origarray would be the same no matter what, allowing user to compare
    }

    public ArrayList<int[]> sort(String algo){
        //checks which the user selected and does the sort
        if(algo.equals("Insertion")){
            insertionSort();
        }else if(algo.equals("Merge")){
            mergeSort();
        }else if(algo.equals("Heap")){
            heapSort();
        }else if(algo.equals("Quick")){
            quickSort();
        }else{
            System.out.println("tf u doing");
        }
        //returns the arraylist of the animation
        return sorting;
    }

    public void insertionSort(){
        sorting.clear(); // clears the arraylist - to reset
        toSort = origArray.clone(); // resets the array to sort
        for(int i = 1; i < toSort.length; i++){//iterates over the array
            int j = i;//makes j as the current index
            while(j > 0 && toSort[j] < toSort[j - 1]){//while elements on the left are less than the current, swaps
                int temp = toSort[j];
                //swaps
                toSort[j] = toSort[j - 1];
                toSort[j - 1] = temp;
                //adds current array to list -> for the animation
                //its like frames, each swap i add to list
                sorting.add(toSort.clone());
                j -= 1;
            }
        }
    }

    public void mergeSort(){
        sorting.clear();
        toSort = origArray.clone();
        //calls the actual merge sort method
        mergeSortAlgo(toSort, 0, toSort.length - 1);
    }
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
                sorting.add(arr.clone());//adds current array to list
                leftIndex++;
            }
            else
            {
                arr[k] = Right[rightIndex];
                sorting.add(arr.clone());//adds current array to list
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
            sorting.add(arr.clone());//adds current array to list
            leftIndex++;
            k++;
        }
        //same explanation as above but with the right array
        while (rightIndex < rightn)
        {
            arr[k] = Right[rightIndex];
            sorting.add(arr.clone());//adds current array to list
            rightIndex++;
            k++;
        }
    }

    public void heapSort(){
        //resets the vars
        sorting.clear();
        toSort = origArray.clone();

        //build the heap by rearranging the array
        for(int i = toSort.length / 2 - 1; i >= 0; i--){
            //heapify to maintain heap strucutre
            heapify(toSort.length, i);
        }
        //by the end, valid heap

        //iterates over array from end to beginning
        for(int i = toSort.length - 1; i >= 0; i--){
            //swaps - jave sucks
            int javasucks = toSort[0];
            toSort[0] = toSort[i];
            toSort[i] = javasucks;
            //adds to arraylist
            sorting.add(toSort.clone());

            //call heapify to maintain heap
            heapify(i, 0);
        }
    }
    //heapify at node
    public void heapify(int length, int i){
        int largestidx = i; //largest at i - root
        int left = 2 * i + 1; // left child
        int right = 2 * i + 2; //right child

        //if left is larger
        if(left < length && toSort[left] > toSort[largestidx]){
            largestidx = left; //makes the largestidx to be the left one
        }

        //right child largest
        if(right < length && toSort[right] > toSort[largestidx]){
            largestidx = right; //makes the largestidx to be the right one
        }

        //if largest not root then not valid
        if(largestidx != i){
            //swaps the value -- also javas ucks
            int javesuxsoswap = toSort[i];
            toSort[i] = toSort[largestidx];
            toSort[largestidx] = javesuxsoswap;
            //adds the current array to arraylist as part of animation
            sorting.add(toSort.clone());

            //heapify again cuz this changes it up
            heapify(length, largestidx);
        }
    }

    public void quickSort(){
        sorting.clear();
        toSort = origArray.clone();

        //calls helper function to actually sort
        quickSortHelper(0, toSort.length - 1);
    }

    public void quickSortHelper(int start, int end){
        //checks if start is less than end
        //cuz stupid if it is
        if(start < end){
            //partition index calls partition that returns idx
            //and also puts toSort[partitionidx] in the right place
            int partitionidx = partition(start, end);

            //sorts elements before and after the partitions
            //recursive - nice
            //System.out.println("test");
            quickSortHelper(start, partitionidx - 1); //befre
            quickSortHelper(partitionidx + 1, end); //after
        }
    }
    public int partition(int start, int end){
        int pivot = toSort[end]; //pivot
        int i = start - 1; //idx of the bot

        //iterates from start to end
        for(int j = start; j < end; j++){
            if(toSort[j] < pivot){//if element is less than pivot
                i++; //move point up

                //swaps java sucks
                int temp = toSort[i];
                toSort[i] = toSort[j];
                toSort[j] = temp;
                //adds to arraylist
                sorting.add(toSort.clone());
            }
        }

        //swaps one last time, pivot and i + 1
        int javesucks = toSort[i + 1];
        toSort[i + 1] = toSort[end];
        toSort[end] = javesucks;
        //adds to arraylist
        sorting.add(toSort.clone());

        //return partition idx
        return i + 1;
    }

    public void setSize(int size){
        // changes the n, size of the arrays to sort
        n = size;
        //makes new array and changes the toSort array
        randomArray(n);
        toSort = origArray.clone();
    }

    public void randomArray(int size){ //fisher yates shuffle
        //makes a new array
        int[] arrayNew = new int[size];
        //updates n
        n = size;
        for(int i = 0; i < arrayNew.length; i++){
            arrayNew[i] = i + 1;
        }//init the array with 0 to n;
        for(int i = 0; i < arrayNew.length; i++){ // shuffles the array
            //random index past current -> thats why random
            int ridx = i + rand.nextInt(arrayNew.length - i);
            //swap values
            int temp = arrayNew[ridx];
            arrayNew[ridx] = arrayNew[i];
            arrayNew[i] = temp;
        }
        // new origarray array
        origArray = arrayNew.clone();
    }
}
