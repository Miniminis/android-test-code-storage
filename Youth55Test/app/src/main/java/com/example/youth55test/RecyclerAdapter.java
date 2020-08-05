package com.example.youth55test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    Context context;
    List<LoanData> list;

    //1. 생성자
    public RecyclerAdapter(Context context, List<LoanData> list) {
        this.context = context;
        this.list = list;
    }

    //2. 통신 후 반응 데이터 넣어줄 setter
    public void setList(List<LoanData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //3. 반복처리를 위한 view holder 정의 : row_content 내부에 있는 각 요소들 캐스팅
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView TextView_loanSubject;
        TextView TextView_businCorpName;
        TextView TextView_socialName;
        TextView TextView_loanMoney;
        TextView TextView_loanCondition;
        TextView TextView_investProgress;
        TextView TextView_grade;

        public MyViewHolder(View itemView) {
            super(itemView);
            TextView_loanSubject = itemView.findViewById(R.id.TextView_loanSubject);
            TextView_businCorpName = itemView.findViewById(R.id.TextView_businCorpName);
            TextView_socialName = itemView.findViewById(R.id.TextView_socialName);
            TextView_loanMoney = itemView.findViewById(R.id.TextView_loanMoney);
            TextView_loanCondition = itemView.findViewById(R.id.TextView_loanCondition);
            TextView_investProgress = itemView.findViewById(R.id.TextView_investProgress);
            TextView_grade = itemView.findViewById(R.id.TextView_grade);
        }
    }

    //4. View Holder 생성
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_content, parent, false);
        return new MyViewHolder(view);
    }

    //5. 반복되는 뷰 생성
    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {

        //내용
        holder.TextView_loanSubject.setText(list.get(position).getLoanSubject());

        //투자기업;. bn
        holder.TextView_businCorpName.setText(list.get(position).getBusinCorpName()+"매칭");
        //사회적기업
        if(list.get(position).getSocialName().equals("")) {
            holder.TextView_socialName.setText("추천없음");
        } else {
            holder.TextView_socialName.setText(list.get(position).getSocialName()+"추천");
        }
        //투자금액
        holder.TextView_loanMoney.setText(new DecimalFormat("###,###").format(list.get(position).getLoanMoney()) +"원");
        //투자조건
        String gender = "";
        if(list.get(position).getGender().equals("W")) {
            gender = "여성";
        } else if(list.get(position).getGender().equals("M")){
            gender = "남성";
        } else {
            gender = "청년";
        }
        holder.TextView_loanCondition.setText("* 대출조건 : "+list.get(position).getRepayTerm() + "개월/"+ list.get(position).getRepayWay() + "/"+list.get(position).getAge()+"대 "+gender);
        holder.TextView_investProgress.setText(list.get(position).getInvestProgress()+"%");
        holder.TextView_grade.setText(list.get(position).getGrade());

        //이미지 넣는 리스트라면 Glide 설정 필요
        //Glide.with(context).load(list.get(position).getImageUrl()).apply(RequestOptions.centerCropTransform()).into(holder.image);
    }

    //6. 리스트(recycler view)에 표시할 아이템이 없을 경우에 대한 예외처리
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
