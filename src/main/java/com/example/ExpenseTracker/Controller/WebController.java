package com.example.ExpenseTracker.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Web Controller for serving static content
 * Handles root path mapping to serve the main web application
 */
@Controller
public class WebController {

    /**
     * Map root path to index.html
     * @return forward to index.html
     */
    @RequestMapping("/")
    public String index() {
        return "forward:/index.html";
    }
}
