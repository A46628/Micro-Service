package asi.saga.demo.order.service;

import asi.saga.demo.order.entity.Product;
import asi.saga.demo.order.entity.Store;
import asi.saga.demo.order.model.*;
import asi.saga.demo.order.repository.LocalStockRepository;
import asi.saga.demo.order.repository.ProductRepository;
import asi.saga.demo.order.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StoreAService {

    private final Logger logger = LoggerFactory.getLogger(StoreAService.class);

    @Autowired
    private LocalStockRepository localStockRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    ProductRepository productRepository;

    public MessageInfo createStore(StoreMessage storeMessage) {
        Store store = new Store(storeMessage.getId(),storeMessage.getName(),storeMessage.getUrl(), storeMessage.getPort() );

        storeRepository.save(store);

        logger.info("Loja criada com sucesso: {}", store.getName());
        return new MessageInfo(true, "Loja criada com sucesso!");
    }


    public MessageInfo createProduct(List<ProductMessage> productMessages) {
        for (ProductMessage productMessage : productMessages){
            Product product = new Product(productMessage.getId(), productMessage.getName(),
                    productMessage.getDescription(),
                    productMessage.getPrice(),
                    productMessage.getStockQuantity(),
                    productMessage.getStoreId());

            productRepository.save(product);

        }
        logger.info("Produto criado com sucesso: {}","ok");
        return new MessageInfo(true, "Produto criado com sucesso!");
    }

    public boolean updateProduct(List<ProductMessage> productMessages, Boolean upDr) {

        for(ProductMessage productMessage : productMessages){
            Optional<Product> productOpt = productRepository.findById(productMessage.getId());
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                product.setName(productMessage.getName());
                product.setDescription(productMessage.getDescription());
                if(upDr){
                    product.setStockQuantity(productMessage.getStockQuantity() + product.getStockQuantity());
                }else{
                    product.setStockQuantity(product.getStockQuantity()-productMessage.getStockQuantity());

                }
                product.setId_loja(productMessage.getStoreId());

                localStockRepository.save(product);
                logger.info("Poduct update with success: {}", product.getName());
                return true;
            } else {
                logger.warn("Product ID {} not found from update.", productMessage.getId());
                return false;
            }
        };

        return false;
    }

    public boolean deleteProduct(UUID id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            logger.info("ProductID {} delete with success.", id);
            return true;
        } else {
            logger.warn("Product with ID {} not found to exclusion.", id);
            return false;
        }
    }

    public List<ProductMessage> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductMessage(product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStockQuantity(),
                        product.getId_loja()
                ))
                .toList();
    }

    public List<StoreMessage> getAllStores() {
        List<Store> lojas = storeRepository.findAll();
        return lojas.stream()
                .map(loja -> new StoreMessage(loja.getId(),
                        loja.getName(),
                        loja.getUrl(),
                        loja.getPort()
                ))
                .toList();
    }

    public boolean increaseStockAll (ProductMessage productMessage){
        List<ProductMessage> productMessages = List.of(productMessage);
        return updateProduct(productMessages, true);

    }


    public boolean decreaseStock (ProductMessage productMessage){

        List<ProductMessage> productMessages = List.of(productMessage);
        return updateProduct(productMessages, false);
    }

    public boolean deleteStore(UUID id){
        if (storeRepository.existsById(id)){
            storeRepository.deleteById(id);
            logger.info("Store by id {} delete with success",id);
            return true;
        }else{
            logger.info("Error to delete Store by id {}",id);
            return false;
        }
    }
}
