package johnsmithwithharuhi.co.nogikeya.Blog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class JSoupHelper {

  List<ViewModel> getViewModelList(String url) throws IOException {
    Document document = Jsoup.connect(url).get();
    Elements elements = document.getElementsByTag("article");
    List<ViewModel> viewModelList = new ArrayList<>();
    for (Element element : elements) {
      ViewModel viewModel = new ViewModel();
      viewModel.time.set(
          element.getElementsByClass(("box-bottom")).first().getElementsByTag("li").first().text());
      viewModel.name.set(element.getElementsByClass("name").text());
      viewModel.title.set(element.getElementsByTag("a").first().text());
      viewModel.content.set(element.getElementsByClass("box-article").text());
      viewModel.url.set(element.getElementsByTag("a").first().attr("href"));
      viewModelList.add(viewModel);
    }
    return viewModelList;
  }
}
