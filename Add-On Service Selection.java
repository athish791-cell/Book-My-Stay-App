import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AddOnService
 *
 * Represents an optional service selected by a guest.
 *
 * @author ATHISH
 * @version 7.0
 */
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

/**
 * AddOnServiceManager
 *
 * Manages add-on services selected for reservations.
 *
 * @author ATHISH
 * @version 7.0
 */
class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);
    }

    public List<AddOnService> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }

    public double calculateAdditionalCost(String reservationId) {
        double total = 0;
        List<AddOnService> services = getServices(reservationId);

        for (AddOnService service : services) {
            total += service.getCost();
        }

        return total;
    }

    public void displayServices(String reservationId) {
        List<AddOnService> services = getServices(reservationId);

        System.out.println("Reservation ID: " + reservationId);
        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Selected Add-On Services:");
        for (AddOnService service : services) {
            System.out.println("- " + service);
        }

        System.out.println("Total Additional Cost: ₹" + calculateAdditionalCost(reservationId));
    }
}

/**
 * UseCase7AddOnServiceSelection
 *
 * Demonstrates optional add-on service selection for reservations.
 *
 * @author ATHISH
 * @version 7.0
 */
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {
        System.out.println("=== Book My Stay App ===");
        System.out.println("Version: 7.0\n");

        // Example existing reservation
        String reservationId = "RES101";

        // Create service manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Guest selects add-on services
        serviceManager.addService(reservationId, new AddOnService("Breakfast", 500));
        serviceManager.addService(reservationId, new AddOnService("Airport Pickup", 1200));
        serviceManager.addService(reservationId, new AddOnService("Extra Bed", 800));

        // Display selected services and total add-on cost
        serviceManager.displayServices(reservationId);

        System.out.println("\nCore booking and inventory state remain unchanged.");
        System.out.println("Application finished.");
    }
}
