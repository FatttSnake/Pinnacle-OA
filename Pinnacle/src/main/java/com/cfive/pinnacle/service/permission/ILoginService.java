package com.cfive.pinnacle.service.permission;

import com.cfive.pinnacle.entity.permission.User;

import java.util.HashMap;

public interface ILoginService {
    HashMap<String, String> login(User user);

    boolean logout(String token);

    HashMap<String, String> renewToken(String token);
}
