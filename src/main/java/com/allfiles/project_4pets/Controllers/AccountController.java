package com.allfiles.project_4pets.Controllers;

import com.allfiles.project_4pets.Objects.UserDTO;
import com.allfiles.project_4pets.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    private final UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }


    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/signup")
    public String signup(@ModelAttribute UserDTO userDTO, Model model) {
        model.addAttribute("userDTO", userDTO);
        return "signup";
    }

    @PostMapping("/signup")
    public String save(UserDTO userDTO, BindingResult bindingResult) {

        if (userService.userExists(userDTO.getEmail())) {
            bindingResult.addError(new FieldError("userDTO", "email", "Email address already in use."));
            System.out.println(" error in the is already in use");
        }

        if (userDTO.getPassword() != null && userDTO.getPassword() != userDTO.getRePassword()){
            if (userDTO.getPassword().equals(userDTO.getRePassword())){
                bindingResult.addError(new FieldError("userDTO", "rePassword", "Password must match."));
                System.out.println(" error in repassword");
            }
        }

        userService.signup(userDTO);
        return "redirect:/";
    }
}
