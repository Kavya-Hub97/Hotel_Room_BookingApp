package com.SweetHome.PaymentService.dao;

import com.SweetHome.PaymentService.entity.TransactionDetailsEntity;

public interface TransactionDao {


    public TransactionDetailsEntity transactionDetailsFromTransactionId(int transactionId);

    public int savePaymentDetails(int bookingId, String paymentMode, String upiId, String cardNumber);

}
