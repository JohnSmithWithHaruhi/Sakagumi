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

  void setKModelList(List<ViewModel> viewModelList, ViewModel.OnItemClickListener listener) {
    mViewModelList = viewModelList;
    mListener = listener;
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
