package soft.onetech_dictionary.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import soft.onetech_dictionary.dto.TextResponse;
import soft.onetech_dictionary.model.Word;
import soft.onetech_dictionary.model.WordType;
import soft.onetech_dictionary.repository.WordRepository;

import java.util.*;

import static org.mockito.Mockito.*;

class TextServiceImplTest {
    @Mock
    WordRepository wordRepository;
    @InjectMocks
    TextServiceImpl textServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testTextChecker() {
        Optional<Word> optionalWord = Optional.of(new Word("привет", "Обращённое к кому-н. выражение чувства личной приязни, доброго пожелания, солидарности", WordType.NOT_TERM, Set.of("здравствуйте")));
        when(wordRepository.findByName(anyString())).thenReturn(optionalWord);

        TextResponse result = textServiceImpl.textChecker(optionalWord.get().getName());
        Assertions.assertEquals(new TextResponse(1, 1L, new HashSet<>(), 0L, 2L, 0L, new HashMap<Word, Long>() {{
            put(optionalWord.get(), Long.valueOf(1));
        }}), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme