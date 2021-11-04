package soft.onetech_dictionary.service;

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
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTextChecker() {
        TextRequest request = new TextRequest("ff hi hi");

        when(wordRepository.findByName(request.getText())).thenReturn(
                Optional.of(
                        Word.builder()
                                .name("hi")
                                .definition("hi is")
                                .type(new WordType("not term"))
                                .build()
                )
        );

        Map<String, Long> numberOfEachWord = new HashMap<>();
        numberOfEachWord.put("ff", 1L);
        numberOfEachWord.put("hi", 2L);

        TextResponse expected = TextResponse.builder()
                .numberOfWords(3)
                .numberOfEachWord(numberOfEachWord)
                .foundInDatabase(0)
                .absentInDatabase(Set.of("ff", "hi"))
                .moreDetailedInfoWordsInDatabase(new HashMap<>())
                .build();

        TextResponse actual = textServiceImpl.textChecker("ff HI hi");

        Assertions.assertEquals(expected, actual);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme