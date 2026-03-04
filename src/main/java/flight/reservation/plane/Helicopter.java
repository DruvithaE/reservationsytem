package flight.reservation.plane;

public class Helicopter implements Aircraft {
    
    private final String model;
    private final int passengerCapacity;
    
    public Helicopter(String model) {
        this.model = model;
        if (model.equals("H1")) {
            this.passengerCapacity = 4;
        } else if (model.equals("H2")) {
            this.passengerCapacity = 6;
        } else {
            throw new IllegalArgumentException(
                String.format("Model type '%s' is not recognized", model)
            );
        }
    }
    
    @Override
    public String getModel() {
        return model;
    }
    
    @Override
    public int getPassengerCapacity() {
        return passengerCapacity;
    }
    
    @Override
    public int getCrewCapacity() {
        return 2;
    }
    
    @Override
    public AircraftType getAircraftType() {
        return AircraftType.HELICOPTER;
    }
}