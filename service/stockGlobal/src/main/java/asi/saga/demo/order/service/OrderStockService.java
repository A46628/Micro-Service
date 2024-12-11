package asi.saga.demo.order.service;

import asi.saga.demo.order.entity.Product;
import asi.saga.demo.order.entity.Store;
import asi.saga.demo.order.model.*;
import asi.saga.demo.order.repository.ProductRepository;
import asi.saga.demo.order.repository.StoreRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public class OrderStockService {



    private final HttpClient httpClient = HttpClient.newHttpClient();


    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    ProductRepository productRepository;

    public List<ProductMessage> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductMessage(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getStockQuantity(),
                        product.getId_loja()
                ))
                .toList();
    }

    public List<StoreMessage> getAllStores() {
        List<Store> lojas = storeRepository.findAll();
        return lojas.stream()
                .map(loja -> new StoreMessage(
                        loja.getId(),
                        loja.getName(),
                        loja.getUrl(),
                        loja.getPort()
                ))
                .toList();
    }

    public boolean areProductsInStock(List<ProductMessage> productMessage){
        for(ProductMessage itemList  : productMessage){
            Optional<Product> product = productRepository.findById(itemList.getId());
            if(product.isEmpty() || product.get().getStockQuantity() < itemList.getStockQuantity())
                return false;

        }
        return true;
    }


    public boolean inscreaseStockAll(List<ProductMessage> productMessages){
        for ( ProductMessage itemList: productMessages){
            Optional<Product> product = productRepository.findById(itemList.getId());
            if (product.isPresent()){
                Optional<Store> store = storeRepository.findById(product.get().getId_loja());
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(new URI("http://"+ store.get().getUrl()+":"+store.get().getPort()+"/store-service/inscrease"))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(convertToJson(itemList)))
                            .build();

                    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    if (response.statusCode() != 200) {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean decreaseStock(List<ProductMessage> productMessages){
        for ( ProductMessage itemList: productMessages){
            Optional<Product> product = productRepository.findById(itemList.getId());
            if (product.isPresent()){
                if(product.get().getStockQuantity() == 0)
                    return false;
                Optional<Store> store = storeRepository.findById(product.get().getId_loja());
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(new URI("http://"+ store.get().getUrl()+":"+store.get().getPort()+"/store-service/decrease"))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(convertToJson(itemList)))
                            .build();

                    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    if (response.statusCode() != 200) {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }

    private String convertToJson(ProductMessage itemLists) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(itemLists);
        } catch (Exception e) {
            return "{}";
        }
    }
 }


