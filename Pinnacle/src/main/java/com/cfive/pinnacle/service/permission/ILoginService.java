package com.cfive.pinnacle.service.permission;

import com.cfive.pinnacle.entity.User;

import java.util.HashMap;

public interface ILoginService {
    HashMap<String, String> login(User user);

    boolean logout();
}
