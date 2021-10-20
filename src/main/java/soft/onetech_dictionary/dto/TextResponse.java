package soft.onetech_dictionary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import soft.onetech_dictionary.model.Word;

import java.util.Map;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TextResponse {

    private int textLength;
    private long foundInDatabase;
    private Set<String> absentInDatabase;
    private long complexWords;
    private long numberOfSlogs;
    private long numberOfTerms;
    private Map<Word, Long> moreDetailedInfoWordsInDatabase;
}
