package com.practice.oauth2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class Oauth2Application

fun main(args: Array<String>) {
	runApplication<Oauth2Application>(*args)
}
