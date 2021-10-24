package soft.onetech_dictionary;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import soft.onetech_dictionary.dto.WordRequest;
import soft.onetech_dictionary.service.WordService;

import java.util.List;


@SpringBootApplication
@EnableAspectJAutoProxy
@Log4j2
public class OneTechDictionaryApplication {

    private static WordService wordService;

    @Autowired
    public OneTechDictionaryApplication(WordService wordService) {
        OneTechDictionaryApplication.wordService = wordService;
    }

    public static void main(String[] args) {
        SpringApplication.run(OneTechDictionaryApplication.class, args);

        System.out.println("----Все слова в базе----");
        System.out.println(wordService.findAll());

        System.out.println("----Все слова в базе в обратном порядке----");
        System.out.println(wordService.findAllReversed());

        System.out.println("----Найти слова в базе----");
        System.out.println(wordService.findWordByName("привет"));

        System.out.println("----Найти все слова в базе где тип термин ----");
        System.out.println(wordService.findByWordsByType("термин"));

        System.out.println("@MethodLogger----Сохранить слова----");
        wordService.save(
                WordRequest.builder()
                        .name("здравствуйте")
                        .definition("здравствуйте это")
                        .type("не термин")
                        .synonymList(List.of("привет"))
                        .build()
        );

        System.out.println("----Найти Сохранненые слова в базе----");
        System.out.println(wordService.findWordByName("здравствуйте"));

        System.out.println("@ExceptionLogger----Искать слова которые нет в базе----");
        System.out.println(wordService.findWordByName("приветыыыыыыы"));

    }

}
