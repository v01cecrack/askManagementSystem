package org.example.taskmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.taskmanagementsystem.model.UserRoles;

import java.util.Set;

@Getter
@Setter
public class AppUserGetDto {
    private Long id;
    private String username;
    private String email;
    private Set<UserRoles> roles;
}
