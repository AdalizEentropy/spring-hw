package ru.nspk.user.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.nspk.user.model.UserRole;

@AllArgsConstructor
@Getter
public class NewUserDto {
    private String login;
    private String password;
    private List<UserRole> role;
}
