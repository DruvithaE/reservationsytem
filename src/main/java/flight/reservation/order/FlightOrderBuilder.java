package flight.reservation.order;

import flight.reservation.Customer;
import flight.reservation.Passenger;
import flight.reservation.flight.ScheduledFlight;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Builder for constructing FlightOrder objects step-by-step.
 * Ensures orders are always valid and complete.
 * 
 * Usage:
 * FlightOrder order = new FlightOrderBuilder()
 *     .withFlights(flights)
 *     .withCustomer(customer)
 *     .withPassengers(passengerNames)
 *     .withPrice(price)
 *     .withSpecialRequests("Window seats")
 *     .withInsurance(true)
 *     .build();
 */
public class FlightOrderBuilder {
    
    // Required fields
    private List<ScheduledFlight> flights;
    private Customer customer;
    private List<Passenger> passengers;
    private double price;
    
    // Optional fields
    private String specialRequests = "";
    private boolean insuranceIncluded = false;
    private int loyaltyPoints = 0;
    
    // Static list of passengers on no-fly list
    private static final List<String> NO_FLY_LIST = Arrays.asList("Peter", "Johannes");
    
    /**
     * Default constructor
     */
    public FlightOrderBuilder() {
        this.flights = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }
    
    /**
     * Sets the flights for this order
     * 
     * @param flights list of scheduled flights
     * @return this builder for method chaining
     * @throws IllegalArgumentException if flights list is null or empty
     */
    public FlightOrderBuilder withFlights(List<ScheduledFlight> flights) {
        if (flights == null || flights.isEmpty()) {
            throw new IllegalArgumentException("Flights list cannot be null or empty");
        }
        this.flights = new ArrayList<>(flights);
        return this;
    }
    
