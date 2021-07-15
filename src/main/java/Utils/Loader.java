package Utils;

import android.os.Environment;

import com.example.filemanager.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Model.Folder;

public class Loader {

    public Loader(){

    }

    public ArrayList<Folder> load_folder(String root_path)
    {

        ArrayList<Folder> list_folder = new ArrayList<>();
        ArrayList<Folder> list_file = new ArrayList<>();

        if(root_path.equalsIgnoreCase("/"))
        {
            File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

            File[] files=root.listFiles();
            for (File file: files){

                if (file.isFile()) {
                    int file_size = Integer.parseInt(String.valueOf(file.length()/(1024 * 1024))) ;
                    list_file.add(new Folder(file.getName(), "" + file_size + " MB", "", getIconFile(file.getName()), file.getName(), file.isFile()));
                }
                else
                {
                    list_folder.add(new Folder(file.getName(), "" + getChild(file.getName()) + " muc",getDate(file ), getIconFolder(file.getName()), file.getName() , file.isFile() ));
                }
            }
            list_folder.addAll(list_file);
            return list_folder;
        }
        else
        {
            File root = new File(Environment.getExternalStorageDirectory() + "/" + root_path  );
            File[] files = root.listFiles();
            if (files == null) return list_folder;
            for(File file : files) {
                if (file.isFile()) {
                    int file_size = Integer.parseInt(String.valueOf(file.length()/ (1024 * 1024)));
                    list_file.add(new Folder(file.getName(), "" + file_size + " MB", "", getIconFile(file.getName()), root_path + "/" + file.getName(), file.isFile()));
                }
                else
                {
                    list_folder.add(new Folder(file.getName(),"" + getChild( root_path + "/" + file.getName()) + " mục",getDate(file ), getIconFolder(file.getName()), root_path + "/" + file.getName() , file.isFile() ));
                }

            }
        }
        list_folder.addAll(list_file);
        return list_folder;
    }

    public String getDate(File file )
    {
        return new SimpleDateFormat("dd-MM-yyyy").format(
                new Date(file.lastModified())
        ).toString();
    }

    public int getChild(String path)
    {
        File dir = new File(Environment.getExternalStorageDirectory() + "/" + path );
        File[] files = dir.listFiles();
        if (files != null) {
            int numberOfFiles = files.length;
            return numberOfFiles;
        }
        return 0;
    }

    public int getIconFolder(String name)
    {
        if (name.equalsIgnoreCase("Documents")) return R.drawable.fl_document;
        else if(name.equalsIgnoreCase("Download")) return R.drawable.fl_download;
        else if(name.equalsIgnoreCase("Pictures")) return R.drawable.fl_pictures;
        else if(name.equalsIgnoreCase("Music")) return R.drawable.fl_music;
        else return R.drawable.fl_normal;
    }

    public int getIconFile (String name){

        if (name.contains("mp3") || name.contains("wav") || name.contains("mp4"))
            return R.drawable.music;
        else if (name.contains("jpg") || name.contains("png") || name.contains("jpeg") || name.contains("svg"))
            return R.drawable.image;
        else if(name.contains("pdf"))
            return R.drawable.pdf;
        else if(name.contains("rar") || name.contains("zip") || name.contains("tar") || name.contains("gz"))
            return R.drawable.zip;
        else
            return R.drawable.document;
    }


}
