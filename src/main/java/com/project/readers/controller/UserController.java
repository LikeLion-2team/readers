package com.project.readers.controller;

import com.project.readers.entity.UserDTO;
import com.project.readers.entity.UserSessionDTO;
import com.project.readers.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public String registerUser(@Valid UserDTO userDTO) {
        userService.registerUser(userDTO);
        return "redirect:/index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public Map<String, String> loginUser(UserDTO userDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();

        UserSessionDTO loginUser = userService.loginUser(userDTO);

        Map<String, String> resultMap = new HashMap<>();
        if (loginUser == null) {
            resultMap.put("result", "fail");
            resultMap.put("message", "아이디가 존재하지 않습니다.");
        } else {
            session.setAttribute("id", loginUser.getId());
            session.setAttribute("roleNum", loginUser.getRoleNum());

            resultMap.put("result", "success");
            resultMap.put("message", "로그인 성공");
        }
        return resultMap;
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
