package com.lendea.java_common_mistakes.repostiory;

import com.lendea.java_common_mistakes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lendea
 * @date 2022/8/12 14:46
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByName(String name);
}
