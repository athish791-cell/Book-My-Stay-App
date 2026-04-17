import java.util.HashMap;
import java.util.Map;

/**
 * RoomInventory
 *
 * Manages room availability using a centralized HashMap.
 *
 * @author ATHISH
 * @version 3.1
 */
class RoomInventory {
    private Map<String, Integer> availabilityMap;

    // Constructor initializes room availability
    public RoomInventory() {
        availabilityMap = new HashMap<>();
        availabilityMap.put("Single Room", 5);
        availabilityMap.put("Double Room", 3);
        availabilityMap.put("Suite Room", 2);
    }

    // Get availability of a specific room type
    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    // Update availability of a specific room type
    public void updateAvailability(String roomType, int newCount) {
        availabilityMap.put(roomType, newCount);
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : availabilityMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue() + " rooms available");
        }
    }
}

/**
 * UseCase3InventorySetup
 *
 * Demonstrates centralized room inventory management using HashMap.
 *
 * @author ATHISH
 * @version 3.1
 */
public class UseCase3InventorySetup {

    public static void main(String[] args) {
        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 3.1\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Retrieve availability
        System.out.println("\nChecking availability:");
        System.out.println("Single Room available: " + inventory.getAvailability("Single Room"));
        System.out.println("Suite Room available: " + inventory.getAvailability("Suite Room"));

        // Update availability
        System.out.println("\nUpdating availability...");
        inventory.updateAvailability("Double Room", 4);

        // Display updated inventory
        System.out.println();
        inventory.displayInventory();

        System.out.println("\nApplication finished.");
    }
}
