import java.util.ArrayList;
import java.util.List;

class Main {

    public static void main(String[] args) {

        // Display welcome banner
        System.out.println("=======================================");
        System.out.println(" === Train Consist Management App === ");
        System.out.println("=======================================\n");

        // Create a dynamic list to store train bogies
        List<String> trainConsist = new ArrayList<>();

        // Display initial consist information
        System.out.println("Train initialized successfully...");
        System.out.println("Initial Bogie Count : " + trainConsist.size());
        System.out.println("Current Train Consist : " + trainConsist);

        System.out.println("\nSystem ready for operations...");
    }
}