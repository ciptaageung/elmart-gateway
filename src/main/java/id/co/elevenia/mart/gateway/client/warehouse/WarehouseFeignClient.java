package id.co.elevenia.mart.gateway.client.warehouse;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import id.co.elevenia.mart.gateway.client.AuthorizedUserFeignClient;

@AuthorizedUserFeignClient(name = "${service.feign.elmartwrhouse}", fallback = WarehouseFeignClientFallback.class)
public interface WarehouseFeignClient {
	@GetMapping("/api/get-by-authId/{authId}")
    public ResponseEntity<Map<String, Object>> findWarehouseByAuthId(@PathVariable("authId") String authId);
}

