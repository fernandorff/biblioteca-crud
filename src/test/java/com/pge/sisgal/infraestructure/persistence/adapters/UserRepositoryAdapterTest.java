package com.pge.sisgal.infraestructure.persistence.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pge.sisgal.TestUtils;
import com.pge.sisgal.domain.entities.User;
import com.pge.sisgal.infraestructure.persistence.entities.UserEntity;
import com.pge.sisgal.infraestructure.persistence.mappers.UserEntityMapper;
import com.pge.sisgal.infraestructure.persistence.repositories.JpaUserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

    @Mock
    private JpaUserRepository jpaUserRepository;

    @Mock
    private UserEntityMapper userEntityMapper;

    @InjectMocks
    private UserRepositoryAdapter userRepositoryAdapter;

    @Test
    void save_ShouldReturnSavedUser_WhenValidUser() {
        User user = TestUtils.createUser();
        UserEntity userEntity = UserEntity.builder()
            .id(user.getId())
            .name(user.getName())
            .registration(user.getRegistration())
            .email(user.getEmail())
            .password(user.getPassword())
            .role(user.getRole())
            .build();

        when(userEntityMapper.toEntity(user)).thenReturn(userEntity);
        when(jpaUserRepository.save(userEntity)).thenReturn(userEntity);
        when(userEntityMapper.toDomain(userEntity)).thenReturn(user);

        User result = userRepositoryAdapter.save(user);

        verify(jpaUserRepository).save(userEntity);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getRole(), result.getRole());
    }

    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        User user = TestUtils.createUser();
        UserEntity userEntity = UserEntity.builder()
            .id(user.getId())
            .name(user.getName())
            .registration(user.getRegistration())
            .email(user.getEmail())
            .password(user.getPassword())
            .role(user.getRole())
            .build();

        when(jpaUserRepository.findById(user.getId())).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toDomain(userEntity)).thenReturn(user);

        Optional<User> result = userRepositoryAdapter.findById(user.getId());

        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getId());
        assertEquals(user.getRole(), result.get().getRole());
    }

    @Test
    void findByRegistration_ShouldReturnUser_WhenUserExists() {
        User user = TestUtils.createUser();
        UserEntity userEntity = UserEntity.builder()
            .id(user.getId())
            .name(user.getName())
            .registration(user.getRegistration())
            .email(user.getEmail())
            .password(user.getPassword())
            .role(user.getRole())
            .build();

        when(jpaUserRepository.findByRegistration(user.getRegistration())).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toDomain(userEntity)).thenReturn(user);

        Optional<User> result = userRepositoryAdapter.findByRegistration(user.getRegistration());

        assertTrue(result.isPresent());
        assertEquals(user.getRegistration(), result.get().getRegistration());
        assertEquals(user.getRole(), result.get().getRole());
    }

    @Test
    void existsByRegistration_ShouldReturnTrue_WhenUserExists() {
        User user = TestUtils.createUser();
        when(jpaUserRepository.existsByRegistration(user.getRegistration())).thenReturn(true);

        Boolean result = userRepositoryAdapter.existsByRegistration(user.getRegistration());

        assertTrue(result);
    }
}
