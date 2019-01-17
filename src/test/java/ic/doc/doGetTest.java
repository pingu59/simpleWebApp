package ic.doc;
import static ic.doc.WebServer.execute;
import static junit.framework.TestCase.assertTrue;

import ic.doc.web.HTMLResultPage;
import ic.doc.web.IndexPage;
import ic.doc.web.Page;
import java.io.File;
import org.junit.Test;


public class doGetTest {

  @Test
  public void noRadioButtonReturnsHTML(){
    Page p = execute("shakespeare", null);
    assertTrue(p instanceof HTMLResultPage);
  }

  @Test
  public void incorrectPdfPage(){
    Page p = execute("qwertyuiop", "pdf");
    assertTrue(p.getAnswer().equals("# Sorry\nSorry, we didn't understand " +"qwertyuiop" ));
  }

  @Test
  public void incorrectHTMLPage(){
    Page p = execute("shakespe", null);
    assertTrue(p.getAnswer().equals("<h1>Sorry</h1>\n<p>Sorry, we didn't understand "
        + "<em>shakespe</em></p>"));
  }

  @Test
  public void incorrectMarkDownPage(){
    Page p = execute("shakespear", "markdown");
    assertTrue(p.getAnswer().equals("# Sorry\nSorry, we didn't understand shakespear"));
  }

  @Test
  public void correctPdfPage() {
    Page p = execute("shakespeare", "pdf");
    assertTrue(p instanceof PdfPage);
  }

  @Test
  public void correctMarkdownPage(){
    Page p = execute("shakespeare", "markdown");
    assertTrue(p instanceof MarkDownPage);
  }
  
  //test that there is a markdown file created
  @Test
  public void markdownFileCreated() throws Exception {
    String query = "shakespeare";
    String result = new QueryProcessor().process(query);

    File markdown = MarkDownPage.toMarkDown(result, query);

    assertTrue(markdown.isFile());
  }


  //test that there is a pdf file created
  @Test
  public void pdfFileCreated() throws Exception {
    String query = "shakespeare";
    String result = new QueryProcessor().process(query);

    File markDown = MarkDownPage.toMarkDown(result, query);
    File pdf = PdfPage.create_pdf(markDown, query);

    assertTrue(pdf.isFile());
  }

  @Test
  public void queryIsToShort() {
    Page p = execute("s", null);
    assertTrue(p.getQuery().equals("s   "));
    p = execute("", null);
    assertTrue(p.getQuery().equals("   "));
  }
}