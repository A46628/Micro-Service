/*
MIT License

Copyright (c) 2023, Nuno Datia, ISEL

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package asi.saga.demo.order.controller;

import asi.saga.demo.common.model.Result;
import asi.saga.demo.order.model.ProductMessage;
import asi.saga.demo.order.model.StockMessage;
import asi.saga.demo.order.model.StoreMessage;
import asi.saga.demo.order.service.OrderStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This a simplified version of what an order service can be.
 * It does not care about autenticated users, something that is key in a real solution.
 * It must implement the SAGA pattern, in its orchestrated variation.
 * */
@RestController
@RestControllerAdvice
public class OrderServiceController {



    private Logger __logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OrderStockService _service;

    @Operation(summary = "Handle s check the product available in the stock  global store")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful  operation",
                    content = @Content(schema = @Schema(implementation = asi.saga.demo.common.model.Result.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })


    @GetMapping("/get-products")
    public ResponseEntity<List<ProductMessage>> getAllProducts() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<ProductMessage> products = _service.getAllProducts();

        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(products);
    }


    @GetMapping("/get-stores")
    public ResponseEntity<List<StoreMessage>> getAllStores() {
        __logger.info("Getting all stores");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<StoreMessage> stores = _service.getAllStores();

        if (stores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(stores);
    }

    @Operation(summary = "Verify the availability of a product in the stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verification success",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Request")
    })
    @PostMapping("/stock-service/check")
    public ResponseEntity<String> checkStock(@RequestBody List<ProductMessage> stockMessage) throws Exception {

        boolean isStockAvailable = _service.areProductsInStock(stockMessage);

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
    public ResponseEntity<String> descreaseStock(@RequestBody List<ProductMessage> productMessages) throws Exception {
        __logger.info("Reservando produto no stock{}", productMessages);

        boolean ok = _service.decreaseStock(productMessages);

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
    public ResponseEntity<String> IncreaseStock(@RequestBody List<ProductMessage> productMessages) throws Exception {
        __logger.info("Recebido: Aumentando de estoque {}", productMessages);

        boolean isStockAvailable = _service.inscreaseStockAll(productMessages);

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

