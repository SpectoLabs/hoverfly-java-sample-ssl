package io.specto;

import io.specto.hoverfly.java.sample.ssl.configuration.K8sConfig;
import io.specto.hoverfly.java.sample.ssl.util.SslUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RestTemplate k8sRestTemplate(K8sConfig k8sConfig) {

		RestTemplate restTemplate = new RestTemplate();

		Path caCertFile = Paths.get(k8sConfig.getCaCertPath());

		if (Files.exists(caCertFile) && Files.isRegularFile(caCertFile)) {
			SSLContext sslContext = SslUtils.createSslContextFromCertFile(caCertFile);
			CloseableHttpClient httpsClient = HttpClients.custom().useSystemProperties().setSSLContext(sslContext).build();
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpsClient));

		} else {
			LOGGER.warn("Certificate file {} is not found, Kubernetes api client will not be configured to use HTTPS protocol.", caCertFile.toString());
			restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory());
		}

		return restTemplate;
	}
}
