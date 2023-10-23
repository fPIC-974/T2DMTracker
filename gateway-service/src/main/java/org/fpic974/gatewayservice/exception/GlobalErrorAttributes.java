package org.fpic974.gatewayservice.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {

        Map<String, Object> errorResponse = super.getErrorAttributes(request, options);

        //extract the status and put custom error message on the map
        HttpStatus status = HttpStatus.valueOf((Integer) errorResponse.get("status"));

        if (status == HttpStatus.BAD_REQUEST) {
            errorResponse.put("message", "Your custom message");
        } else {
            errorResponse.put("message", "Something went wrong!");
        }

        return errorResponse;
    }
}
