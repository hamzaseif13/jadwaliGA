package jadwaliGenetics.model;

public class test {
    public static void main(String[] args) {
        int [] arr = new int[]{1,3,5,1,13,2,-1};
        int min1 = arr[0];
        int min2 = arr[1];

        int index1 = 0;
        int index2 = 1;

        if (min1 > min2) {
            int temp = min1;
            min1 = min2;
            min2 = temp;

            index1 = 1;
            index2 = 0;
        }

        for (int i = 2; i < arr.length; i++) {
            if (arr[i] < min1) {
                int temp = min1;
                min1 = arr[i];
                min2 = temp;
                index2 = index1;
                index1 = i;
            } else if (arr[i] < min2) {
                min2 = arr[i];
                index2 = i;
            }
        }

    }
}
