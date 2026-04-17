import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Reservation
 *
 * Represents a confirmed booking in the system.
 *
 * @author ATHISH
 * @version 10.0
 */
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean cancelled;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.cancelled = false;
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

    public String getRoomId() {
        return roomId;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
               " | Guest: " + guestName +
               " | Room Type: " + roomType +
               " | Room ID: " + roomId +
               " | Status: " + (cancelled ? "Cancelled" : "Confirmed");
    }
}

/**
 * RoomInventory
 *
 * Maintains current room availability.
 *
 * @author ATHISH
 * @version 10.0
 */
class RoomInventory {
    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
        availabilityMap.put("Single Room", 1);
        availabilityMap.put("Double Room", 1);
        availabilityMap.put("Suite Room", 0);
    }

    public void incrementAvailability(String roomType) {
        availabilityMap.put(roomType, availabilityMap.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : availabilityMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

/**
 * CancellationService
 *
 * Validates and performs booking cancellation with rollback.
 *
 * @author ATHISH
 * @version 10.0
 */
class CancellationService {
    private Map<String, Reservation> bookingHistory;
    private RoomInventory inventory;
    private Stack<String> rollbackStack;

    public CancellationService(Map<String, Reservation> bookingHistory, RoomInventory inventory) {
        this.bookingHistory = bookingHistory;
        this.inventory = inventory;
        this.rollbackStack = new Stack<>();
    }

    public void cancelBooking(String reservationId) {
        System.out.println("\nCancellation Request for Reservation ID: " + reservationId);

        Reservation reservation = bookingHistory.get(reservationId);

        if (reservation == null) {
            System.out.println("Cancellation Failed: Reservation does not exist.");
            return;
        }

        if (reservation.isCancelled()) {
            System.out.println("Cancellation Failed: Reservation is already cancelled.");
            return;
        }

        // Record released room ID in rollback structure
        rollbackStack.push(reservation.getRoomId());

        // Restore inventory immediately
        inventory.incrementAvailability(reservation.getRoomType());

        // Update booking history
        reservation.cancel();

        System.out.println("Cancellation Successful");
        System.out.println("Released Room ID: " + rollbackStack.peek());
        System.out.println("Inventory restored for: " + reservation.getRoomType());
    }

    public void displayRollbackStack() {
        System.out.println("\nRollback Stack (Recently Released Room IDs): " + rollbackStack);
    }
}

/**
 * UseCase10BookingCancellation
 *
 * Demonstrates safe booking cancellation and inventory rollback.
 *
 * @author ATHISH
 * @version 10.0
 */
public class UseCase10BookingCancellation {

    public static void main(String[] args) {
        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 10.0");

        // Simulated booking history
        Map<String, Reservation> bookingHistory = new HashMap<>();
        bookingHistory.put("RES101", new Reservation("RES101", "Athish", "Single Room", "S1"));
        bookingHistory.put("RES102", new Reservation("RES102", "Amal", "Suite Room", "SU1"));

        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService(bookingHistory, inventory);

        // Cancel valid booking
        cancellationService.cancelBooking("RES101");

        // Attempt duplicate cancellation
        cancellationService.cancelBooking("RES101");

        // Attempt invalid cancellation
        cancellationService.cancelBooking("RES999");

        // Show rollback data and current state
        cancellationService.displayRollbackStack();
        inventory.displayInventory();

        System.out.println("\nUpdated Booking History:");
        for (Reservation reservation : bookingHistory.values()) {
            System.out.println(reservation);
        }

        System.out.println("\nApplication finished.");
    }
}
