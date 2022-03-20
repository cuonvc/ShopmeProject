package com.shopme.shopmebackend.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

    @Test
    public void testEncodePassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "12356756793abcdsdsfd";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println(encodedPassword);  //sample encode output (60char): $2a$10$nmc1UdYFXDlHBj.Fpwfd.ONrsniwfKm.JJB8Zu6skHzCo9JjFgO3.
    }
}
