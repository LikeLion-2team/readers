package com.project.readers.repository;

import com.project.readers.entity.UserDTO;
import com.project.readers.entity.UserSessionDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {
    Integer register(UserDTO userDTO);
    UserSessionDTO login(UserDTO userDTO);

    void update(UserDTO userDTO);

    void delete(int idNum);

    void insertRole(UserDTO userDTO);

    String compareId(String id);
}
