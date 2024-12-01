import java.util.*;

public class Dashboard {
    public static void showRoutes(List<String> cities) {
        System.out.println("Dashboard - Cities:");
        for (String city : cities) {
            System.out.println("- " + city);
        }
    }

    public static void showKnapsackResult(int maxEfficiency) {
        System.out.println("Knapsack Optimization - Maximum Efficiency: " + maxEfficiency);
    }
}
