package com.scubaworld.scubagear.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scubaworld.scubagear.appuser.UserInfo;
import com.scubaworld.scubagear.appuser.AppuserDao;

@Service
public class AppuserService {
    private final AppuserDao appuserDao;

    @Autowired
    public AppuserService(AppuserDao appuserDao){
        this.appuserDao = appuserDao;
    }

    public UserInfo readUserInfo(int userIdentifier){
        return appuserDao.readUserInfo(userIdentifier);
    }
}
