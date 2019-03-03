package project.com.maktab.onlinemarket.controller.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.controller.activity.CompleteProductListActivity;
import project.com.maktab.onlinemarket.model.category.Category;
import project.com.maktab.onlinemarket.model.category.CategoryLab;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubCategoryRecyclerFragment extends VisibleFragment {

    private static final String CATEGORY_Parent_ID_ARGS = "categoryIdArgs";


    private RecyclerView mRecyclerView;
    private SubCategoryAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private List<Category> mCategoryList;

    private long mCategoryParentId;

    public static SubCategoryRecyclerFragment newInstance(long categoryId) {

        Bundle args = new Bundle();
        args.putLong(CATEGORY_Parent_ID_ARGS, categoryId);
        SubCategoryRecyclerFragment fragment = new SubCategoryRecyclerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryParentId = getArguments().getLong(CATEGORY_Parent_ID_ARGS);
        mCategoryList = CategoryLab.getmCategoryInstance().getSubCategoires(mCategoryParentId);
    }

    public SubCategoryRecyclerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_category_recycler, container, false);
        mRecyclerView = view.findViewById(R.id.sub_category_recycler_view);
     /*   mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.progress_product));
        mProgressDialog.show();*/

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new SubCategoryAdapter(mCategoryList);
        mRecyclerView.setAdapter(mAdapter);

/*

        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getSubCategories(String.valueOf(mCategoryParentId))
                .enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        if (response.isSuccessful()) {
                            List<Category> categoryList = response.body();
                            mAdapter = new SubCategoryAdapter(categoryList);
                            mRecyclerView.setAdapter(mAdapter);
                            mProgressDialog.cancel();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();
                    }
                });
*/


        return view;
    }

    private class SubCategoryViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mImageView;
        private TextView mTextView;
        private Category mCategory;

        public SubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.category_image_view_list_item);
            mTextView = itemView.findViewById(R.id.category_text_view_list_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = CompleteProductListActivity.newIntent(getActivity(),"nothing",mCategory.getId(),
                            "nothing",false,true,false);
                    startActivity(intent);
                }
            });
        }

        public void bind(Category category) {
            mCategory = category;
            mTextView.setText(category.getName());

            if (category.getImage() != null) {
                Picasso.get().load(category.getImage().getPath())
                        .placeholder(R.drawable.shop)
                        .into(mImageView);

            }

        }

    }

    private class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryViewHolder> {
        List<Category> mCategoryList;

        public SubCategoryAdapter(List<Category> categoryList) {
            mCategoryList = categoryList;
        }

        public void setCategoryList(List<Category> categoryList) {
            mCategoryList = categoryList;
        }

        @NonNull
        @Override
        public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.category_list_item, viewGroup, false);
            return new SubCategoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SubCategoryViewHolder subCategoryViewHolder, int i) {
            Category category = mCategoryList.get(i);
            subCategoryViewHolder.bind(category);
        }

        @Override
        public int getItemCount() {
            return mCategoryList.size();
        }
    }

}
