package ic.doc;


import ic.doc.web.HTMLResultPage;
import ic.doc.web.IndexPage;
import ic.doc.web.Page;
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
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
            String query = req.getParameter("q");
            String type = req.getParameter("t");
            Page p = execute(query, type);
            try{
                p.writeTo(resp);
            }catch (InterruptedException e){

            }
        }

    }


    public static Page execute(String query, String type) {
        if (query.length() <3){
            query += "   ";
                /* java.lang.IllegalArgumentException: Prefix string
                    "" too short: length must be at least 3 */
        }
        if (query == null) {
            return new IndexPage();
        } else {
            String result = new QueryProcessor().process(query);
            if(type == null){
                return new HTMLResultPage(query, result);
            }else if (type.equals("markdown")){
                return new MarkDownPage(query, result);
            }else{
                return new PdfPage(query, result);
            }
        }
    }


    public static void main(String[] args) throws Exception {
        new WebServer();
    }
}

