package johnsmithwithharuhi.co.sakagumi.Presentation.ViewModel.Item;

import android.databinding.ObservableField;
import android.view.View;

public class ItemBlogListViewModel {
  public ObservableField<Integer> type = new ObservableField<>();
  public ObservableField<String> title = new ObservableField<>();
  public ObservableField<String> name = new ObservableField<>();
  public ObservableField<String> time = new ObservableField<>();
  public ObservableField<String> content = new ObservableField<>();
  public ObservableField<String> url = new ObservableField<>();
  private OnItemClickListener mListener;

  public void setOnItemClickListener(OnItemClickListener listener) {
    mListener = listener;
  }

  public void onItemClick(View view) {
    mListener.onItemClick(url.get());
  }

  public interface OnItemClickListener {
    void onItemClick(String url);
  }
}
