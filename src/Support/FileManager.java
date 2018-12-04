package Support;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

public class FileManager {
    static File mainFile = new File("F:\\BULAT\\JAVA Project\\");

    private static void fileView() throws IOException {
        String[] entries = mainFile.list();

        for (String entry : entries) {
            System.out.println(entry);
            //System.out.println( new File(mainFile+"\\"+entry+"\\Debug\\").delete());
            System.out.println();
        }
    }

    private static boolean moveFile(String sourcePath, String targetPath) {
        boolean fileMoved = true;

        try {
            Files.move(Paths.get(sourcePath), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            fileMoved = false;
        }
        return fileMoved;
    }

    private static boolean createFolder(String sourcePath) {
        boolean fileCreated = true;

        try {
            new File(sourcePath).mkdirs();
        } catch (Exception e) {
            fileCreated = false;
        }

        return fileCreated;
    }

    private static void deleteFileOrFolder(final Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs)
                    throws IOException {
                Files.delete(file);
                return CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(final Path file, final IOException e) {
                return handleException(e);
            }

            private FileVisitResult handleException(final IOException e) {
                e.printStackTrace(); // replace with more robust error handling
                return TERMINATE;
            }

            @Override
            public FileVisitResult postVisitDirectory(final Path dir, final IOException e)
                    throws IOException {
                if (e != null) return handleException(e);
                Files.delete(dir);
                return CONTINUE;
            }
        });
    }
}
