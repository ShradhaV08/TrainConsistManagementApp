import java.util.HashSet;
import java.util.Set;

class Main {
    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("UC3 - Track Unique Bogie IDs");
        System.out.println("======================================");

        // Create HashSet for storing unique bogie IDs
        Set<String> bogieIds = new HashSet<>();

        // Adding bogie IDs (including duplicates intentionally)
        bogieIds.add("BG104");
        bogieIds.add("BG103");
        bogieIds.add("BG102");
        bogieIds.add("BG101");
        bogieIds.add("BG101"); // duplicate
        bogieIds.add("BG102"); // duplicate

        // Display unique bogie IDs
        System.out.println("\nBogie IDs After Insertion:");
        System.out.println(bogieIds);

        System.out.println("\nNote:");
        System.out.println("Duplicates are automatically ignored by HashSet.");

        System.out.println("\nUC3 uniqueness validation completed...");
    }
}