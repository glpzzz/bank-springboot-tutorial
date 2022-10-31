package com.daxslab.springboot.tutorial01

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Tutorial01Application

fun main(args: Array<String>) {
	runApplication<Tutorial01Application>(*args)
}
