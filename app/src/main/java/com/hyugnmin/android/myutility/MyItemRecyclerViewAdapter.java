package com.hyugnmin.android.myutility;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.List;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final Context context;
    private List<String> datas;

    public MyItemRecyclerViewAdapter(Context context, List<String> datas) {

        this.context = context;
        this.datas = datas;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d("viewhodler", "viewhodler---------------------------------------" + holder);
        holder.imageUri = datas.get(position);
        //holder.imageViewGallery.setImageURI(Uri.parse(holder.imageUri));
        Glide.with(context).load(holder.imageUri).into(holder.imageViewGallery);
    }

        @Override
        public int getItemCount () {
            return datas.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageViewGallery;
            public String imageUri;
            int position;

            public ViewHolder(View view) {
                super(view);

                imageViewGallery = (ImageView) view.findViewById(R.id.imageViewGallery);
                imageUri = null;

                imageViewGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    //클릭시 큰 이미지 보여주기
//                    Intent intent = new Intent (context, ZoomIn.class);
//                    intent.putExtra("position", datas.get(position));
//                    context.startActivity(intent);
                    }
                });
            }
        }
    }

