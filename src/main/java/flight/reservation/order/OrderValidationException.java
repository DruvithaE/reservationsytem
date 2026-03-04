package flight.reservation.order;

/**
 * Exception thrown when order validation fails during construction
 */
public class OrderValidationException extends Exception {
    
    /**
     * Constructor with message
     * 
     * @param message error message
     */
    public OrderValidationException(String message) {
        super(message);
    }
    
    /**
     * Constructor with message and cause
     * 
     * @param message error message
     * @param cause the cause exception
     */
    public OrderValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}