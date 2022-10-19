package com.SweetHome.PaymentService.service;

import com.SweetHome.PaymentService.entity.TransactionDetailsEntity;

public interface TransactionService {
    public int savePaymentDetails(int bookingId, String paymentMode, String upiId, String cardNumber);

    public TransactionDetailsEntity transactionDetailsFromTransactionId(int transactionId);




}
