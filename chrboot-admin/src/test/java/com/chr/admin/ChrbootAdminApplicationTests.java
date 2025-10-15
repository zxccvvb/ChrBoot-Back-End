package com.chr.admin;

import com.chr.admin.pojo.vo.req.UserUpdateVo;
import com.chr.admin.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChrbootAdminApplicationTests {


    @Autowired
    private UserService userService;


    @Test
    void testUser() {
        UserUpdateVo userUpdateVo = new UserUpdateVo();
        userUpdateVo.setId(1);
        userUpdateVo.setUsername("test1");
        userService.updateUser(userUpdateVo);
    }

}
