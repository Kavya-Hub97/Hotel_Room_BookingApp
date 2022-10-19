package com.SweetHome.BookingService.controller;

import com.SweetHome.BookingService.dto.BookingDto;
import com.SweetHome.BookingService.entity.BookingInfoEntity;
import com.SweetHome.BookingService.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("hotel")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @Autowired
    ModelMapper _modelMapper;

    @PostMapping(value = "/booking" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveBooking(@RequestBody BookingDto bookingDto){
       BookingInfoEntity entity = _modelMapper.map(bookingDto, BookingInfoEntity.class);
       bookingService.saveBooking(entity);
       BookingDto bookedDto = _modelMapper.map(entity,BookingDto.class);
       return new ResponseEntity(bookedDto, HttpStatus.CREATED);
    }

    @PostMapping(value = "/booking/transaction" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity savePaymentFromBooking(@RequestParam("paymentMode") String paymentMode,
                                      @RequestParam("bookingId") int bookingId,
                                      @RequestParam("upiId") String upiId,
                                      @RequestParam("cardNumber") String cardNumber){
        if((upiId==null || upiId.isEmpty()) && (cardNumber==null || cardNumber.isEmpty())){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("message: Invalid mode of payment \n statusCode:"+HttpStatus.BAD_REQUEST.value());
        }

        BookingInfoEntity entity = bookingService.getBooking(bookingId);

        if(entity==null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("message: Invalid booking Id \n statusCode:" + HttpStatus.BAD_REQUEST.value());
        }

        entity = bookingService.savePaymentDetails(paymentMode,bookingId,upiId,cardNumber,entity);
        BookingDto bookedDto = _modelMapper.map(entity,BookingDto.class);
        return new ResponseEntity(bookedDto, HttpStatus.CREATED);
    }


}
