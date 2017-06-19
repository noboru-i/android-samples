package hm.orz.chaos114.android.androidsamples;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hm.orz.chaos114.android.androidsamples.databinding.ActivityShareBinding;

public class ShareActivity extends AppCompatActivity {

    public static final String EMAIL = "mailto:";

    private static final List<String> sharePackages = Arrays.asList(
            "jp.naver.line.android",
            "com.facebook.katana",
            "com.twitter.android"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityShareBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_share);
        binding.setHandlers(this);
    }

    public void onClickShare(View view) {
        openShareDialog(this, "name", "https://google.co.jp", "extra");
    }

    public static void openShareDialog(@NonNull Activity activity, @NonNull String name, @NonNull String url, @NonNull String extraText) {
        List<Intent> targetedShareIntents = new ArrayList<>();
        ComponentName emailAPPs = getEmailAPPs(activity);
        if (emailAPPs != null) {
            // if email app is exists
            targetedShareIntents.add(MailShareActivity.getIntent(activity, name, url, extraText));
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        List<ResolveInfo> activities = getActivities(activity, intent);
        if (activities.size() == 0) {
            // Not have any share intent
            return;
        }

        for (ResolveInfo info : activities) {
            if (!sharePackages.contains(info.activityInfo.packageName)) {
                continue;
            }
            Log.d(ShareActivity.class.getSimpleName(), "info = " + info);

            String realExtraText = info.activityInfo.packageName.contains("twitter") ? extraText : "";
            Intent targeted;
            if (info.activityInfo.packageName.contains("facebook")) {
                targeted = new Intent(Intent.ACTION_SEND);
                targeted.setType("text/plain");
                targeted.setPackage(info.activityInfo.packageName);
                targeted.setClassName(info.activityInfo.packageName, info.activityInfo.name);
                targeted.putExtra(Intent.EXTRA_HTML_TEXT, url);
            } else {
                targeted = new Intent(Intent.ACTION_SEND);
                targeted.setType("text/plain");
                targeted.setPackage(info.activityInfo.packageName);
                targeted.setClassName(info.activityInfo.packageName, info.activityInfo.name);
            }
            targeted.putExtra(Intent.EXTRA_TEXT, String.format("%s %s %s", name, url, realExtraText));
            targetedShareIntents.add(targeted);
        }

        if (targetedShareIntents.isEmpty()) {
            // Not have any target share intent
            return;
        }

        Log.d(ShareActivity.class.getSimpleName(), "targetedShareIntents = " + targetedShareIntents);
        Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "share by other app");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));

        activity.startActivity(chooserIntent);
    }

    /**
     * get share activities
     *
     * @param activity activity
     * @param intent   share type intent
     * @return match the intent activities
     */
    public static List<ResolveInfo> getActivities(Activity activity, Intent intent) {
        PackageManager packageManager = activity.getPackageManager();
        return packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
    }

    /**
     * get the email apps
     *
     * @param activity activity
     * @return component name
     */
    public static ComponentName getEmailAPPs(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(EMAIL));
        return intent.resolveActivity(activity.getPackageManager());
    }
}
