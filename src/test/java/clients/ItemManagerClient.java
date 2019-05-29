package clients;

import entities.Item;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class ItemManagerClient {

    private final String baseUrl;

    public ItemManagerClient() {
        this.baseUrl = "http://localhost:8082/item-manager";
    }

    public void addItem(Item newItem) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .path("/item")
                .build().toUri();
        HttpEntity<Item> httpRequestEntity = getHttpEntity(newItem);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(uri.toString(), HttpMethod.POST, httpRequestEntity, Item.class);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private <T> HttpEntity<T> getHttpEntity(T body) {
        HttpHeaders httpHeaders = getHttpHeaders();
        return new HttpEntity<>(body, httpHeaders);
    }
}
