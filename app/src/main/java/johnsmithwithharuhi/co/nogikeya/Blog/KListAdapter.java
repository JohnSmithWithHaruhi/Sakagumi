package johnsmithwithharuhi.co.nogikeya.Blog;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import johnsmithwithharuhi.co.nogikeya.Blog.Model.KModel;
import johnsmithwithharuhi.co.nogikeya.R;
import johnsmithwithharuhi.co.nogikeya.databinding.ItemBlogListBinding;

class KListAdapter extends RecyclerView.Adapter<KListAdapter.ViewHolder> {

    private List<KModel> mKModelList = new ArrayList<>();
    private KModel.OnItemClickListener mListener;

    void setKModelList(List<KModel> kModelList, KModel.OnItemClickListener listener) {
        mKModelList = kModelList;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_blog_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemBlogListBinding binding = holder.getBinding();
        KModel kModel = mKModelList.get(position);
        kModel.setOnItemClickListener(mListener);
        binding.setViewModel(kModel);
    }

    @Override
    public int getItemCount() {
        return mKModelList.size();
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
