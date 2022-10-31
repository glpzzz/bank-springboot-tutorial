package com.daxslab.springboot.tutorial01

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/hello")
class HelloWorldController {
    @GetMapping
    fun HelloWorld():String{
        return "Hello, Spring Boot!!!"
    }
}
