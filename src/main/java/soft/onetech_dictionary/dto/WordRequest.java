package soft.onetech_dictionary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soft.onetech_dictionary.model.Word;
import soft.onetech_dictionary.model.WordType;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordRequest {

    private Long id;

    private String name;

    private String definition;

    private String type;

    private List<String> synonymList;
}
