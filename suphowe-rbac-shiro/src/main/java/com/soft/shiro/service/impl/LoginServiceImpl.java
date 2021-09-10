package com.soft.shiro.service.impl;

import com.soft.shiro.beans.Permissions;
import com.soft.shiro.beans.Role;
import com.soft.shiro.beans.User;
import com.soft.shiro.service.ILoginService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 登录逻辑处理
 * @author suphowe
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Override
    public User getUserByName(String getMapByName) {
        return getMapByName(getMapByName);
    }

    /**
     * 模拟数据库查询
     *
     * @param userName 用户名
     * @return User
     */
    private User getMapByName(String userName) {
        Permissions permissions1 = new Permissions("1", "query");
        Permissions permissions2 = new Permissions("2", "add");

        //添加query 和add权限
        Set<Permissions> permissionsSet = new HashSet<>(4);
        permissionsSet.add(permissions1);
        permissionsSet.add(permissions2);
        Role role = new Role("1", "admin", permissionsSet);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        User user = new User("1", "suphowe", "123456", roleSet);
        HashMap<String, User> map = new HashMap<>(4);
        map.put(user.getUserName(), user);

        // 只添加query
        Set<Permissions> permissionsSet1 = new HashSet<>();
        permissionsSet1.add(permissions1);
        Role role1 = new Role("2", "user", permissionsSet1);
        Set<Role> roleSet1 = new HashSet<>();
        roleSet1.add(role1);
        User user1 = new User("2", "zhangsan", "123456", roleSet1);
        map.put(user1.getUserName(), user1);
        return map.get(userName);
    }

}
