package org.devleopx.kiosk.api.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ValidationController {

    @GetMapping("/validation")
    public String validationCheck(@Validated @ModelAttribute ValidationRequest request, BindingResult bindingResult) {

        log.info("request={}", request);
        return "ok";
    }


}
