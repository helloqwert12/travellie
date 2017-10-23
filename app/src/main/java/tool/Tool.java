package tool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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

}
