package org.devleopx.kiosk.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devleopx.kiosk.global.exception.data.ErrorResult;
import org.hibernate.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

import static lombok.AccessLevel.PACKAGE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor(access = PACKAGE)
//@RestControllerAdvice("org.devleopx.kiosk")
public class ExceptionControllerAdvice {

    private final MessageSource messageSource;


    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ErrorResult illegalExHandle(RuntimeException e, Locale locale) {
        String typeName = e.getClass().getTypeName();
        final String message = findErrorMessage(typeName, e.getMessage(), locale);
        return new ErrorResult(message, typeName);
    }
//    @ResponseStatus(BAD_REQUEST)
//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    public ErrorResult errorhandle(MethodArgumentNotValidException e) {
//        String typeName = e.getClass().getTypeName();
//        final String message = findErrorMessage(typeName, e.getMessage(), null);
//        return new ErrorResult(message, typeName);
//    }
//
//


    private String findErrorMessage(String code, String message, Locale locale) {
        final String text = StringUtils.hasText(message) ? message : code;
        try {
            return messageSource.getMessage(text, null, locale);
        } catch (NoSuchMessageException e) {
            return text;
        }
    }
}
