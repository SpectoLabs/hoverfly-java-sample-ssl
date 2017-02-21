package io.specto.hoverfly.java.sample.ssl.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "k8s")
public class K8sConfig {

    private static final String KUBERNETES_NAMESPACE_PATH = "/var/run/secrets/kubernetes.io/serviceaccount/namespace";
    private static final String KUBERNETES_SERVICE_ACCOUNT_TOKEN_PATH = "/var/run/secrets/kubernetes.io/serviceaccount/token";
    private static final String KUBERNETES_SERVICE_ACCOUNT_CA_CRT_PATH = "/var/run/secrets/kubernetes.io/serviceaccount/ca.crt";

    private String namespace;

    private String baseUrl;

    private String oauthToken;

    private String namespacePath = KUBERNETES_NAMESPACE_PATH;

    private String tokenPath = KUBERNETES_SERVICE_ACCOUNT_TOKEN_PATH;

    private String caCertPath = KUBERNETES_SERVICE_ACCOUNT_CA_CRT_PATH;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getOauthToken() {

        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    public String getCaCertPath() {
        return caCertPath;
    }

    public void setCaCertPath(String caCertPath) {
        this.caCertPath = caCertPath;
    }
}
