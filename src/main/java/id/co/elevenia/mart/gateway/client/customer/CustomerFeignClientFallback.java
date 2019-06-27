package id.co.elevenia.mart.gateway.client.customer;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public class CustomerFeignClientFallback implements CustomerFeignClient {
	@Override
	public ResponseEntity<Map<String, Object>> getCustomerBasicInfo(Long id) {
		return ResponseEntity.badRequest().build();
	}
}
