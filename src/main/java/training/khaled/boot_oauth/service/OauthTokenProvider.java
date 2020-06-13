
package training.khaled.boot_oauth.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import training.khaled.boot_oauth.model.OAuth2Token;

import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * @author  Khaled
 *
 * This service class should be used when you request token internally
 * instead of hitting the api through postman
 * (when your code needs to access some secured resource)
 */

@Service
public class OauthTokenProvider {

   private static final String AUTH_SERVER_URI = "http://localhost:8080/oauth/token";

   private static final String QPM_PASSWORD_GRANT = "?grant_type=password&username=test@gmail.com&password=whatever";



   public OAuth2Token getToken() {
      return sendTokenRequest();
   }

   /*
    * Prepare HTTP Headers.
    */
   public static HttpHeaders getHeaders() {
      HttpHeaders headers = new HttpHeaders();
      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
      return headers;
   }

   /*
    * Add HTTP Authorization header, using Basic-Authentication to send client-credentials.
    */
   private static HttpHeaders getHeadersWithClientCredentials() {
      String plainClientCredentials = "my-web-client:secret";
      String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));

      HttpHeaders headers = getHeaders();
      headers.add("Authorization", "Basic " + base64ClientCredentials);
      return headers;
   }

   /*
    * Send a POST request [on /oauth/token] to get an access-token, which will then be send with each request.
    */
   @SuppressWarnings({"unchecked"})
   private static OAuth2Token sendTokenRequest() {
      RestTemplate restTemplate = new RestTemplate();

      HttpEntity<String> request = new HttpEntity<>(getHeadersWithClientCredentials());
      ResponseEntity<Object> response = restTemplate.exchange(AUTH_SERVER_URI + QPM_PASSWORD_GRANT, HttpMethod.POST, request, Object.class);
      LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
      OAuth2Token tokenInfo = null;

      if (map != null) {
         tokenInfo = new OAuth2Token();
         tokenInfo.setAccessToken((String) map.get("access_token"));
         tokenInfo.setTokenType((String) map.get("token_type"));
         tokenInfo.setRefreshToken((String) map.get("refresh_token"));
         tokenInfo.setExpiresIn((int) map.get("expires_in"));
         tokenInfo.setScope((String) map.get("scope"));
      } else {
         System.out.println("can't give access token due to bad credentials");
      }
      return tokenInfo;
   }


}
