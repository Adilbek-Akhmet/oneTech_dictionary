package soft.onetech_dictionary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import soft.onetech_dictionary.dto.TextRequest;
import soft.onetech_dictionary.dto.TextResponse;
import soft.onetech_dictionary.service.DictionaryService;
import soft.onetech_dictionary.service.TextService;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TextController.class)
@ExtendWith(SpringExtension.class)
class TextControllerTest {

    @MockBean
    TextService textService;

    @MockBean
    DictionaryService dictionaryService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void testTextResult() throws Exception {
        TextRequest request = new TextRequest("ff HI hi");

        Map<String, Long> numberOfEachWord = new HashMap<>();
        numberOfEachWord.put("ff", 1L);
        numberOfEachWord.put("hi", 2L);

        TextResponse textResponse = TextResponse.builder()
                .numberOfWords(3)
                .numberOfEachWord(numberOfEachWord)
                .foundInDatabase(0)
                .absentInDatabase(Set.of("ff", "hi"))
                .moreDetailedInfoWordsInDatabase(new HashMap<>())
                .build();

        when(textService.textChecker(request.getText())).thenReturn(textResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/dictionaries/text/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON);

        System.out.println(objectMapper.writeValueAsString(textResponse));
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(textResponse), true));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme