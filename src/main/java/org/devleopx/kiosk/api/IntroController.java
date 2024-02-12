package org.devleopx.kiosk.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class IntroController {

    private final MessageSource messageSource;

    @GetMapping("/intro")
    public String helloKiosk(Locale locale) {
        return messageSource.getMessage("introduction", null, locale);
    }
}
