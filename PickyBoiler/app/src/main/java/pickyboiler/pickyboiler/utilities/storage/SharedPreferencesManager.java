package pickyboiler.pickyboiler.Utilities.Storage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pickyboiler.pickyboiler.R;

public class SharedPreferencesManager extends Application{
    private static SharedPreferences sharedPreferences;
    private static String PREF_NAME = "prefs";
    private static Context context;
    public static Toast toastShow;
    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        context = getApplicationContext();
        toastShow = null;
    }
    // Code for toast singleTron
    public static void showToast(String text) {

        if (toastShow == null || toastShow.getView().getWindowVisibility() != View.VISIBLE) {
            toastShow = Toast.makeText(SharedPreferencesManager.getContext(), text, Toast.LENGTH_SHORT);
            toastShow.show();
            Log.v("Toasty", "== null or it is not visible");
            return;
        }
        if (toastShow != null) {
            toastShow.setText(text);
            toastShow.show();
            Log.v("Toasty", "!= null");
        }
    }

    public static SharedPreferences getPrefs() {

        return sharedPreferences;
    }

    public static Context getContext() {
        return context;
    }
    public static void putStringSharedPreferences(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value).apply();
    }

    public static String getValueFromKey(Context context, String key) {
        return sharedPreferences.getString(key, null);
    }

    public static void addOrAppendStringToSharedPreferences(Context context, String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String oldValue = sharedPreferences.getString(key, null);

        if(oldValue == null) {
            editor.putString(key, value).apply();
        }
        else {
            editor.putString(key, oldValue + ',' + value).apply();
        }
    }

    public static void addFavoriteItem(Context context, String item) {
        addOrAppendStringToSharedPreferences(context, context.getResources().getString(R.string.favoriteFood), item);
    }
    public static void removeFavoriteItem(String itemToRemoved) {
        ArrayList<String> favoriteList = getAllFavoriteItem();
        if(favoriteList.size() == 0) {
            return;
        }
        String newList = "";
        for (String iterator: favoriteList) {
            Log.d("ITERAATORRRR", ">>" + iterator);
            if(!iterator.equals(itemToRemoved))
                newList += iterator + ',';
        }
        if(newList.length() > 0)
            newList = newList.substring(0, newList.length()-1);

        Log.d("REMOVEFAV_fofygg", "old: " + sharedPreferences.getString(context.getResources().getString(R.string.favoriteFood), null) + " new: " + newList);
        putStringSharedPreferences(context.getResources().getString(R.string.favoriteFood), newList);
    }

    public static ArrayList<String> getAllFavoriteItem() {
        String favorite = sharedPreferences.getString(context.getResources().getString(R.string.favoriteFood), null);
        if(favorite == null) {
            return new ArrayList<String>();
        }
        favorite = favorite.trim();


        return new ArrayList<String>(Arrays.asList(favorite.trim().split(",")));
    }
    public static ArrayList<String> getPrefFavListtFofy() {
        String favorite = sharedPreferences.getString(context.getResources().getString(R.string.favoriteFood), null);
        if(favorite == null || favorite.trim().equals("")) {
            return new ArrayList<String>();
        }
        favorite = favorite.trim();
        if(favorite.length() >= 2) {
            // favorite = favorite.substring(1);
        }
        Log.d("fromHIGH_fofygg", favorite);
        return new ArrayList<String>(Arrays.asList(favorite.trim().split(",")));
    }
    public static boolean isVeggie() {
        return sharedPreferences.getBoolean(context.getResources().getString(R.string.isVeggie), false);
    }

    public static ArrayList<String> getAllAllergens() {
        String[] allergensKey = {context.getResources().getString(R.string.AllergenEgg), context.getResources().getString(R.string.AllergenFish), context.getResources().getString(R.string.AllergenGluten)
                , context.getResources().getString(R.string.AllergenMilk), context.getResources().getString(R.string.AllergenNut), context.getResources().getString(R.string.AllergenPeanut)
                , context.getResources().getString(R.string.AllergenShellfish), context.getResources().getString(R.string.AllergenSoy), context.getResources().getString(R.string.AllergenWheat)};

        ArrayList<String> allergensList = new ArrayList<>();
        for (String allergenKey : allergensKey) {
            if(getValueFromKey(context, allergenKey) != null && getValueFromKey(context, allergenKey).equals("true")) {
                allergensList.add(allergenKey);
            }
        }

        return allergensList;
    }
}
