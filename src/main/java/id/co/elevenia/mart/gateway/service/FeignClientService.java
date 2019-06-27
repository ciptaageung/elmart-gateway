package id.co.elevenia.mart.gateway.service;

import java.util.Map;

public interface FeignClientService {
    public Map<String, Object> getWarehouseData(String login);
    public Map<String, Object> getCustomerData(long id);
}
