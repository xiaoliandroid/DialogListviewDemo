package com.example.lgb.dialoglistviewdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private TextView tv;
    private String[] provinceString = {"江西省", "广东省", "深圳市"};
    private String[] cityString = {"南昌市", "九江市", "吉安市"};
    private String addressString = "";
    private ArrayAdapter<String> adapter;
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewByID();
        initView(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void findViewByID() {
        tv = (TextView) findViewById(R.id.tv1);
        bt = (Button) findViewById(R.id.bt1);
    }

    private void initView(final Context context) {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout linearLayoutMain = new LinearLayout(context);//自定义一个布局文件
                linearLayoutMain.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                final ListView listView = new ListView(context);//this为获取当前的上下文
                listView.setFadingEdgeLength(0);
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1, provinceString);

                listView.setAdapter(adapter);
                listView.setClickable(true);
                linearLayoutMain.addView(listView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);//往这个布局中加入listview

                final AlertDialog dialog = new AlertDialog.Builder(context)
                        .setView(linearLayoutMain)//在这里把写好的这个listview的布局加载dialog中
                        .create();
                dialog.setCanceledOnTouchOutside(false);//使除了dialog以外的地方不能被点击
                dialog.show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(context, provinceString[position], Toast.LENGTH_SHORT).show();
                        if (1 == position) {
                            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1, cityString);
                            addressString = provinceString[position];
                            listView.setAdapter(adapter);
                            linearLayoutMain.invalidate();
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    addressString = addressString + cityString[position];
                                    dialog.dismiss();
                                    Toast.makeText(context, addressString, Toast.LENGTH_SHORT).show();
                                    tv.setText(addressString);
                                }
                            });
                        }

                    }
                });


            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
