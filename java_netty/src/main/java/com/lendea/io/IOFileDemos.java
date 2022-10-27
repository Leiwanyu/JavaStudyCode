package com.lendea.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author lendea
 * @date 2022/8/30 13:51
 */
public class IOFileDemos {

    public static void main(String[] args) throws IOException {
        File file = new File("./src/main/resources/hello.sh");
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getCanonicalPath());
        System.out.println(file.getPath());

        System.out.println(file.isFile());
        System.out.println(file.isDirectory());
        System.out.println(file.canRead());
        System.out.println(file.canWrite());
        System.out.println(file.canExecute());
        System.out.println(file.length());//字节大小

        File f = new File("./src/main/resources/hello.txt");
        if (f.createNewFile()) {
            System.out.println("文件创建成功");
        }

        final File tempFile = File.createTempFile("tmp-", ".txt");//提供临时文件的前缀和后缀
        f.deleteOnExit();//JVM退出时候自动删除
        System.out.println(tempFile.isFile());
        System.out.println(tempFile.getAbsolutePath());


        File file1 = new File(".");
        System.out.println("--------> start print file , " + file1.getAbsolutePath());
        printFile(file1);
        final File[] imlFiles = file1.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        });

        try (InputStream inputStream = new FileInputStream("./src/main/resources/hello.sh")) {
            for (; ; ) {
                final int read = inputStream.read();
                if (read == -1) {
                    break;
                }
                System.out.print(read + ", ");
            }
        }

        try (InputStream inputStream = new FileInputStream("./src/main/resources/hello.sh")) {
            final byte[] buffer = new byte[2];
            for (; ; ) {
                final int read = inputStream.read(buffer);
                if (read == -1) {
                    break;
                }
                System.out.println(read);
            }
        }

        byte[] data = new byte[]{101, 99, 104, 111, 32, 39, 104, 101, 108, 108, 111, 44, 119, 111, 114, 108, 100, 39, 10};
        try (InputStream byteArrayInputStream = new ByteArrayInputStream(data)) {
            int n;
            while ((n = byteArrayInputStream.read()) != -1) {
                System.out.print((char) n);
            }
        }

        final File file2 = new File("./src/main/resources/helloworld.txt");
        System.out.println(file2.exists());
        if (!file2.exists()) {
            final boolean newFile = file2.createNewFile();
            System.out.println(file2.getAbsolutePath() + " create " + newFile);
        }
        try (OutputStream outputStream = new FileOutputStream(file2)) {
            outputStream.write(101);
            outputStream.write(99);
            outputStream.write(104);
            outputStream.write(111);

            byte[] data2 = new byte[]{32, 39, 104, 101, 108, 108, 111, 44, 119, 111, 114, 108, 100, 39, 10};
            outputStream.write(data2);
            outputStream.write("我们和你".getBytes(StandardCharsets.UTF_8));

            outputStream.flush();
        }

        byte[] datas;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byteArrayOutputStream.write("hello".getBytes(StandardCharsets.UTF_8));
            byteArrayOutputStream.write(",world".getBytes(StandardCharsets.UTF_8));
            datas = byteArrayOutputStream.toByteArray();
        }
        System.out.println(new String(datas, StandardCharsets.UTF_8));


        IOFileDemos fileDemos = new IOFileDemos();
        Properties props = new Properties();
        props.load(fileDemos.getResourcesFromClassPath("/p1.properties"));
        props.load(fileDemos.getResourceAsStream("./src/main/resources/p2.properties"));

    }

    private static int depth = 0;

    private static void printFile(File file) {
        final File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                System.out.println(repeatBlank(depth) + f.getName());
                if (f.isDirectory()) {
                    depth++;
                    printFile(f);
                    depth--;
                }
            }
        }
    }

    private static String repeatBlank(int depth) {
        if (depth == 0) {
            return "";
        }
        StringBuilder blanks = new StringBuilder();
        for (int i = 1; i <= depth; i++) {
            blanks.append(" ");
        }
        return blanks.toString();
    }


    public InputStream getResourcesFromClassPath(String filename) throws IOException {
        try(InputStream stream = this.getClass().getResourceAsStream(filename)){
            if (stream != null) {
                return stream;
            }
        }
        return null;
    }

    public InputStream getResourceAsStream(String filename) throws IOException {
        try(InputStream input = new FileInputStream(filename)){
            return input;
        }
    }

}

class CountInputStream extends FilterInputStream{
    private volatile int count = 0;

    public CountInputStream(InputStream input) {
        super(input);
    }

    @Override
    public int read() throws IOException {
        int n = in.read();
        if (n != 1) {
            this.count++;
        }
        return count;
    }


    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int n = in.read(b, off, len);
        if (n != 1) {
            this.count += n;
        }
        return n;
    }
}
