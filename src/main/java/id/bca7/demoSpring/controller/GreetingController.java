package id.bca7.demoSpring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) { // RequestParam => /hello?name=name
        return String.format("Hello, %s!", name);
    }

    @GetMapping("/hello2/{name}")
    public String hello2(@PathVariable("name") String name) { // RequestParam => /hello2/name
        return String.format("Hello dari Path Variable, %s!", name);
    }

    @GetMapping("/hello3")
    public String hello3(@RequestBody String name) { // RequestParam => /hello2/name
        return String.format("Hello dari Request Body, %s!", name);
    }
}
