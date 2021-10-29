package soft.onetech_dictionary.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import soft.onetech_dictionary.model.Dictionary;

import java.util.Optional;

public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {
    Optional<Dictionary> findByName(String name);
    void deleteByName(String name);
    boolean existsByName(String name);
}
