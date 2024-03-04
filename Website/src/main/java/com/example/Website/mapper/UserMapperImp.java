package com.example.Website.mapper;

import com.example.Website.DTO.UserDto;
import com.example.Website.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImp implements Mapper<User, UserDto> {

    private ModelMapper modelMapper;

    public UserMapperImp(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    @Override
    public UserDto mapTo(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User mapFrom(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
