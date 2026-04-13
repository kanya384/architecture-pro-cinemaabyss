package ru.yandex.proxy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.yandex.proxy.service.ProxyService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/movies", "/api/users"})
public class ProxyController {
    private final ProxyService proxyService;
    private final String PATH = "/api/movies";

    @GetMapping
    public Mono<Object> proxy(@RequestHeader Map<String, String> headers) {
        return proxyService.proxyGet(headers, PATH, Object.class);
    }

    @GetMapping("/{id}")
    public Mono<Object> proxyWithId(@RequestHeader Map<String, String> headers, @PathVariable String id) {
        return proxyService.proxyGet(headers, String.format("%s/%s", PATH, id), Object.class);
    }

    @PostMapping
    public Mono<Object> proxy(@RequestHeader Map<String, String> headers, @RequestBody Object request) {
        return proxyService.proxyPost(headers, PATH, request, Object.class);
    }
}
