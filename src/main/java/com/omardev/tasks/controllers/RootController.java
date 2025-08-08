package com.omardev.tasks.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that handles the root endpoint ("/").
 */
@RestController
public class RootController {

    /**
     * Returns a simple welcome message for the root endpoint.
     *
     * @return welcome message
     */
    @GetMapping("/")
    public String welcome() {
        return "✅ Task Tracker API is up and running!";
    }
}