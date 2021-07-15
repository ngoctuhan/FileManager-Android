package Model;

public class Folder {

    private String name;
    private String  num_child;
    private String creat_time;
    private int  image_avt;
    private String path;
    private boolean  isFile;
    public Folder() {

    }

    public Folder(String name, String  num_child, String creat_time, int image_avt ) {
        this.name = name;
        this.num_child = num_child;
        this.creat_time = creat_time;
        this.image_avt = image_avt;

    }

    public Folder(String name, String  num_child, String creat_time, int image_avt, String path, boolean isFile ) {
        this.name = name;
        this.num_child = num_child;
        this.creat_time = creat_time;
        this.image_avt = image_avt;
        this.path = path;
        this.isFile = isFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String  getNum_child() {
        return num_child  ;
    }

    public void setNum_child( String num_child) {
        this.num_child = num_child;
    }

    public String getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(String creat_time) {
        this.creat_time = creat_time;
    }

    public int getImage_avt() {
        return image_avt;
    }

    public void setImage_avt(int  image_avt) {
        this.image_avt = image_avt;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "path='" + path + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Folder folder = (Folder) o;
        return path.equals(folder.path);
    }
}
