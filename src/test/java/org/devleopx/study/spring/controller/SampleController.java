package org.devleopx.study.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class SampleController {

    // get or form (get, post) 호출시 처리 된다
    @RequestMapping("/requestParam")
    @ResponseBody
    public String requestParam(
            @RequestParam(required = false) String username,
            @RequestParam int age
    ) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    public record TestDto(String username, int age) {
    }

    @RequestMapping("/modelAttribute")
    @ResponseBody
    public String modelAttribute(@ModelAttribute TestDto dto) {
        log.info("username={}, age={}", dto.username(), dto.age());
        return "ok";
    }


    // json 형태로 호출 시 사용 되는 @RequestBody
    // form 형태가 아닌 post, put, patch 등에서 호출시 요청 데이터를 저장하는 공간
    @RequestMapping("/requestBody")
    @ResponseBody
    public String requestBody(@RequestBody TestDto dto) {
        log.info("username={}, age={}", dto.username(), dto.age());
        return "ok";
    }

    @RequestMapping("/httpEntity")
    @ResponseBody
    public String httpEntity(RequestEntity<TestDto> requestEntity) {
        TestDto dto = requestEntity.getBody();
        log.info("username={}, age={}", dto.username(), dto.age());
        return "ok";
    }

}
