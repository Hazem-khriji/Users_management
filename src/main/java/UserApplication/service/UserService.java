package UserApplication.service;


import UserApplication.model.User;
import UserApplication.exception.UserNotFoundException;
import UserApplication.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService() {}

    public void UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        if (this.userRepository.existsById(id)) {
            return this.userRepository.findById(id);
        } else {
            throw new UserNotFoundException("No user with matching id was found !");
        }
    }
    public Optional<User> getUserByEmail(String email) {
        if (this.userRepository.findByEmail(email).isPresent()) {
            return this.userRepository.findByEmail(email);
        } else {
            throw new UserNotFoundException("No user with matching email was found !");
        }
    }
    public User addUser(User user) {
        return userRepository.save(user);
    }
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }
    public void deleteByEmail(String email) {
        this.userRepository.deleteByEmail(email);
    }


}
