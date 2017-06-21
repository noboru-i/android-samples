package hm.orz.chaos114.android.androidsamples;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class MailShareActivity extends AppCompatActivity {

    private static final String SHARE_NAME = "name";
    private static final String SHARE_URL = "url";
    private static final String SHARE_EXTRA_TEXT = "extraText";

    public static Intent getIntent(Context context, String name, String url, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.setPackage(context.getPackageName());
        intent.setClass(context, MailShareActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(SHARE_NAME, name);
        bundle.putString(SHARE_URL, url);
        bundle.putString(SHARE_EXTRA_TEXT, extraText);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Bundle extras = getIntent().getExtras();
        openShareDialog(extras.getString(SHARE_NAME), extras.getString(SHARE_URL),
                extras.getString(SHARE_EXTRA_TEXT));
    }

    public void openShareDialog(@NonNull String name, @NonNull String url, @NonNull String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        if (activities.size() == 0) {
            finish();
        }

        Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
        mailIntent.setType("*/*");
        mailIntent.putExtra(Intent.EXTRA_EMAIL, "");
        mailIntent.putExtra(Intent.EXTRA_TEXT, "text");
        mailIntent.setData(Uri.parse("mailto:"));
        mailIntent.putExtra(Intent.EXTRA_TEXT, String.format("%s %s %s", name, url, extraText));
        Intent chooserIntent = Intent.createChooser(mailIntent, "mail");
        startActivity(chooserIntent);
        this.finish();
    }
}
