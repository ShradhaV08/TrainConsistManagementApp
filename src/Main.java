import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

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
    @Override public String getRoomType() { return "Single Room"; }
}

class DoubleRoom extends Room {
    public DoubleRoom(String roomNumber) { super(roomNumber, 2, 350.0, 149.99); }
    @Override public String getRoomType() { return "Double Room"; }
}

class SuiteRoom extends Room {
    public SuiteRoom(String roomNumber) { super(roomNumber, 3, 750.0, 349.99); }
    @Override public String getRoomType() { return "Suite Room"; }
}

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 10);
        inventory.put("Double Room",  5);
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

class Reservation {

    private static int counter = 1;

    private int    reservationId;
    private String guestName;
    private String roomType;
    private int    numberOfNights;

    public Reservation(String guestName, String roomType, int numberOfNights) {
        this.reservationId  = counter++;
        this.guestName      = guestName;
        this.roomType       = roomType;
        this.numberOfNights = numberOfNights;
    }

    public int    getReservationId()  { return reservationId; }
    public String getGuestName()      { return guestName; }
    public String getRoomType()       { return roomType; }
    public int    getNumberOfNights() { return numberOfNights; }

    public void displayReservation() {
        System.out.printf("  [#%03d]  Guest: %-15s  Room Type: %-15s  Nights: %d%n",
                reservationId, guestName, roomType, numberOfNights);
    }
}

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.printf("  [QUEUED]  Request #%03d added — Guest: %s, Room: %s, Nights: %d%n",
                reservation.getReservationId(),
                reservation.getGuestName(),
                reservation.getRoomType(),
                reservation.getNumberOfNights());
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public Reservation peekNextRequest() {
        return requestQueue.peek();
    }

    public boolean isEmpty() {
        return requestQueue.isEmpty();
    }

    public int getQueueSize() {
        return requestQueue.size();
    }

    public Queue<Reservation> getQueue() {
        return requestQueue;
    }

    public void displayQueue() {
        System.out.println("\n  ── Booking Request Queue (FIFO Order) ──────────────");
        System.out.printf("  %-8s  %-15s  %-15s  %s%n", "Req ID", "Guest Name", "Room Type", "Nights");
        System.out.println("  " + "-".repeat(58));
        if (requestQueue.isEmpty()) {
            System.out.println("  No pending requests in the queue.");
        } else {
            for (Reservation r : requestQueue) {
                System.out.printf("  #%-7d  %-15s  %-15s  %d%n",
                        r.getReservationId(),
                        r.getGuestName(),
                        r.getRoomType(),
                        r.getNumberOfNights());
            }
        }
        System.out.println("  " + "-".repeat(58));
        System.out.println("  Total Pending Requests: " + requestQueue.size());
    }
}

class UseCase5HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println("        Welcome to Book My Stay App                        ");
        System.out.println("        Hotel Booking Management System  v5.0              ");
        System.out.println("============================================================");

        System.out.println("\n[STEP 1] Initializing Room Inventory...");
        RoomInventory inventory = new RoomInventory();
        inventory.displayInventory();

        System.out.println("\n[STEP 2] Initializing Booking Request Queue...");
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        System.out.println("  [INFO] Queue initialized. Ready to accept booking requests.");

        System.out.println("\n[STEP 3] Guests submitting booking requests...");
        bookingQueue.addRequest(new Reservation("Alice",   "Single Room", 3));
        bookingQueue.addRequest(new Reservation("Bob",     "Suite Room",  2));
        bookingQueue.addRequest(new Reservation("Charlie", "Double Room", 5));
        bookingQueue.addRequest(new Reservation("Diana",   "Single Room", 1));
        bookingQueue.addRequest(new Reservation("Eve",     "Suite Room",  4));

        System.out.println("\n[STEP 4] Displaying current booking queue (FIFO order)...");
        bookingQueue.displayQueue();

        System.out.println("\n[STEP 5] Peeking at the next request to be processed...");
        Reservation nextUp = bookingQueue.peekNextRequest();
        if (nextUp != null) {
            System.out.print("  [NEXT UP] ");
            nextUp.displayReservation();
        }

        System.out.println("\n[STEP 6] Verifying inventory is unchanged after queuing...");
        inventory.displayInventory();

        System.out.println("\n[STEP 7] Previewing processing order (FIFO — no dequeue)...");
        System.out.println("\n  ── Requests will be processed in this order ────────");
        int position = 1;
        for (Reservation r : bookingQueue.getQueue()) {
            System.out.printf("  Position %d: ", position++);
            r.displayReservation();
        }

        System.out.println("\n============================================================");
        System.out.println("  All requests are queued in arrival order (FIFO).          ");
        System.out.println("  Inventory remains untouched at this stage.                ");
        System.out.println("  Requests are ready for fair, sequential allocation.       ");
        System.out.println("============================================================");
    }
}