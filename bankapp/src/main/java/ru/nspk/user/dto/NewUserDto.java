package ru.nspk.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.nspk.user.model.UserRole;

import java.util.List;

@AllArgsConstructor
@Getter
public class NewUserDto {
    private String login;
    private String password;
    private List<UserRole> role;
}
