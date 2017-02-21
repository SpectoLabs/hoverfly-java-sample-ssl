package io.specto.hoverfly.java.sample.ssl.gateway;


import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static io.specto.hoverfly.junit.core.HoverflyConfig.configs;
import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class K8sGatewayTest {

    @Autowired
    private RestTemplate k8sRestTemplate;

    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule.inSimulationMode(
            configs()
                    .sslCertificatePath("ssl/ca.crt")
                    .sslKeyPath("ssl/ca.key"));

    @Test
    public void shouldBeToCallHttpsServerUsingSelfSignedCertificate() throws Exception {

        hoverflyRule.simulate(dsl(
                service("https://kubernetes")
                        .get("/api/version")
                        .willReturn(success("v1.5", MediaType.TEXT_PLAIN_VALUE))
        ));


        // When
        ResponseEntity<String> response = k8sRestTemplate.getForEntity("https://kubernetes/api/version", String.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("v1.5");

    }
}