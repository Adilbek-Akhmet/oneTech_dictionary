package soft.onetech_dictionary.repository;

import org.springframework.stereotype.Repository;
import soft.onetech_dictionary.database.Database;
import soft.onetech_dictionary.model.Word;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static soft.onetech_dictionary.database.Database.*;

@Repository
public class WordRepositoryImpl implements WordRepository {

    public Set<Word> findAll() {
        return INSTANCE.findAll();
    }

    public Optional<Word> findByName(String name) {
        return INSTANCE.findByName(name);
    }

    public void add(Word word) {
        INSTANCE.add(word);
    }

}
