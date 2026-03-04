package flight.reservation.plane;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for creating Aircraft objects.
 * Encapsulates aircraft creation logic and reduces coupling.
 */
public class AircraftFactory {
    
    // Singleton instance
    private static AircraftFactory instance;
    
    // Registry of aircraft models and their types
    private static final Map<String, AircraftType> AIRCRAFT_REGISTRY = new HashMap<>();
    
    static {
        // Register all known aircraft models
        AIRCRAFT_REGISTRY.put("A380", AircraftType.PASSENGER_PLANE);
        AIRCRAFT_REGISTRY.put("A350", AircraftType.PASSENGER_PLANE);
        AIRCRAFT_REGISTRY.put("Embraer 190", AircraftType.PASSENGER_PLANE);
        AIRCRAFT_REGISTRY.put("Antonov AN2", AircraftType.PASSENGER_PLANE);
        AIRCRAFT_REGISTRY.put("H1", AircraftType.HELICOPTER);
        AIRCRAFT_REGISTRY.put("H2", AircraftType.HELICOPTER);
        AIRCRAFT_REGISTRY.put("HypaHype", AircraftType.PASSENGER_DRONE);
    }
    
    /**
     * Private constructor for singleton pattern
     */
    private AircraftFactory() {
    }
    
    /**
     * Gets singleton instance of the factory
     * @return AircraftFactory instance
     */
    public static AircraftFactory getInstance() {
        if (instance == null) {
            instance = new AircraftFactory();
        }
        return instance;
    }
    
    /**
     * Creates an aircraft object based on the model name
     * 
     * @param model the aircraft model name
     * @return Aircraft object of appropriate type
     * @throws IllegalArgumentException if model is not recognized
     */
    public Aircraft createAircraft(String model) throws IllegalArgumentException {
        if (!AIRCRAFT_REGISTRY.containsKey(model)) {
            throw new IllegalArgumentException(
                String.format("Aircraft model '%s' is not registered", model)
            );
        }
        
        AircraftType type = AIRCRAFT_REGISTRY.get(model);
        
        switch (type) {
            case PASSENGER_PLANE:
                return new PassengerPlane(model);
            case HELICOPTER:
                return new Helicopter(model);
            case PASSENGER_DRONE:
                return new PassengerDrone(model);
            default:
                throw new IllegalArgumentException(
                    String.format("Unknown aircraft type: %s", type)
                );
        }
    }
    
    /**
     * Gets the type of an aircraft model without creating it
     * 
     * @param model the aircraft model name
     * @return AircraftType of the model
     */
    public AircraftType getAircraftType(String model) {
        return AIRCRAFT_REGISTRY.get(model);
    }
    
    /**
     * Checks if an aircraft model is registered
     * 
     * @param model the aircraft model name
     * @return true if registered, false otherwise
     */
    public boolean isAircraftRegistered(String model) {
        return AIRCRAFT_REGISTRY.containsKey(model);
    }
    
    /**
     * Gets all registered aircraft models
     * 
     * @return array of registered model names
     */
    public String[] getRegisteredModels() {
        return AIRCRAFT_REGISTRY.keySet().toArray(new String[0]);
    }
}