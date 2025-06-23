package com.pge.sisgal.infraestructure.persistence.repositories;

import com.pge.sisgal.infraestructure.persistence.entities.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByRegistration(String registration);

    Boolean existsByRegistration(String registration);
}
