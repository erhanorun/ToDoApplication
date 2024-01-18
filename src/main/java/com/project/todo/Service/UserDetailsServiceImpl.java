package com.project.todo.Service;

import com.project.todo.Entities.ToDoUser;
import com.project.todo.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ToDoUser user =
                usersRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), new ArrayList<>());
    }
}
