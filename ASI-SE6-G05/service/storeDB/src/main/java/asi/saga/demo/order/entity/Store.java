package asi.saga.demo.order.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

import javax.persistence.*;
@Entity
@Table(name = "lojas")

public class Store {
    @Id
    @GeneratedValue
    @Column(name = "id_loja", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nome_loja", nullable = false, length = 100)
    private String name;

    @Column(name = "url_loja", nullable = false, length = 100)
    private String url;

    public void setPort(int port) {
        this.port = port;
    }

    @Column(name = "port", nullable = false)
    private int port;


    public Store(){}

    public Store(UUID id, String name, String url, int port) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.port = port;
    }

    public Store(String name, String url, int port) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.port = port;
    }

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





    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", port=" + port +
                '}';
    }
}
