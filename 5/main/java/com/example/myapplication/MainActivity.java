package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) { switch ( item.getItemId() ) {
        case R.id.menu1:
            Intent intent = new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent);
            return true;
        case R.id.menu2:
            Intent intent2 = new Intent(MainActivity.this,Main3Activity.class);
            startActivity(intent2);
            return true;
        default:
            return false;
    }
    }

}

