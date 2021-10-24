package soft.onetech_dictionary.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Word {

    private Long id;

    private String name;

    private String definition;

    //one to one
    private WordType type;

    //one to many
    private List<Word> synonymList;


    public Word(Long id, String name, String definition, WordType type) {
        this.id = id;
        this.name = name;
        this.definition = definition;
        this.type = type;
    }


}
