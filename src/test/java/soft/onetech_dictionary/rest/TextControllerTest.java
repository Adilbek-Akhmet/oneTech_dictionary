package soft.onetech_dictionary.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import soft.onetech_dictionary.dto.TextRequest;
import soft.onetech_dictionary.dto.TextResponse;
import soft.onetech_dictionary.model.Word;
import soft.onetech_dictionary.model.WordType;
import soft.onetech_dictionary.service.TextService;

import java.util.*;

import static org.mockito.Mockito.*;

class TextControllerTest {
    @Mock
    TextService textService;
    @InjectMocks
    TextController textController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testTextResult() {
        Word word = new Word("привет", "Обращённое к кому-н. выражение чувства личной приязни, доброго пожелания, солидарности", WordType.NOT_TERM, Set.of("здравствуйте"));
        TextResponse expected = new TextResponse(1, 1L, new HashSet<>(), 0L, 2L, 0L, new HashMap<Word, Long>() {{
            put(word, Long.valueOf(1));
        }});
        when(textService.textChecker(anyString())).thenReturn(expected);

        TextResponse result = textController.textResult(new TextRequest("привет"));
        Assertions.assertEquals(expected, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme