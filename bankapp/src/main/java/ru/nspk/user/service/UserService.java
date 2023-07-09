package ru.nspk.user.service;

import ru.nspk.user.dto.NewUserDto;

public interface UserService {

    void registerUser(NewUserDto newUserDto);
}
