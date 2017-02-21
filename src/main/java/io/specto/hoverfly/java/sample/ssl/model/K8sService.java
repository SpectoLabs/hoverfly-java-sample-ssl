package io.specto.hoverfly.java.sample.ssl.model;

public class K8sService {

    private Metadata metadata;
    private Spec spec;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Spec getSpec() {
        return spec;
    }

    public void setSpec(Spec spec) {
        this.spec = spec;
    }
}
