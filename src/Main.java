import java.util.HashMap;
import java.util.Map;

abstract class Room {
    protected String roomNumber;
    protected int numberOfBeds;
    protected double roomSize;
    protected double pricePerNight;

    public Room(String roomNumber, int numberOfBeds, double roomSize, double pricePerNight) {
        this.roomNumber    = roomNumber;
        this.numberOfBeds  = numberOfBeds;
        this.roomSize      = roomSize;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomNumber()    { return roomNumber; }
    public int getNumberOfBeds()     { return numberOfBeds; }
    public double getRoomSize()      { return roomSize; }
    public double getPricePerNight() { return pricePerNight; }
    public abstract String getRoomType();

    public void displayRoomDetails() {
        System.out.println("  Room Number   : " + roomNumber);
        System.out.println("  Room Type     : " + getRoomType());
        System.out.println("  Number of Beds: " + numberOfBeds);
        System.out.println("  Room Size     : " + roomSize + " sq ft");
        System.out.println("  Price/Night   : $" + pricePerNight);
    }
}

class SingleRoom extends Room {
    public SingleRoom(String roomNumber) { super(roomNumber, 1, 200.0, 99.99); }

    @Override
    public String getRoomType() { return "Single Room"; }
}

class DoubleRoom extends Room {
    public DoubleRoom(String roomNumber) { super(roomNumber, 2, 350.0, 149.99); }

    @Override
    public String getRoomType() { return "Double Room"; }
}

class SuiteRoom extends Room {
    public SuiteRoom(String roomNumber) { super(roomNumber, 3, 750.0, 349.99); }

    @Override
    public String getRoomType() { return "Suite Room"; }
}

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 10);
        inventory.put("Double Room",  0);
        inventory.put("Suite Room",   3);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int updatedCount) {
        if (!inventory.containsKey(roomType)) {
            System.out.println("  [WARNING] Room type not found: " + roomType);
            return;
        }
        if (updatedCount < 0) {
            System.out.println("  [WARNING] Availability cannot be negative. Update rejected.");
            return;
        }
        inventory.put(roomType, updatedCount);
        System.out.println("  [UPDATE]  " + roomType + " availability updated to " + updatedCount);
    }

    public void addRoomType(String roomType, int initialCount) {
        if (inventory.containsKey(roomType)) {
            System.out.println("  [INFO] Room type already exists: " + roomType);
            return;
        }
        inventory.put(roomType, initialCount);
        System.out.println("  [ADDED]   New room type registered: " + roomType
                + " (count: " + initialCount + ")");
    }

    public HashMap<String, Integer> getInventorySnapshot() {
        return new HashMap<>(inventory);
    }

    public void displayInventory() {
        System.out.println("\n  ── Current Room Inventory ──────────────────────────");
        System.out.printf("  %-20s  %s%n", "Room Type", "Available Rooms");
        System.out.println("  " + "-".repeat(42));
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.printf("  %-20s  %d%n", entry.getKey(), entry.getValue());
        }
        System.out.println("  " + "-".repeat(42));
    }
}

class RoomSearchService {

    private RoomInventory inventory;
    private Map<String, Room> roomCatalog;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory   = inventory;
        this.roomCatalog = new HashMap<>();
        roomCatalog.put("Single Room", new SingleRoom("R101"));
        roomCatalog.put("Double Room", new DoubleRoom("R201"));
        roomCatalog.put("Suite Room",  new SuiteRoom("R301"));
    }

    public void searchAvailableRooms() {
        System.out.println("\n  ── Available Rooms ─────────────────────────────────");

        HashMap<String, Integer> snapshot = inventory.getInventorySnapshot();
        boolean anyAvailable = false;

        for (Map.Entry<String, Integer> entry : snapshot.entrySet()) {
            String roomType = entry.getKey();
            int    count    = entry.getValue();

            if (count <= 0) {
                continue;
            }

            Room room = roomCatalog.get(roomType);
            if (room == null) {
                continue;
            }

            anyAvailable = true;
            System.out.println("\n  ----------------------------------------------------");
            room.displayRoomDetails();
            System.out.println("  Rooms Available : " + count);
        }

        if (!anyAvailable) {
            System.out.println("\n  No rooms are currently available.");
        }

        System.out.println("\n  " + "-".repeat(52));
        System.out.println("  [INFO] Search complete. Inventory was not modified.");
    }

    public void searchByRoomType(String roomType) {
        System.out.println("\n  ── Search Result for: " + roomType + " ─────────────────");

        int count = inventory.getAvailability(roomType);
        Room room = roomCatalog.get(roomType);

        if (room == null) {
            System.out.println("  [ERROR] Room type not recognized: " + roomType);
            return;
        }

        if (count <= 0) {
            System.out.println("  [UNAVAILABLE] " + roomType + " is fully booked.");
            return;
        }

        System.out.println();
        room.displayRoomDetails();
        System.out.println("  Rooms Available : " + count);
        System.out.println("  " + "-".repeat(52));
    }
}

class UseCase4HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println("        Welcome to Book My Stay App                        ");
        System.out.println("        Hotel Booking Management System  v4.0              ");
        System.out.println("============================================================");

        System.out.println("\n[STEP 1] Initializing Room Inventory...");
        RoomInventory inventory = new RoomInventory();
        inventory.displayInventory();

        System.out.println("\n[STEP 2] Initializing Room Search Service...");
        RoomSearchService searchService = new RoomSearchService(inventory);
        System.out.println("  [INFO] Search service ready. Read-only access enforced.");

        System.out.println("\n[STEP 3] Guest searches all available rooms...");
        searchService.searchAvailableRooms();

        System.out.println("\n[STEP 4] Guest searches for a specific room type (Suite Room)...");
        searchService.searchByRoomType("Suite Room");

        System.out.println("\n[STEP 5] Guest searches for an unavailable room type (Double Room)...");
        searchService.searchByRoomType("Double Room");

        System.out.println("\n[STEP 6] Guest searches for an unrecognized room type...");
        searchService.searchByRoomType("Penthouse");

        System.out.println("\n[STEP 7] Verifying inventory was not modified during search...");
        inventory.displayInventory();

        System.out.println("\n============================================================");
        System.out.println("  Search operations are fully read-only. Inventory state    ");
        System.out.println("  remains unchanged. Only rooms with availability > 0       ");
        System.out.println("  are shown, enforcing defensive programming principles.    ");
        System.out.println("============================================================");
    }
}