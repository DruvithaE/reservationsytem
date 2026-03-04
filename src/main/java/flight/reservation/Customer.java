package flight.reservation;

import flight.reservation.flight.ScheduledFlight;
import flight.reservation.order.FlightOrder;
import flight.reservation.order.Order;
import flight.reservation.order.OrderValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Refactored Customer class using Builder Pattern for order creation.
 */
public class Customer {
    
    private String email;
    private String name;
    private List<Order> orders;
    
    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
        this.orders = new ArrayList<>();
    }
    
    /**
     * Creates a new flight order using the builder pattern
     * Much cleaner than the previous multi-setter approach!
     * 
     * @param passengerNames list of passenger names
     * @param flights list of scheduled flights
     * @param price ticket price
     * @return created FlightOrder
     * @throws OrderValidationException if order is invalid
     */
    public FlightOrder createOrder(List<String> passengerNames, 
                                  List<ScheduledFlight> flights, 
                                  double price) throws OrderValidationException {
        // Build order using builder pattern - clean and readable!
        FlightOrder order = FlightOrder.builder()
            .withCustomer(this)
            .withPassengerNames(passengerNames)
            .withFlights(flights)
            .withPrice(price)
            .build();
        
        orders.add(order);
        return order;
    }
    
    /**
     * Creates a new flight order with optional parameters
     * Shows the power of builder pattern for optional parameters
     * 
     * @param passengerNames list of passenger names
     * @param flights list of scheduled flights
     * @param price ticket price
     * @param specialRequests special requests
     * @param insuranceIncluded whether insurance is included
     * @return created FlightOrder
     * @throws OrderValidationException if order is invalid
     */
    public FlightOrder createPremiumOrder(List<String> passengerNames,
                                         List<ScheduledFlight> flights,
                                         double price,
                                         String specialRequests,
                                         boolean insuranceIncluded) throws OrderValidationException {
        // Build order with optional parameters - very clean!
        FlightOrder order = FlightOrder.builder()
            .withCustomer(this)
            .withPassengerNames(passengerNames)
            .withFlights(flights)
            .withPrice(price)
            .withSpecialRequests(specialRequests)
            .withInsurance(insuranceIncluded)
            .withLoyaltyPoints(100)
            .build();
        
        orders.add(order);
        return order;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Order> getOrders() {
        return orders;
    }
    
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    
    /**
     * Gets all flight orders for this customer
     * 
     * @return list of FlightOrders
     */
    public List<FlightOrder> getFlightOrders() {
        return orders.stream()
            .filter(o -> o instanceof FlightOrder)
            .map(o -> (FlightOrder) o)
            .collect(Collectors.toList());
    }
    
    /**
     * Gets total spent by customer
     * 
     * @return total amount spent
     */
    public double getTotalSpent() {
        return orders.stream()
            .mapToDouble(Order::getPrice)
            .sum();
    }
}