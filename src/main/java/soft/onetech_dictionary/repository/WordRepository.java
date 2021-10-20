package soft.onetech_dictionary.repository;

import soft.onetech_dictionary.model.Word;

import java.util.Optional;
import java.util.Set;

public interface WordRepository {
    Set<Word> findAll();
    Optional<Word> findByName(String name);
    void add(Word word);
}
