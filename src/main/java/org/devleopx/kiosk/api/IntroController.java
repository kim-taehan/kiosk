package org.devleopx.kiosk.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
@Slf4j
@RestController
@RequiredArgsConstructor
public class IntroController {

    private final MessageSource messageSource;

    @GetMapping("/intro")
    public String helloKiosk(Locale locale) {

        log.info("test call");

        return messageSource.getMessage("introduction", null, locale);
    }
}
