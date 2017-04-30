package johnsmithwithharuhi.co.sakagumi.Presentation.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import johnsmithwithharuhi.co.sakagumi.Presentation.ViewModel.Item.ItemBlogListViewModel;
import johnsmithwithharuhi.co.sakagumi.R;
import johnsmithwithharuhi.co.sakagumi.databinding.ItemBlogListBinding;

public class BlogListAdapter extends RecyclerView.Adapter<BlogListAdapter.ViewHolder> {

  private Context mContext;
  private List<ItemBlogListViewModel> mViewModelList = new ArrayList<>();
  private ItemBlogListViewModel.OnItemClickListener mListener;

  public BlogListAdapter(Context context, ItemBlogListViewModel.OnItemClickListener listener) {
    mContext = context;
    mListener = listener;
  }

  public void initViewModelList(List<ItemBlogListViewModel> viewModelList) {
    mViewModelList = viewModelList;
    notifyDataSetChanged();
  }

  public void putViewModelList(List<ItemBlogListViewModel> viewModelList) {
    mViewModelList.addAll(0, viewModelList);
    notifyItemRangeInserted(0, viewModelList.size());
  }

  public String getNewestUrl() {
    return mViewModelList.get(0).url.get();
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blog_list, parent, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    ItemBlogListBinding binding = holder.getBinding();
    ItemBlogListViewModel viewModel = mViewModelList.get(position);
    viewModel.setOnItemClickListener(mListener);
    binding.setViewModel(viewModel);
    binding.name.setTextColor(ContextCompat.getColor(mContext, viewModel.textColor));
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
