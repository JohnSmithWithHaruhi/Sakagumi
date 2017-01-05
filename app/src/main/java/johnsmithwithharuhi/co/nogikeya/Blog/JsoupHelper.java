package johnsmithwithharuhi.co.nogikeya.Blog;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class JSoupHelper {

  List<ViewModel> getViewModelList(String url) throws Exception {
    Document document = Jsoup.connect(url).get();
    Elements elements = document.getElementsByClass("box-newposts").first().getElementsByTag("li");
    List<ViewModel> viewModelList = new ArrayList<>();
    for (Element element : elements) {
      Element boxBlogElement = element.getElementsByClass(("box-blog")).first();
      ViewModel viewModel = new ViewModel();
      viewModel.time.set(boxBlogElement.select("time").text());
      viewModel.name.set(boxBlogElement.select("p").not(".ttl").first().text());
      viewModel.title.set(boxBlogElement.getElementsByClass("ttl").text());
      viewModel.content.set(boxBlogElement.getElementsByClass("text").text());
      viewModel.url.set(element.getElementsByTag("a").first().attr("href"));
      viewModelList.add(viewModel);
    }
    return viewModelList;
  }
}
