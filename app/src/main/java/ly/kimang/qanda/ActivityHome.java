package ly.kimang.qanda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;

import score.scores;

public class ActivityHome extends AppCompatActivity {
    private Function function = new Function();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_grade:
                function.openActivity(this,ActivityScore.class);
                break;
            case R.id.menu_sitting:
                Toast.makeText(this, "Version 1.0 released", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void intent(String dataName, String subject){
        final String data = function.readFileAsset(this, dataName);
        final HashMap<String,String> map = new HashMap<>();
        map.put("data",data);
        map.put("sub",subject);
        function.openActivityForResult(this,ActivityQs.class,map,1);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_math:
                intent("data_math.json","M");
                break;
            case R.id.btn_phy:
                intent("data_phy.json","P");
                break;
            case R.id.btn_bio:
                intent("data_bio.json","B");
                break;
            case R.id.btn_eng:
                intent("data_eng.json","E");
                break;
        }
    }
}
