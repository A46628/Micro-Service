package asi.saga.demo.order.service;

import asi.saga.demo.order.model.*;
import asi.saga.demo.order.repository.SagaLogRepository;
import asi.saga.demo.order.repository.SagaLogRepositoryPending;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private Logger __logger = LoggerFactory.getLogger(getClass());
    private static final String STOCK_SERVICE_URL = "http://localhost:8050/stock-service/check";
    private static final String STOCK_SERVICE_URL_INCREASE  = "http://localhost:8050/stock-service/inscrease";
    private static final String STOCK_SERVICE_URL_DECREASE = "http://localhost:8050/stock-service/decrease";// Corrigido URL de aumento de estoque
    private static final String PAYMENT_SERVICE_URL = "http://localhost:8040/payment-service/pay";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public List<SagaLog> getAll() {
            return _repo.findAll();
    }

    public enum State {
        INITIATED(0),
        STOCK_VERIFIED_PENDING(1),
        RESERVE_STOCK(2),
        PAYMENT_PENDING(5),
        REVERSE_ORDER(6),
        PAYMENT_COMPLETED(7),
        ORDER_COMPLETED(8),
        ERROR(9);

        private int value;

        State(int val) {
            this.value = val;
        }

        public int getValue() {
            return value;
        }
    }

    @Autowired
    private SagaLogRepository _repo;
    private SagaLogRepositoryPending sagaLogRepositoryPending;
    public MessageInfo doSaga(OrderMessage order) {
        MessageInfo messageInfo = new MessageInfo();
        SagaLog s = init(order);
        String  sagaId =  s.getId();
        while (s.getState() != State.ORDER_COMPLETED.getValue()
                && s.getState() != State.ERROR.getValue()) {
            s = _repo.findById(sagaId).orElseThrow(() -> new IllegalStateException("Saga não encontrada"));
            switch (State.values()[s.getState()]){
                case INITIATED:
                    nextState(s,State.STOCK_VERIFIED_PENDING.getValue());
                    break;
                case STOCK_VERIFIED_PENDING:
                    if (callServiceStock(order)) {
                        nextState(s,State.RESERVE_STOCK.getValue());
                    }else{
                        messageInfo.setError(true);
                        messageInfo.setMessage("Stock Error ");
                        nextState(s,State.ERROR.getValue());
                    }
                    break;
                case RESERVE_STOCK:
                    if(callServiceStockReserve(order)){
                        nextState(s,State.PAYMENT_PENDING.getValue());
                    }else {
                        messageInfo.setError(true);
                        messageInfo.setMessage("Stock Error ");
                        nextState(s,State.ERROR.getValue());
                    }
                case PAYMENT_PENDING:
                    if (callPaymentService(order))
                        nextState(s,State.PAYMENT_COMPLETED.getValue());
                    else {
                        messageInfo.setError(false);
                        messageInfo.setMessage("paymment Error ");

                        nextState(s, State.REVERSE_ORDER.getValue());
                    }
                    break;
                case PAYMENT_COMPLETED:
                    nextState(s, State.ORDER_COMPLETED.getValue());
                    break;
                case REVERSE_ORDER:
                    if (callServiceStockIncrease( order )){
                        nextState(s, State.ERROR.getValue());
                    }else{
                        nextState(s, State.ERROR.getValue());
                        Sagalog_pending sagalogPending = new Sagalog_pending();
                        sagalogPending.setCreatedAt(s.getCreatedAt());
                        sagalogPending.setId(s.getId());
                        sagalogPending.setOrderInfo(s.getOrderInfo());
                        sagalogPending.setState(s.getState());

                        sagaLogRepositoryPending.save(sagalogPending);
                    }
                    break;
            }
        }
        messageInfo.setSuccess(s.getState() == State.ORDER_COMPLETED.getValue() );
        return messageInfo;
    }


    @Transactional
    private SagaLog init(OrderMessage order) {
        UUID uuid = UUID.randomUUID();
        __logger.info(order.toString());
        SagaLog s = new SagaLog(uuid.toString(), State.INITIATED.getValue(), order.toString());
        _repo.saveAndFlush(s);
        return s;
    }

    public Optional<SagaLog> getSaga(String sagaId) {
        return _repo.findById(sagaId);
    }

    @Transactional
    private SagaLog nextState(SagaLog sagaLog, int state) {
        sagaLog.setState(state);
        return _repo.saveAndFlush(sagaLog);

    }

    private boolean callServiceStock(OrderMessage order) {
        __logger.info("Calling stock service for order: {}", order);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(STOCK_SERVICE_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(convertToJson(order.getItemLists())))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                __logger.info("Stock service response: {}", response.body());
                return true;
            } else {
                __logger.error("Stock service failed with status: {}", response.statusCode());
                return false;
            }
        } catch (Exception e) {
            __logger.error("Error calling stock service", e);
            return false;
        }
    }

    private boolean callServiceStockReserve(OrderMessage order){
        __logger.info("Reserve Stock: {}", order);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(STOCK_SERVICE_URL_DECREASE))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(convertToJson(order.getItemLists())))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                __logger.info("Stock service ok: {}", response.body());
                return true;
            } else {
                __logger.error("Error in reserve Stock: {}", response.statusCode());
                return false;
            }
        } catch (Exception e) {
            __logger.error("Error calling stock service", e);
            return false;
        }
    }

    private boolean callServiceStockIncrease(OrderMessage order) {
        __logger.info("Reserve inscrease Stock: {}", order);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(STOCK_SERVICE_URL_INCREASE))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(convertToJson(order.getItemLists())))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                __logger.info("Stock service increase ok: {}", response.body());
                return true;
            } else {
                __logger.error("Error in reserve increase Stock: {}", response.statusCode());
                return false;
            }
        } catch (Exception e) {
            __logger.error("Error calling stock service", e);
            return false;
        }
    }



    private boolean callPaymentService(OrderMessage order) {
        __logger.info("Calling payment service for order: {}", order);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String paymentMessage = convertToPaymentMessage(order);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(PAYMENT_SERVICE_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(paymentMessage)) // Enviando o PaymentMessage
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                __logger.info("Payment service response: {}", response.body());
                return true;
            } else {
                __logger.error("Payment service failed with status: {}", response.statusCode());
                return false;
            }
        } catch (Exception e) {
            __logger.error("Error calling payment service", e);
            return false;
        }
    }
    /*
        O que falta fazer, sepor a definição de saga,
        Não esquecer de apagar as mensagem novamente
     */

    private String convertToPaymentMessage(OrderMessage order) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            __logger.info("Converting OrderMessage to PaymentMessage");
            BigDecimal totalAmount = order.getItemLists().stream()
                    .map(item -> BigDecimal.valueOf(item.getUnitPrice()).multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            String transactionId = UUID.randomUUID().toString();
            PaymentMessage paymentMessage = new PaymentMessage(
                    order.getCardid(),
                    totalAmount,
                    order.getIssuer(),
                    transactionId
            );

            return objectMapper.writeValueAsString(paymentMessage);

        } catch (Exception e) {
            __logger.error("Failed to convert object to JSON", e);
            return "{}";
        }
    }

    private String convertToJson(List<ItemList> itemLists) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StockMessage stockMessage = new StockMessage();
            stockMessage.setItems(itemLists);
            return objectMapper.writeValueAsString(stockMessage);
        } catch (Exception e) {
            __logger.error("Failed to convert object to JSON", e);
            return "{}";
        }
    }
}
