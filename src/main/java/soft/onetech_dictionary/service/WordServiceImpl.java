package soft.onetech_dictionary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import soft.onetech_dictionary.aspect.ExceptionLogger;
import soft.onetech_dictionary.aspect.MethodLogger;
import soft.onetech_dictionary.dto.WordRequest;
import soft.onetech_dictionary.exception.WordNotFoundException;
import soft.onetech_dictionary.exception.WordTypeNotFoundException;
import soft.onetech_dictionary.model.Word;
import soft.onetech_dictionary.repository.WordRepository;
import soft.onetech_dictionary.repository.WordTypeRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;
    private final WordTypeRepository wordTypeRepository;


    @Override
    public List<Word> findAll() {
        return wordRepository.findAll();
    }

    @Override
    public List<Word> findAllReversed() {
        return wordRepository.findAll().stream()
                .sorted(Comparator.comparing(Word::getName).reversed())
                .collect(Collectors.toList());
    }

    @Override
    @ExceptionLogger
    public Word findWordByName(String name) {
        return wordRepository.findWordByName(name)
                .orElseThrow(() -> new WordNotFoundException("Слова не найдено"));
    }

    @Override
    @ExceptionLogger
    public List<Word> findByWordsByType(String type) {
        if (wordTypeRepository.findByTypeName(type).isEmpty()) {
            throw new WordTypeNotFoundException("Неправильный тип слова");
        }
        return wordRepository.findWordByType(type);
    }

    @Override
    @MethodLogger
    @ExceptionLogger
    public void save(WordRequest wordRequest) {
        if (wordTypeRepository.findByTypeName(wordRequest.getType()).isEmpty()) {
            throw new WordTypeNotFoundException("Неправильный тип слова");
        }

        List<Word> synonyms = new ArrayList<>();
        for (String name: wordRequest.getSynonymList()) {
            Word word = wordRepository.findWordByName(name)
                    .orElseThrow(() -> new WordNotFoundException("Синоним не найдено"));
            synonyms.add(word);
        }
        wordRepository.save(
                Word.builder()
                        .name(wordRequest.getName())
                        .definition(wordRequest.getDefinition())
                        .type(wordTypeRepository.findByTypeName(wordRequest.getType()).get())
                        .synonymList(synonyms)
                        .build()
        );
    }


}
