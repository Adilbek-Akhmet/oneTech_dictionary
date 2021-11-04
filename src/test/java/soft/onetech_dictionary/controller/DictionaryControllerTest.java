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
import soft.onetech_dictionary.kafka.KafkaProducer;
import soft.onetech_dictionary.model.Dictionary;
import soft.onetech_dictionary.model.Word;
import soft.onetech_dictionary.model.WordType;
import soft.onetech_dictionary.service.DictionaryService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DictionaryController.class)
@ExtendWith(SpringExtension.class)
class DictionaryControllerTest {
    @MockBean
    KafkaProducer kafkaProducer;

    @MockBean
    DictionaryService dictionaryService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper;

    Dictionary dictionary;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        WordType term = new WordType("term");
        WordType not_term = new WordType("not term");

        dictionary = new Dictionary(
                "english",
                List.of(
                        Word.builder()
                                .name("Hello")
                                .definition("Hello is")
                                .type(not_term)
                                .build()
                ));
    }


    @Test
    void testFindAll() throws Exception {
        when(dictionaryService.findAll()).thenReturn(List.of(dictionary));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/dictionaries")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(dictionary)), true));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void testFindByName() throws Exception {
        when(dictionaryService.findByName("english")).thenReturn(dictionary);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/dictionaries/{dictionaryName}", "english")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dictionary), true));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void testFindWordsByWordTypeName() throws Exception {
        String dictionaryName = "english";
        String wordType = "not term";

        Word word = Word.builder()
                .name("Hello")
                .definition("Hello is")
                .type(new WordType(wordType))
                .build();

        when(dictionaryService.findWordsByWordTypeName(dictionaryName, wordType)).thenReturn(List.of(
                word
        ));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/dictionaries/{dictionaryName}/{wordTypeName}", dictionaryName, wordType)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(word)), true));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void testFindAllWordTypes() throws Exception {
        String wordType1 = "term";
        String wordType2 = "not term";

        when(dictionaryService.findAllWordTypes()).thenReturn(List.of(
                new WordType(wordType1),
                new WordType(wordType2)
        ));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/dictionaries/word/types")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"term\"}, {\"name\":\"not term\"}]", false));


    }

    @Test
    @WithMockUser(roles = {"USER"})
    void testSave() throws Exception {
        String dictionaryJson = "{\"name\":\"english\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/dictionaries/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(dictionaryJson)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated());

        //Because I used PostConstruct
        verify(dictionaryService, times(1)).save(any(Dictionary.class));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void testDeleteByName() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/dictionaries/delete/{dictionaryName}", "english");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(dictionaryService, times(1)).deleteByName(anyString());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme