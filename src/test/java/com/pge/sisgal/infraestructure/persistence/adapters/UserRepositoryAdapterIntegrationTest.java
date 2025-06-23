package com.pge.sisgal.infraestructure.persistence.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.infraestructure.persistence.entities.UserEntity;
import com.pge.sisgal.infraestructure.persistence.mappers.UserEntityMapper;
import com.pge.sisgal.infraestructure.persistence.repositories.JpaUserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(UserEntityMapper.class)
class UserRepositoryAdapterIntegrationTest {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private TestEntityManager entityManager;

    private UserRepositoryAdapter userRepositoryAdapter;

    @BeforeEach
    void setUp() {
        userRepositoryAdapter = new UserRepositoryAdapter(jpaUserRepository, userEntityMapper);
        entityManager.clear();
    }

    @Test
    void save_ShouldPersistUserAndReturnIt() {
        User user = TestUtils.createUser();
        user.setId(null);

        User savedUser = userRepositoryAdapter.save(user);

        entityManager.flush();
        assertTrue(savedUser.getId() > 0);
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getRegistration(), savedUser.getRegistration());
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        User user = TestUtils.createUser();
        user.setId(null);
        UserEntity userEntity = userEntityMapper.toEntity(user);
        UserEntity savedUserEntity = jpaUserRepository.save(userEntity);
        entityManager.flush();
        entityManager.clear();

        Optional<User> result = userRepositoryAdapter.findById(savedUserEntity.getId());

        assertTrue(result.isPresent());
        assertEquals(savedUserEntity.getId(), result.get().getId());
        assertEquals(user.getName(), result.get().getName());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenUserDoesNotExist() {
        Optional<User> result = userRepositoryAdapter.findById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void findByRegistration_ShouldReturnUser_WhenUserExists() {
        User user = TestUtils.createUser();
        user.setId(null);
        UserEntity userEntity = userEntityMapper.toEntity(user);
        UserEntity savedUserEntity = jpaUserRepository.save(userEntity);
        entityManager.flush();
        entityManager.clear();

        Optional<User> result = userRepositoryAdapter.findByRegistration(user.getRegistration());

        assertTrue(result.isPresent());
        assertEquals(savedUserEntity.getId(), result.get().getId());
        assertEquals(user.getRegistration(), result.get().getRegistration());
    }

    @Test
    void findByRegistration_ShouldReturnEmpty_WhenUserDoesNotExist() {
        Optional<User> result = userRepositoryAdapter.findByRegistration("99999999");

        assertFalse(result.isPresent());
    }

    @Test
    void existsByRegistration_ShouldReturnTrue_WhenUserExists() {
        User user = TestUtils.createUser();
        user.setId(null);
        UserEntity userEntity = userEntityMapper.toEntity(user);
        jpaUserRepository.save(userEntity);
        entityManager.flush();
        entityManager.clear();

        Boolean result = userRepositoryAdapter.existsByRegistration(user.getRegistration());

        assertTrue(result);
    }

    @Test
    void existsByRegistration_ShouldReturnFalse_WhenUserDoesNotExist() {
        Boolean result = userRepositoryAdapter.existsByRegistration("99999999");

        assertFalse(result);
    }
}
