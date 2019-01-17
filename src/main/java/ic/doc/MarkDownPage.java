package ic.doc;
import ic.doc.web.NoneIndexPage;
import ic.doc.web.Page;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;

public class MarkDownPage extends NoneIndexPage {
  public MarkDownPage(String query, String answer) {
    super(query,answer);
    if (answer == null || answer.isEmpty()) {
      answer = "# Sorry\nSorry, we didn't understand " + query;
    }else{
      answer ="# "+ query + "\n" + answer;
    }
    this.answer = answer;
  }

  public static File toMarkDown(String result, String query) throws IOException {
    File temp = File.createTempFile(query,".md",null);
    System.out.println(temp.getAbsolutePath());
    BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
    writer.write(result);
    writer.close();
    return temp;
  }

  @Override
  public void writeTo(HttpServletResponse resp) throws IOException {
    resp.setContentType("text/markdown; variant=CommonMark");
    OutputStream writer = resp.getOutputStream();
    File markdown = toMarkDown(answer, query);
    InputStream stream = new FileInputStream(markdown);
    writer.write(stream.readAllBytes());
    writer.close();
    markdown.delete();
  }
}
