import java.util.LinkedList;
import java.util.Queue;

/**
 * Reservation request made by a guest.
 *
 * @author ATHISH
 * @version 11.0
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
 * Shared room inventory with synchronized allocation logic.
 *
 * @author ATHISH
 * @version 11.0
 */
class RoomInventory {
    private int singleRoomCount;

    public RoomInventory(int singleRoomCount) {
        this.singleRoomCount = singleRoomCount;
    }

    public synchronized boolean allocateRoom(String roomType, String guestName) {
        if (!"Single Room".equals(roomType)) {
            System.out.println(Thread.currentThread().getName() +
                    " -> Unsupported room type for this simulation: " + roomType);
            return false;
        }

        if (singleRoomCount > 0) {
            System.out.println(Thread.currentThread().getName() +
                    " processing booking for " + guestName);

            // Critical section: check + update done together
            singleRoomCount--;

            System.out.println("Booking CONFIRMED for " + guestName +
                    " | Remaining Single Rooms: " + singleRoomCount);
            return true;
        } else {
            System.out.println("Booking FAILED for " + guestName +
                    " | No Single Rooms available");
            return false;
        }
    }

    public synchronized int getSingleRoomCount() {
        return singleRoomCount;
    }
}

/**
 * Shared booking queue with synchronized retrieval.
 *
 * @author ATHISH
 * @version 11.0
 */
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public synchronized Reservation getNextRequest() {
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

/**
 * Concurrent booking processor thread.
 *
 * @author ATHISH
 * @version 11.0
 */
class BookingProcessor extends Thread {
    private BookingQueue bookingQueue;
    private RoomInventory inventory;

    public BookingProcessor(String name, BookingQueue bookingQueue, RoomInventory inventory) {
        super(name);
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            Reservation reservation = bookingQueue.getNextRequest();

            if (reservation == null) {
                break;
            }

            inventory.allocateRoom(reservation.getRoomType(), reservation.getGuestName());
        }
    }
}

/**
 * UseCase11ConcurrentBookingSimulation
 *
 * Demonstrates concurrent booking requests handled safely using synchronization.
 *
 * @author ATHISH
 * @version 11.0
 */
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {
        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 11.0\n");

        // Shared queue and shared inventory
        BookingQueue bookingQueue = new BookingQueue();
        RoomInventory inventory = new RoomInventory(2);

        // Simulate multiple guest requests arriving nearly simultaneously
        bookingQueue.addRequest(new Reservation("Athish", "Single Room"));
        bookingQueue.addRequest(new Reservation("Amal", "Single Room"));
        bookingQueue.addRequest(new Reservation("Zamaad", "Single Room"));
        bookingQueue.addRequest(new Reservation("John", "Single Room"));

        // Multiple processor threads
        BookingProcessor t1 = new BookingProcessor("Processor-1", bookingQueue, inventory);
        BookingProcessor t2 = new BookingProcessor("Processor-2", bookingQueue, inventory);
        BookingProcessor t3 = new BookingProcessor("Processor-3", bookingQueue, inventory);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted: " + e.getMessage());
        }

        System.out.println("\nFinal Inventory State:");
        System.out.println("Single Rooms Remaining: " + inventory.getSingleRoomCount());

        System.out.println("\nConcurrent booking simulation completed safely.");
    }
}
