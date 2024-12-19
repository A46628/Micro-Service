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
package asi.saga.demo.payment.controller;

import asi.saga.demo.payment.entity.Payment;
import asi.saga.demo.payment.model.PaymentMessage;
import asi.saga.demo.payment.model.Result;
import asi.saga.demo.payment.service.PaymentService;
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


import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RestControllerAdvice
public class PaymentServiceController {
    private Logger __logger = LoggerFactory.getLogger(getClass());
    private PaymentService paymentService;

    @Autowired
    public PaymentServiceController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Handle a payment request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful payment operation",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/payment-service/pay")
    public ResponseEntity<String> paymentRequest(@RequestBody PaymentMessage payment) throws Exception {
        // process the object
        __logger.info("POST:pay {}", payment);

        issuePayment(payment,1);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Result res = new Result("Success", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return new ResponseEntity<>(new ObjectMapper().writeValueAsString(res), headers, HttpStatus.OK);
    }

    private void issuePayment(PaymentMessage payment, int signal) {
        Payment p =paymentService.createPayment(payment.getCardid(), (signal==1)?payment.getAmount():payment.getAmount().negate(), payment.getIssuer(), payment.getTransaction());
    }

    @Operation(summary = "Handle a refund request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful refund operation",
                    content = @Content(schema = @Schema(implementation = Result.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/payment-service/refund")
    public ResponseEntity<String> refundRequest(@RequestBody PaymentMessage payment) throws Exception {
        // process the object
        __logger.info("POST:refund {}", payment);

        issuePayment(payment,-1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Result res = new Result("Success", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return new ResponseEntity<>(new ObjectMapper().writeValueAsString(res), headers, HttpStatus.OK);
    }

}

