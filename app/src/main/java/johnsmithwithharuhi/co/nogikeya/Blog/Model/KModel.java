package johnsmithwithharuhi.co.nogikeya.Blog.Model;

import android.databinding.ObservableField;
import android.view.View;

import johnsmithwithharuhi.co.nogikeya.Constant;

public class KModel {
    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> time = new ObservableField<>();
    public ObservableField<String> content = new ObservableField<>();
    public ObservableField<String> url = new ObservableField<>();
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(String url);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void onItemClick(View view) {
        mListener.onItemClick(Constant.K_URL + url.get());
    }
}
