package guru.springframework.springrestclientexamples.controllers;

import guru.springframework.springrestclientexamples.services.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@Controller
public class UserController {

    private ApiService apiService;

    public UserController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping({"", "/", "/index"})
    public String index() {
        return "index";
    }

    @PostMapping("/users")
    public String formPost(Model model, ServerWebExchange serverWebExchange) {
        MultiValueMap<String, String> map = serverWebExchange.getFormData().block();

        String limitParam = map.get("limit").get(0);
        log.debug("User request with limit " + limitParam);
        Integer limit;
        if(limitParam == null || limitParam.equals("") || limitParam.equals("0")) {
            log.debug("Setting default limit 10");
            limit = Integer.valueOf(10);
        } else {
            limit = Integer.valueOf(limitParam);
        }
        model.addAttribute("users", apiService.getUsers(limit));
        return "userlist";
    }
}
