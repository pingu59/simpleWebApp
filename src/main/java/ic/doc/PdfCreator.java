package ic.doc;

import static ic.doc.MarkDownCreator.toMarkDown;

import ic.doc.web.Page;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;

public class PdfCreator implements Page {

  private final String query;
  private String answer;

  public PdfCreator(String query, String answer) {
    this.query = query;
    this.answer = answer;
  }

  @Override
  public void writeTo(HttpServletResponse resp) throws IOException {
    resp.setContentType("application/pdf");
    OutputStream writer = resp.getOutputStream();
    File markdown = toMarkDown(answer, query);
    // turn markdown to a pdf using pandoc
    InputStream stream = new FileInputStream(markdown);
    writer.write(stream.readAllBytes());
    writer.close();
    markdown.delete();
  }
}
