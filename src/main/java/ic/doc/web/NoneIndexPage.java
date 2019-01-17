package ic.doc.web;

public abstract class NoneIndexPage implements Page{
  protected final String query;
  protected String answer;

  public NoneIndexPage(String query, String answer) {
    this.query = query;
    this.answer = answer;
  }

  @Override
  public String getAnswer(){
    return answer;
  }
}
