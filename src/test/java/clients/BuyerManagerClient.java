package clients;

import entities.Buyer;

import java.net.URI;

import entities.Purchase;
import entities.PurchaseOrder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class BuyerManagerClient {

    private final String baseUrl;

    public BuyerManagerClient() {
        this.baseUrl = "http://localhost:8081/buyer-manager";
    }

    public void addBuyer(Buyer newBuyer) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .path("/buyer")
                .build().toUri();
        HttpEntity<Buyer> httpRequestEntity = getHttpEntity(newBuyer);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(uri.toString(), HttpMethod.POST, httpRequestEntity, Buyer.class);
    }

    public Purchase[] getOrdersFromBuyer(Long id) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .path("/buyer/" + id)
                .build().toUri();
        RestTemplate restTemplate = new RestTemplate();
        Buyer buyer = restTemplate.getForObject(uri.toString(), Buyer.class);
        return buyer.getPurchasedItems();
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
