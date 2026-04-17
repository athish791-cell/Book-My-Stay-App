import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reservation
 *
 * Represents a confirmed booking.
 *
 * @author ATHISH
 * @version 12.0
 */
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
               " | Guest: " + guestName +
               " | Room Type: " + roomType;
    }
}

/**
 * RoomInventory
 *
 * Maintains inventory state for room availability.
 *
 * @author ATHISH
 * @version 12.0
 */
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
        availabilityMap.put("Single Room", 2);
        availabilityMap.put("Double Room", 1);
        availabilityMap.put("Suite Room", 1);
    }

    public Map<String, Integer> getAvailabilityMap() {
        return availabilityMap;
    }

    public void displayInventory() {
        System.out.println("=== Inventory State ===");
        for (Map.Entry<String, Integer> entry : availabilityMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

/**
 * HotelState
 *
 * Wraps booking history and inventory into one serializable object.
 *
 * @author ATHISH
 * @version 12.0
 */
class HotelState implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Reservation> bookingHistory;
    private RoomInventory inventory;

    public HotelState(List<Reservation> bookingHistory, RoomInventory inventory) {
        this.bookingHistory = bookingHistory;
        this.inventory = inventory;
    }

    public List<Reservation> getBookingHistory() {
        return bookingHistory;
    }

    public RoomInventory getInventory() {
        return inventory;
    }
}

/**
 * PersistenceService
 *
 * Handles saving and loading system state.
 *
 * @author ATHISH
 * @version 12.0
 */
class PersistenceService {
    private static final String FILE_NAME = "hotel_state.ser";

    public void saveState(HotelState state) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(state);
            System.out.println("System state saved successfully to file.");
        } catch (IOException e) {
            System.out.println("Error while saving state: " + e.getMessage());
        }
    }

    public HotelState loadState() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No persistence file found. Starting with default state.");
            return null;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            HotelState state = (HotelState) in.readObject();
            System.out.println("System state restored successfully from file.");
            return state;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while loading state: " + e.getMessage());
            System.out.println("Starting with a safe default state.");
            return null;
        }
    }
}

/**
 * UseCase12DataPersistenceRecovery
 *
 * Demonstrates saving and restoring booking and inventory state.
 *
 * @author ATHISH
 * @version 12.0
 */
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {
        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 12.0\n");

        PersistenceService persistenceService = new PersistenceService();

        // Try loading existing state
        HotelState restoredState = persistenceService.loadState();

        List<Reservation> bookingHistory;
        RoomInventory inventory;

        if (restoredState != null) {
            bookingHistory = restoredState.getBookingHistory();
            inventory = restoredState.getInventory();

            System.out.println("\nRecovered Booking History:");
            for (Reservation reservation : bookingHistory) {
                System.out.println(reservation);
            }

            System.out.println();
            inventory.displayInventory();
        } else {
            // Create default state if no file exists
            bookingHistory = new ArrayList<>();
            bookingHistory.add(new Reservation("RES101", "Athish", "Single Room"));
            bookingHistory.add(new Reservation("RES102", "Amal", "Suite Room"));

            inventory = new RoomInventory();

            System.out.println("Initialized new in-memory state.\n");

            System.out.println("Booking History:");
            for (Reservation reservation : bookingHistory) {
                System.out.println(reservation);
            }

            System.out.println();
            inventory.displayInventory();

            // Save the new state
            HotelState newState = new HotelState(bookingHistory, inventory);
            System.out.println();
            persistenceService.saveState(newState);
        }

        System.out.println("\nSystem continues operating safely after recovery.");
    }
}
