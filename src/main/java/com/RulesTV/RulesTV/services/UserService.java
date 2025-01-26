package com.RulesTV.RulesTV.services;

import com.RulesTV.RulesTV.entity.UserAuth;
import com.RulesTV.RulesTV.repositories.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public UserAuth createUser(UserAuth userAuth){
        if(userRepository.findByEmail(userAuth.getEmail()).isPresent()){
            throw new IllegalArgumentException("The email" + userAuth.getEmail() + "is already in use");
        }

        return userRepository.save(userAuth);
    }

    public UserAuth updateUser(UserAuth userAuth){
        if(userRepository.findByEmail(userAuth.getEmail()).isPresent()){
            throw new IllegalArgumentException("The email" + userAuth.getEmail() + "is already in use");
        }

        return userRepository.save(userAuth);
    }


}
