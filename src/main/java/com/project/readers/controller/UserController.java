package com.project.readers.controller;

import com.project.readers.common.Constant;
import com.project.readers.config.UserRoleConfig;
import com.project.readers.entity.UserDTO;
import com.project.readers.entity.UserSessionDTO;
import com.project.readers.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public Map<String, Object> registerUser(UserDTO userDTO) {
        Map<String, Object> result = new HashMap<>();

        userService.registerUser(userDTO);
        result.put("result", "success");
        return result;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> loginUser(UserDTO userDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> resultMap = new HashMap<>();
        UserSessionDTO loggedUser = userService.loginUser(userDTO);

        if (loggedUser != null) {
            loggedUser.setId(userDTO.getId());
            loggedUser.setRoleNum(UserRoleConfig.UserRole.USER.getLevel());

            session.setAttribute(Constant.USER_INFO, loggedUser);

            resultMap.put("msg", "로그인 성공");
            resultMap.put("resultCode", "1");

        } else {
            resultMap.put("msg", "로그인 실패");
            resultMap.put("resultCode", "2");
        }
        return resultMap;
    }

    @GetMapping("/idcheck")
    @ResponseBody
    public Map<String, Object> validateUserId(UserDTO dto) {

        Map<String, Object> result = new HashMap<>();
//
//        if (userService.idCheck(dto)) {
//            result.put("result", "success");
//        } else {
//            result.put("result", "fail");
//        }
        return result;
    }

    @GetMapping("/emailcheck")
    @ResponseBody
    public Map<String, Object> validateUserEmail(UserDTO dto) {
        Map<String, Object> result = new HashMap<>();

//        if (userService.emailCheck(dto)) {
//            result.put("result", "success");
//        } else {
//            result.put("result", "fail");
//        }
        return result;
    }

    @PutMapping("/update")
    public void updateUser(UserDTO userDTO) {
        userService.updateUser(userDTO);
    }

    @DeleteMapping("/delete")
    public void deleteUser(int idNum) {
        userService.deleteUser(idNum);
    }
}
