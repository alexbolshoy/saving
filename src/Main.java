import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(18, 15, 14, 16);
        GameProgress gameProgress2 = new GameProgress(16, 19, 18, 9.9);
        GameProgress gameProgress3 = new GameProgress(20, 21, 22, 29.2);
        String save1 = "D:/Games/savegames/save1.dat";
        String save2 = "D:/Games/savegames/save2.dat";
        String save3 = "D:/Games/savegames/save3.dat";
        String wayZip = "D:/Games/savegames/zip.zip";

        List<String> listSaveGame = Arrays.asList(save1, save2, save3);
        List<GameProgress> listGameProgress = Arrays.asList(gameProgress1, gameProgress2, gameProgress3);

        saveGame(listSaveGame, listGameProgress);
        zipFile(listSaveGame, wayZip);
    }

    public static void saveGame(List<String> listSaveGame, List<GameProgress> listGameProgress) {
        for (int i = 0; i < listSaveGame.size(); i++) {
            for (int j = 0; j < listGameProgress.size(); j++) {
                if (i == j) {
                    try (FileOutputStream fos = new FileOutputStream(listSaveGame.get(i));
                         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                        oos.writeObject(listGameProgress.get(j));
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }
    }

    public static void zipFile(List<String> listSaveGame, String wayZip) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(wayZip))) {
            for (String save : listSaveGame) {
                File fileNew = new File(save);
                try (FileInputStream fis = new FileInputStream(save)) {
                    ZipEntry entry = new ZipEntry(fileNew.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            System.out.println("Архив сохранений создан");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        for (int k = 0; k < listSaveGame.size(); k++) {
            File file = new File(listSaveGame.get(k));
            if (file.delete()) {
                System.out.println("Лишний файл сохранения, по адресу: " + file.getAbsolutePath() + " удален");
            } else {
                System.out.println("Ошибка удаления файла");
            }
        }
    }
}