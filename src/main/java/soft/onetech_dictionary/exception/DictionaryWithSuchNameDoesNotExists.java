package soft.onetech_dictionary.exception;

public class DictionaryWithSuchNameDoesNotExists extends RuntimeException {
    public DictionaryWithSuchNameDoesNotExists(String message) {
        super(message);
    }
}
