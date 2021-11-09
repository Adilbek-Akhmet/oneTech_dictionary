package soft.onetech_dictionary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.onetech_dictionary.exception.DictionaryWithSuchNameDoesNotExists;
import soft.onetech_dictionary.exception.WordTypeNotFoundException;
import soft.onetech_dictionary.model.Dictionary;
import soft.onetech_dictionary.model.Word;
import soft.onetech_dictionary.model.WordType;
import soft.onetech_dictionary.repository.DictionaryRepository;
import soft.onetech_dictionary.repository.WordTypeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryRepository dictionaryRepository;
    private final WordTypeRepository wordTypeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Dictionary> findAll() {
        return dictionaryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Word> findWordsByWordTypeName(String dictionaryName, String typeName) {
        if (wordTypeRepository.findByName(typeName).isEmpty()) {
            throw new WordTypeNotFoundException("Неправильный тип слова");
        }
        return findByName(dictionaryName).getWordList().stream()
                .filter(word -> word.getType().getName().equals(typeName))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public Dictionary findByName(String name) {
        return dictionaryRepository.findByName(name)
                .orElseThrow(() -> new DictionaryWithSuchNameDoesNotExists("Словарь не существует"));
    }

    @Override
    public void deleteByName(String name) {
        dictionaryRepository.deleteByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return dictionaryRepository.existsByName(name);
    }


    @Override
    public void save(Dictionary dictionary) {
        dictionaryRepository.save(dictionary);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WordType> findAllWordTypes() {
        return wordTypeRepository.findAll();
    }


}
