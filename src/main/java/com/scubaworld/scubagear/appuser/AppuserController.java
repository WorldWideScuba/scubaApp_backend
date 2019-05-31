package com.scubaworld.scubagear.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.scubaworld.scubagear.appuser.AppuserService;

@RestController
public class AppuserController {

    private AppuserService appuserService;

    @Autowired
    public AppuserController(AppuserService appuserService){
        this.appuserService = appuserService;
    }

    @GetMapping(value = "/api/users")
    public UserInfo readUserInfo(
        @RequestParam(value = "userIdentifier", required=true) int userIdentifier){
            return appuserService.readUserInfo(userIdentifier);
    }
}
