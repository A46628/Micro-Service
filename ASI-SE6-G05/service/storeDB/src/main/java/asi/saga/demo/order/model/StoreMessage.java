package asi.saga.demo.order.model;

import java.util.UUID;

public class StoreMessage {
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private UUID id;
    private String name;
    private String url;
    private int port;

    public StoreMessage(UUID id, String name, String url, int port) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.port = port;
    }

    public StoreMessage( String name, String url, int port) {
        this.name = name;
        this.url = url;
        this.port = port;
    }
    public StoreMessage() {}

}
