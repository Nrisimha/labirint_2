package source;

/**
 * Klasa GameMaster przechowuje grawca i obecny poziom labiryntu dla MiniMapy,
 * odpowiada za zapisywanie i zakończenie gry
 * @author Maksym Titov
 */
public class GameMaster {
    public TileMap tileMap;
    public Player player;
    
    public GameMaster(){ }
        
    public GameMaster(Player player, TileMap tileMap){
        this.player=player;
        this.tileMap=tileMap;
    }
    /**
     * zapisywanie gry
     */
    public void save(){};
    /**
     * koniec gry
     */
    public void end(){
        System.exit(0);
    }
}
