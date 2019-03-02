package project.com.maktab.onlinemarket.controller.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.maktab.onlinemarket.R;
import project.com.maktab.onlinemarket.eventbus.FilterProductMassage;
import project.com.maktab.onlinemarket.model.attributes.Attributes;
import project.com.maktab.onlinemarket.model.attributes.AttributesTerms;
import project.com.maktab.onlinemarket.network.base.Api;
import project.com.maktab.onlinemarket.network.base.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFilterFragment extends Fragment {
    @BindView(R.id.attribute_recycler_view)
    RecyclerView mAttributeRecyclerView;
    @BindView(R.id.attribute_term_recycler_view)
    RecyclerView mTermAttrRecyclerView;
    @BindView(R.id.do_filter_btn)
    Button mDoFilterBtn;
    @BindView(R.id.filter_progress_bar)
    ProgressBar mProgressBar;
    private int selectedPos = RecyclerView.NO_POSITION;
    int selected_position = 0;
    public static List<String> mFilteredAttributes ;
    private AttrAdapter mAttrAdapter;
    private TermAttrAdapter mTermAttrAdapter;
    private List<Attributes> mAttributesList;
    private List<AttributesTerms> mTermsAttributesList;

    private static final String PRODUCT_ID_ARGS = "PRODUCT_ID_ARGS";
    private String mProductId;


    public static ProductFilterFragment newInstance(String productId) {

        Bundle args = new Bundle();
        args.putString(PRODUCT_ID_ARGS, productId);
        ProductFilterFragment fragment = new ProductFilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductId = getArguments().getString(PRODUCT_ID_ARGS);
        mAttributesList = new ArrayList<>();
        mTermsAttributesList = new ArrayList<>();
        mFilteredAttributes = new ArrayList<>();

        getActivity().getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

    }

/*    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }*/

    public ProductFilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_filter, container, false);
        ButterKnife.bind(this, view);
        mProgressBar.setVisibility(View.VISIBLE);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.filter_action_bar);

        mAttributeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTermAttrRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        updateAttrUI();
        updateTermAttrUI();


        mDoFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new FilterProductMassage());
                getActivity().finish();
            }
        });

        if(mProductId.equalsIgnoreCase(CompleteProductListFragment.SHOULD_HANDLE_FILTER)){
            Attributes attributes = new Attributes(1, "رنگ","pa_color");
            mAttributesList.add(attributes);
            updateAttrUI();
            mProgressBar.setVisibility(View.GONE);
        }

        else {

        RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                .getProductAttributes(mProductId)
                .enqueue(new Callback<List<Attributes>>() {
                    @Override
                    public void onResponse(Call<List<Attributes>> call, Response<List<Attributes>> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mAttributesList = response.body();
                        updateAttrUI();
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<List<Attributes>> call, Throwable t) {
                        Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();
                    }
                });

        }


        return view;
    }

    private void updateAttrUI() {
        if (isAdded()) {
            if (mAttrAdapter == null) {

                mAttrAdapter = new AttrAdapter(mAttributesList);
                mAttributeRecyclerView.setAdapter(mAttrAdapter);

            } else {
                mAttrAdapter.setAttributesList(mAttributesList);
                mAttrAdapter.notifyDataSetChanged();

            }

        }
    }

    private void updateTermAttrUI() {

        if (isAdded()) {

            if (mTermAttrAdapter == null) {
                mTermAttrAdapter = new TermAttrAdapter(mTermsAttributesList);
                mTermAttrRecyclerView.setAdapter(mTermAttrAdapter);

            } else {
                mTermAttrAdapter.setAttributesTermsList(mTermsAttributesList);
                mTermAttrAdapter.notifyDataSetChanged();
            }

        }


    }

     class AttrViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.attribute_item_text_view)
        TextView mAttrNameTextView;

        Attributes mAttributes;

        public AttrViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

                    // Updating old as well as new positions
                    mAttrAdapter.notifyItemChanged(selected_position);
                    selected_position = getAdapterPosition();
                    mAttrAdapter.notifyItemChanged(selected_position);


                    mProgressBar.setVisibility(View.VISIBLE);

                    RetrofitClientInstance.getRetrofitInstance().create(Api.class)
                            .getAttributesTerms(String.valueOf(mAttributes.getId()))
                            .enqueue(new Callback<List<AttributesTerms>>() {
                                @Override
                                public void onResponse(Call<List<AttributesTerms>> call, Response<List<AttributesTerms>> response) {
                                    if (!response.isSuccessful()) {
                                        Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    mTermsAttributesList.clear();
                                    mTermsAttributesList = response.body();
                                    updateTermAttrUI();
                                    mProgressBar.setVisibility(View.GONE);

                                }

                                @Override
                                public void onFailure(Call<List<AttributesTerms>> call, Throwable t) {
                                    Toast.makeText(getActivity(), R.string.problem_response, Toast.LENGTH_SHORT).show();
                                }
                            });


                }
            });


        }

        public void bind(Attributes attributes) {
            mAttributes = attributes;
            mAttrNameTextView.setText(attributes.getName());

        }
    }

    private class AttrAdapter extends RecyclerView.Adapter<AttrViewHolder> {
        private List<Attributes> mAttributesList;

        public AttrAdapter(List<Attributes> attributesList) {
            mAttributesList = attributesList;
        }

        public void setAttributesList(List<Attributes> attributesList) {
            mAttributesList = attributesList;
        }

        @NonNull
        @Override
        public AttrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.filter_attribute_list_item, parent, false);
            return new AttrViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AttrViewHolder holder, int position) {
            holder.itemView.setBackgroundColor(selected_position == position ? Color.BLACK : Color.TRANSPARENT);
            Attributes attributes = mAttributesList.get(position);
            holder.bind(attributes);

        }

        @Override
        public int getItemCount() {
            return mAttributesList.size();
        }
    }

     class TermAttrViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.attribute_term_item_check_box)
        CheckBox mCheckBox;
        private AttributesTerms mAttributesTerms;

        public TermAttrViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if(mFilteredAttributes.size()>0&&mFilteredAttributes.contains(mAttributesTerms.getName())){
                mCheckBox.setChecked(true);
            }
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mFilteredAttributes.add(mAttributesTerms.getName());
                }else {
                    if(mFilteredAttributes.contains(mAttributesTerms.getName()))
                        mFilteredAttributes.remove(mAttributesTerms.getName());
                }

                }
            });

        }

        public void bind(AttributesTerms terms) {
            mAttributesTerms = terms;
            mCheckBox.setText(terms.getName());
        }
    }

    private class TermAttrAdapter extends RecyclerView.Adapter<TermAttrViewHolder> {
        private List<AttributesTerms> mAttributesTermsList;

        public TermAttrAdapter(List<AttributesTerms> attributesTermsList) {
            mAttributesTermsList = attributesTermsList;
        }

        public void setAttributesTermsList(List<AttributesTerms> attributesTermsList) {
            mAttributesTermsList = attributesTermsList;
        }

        @NonNull
        @Override
        public TermAttrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.filter_term_attribute_list_item, parent, false);
            return new TermAttrViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TermAttrViewHolder holder, int position) {
            AttributesTerms terms = mAttributesTermsList.get(position);
            holder.bind(terms);

        }

        @Override
        public int getItemCount() {
            return mAttributesTermsList.size();
        }
    }


}
