package com.example.myexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView pathTextView= findViewById(R.id.textView);
        Button homeButton = findViewById(R.id.button);
        ListView fileListView = findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        fileListView.setAdapter(adapter);
        adapter.add("file1");
        adapter.add("file1");
        adapter.add("file1");
        adapter.add("file1");
        adapter.add("file1");
        adapter.notifyDataSetChanged();

        File rootDirectory = Environment.getExternalStorageDirectory(); //외부저장소 최상위파일
        String rootDirectoryPath = rootDirectory.getPath(); //최상위경로

        pathTextView.setText(rootDirectoryPath);

    }
}
/*    class FileItemAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }
*/