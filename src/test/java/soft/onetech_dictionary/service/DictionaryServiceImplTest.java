package soft.onetech_dictionary.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import soft.onetech_dictionary.exception.DictionaryWithSuchNameDoesNotExists;
import soft.onetech_dictionary.exception.WordTypeNotFoundException;
import soft.onetech_dictionary.model.Dictionary;
import soft.onetech_dictionary.model.Word;
import soft.onetech_dictionary.model.WordType;
import soft.onetech_dictionary.repository.DictionaryRepository;
import soft.onetech_dictionary.repository.WordTypeRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;


class DictionaryServiceImplTest {

    @Mock
    DictionaryRepository dictionaryRepository;

    @Mock
    WordTypeRepository wordTypeRepository;

    @InjectMocks
    DictionaryServiceImpl underTest;

    private Dictionary dictionary;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        WordType term = new WordType("термин");
        WordType not_term = new WordType("не термин");

        dictionary = new Dictionary(
                "russia",
                List.of(
                        Word.builder()
                                .name("здравствуйте")
                                .definition("здравствуйте это")
                                .type(not_term)
                                .build(),
                        Word.builder()
                                .name("привет")
                                .definition("привет это")
                                .type(not_term)
                                .build(),
                        Word.builder()
                                .name("химия")
                                .definition("химия это")
                                .type(term)
                                .build()
                ));
    }

    @Test
    void testFindAll() {
        when(dictionaryRepository.findAll()).thenReturn(List.of(dictionary));

        List<Dictionary> result = underTest.findAll();
        Assertions.assertEquals(List.of(dictionary), result);

        verify(dictionaryRepository, times(1)).findAll();
    }

    @Test
    void testFindWordsByWordTypeName() {
        String dictionaryName = "russia";
        WordType term = new WordType("термин");

        when(dictionaryRepository.findByName(anyString())).thenReturn(Optional.of(dictionary));
        when(wordTypeRepository.findByName(anyString())).thenReturn(Optional.of(term));

        List<Word> result = underTest.findWordsByWordTypeName(dictionaryName, term.getName());
        Assertions.assertEquals(
                List.of(
                        Word.builder()
                                .name("химия")
                                .definition("химия это")
                                .type(term)
                                .build()
                ),
                result
        );

        verify(dictionaryRepository, times(1)).findByName(anyString());
        verify(wordTypeRepository, times(1)).findByName(anyString());
    }

    @Test
    void testExceptionFindWordsByWordTypeName() {
        String dictionaryName = "russia";
        String typeName = "no exists";

        assertThatThrownBy(() -> underTest.findWordsByWordTypeName(dictionaryName, typeName))
                .isInstanceOf(WordTypeNotFoundException.class)
                .hasMessage("Неправильный тип слова");

        verify(dictionaryRepository, never()).findByName(anyString());
    }

    @Test
    void testFindByName() {
        when(dictionaryRepository.findByName(anyString())).thenReturn(Optional.of(dictionary));

        Dictionary result = underTest.findByName("russia");
        Assertions.assertEquals(dictionary , result);
    }

    @Test
    void testExceptionFindByName() {
        when(dictionaryRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.findByName("no exists"))
                .isInstanceOf(DictionaryWithSuchNameDoesNotExists.class)
                .hasMessageContaining("Словарь не существует");
    }

    @Test
    void testDeleteByName() {
        underTest.deleteByName("name");
        verify(dictionaryRepository, times(1)).deleteByName(anyString());
    }

    @Test
    void testExistsByName() {
        when(dictionaryRepository.existsByName(anyString())).thenReturn(true);

        boolean result = underTest.existsByName("name");
        Assertions.assertTrue(result);
    }

    @Test
    void testSave() {
        underTest.save(dictionary);
        verify(dictionaryRepository, times(1)).save(dictionary);
    }

    @Test
    void testFindAllWordTypes() {
        List<WordType> wordTypes = List.of(new WordType("термин"), new WordType("не термин"));
        when(wordTypeRepository.findAll()).thenReturn(wordTypes);

        List<WordType> result = underTest.findAllWordTypes();
        Assertions.assertEquals(wordTypes, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme