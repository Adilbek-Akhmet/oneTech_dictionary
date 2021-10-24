package soft.onetech_dictionary.repository;

import soft.onetech_dictionary.model.WordType;

import java.util.Optional;

public interface WordTypeRepository {

    Optional<WordType> findByTypeId(Long id);

    Optional<WordType> findByTypeName(String typeName);
}
