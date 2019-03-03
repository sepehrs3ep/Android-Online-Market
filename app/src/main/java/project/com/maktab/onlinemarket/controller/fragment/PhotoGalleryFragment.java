package project.com.maktab.onlinemarket.controller.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import project.com.maktab.onlinemarket.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoGalleryFragment extends VisibleFragment {
    private static final String IMAGE_PATH_ARGS = "imagePath";
    private static final String IMAGE_DRAWBLE_ARGS = "IMAGE_DRAWBLE_ARGS";
    private static final String IMAGE_IS_DRAWBLE_ARGS = "IMAGE_IS_DRAWBLE_ARGS";
    private String imagePath;
    private ImageView mImageView;
    private int mImageDrawble;
    private boolean isDrawble;


    public static PhotoGalleryFragment newInstance(String imagePath,Integer imageDraw,boolean isDrawble) {

        Bundle args = new Bundle();
        args.putString(IMAGE_PATH_ARGS,imagePath);
        args.putInt(IMAGE_DRAWBLE_ARGS,imageDraw);
        args.putBoolean(IMAGE_IS_DRAWBLE_ARGS,isDrawble);
        PhotoGalleryFragment fragment = new PhotoGalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public PhotoGalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePath = getArguments().getString(IMAGE_PATH_ARGS);
        mImageDrawble = getArguments().getInt(IMAGE_DRAWBLE_ARGS,-5);
        isDrawble = getArguments().getBoolean(IMAGE_IS_DRAWBLE_ARGS,false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        mImageView = view.findViewById(R.id.photo_gallery_image_view);
        if(isDrawble){
            Picasso.get().load(mImageDrawble)
                    .placeholder(R.drawable.shop)
                    .into(mImageView);
        }
        else
        Picasso.get().load(imagePath).into(mImageView);
        return view;
    }

}
