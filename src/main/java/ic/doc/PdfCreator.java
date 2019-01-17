package ic.doc;

import static ic.doc.MarkDownCreator.toMarkDown;
import ic.doc.web.Page;
import java.io.*;
import javax.servlet.http.HttpServletResponse;

public class PdfCreator implements Page {

  private final String query;
  private String answer;

  public PdfCreator(String query, String answer) {
    this.query = query;
    this.answer = answer;
  }

  @Override
  public void writeTo(HttpServletResponse resp) throws IOException, InterruptedException {
    resp.setContentType("application/pdf");
    OutputStream writer = resp.getOutputStream();
    File markdown = toMarkDown(answer, query);
    // turn markdown to a pdf using pandoc
    ProcessBuilder pb = new ProcessBuilder("pandoc",markdown.getName() , "-o",query + ".pdf");
    File dir = new File("/tmp");
    pb.directory(dir);
    System.out.println("The new pdf will have name of "+query + ".pdf");
    Process process = pb.start();
    process.waitFor();
    System.out.println("=================================================");
    String[] strs = dir.list();
    for(String str: strs){
      System.out.println(str);
    }
    System.out.println("=================================================");
    File pdf = new File("/tmp/"+query+".pdf");
    writer.write(new FileInputStream(pdf).readAllBytes());
    writer.close();
    markdown.delete();
    pdf.delete();
  }
}
