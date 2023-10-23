package org.fpic974.gatewayservice.exception;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.util.HtmlUtils;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Map;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {


    public GlobalErrorWebExceptionHandler(GlobalErrorAttributes g,
                                          ApplicationContext applicationContext,
                                          ServerCodecConfigurer serverCodecConfigurer) {

        super(g, new WebProperties.Resources(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse (ServerRequest request) {
        Map<String, Object> errorPropertiesMap = getErrorAttributes(request, ErrorAttributeOptions.defaults());

        HttpStatus status = HttpStatus.valueOf((Integer) errorPropertiesMap.get("status"));

        ServerResponse.BodyBuilder responseBody = ServerResponse.status(status)
                .contentType(MediaType.TEXT_HTML);

        return customRenderDefaultErrorView(responseBody, errorPropertiesMap);
    }

    protected Mono<ServerResponse> customRenderDefaultErrorView(ServerResponse.BodyBuilder responseBody,
                                                          Map<String, Object> error) {

        StringBuilder builder = new StringBuilder();
        Date timestamp = (Date) error.get("timestamp");
        Object message = error.get("message");
        Object trace = error.get("trace");
        Object requestId = error.get("requestId");
        builder.append("<html><body><h1>Unable To Access Requested Page</h1>")
                .append("<p>You are not authorized to access requested resource.</p>")
                .append("<p></p>")
                .append("<p>Please login <a href=\"http://localhost:8080/login\">here.</a></p>")
                .append("<div id='created'>")
                .append(timestamp)
                .append("</div>")
                .append("<div>[")
                .append(requestId)
                .append("] There was an unexpected error (type=")
                .append(htmlEscape(error.get("error")))
                .append(", status=")
                .append(htmlEscape(error.get("status")))
                .append(").</div>");
        if (message != null) {
            builder.append("<div>").append(htmlEscape(message)).append("</div>");
        }
        if (trace != null) {
            builder.append("<div style='white-space:pre-wrap;'>").append(htmlEscape(trace)).append("</div>");
        }
        builder.append("</body></html>");
        return responseBody.bodyValue(builder.toString());
    }
    private String htmlEscape(Object input) {
        return (input != null) ? HtmlUtils.htmlEscape(input.toString()) : null;
    }
}
