import java.util.*;

public class SmartDeliverySystem {
    private static Map<String, User> users = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isAuthenticated = false;

    public static void main(String[] args) {
        users = FileManager.loadUsers();
        List<String> cities = Arrays.asList("mirpur10", "shewrapara", "farmgate", "dhanmondi32", "uttra", "agargoan",
                "mohakhali", "bijoy saroni", "gabtoli", "banglamotor", "mohamadpur");
        Graph graph = new Graph(cities);

        graph.addEdge("mirpur10", "shewrapara", 2);
        graph.addEdge("mirpur10", "uttra", 10);
        graph.addEdge("mirpur10", "bijoy saroni", 5);
        graph.addEdge("shewrapara", "agargoan", 1);
        graph.addEdge("shewrapara", "mohamadpur", 6);
        graph.addEdge("agargoan", "bijoy saroni", 2);
        graph.addEdge("bijoy saroni", "mohamadpur", 4);
        graph.addEdge("uttra", "bijoy saroni", 12);
        graph.addEdge("mohamadpur", "agargoan", 3);
        graph.addEdge("mohamadpur", "mohakhali", 8);
        graph.addEdge("mohakhali", "uttra", 9);
        graph.addEdge("mohakhali", "banglamotor", 10);
        graph.addEdge("gabtoli", "mohamadpur", 5);
        graph.addEdge("farmgate", "uttra", 7);
        graph.addEdge("farmgate", "mohakhali", 2);
        graph.addEdge("farmgate", "gabtoli", 11);
        graph.addEdge("farmgate", "banglamotor", 3);
        graph.addEdge("farmgate", "dhanmondi32", 4);
        graph.addEdge("banglamotor", "shewrapara", 8);
        graph.addEdge("dhanmondi32", "mirpur10", 9);

        while (true) {
            System.out.println("\nFood Delivery System");
            System.out.println("No user logged in!");
            System.out.print("Register or Login or Exit? (R/L/E):");

            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("R")) {
                register();
            } else if (choice.equalsIgnoreCase("L")) {
                login();
                if (isAuthenticated) {
                    mainMenu(graph, cities);
                }
            } else if (choice.equalsIgnoreCase("E")) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void register() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please try another.");
            return;
        }
        System.out.print("Enter your email address: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your confirm password: ");
        String confirm_password = scanner.nextLine();
        if (!password.equals(confirm_password)) {
            System.out.println("Password doesn't match. Please try again!");
            return;
        }
        users.put(username, new User(email, password, confirm_password));
        FileManager.saveUser(username, email, password);
        System.out.println("Registration successful!");
    }

    private static void login() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username)) {
            User user = users.get(username);
            if (user.password.equals(password)) {
                System.out.println("Logged in successful!");
                isAuthenticated = true;
            } else {
                System.out.println("Invalid password.");
                isAuthenticated = false;
            }
        } else {
            System.out.println("Invalid username.");
            isAuthenticated = false;
        }
    }

    private static void mainMenu(Graph graph, List<String> cities) {
        while (true) {
            System.out.println("\nWelcome to our smart delivery system");
            System.out.println("1. Find Shortest Path");
            System.out.println("2. Show All Possible Routes");
            System.out.println("3. Optimize Load Balancing");
            System.out.println("4. Connect Warehouses");
            System.out.println("5. View Dashboard");
            System.out.println("6. Logout");

            System.out.print("Please choose your number: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter source city: ");
                String start = scanner.nextLine();
                System.out.print("Enter destination city: ");
                String end = scanner.nextLine();

                graph.dijkstra(start);
                graph.printPath(end);
            } else if (choice == 2) {
                System.out.print("Enter source city: ");
                String start = scanner.nextLine();
                System.out.print("Enter destination city: ");
                String end = scanner.nextLine();

                graph.printAllRoutes(start, end);
            } 
            else if (choice == 3) {
                int capacity = 50;
                int[] weights = { 20, 10, 15, 25, 30 };
                int[] values = { 60, 40, 50, 70, 80 };
                int n = weights.length;
                System.out.println("Lenght: " + n);
                int maxEfficiency = Knapsack.knapsack
                (capacity, weights, values, n);
                System.out.println("Maximum efficiency: " 
                + maxEfficiency);
            } 
            else if (choice == 4) {
                List<Kruskal.Edge> edges = graph.getAllEdges();
                Kruskal.kruskal(cities.size(), edges);
            } else if (choice == 5) {
                Dashboard.showRoutes(cities);
            } else if (choice == 6) {
                System.out.println("Logging out...");
                isAuthenticated = false;
                break;
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
