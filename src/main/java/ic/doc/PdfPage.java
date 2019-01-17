package ic.doc;

import static ic.doc.MarkDownPage.toMarkDown;

import ic.doc.web.NoneIndexPage;
import ic.doc.web.Page;
import java.io.*;
import javax.servlet.http.HttpServletResponse;

public class PdfPage extends MarkDownPage {
  public PdfPage(String query, String answer) {
    super(query,answer);

  }

  public static File create_pdf(File markdown,String query) throws IOException{
    ProcessBuilder pb = new ProcessBuilder("pandoc",markdown.getName(), "-o",query + ".pdf");
    File dir = new File("/tmp");
    pb.directory(dir);
    Process process = pb.start();
    try{
      System.out.println("The process returns the result :" + process.waitFor());
    }catch (InterruptedException e){

    }
    return new File("/tmp/"+query+".pdf");
  }

  @Override
  public void writeTo(HttpServletResponse resp) throws IOException {
    resp.setContentType("application/pdf");
    OutputStream writer = resp.getOutputStream();
    File markdown = toMarkDown(answer, query);
    File pdf = create_pdf(markdown, query);
    writer.write(new FileInputStream(pdf).readAllBytes());
    writer.close();
    boolean md = markdown.delete();
    boolean pd = pdf.delete();
  }
}
