
public class GameVector{
    public double x;
    public double y;


    public  GameVector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void setX(double x){
        this.x = x;
    }

    public GameVector Dot(GameVector gameVector1, GameVector gameVector2){
        return new GameVector(gameVector1.x * gameVector2.x, gameVector1.y * gameVector2.y);
        
    }

    public void setY(double y){
        this.y = y;
    }

}

