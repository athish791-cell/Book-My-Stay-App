import java.util.ArrayList;
import java.util.List;

/**
 * Reservation
 *
 * Represents a confirmed booking.
 *
 * @author ATHISH
 * @version 8.0
 */
class Reservation {
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
 * BookingHistory
 *
 * Stores confirmed reservations in insertion order.
 *
 * @author ATHISH
 * @version 8.0
 */
class BookingHistory {
    private List<Reservation> confirmedBookings;

    public BookingHistory() {
        confirmedBookings = new ArrayList<>();
    }

    public void addConfirmedReservation(Reservation reservation) {
        confirmedBookings.add(reservation);
    }

    public List<Reservation> getConfirmedBookings() {
        return confirmedBookings;
    }
}

/**
 * BookingReportService
 *
 * Generates reports from booking history.
 *
 * @author ATHISH
 * @version 8.0
 */
class BookingReportService {

    public void displayBookingHistory(List<Reservation> reservations) {
        System.out.println("=== Booking History ===");
        if (reservations.isEmpty()) {
            System.out.println("No confirmed bookings found.");
            return;
        }

        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    public void displaySummaryReport(List<Reservation> reservations) {
        System.out.println("\n=== Booking Summary Report ===");
        System.out.println("Total Confirmed Reservations: " + reservations.size());
    }
}

/**
 * UseCase8BookingHistoryReport
 *
 * Demonstrates booking history storage and report generation.
 *
 * @author ATHISH
 * @version 8.0
 */
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {
        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 8.0\n");

        // Booking history storage
        BookingHistory bookingHistory = new BookingHistory();

        // Simulate confirmed bookings
        bookingHistory.addConfirmedReservation(new Reservation("RES101", "Athish", "Single Room"));
        bookingHistory.addConfirmedReservation(new Reservation("RES102", "Amal", "Suite Room"));
        bookingHistory.addConfirmedReservation(new Reservation("RES103", "Zamaad", "Double Room"));

        // Reporting service
        BookingReportService reportService = new BookingReportService();

        // Display booking history
        reportService.displayBookingHistory(bookingHistory.getConfirmedBookings());

        // Display summary report
        reportService.displaySummaryReport(bookingHistory.getConfirmedBookings());

        System.out.println("\nReporting completed without modifying booking history.");
        System.out.println("Application finished.");
    }
}
