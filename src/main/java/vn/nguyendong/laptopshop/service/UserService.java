package vn.nguyendong.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.nguyendong.laptopshop.domain.User;
import vn.nguyendong.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User handleSaveUser(User user) {
        User saveUser = this.userRepository.save(user);
        return saveUser;
    }
}
