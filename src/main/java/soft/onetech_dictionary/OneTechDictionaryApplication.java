package soft.onetech_dictionary;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import soft.onetech_dictionary.model.Dictionary;
import soft.onetech_dictionary.model.Word;
import soft.onetech_dictionary.model.WordType;
import soft.onetech_dictionary.service.DictionaryService;

import java.util.List;


@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy
@Log4j2
public class OneTechDictionaryApplication {

    private static DictionaryService dictionaryService;

    @Autowired
    public OneTechDictionaryApplication(DictionaryService dictionaryService) {
        OneTechDictionaryApplication.dictionaryService = dictionaryService;
    }

    public static void main(String[] args) {
        SpringApplication.run(OneTechDictionaryApplication.class, args);

        WordType term = new WordType("термин");
        WordType not_term = new WordType("не термин");

        Dictionary dictionary = new Dictionary(
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

        System.out.println("---Сохранить словарь---");
        dictionaryService.save(dictionary);

        System.out.println("----Найти словарь----");
        System.out.println(dictionaryService.findByName("russia"));

        System.out.println("---Все типы слов---");
        System.out.println(dictionaryService.findAllWordTypes());

        System.out.println("----Найти слова в russia dictionary по типу----");
        System.out.println(dictionaryService.findWordsByWordTypeName("russia", "термин"));

        System.out.println("---Существует ли словарь russia");
        System.out.println(dictionaryService.existsByName("russia"));

        System.out.println("---Удалить словарь---");
        dictionaryService.deleteByName("russia");

        System.out.println("---Найти все словари(После удаление должно быт пусто)---");
        System.out.println(dictionaryService.findAll());

    }

}
