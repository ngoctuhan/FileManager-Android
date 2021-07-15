package com.example.filemanager;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Adapter.HomeAdapter;
import Adapter.OnItemClickListener;
import Model.Folder;
import Utils.FileOpener;
import Utils.Loader;
import Utils.Transformer;

public class Main_Activity extends AppCompatActivity implements OnItemClickListener , View.OnClickListener {
    RecyclerView recyclerView;
    ArrayList<Folder> list_folder;
    Loader loader = new Loader();
    HomeAdapter homeAdapter ;
    String current_path;
    LinearLayout layout ;
    int function;
    Button btnOk;
    Button btnCacel;

    ArrayList<Folder> list_selected;
    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!Environment.isExternalStorageManager()){
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview);
        layout = findViewById(R.id.selected);
        btnOk = findViewById(R.id.mybutton_id2);
        btnOk.setOnClickListener(this );
        layout.setVisibility(View.GONE);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        list_folder = loader.load_folder("/");
        current_path = "/";
        homeAdapter = new HomeAdapter(getApplicationContext(),list_folder , this,   false  );
        recyclerView.setAdapter(homeAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.item1:
                list_selected = new ArrayList<>();
                current_path = "/";
                homeAdapter = new HomeAdapter(getApplicationContext(),list_folder , this, true );
                recyclerView.setAdapter(homeAdapter);
                return true;
            case R.id.item2:
                // sao chep
                if (this.list_selected == null || this.list_selected.size() == 0)
                    Toast.makeText(this, "Vui lòng chọn file ", Toast.LENGTH_SHORT).show();
                else
                {
                    function = 1;
                    layout.setVisibility(View.VISIBLE);
                    list_folder = loader.load_folder("/");
                    current_path = "/";
                    homeAdapter = new HomeAdapter(getApplicationContext(),list_folder , this,   false  );
                    recyclerView.setAdapter(homeAdapter);

                }
                return true;
            case R.id.item3:
                // di chuyen

                if (this.list_selected == null || this.list_selected.size() == 0)
                    Toast.makeText(this, "Vui lòng chọn file ", Toast.LENGTH_SHORT).show();
                else
                {
                    function = 2;
                    layout.setVisibility(View.VISIBLE);
                    list_folder = loader.load_folder("/");
                    current_path = "/";
                    homeAdapter = new HomeAdapter(getApplicationContext(),list_folder , this,   false  );
                    recyclerView.setAdapter(homeAdapter);

                }
                return true;
            case R.id.item4:
                // xoa
                if (this.list_selected == null || this.list_selected.size() == 0)
                    Toast.makeText(this, "Vui lòng chọn file ", Toast.LENGTH_SHORT).show();
                else {
                    for (Folder fl : list_selected) {
                        File dir = new File(Environment.getExternalStorageDirectory() + "/" + fl.getPath());
                        Transformer.deleteRecursive(dir);
                    }
                    current_path = list_selected.get(0).getPath();
                    onBackPressed();
                    list_selected = new ArrayList<>();
                    Toast.makeText(getApplicationContext(), "Xoá hoàn thành", Toast.LENGTH_LONG).show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onItemClick(Folder folder) {

        if(folder.isFile())
        {
            String full_path = Environment.getExternalStorageDirectory() + "/" + folder.getPath();
            //Toast.makeText(this, (CharSequence) full_path, Toast.LENGTH_SHORT).show();  // toi con k toast đc

            File myFile = new File(full_path );
            try {
                FileOpener.openFile(this, myFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            this.list_folder = loader.load_folder(folder.getPath());
            homeAdapter = new HomeAdapter(getApplicationContext(),list_folder , this, false );
            recyclerView.setAdapter(homeAdapter);
            current_path = folder.getPath();
        }
    }
    @Override
    public void onItemSelect(Folder folder, boolean state ) {

        if(state == true) {
            this.list_selected.add(folder);
        }
        else {
            this.list_selected.remove(folder);
        }
        System.out.println(list_selected);
    }
    @Override
    public void onBtnCopy(Folder folđer) {

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        String back_path = "";
        String [] path = current_path.split("/");
        if (path.length == 0)
        {
            // thoat ung dung
            finish();
            System.exit(0);
        }

        else    {
            for(int i = 0; i < path.length - 1; i ++)
            {
                back_path += path[i];
                if(i < path.length - 2) back_path += "/";
            }
            this.list_folder = loader.load_folder(back_path);
            homeAdapter = new HomeAdapter(getApplicationContext(),list_folder , this, false );
            recyclerView.setAdapter(homeAdapter);
            current_path = back_path;
        }
    }

    @Override
    public void onClick(View v) {
        System.out.println("Sẽ đc th hiện nhé yên tâm đi con trai: " + current_path);

        File dst = new File(Environment.getExternalStorageDirectory() + "/" + current_path);
        if(dst.isFile())
        {
            Toast.makeText(this, "Chọn thư mục để thực hiện không phải file",Toast.LENGTH_SHORT).show();
            return;
        }
        if(function == 1 || function == 2)
        {
            // copy to current path
            for(Folder fl : list_selected)
            {
                if(fl.isFile() == false)
                {
                    File src = new File(Environment.getExternalStorageDirectory() + "/" + fl.getPath());
                    dst = new File(Environment.getExternalStorageDirectory() + "/" + current_path);
                    try {
                        Transformer.copyDirectoryOneLocationToAnotherLocation(src, dst);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                if(fl.isFile() == true )
                {
                    File src = new File(Environment.getExternalStorageDirectory() + "/" + fl.getPath());
                    dst = new File(Environment.getExternalStorageDirectory() + "/" + current_path + "/" + fl.getName());
                    try {
                        Transformer.copyDirectoryOneLocationToAnotherLocation(src, dst);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
            list_folder = loader.load_folder(current_path);
            homeAdapter = new HomeAdapter(getApplicationContext(),list_folder ,this  , false );
            recyclerView.setAdapter(homeAdapter);
            if(function == 1)Toast.makeText(this, "Hoàn tất sao chép",Toast.LENGTH_SHORT).show();
            layout.setVisibility(View.GONE);
        }
        if(function == 2)
        {
            // move to current path
            // xoa cac file trong list select
            for (Folder fl : list_selected) {
                File dir = new File(Environment.getExternalStorageDirectory() + "/" + fl.getPath());
                Transformer.deleteRecursive(dir);
            }

            Toast.makeText(getApplicationContext(), "Di chuyển thành công", Toast.LENGTH_LONG).show();

        }
        else if(function == 3)
        {
            // compression to currrent path
        }
    }

}