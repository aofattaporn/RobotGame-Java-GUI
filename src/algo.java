public class algo {

    public static void main(String[] args) {
        int[] x = {1, 2, 3, 4, 5};

        int rotate = 0;


        System.out.print("{ ");

        for (int i: rotation(x, rotate)) {
            System.out.print(i + ", ");
        }
        System.out.print(" }");

    }

    private static int[] rotation(int[] arr, int rotate){

        int temp = 0;
        for (int i = arr.length - 1; i >= 0; i--){
            if (i == arr.length - 1){
                temp = i;
            }else {
                if (i == 0) arr[i] = temp;
                else arr[i] = arr[i - 1];
            }
        }

        return arr;
    }
}
