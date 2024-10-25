import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class App {

    public static int[] createRandomArray(int arrayLength) {
        Random rand = new Random();
        int[] array = new int[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            array[i] = rand.nextInt(101);
        }
        return array;
    }

    public static void writeArrayToFile(int[] array, String filename, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(message);
            for (int value : array) {
                writer.write(value + " ");
            }
            writer.write("\n\n"); // Add a newline for better separation
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] readFileToArray(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.lines().mapToInt(Integer::parseInt).toArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new int[0];
        }
    }

    public static void bubbleSort(int[] array, String filename) {
        long startTime = System.currentTimeMillis();
        boolean swapped;
        for (int i = 0; i < array.length - 1; i++) {
            swapped = false;
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }
            writeArrayToFile(array, filename, "Bubble Sort Step " + (i + 1) + ":\n");
            if (!swapped) {
                break;
            }
        }
        long endTime = System.currentTimeMillis();
        writeArrayToFile(array, filename, "Total Bubble Sort Time: " + (endTime - startTime) + " ms\n");
    }

    public static void mergeSort(int[] array, String filename) {
        long startTime = System.currentTimeMillis();
        mergeSortHelper(array, 0, array.length - 1, filename);
        long endTime = System.currentTimeMillis();
        writeArrayToFile(array, filename, "Total Merge Sort Time: " + (endTime - startTime) + " ms\n");
    }

    private static void mergeSortHelper(int[] array, int left, int right, String filename) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortHelper(array, left, mid, filename);
            mergeSortHelper(array, mid + 1, right, filename);
            merge(array, left, mid, right, filename);
        }
    }

    private static void merge(int[] array, int left, int mid, int right, String filename) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(array, left, L, 0, n1);
        System.arraycopy(array, mid + 1, R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                array[k++] = L[i++];
            } else {
                array[k++] = R[j++];
            }
        }
        while (i < n1) {
            array[k++] = L[i++];
        }
        while (j < n2) {
            array[k++] = R[j++];
        }

        writeArrayToFile(array, filename, "Merge Step (left: " + left + ", mid: " + mid + ", right: " + right + "):\n");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the length of the array: ");
        int length = scanner.nextInt();

        int[] randomArray = createRandomArray(length);
        System.out.println("Generated Array: ");
        for (int value : randomArray) {
            System.out.print(value + " ");
        }
        System.out.println();

        System.out.print("Enter a filename (or press Enter to skip): ");
        scanner.nextLine();  
        String filename = scanner.nextLine();
        
        if (!filename.isEmpty()) {
            writeArrayToFile(randomArray, filename, "Initial Array:\n");
            System.out.println("Array written to " + filename);
            
            int[] bubbleArray = randomArray.clone();
            int[] mergeArray = randomArray.clone();

            bubbleSort(bubbleArray, filename);
            mergeSort(mergeArray, filename);

            System.out.println("Final Bubble Sorted Array: ");
            for (int value : bubbleArray) {
                System.out.print(value + " ");
            }
            System.out.println();

            System.out.println("Final Merge Sorted Array: ");
            for (int value : mergeArray) {
                System.out.print(value + " ");
            }
            System.out.println();
        } else {
            System.out.println("Filename not provided. Skipping file operations.");
        }

        scanner.close();
    }
}
