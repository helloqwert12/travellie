package tool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

/**
 * Created by tranminhquan on 10/22/2017.
 * Class chua cac method ho tro
 */

public class Tool {

    public static void changeActivity(Context a, Class b){
        Intent intent = new Intent(a, b);
        a.startActivity(intent);
    }

    public static void pushDataAndChangeActivity(Context a, Class b, Bundle data){
        Intent intent = new Intent(a, b);
        intent.putExtra("BUNDLE", data);
        a.startActivity(intent);
    }

    public static boolean isInternetAvailable(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
