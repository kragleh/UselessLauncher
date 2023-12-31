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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);

        // Register add button
        Button addButton = findViewById(R.id.add);
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        });

        // Register remove button
        Button removeButton = findViewById(R.id.remove);
        removeButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, RemoveActivity.class);
            startActivity(intent);
        });

        // Get app list to display on home page
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
                button.setOnClickListener(view -> launchApp(info.packageName));
                containerLayout.addView(button);
            } catch (Exception ignored) { }
        }
    }

    private void launchApp(String packageName) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntent != null) {
            startActivity(launchIntent);
        } else {
            Toast.makeText(this, "App not found", Toast.LENGTH_SHORT).show();
        }
    }

}