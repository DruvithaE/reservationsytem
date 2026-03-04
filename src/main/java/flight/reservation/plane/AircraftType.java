package flight.reservation.plane;

/**
 * Enumeration for different aircraft types
 */
public enum AircraftType {
    PASSENGER_PLANE("PassengerPlane"),
    HELICOPTER("Helicopter"),
    PASSENGER_DRONE("PassengerDrone");
    
    private final String typeName;
    
    AircraftType(String typeName) {
        this.typeName = typeName;
    }
    
    public String getTypeName() {
        return typeName;
    }
}