package ru.mono;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Main {
    public static void main(String[] args) throws IOException {
        reflectDemo();
        nioDemo();
    }

    public static void reflectDemo(){
        String text = "Hello Fellow World!";
        Printer printer = new Printer();
        Method[] methods = Printer.class.getDeclaredMethods();
        for(Method meth : methods){
            meth.setAccessible(true);
            int count = 0;
            if(meth.isAnnotationPresent(InvokeCount.class)) count = meth.getAnnotation(InvokeCount.class).value();
            for(int i=0; i<count; i++) {
                try {
                    meth.invoke(printer, text);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void nioDemo() throws IOException {
        String surname = "src/Naydanov";
        String name = "Sergey";
        Path mainDir = Paths.get(surname);
        Path nameFile = Paths.get(surname,name);
        Files.createDirectory(mainDir);
        Files.createFile(nameFile);
        for(int i=1; i<=3; i++){
            Files.createDirectory(Paths.get(surname,"dir"+i));
            Files.copy(nameFile, Paths.get(surname, "dir"+i, name));
        }
        Files.createFile(Paths.get(surname, "dir1", "file1"));
        Files.createFile(Paths.get(surname, "dir2", "file2"));
        Files.walkFileTree(mainDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attr){
                System.out.println("F: "+file.getFileName());
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                System.out.println("D: "+dir.getFileName());
                return FileVisitResult.CONTINUE;
            }
        });

        Files.walkFileTree(mainDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
