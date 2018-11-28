package interfaces;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class RESTController {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/user")
    public User user(@RequestParam(value="name", defaultValue="Patrick") String name) {
        return new User(counter.incrementAndGet(), name);
    }
}