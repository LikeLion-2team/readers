package com.project.readers.service;

import com.project.readers.entity.UserDTO;
import com.project.readers.entity.UserSessionDTO;
import com.project.readers.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;

    public void registerUser(UserDTO userDTO) {
        userDAO.register(userDTO);
    }

    public UserSessionDTO loginUser(UserDTO userDTO) {
        return userDAO.login(userDTO);
    }

    public void updateUser(UserDTO userDTO) {
        userDAO.update(userDTO);
    }

    public void deleteUser(int idNum) {
        userDAO.delete(idNum);
    }
}
