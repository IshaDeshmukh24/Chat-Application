import java.util.Scanner; 

public class Sort
{
    public static void bubbleSort() //declare an array 
    {
        
        int array[] = new int[5];
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number 1: ");
        array[0] = sc.nextInt();
        System.out.print("Enter number 2: ");
        array[1] = sc.nextInt();
        System.out.print("Enter number 3: ");
        array[2] = sc.nextInt();
        System.out.print("Enter number 4: ");
        array[3] = sc.nextInt();
        System.out.print("Enter number 5: ");
        array[4] = sc.nextInt();
        System.out.println();
        System.out.println("Your numbers: " + array[0] + " " + array[1] + " " + array[2] + " " + array[3] + " " + array[4]);

        // Sorting begins here
        int n = 5;
        int temp;
        for (int i = 0; i < n; i++){
            for (int j = 1; j < (n-i); j++){
                //swap elements
                temp = array[j-1];
                array[j-1] = array[j];
                array[j] = temp;
            }    
        }
        System.out.println("Your numbers: " + array[0] + " " + array[1] + " " + array[2] + " " + array[3] + " " + array[4]);
        
    }
}