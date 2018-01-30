package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

  @Bean //パスワードの暗号化方式を宣言（平文でDBにパスワードを保存しないこと！）
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
}
