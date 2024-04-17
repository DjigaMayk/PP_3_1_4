package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public void addNewPerson(User user) {
        usersRepository.save(user);
    }

    @Override
    public Optional<User> getPersonById(int id) {
        return usersRepository.findById(id); }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getPersonByName(String name){ return usersRepository.findByFirstName(name); }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllPersons() { return usersRepository.findAll(); }


    @Transactional
    @Override
    public void changePersonById(int id, User user) { usersRepository.updateUserById(id, user); }


    @Override
    public void deletePersonById(int id){ usersRepository.deleteById(id); }



    @Override
    public void changePassword(int userId, String newPassword) {
        User user = usersRepository.findById(userId).
                orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user != null) {
            user.setPassword(newPassword);
            usersRepository.save(user);
        }
    }


}
