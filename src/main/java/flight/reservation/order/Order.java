package flight.reservation.order;

import flight.reservation.Customer;
import flight.reservation.Passenger;
import java.util.List;
import java.util.UUID;

/**
 * Base class for orders.
 * Refactored to work with Builder Pattern.
 */
public class Order {
    
    private final UUID id;
    private final double price;
    private boolean isClosed = false;
    private final Customer customer;
    private final List<Passenger> passengers;
    
    /**
     * Protected constructor - use builders to create orders
     * 
     * @param customer the customer
     * @param passengers the passengers
     * @param price the price
     */
    protected Order(Customer customer, List<Passenger> passengers, double price) {
        this.id = UUID.randomUUID();
        this.customer = customer;
        this.passengers = passengers;
        this.price = price;
    }
    
    public UUID getId() {
        return id;
    }
    
    public double getPrice() {
        return price;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public List<Passenger> getPassengers() {
        return passengers;
    }
    
    public boolean isClosed() {
        return isClosed;
    }
    
    public void setClosed() {
        isClosed = true;
    }
}