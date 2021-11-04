package soft.onetech_dictionary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soft.onetech_dictionary.model.Word;

import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long> {
    Optional<Word> findByName(String name);
}
