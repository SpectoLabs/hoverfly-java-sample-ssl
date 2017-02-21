package io.specto.hoverfly.java.sample.ssl.gateway;

import io.specto.hoverfly.java.sample.ssl.configuration.K8sConfig;
import io.specto.hoverfly.java.sample.ssl.model.K8sService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class K8sGateway {


    private final RestTemplate restTemplate;
    private final K8sConfig k8sConfig;

    @Autowired
    public K8sGateway(RestTemplate k8sRestTemplate, K8sConfig k8sConfig) {
        this.restTemplate = k8sRestTemplate;
        this.k8sConfig = k8sConfig;
    }

    public K8sService getService(final String k8sServiceName) {
        final String uri = UriComponentsBuilder.fromHttpUrl(k8sConfig.getBaseUrl())
                .pathSegment("api", "v1", "namespaces", k8sConfig.getNamespace(), "services", k8sServiceName)
                .toUriString();

        return restTemplate.getForEntity(uri, K8sService.class).getBody();
    }
}
