package soft.onetech_dictionary;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import soft.onetech_dictionary.model.Dictionary;
import soft.onetech_dictionary.model.Word;
import soft.onetech_dictionary.model.WordType;
import soft.onetech_dictionary.service.DictionaryService;

import javax.annotation.PostConstruct;
import java.util.List;


@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy
@Log4j2
@RequiredArgsConstructor
public class OneTechDictionaryApplication {

    private final DictionaryService dictionaryService;

    @PostConstruct
    public void init() {
        WordType rusTerm = new WordType("термин");
        WordType rusNotTerm = new WordType("неТермин");

        Dictionary rusDictionary = new Dictionary(
                "russia",
                List.of(
                        Word.builder()
                                .name("здравствуйте")
                                .definition("здравствуйте это")
                                .type(rusNotTerm)
                                .build(),
                        Word.builder()
                                .name("привет")
                                .definition("привет это")
                                .type(rusNotTerm)
                                .build(),
                        Word.builder()
                                .name("химия")
                                .definition("химия это")
                                .type(rusTerm)
                                .build()
                ));

        WordType engTerm = new WordType("term");
        WordType engNotTerm = new WordType("notTerm");

        Dictionary engDictionary = new Dictionary(
                "english",
                List.of(
                        Word.builder()
                                .name("hello")
                                .definition("hello is")
                                .type(engNotTerm)
                                .build(),
                        Word.builder()
                                .name("hi")
                                .definition("hi is")
                                .type(engNotTerm)
                                .build(),
                        Word.builder()
                                .name("chemistry")
                                .definition("chemistry is")
                                .type(engTerm)
                                .build()
                ));

        dictionaryService.save(rusDictionary);
        dictionaryService.save(engDictionary);
    }

    public static void main(String[] args) {
        SpringApplication.run(OneTechDictionaryApplication.class, args);
    }

}
