package beauty.useless.uselesslauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class RemoveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove);

        List<String> apps = Paper.book().read("apps");
        LinearLayout containerLayout = findViewById(R.id.app_layout);
        PackageManager pm = getPackageManager();

        if (apps == null) {
            apps = new ArrayList<>();
            Paper.book().write("apps", apps);
            return;
        }

        for (String app : apps) {
            try {
                ApplicationInfo info = pm.getApplicationInfo(app, PackageManager.GET_META_DATA);
                Button button = new Button(this);
                button.setBackgroundColor(Color.BLACK);
                button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                button.setTextColor(Color.WHITE);
                button.setText(info.loadLabel(pm));
                List<String> finalApps = apps;
                button.setOnClickListener(view -> {
                    finalApps.remove(app);
                    Paper.book().write("apps", finalApps);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                });
                containerLayout.addView(button);
            } catch (Exception ignored) { }
        }
    }

}