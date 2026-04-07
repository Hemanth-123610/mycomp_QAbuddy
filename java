package com.qabuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

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
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background: #f4f7fb;
                            margin: 0;
                            padding: 0;
                        }
                        .container {
                            max-width: 900px;
                            margin: 40px auto;
                            background: white;
                            padding: 30px;
                            border-radius: 14px;
                            box-shadow: 0 4px 20px rgba(0,0,0,0.08);
                        }
                        h1 {
                            color: #1f3c88;
                        }
                        p {
                            color: #555;
                        }
                        textarea {
                            width: 100%;
                            height: 130px;
                            padding: 12px;
                            border-radius: 10px;
                            border: 1px solid #ccc;
                            margin-top: 10px;
                            font-size: 15px;
                            box-sizing: border-box;
                        }
                        button {
                            background: #1f3c88;
                            color: white;
                            border: none;
                            padding: 12px 20px;
                            margin-top: 15px;
                            border-radius: 10px;
                            cursor: pointer;
                            font-size: 15px;
                        }
                        button:hover {
                            background: #162d66;
                        }
                        .box {
                            margin-top: 25px;
                            padding: 20px;
                            background: #f9fbff;
                            border-left: 5px solid #1f3c88;
                            border-radius: 10px;
                        }
                        ul {
                            padding-left: 20px;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>QA Buddy</h1>
                        <p>Enter a scenario. QA Buddy generates sample test cases, possible bugs, and automation suggestion.</p>

                        <form method="post" action="/analyze">
                            <textarea name="scenario" placeholder="Example: Login page with username and password validation"></textarea>
                            <br>
                            <button type="submit">Analyze</button>
                        </form>
                    </div>
                </body>
                </html>
                """;
    }

    @PostMapping("/analyze")
    public String analyze(@RequestParam("scenario") String scenario) {
        String safeScenario = escapeHtml(scenario);

        StringBuilder html = new StringBuilder();
        html.append("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>QA Buddy Result</title>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background: #f4f7fb;
                            margin: 0;
                            padding: 0;
                        }
                        .container {
                            max-width: 900px;
                            margin: 40px auto;
                            background: white;
                            padding: 30px;
                            border-radius: 14px;
                            box-shadow: 0 4px 20px rgba(0,0,0,0.08);
                        }
                        h1, h2 {
                            color: #1f3c88;
                        }
                        p {
                            color: #555;
                        }
                        .box {
                            margin-top: 25px;
                            padding: 20px;
                            background: #f9fbff;
                            border-left: 5px solid #1f3c88;
                            border-radius: 10px;
                        }
                        ul {
                            padding-left: 20px;
                        }
                        a {
                            display: inline-block;
                            margin-top: 20px;
                            text-decoration: none;
                            background: #1f3c88;
                            color: white;
                            padding: 10px 18px;
                            border-radius: 8px;
                        }
                        a:hover {
                            background: #162d66;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>QA Buddy Analysis Result</h1>
                """);

        html.append("<div class='box'>");
        html.append("<h2>Scenario</h2>");
        html.append("<p>").append(safeScenario).append("</p>");
        html.append("</div>");

        html.append("<div class='box'>");
        html.append("<h2>Generated Test Cases</h2>");
        html.append("<ul>");
        html.append("<li>Verify page loads successfully for scenario: ").append(safeScenario).append("</li>");
        html.append("<li>Verify valid input is accepted correctly</li>");
        html.append("<li>Verify invalid input shows validation message</li>");
        html.append("<li>Verify submit button works properly</li>");
        html.append("<li>Verify data is saved/displayed correctly</li>");
        html.append("<li>Verify application behavior on mobile and desktop screens</li>");
        html.append("</ul>");
        html.append("</div>");

        html.append("<div class='box'>");
        html.append("<h2>Possible Bugs</h2>");
        html.append("<ul>");
        html.append("<li>Empty fields may be accepted</li>");
        html.append("<li>Submit button may fail on first click</li>");
        html.append("<li>Error messages may not display properly</li>");
        html.append("<li>UI may break on small screens</li>");
        html.append("<li>Entered data may not persist after refresh</li>");
        html.append("</ul>");
        html.append("</div>");

        html.append("<div class='box'>");
        html.append("<h2>Automation Suggestion</h2>");
        html.append("<p>Use Selenium or Playwright to automate input validation, button click flow, and result verification for this scenario.</p>");
        html.append("</div>");

        html.append("<a href='/'>Back</a>");
        html.append("""
                    </div>
                </body>
                </html>
                """);

        return html.toString();
    }

    private String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
