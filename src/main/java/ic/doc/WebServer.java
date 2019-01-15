package ic.doc;


import ic.doc.web.HTMLResultPage;
import ic.doc.web.IndexPage;
import java.io.File;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebServer {

    public WebServer() throws Exception {
        Server server = new Server(Integer.valueOf(System.getenv("PORT")));

        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(new ServletHolder(new Website()), "/*");
        server.setHandler(handler);

        server.start();
    }

    static class Website extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            String query = req.getParameter("q");
            String type = req.getParameter("t");
            if (query == null) {
                new IndexPage().writeTo(resp);
            } else {
                String result = new QueryProcessor().process(query);
                if(type == null){
                    new HTMLResultPage(query, result).writeTo(resp);
                }else if (type.equals("markdown")){
                    new MarkDownCreator(query, result).writeTo(resp);
                }else{
                    //PDF

                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new WebServer();
    }
}

