package id.co.elevenia.mart.gateway.client.warehouse;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public class WarehouseFeignClientFallback implements WarehouseFeignClient {
    @Override
    public ResponseEntity<Map<String, Object>> findWarehouseByAuthId(String authId) {
        return ResponseEntity.badRequest().build();
    }
}
