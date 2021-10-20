package soft.onetech_dictionary.database;

import soft.onetech_dictionary.model.Word;
import soft.onetech_dictionary.model.WordType;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public enum Database {
    INSTANCE;

    private final Set<Word> wordSet = new HashSet<>(
            Set.of(
                    new Word("привет", "Обращённое к кому-н. выражение чувства личной приязни, доброго пожелания, солидарности", WordType.NOT_TERM, Set.of("здравствуйте")),
                    new Word("красивая", "приятный на вид, радующий взгляд, соответствующий представлениям о красоте и гармонии", WordType.NOT_TERM, Set.of("симпатичный", "очаровательный", "прекрасный")),
                    new Word("валюта", "денежная система государства и соответствующая официальная денежная единица", WordType.TERM, Set.of("денежная единица"))
            )
    );


    public Set<Word> findAll() {
        return wordSet;
    }

    public Optional<Word> findByName(String name) {
        return wordSet.stream()
                .filter(word -> word.getName().equals(name))
                .findFirst();
    }

    public void add(Word word) {
        wordSet.add(word);
    }

}
