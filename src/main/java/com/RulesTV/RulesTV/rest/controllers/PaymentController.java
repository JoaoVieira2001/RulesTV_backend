package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.Genre;
import com.RulesTV.RulesTV.entity.PermissionRole;
import com.RulesTV.RulesTV.rest.DTO.PaymentDTO;
import com.RulesTV.RulesTV.rest.DTO.PermissionRoleDTO;
import org.springframework.http.HttpStatus;
import com.RulesTV.RulesTV.entity.Payment;
import com.RulesTV.RulesTV.repositories.PaymentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/all")
    public List<PaymentDTO> getAllPayments(){
        List<Payment> payments = paymentRepository.findAll();

        return payments.stream().map(payment -> {
            PaymentDTO dto = new PaymentDTO(payment.getTransiction_id(),payment.getPayment_date(),payment.getPayment_method().name(),payment.getPayment_status().name(),payment.getPayment_reason(),payment.getTransaction_reference(),payment.getSubscription_plan().getSubscription_plan_type().name(),payment.getCurrency());
            dto.setTransaction_id(payment.getTransiction_id());
            dto.setTransaction_reference(payment.getTransaction_reference());
            dto.setPayment_date(payment.getPayment_date());
            dto.setPayment_method(payment.getPayment_method().name());
            dto.setPayment_status(payment.getPayment_status().name());
            dto.setPayment_reason(payment.getPayment_reason());
            dto.setCurrency(payment.getCurrency());
            dto.setSubscription_plan_type(payment.getSubscription_plan().getSubscription_plan_type().name());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentByTransactionId(@PathVariable int id){
        return paymentRepository.findById(id)
                .map(payment -> {
                    PaymentDTO dto = new PaymentDTO(payment.getTransiction_id(),payment.getPayment_date(),payment.getPayment_method().name(),payment.getPayment_status().name(),payment.getPayment_reason(),payment.getTransaction_reference(),payment.getSubscription_plan().getSubscription_plan_type().name(),payment.getCurrency());
                    dto.setTransaction_id(payment.getTransiction_id());
                    dto.setTransaction_reference(payment.getTransaction_reference());
                    dto.setPayment_date(payment.getPayment_date());
                    dto.setPayment_method(payment.getPayment_method().name());
                    dto.setPayment_status(payment.getPayment_status().name());
                    dto.setPayment_reason(payment.getPayment_reason());
                    dto.setCurrency(payment.getCurrency());
                    dto.setSubscription_plan_type(payment.getSubscription_plan().getSubscription_plan_type().name());
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @PostMapping("/post")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment){
        Payment savedPayment = paymentRepository.save(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPayment);
    }

    @PutMapping("/put/{transaction_id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable int transaction_id, @RequestBody Payment updatedPayment) {
        return paymentRepository.findById(transaction_id).map(existingPayment -> {
            if (updatedPayment.getPayment_method() != null) {
                existingPayment.setPayment_method(updatedPayment.getPayment_method());
            }
            if (updatedPayment.getPayment_status() != null) {
                existingPayment.setPayment_status(updatedPayment.getPayment_status());
            }
            if (updatedPayment.getPayment_reason() != null) {
                existingPayment.setPayment_reason(updatedPayment.getPayment_reason());
            }
            if (updatedPayment.getTransaction_reference() != null) {
                existingPayment.setTransaction_reference(updatedPayment.getTransaction_reference());
            }
            if (updatedPayment.getCurrency() != null) {
                existingPayment.setCurrency(updatedPayment.getCurrency());
            }
            if (updatedPayment.getPayment_date() != null) {
                existingPayment.setPayment_date(updatedPayment.getPayment_date());
            }
            if (updatedPayment.getSubscription_plan() != null) {
                existingPayment.setSubscription_plan(updatedPayment.getSubscription_plan());
            }
            Payment savedPayment = paymentRepository.save(existingPayment);
            return ResponseEntity.ok(savedPayment);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{transiction_id}")
    public ResponseEntity<String> deletePayment(@PathVariable int transiction_id){
        if(paymentRepository.existsById(transiction_id)){
            paymentRepository.deleteById(transiction_id);
            return ResponseEntity.ok("The Payment Transaction with ID " + transiction_id + " has been successfully deleted.");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment Transaction with ID " + transiction_id + " not found.");
        }
    }

}
