package flight.reservation.plane;

/**
 * Common interface for all aircraft types
 */
public interface Aircraft {
    
    /**
     * Gets the model name of the aircraft
     * @return model name
     */
    String getModel();
    
    /**
     * Gets passenger capacity
     * @return passenger capacity
     */
    int getPassengerCapacity();
    
    /**
     * Gets crew capacity
     * @return crew capacity
     */
    int getCrewCapacity();
    
    /**
     * Gets aircraft type for identification
     * @return aircraft type
     */
    AircraftType getAircraftType();
}