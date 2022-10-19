package com.SweetHome.PaymentService.controller;

import com.SweetHome.PaymentService.dto.PaymentDto;
import com.SweetHome.PaymentService.entity.TransactionDetailsEntity;
import com.SweetHome.PaymentService.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    TransactionService transactionService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping(value = "/transaction" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity savePaymentDetails(@RequestParam("paymentMode") String paymentMode,
                                             @RequestParam("bookingId") int bookingId,
                                             @RequestParam("upiId") String upiId,
                                             @RequestParam("cardNumber") String cardNumber){
        int transactionId=transactionService.savePaymentDetails(bookingId, paymentMode, upiId, cardNumber);
        return new ResponseEntity(transactionId,HttpStatus.CREATED);
    }

    @GetMapping(value = "/transaction/{transactionId}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity transactionDetailsFromTransactionId(@PathVariable(name="transactionId") int transactionId){
        TransactionDetailsEntity transactionDetailsEntity=transactionService.transactionDetailsFromTransactionId(transactionId);
        PaymentDto paymentDto=modelMapper.map(transactionDetailsEntity,PaymentDto.class);
        return new ResponseEntity(paymentDto,HttpStatus.OK);
    }
}
