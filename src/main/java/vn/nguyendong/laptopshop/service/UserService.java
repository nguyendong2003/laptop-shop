package vn.nguyendong.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.nguyendong.laptopshop.domain.Role;
import vn.nguyendong.laptopshop.domain.User;
import vn.nguyendong.laptopshop.domain.dto.RegisterDTO;
import vn.nguyendong.laptopshop.repository.RoleRepository;
import vn.nguyendong.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public User handleSaveUser(User user) {
        User saveUser = this.userRepository.save(user);
        return saveUser;
    }

    public void handleDeleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    public boolean checkEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }
}
