package net.myBet.controller;

import net.myBet.form.UserForm;
import net.myBet.util.ViewName;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signIn")
public class SignInController {

    @GetMapping
    public String signIn(Model model){
        model.addAttribute("userForm",new UserForm());
        return ViewName.SIGN_IN;
    }
}
