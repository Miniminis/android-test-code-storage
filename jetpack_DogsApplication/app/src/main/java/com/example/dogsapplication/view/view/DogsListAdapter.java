package com.example.dogsapplication.view.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogsapplication.R;
import com.example.dogsapplication.databinding.ItemDogBinding;
import com.example.dogsapplication.view.model.DogBreed;
import com.example.dogsapplication.view.util.Util;


import java.util.ArrayList;
import java.util.List;

/* view adapter 생성 */
public class DogsListAdapter extends RecyclerView.Adapter<DogsListAdapter.DogViewHolder>
                            implements DogClickListener {

    /* 1. viewholder class 생성 + 생성자
     * 2. oncreateviewholder
     * 3. onbindviewholder
     * 4. itemcount
     * 5. adapter 관련 설정 */

    /* recyclerview 에 표시할 list data */
    private ArrayList<DogBreed> dogsList;

    public DogsListAdapter(ArrayList<DogBreed> list) {
        this.dogsList = list;
    }

    public void updateDogsList(List<DogBreed> newDogsList) {
        dogsList.clear();
        dogsList.addAll(newDogsList);
        notifyDataSetChanged();
    }

    /* viewholder 생성 */
    class DogViewHolder extends RecyclerView.ViewHolder {

        public ItemDogBinding itemView;

        public DogViewHolder(@NonNull ItemDogBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }
    }

    /* viewholder 생성될 때, view 전달 */
    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /* view holder 는 새로운 view 와 같이 생성되어야만 한다. 여기서는 xml 로부터 view를 불러와 사용.*/
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dog, parent, false);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemDogBinding view = DataBindingUtil.inflate(inflater, R.layout.item_dog, parent, false);
        return new DogViewHolder(view);
    }

    /* 각 item 들에 표시될 데이터 setting  */
    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        /* item 요소들 */
        holder.itemView.setDog(dogsList.get(position)); //Databinding 으로 화면 구성
        holder.itemView.setClickListener(this);
    }

    /* layout click listener 오버라이딩 */
    @Override
    public void onDogClicked(View view) {

        String dogId = ((TextView)view.findViewById(R.id.dogId)).getText().toString();
        int uuid = Integer.parseInt(dogId);
        ListFragmentDirections.ActionDetail action = ListFragmentDirections.actionDetail();
        action.setDogId(uuid);
        Navigation.findNavController(view).navigate(action);

    }


    /* 총 recycler adapter 의 list - 아이템 개수  */
    @Override
    public int getItemCount() {
        return dogsList == null ? 0 : dogsList.size();
    }
}
