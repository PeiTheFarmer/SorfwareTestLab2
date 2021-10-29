package com.example.searchview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private ListView mLv;
    private SearchView mSearchView;
//    private String[] data = {"Apple","Banana","Orange","Watermelon","Pear","Grape","Pineapple","Strawberry","Cherry","Mango",
//            "Apple","Banana","Orange","Watermelon","Pear","Grape","Pineapple","Strawberry","Cherry","Mango"};
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View view1 = inflater.inflate(R.layout.activity_main, null);
//        ListView listView = view1.findViewById(R.id.list_view);
//        Log.e("FUDAN_PCX", listView == null ? "read failed" : "read success");
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                MainActivity.this,android.R.layout.simple_list_item_1,data);
////        ListView listView= (ListView)findViewById(R.id.list_view);
//        listView.setAdapter(adapter);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view1 = inflater.inflate(R.layout.activity_main, null);

        mLv = view1.findViewById(R.id.list_view);

        //通过MenuItem得到SearchView
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setIconified(false);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.onActionViewExpanded();
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Please input something");
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(getApplicationContext(), "Close", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Open", Toast.LENGTH_SHORT).show();
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //Log.e("Fudan_PCX", "TextSubmit : " + s);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                //Log.e("Fudan_PCX", "TextChange--> " + s);
                Cursor cursor = TextUtils.isEmpty(s) ? null : queryData(s);
                if (mSearchView.getSuggestionsAdapter() == null) {
                    mSearchView.setSuggestionsAdapter(new SimpleCursorAdapter(MainActivity.this, R.layout.item_layout, cursor, new String[]{"name"}, new int[]{R.id.text1}));
                }
                else {
                    mSearchView.getSuggestionsAdapter().changeCursor(cursor);
                }
//                if (mLv.getAdapter() == null) {
//                    SimpleCursorAdapter adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.item_layout, cursor, new String[]{"name"}, new int[]{R.id.text1});
//                    mLv.setAdapter(adapter);
//                } else {
//                    ((SimpleCursorAdapter) mLv.getAdapter()).changeCursor(cursor);
//                }

                return false;
            }
        });

//        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String x) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });
        return super.onCreateOptionsMenu(menu);
    }

//    private void setAdapter(Cursor cursor) {
//        if (mLv.getAdapter() == null) {
//            Si
//        }
//    }

//    @Override
//    public boolean onMenuOpened(int featureId, Menu menu) {
//        if (menu != null) {
//            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
//                try {
//                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
//                    method.setAccessible(true);
//                    method.invoke(menu, true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return super.onMenuOpened(featureId, menu);
//    }

    private Cursor queryData(String key) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir() + "fudan.db", null);
        Cursor cursor = null;
        try {
            String createSql = "create table fudan_lab2 (_id integer primary key autoincrement, name varchar(100))";
            //db.execSQL(createSql);
            String insertSql;
            //Log.e("FUDAN_PCX", "createQuery = " + createSql);
//            for (int i = 0; i < 100; i++) {
//                insertSql = "insert into fudan_lab2 values (null, 'row: " + String.valueOf(i) + "')";
//                db.execSQL(insertSql);
////                Log.e("FUDAN_PCX", "insertQUery" + insertSql);
//            }
            String querySql = "select * from fudan_lab2 where name like '%" + key +"%'";
            cursor = db.rawQuery(querySql, null);
            Log.e("FUDAN_PCX", String.valueOf(cursor.getColumnCount()));
            Log.e("FUDAN_PCX", "querySql = " + querySql);
        } catch (Exception e) {
            e.printStackTrace();

            String querySql = "select * from fudan_lab2 where name like '%" + key + "%'";
            cursor = db.rawQuery(querySql, null);

            Log.e("FUDAN_PCX", "querySql = " + querySql);
        }
        return cursor;
    }
}