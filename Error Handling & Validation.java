import java.util.HashMap;
import java.util.Map;

/**
 * InvalidBookingException
 *
 * Custom exception used for invalid booking scenarios.
 *
 * @author ATHISH
 * @version 9.0
 */
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

/**
 * RoomInventory
 *
 * Maintains room availability and validates inventory updates.
 *
 * @author ATHISH
 * @version 9.0
 */
class RoomInventory {
    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
        availabilityMap.put("Single Room", 2);
        availabilityMap.put("Double Room", 1);
        availabilityMap.put("Suite Room", 1);
    }

    public boolean isValidRoomType(String roomType) {
        return availabilityMap.containsKey(roomType);
    }

    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    public void reserveRoom(String roomType) throws InvalidBookingException {
        if (!isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        int available = getAvailability(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }

        availabilityMap.put(roomType, available - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : availabilityMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

/**
 * InvalidBookingValidator
 *
 * Validates guest booking requests before processing.
 *
 * @author ATHISH
 * @version 9.0
 */
class InvalidBookingValidator {
    private RoomInventory inventory;

    public InvalidBookingValidator(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void validateAndProcess(String guestName, String roomType) throws InvalidBookingException {
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty");
        }

        inventory.reserveRoom(roomType);
        System.out.println("Booking confirmed for " + guestName + " -> " + roomType);
    }
}

/**
 * UseCase9ErrorHandlingValidation
 *
 * Demonstrates validation and graceful error handling for bookings.
 *
 * @author ATHISH
 * @version 9.0
 */
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {
        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 9.0");

        RoomInventory inventory = new RoomInventory();
        InvalidBookingValidator validator = new InvalidBookingValidator(inventory);

        // Valid booking
        try {
            validator.validateAndProcess("Athish", "Single Room");
        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }

        // Invalid room type (case sensitive)
        try {
            validator.validateAndProcess("Amal", "single room");
        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }

        // Empty guest name
        try {
            validator.validateAndProcess("", "Suite Room");
        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }

        // Booking until unavailable
        try {
            validator.validateAndProcess("Zamaad", "Suite Room");
            validator.validateAndProcess("John", "Suite Room");
        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }

        inventory.displayInventory();

        System.out.println("\nApplication continues safely after handling errors.");
    }
}
