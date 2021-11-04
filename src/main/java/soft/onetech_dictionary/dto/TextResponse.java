package soft.onetech_dictionary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soft.onetech_dictionary.model.Word;

import java.util.Map;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextResponse {
    private long numberOfWords;
    private Map<String, Long> numberOfEachWord;
    private long foundInDatabase;
    private Set<String> absentInDatabase;
    private Map<Word, Long> moreDetailedInfoWordsInDatabase;
}
