package com.example.myexplorer;

import static android.os.Build.VERSION.SDK_INT;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private boolean readPermissionGranted = false;
    private boolean writePermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView pathTextView= findViewById(R.id.textView);
        Button homeButton = findViewById(R.id.button);
        ListView fileListView = findViewById(R.id.fileListView);


        //1 외부저장소 최상위 경로를 가져옴
        File root = Environment.getExternalStorageDirectory(); //외부저장소 최상위파일
        String rootDirectoryPath = root.getPath(); //최상위경로

        File file = new File(root.toString());

        if(file.exists()){
            System.out.println("That file exists! :o");
            System.out.println(file.getPath());
            System.out.println(file.getAbsolutePath());
            System.out.println(file.isFile());
            System.out.println(file.isDirectory());
        }else{
            System.out.println("That file doesn't exist :(");
        }

        //2 현재 경로 표시
        pathTextView.setText(rootDirectoryPath);
        //3 현재 경로에 존재하는 모든 파일들을 리스트 데이터로 담음
        //4 ListView에 리스트 데이터 파일들을 표시
        //5 ListView 중 item이 클릭되면
        //6 Toast & 선택된 item이 디렉토리면 2부터 반복. 화면갱신
       List<String> data = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);

        fileListView.setAdapter(adapter);
        adapter.add("file1");
        adapter.add("file1");
        adapter.add("file1");
        adapter.add("file1");
        adapter.add("file1");
        data.add("file2");
        adapter.notifyDataSetChanged();

        fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapter.getItem(i);
                Toast.makeText(getApplicationContext(),"선택:"+text,Toast.LENGTH_SHORT).show();
            }
        });


        checkPermissions();

//        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//        startActivity(intent);

    }

    private void checkPermissions(){
        //권한 보유 여부를 확인하는 코드. 앱에 권한이 있는경우
        if (SDK_INT >= Build.VERSION_CODES.R) {
            if(Environment.isExternalStorageManager()){
                Toast.makeText(this,"파일 액세스 권한 주어져져 있음", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"파일 엑세스 권한 없음.",Toast.LENGTH_SHORT).show();
            }
        }

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