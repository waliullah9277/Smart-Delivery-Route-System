import java.io.*;
import java.util.*;

public class FileManager {
    private static final String FILE_NAME = "users.txt";

    public static void saveUser(String username, String email, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(username + "," + email + "," + password);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }

    public static Map<String, User> loadUsers() {
        Map<String, User> users = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    users.put(parts[0], new User(parts[1], parts[2], parts[2]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("User file not found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading user data: " + e.getMessage());
        }
        return users;
    }
}
