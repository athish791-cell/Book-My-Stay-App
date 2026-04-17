import java.util.LinkedList;
import java.util.Queue;

/**
 * Reservation
 *
 * Represents a guest's intent to book a room.
 *
 * @author ATHISH
 * @version 5.0
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

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Room Type: " + roomType;
    }
}

/**
 * BookingRequestQueue
 *
 * Stores booking requests in FIFO order.
 *
 * @author ATHISH
 * @version 5.0
 */
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public void displayQueue() {
        if (requestQueue.isEmpty()) {
            System.out.println("No booking requests in queue.");
            return;
        }

        System.out.println("Current Booking Request Queue:");
        for (Reservation reservation : requestQueue) {
            System.out.println(reservation);
        }
    }
}

/**
 * UseCase5BookingRequestQueue
 *
 * Demonstrates first-come-first-served booking request handling.
 *
 * @author ATHISH
 * @version 5.0
 */
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {
        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 5.0\n");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guest booking requests
        Reservation r1 = new Reservation("Athish", "Single Room");
        Reservation r2 = new Reservation("Amal", "Suite Room");
        Reservation r3 = new Reservation("Zamaad", "Double Room");

        // Add requests in arrival order
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queued requests
        bookingQueue.displayQueue();

        System.out.println("\nRequests stored successfully in arrival order.");
        System.out.println("No inventory updates performed at this stage.");
    }
}
