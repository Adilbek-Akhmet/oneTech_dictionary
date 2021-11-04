package soft.onetech_dictionary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soft.onetech_dictionary.dto.TextResponse;
import soft.onetech_dictionary.model.Word;
import soft.onetech_dictionary.repository.WordRepository;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TextServiceImpl implements TextService {

    private final WordRepository wordRepository;

    public TextResponse textChecker(String text) {
        String[] words = text.split("\\s+");
        return TextResponse.builder()
                .numberOfWords(countAllWords(words))
                .numberOfEachWord(countWords(words))
                .foundInDatabase(countAllWordsInDatabase(words))
                .absentInDatabase(absentWordsInDatabase(words))
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


    private Map<Word, Long> moreDetailedInfoWordsInDatabase(String[] words) {
        return Arrays.stream(words)
                .map(String::toLowerCase)
                .filter(word -> wordRepository.findByName(word).isPresent())
                .map(word -> wordRepository.findByName(word).orElseThrow())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private Map<String, Long> countWords(String[] words) {
        return Arrays.stream(words)
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));
    }
}
