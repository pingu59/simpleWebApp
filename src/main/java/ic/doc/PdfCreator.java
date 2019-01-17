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
    ProcessBuilder pb = new ProcessBuilder("pandoc",markdown.getName() , "-o", "temp.pdf");
    File dir = new File("/tmp");
    pb.directory(dir);
    Process process = pb.start();
    process.waitFor();
    File pdf = new File("/tmp/temp.pdf");
    writer.write(new FileInputStream(pdf).readAllBytes());
    writer.close();
    markdown.delete();
    pdf.delete();
  }
}
