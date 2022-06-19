package com.example.ygm.Category;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ygm.R;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<CategoryItem> listItemCategory = new ArrayList<>();

    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        Context context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_category, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listItemCategory.get(position), position);
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listItemCategory.size();
    }

    public void addItem(CategoryItem ItemCategory) {
        // 외부에서 item을 추가시킬 함수입니다.
        listItemCategory.add(ItemCategory);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivTitle;
        private TextView tvTitle_kor;
        private TextView tvTitle_eng;
        private TextView[] tvSub = new TextView[5];
        private CategoryItem item;
        private int position;

        ItemViewHolder(View itemView) {
            super(itemView);
            ivTitle = itemView.findViewById(R.id.image_list_category);
            tvTitle_kor = itemView.findViewById(R.id.category_kor_list_category);
            tvTitle_eng = itemView.findViewById(R.id.category_eng_list_category);
            tvSub[0] = itemView.findViewById(R.id.item1_list_category);
            tvSub[1] = itemView.findViewById(R.id.item2_list_category);
            tvSub[2] = itemView.findViewById(R.id.item3_list_category);
            tvSub[3] = itemView.findViewById(R.id.item4_list_category);
            tvSub[4] = itemView.findViewById(R.id.item5_list_category);
        }

        void onBind(CategoryItem item, int position) {
            this.item = item;
            this.position = position;

            Glide.with(itemView).load(item.getImg())
                    .fitCenter()
                    .override(Target.SIZE_ORIGINAL) //보고 제일 큰 값보다 조금더 큰값으로 넣자
                    .into(ivTitle);
            tvTitle_kor.setText(item.getTitle_kor());
            tvTitle_eng.setText(item.getTitle_eng());
            tvSub[0].setText(item.getSub1());
            tvSub[1].setText(item.getSub2());
            tvSub[2].setText(item.getSub3());
            tvSub[3].setText(item.getSub4());
            tvSub[4].setText(item.getSub5());

            changeVisibility(selectedItems.get(position));

            itemView.setOnClickListener(this);
            tvTitle_kor.setOnClickListener(this);
            tvTitle_eng.setOnClickListener(this);
            tvSub[0].setOnClickListener(this);
            tvSub[1].setOnClickListener(this);
            tvSub[2].setOnClickListener(this);
            tvSub[3].setOnClickListener(this);
            tvSub[4].setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.category_kor_list_category:
                case R.id.category_eng_list_category:
                case R.id.image_list_category:
                case R.id.list_category:
                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                    } else {
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                        Log.d("position", String.valueOf(position));
                    }
                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
                    prePosition = position;
                    break;
                case R.id.item1_list_category:
                    if(position==0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "100");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }else if(position==1){
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "200");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }else if(position==2){
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "300");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }else{
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "400");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }
                    break;
                case R.id.item2_list_category:
                    if(position==0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "101");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }else if(position==1){
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "201");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }else if(position==2){
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "301");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }else{
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "401");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }
                    break;
                case R.id.item3_list_category:
                    if(position==0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "102");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }else if(position==1){
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "202");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }else if(position==2){
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "302");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }else{
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "402");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }
                    break;
                case R.id.item4_list_category:
                    if(position==0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "103");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }else if(position==1){
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "203");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }else if(position==2){
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "303");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }else{
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "403");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }
                    break;
                case R.id.item5_list_category:
                    if(position==0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "104");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }else if(position==1){
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "204");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }else if(position==2){
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "304");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }else{
                        Bundle bundle = new Bundle();
                        bundle.putString("Name", "404");
                        Navigation.findNavController(v).navigate(R.id.action_categoryFragment_to_productListFragment,bundle);
                    }
            }
        }

        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            tvSub[0].setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            tvSub[1].setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            tvSub[2].setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            tvSub[3].setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            tvSub[4].setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        }
    }
}