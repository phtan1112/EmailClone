package com.javafinal.WebMail.Controller;


import com.javafinal.WebMail.Model.User;
import com.javafinal.WebMail.Model.UserRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {
    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;
    @GetMapping("")
    public String forgot(){
        return "/LoginAndRegister/forgotPassword";
    }
    @PostMapping("")
    public String NextforgotPassword(ForgotPass forgotPass, Model model) {
        List<User> lstUser = (List<User>) repo.findAll();
        boolean check = false;
        User u1 = null;
        for (User u : lstUser) {
            if (u.getPhone().equals(forgotPass.getPhone())) {
                check = true;
                u1 = u;
                break;
            }
        }
        if (forgotPass.getPhone().equals("")) {
            model.addAttribute("ErrorMsg", "Your phone is empty");
        } else {
            if (!check) {
                model.addAttribute("ErrorMsg", "Your phone is not exist");
            } else {
                if (forgotPass.getPassword1().equals("")) {
                    model.addAttribute("ErrorMsg", "Your new password is empty");
                } else if (forgotPass.getPassword1().length() < 6) {
                    model.addAttribute("ErrorMsg", "Password at least 6 characters");
                } else if (!forgotPass.getPassword1().equals(forgotPass.getPassword2())) {
                    model.addAttribute("ErrorMsg", "Confirm password is not correct");
                } else {
                    u1.setPassword(encoder.encode(forgotPass.getPassword1()));
                    repo.save(u1);
                    model.addAttribute("SuccessMsg", "Recover account successfully");

                }
            }

        }
        return "/LoginAndRegister/forgotPassword";
    }

}
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class ForgotPass{
    private String phone;
    private String password1;
    private String password2;
}
