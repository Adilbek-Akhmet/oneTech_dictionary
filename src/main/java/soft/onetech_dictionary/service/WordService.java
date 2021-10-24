package soft.onetech_dictionary.service;

import soft.onetech_dictionary.dto.WordRequest;
import soft.onetech_dictionary.model.Word;

import java.util.List;

public interface WordService {

    List<Word> findAll();

    List<Word> findAllReversed();

    Word findWordByName(String name);

    List<Word> findByWordsByType(String type);

    void save(WordRequest wordRequest);

}
