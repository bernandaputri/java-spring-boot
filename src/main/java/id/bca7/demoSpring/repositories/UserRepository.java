package id.bca7.demoSpring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.bca7.demoSpring.models.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByIsDeleted(Boolean isDeleted);
    Optional<User> findByIdAndIsDeleted(Integer id, Boolean isDeleted);
}
