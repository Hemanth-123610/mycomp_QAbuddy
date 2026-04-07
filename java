package com.qabuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class QABuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(QABuddyApplication.class, args);
    }

    @GetMapping("/")
    public String home() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>QA Buddy</title>
                    <meta charset="UTF-8">
                </head>
                <body>
                    <h1>QA Buddy</h1>
                    <form method='post' action='/analyze'>
                        <textarea name='scenario' rows='7' cols='50'></textarea><br><br>
                        <button type='submit'>Analyze</button>
                    </form>
                </body>
                </html>
                """;
    }

    @PostMapping("/analyze")
    public String analyze(@RequestParam("scenario") String scenario) {
        return "<h1>QA Buddy Result</h1><p>Scenario: " + scenario + "</p><a href='/'>Back</a>";
    }
}
