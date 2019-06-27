package id.co.elevenia.mart.gateway.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import id.co.elevenia.mart.gateway.client.customer.CustomerFeignClient;
import id.co.elevenia.mart.gateway.client.warehouse.WarehouseFeignClient;
import id.co.elevenia.mart.gateway.service.FeignClientService;
import id.co.elevenia.mart.gateway.web.rest.errors.BadRequestAlertException;

@Service
public class FeignClientServiceImpl implements FeignClientService {
    private static final Logger log = LoggerFactory.getLogger(FeignClientServiceImpl.class);
    private static final List<HttpStatus> successStatus = Arrays.asList(HttpStatus.OK, HttpStatus.CREATED);
    
    @Autowired
    private WarehouseFeignClient warehouseFeignClient;
    
    @Autowired
    private CustomerFeignClient customerFeignClient;
    
    @Override
    public Map<String, Object> getWarehouseData(String login) {
        if(Objects.isNull(login)) {
            return Collections.emptyMap();
        }
        
        log.info("Execute WarehouseFeignClient.findWarehouseByAuthId()");
        long startTime = System.nanoTime();
        
        ResponseEntity<Map<String, Object>> response = warehouseFeignClient.findWarehouseByAuthId(login);
        log.info("Finished WarehouseFeignClient.findWarehouseByAuthId() in {} ms", (TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)));
        
        if(!successStatus.contains(response.getStatusCode()) || !response.hasBody()) {
            log.error("Failed to access warehouse");
            throw new BadRequestAlertException("Failed to access warehouse", "warehouse", "warehouse.failed");
        }
        
        return response.getBody();
    }
    
    @Override
    public Map<String, Object> getCustomerData(long id) {
    	log.info("Execute CustomerFeignClient.getCustomerBasicInfo()");
        long startTime = System.nanoTime();
        
        ResponseEntity<Map<String, Object>> response = customerFeignClient.getCustomerBasicInfo(id);
        log.info("Finished CustomerFeignClient.getCustomerBasicInfo() in {} ms", (TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime)));
        
        if(!successStatus.contains(response.getStatusCode()) || !response.hasBody()) {
            log.error("Failed to access customer");
            throw new BadRequestAlertException("Failed to access customer", "customer", "customer.failed");
        }
        
        return response.getBody();
    }
}
