package com.ecommerce.user.controller;

import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.service.UserService;
import com.ecommerce.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

  @Autowired
   private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> FethchAllUser(){
        return new ResponseEntity<>(userService.fetchAllUser(), HttpStatus.OK);

    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){

       /* if(user==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.fetchUser(id));*/
        return userService.fetchUser(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());

    }


    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest ){
        userService.addUserList(userRequest);

        return  ResponseEntity.ok("Created sir");

    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UserRequest updateUserRequest){


        if(userService.updateUser(id, updateUserRequest)){
            return ResponseEntity.ok("Updates Successfully");
        }

        return ResponseEntity.notFound().build();
    }
}
