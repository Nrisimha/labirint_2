/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Interface służący do zapisu/odczytu pliku z labiryntem.
 * @author Piotr Bartman 
 */
public interface FileAdapter {


    /**
     *
     * @param path
     * @return Maze. Jeżeli nie powiedzie się wczytanie pliku (zła ścieżka lub
     * uszkodzony plik) metoda zwraca null.
     */
    Maze load(String path);
    
    /**
     *
     * @param maze
     * @param path
     * @return boolean. Jeżeli nieudało się uzyskać dostępu do ścieżki lub Maze
     * był wartością null zostanie zwrócone false.
     */
    boolean save(Maze maze, String path);
}