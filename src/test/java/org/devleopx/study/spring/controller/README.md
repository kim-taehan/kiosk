## HTTP 요청 파라메터 어노테이션 정리 

### 1. queryString or form(post, get) 인경우 처리 프로세스 
> username=admin&age=30 의 형태로 오는 경우이다.

#### @RequestParam (생략가능)
```java
@RequestMapping("/requestParam")
@ResponseBody
public String requestParam(
        @RequestParam(required = false) String username,
        @RequestParam int age
) {
    log.info("username={}, age={}", username, age);
    return "ok";
}
```

#### @ModelAttribute (생략가능)
```java
@RequestMapping("/modelAttribute")
@ResponseBody
public String modelAttribute(@ModelAttribute TestDto dto) {
    log.info("username={}, age={}", dto.username(), dto.age());
    return "ok";
}
```

### 2. form 이 아닌 post, put, patch, delete 등의 메서드 request body
> String 도 처리 되지만 여기서는 json으로 가정  
> {"username":"admin", "age":30}

#### @RequestBody (생략불가)
```java
@RequestMapping("/requestBody")
@ResponseBody
public String requestBody(@RequestBody TestDto dto) {
    log.info("username={}, age={}", dto.username(), dto.age());
    return "ok";
}
```

#### RequestEntity 를 사용해도 가능
```java
@RequestMapping("/httpEntity")
@ResponseBody
public String httpEntity(RequestEntity<TestDto> requestEntity) {
    TestDto dto = requestEntity.getBody();
    log.info("username={}, age={}", dto.username(), dto.age());
    return "ok";
}
```