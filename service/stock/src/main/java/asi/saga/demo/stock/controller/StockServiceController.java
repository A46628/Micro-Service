package asi.saga.demo.stock.controller;

import asi.saga.demo.stock.entity.Stock;
import asi.saga.demo.stock.model.Result;
import asi.saga.demo.stock.model.StockMessage;
import asi.saga.demo.stock.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

//import javax.xml.transform.Result;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@RestController
@RestControllerAdvice
public class StockServiceController {

    private Logger __logger = LoggerFactory.getLogger(getClass());
    private StockService stockService;

    @Autowired
    public StockServiceController(StockService stockService) {
        this.stockService = stockService;
    }



    @Operation(summary = "Add a product to the stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(schema = @Schema(implementation = Stock.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Request")
    })
    @PostMapping("/stock-service/add")
    public ResponseEntity<Stock> addProductToStock(@RequestBody Stock stock) {
        __logger.info("Recebido: Adicionando novo produto ao estoque {}", stock);


        Stock createdStock = stockService.addProduct(stock);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
    }

    @Operation(summary = "Verify the availability of a product in the stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verification success",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Request")
    })
    @PostMapping("/stock-service/check")
    public ResponseEntity<String> checkStock(@RequestBody StockMessage stockMessage) throws Exception {
        __logger.info("Recebido: Verificação de estoque {}", stockMessage);

        boolean isStockAvailable = stockService.areProductsInStock(stockMessage);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (isStockAvailable) {
            Result res = new Result("Success", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(res), headers, HttpStatus.OK);
        } else {
            Result res = new Result("Error", "Some products not found or insufficient stock");
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(res), headers, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Verify the availability of a product in the stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verification success",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Request")
    })
    @PostMapping("/stock-service/decrease")
    public ResponseEntity<String> descreaseStock(@RequestBody StockMessage stockMessage) throws Exception {
        __logger.info("Reservando produto no stock{}", stockMessage);

        boolean ok = stockService.decreaseStock(stockMessage);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (ok) {
            Result res = new Result("Success", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(res), headers, HttpStatus.OK);
        } else {
            Result res = new Result("Error", "Some products not found or insufficient stock");
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(res), headers, HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(summary = "Increase the availability of a product in the stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verification success",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Request")
    })
    @PostMapping("/stock-service/inscrease")
    public ResponseEntity<String> IncreaseStock(@RequestBody StockMessage stockMessage) throws Exception {
        __logger.info("Recebido: Aumentando de estoque {}", stockMessage);

        boolean isStockAvailable = stockService.increaseStockAll(stockMessage);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (isStockAvailable) {
            Result res = new Result("Success", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(res), headers, HttpStatus.OK);
        } else {
            Result res = new Result("Error", "Some products not found or insufficient stock");
            return new ResponseEntity<>(new ObjectMapper().writeValueAsString(res), headers, HttpStatus.BAD_REQUEST);
        }
    }
}