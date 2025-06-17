package com.Perfulandia.ApiGateway.redireccion.cupones;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/proxy/cupones")
@RequiredArgsConstructor
public class CuponesProxyController {

    private final RestTemplate restTemplate;

    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<?> proxyCupones(HttpServletRequest request,
                                          @RequestBody(required = false) String body,
                                          @RequestHeader HttpHeaders headers) {

        String originalPath = request.getRequestURI().replace("/api/proxy/cupones", "");
        String targetUrl = "http://localhost:8006/api/cupones" + originalPath;
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        // Clonar headers válidos
        HttpHeaders cleanHeaders = new HttpHeaders();
        headers.forEach((key, value) -> {
            if (!key.equalsIgnoreCase(HttpHeaders.CONTENT_LENGTH)) {
                cleanHeaders.put(key, value);
            }
        });
        cleanHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body, cleanHeaders);

        try {
            ResponseEntity<String> response = restTemplate.exchange(targetUrl, method, entity, String.class);
            return ResponseEntity.status(response.getStatusCode()).contentType(MediaType.APPLICATION_JSON).body(response.getBody());

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            return ResponseEntity.status(ex.getStatusCode()).contentType(MediaType.APPLICATION_JSON).body(ex.getResponseBodyAsString());

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\": \"Error inesperado en el API Gateway\", \"detalle\": \"" + ex.getMessage() + "\"}");
        }
    }
}