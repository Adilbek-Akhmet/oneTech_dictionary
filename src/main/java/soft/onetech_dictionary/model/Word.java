package soft.onetech_dictionary.model;

import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Word {

    private String name;

    private String definition;

    //one to one
    private WordType type;

    //one to many
    private Set<String> synonyms;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return name.equals(word.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
