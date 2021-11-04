package soft.onetech_dictionary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soft.onetech_dictionary.kafka.KafkaProducer;
import soft.onetech_dictionary.model.Dictionary;
import soft.onetech_dictionary.model.Word;
import soft.onetech_dictionary.model.WordType;
import soft.onetech_dictionary.service.DictionaryService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/dictionaries")
public class DictionaryController {

    private final KafkaProducer kafkaProducer;
    private final DictionaryService dictionaryService;

    @GetMapping
    public List<Dictionary> findAll() {
        kafkaProducer.sendMessage(dictionaryService.findAll().toString());
        return dictionaryService.findAll();
    }

    @GetMapping("/{dictionaryName}")
    public Dictionary findByName(@PathVariable String dictionaryName) {
        Dictionary dictionary = dictionaryService.findByName(dictionaryName);
        kafkaProducer.sendMessage(dictionary.toString());
        return dictionary;
    }

    @GetMapping("/{dictionaryName}/{wordTypeName}")
    public List<Word> findWordsByWordTypeName(@PathVariable String dictionaryName, @PathVariable String wordTypeName) {
        return dictionaryService.findWordsByWordTypeName(dictionaryName, wordTypeName);
    }

    @GetMapping("/word/types")
    public List<WordType> findAllWordTypes() {
        return dictionaryService.findAllWordTypes();
    }

    @PostMapping("/add")
    public ResponseEntity<Void> save(@RequestBody Dictionary dictionary) {
        dictionaryService.save(dictionary);
        kafkaProducer.sendMessage("Saved: New Dictionary with name " + dictionary.getName() + " saved");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{dictionaryName}")
    public void deleteByName(@PathVariable String dictionaryName) {
        dictionaryService.deleteByName(dictionaryName);
        kafkaProducer.sendMessage("Deleted: Dictionary with name " + dictionaryName + " deleted");
    }


}
