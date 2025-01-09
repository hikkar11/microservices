//package ru.itmentor.spring.boot_security.demo.controller;
//
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import ru.itmentor.spring.boot_security.demo.model.User;
//import ru.itmentor.spring.boot_security.demo.service.UserService;
//
//import java.util.List;
//
//@RestController
//@AllArgsConstructor
//@RequestMapping("/")
//public class UserController {
//
//    private final UserService userService;
//
//    @GetMapping("/users")
//    public String findAll(Model model) {
//        List<User> users = userService.findAll();
//        model.addAttribute("users", users);
//
//        return "user-list";
//    }
//
//    @GetMapping("/admin/user-create")
//    public String createUserForm(User user) {
//        return "user-create";
//    }
//
//    @PostMapping("/admin/user-create")
//    public String createUser(@ModelAttribute User user) {
//        userService.save(user);
//        return "redirect:/users";
//    }
//
//    @GetMapping("/admin/user-delete/{id}")
//    public String deleteUSer(@PathVariable("id") Long id) {
//        userService.deleteById(id);
//        return "redirect:/users";
//    }
//
//    @GetMapping("/admin/user-update/{id}")
//    public String updateUserForm(@PathVariable("id") Long id, Model model) {
//        User user = userService.findById(id);
//        model.addAttribute("user", user);
//        return "user-update";
//    }
//
//    @PostMapping("/admin/user-update")
//    public String updateUser(User user) {
//        userService.save(user);
//        return "redirect:/users";
//    }
//}
