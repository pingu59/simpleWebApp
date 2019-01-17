package ic.doc.web;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HTMLResultPage extends NoneIndexPage {

    public HTMLResultPage(String query, String answer) {
        super(query,answer);
        if (answer == null || answer.isEmpty()) {
            answer = "<h1>Sorry</h1>\n<p>Sorry, we didn't understand <em>"+ query + "</em></p>";
        } else {
            answer = "<h1>" + query + "</h1>\n<p>"
                + answer.replace("\n", "<br>") + "</p>";
        }
        this.answer = answer;

    }

    public void writeTo(HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        // Header
        writer.println("<html>");
        writer.println("<head><title>" + query + "</title></head>");
        writer.println("<body>");

        // Content
        writer.println(answer);

        writer.println("<p><a href=\"/\">Back to Search Page</a></p>");

        // Footer
        writer.println("</body>");
        writer.println("</html>");
    }
}
