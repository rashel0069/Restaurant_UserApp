package com.digitalistic.co.restaurant_userapp.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalistic.co.restaurant_userapp.Apis.UserService;
import com.digitalistic.co.restaurant_userapp.Apis.model.ViewFeatured;
import com.digitalistic.co.restaurant_userapp.R;
import com.digitalistic.co.restaurant_userapp.ShowMenu;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    ProgressBar loading_progress;
    recyclerAdapter adapter;
    TextView restQr,tableQr;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        searchView = view.findViewById(R.id.search_view_id);

        recyclerView = view.findViewById(R.id.recyclerview_id);
        loading_progress = view.findViewById(R.id.load_progress_id);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(linearLayoutManager);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String searchTex = newText;
                adapter.getFilter().filter(searchTex);
                return false;
            }
        });

        LoadData();

        return view;
    }

    private void filterList(String text) {
        List<ViewFeatured.Datum> filteredList = new ArrayList<>();
        filteredList.clear();
        for (ViewFeatured.Datum datum : adapter.datumList){
            if (datum.getRestaurantName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(datum);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(getContext(), "No Restaurant Found", Toast.LENGTH_SHORT).show();
        }else {
            adapter = new recyclerAdapter(filteredList);
            recyclerView.setAdapter(adapter);

        }
    }

    private void LoadData(){
        loading_progress.setVisibility(getView().VISIBLE);
        Retrofit retrofitRequest = new Retrofit.Builder()
                .baseUrl(UserService.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final UserService request = retrofitRequest.create(UserService.class);

        Call<ViewFeatured> call = request.getFeaturedRes();
        call.enqueue(new Callback<ViewFeatured>() {
            @Override
            public void onResponse(Call<ViewFeatured> call, Response<ViewFeatured> response) {
                if (response.isSuccessful()){
                    Log.d("TAG", "onResponse: "+response.body().getMessage());
                    if (!response.body().getData().isEmpty()){
                        loading_progress.setVisibility(getView().GONE);
                        adapter = new recyclerAdapter(response.body().getData());
                        recyclerView.setAdapter(adapter);
                    }else {
                        loading_progress.setVisibility(getView().GONE);
                    }
                }else {
                    Toast.makeText(getContext(), "Failed!!!", Toast.LENGTH_SHORT).show();
                    loading_progress.setVisibility(getView().GONE);
                }
            }

            @Override
            public void onFailure(Call<ViewFeatured> call, Throwable t) {
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
                loading_progress.setVisibility(getView().GONE);
            }
        });

    }

    class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> implements Filterable {
        List<ViewFeatured.Datum> datumList;
        List<ViewFeatured.Datum> setfilteredList;
        public recyclerAdapter(List<ViewFeatured.Datum> datumList){
            this.datumList = datumList;
            this.setfilteredList = datumList;
            //notifyDataSetChanged();
        }


        @NonNull
        @Override
        public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featuredresview,null);
            recyclerAdapter.MyViewHolder viewHolder = new recyclerAdapter.MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

            holder.restaurant_name.setText(datumList.get(position).getRestaurantName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String res_Id = datumList.get(position).getRestaurantOwnerId();
                    String menu_Id = datumList.get(position).getId();
                    Intent intent = new Intent(getContext(), ShowMenu.class);
                    intent.putExtra("Res_ID",res_Id);
                    intent.putExtra("Menu_ID",menu_Id);

                    Toast.makeText(getContext(), ""+res_Id+"\n"+menu_Id, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return datumList.size();
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint == null || constraint.length() == 0){
                        filterResults.values = setfilteredList;
                        filterResults.count = setfilteredList.size();
                    }else {
                        String searchStr = constraint.toString();
                        List<ViewFeatured.Datum> viewList = new ArrayList<>();
                        for (ViewFeatured.Datum datum :setfilteredList){
                            if (datum.getRestaurantName().toLowerCase().contains(searchStr)){
                                viewList.add(datum);
                            }
                        }
                        filterResults.values = viewList;
                        filterResults.count = viewList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    datumList = (List<ViewFeatured.Datum>) results.values;
                    notifyDataSetChanged();
                }
            };

            return filter;
        }


        class MyViewHolder extends RecyclerView.ViewHolder{

            TextView restaurant_name;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                restaurant_name = itemView.findViewById(R.id.restaurant_name_id);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dexter.withContext(getContext())
                .withPermissions(Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
}
