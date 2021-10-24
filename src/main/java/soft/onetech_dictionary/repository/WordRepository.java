package soft.onetech_dictionary.repository;

import soft.onetech_dictionary.model.Word;

import java.util.List;
import java.util.Optional;

public interface WordRepository {

    List<Word> findAll();

    Optional<Word> findWordById(Long id);

    Optional<Word> findWordByName(String name);

    List<Word> findWordByType(String name);

    void save(Word word);

    Long findByName(String name);
}
