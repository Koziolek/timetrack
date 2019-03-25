package pl.koziolekweb.fh.timetrack

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class TimetrackApplication

fun main(args: Array<String>) {
    SpringApplication.run(TimetrackApplication::class.java, *args)
}
