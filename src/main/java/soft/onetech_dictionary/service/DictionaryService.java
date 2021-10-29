package soft.onetech_dictionary.service;


import soft.onetech_dictionary.model.Dictionary;
import soft.onetech_dictionary.model.Word;
import soft.onetech_dictionary.model.WordType;

import java.util.List;

public interface DictionaryService {

    List<Dictionary> findAll();

    List<Word> findWordsByWordTypeName(String dictionaryName, String typeName);

    Dictionary findByName(String name);

    void deleteByName(String name);

    boolean existsByName(String name);

    void save(Dictionary dictionary);

    List<WordType> findAllWordTypes();
}
