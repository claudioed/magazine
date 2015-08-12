
package infra.integration;

/**
 * @author Claudio E. de Oliveira (claudioed.oliveira@gmail.com).
 */
public enum Services {

    CATALOG(9004,"localhost");

    private final int port;

    private final String host;

    private Services(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public int port() {
        return port;
    }

    public String host() {
        return host;
    }

}
