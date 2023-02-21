package endpoints;

public enum Endpoints {
    CALCULATE("/calculate");

    private final String endpoint;

    Endpoints(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return endpoint;
    }
}
