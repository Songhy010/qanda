package ly.kimang.qanda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

import score.scores;

public class ActivityQs extends AppCompatActivity {

    private Function function = new Function();
    private JSONArray array;
    private RecyclerView recycler;
    private TextView tv_qs;
    private String sub;
    int numQs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qs);
        //initActionBar();
        try {
            final HashMap<String, String> map = function.getIntentHashMap(getIntent());
            sub = map.get("sub");
            setScore(0);
            array = new JSONArray(map.get("data"));
            initView();
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        }
    }

    private void setScore(int score) {
        switch (sub) {
            case "M":
                scores.getInstance().setMath(score);
                break;
            case "P":
                scores.getInstance().setPhy(score);
                break;
            case "B":
                scores.getInstance().setBio(score);
                break;
            case "E":
                scores.getInstance().setEng(score);
                break;
        }
    }

    private void findView() {
        tv_qs = findViewById(R.id.tv_qs);
        recycler = findViewById(R.id.recycler);
    }

    private void initView() {
        try {
            findView();
            final JSONObject object = array.getJSONObject(numQs);
            initQs(object);
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }

        //initRecycler(array);
    }


    private void initQs(JSONObject object) {
        try {
            final ProgressBar progress = findViewById(R.id.progress);
            final RadioButton[] button = {findViewById(R.id.rb_1), findViewById(R.id.rb_2), findViewById(R.id.rb_3)};
            tv_qs.setText(object.getString("qs"));
            final JSONArray ar = object.getJSONArray("ans");
            for (int i = 0; i < ar.length(); i++) {
                final JSONObject obj = ar.getJSONObject(i);
                button[i].setChecked(false);
                button[i].setEnabled(true);
                button[i].setText(obj.getString("desc"));
                final int finalI = i;
                button[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            button[2].setEnabled(false);
                            button[1].setEnabled(false);
                            button[0].setEnabled(false);
                            progress.setVisibility(View.VISIBLE);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        final TextView tv_result = findViewById(R.id.tv_result);
                                        if (obj.getString("status").equals("true")) {
                                            numQs += 1;
                                            final JSONObject object = array.getJSONObject(numQs);
                                            tv_result.setText("Corrected");
                                            tv_result.setTextColor(getResources().getColor(R.color.colorPrimary));
                                            tv_result.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    initQs(object);
                                                    tv_result.setText("");
                                                }
                                            });

                                        } else {
                                            for (int i = 0; i < ar.length(); i++) {
                                                final JSONObject obj = ar.getJSONObject(i);
                                                if (obj.getString("status").equals("true")) {
                                                    tv_result.setTextColor(getResources().getColor(R.color.colorAccent));
                                                    tv_result.setText(String.format("You lose!!! Answer is %s", obj.getString("desc")));
                                                    tv_result.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            finish();
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                        progress.setVisibility(View.GONE);
                                    } catch (Exception e) {
                                        Log.e("Err", e.getMessage() + "");
                                    }
                                }
                            }, 5 * 1000);
                        } catch (Exception e) {
                            Log.e("Err", e.getMessage() + "");
                        }
                    }
                });
            }

        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void initRecycler(JSONArray array) {
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(new QAAdapter(this, array));
    }

    public class QAAdapter extends RecyclerView.Adapter<QAAdapter.ItemHolder> {

        private Context context;
        private JSONArray array;
        private int count = 0;
        private int score = 0;

        public QAAdapter(Context context, JSONArray array) {
            this.context = context;
            this.array = array;
        }

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ItemHolder holder, final int position) {
            try {
                RadioButton[] button = {holder.rb_1, holder.rb_2, holder.rb_3};
                JSONObject object = (JSONObject) array.get(position);

                holder.tv.setText((position + 1) + " ." + object.getString("qs"));
                final JSONArray arr = object.getJSONArray("ans");
                for (int j = 0; j < arr.length(); j++) {

                    final JSONObject obj = arr.getJSONObject(j);
                    button[j].setText(obj.getString("desc"));
                    button[j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            count = count + 1;
                            holder.rb_1.setEnabled(false);
                            holder.rb_2.setEnabled(false);
                            holder.rb_3.setEnabled(false);
                            try {
                                if (obj.getString("status").equals("true")) {
                                    holder.tv_ans.setVisibility(View.VISIBLE);
                                    holder.tv_ans.setText("10");
                                    score = score + 10;
                                } else {
                                    holder.tv_ans.setVisibility(View.VISIBLE);
                                    holder.tv_ans.setText("00");
                                    holder.tv_ans.setTextColor(getResources().getColor(R.color.colorAccent));
                                }

                                if (count == array.length()) {
                                    setScore(score);
                                    Toast.makeText(context, "Thank you please check you score in grade menu", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } catch (Exception e) {
                                Log.e("Err", e.getMessage());
                            }
                        }
                    });
                }
            } catch (Exception e) {
                Log.e("Err", e.getMessage());
            }
        }

        @Override
        public int getItemCount() {
            return array.length();
        }

        public class ItemHolder extends RecyclerView.ViewHolder {
            private RadioGroup rg;
            private RadioButton rb_1;
            private RadioButton rb_2;
            private RadioButton rb_3;
            private TextView tv, tv_ans;

            public ItemHolder(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
                rg = itemView.findViewById(R.id.rg);
                rb_1 = itemView.findViewById(R.id.rb_1);
                rb_2 = itemView.findViewById(R.id.rb_2);
                rb_3 = itemView.findViewById(R.id.rb_3);
                tv_ans = itemView.findViewById(R.id.tv_ans);
            }
        }
    }
}
