package us.vicentini.springrestclientexamples.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import us.vicentini.springrestclientexamples.services.ApiService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final ApiService apiService;


    @GetMapping({"", "/", "/index"})
    public String index() {
        return "index";
    }


    @PostMapping("/users")
    public Mono<String> formPost(Model model, ServerWebExchange serverWebExchange) {
        return serverWebExchange.getFormData()
                .flatMap(map -> {
                    Integer limit = Integer.valueOf(map.getFirst("limit"));
                    return Mono.just(limit);
                })
                .map(apiService::getUsersReactive)
                .flatMap(userFlux -> {
                    model.addAttribute("users", userFlux);
                    return Mono.just("userlist");
                });
    }
}
