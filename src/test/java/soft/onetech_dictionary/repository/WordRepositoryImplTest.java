package soft.onetech_dictionary.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import soft.onetech_dictionary.model.Word;
import soft.onetech_dictionary.model.WordType;

import java.util.*;

class WordRepositoryImplTest {
    WordRepositoryImpl wordRepositoryImpl = new WordRepositoryImpl();

    @Test
    void testFindAll() {
        Set<Word> result = wordRepositoryImpl.findAll();
        Assertions.assertEquals(
                Set.of(
                        new Word("привет", "Обращённое к кому-н. выражение чувства личной приязни, доброго пожелания, солидарности", WordType.NOT_TERM, Set.of("здравствуйте")),
                        new Word("красивая", "приятный на вид, радующий взгляд, соответствующий представлениям о красоте и гармонии", WordType.NOT_TERM, Set.of("симпатичный", "очаровательный", "прекрасный")),
                        new Word("валюта", "денежная система государства и соответствующая официальная денежная единица", WordType.TERM, Set.of("денежная единица"))
                ),
                result
        );
    }

    @Test
    void testFindByName() {
        Optional<Word> result = wordRepositoryImpl.findByName("нет");
        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    void testAdd() {
        wordRepositoryImpl.add(new Word("name", "definition", WordType.TERM, Set.of("new")));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme