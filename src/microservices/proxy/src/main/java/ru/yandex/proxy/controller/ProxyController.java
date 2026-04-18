package ru.yandex.proxy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.yandex.proxy.service.ProxyService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api/movies", "/api/users"})
public class ProxyController {
    private final ProxyService proxyService;

    @GetMapping
    public Mono<Object> proxy(ServerHttpRequest request, @RequestHeader Map<String, String> headers) {
        var path = request.getPath().value();
        if (request.getURI().getQuery() != null) {
            path += "?" + request.getURI().getQuery();
        }
        return proxyService.proxyGet(headers, path, Object.class, request.getPath().value().contains("users"));
    }

    @GetMapping("/{id}")
    public Mono<Object> proxyWithId(ServerHttpRequest request, @RequestHeader Map<String, String> headers, @PathVariable String id) {
        return proxyService.proxyGet(headers, request.getPath().value(), Object.class, request.getPath().value().contains("users"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Object> proxy(ServerHttpRequest serverHttpRequest, @RequestHeader Map<String, String> headers, @RequestBody Object request) {
        return proxyService.proxyPost(headers, serverHttpRequest.getPath().value(), request, Object.class, serverHttpRequest.getPath().value().contains("users"));
    }
}
