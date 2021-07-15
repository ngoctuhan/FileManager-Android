package Utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Decompression {

    public static void UnzipFolder(String src, String dst )
    {

        try (ZipFile file = new ZipFile(src ))
        {
            Enumeration<? extends ZipEntry> zipEntries = file.entries();
            while (zipEntries.hasMoreElements())
            {
                ZipEntry zipEntry = zipEntries.nextElement();
                File newFile = new File(dst , zipEntry.getName());

                //create sub directories
                newFile.getParentFile().mkdirs();

                if (!zipEntry.isDirectory())
                {
                    try (FileOutputStream outputStream = new FileOutputStream(newFile))
                    {
                        BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream(zipEntry));
                        while (inputStream.available() > 0)
                        {
                            outputStream.write(inputStream.read());
                        }
                        inputStream.close();
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
