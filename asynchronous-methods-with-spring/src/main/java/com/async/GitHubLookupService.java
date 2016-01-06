package com.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
public class GitHubLookupService {

    private RestTemplate restTemplate = new RestTemplate();

    @Async
    public Future<User> findUser(String user) throws InterruptedException {
        System.out.println("Looking up " + user);
        User results = restTemplate.getForObject(
                "https://api.github.com/users/" + user, User.class
        );

        // Artificial delay for demonstration purposes
        TimeUnit.SECONDS.sleep(1);

        return new AsyncResult<>(results);
    }
}
