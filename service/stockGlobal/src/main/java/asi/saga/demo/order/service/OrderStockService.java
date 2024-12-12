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
            /*
                change the way the consulting the database,
                em vez de consultar a base de dados, por id consultar os produtos,
                por nome e não por id, assim fica mais facil a configurações dos dados,
                depois de consultar pelo nome acho que nesse caso retorna um conjunto de lojas e é necessario tentar consultar em apenas um se form bem sucessido
                tem que mudar o id ou adicionar o id na resposta de mo
             */
            List<Product> product = productRepository.findByName(itemList.getName());
            if(product.isEmpty())
                return false;
        }
        return true;
    }


    public boolean inscreaseStockAll(ReservationResult productMessages){
        for ( ProductMessage itemList: productMessages.getReservedProducts()){
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

    public ReservationResult decreaseStock(List<ProductMessage> productMessages) {
        boolean resultReserve = true;
        ReservationResult reservationResult = new ReservationResult();

        for (ProductMessage itemList : productMessages) {
            List<Product> products = productRepository.findByName(itemList.getName());
            System.out.println("#######################################################");
            System.out.println("size " + products.size());

            boolean reservado = false;
            for (Product product : products) {
                System.out.println(product.toString());
                if (product.getStockQuantity() > 0) {
                    Optional<Store> store = storeRepository.findById(product.getId_loja());
                    if (store.isPresent()) {
                        System.out.println("vendo a loja de saida do stock " + store.toString());
                        System.out.println("headQuarters other now #########################################################");
                        try {
                            ProductMessage productMessageDe = new ProductMessage(product.getId(),product.getName(), product.getDescription(),itemList.getStockQuantity(),product.getId_loja());
                            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(new URI("http://" + store.get().getUrl() + ":" + store.get().getPort() + "/store-service/decrease"))
                                    .header("Content-Type", "application/json")
                                    .POST(HttpRequest.BodyPublishers.ofString(convertToJson(productMessageDe)))
                                    .build();

                            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                            if (response.statusCode() == 200) {
                                System.out.println("Resutado do check vendo informações");
                                itemList.setId(product.getId());
                                reservationResult.addReservedProduct(productMessageDe);
                                reservado = true;
                                break;
                            }
                        } catch (Exception e) {
                            System.err.println("Erro ao tentar reservar na loja: " + store.get().getUrl() + " - " + e.getMessage());
                        }
                    }
                }
            }
            if (!reservado) {
                resultReserve = false;
                break;
            }
        }
        reservationResult.setSuccess(resultReserve);
        return reservationResult;
    }




    public List<ProductMessage> getProdcutByName(String name){
        List<Product> products = productRepository.findByName(name);
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

    private String convertToJson(ProductMessage itemLists) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(itemLists);
        } catch (Exception e) {
            return "{}";
        }
    }
 }


