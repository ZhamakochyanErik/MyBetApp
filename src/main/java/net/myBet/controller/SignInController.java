package net.myBet.controller;

import net.myBet.util.ViewName;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signIn")
public class SignInController {

    @GetMapping
    public String signIn(){
        return ViewName.SIGN_IN;
    }
}
