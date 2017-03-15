package io.specto.hoverfly.java.sample.ssl.gateway;


import io.specto.hoverfly.java.sample.ssl.model.K8sService;
import io.specto.hoverfly.java.sample.ssl.model.Metadata;
import io.specto.hoverfly.java.sample.ssl.model.Spec;
import io.specto.hoverfly.junit.dsl.HttpBodyConverter;
import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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
    private K8sGateway k8sGateway;

    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule.inSimulationMode(
            configs()
                    .sslCertificatePath("ssl/ca.crt")
                    .sslKeyPath("ssl/ca.key"));

    @Test
    public void shouldBeAbleToCallHttpsServerUsingSelfSignedCertificate() throws Exception {

        // Given
        K8sService expected = new K8sService(new Metadata("hoverfly", "some-uuid", "default"), new Spec());
        hoverflyRule.simulate(dsl(
                service("https://kubernetes")
                        .get("/api/v1/namespaces/default/services/hoverfly")
                        .willReturn(success(HttpBodyConverter.json(expected)))
        ));

        // When
        K8sService actual = k8sGateway.getService("hoverfly");

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}