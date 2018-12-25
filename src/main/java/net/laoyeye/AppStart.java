package net.laoyeye;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**

 */
@SpringBootApplication(scanBasePackages = "net.laoyeye.yyblog")
@EnableTransactionManagement
@MapperScan(basePackages = "net.laoyeye.yyblog.mapper")
@EnableCaching
public class AppStart
{

    public static void main(String[] args) throws Exception
    {
        SpringApplication.run(AppStart.class, args);
    }
}
