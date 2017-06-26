
public interface FileAdapter {

    /**
     *
     * @param maze
     * @param path
     * @return boolean. Jeżeli nieudało się uzyskać dostępu do ścieżki lub Maze
     * był wartością null zostanie zwrócone false.
     */
    boolean save(Maze maze, String path);

    /**
     *
     * @param path
     * @return Maze. Jeżeli nie powiedzie się wczytanie pliku (zła ścieżka lub
     * uszkodzony plik) metoda zwraca null.
     */
    Maze load(String path);
}
