package johnsmithwithharuhi.co.sakagumi.Data.Entity;

public class BlogEntity {

  public static final int OSU_KEY = 0;
  public static final int NOG_KEY = 1;
  public static final int KEY_KEY = 2;

  private int type;
  private String name;
  private String title;
  private String url;
  private String content;
  private String time;

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }
}
