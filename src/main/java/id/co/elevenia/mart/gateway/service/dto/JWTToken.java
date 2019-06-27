package id.co.elevenia.mart.gateway.service.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JWTToken {
    @JsonProperty("id_token")
    private String token;
    
    @JsonProperty("customer_id")
    private String customerId;
    
    @JsonProperty("customer_code")
    private String customerCode;
    
    @JsonProperty("warehouse_id")
    private String warehouseId;
    
    @JsonProperty("warehouse_name")
    private String warehouseName;
    
    @JsonProperty("payment_method")
    private List<String> paymentMethod = new ArrayList<>();
    
    public JWTToken(String token) {
        this.token = token;
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public List<String> getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(List<String> paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "JWTToken {" +
            "token=" + token +
            ", customerId=" + customerId +
            ", customerCode=" + customerCode +
            ", warehouseId=" + warehouseId +
            ", warehouseName=" + warehouseName +
            ", paymentMethod=" + paymentMethod +
            "}";
    }
}  
