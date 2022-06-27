package com.thedebuggers.backend.domain.repository.user;

import com.thedebuggers.backend.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByNo(Long no);

    Optional<User> findByNickname(String nickname);

    @Modifying
    @Query("update User u set u.coin = u.coin + :price where u.no = :no")
    void updateCoinByNo(Long no, int price);
}
