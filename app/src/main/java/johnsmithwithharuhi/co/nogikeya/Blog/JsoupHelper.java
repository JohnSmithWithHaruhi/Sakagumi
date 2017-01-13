package johnsmithwithharuhi.co.nogikeya.Blog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import johnsmithwithharuhi.co.nogikeya.Constant;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class JSoupHelper {

  List<ViewModel> getKViewModelList(String url) throws IOException {
    Document document = Jsoup.connect(url).get();
    Elements elements = document.getElementsByTag("article");
    List<ViewModel> viewModelList = new ArrayList<>();
    for (Element element : elements) {
      ViewModel viewModel = new ViewModel();
      viewModel.name.set(element.getElementsByClass("name").text());
      viewModel.title.set(element.getElementsByTag("a").first().text());
      viewModel.url.set(Constant.K_URL + element.getElementsByTag("a").first().attr("href"));
      viewModel.content.set(element.getElementsByClass("box-article").text());
      viewModel.time.set(
          element.getElementsByClass(("box-bottom")).first().getElementsByTag("li").first().text());
      viewModelList.add(viewModel);
    }
    return viewModelList;
  }

  List<ViewModel> getNViewModelList(String url) throws IOException {
    Document document = Jsoup.connect(url).get();
    Elements headElements = document.select(".heading");
    Elements bodyElements = document.getElementsByClass("entrybody");
    Elements bottomElements = document.getElementsByClass("entrybottom");
    List<ViewModel> viewModelList = new ArrayList<>();
    for (int i = 0; i < headElements.size(); i++) {
      Element headElement = headElements.get(i);
      ViewModel viewModel = new ViewModel();
      viewModel.name.set(headElement.getElementsByClass("author").text());
      viewModel.title.set(headElement.getElementsByTag("a").first().text());
      viewModel.url.set(headElement.getElementsByTag("a").first().attr("href"));
      viewModel.content.set(bodyElements.get(i).text().replace(" ", ""));
      viewModel.time.set(bottomElements.get(i).text().split("｜")[0]);
      viewModelList.add(viewModel);
    }
    return viewModelList;
  }
}
