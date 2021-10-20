package soft.onetech_dictionary.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soft.onetech_dictionary.dto.TextRequest;
import soft.onetech_dictionary.dto.TextResponse;
import soft.onetech_dictionary.service.TextService;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("text")
public class TextController {

    private final TextService textService;

    @PostMapping("check")
    public TextResponse textResult(@RequestBody TextRequest text) {
        return textService.textChecker(text.getText());
    }

}
