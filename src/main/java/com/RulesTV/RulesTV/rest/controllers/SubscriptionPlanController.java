package com.RulesTV.RulesTV.rest.controllers;
import com.RulesTV.RulesTV.entity.Episode;
import com.RulesTV.RulesTV.entity.SubscriptionPlan;
import com.RulesTV.RulesTV.repositories.SubscriptionPlanRepository;
import com.RulesTV.RulesTV.rest.DTO.EpisodeDTO;
import com.RulesTV.RulesTV.rest.DTO.SubscriptionPlanDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/subscription_plan")
public class SubscriptionPlanController {

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionPlanController(SubscriptionPlanRepository subscriptionPlanRepository){
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    @GetMapping("/all")
    public List<SubscriptionPlanDTO> getAllSubscriptionPlans(){
        List<SubscriptionPlan> subs_plans = subscriptionPlanRepository.findAll();
        return subs_plans.stream().map(sub_plan -> {
            SubscriptionPlanDTO dto = new SubscriptionPlanDTO(sub_plan.getPrice(),sub_plan.getMax_profiles(),sub_plan.getResolution().name(),sub_plan.getAmount(),sub_plan.getSubscription_plan_type().name());
            dto.setPrice(sub_plan.getPrice());
            dto.setMax_profiles(sub_plan.getMax_profiles());
            dto.setResolution(sub_plan.getResolution().name());
            dto.setAmount(sub_plan.getAmount());
            dto.setSubscription_plan_type(sub_plan.getSubscription_plan_type().name());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionPlanDTO> getSubscriptionPlanById(@PathVariable int id){
        return subscriptionPlanRepository.findById(id)
                .map(sub_plan -> {
                    SubscriptionPlanDTO dto = new SubscriptionPlanDTO(sub_plan.getPrice(),sub_plan.getMax_profiles(),sub_plan.getResolution().name(),sub_plan.getAmount(),sub_plan.getSubscription_plan_type().name());
                    dto.setPrice(sub_plan.getPrice());
                    dto.setMax_profiles(sub_plan.getMax_profiles());
                    dto.setResolution(sub_plan.getResolution().name());
                    dto.setAmount(sub_plan.getAmount());
                    dto.setSubscription_plan_type(sub_plan.getSubscription_plan_type().name());
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/post")
    public ResponseEntity<SubscriptionPlan> createSubscriptionPlanById(@RequestBody SubscriptionPlan subscriptionPlan){
        SubscriptionPlan savedSubscriptionPlan = subscriptionPlanRepository.save(subscriptionPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubscriptionPlan);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<SubscriptionPlan> updateSubscriptionPlan(@PathVariable int id, @RequestBody SubscriptionPlan updatedSubscriptionPlan) {
        return subscriptionPlanRepository.findById(id)

                .map(existingSubscriptionPlan -> {
                    if (updatedSubscriptionPlan.getPrice() != null) {
                        existingSubscriptionPlan.setPrice(updatedSubscriptionPlan.getPrice());
                    }
                    if (updatedSubscriptionPlan.getMax_profiles() != null) {
                        existingSubscriptionPlan.setMax_profiles(updatedSubscriptionPlan.getMax_profiles());
                    }
                    if (updatedSubscriptionPlan.getResolution() != null) {
                        existingSubscriptionPlan.setResolution(updatedSubscriptionPlan.getResolution());
                    }
                    if (updatedSubscriptionPlan.getSubscription_plan_type() != null) {
                        existingSubscriptionPlan.setSubscription_plan_type(updatedSubscriptionPlan.getSubscription_plan_type());
                    }
                    if (updatedSubscriptionPlan.getAmount() != null) {
                        existingSubscriptionPlan.setAmount(updatedSubscriptionPlan.getAmount());
                    }
                    if (updatedSubscriptionPlan.getPaymentsList() != null) {
                        existingSubscriptionPlan.setPaymentsList(updatedSubscriptionPlan.getPaymentsList());
                    }
                    if (updatedSubscriptionPlan.getAccountsList() != null) {
                        existingSubscriptionPlan.setAccountsList(updatedSubscriptionPlan.getAccountsList());
                    }
                    subscriptionPlanRepository.save(existingSubscriptionPlan);
                    return ResponseEntity.ok(existingSubscriptionPlan);
                })
                .orElseGet(() -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSubscriptionPlan(@PathVariable int id) {
        if (subscriptionPlanRepository.existsById(id)) {
            subscriptionPlanRepository.deleteById(id);
            return ResponseEntity.ok("The SubscriptionPlan with ID " + id + " has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SubscriptionPlan with ID " + id + " not found.");
        }
    }
}
