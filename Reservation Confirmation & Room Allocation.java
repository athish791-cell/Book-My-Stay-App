import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Reservation
 *
 * Represents a guest booking request.
 *
 * @author ATHISH
 * @version 6.0
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * RoomInventory
 *
 * Maintains room availability for each room type.
 *
 * @author ATHISH
 * @version 6.0
 */
class RoomInventory {
    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
        availabilityMap.put("Single Room", 2);
        availabilityMap.put("Double Room", 1);
        availabilityMap.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    public void decrementAvailability(String roomType) {
        int current = getAvailability(roomType);
        if (current > 0) {
            availabilityMap.put(roomType, current - 1);
        }
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : availabilityMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

/**
 * BookingService
 *
 * Processes booking requests and allocates unique rooms safely.
 *
 * @author ATHISH
 * @version 6.0
 */
class BookingService {
    private Queue<Reservation> requestQueue;
    private RoomInventory inventory;
    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> roomTypeAllocations;
    private int roomCounter = 1;

    public BookingService(Queue<Reservation> requestQueue, RoomInventory inventory) {
        this.requestQueue = requestQueue;
        this.inventory = inventory;
        this.allocatedRoomIds = new HashSet<>();
        this.roomTypeAllocations = new HashMap<>();
    }

    public void processBookings() {
        while (!requestQueue.isEmpty()) {
            Reservation reservation = requestQueue.poll();
            String roomType = reservation.getRoomType();

            System.out.println("\nProcessing booking for: " + reservation.getGuestName());
            System.out.println("Requested Room Type: " + roomType);

            if (inventory.getAvailability(roomType) > 0) {
                String roomId = generateUniqueRoomId(roomType);

                allocatedRoomIds.add(roomId);

                roomTypeAllocations.putIfAbsent(roomType, new HashSet<>());
                roomTypeAllocations.get(roomType).add(roomId);

                inventory.decrementAvailability(roomType);

                System.out.println("Reservation CONFIRMED");
                System.out.println("Assigned Room ID: " + roomId);
            } else {
                System.out.println("Reservation FAILED - No rooms available for " + roomType);
            }
        }
    }

    private String generateUniqueRoomId(String roomType) {
        String prefix;

        if (roomType.equalsIgnoreCase("Single Room")) {
            prefix = "S";
        } else if (roomType.equalsIgnoreCase("Double Room")) {
            prefix = "D";
        } else {
            prefix = "SU";
        }

        String roomId;
        do {
            roomId = prefix + roomCounter;
            roomCounter++;
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }

    public void displayAllocatedRooms() {
        System.out.println("\nAllocated Room IDs:");
        for (Map.Entry<String, Set<String>> entry : roomTypeAllocations.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

/**
 * UseCase6RoomAllocationService
 *
 * Demonstrates reservation confirmation and safe room allocation.
 *
 * @author ATHISH
 * @version 6.0
 */
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {
        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 6.0");

        Queue<Reservation> requestQueue = new LinkedList<>();
        requestQueue.offer(new Reservation("Athish", "Single Room"));
        requestQueue.offer(new Reservation("Amal", "Suite Room"));
        requestQueue.offer(new Reservation("Zamaad", "Single Room"));
        requestQueue.offer(new Reservation("John", "Single Room")); // should fail if unavailable

        RoomInventory inventory = new RoomInventory();

        BookingService bookingService = new BookingService(requestQueue, inventory);

        bookingService.processBookings();
        bookingService.displayAllocatedRooms();
        inventory.displayInventory();

        System.out.println("\nApplication finished.");
    }
}
