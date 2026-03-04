package flight.reservation.order;

import flight.reservation.Customer;
import flight.reservation.Passenger;
import flight.reservation.flight.ScheduledFlight;
import flight.reservation.payment.PaymentStrategy;
import java.util.Arrays;
import java.util.List;

/**
 * Refactored FlightOrder using Builder Pattern.
 * Orders are now immutable and always valid.
 */
public class FlightOrder extends Order {
    
    private final List<ScheduledFlight> flights;
    static List<String> noFlyList = Arrays.asList("Peter", "Johannes");
    
    // Optional fields (final for immutability)
    private final String specialRequests;
    private final boolean insuranceIncluded;
    private final int loyaltyPoints;
    
    // Strategy pattern: payment method
    private PaymentStrategy paymentStrategy;
    
    /**
     * Private constructor - use FlightOrderBuilder instead
     * 
     * @param flights the scheduled flights
     * @param customer the customer
     * @param passengers the passengers
     * @param price the order price
     * @param specialRequests special requests
     * @param insuranceIncluded whether insurance is included
     * @param loyaltyPoints loyalty points for the customer
     */
    protected FlightOrder(List<ScheduledFlight> flights, 
                         Customer customer,
                         List<Passenger> passengers,
                         double price,
                         String specialRequests,
                         boolean insuranceIncluded,
                         int loyaltyPoints) {
        super(customer, passengers, price);
        this.flights = flights;
        this.specialRequests = specialRequests;
        this.insuranceIncluded = insuranceIncluded;
        this.loyaltyPoints = loyaltyPoints;
    }
    
    /**
     * Creates a new builder for FlightOrder
     * 
     * @return a new FlightOrderBuilder
     */
    public static FlightOrderBuilder builder() {
        return new FlightOrderBuilder();
    }
    
    public static List<String> getNoFlyList() {
        return noFlyList;
    }
    
    public List<ScheduledFlight> getScheduledFlights() {
        return flights;
    }
    
    /**
     * Sets the payment strategy to be used for this order
     * 
     * @param paymentStrategy the payment strategy
     */
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
    
    /**
     * Processes payment using the set payment strategy
     * 
     * @return true if payment successful
     * @throws IllegalStateException if order is already closed or no payment method set
     */
    public boolean processPayment() throws IllegalStateException {
        if (isClosed()) {
            System.out.println("Order already processed. Skipping payment.");
            return true;
        }
        
        if (paymentStrategy == null) {
            throw new IllegalStateException(
                "No payment method selected. Please set a payment strategy."
            );
        }
        
        if (paymentStrategy.pay(this.getPrice())) {
            this.setClosed();
            return true;
        }
        return false;
    }
    
    /**
     * Gets the special requests for this order
     * 
     * @return special requests string
     */
    public String getSpecialRequests() {
        return specialRequests;
    }
    
    /**
     * Checks if insurance is included in this order
     * 
     * @return true if insurance included
     */
    public boolean isInsuranceIncluded() {
        return insuranceIncluded;
    }
    
    /**
     * Gets the loyalty points associated with this order
     * 
     * @return loyalty points
     */
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }
    
    /**
     * Calculates total cost including optional insurance
     * 
     * @return total cost
     */
    public double getTotalCost() {
        double total = this.getPrice();
        if (insuranceIncluded) {
            // Insurance is 5% of ticket price
            total += (total * 0.05);
        }
        return total;
    }
    
    /**
     * Gets a detailed order summary
     * 
     * @return order summary
     */
    public String getOrderSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("\n========== FLIGHT ORDER SUMMARY ==========\n");
        summary.append("Order ID: ").append(getId()).append("\n");
        summary.append("Customer: ").append(getCustomer().getName()).append("\n");
        summary.append("Email: ").append(getCustomer().getEmail()).append("\n");
        summary.append("\nPassengers:\n");
        for (Passenger p : getPassengers()) {
            summary.append("  - ").append(p.getName()).append("\n");
        }
        summary.append("\nFlights: ").append(flights.size()).append("\n");
        for (ScheduledFlight flight : flights) {
            try {
                summary.append("  - Flight ").append(flight.getNumber())
                    .append(": ").append(flight.getDeparture().getCode())
                    .append(" -> ").append(flight.getArrival().getCode()).append("\n");
            } catch (Exception e) {
                // Handle exception silently
            }
        }
        summary.append("\nTicket Price: $").append(String.format("%.2f", this.getPrice())).append("\n");
        if (insuranceIncluded) {
            double insurance = this.getPrice() * 0.05;
            summary.append("Insurance: $").append(String.format("%.2f", insurance)).append("\n");
        }
        summary.append("Total Cost: $").append(String.format("%.2f", getTotalCost())).append("\n");
        summary.append("Status: ").append(isClosed() ? "PAID" : "PENDING PAYMENT").append("\n");
        if (!specialRequests.isEmpty()) {
            summary.append("Special Requests: ").append(specialRequests).append("\n");
        }
        summary.append("=========================================\n");
        return summary.toString();
    }
}