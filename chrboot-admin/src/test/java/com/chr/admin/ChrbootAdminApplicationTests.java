package com.chr.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chr.admin.service.UserService;
import com.chr.common.utils.http.HttpClientUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChrbootAdminApplicationTests {


    @Autowired
    private UserService userService;


    @Test
    public void test1() {
        String res = HttpClientUtil.doGet("http://127.0.0.1:8088/admin/user/3",null);
        System.out.println("res = " + res);
        JSONObject jsonObject = JSON.parseObject(res);
        String data = jsonObject.getString("data");
        System.out.println("data = " + data);

    }

}
