package soft.onetech_dictionary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WordType {

    private Long id;

    private String name;

    public WordType(String name) {
        this.name = name;
    }
}
