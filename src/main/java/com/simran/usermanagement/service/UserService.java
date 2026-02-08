package com.simran.usermanagement.service;

import com.simran.usermanagement.entity.User;
import com.simran.usermanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        return userRepository.save(user);
    }

    public Optional<User> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(u -> u.getPassword().equals(password));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> updateProfile(Long id, User updates) {
        return userRepository.findById(id)
                .map(user -> {
                    if (updates.getName() != null) user.setName(updates.getName());
                    if (updates.getPhone() != null) user.setPhone(updates.getPhone());
                    if (updates.getPassword() != null && !updates.getPassword().isEmpty())
                        user.setPassword(updates.getPassword());
                    return userRepository.save(user);
                });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
