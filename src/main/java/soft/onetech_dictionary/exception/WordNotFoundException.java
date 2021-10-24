package soft.onetech_dictionary.exception;

public class WordNotFoundException extends RuntimeException {

    public WordNotFoundException(String message) {
        super(message);
    }
}
