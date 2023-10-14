package org.fpic974.webservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix="org.fpic974.webservice")
public class CustomProperties {

    private String patientApiUrl;
}
