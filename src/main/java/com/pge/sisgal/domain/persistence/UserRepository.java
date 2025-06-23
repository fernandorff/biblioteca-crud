package com.pge.sisgal.domain.persistence;

import com.pge.sisgal.domain.entities.User;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByRegistration(String registration);

    Boolean existsByRegistration(String registration);

}
