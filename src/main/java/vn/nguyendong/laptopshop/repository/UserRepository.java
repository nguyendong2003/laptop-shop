package vn.nguyendong.laptopshop.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.nguyendong.laptopshop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);

    List<User> findByEmail(String email);
}
