package training.khaled.boot_oauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Khaled
 */
@RestController
public class DemoController {

    @GetMapping(value = "/")
    public String index() {
        return "Hello world";
    }

    @GetMapping(value = "/demo")
    public String helloForAdmins() {
        return "Hello Admin ;)";
    }

}