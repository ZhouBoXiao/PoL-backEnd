package com.whu.bootuserservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BootUserServiceApplicationTests {

    @Test
    void contextLoads() {
        String s = "1,2,3,4,";
        String[] ll = s.split(",");
        for(int i=0; i<ll.length ;i++){
            System.out.println(ll[i]);
        }
    }

}
