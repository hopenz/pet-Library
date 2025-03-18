package ru.hopenz.petLibrary.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.hopenz.petLibrary.data.dto.user.RequestUpdateProfile;
import ru.hopenz.petLibrary.data.dto.user.ResponseUserDto;
import ru.hopenz.petLibrary.data.entity.User;
import ru.hopenz.petLibrary.data.entity.enums.UserRole;
import ru.hopenz.petLibrary.data.mapper.UserMapper;
import ru.hopenz.petLibrary.exception.EntityNotFoundException;
import ru.hopenz.petLibrary.repository.UserRepository;

@Component
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("user not found");
        }
    }

    public boolean addUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRole(UserRole.ROLE_READER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateUserProfile(String username, RequestUpdateProfile requestUpdateProfile) {
        User user = userRepository.findByUsername(username);

        user.setName(requestUpdateProfile.name());
        user.setSurname(requestUpdateProfile.surname());
        user.setEmail(requestUpdateProfile.email());

        return userRepository.save(user);
    }

    public ResponseUserDto getUserById(Long id) {
        User user = userRepository.findAllById(id);
        return userMapper.toResponseDto(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    public void updateUser(Long id, RequestUpdateProfile requestUpdateProfile) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("User", id));

        user.setName(requestUpdateProfile.name());
        user.setSurname(requestUpdateProfile.surname());
        user.setEmail(requestUpdateProfile.email());

        userRepository.save(user);
    }
}
