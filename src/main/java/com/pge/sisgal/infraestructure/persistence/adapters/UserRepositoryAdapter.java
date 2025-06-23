package com.pge.sisgal.infraestructure.persistence.adapters;

import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.domain.persistence.UserRepository;
import com.pge.sisgal.infraestructure.persistence.entities.UserEntity;
import com.pge.sisgal.infraestructure.persistence.mappers.UserEntityMapper;
import com.pge.sisgal.infraestructure.persistence.repositories.JpaUserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public User save(User user) {
        UserEntity entity = userEntityMapper.toEntity(user);
        UserEntity savedEntity = jpaUserRepository.save(entity);
        return userEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id)
            .map(userEntityMapper::toDomain);
    }

    @Override
    public Optional<User> findByRegistration(String registration) {
        return jpaUserRepository.findByRegistration(registration)
            .map(userEntityMapper::toDomain);
    }

    @Override
    public Boolean existsByRegistration(String registration) {
        return jpaUserRepository.existsByRegistration(registration);
    }
}
