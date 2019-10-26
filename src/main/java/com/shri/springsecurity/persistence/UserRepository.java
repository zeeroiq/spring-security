package com.shri.springsecurity.persistence;

import com.shri.springsecurity.web.model.User;

public interface UserRepository {

    Iterable<User> findAll();

    User save(User user);

    User findUser(Long id);

    void deleteUser(Long id);

}
