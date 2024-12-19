package asi.saga.demo.order.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "sagalog_pending")
public class Sagalog_pending {
    @Id
    String id;
    int state;

    @Column(name = "orderinfo")
    String orderInfo;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "SagaLog{" +
                "id='" + id + '\'' +
                ", state=" + state +
                ", orderInfo='" + orderInfo + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }



    public void setState(int state) {
        this.state = state;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public Sagalog_pending(String id, int state, String orderinfo) {
        this.id = id;
        this.state = state;
        this.orderInfo = orderinfo;
        this.createdAt = ZonedDateTime.now();
    }

    public  Sagalog_pending(){}
}