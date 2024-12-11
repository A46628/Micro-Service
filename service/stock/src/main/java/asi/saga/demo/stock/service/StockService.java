package asi.saga.demo.stock.service;

import asi.saga.demo.stock.entity.Stock;
import asi.saga.demo.stock.model.ItemList;
import asi.saga.demo.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


import asi.saga.demo.stock.entity.Stock;
import asi.saga.demo.stock.model.StockMessage;
import asi.saga.demo.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    public Stock addProduct(Stock stock) {
        return stockRepository.save(stock);
    }


    public boolean areProductsInStock(StockMessage stockMessages) {

        for (ItemList stockMessage : stockMessages.getItems()) {
            Stock stock = stockRepository.findById(stockMessage.getId());
            if (stock == null || stock.getQuantity() ==0) {
                return false;
            }
        }
        return true;
    }

        public boolean increaseStockAll (StockMessage stockMessages){
            try {
                for (ItemList stockMessage : stockMessages.getItems()) {
                    Stock stock = stockRepository.findById(stockMessage.getId());
                    stock.setQuantity(stock.getQuantity() + stockMessage.getQuantity());
                    stockRepository.save(stock);
                }
                return true;
            } catch (Exception e) {
                System.err.println("Erro ao verificar estoque: " + e.getMessage());
                return false;
            }
        }


        public boolean decreaseStock (StockMessage stockMessages){
            try {
                for (ItemList stockMessage : stockMessages.getItems()) {
                    Stock stock = stockRepository.findById(stockMessage.getId());
                    if (stock.getQuantity() - stockMessage.getQuantity() < 0)
                        return false;
                    stock.setQuantity(stock.getQuantity() - stockMessage.getQuantity());
                    stockRepository.save(stock);

                }
                return true;
            } catch (Exception e) {
                System.err.println("Erro ao verificar estoque: " + e.getMessage());
                return false;
            }
        }

    }

