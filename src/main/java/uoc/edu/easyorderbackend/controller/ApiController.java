package uoc.edu.easyorderbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uoc.edu.easyorderbackend.model.Api;

@Deprecated
@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping
    public Api getApi() {
        return new Api("id", "title");
    }
}
