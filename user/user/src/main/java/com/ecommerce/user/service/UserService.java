package com.ecommerce.user.service;


import com.ecommerce.user.Repository.UserRepository;
import  com.ecommerce.user.dto.AddressDTO;
import  com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.model.Address;
import com.ecommerce.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;


@Service
@RequiredArgsConstructor
public class UserService {
    //   private Long NextId=1l;

    private final UserRepository userRepository;
    //  private List<User> userList= new ArrayList<>();

    public List<UserResponse> fetchAllUser() {
       return userRepository.findAll().stream().
               map(this::maptoUserResponse).collect(Collectors.toList());

    }


    public Optional<UserResponse> fetchUser(Long id) {

/*
        return   userList.stream().filter(user->user.getId().equals(id)).findFirst();
*/
        return userRepository.findById(String.valueOf(id)).map(this::maptoUserResponse);

    }

    public void addUserList(UserRequest userRequest) {
        /*user.setId(NextId++);
        userList.add(user);*/
        User user = new User();
        UpdateUserFromRequest(user ,userRequest);
        userRepository.save(user);
    }

    private void UpdateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPhone(userRequest.getPhone());
        user.setEmail(userRequest.getEmail());

        if(userRequest.getAddress()!=null){
            Address address= new Address();
            address.setCity(userRequest.getAddress().getCity());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddress(address);

        }
    }

    public Boolean updateUser(String id, UserRequest updateUserRequest) {

/* return userList.stream().filter(user->user.getId().equals(id)).findFirst().
            map(existingUser->{
            existingUser.setFirstName(updateUser.getFirstName());
            existingUser.setLastName((updateUser.getLastName()));
            return true;
            }).orElse(false);*/
        return userRepository.findById(String.valueOf(id)).
                map(existingUser -> {
                    UpdateUserFromRequest(existingUser,updateUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);


    }

    private UserResponse maptoUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName((user.getLastName()));
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);

        }
        return response;


    }
}
