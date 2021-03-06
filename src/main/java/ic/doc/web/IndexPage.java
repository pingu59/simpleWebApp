package ic.doc.web;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class IndexPage implements Page {
    @Override
    public String getQuery(){
        return null;
    }

    public void writeTo(HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        // Header
        writer.println("<html>");
        writer.println("<head><title>Welcome</title></head>");
        writer.println("<h2><title>This is the Circle CI extension </title></h2>");
        writer.println("<body>");

        // Content
        writer.println(
                "<h1>Welcome!!</h1>" +
                        "<p>Enter your query in the box below: " +
                        "<form>" +
                        "<input type=\"text\" name=\"q\" /><br>" +
                        "<input type=\"radio\" name=\"t\" value=\"markdown\"> markdown<br>"+
                        "<input type=\"radio\" name=\"t\" value=\"pdf\"> pdf<br>"+
                        "<br><br><input type=\"submit\">" +
                        "</form>" +
                        "</p>");

        // Footer
        writer.println("</body>");
        writer.println("</html>");
    }

    @Override
    public String getAnswer(){
        return null;
    }
    
}
