package com.example.covid_19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class myadopterclass extends ArrayAdapter<countrymodal> {
    private Context context;
    private List<countrymodal> countrymodalList;
    private List<countrymodal> countryModelsListFiltered;


    public myadopterclass( Context context, List<countrymodal> countrymodalList) {
        super(context, R.layout.list_custom_item,countrymodalList);

        this.context=context;
        this.countrymodalList=countrymodalList;
        this.countryModelsListFiltered = countrymodalList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item,null,true);
        TextView tvCountryName = view.findViewById(R.id.tvCountryName);
        ImageView imageView = view.findViewById(R.id.imageFlag);

        tvCountryName.setText(countryModelsListFiltered.get(position).getCountry());
        Glide.with(context).load(countryModelsListFiltered.get(position).getFlag()).into(imageView);

        return view;

    }

    @Override
    public int getCount() {
        return countryModelsListFiltered.size();
    }

    @Nullable
    @Override
    public countrymodal getItem(int position) {
        return countryModelsListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = countrymodalList.size();
                    filterResults.values = countrymodalList;

                }else{
                    List<countrymodal> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(countrymodal itemsModel:countrymodalList){
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                countryModelsListFiltered = (List<countrymodal>) results.values;
                affectedCountries.countryModelsList = (List<countrymodal>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}
