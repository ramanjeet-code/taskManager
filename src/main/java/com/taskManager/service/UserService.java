package com.taskManager.service;

import com.taskManager.authentication.JwtProvider;
import com.taskManager.modules.User;
import com.taskManager.repository.UserRepository;
import com.taskManager.utility.JwtRequest;
import com.taskManager.utility.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository uRepo;
    @Autowired
    private JwtProvider jwtProvider;

    /**
     * signup
     * @param user
     * @return
     */
    public User addUser(User user) {
        User u=uRepo.findByUsername(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return uRepo.save(user);
    }

    /**
     * login jwt
     * @param jwtRequest
     * @return
     */
    public JwtResponse login(JwtRequest jwtRequest)  {
        System.out.println(jwtRequest.getUsername());
        User user=uRepo.findByUsername(jwtRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.genrateTokan(authentication);
        JwtResponse response=new JwtResponse();
        response.setJwtToken(token);
        response.setEmail(user.getEmail());
        return response;
    }
}
