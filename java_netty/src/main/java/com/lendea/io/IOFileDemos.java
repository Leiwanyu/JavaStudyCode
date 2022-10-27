package com.lendea.nio;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

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
}
