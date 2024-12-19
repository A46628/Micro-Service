package asi.saga.demo.order.repository;

import asi.saga.demo.order.model.SagaLog;
import asi.saga.demo.order.model.Sagalog_pending;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SagaLogRepositoryPending extends
        JpaRepository<Sagalog_pending, String> {
}
