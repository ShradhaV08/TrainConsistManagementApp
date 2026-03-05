
abstract class Room {

    protected String roomNumber;

    protected int numberOfBeds;

    protected double roomSize;

    protected double pricePerNight;

    public Room(String roomNumber, int numberOfBeds, double roomSize, double pricePerNight) {
        this.roomNumber     = roomNumber;
        this.numberOfBeds   = numberOfBeds;
        this.roomSize       = roomSize;
        this.pricePerNight  = pricePerNight;
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

    public SingleRoom(String roomNumber) {
        super(roomNumber, 1, 200.0, 99.99);
    }

    @Override
    public String getRoomType() {
        return "Single Room";
    }
}

class DoubleRoom extends Room {

    public DoubleRoom(String roomNumber) {
        super(roomNumber, 2, 350.0, 149.99);
    }

    @Override
    public String getRoomType() {
        return "Double Room";
    }
}

class SuiteRoom extends Room {

    public SuiteRoom(String roomNumber) {
        super(roomNumber, 3, 750.0, 349.99);
    }

    @Override
    public String getRoomType() {
        return "Suite Room";
    }
}

class UseCase2HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println("        Welcome to Book My Stay App                        ");
        System.out.println("        Hotel Booking Management System  v2.0              ");
        System.out.println("============================================================");

        // ── Create room objects via polymorphic Room references ──────────
        Room singleRoom = new SingleRoom("R101");
        Room doubleRoom = new DoubleRoom("R201");
        Room suiteRoom  = new SuiteRoom("R301");

        // ── Static availability — simple variables (intentional limitation) ──
        boolean isSingleRoomAvailable = true;
        boolean isDoubleRoomAvailable = false;
        boolean isSuiteRoomAvailable  = true;

        // ── Display room details and availability ────────────────────────
        System.out.println("\n------------------------------------------------------------");
        singleRoom.displayRoomDetails();
        System.out.println("  Availability  : " + (isSingleRoomAvailable ? "Available" : "Not Available"));

        System.out.println("\n------------------------------------------------------------");
        doubleRoom.displayRoomDetails();
        System.out.println("  Availability  : " + (isDoubleRoomAvailable ? "Available" : "Not Available"));

        System.out.println("\n------------------------------------------------------------");
        suiteRoom.displayRoomDetails();
        System.out.println("  Availability  : " + (isSuiteRoomAvailable ? "Available" : "Not Available"));

        System.out.println("\n============================================================");
        System.out.println("  NOTE: Availability is stored as individual variables.     ");
        System.out.println("  This approach does not scale — future use cases will      ");
        System.out.println("  replace this with proper data structures.                 ");
        System.out.println("============================================================");
    }
}