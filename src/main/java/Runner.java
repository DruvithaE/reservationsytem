import flight.reservation.Airport;
import flight.reservation.flight.Schedule;
import flight.reservation.flight.Flight;
import flight.reservation.plane.AircraftFactory;
import flight.reservation.plane.Aircraft;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    
    static AircraftFactory aircraftFactory = AircraftFactory.getInstance();
    
    static List<Airport> airports = new ArrayList<>();
    
    static List<Aircraft> aircrafts = new ArrayList<>();
    
    static List<Flight> flights = new ArrayList<>();
    
    static Schedule schedule;
    
    static {
        // Initialize airports
        airports.add(new Airport("Berlin Airport", "BER", "Berlin, Berlin"));
        airports.add(new Airport("Frankfurt Airport", "FRA", "Frankfurt, Hesse"));
        airports.add(new Airport("Madrid Barajas Airport", "MAD", "Barajas, Madrid"));
        airports.add(new Airport("Guarulhos International Airport", "GRU", "Guarulhos (São Paulo)"));
        airports.add(new Airport("John F. Kennedy International Airport", "JFK", "Queens, New York, New York"));
        airports.add(new Airport("Istanbul Airport", "IST", "Arnavutköy, Istanbul"));
        airports.add(new Airport("Dubai International Airport", "DXB", "Garhoud, Dubai"));
        airports.add(new Airport("Chengdu Shuangliu International Airport", "CTU", "Shuangliu-Wuhou, Chengdu, Sichuan"));
        
        // Create aircraft using Factory - NO MORE DIRECT INSTANTIATION!
        aircrafts.add(aircraftFactory.createAircraft("A380"));
        aircrafts.add(aircraftFactory.createAircraft("A350"));
        aircrafts.add(aircraftFactory.createAircraft("Embraer 190"));
        aircrafts.add(aircraftFactory.createAircraft("Antonov AN2"));
        aircrafts.add(aircraftFactory.createAircraft("H1"));
        aircrafts.add(aircraftFactory.createAircraft("HypaHype"));
        
        // Create flights using factory-created aircraft
        flights.add(new Flight(1, airports.get(0), airports.get(1), aircrafts.get(0)));
        flights.add(new Flight(2, airports.get(1), airports.get(2), aircrafts.get(1)));
        flights.add(new Flight(3, airports.get(2), airports.get(4), aircrafts.get(2)));
        flights.add(new Flight(4, airports.get(3), airports.get(2), aircrafts.get(3)));
        flights.add(new Flight(5, airports.get(4), airports.get(2), aircrafts.get(4)));
        flights.add(new Flight(6, airports.get(5), airports.get(7), aircrafts.get(5)));
    }
    
    public static void main(String[] args) {
        System.out.println("Aircraft created using Factory Pattern:");
        for (Aircraft aircraft : aircrafts) {
            System.out.println("- " + aircraft.getModel() + 
                             " (Type: " + aircraft.getAircraftType() + ")");
        }
    }
}