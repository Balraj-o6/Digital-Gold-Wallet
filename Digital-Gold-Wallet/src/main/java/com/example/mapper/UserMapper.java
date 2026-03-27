package com.example.mapper;


import com.example.dto.UserDTO;
import com.example.entity.User;

public class UserMapper {
    public static UserDTO convertEntityToDTO(User user){
        if(user==null) return null;
        UserDTO uDTO=new UserDTO();
        uDTO.setUserId(user.getUserId());
        uDTO.setName(user.getName());
        uDTO.setEmail(user.getEmail());
        uDTO.setBalance(user.getBalance());
        uDTO.setAddress(AddressMapper.convertEntityToObject(user.getAddress()));
        return uDTO;
    }
    public static User convertObjectToEntity(UserDTO uDTO){
        if(uDTO==null) return null;
        User user=new User();
        user.setUserId(uDTO.getUserId());
        user.setName(uDTO.getName());
        user.setEmail(uDTO.getEmail());
        user.setBalance(uDTO.getBalance());
        user.setAddress(AddressMapper.convertObjectToEntity(uDTO.getAddress()));
        return user;
    }
}
