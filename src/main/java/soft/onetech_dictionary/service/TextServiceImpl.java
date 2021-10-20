package soft.onetech_dictionary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soft.onetech_dictionary.dto.TextResponse;
import soft.onetech_dictionary.model.Word;
import soft.onetech_dictionary.model.WordType;
import soft.onetech_dictionary.repository.WordRepository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TextServiceImpl implements TextService {

    private final WordRepository wordRepository;

    public TextResponse textChecker(String text) {
        String[] words = text.split("\\s+");
        return TextResponse.builder()
                .textLength(countAllWords(words))
                .foundInDatabase(countAllWordsInDatabase(words))
                .absentInDatabase(absentWordsInDatabase(words))
                .complexWords(countComplexWords(words))
                .numberOfSlogs(countSlogs(words))
                .numberOfTerms(countNumberOfTerms(words))
                .moreDetailedInfoWordsInDatabase(moreDetailedInfoWordsInDatabase(words))
                .build();
    }

    private int countAllWords(String[] words) {
        return words.length;
    }

    private long countAllWordsInDatabase(String[] words) {
        return countWords(words).entrySet().stream()
                .filter(map -> wordRepository.findByName(map.getKey()).isPresent())
                .mapToLong(Map.Entry::getValue)
                .sum();
    }

    private Set<String> absentWordsInDatabase(String[] words) {
        return countWords(words).keySet().stream()
                .filter(key -> wordRepository.findByName(key).isEmpty())
                .collect(Collectors.toSet());
    }

    private long countComplexWords(String[] words) {
        return countWords(words).entrySet().stream()
                .filter(map -> countVowels(map.getKey()) > 4)
                .mapToLong(Map.Entry::getValue)
                .sum();
    }

    private long countSlogs(String[] words) {
        return countWords(words).entrySet().stream()
                .mapToLong(map -> countVowels(map.getKey()) * map.getValue())
                .sum();
    }

    private long countNumberOfTerms(String[] words) {
        return countWords(words).keySet().stream()
                .filter(name -> {
                    if (wordRepository.findByName(name).isPresent()) {
                        return wordRepository
                                .findByName(name)
                                .get()
                                .getType()
                                .equals(WordType.TERM);
                    }
                    return false;
                })
                .count();
    }

    private Map<Word, Long> moreDetailedInfoWordsInDatabase(String[] words) {
        return countWords(words).keySet().stream()
                .filter(key -> wordRepository.findByName(key).isPresent())
                .map(key -> wordRepository.findByName(key).get())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }


    private Map<String, Long> countWords(String[] words) {
        return Arrays.stream(words)
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));
    }

    private long countVowels(String word) {
        int vowels = 0;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (ch == 'а' || ch == 'у' || ch == 'о' || ch == 'ы' ||
                    ch == 'и' || ch == 'э' || ch == 'я' || ch == 'ю' ||
                    ch == 'ё' || ch == 'е') {
                vowels++;
            }
        }
        return vowels;
    }
}
