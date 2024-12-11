package asi.saga.demo.order.service;

import asi.saga.demo.order.entity.Product;
import asi.saga.demo.order.entity.Store;
import asi.saga.demo.order.model.*;
import asi.saga.demo.order.repository.ProductRepository;
import asi.saga.demo.order.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);


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

}
