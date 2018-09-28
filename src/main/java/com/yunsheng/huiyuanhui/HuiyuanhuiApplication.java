package com.yunsheng.huiyuanhui;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.yunsheng.huiyuanhui.mapper")
public class HuiyuanhuiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuiyuanhuiApplication.class, args);
	}
}
