package ic.doc;
import ic.doc.web.Page;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;

public class MarkDownCreator implements Page {

  private final String query;
  private String answer;

  public MarkDownCreator(String query, String answer) {
    this.query = query;
    this.answer = answer;
  }

  private File toMarkDown(String result) throws IOException {
    File temp = File.createTempFile(query,".markdown",null);
    System.out.println(temp.getAbsolutePath());
    BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
    writer.write(result);
    writer.close();
    return temp;
  }

  @Override
  public void writeTo(HttpServletResponse resp) throws IOException {
    resp.setContentType("text/md");
    OutputStream writer = resp.getOutputStream();
    // Content
    if (answer == null || answer.isEmpty()) {
      answer = "# Sorry\nSorry, we didn't understand" + query;
    }else{
      answer ="# "+ query + "\n" + answer;
    }
    File markdown = toMarkDown(answer);
    InputStream stream = new FileInputStream(markdown);
    writer.write(stream.readAllBytes());
    writer.close();
    markdown.delete();
  }
}
