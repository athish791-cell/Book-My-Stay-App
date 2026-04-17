import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Room class representing common room properties.
 *
 * @author ATHISH
 * @version 4.0
 */
abstract class Room {
    protected String type;
    protected int beds;
    protected double price;
    protected String amenities;

    public Room(String type, int beds, double price, String amenities) {
        this.type = type;
        this.beds = beds;
        this.price = price;
        this.amenities = amenities;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public void displayDetails() {
        System.out.println("Room Type : " + type);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : ₹" + price);
        System.out.println("Amenities : " + amenities);
    }
}

/**
 * Single room implementation.
 */
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000, "WiFi, TV, Attached Bathroom");
    }
}

/**
 * Double room implementation.
 */
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500, "WiFi, TV, AC, Attached Bathroom");
    }
}

/**
 * Suite room implementation.
 */
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000, "WiFi, TV, AC, Mini Bar, Living Area");
    }
}

/**
 * Centralized inventory manager using HashMap.
 */
class RoomInventory {
    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
        availabilityMap.put("Single Room", 5);
        availabilityMap.put("Double Room", 0);
        availabilityMap.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    public void displayInventory() {
        for (Map.Entry<String, Integer> entry : availabilityMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

/**
 * UseCase4RoomSearch
 *
 * Demonstrates read-only room search and availability check.
 *
 * @author ATHISH
 * @version 4.0
 */
public class UseCase4RoomSearch {

    public static void main(String[] args) {
        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 4.0\n");

        // Centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Room domain objects
        Room[] rooms = {
            new SingleRoom(),
            new DoubleRoom(),
            new SuiteRoom()
        };

        System.out.println("Available Rooms:\n");

        boolean found = false;

        // Read-only search: no inventory updates here
        for (Room room : rooms) {
            int availableCount = inventory.getAvailability(room.getType());

            if (availableCount > 0) {
                room.displayDetails();
                System.out.println("Available  : " + availableCount);
                System.out.println("----------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms are currently available.");
        }

        System.out.println("\nSearch completed without modifying inventory.");
    }
}
