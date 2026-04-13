package ru.yandex.proxy.clients;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

public class RestProxyClient {
    private final WebClient webClient;
    private final String upstreamUrl;

    public RestProxyClient(WebClient webClient, String upstreamUrl) {
        this.webClient = webClient;
        this.upstreamUrl = upstreamUrl;
    }

    public <T> Mono<T> processGet(Map<String, String> headers, String endpoint, Class<T> responseType) {
        return webClient
                .get()
                .uri(upstreamUrl + endpoint)
                .headers(httpHeaders -> httpHeaders.setAll(headers))
                .retrieve()
                .bodyToMono(responseType);
    }

    public <T, R> Mono<T> processPost(Map<String, String> headers, String endpoint, R body, Class<T> responseType) {
        return webClient
                .post()
                .uri(upstreamUrl + endpoint)
                .headers(httpHeaders -> httpHeaders.setAll(headers))
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType);
    }
}