    /**
     * Sets the customer for this order
     * 
     * @param customer the customer
     * @return this builder for method chaining
     * @throws IllegalArgumentException if customer is null
     */
    public FlightOrderBuilder withCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        this.customer = customer;
        return this;
    }
    
    /**
     * Sets the passengers for this order
     * 
     * @param passengers list of passengers
     * @return this builder for method chaining
     * @throws IllegalArgumentException if passengers list is null or empty
     */
    public FlightOrderBuilder withPassengers(List<Passenger> passengers) {
        if (passengers == null || passengers.isEmpty()) {
            throw new IllegalArgumentException("Passengers list cannot be null or empty");
        }
        this.passengers = new ArrayList<>(passengers);
        return this;
    }
    
    /**
     * Sets the passenger names and creates Passenger objects
     * Convenience method that takes strings instead of Passenger objects
     * 
     * @param passengerNames list of passenger names
     * @return this builder for method chaining
     * @throws IllegalArgumentException if passenger names list is null or empty
     */
    public FlightOrderBuilder withPassengerNames(List<String> passengerNames) {
        if (passengerNames == null || passengerNames.isEmpty()) {
            throw new IllegalArgumentException("Passenger names list cannot be null or empty");
        }
        this.passengers = new ArrayList<>();
        for (String name : passengerNames) {
            this.passengers.add(new Passenger(name));
        }
        return this;
    }
    
    /**
     * Sets the total price for this order
     * 
     * @param price the price
     * @return this builder for method chaining
     * @throws IllegalArgumentException if price is negative
     */
    public FlightOrderBuilder withPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
        return this;
    }
    
    /**
     * Sets special requests for this order
     * 
     * @param specialRequests special requests string
     * @return this builder for method chaining
     */
    public FlightOrderBuilder withSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests != null ? specialRequests : "";
        return this;
    }
    
    /**
     * Sets whether insurance is included in this order
     * 
     * @param insuranceIncluded true if insurance included
     * @return this builder for method chaining
     */
    public FlightOrderBuilder withInsurance(boolean insuranceIncluded) {
        this.insuranceIncluded = insuranceIncluded;
        return this;
    }
    
    /**
     * Sets loyalty points for this customer
     * 
     * @param loyaltyPoints number of loyalty points
     * @return this builder for method chaining
     * @throws IllegalArgumentException if loyalty points is negative
     */
    public FlightOrderBuilder withLoyaltyPoints(int loyaltyPoints) {
        if (loyaltyPoints < 0) {
            throw new IllegalArgumentException("Loyalty points cannot be negative");
        }
        this.loyaltyPoints = loyaltyPoints;
        return this;
    }
    
    /**
     * Validates the order before building
     * Checks all required fields and business rules
     * 
     * @throws OrderValidationException if order is invalid
     */
    private void validate() throws OrderValidationException {
        // Check required fields
        if (flights == null || flights.isEmpty()) {
            throw new OrderValidationException("At least one flight must be specified");
        }
        
        if (customer == null) {
            throw new OrderValidationException("Customer must be specified");
        }
        
        if (passengers == null || passengers.isEmpty()) {
            throw new OrderValidationException("At least one passenger must be specified");
        }
        
        if (price <= 0) {
            throw new OrderValidationException("Price must be greater than zero");
        }
        
        // Check no-fly list
        if (NO_FLY_LIST.contains(customer.getName())) {
            throw new OrderValidationException(
                "Customer '" + customer.getName() + "' is on the no-fly list"
            );
        }
        
        // Check passengers for no-fly list
        for (Passenger passenger : passengers) {
            if (NO_FLY_LIST.contains(passenger.getName())) {
                throw new OrderValidationException(
                    "Passenger '" + passenger.getName() + "' is on the no-fly list"
                );
            }
        }
        
        // Check passenger count matches flight capacity
        for (ScheduledFlight flight : flights) {
            try {
                if (flight.getAvailableCapacity() < passengers.size()) {
                    throw new OrderValidationException(
                        "Flight " + flight.getNumber() + " does not have enough capacity. " +
                        "Required: " + passengers.size() + ", Available: " + flight.getAvailableCapacity()
                    );
                }
            } catch (NoSuchFieldException e) {
                throw new OrderValidationException("Error checking flight capacity: " + e.getMessage());
            }
        }
    }
    
    /**
     * Builds and returns the FlightOrder
     * Validates the order before returning
     * 
     * @return a new FlightOrder instance
     * @throws OrderValidationException if order is invalid
     */
    public FlightOrder build() throws OrderValidationException {
        // Validate before building
        validate();
        
        // Create the order
        FlightOrder order = new FlightOrder(
            flights,
            customer,
            passengers,
            price,
            specialRequests,
            insuranceIncluded,
            loyaltyPoints
        );
        
        // Add passengers to flights
        for (ScheduledFlight flight : flights) {
            flight.addPassengers(passengers);
        }
        
        return order;
    }
    
    /**
     * Resets the builder to its initial state
     * 
     * @return this builder for method chaining
     */
    public FlightOrderBuilder reset() {
        this.flights = new ArrayList<>();
        this.customer = null;
        this.passengers = new ArrayList<>();
        this.price = 0;
        this.specialRequests = "";
        this.insuranceIncluded = false;
        this.loyaltyPoints = 0;
        return this;
    }
    
    /**
     * Gets a summary of the current builder state
     * 
     * @return summary string
     */
    public String getSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Order Summary:\n");
        summary.append("  Flights: ").append(flights.size()).append("\n");
        summary.append("  Customer: ").append(customer != null ? customer.getName() : "Not set").append("\n");
        summary.append("  Passengers: ").append(passengers.size()).append("\n");
        summary.append("  Price: $").append(price).append("\n");
        summary.append("  Insurance: ").append(insuranceIncluded ? "Yes" : "No").append("\n");
        if (!specialRequests.isEmpty()) {
            summary.append("  Special Requests: ").append(specialRequests).append("\n");
        }
        return summary.toString();
    }
}