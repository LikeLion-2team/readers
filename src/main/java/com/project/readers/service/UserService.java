package com.project.readers.service;

import com.project.readers.common.Constant;
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
        Integer resultValue = userDAO.register(userDTO);
        insertRole(userDTO, resultValue);
    }

    private void insertRole(UserDTO userDTO, Integer resultValue) {
        if (!resultValue.equals(Constant.FALSE_VALUE)) {
            userDAO.insertRole(userDTO);
        }
    }

    public String compareId(String id) {
        String resultValue = userDAO.compareId(id);
        if (resultValue == null) {
            return "";
        }
        return resultValue;
    }

    public UserSessionDTO loginUser(UserDTO userDTO) {
        return userDAO.login(userDTO);
    }

    public void updateUser(UserDTO userDTO) {
        userDAO.update(userDTO);
    }
}
