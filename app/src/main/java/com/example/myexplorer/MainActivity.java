package com.example.myexplorer;

import static android.os.Build.VERSION.SDK_INT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private boolean readPermissionGranted = false;
    private boolean writePermissionGranted = false;

    VideoView videoView;

    public static String url = "https://file-examples-com.github.io/uploads/2017/04/file_example_MP4_480_1_5MG.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView pathTextView = findViewById(R.id.textView);
        Button homeButton = findViewById(R.id.button);
        ListView fileListView = findViewById(R.id.fileListView);

        loadStorage(pathTextView, fileListView);

        MediaController controller = new MediaController(this);//재생,일시중지같은 버튼이 들어가있는것
        videoView = findViewById(R.id.videoView);
        videoView.setMediaController(controller);
        videoView.setVideoURI(Uri.parse(url));//동영상 파일이 있는 위치를 videoView가 확인할 수 있게 됨
        videoView.requestFocus();//파일을 갖고오게 됨

        //동영상 준비과정이 끝났는지 확인
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //준비가 끝나면 실행
                Toast.makeText(getApplicationContext(), "동영상 준비됨", Toast.LENGTH_LONG).show();
            }
        });

        Button startButton = findViewById(R.id.videoViewStartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.seekTo(0);//동영상 처음위치로
                videoView.start();
            }
        });

    }

    private void loadStorage(TextView pathTextView, ListView fileListView) {
        File[] subFiles = null; //외부 저장소에 존재하는 파일들을 저장

        //1 외부저장소 최상위 경로를 가져옴
        File root = Environment.getExternalStorageDirectory(); //외부저장소 최상위파일 : /storage/emulated/0
        String rootDirectoryPath = root.getPath(); //최상위경로

        File file = new File(root.toString());

        //2 현재 경로 표시
        pathTextView.setText(rootDirectoryPath);
        //3 현재 경로에 존재하는 모든 파일들을 리스트 데이터로 담음
        if (file.exists()) {
            System.out.println("That file exists! :o");
            System.out.println(file.getPath());
            System.out.println(file.getAbsolutePath());
            System.out.println(file.isFile());
            System.out.println(file.isDirectory());

            if (file.isDirectory()) {
                String filePath = file + "/"; ///storage/emulated/0/
                System.out.println(filePath);
                File clickedDirectory = new File(filePath);
                System.out.println(clickedDirectory.isDirectory());
                System.out.println(clickedDirectory.list());
                System.out.println(clickedDirectory.listFiles().length);
                subFiles = clickedDirectory.listFiles(); // storage/emulated/0/ 에 존재하는 파일들 저장
                for (int i = 0; i < subFiles.length; i++) {
                    System.out.println(subFiles[i].getAbsolutePath());
                }
            }
        } else {
            System.out.println("That file doesn't exist :(");
        }
        //4 ListView에 리스트 데이터 파일들을 표시
        FileItemAdapter adapter = new FileItemAdapter();
        fileListView.setAdapter(adapter);

        for (int i = 0; i < subFiles.length; i++) {
            adapter.addItem(new File(subFiles[i].getAbsolutePath())); ///storage/emulated/0/ 에 존재하는 파일들로 리스트뷰 데이터에 적용
        }

        adapter.notifyDataSetChanged();

        //5 ListView 중 item이 클릭되면
        //6 Toast & 선택된 item이 디렉토리면 2부터 반복. 화면갱신

        fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                File item = (File) adapter.getItem(i);
                String text = item.getName();
                Toast.makeText(getApplicationContext(), "선택:" + text, Toast.LENGTH_SHORT).show();
            }
        });


        checkPermissions();

//        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//        startActivity(intent);
    }

    private void checkPermissions() {
        //권한 보유 여부를 확인하는 코드. 앱에 권한이 있는경우
        if (SDK_INT >= Build.VERSION_CODES.R) { //API30이상일때는
            if (Environment.isExternalStorageManager()) {
                Toast.makeText(this, "파일 액세스 권한 주어져져 있음", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "파일 엑세스 권한 없음.", Toast.LENGTH_SHORT).show();
                //MediaStore.Files와 ContentResolver를 이용하여 미디어 파일 접근하는 방식의 코드
            }
        } else {
            //API29 : legacy Storage 이용?
            //API28 이하:
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }


    }

    class FileItemAdapter extends BaseAdapter {
        ArrayList<File> items = new ArrayList<File>();

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            //items에서 데이터를 받아서 내가원하는 View로 포장해준다
            FileItemView presentedView = new FileItemView(getApplicationContext()); //내부클래스로 정의해서 이렇게 context를 부를수있다 외부 class로 정의할땐?
            File item = items.get(i);
//            파일인지 디렉토리인지에 따라 글자색과 썸네일을 바꿔주는 코드
            int thumbResId = R.drawable.ic_launcher_background;
            int textColor = Color.BLACK;
            if (item.isDirectory()) {
                thumbResId = R.drawable.ic_launcher_foreground;
                textColor = Color.BLUE; //RED
            }
            presentedView.setTextViews(item.getName(), item.getPath(), thumbResId, textColor);
            return presentedView;
        }

        public void addItem(File item) {
            items.add(item);
        }
    }


}
