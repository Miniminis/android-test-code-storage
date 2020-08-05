package com.example.androidtestproject.newsapplication;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtestproject.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<NewsData> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    //3. 넘겨받은 요소들 캐스팅
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewTitle;
        public TextView mTextViewContent;
        //public ImageView mImageView;
        public SimpleDraweeView mImageView;

        // each data item is just a string in this case
        public MyViewHolder(View v) {
            super(v);

            mTextViewTitle = v.findViewById(R.id.mTextViewTitle);
            mTextViewContent = v.findViewById(R.id.mTextViewContent);
            //mImageView = v.findViewById(R.id.mImageView);
            mImageView = (SimpleDraweeView) v.findViewById(R.id.mImageView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    // 1. NewsActivity 에서 생성자 통해서 서버 데이터 전달받기
    public MyAdapter(List<NewsData> myDataset, Context context) {

        //{"첫번째 기사!", "두번째 기사!!!!"}
        mDataset = myDataset; //서버에서 데이터 세트 넘겨받음
        Log.d("MyAdapter 에서 파라미터 값", myDataset.toString());

        //이미지 표시를 위한 fresco initialize
        Fresco.initialize(context);
    }

    // Create new views (invoked by the layout manager)
    // 2. 가져온 데이터를 넣어줄 요소들 가져오기 + MyViewHolder 통해서 전달
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        //2-1. row_news 에 있는 요소들 캐스팅
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_news, parent, false);

        //2-2. MyViewHolder로 넘겨주기
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    // 4. 반복되는 데이터 표시
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //gson 등의 데이터 변환 라이브러리 필요 없음 ! - volley

        //반복되는 데이터 표시  mDataset 에서 position 에 있는 값을 newsdata 로 가져와 화면에 주입
        NewsData newsdata = mDataset.get(position);

        //제목 주입
        holder.mTextViewTitle.setText(newsdata.getTitle());

        //콘텐츠 주입
        String mContent = "내용이 없습니다!";
        if(newsdata.getContent() != null) {
            holder.mTextViewContent.setText(newsdata.getContent());
        } else {
            holder.mTextViewContent.setText(mContent);
        }

        //이미지 주입
        Uri uri = Uri.parse(newsdata.getUrlToImage());
        holder.mImageView.setImageURI(uri);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //mDataset : null 값 예방
        return mDataset == null ? 0: mDataset.size();
    }
}
