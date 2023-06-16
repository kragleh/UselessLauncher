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

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        PackageManager pm = getPackageManager();
        List<ApplicationInfo> appList = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        LinearLayout containerLayout = findViewById(R.id.app_layout);

        for (ApplicationInfo appInfo : appList) {
            Button button = new Button(this);
            button.setText(appInfo.loadLabel(pm));
            button.setOnClickListener(view -> {
                List<String> apps = Paper.book().read("apps");

                if (apps == null) {
                    apps = new ArrayList<>();
                }

                apps.add(appInfo.packageName);
                Paper.book().write("apps", apps);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            });
            button.setBackgroundColor(Color.BLACK);
            button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            button.setTextColor(Color.WHITE);
            containerLayout.addView(button);
        }
    }

}