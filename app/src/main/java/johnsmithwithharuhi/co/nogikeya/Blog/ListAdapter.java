package johnsmithwithharuhi.co.nogikeya.Blog;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import johnsmithwithharuhi.co.nogikeya.R;
import johnsmithwithharuhi.co.nogikeya.databinding.ItemBlogListBinding;

class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

  private List<ViewModel> mViewModelList = new ArrayList<>();
  private ViewModel.OnItemClickListener mListener;

  ListAdapter(ViewModel.OnItemClickListener listener) {
    mListener = listener;
  }

  void putViewModelList(List<ViewModel> viewModelList) {
    if (!mViewModelList.isEmpty()) {
      String firstUrl = mViewModelList.get(0).url.get();
      List<ViewModel> tempViewModelList = new ArrayList<>();
      for (ViewModel viewModel : viewModelList) {
        if (firstUrl.equals(viewModel.url.get())) {
          break;
        } else {
          tempViewModelList.add(viewModel);
        }
      }
      mViewModelList.addAll(tempViewModelList);
      notifyItemRangeInserted(0, tempViewModelList.size());
    } else {
      mViewModelList.addAll(viewModelList);
      notifyDataSetChanged();
    }
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blog_list, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    ItemBlogListBinding binding = holder.getBinding();
    ViewModel viewModel = mViewModelList.get(position);
    viewModel.setOnItemClickListener(mListener);
    binding.setViewModel(viewModel);
  }

  @Override public int getItemCount() {
    return mViewModelList.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    private ItemBlogListBinding mBinding;

    ViewHolder(View itemView) {
      super(itemView);
      mBinding = DataBindingUtil.bind(itemView);
    }

    ItemBlogListBinding getBinding() {
      return mBinding;
    }
  }
}
