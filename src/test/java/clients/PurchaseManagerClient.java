package clients;

import entities.PurchaseOrder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class PurchaseManagerClient {

    private final String baseUrl;

    public PurchaseManagerClient() {
        this.baseUrl = "http://localhost:8083/purchase-manager";
    }

    public void addPurchase(PurchaseOrder newPurchaseOrder) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .path("/purchase")
                .build().toUri();
        HttpEntity<PurchaseOrder> httpRequestEntity = getHttpEntity(newPurchaseOrder);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(uri.toString(), HttpMethod.POST, httpRequestEntity, PurchaseOrder.class);
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
