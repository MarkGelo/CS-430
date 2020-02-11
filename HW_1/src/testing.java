import java.util.ArrayList;
import java.util.Random;

public class testing{
    static int[] array;
    public static void main(String[] args){
        array = new int[]{15,14,13,12,11,10,9,8,7,6,5,4,3,2,1};
        mergeSort(array,0, array.length - 1);
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
    public static void mergeSort(int[] arr, int start, int end){
        //keeps dividing by half each recursion
        int mid = (start + end) / 2;
        if(start < end){
            mergeSort(arr, start, mid);
            mergeSort(arr, mid + 1, end);
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
        }
    }
}