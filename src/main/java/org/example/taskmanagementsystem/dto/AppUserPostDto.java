package org.example.taskmanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
public class AppUserPostDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("username")
    private String username;
    @NotNull
    @JsonProperty("password")
    private String password;
    @NotNull
    @JsonProperty("email")
    private String email;

}
