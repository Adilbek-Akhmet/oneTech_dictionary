package soft.onetech_dictionary;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import soft.onetech_dictionary.dto.TextResponse;
import soft.onetech_dictionary.service.TextService;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@SpringBootApplication
@Log4j2
public class OneTechDictionaryApplication {

    private static TextService textService;

    @Autowired
    public OneTechDictionaryApplication(TextService textService) {
        OneTechDictionaryApplication.textService = textService;
    }

    public static void main(String[] args) {
        SpringApplication.run(OneTechDictionaryApplication.class, args);

        String text = "Привет   красивая экономика валюта Экономика как хозяйство система хозяйствования обеспечивающая общество материальными вещественными и нематериальными духовными благами";
        System.out.println("---Программа проверка текста---");
        System.out.println("Текст который нужно проверить: " + text);

        TextResponse textResponse = textService.textChecker(text);

        System.out.println("---Результат проверки---");
        System.out.println("количество слов в тексте: " + textResponse.getTextLength());
        System.out.println("количество слов найденных в базе: " + textResponse.getFoundInDatabase());
        System.out.println("слова которые нет в базе: " + textResponse.getAbsentInDatabase());
        System.out.println("количество комплексных слов: " + textResponse.getComplexWords());
        System.out.println("количество терминов: " + textResponse.getNumberOfTerms());
        System.out.println("количество слогов: " + textResponse.getNumberOfSlogs());
        System.out.println("\n---показать детально слов найденных в базе---");
        textResponse.getMoreDetailedInfoWordsInDatabase().forEach((key, value) -> {
            System.out.println("------------");
            System.out.println(key.getName());
            System.out.println("Найдено в тексте: " + value);
            System.out.println("Определение: " + key.getDefinition());
            System.out.println("Тип слова: " + key.getType());
            System.out.println("Синонимы: " + key.getSynonyms());
        });
    }

}
