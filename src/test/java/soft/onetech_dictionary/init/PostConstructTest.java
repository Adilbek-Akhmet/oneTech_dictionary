package soft.onetech_dictionary.init;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import soft.onetech_dictionary.OneTechDictionaryApplication;
import soft.onetech_dictionary.model.Dictionary;
import soft.onetech_dictionary.service.DictionaryService;

import static org.mockito.Mockito.*;

class PostConstructTest {
    @Mock
    DictionaryService dictionaryService;

    @InjectMocks
    OneTechDictionaryApplication oneTechDictionaryApplication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInit() {
        oneTechDictionaryApplication.init();

        verify(dictionaryService, times(2)).save(any(Dictionary.class));
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme