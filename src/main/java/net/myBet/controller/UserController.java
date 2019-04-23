package net.myBet.controller;

import net.myBet.dto.BooleanDto;
import net.myBet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/email/{checkedEmail}/exists")
    public @ResponseBody
    ResponseEntity isExistsUserEmail(@PathVariable("checkedEmail") String checkedEmail){
        boolean result = userService.existsByEmail(checkedEmail);
        return ResponseEntity.ok(new BooleanDto(result));
    }
}