package soft.onetech_dictionary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soft.onetech_dictionary.model.WordType;

import java.util.Optional;

public interface WordTypeRepository extends JpaRepository<WordType, Long> {
    Optional<WordType> findByName(String name);
}
