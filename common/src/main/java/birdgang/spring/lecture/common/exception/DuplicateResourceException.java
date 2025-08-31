package birdgang.spring.lecture.common.exception;

public class DuplicateResourceException extends BusinessException {
    
    public DuplicateResourceException(String resourceName, String fieldName, Object fieldValue) {
        super("DUPLICATE_RESOURCE", 
              String.format("%s already exists with %s: %s", resourceName, fieldName, fieldValue));
    }
    
    public DuplicateResourceException(String message) {
        super("DUPLICATE_RESOURCE", message);
    }
}
