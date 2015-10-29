package net.caspervg.katch

import org.redisson.Config
import org.redisson.Redisson
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@ComponentScan
@Configuration
@EnableAutoConfiguration
public open class Application {
    companion object {
        @JvmStatic public fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args);
        }


        @Bean
        public fun redis() : Redisson {
            val config = Config()
            config.useSingleServer()

            return Redisson.create()
        }
    }
}

