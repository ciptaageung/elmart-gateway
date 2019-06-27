package id.co.elevenia.mart.gateway.web.rest;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import id.co.elevenia.mart.gateway.security.oauth2.OAuth2AuthenticationService;
import id.co.elevenia.mart.gateway.service.FeignClientService;
import id.co.elevenia.mart.gateway.service.dto.JWTToken;

/**
 * Authentication endpoint for web client.
 * Used to authenticate a user using OAuth2 access tokens or log him out.
 *
 * @author markus.oellinger
 */
@RestController
@RequestMapping("/auth")
public class AuthResource {
    private static final Logger log = LoggerFactory.getLogger(AuthResource.class);
    private OAuth2AuthenticationService authenticationService;
    
    @Autowired
    private FeignClientService feignClientService;
    
    public AuthResource(OAuth2AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Authenticates a user setting the access and refresh token cookies.
     *
     * @param request  the {@link HttpServletRequest} holding - among others - the headers passed from the client.
     * @param response the {@link HttpServletResponse} getting the cookies set upon successful authentication.
     * @param params   the login params (username, password, rememberMe).
     * @return the access token of the authenticated user. Will return an error code if it fails to authenticate the user.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType
        .APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OAuth2AccessToken> authenticate(HttpServletRequest request, HttpServletResponse response, @RequestBody
        Map<String, String> params) {
        return authenticationService.authenticate(request, response, params);
    }

    /**
     * Logout current user deleting his cookies.
     *
     * @param request  the {@link HttpServletRequest} holding - among others - the headers passed from the client.
     * @param response the {@link HttpServletResponse} getting the cookies set upon successful authentication.
     * @return an empty response entity.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("logging out user {}", SecurityContextHolder.getContext().getAuthentication().getName());
        authenticationService.logout(request, response);
        return ResponseEntity.noContent().build();
    }
    
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, 
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JWTToken> authenticateUser(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, String> params) throws URISyntaxException {
	    OAuth2AccessToken oldToken = authenticationService.authenticate(request, response, params).getBody();
        JWTToken newToken = new JWTToken(oldToken.getValue());
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!Objects.isNull(authentication) && authentication instanceof AbstractAuthenticationToken) {
	        request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE, newToken.getToken());
	        OAuth2AuthenticationDetails authenticationDetail = new OAuth2AuthenticationDetails(request);
	        AbstractAuthenticationToken abstractAuthentication = (AbstractAuthenticationToken) authentication;
	        abstractAuthentication.setDetails(authenticationDetail);
        }
        
		/*
		 * Map<String, Object> warehouseData =
		 * feignClientService.getWarehouseData(params.get("username"));
		 * newToken.setWarehouseId(String.valueOf(warehouseData.get("warehouseCode")));
		 * newToken.setWarehouseName(String.valueOf(warehouseData.get("warehouseName")))
		 * ; newToken.setCustomerId(String.valueOf(warehouseData.get("customerId")));
		 * 
		 * if(!StringUtils.isEmpty(newToken.getCustomerId())) { Map<String, Object>
		 * customerData =
		 * feignClientService.getCustomerData(Long.valueOf(newToken.getCustomerId()));
		 * newToken.setCustomerCode(String.valueOf(customerData.get("customerCode"))); }
		 */
        //TODO feign to get payment_method
        return ResponseEntity.ok(newToken);
	}
}
