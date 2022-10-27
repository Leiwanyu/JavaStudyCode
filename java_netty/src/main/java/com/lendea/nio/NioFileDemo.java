package com.lendea.nio;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author lendea
 * @date 2022/8/30 14:16
 */
public class NioFileDemo {

    public static void main(String[] args) {

        final FileSystem fileSystem = FileSystems.getDefault();
        System.out.println(fileSystem);
        System.out.println(fileSystem.getRootDirectories());
        System.out.println(fileSystem.getSeparator());

        final Path path = Paths.get(".");
        System.out.println(path);
        System.out.println(path.getFileSystem().getSeparator());
        final Path absolutePath = path.toAbsolutePath();
        System.out.println(absolutePath);

        final Path normalize = absolutePath.normalize();
        System.out.println(normalize);

        final File file = normalize.toFile();
        System.out.println(file);

        for (Path path1 : Paths.get("..").toAbsolutePath()) {
            System.out.println(" " + path1);
        }

    }

}
