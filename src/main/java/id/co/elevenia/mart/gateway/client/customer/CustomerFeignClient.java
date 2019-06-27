
package id.co.elevenia.mart.gateway.client.customer;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import id.co.elevenia.mart.gateway.client.AuthorizedUserFeignClient;

@AuthorizedUserFeignClient(name = "${service.feign.elmartcust}", fallback = CustomerFeignClientFallback.class)
public interface CustomerFeignClient {
	@GetMapping("/api/customers/{id}/basic-info")
    public ResponseEntity<Map<String, Object>> getCustomerBasicInfo(@PathVariable("id") Long id);
}
