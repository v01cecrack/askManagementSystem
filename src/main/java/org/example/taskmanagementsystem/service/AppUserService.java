package org.example.taskmanagementsystem.service;

import org.example.taskmanagementsystem.dto.AppUserGetDto;
import org.example.taskmanagementsystem.dto.AppUserPostDto;
import org.example.taskmanagementsystem.dto.AuthRequest;
import org.example.taskmanagementsystem.mapper.MapStructMapper;
import org.example.taskmanagementsystem.model.AppUser;
import org.example.taskmanagementsystem.model.UserRoles;
import org.example.taskmanagementsystem.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppUserService {
    private final MapStructMapper mapper;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AppUserService(MapStructMapper mapper,
                          UserDetailsService userDetailsService,
                          JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          UserService userService) {
        this.mapper = mapper;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }


    public String login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        Set<String> roles = userService.getUserByEmail(userDetails.getUsername())
                .getRoles()
                .stream().map(Enum::name)
                .collect(Collectors.toSet());
        return jwtUtil.generateToken(authRequest.getEmail(), roles);
    }

    public AppUserGetDto register(AppUserPostDto userDto) {
        AppUser user = mapper.userPostDtoToAppUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<UserRoles> roles = new HashSet<>();
        user.setRoles(roles);
        roles.add(UserRoles.GUEST);
        return userService.save(user);
    }

    public AppUserGetDto registerAdmin(AppUserPostDto userDto) {
        AppUser user = mapper.userPostDtoToAppUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<UserRoles> roles = new HashSet<>();
        user.setRoles(roles);
        roles.add(UserRoles.USER);
        roles.add(UserRoles.ADMIN);
        return userService.save(user);
    }

}
