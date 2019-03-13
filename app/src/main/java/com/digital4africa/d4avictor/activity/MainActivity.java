package com.digital4africa.d4avictor.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.digital4africa.d4avictor.R;
import com.digital4africa.d4avictor.adapter.ListItemAdapter;
import com.digital4africa.d4avictor.api.ServiceGenerator;
import com.digital4africa.d4avictor.api.service.ApiClient;
import com.digital4africa.d4avictor.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<Post> postList = new ArrayList<>();
    private ListItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        fetchPosts();
    }

    private void fetchPosts() {
        ApiClient apiClient = ServiceGenerator.createService(ApiClient.class);

        Call<ResponseBody> call = apiClient.getPosts();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Rep", response.message());


                try {
                    JSONObject jsonObject =
                            new JSONObject(Objects.requireNonNull(response.body()).string());
                    String status = jsonObject.getString("status");

                    Toast.makeText(MainActivity.this, status, Toast.LENGTH_LONG).show();


                    JSONArray posts = jsonObject.getJSONArray("posts");
                    Toast.makeText(MainActivity.this, "" + posts.length(), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject postObject = posts.getJSONObject(i);

                        String title = postObject.getString("title");
                        String excerpt = postObject.getString("excerpt");
                        excerpt = Html.fromHtml(excerpt).toString().replaceAll("\n", "").trim();

                        String content = postObject.getString("content");
                        content = Html.fromHtml(content).toString().replaceAll("\n", "").trim();

                        String date = postObject.getString("modified");
                        String thumbnail = postObject.getString("thumbnail");

                        JSONObject author = postObject.getJSONObject("author");
                        String authorName = author.getString("first_name") + " " +
                                author.getString("last_name");


                        postList.add(new Post(title, excerpt, date, content, thumbnail, authorName));

                        Log.d("List Size", String.valueOf(postList.size()));

                        adapter = new ListItemAdapter(MainActivity.this, postList);
                        recyclerView.setAdapter(adapter);


                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
