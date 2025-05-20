import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter; 
import java.awt.event.WindowEvent; 
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



import java.util.List;


//TODO //1. Create collision detection system using components that make boxes taking velocity into account
//TODO //2. Create Collisions that take angel mass and velocity into account
public class myFrame extends Frame{


    public ArrayList<GameObject> gameObjects;
    

    //public List<java.base.Vector3> gameObjects = new java.util.ArrayList<Vector3>();

    public myFrame(){
       
        setSize(700,700);
        setVisible(true);
        
        addWindowListener(new WindowAdapter(){ @Override
            public void windowClosing(WindowEvent e) 
            { 
                System.exit(0); 
            }});
    }

    

    public static void main(String[] args) {
        Random r = new Random();
        myFrame f = new myFrame();
        f.setTitle("Ebic GAME 8)");
        f.update(f.getGraphics());
        Graphics g = f.getGraphics();
        java.util.Timer t = new java.util.Timer("GameTicker");
        f.Start();
        t.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                f.Update(g);
            }
        }, 0, 1000/60);
    }

    
    public void Start(){
 
        gameObjects = new ArrayList<GameObject>();
        GameObject player = new GameObject(new GameVector(30,55), new GameVector(0,0), new GameVector(10,10), "Player", "Player");
        for (int i = 0; i < 10; i++){
            MakeEnemy();
        }
        GameObject enemy = new GameObject(new GameVector(50,50), new GameVector(0,0), new GameVector(15,15), "Enemy", "Enemy");
        gameObjects.add(enemy);
        GameObject border = new GameObject(new GameVector(25,50), new GameVector(0,0), new GameVector(625,625), "Border", "Border");
        gameObjects.add(border);
        gameObjects.add(player);
    }


    public void MakeEnemy(){
        Random r = new Random();
        GameObject enemy = new GameObject(new GameVector(50,50), new GameVector(r.nextDouble()*(4 - 0) + 0,r.nextDouble()*(4 - 0) + 0), new GameVector(15,15), "Enemy", "Enemy");
        gameObjects.add(enemy);
    }


    public void Update(Graphics g){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() ->  PlayerLogic());
        executorService.shutdown();
        UpdateObject();
        for(GameObject gameObject : gameObjects){
            try {
                FindCollidingObject(gameObject);
            } catch (InterruptedException ex) {
            }
        }

        RenderGame(g);
        

    }

    public void FindCollidingObject(GameObject gameObject) throws InterruptedException{
        for(GameObject gameObject2 : gameObjects){  
            if(gameObject.objectTag.equals("Border") || gameObject2.objectTag.equals("Border")){
                if(gameObject.position.x +gameObject.size.x < gameObject2.position.x + gameObject2.size.x && gameObject.position.x + gameObject.size.x > gameObject2.position.x + gameObject.size.x &&
                    gameObject.position.y + gameObject.size.y< gameObject2.position.y + gameObject2.size.y && gameObject.position.y + gameObject.size.y > gameObject2.position.y + gameObject.size.y){
                      //  System.out.println("INSIDE BORDER" + gameObject.objectName + " " + gameObject2.objectName);
                    }  
                else{
                   // System.out.println("ESCAPED!!!" + gameObject.objectName + " " + gameObject2.objectName);
                    gameObject.setVelocity(new GameVector(-gameObject.getVelocity().x,-gameObject.getVelocity().y));
                }
                
            }
            else if(gameObject.position.x +gameObject.size.x < gameObject2.position.x + gameObject2.size.x && gameObject.position.x + gameObject.size.x > gameObject2.position.x + gameObject.size.x &&
                    gameObject.position.y + gameObject.size.y < gameObject2.position.y + gameObject2.size.y && gameObject.position.y + gameObject.size.y > gameObject2.position.y + gameObject.size.y){
                   
                    if(gameObject.ObjectCollided(gameObject2) == false)
                    {
                        GameVector newVelocity = gameObject.getVelocity();
                        GameVector newVelocity2 = gameObject2.getVelocity();
                        System.out.println("COLLISION DETECTED" + gameObject.objectName + " " + gameObject2.objectName);
                        gameObject.setVelocity(new GameVector(-gameObject.getVelocity().x,-gameObject.getVelocity().y));
                        gameObject2.setVelocity(new GameVector(-gameObject2.getVelocity().x,-gameObject2.getVelocity().y));
                    }
                    else{
                        //System.out.println("COLLISION DETECTED BUT ALREADY COLLIDED");
                    }
          
                }  
        }
    }

    public void PlayerLogic(){

        for(GameObject gameObject : gameObjects){
            if(gameObject.objectTag.equals("Player")){
                
                boolean horizontalMovement = KeyBoard.isKeyPressed(KeyEvent.VK_A) || KeyBoard.isKeyPressed(KeyEvent.VK_D);
                boolean verticalMovement = KeyBoard.isKeyPressed(KeyEvent.VK_W) || KeyBoard.isKeyPressed(KeyEvent.VK_S);
                GameVector velocity = new GameVector(0,0);
                if(horizontalMovement == true){
                    velocity.x = KeyBoard.isKeyPressed(KeyEvent.VK_A) ? -3 : 3;
                }
                if(verticalMovement == true){
                    velocity.y = KeyBoard.isKeyPressed(KeyEvent.VK_W) ? -3 : 3;
                }
                gameObject.setVelocity(velocity);
               // System.out.println(gameObject.velocity.x + " " + gameObject.velocity.y);
            }
        }
       



        
    }

    public void RenderGame(Graphics g){
        g.clearRect(0, 0, 700, 700);
        for(GameObject gameObject : gameObjects){
           // System.out.println(gameObject.objectName);
          //  System.out.println(gameObject.position.x + " " + gameObject.position.y);
            switch (gameObject.objectTag) {
                case "Player":
                    g.setColor(Color.RED);
                    break;
                case "Enemy":
                    g.setColor(Color.BLUE);
                    break;
                case "Border":
                    g.setColor(Color.BLACK);
                   
                    break;
                default:
                    g.setColor(Color.GREEN);
                    break;
            }
            g.draw3DRect((int)gameObject.position.x,(int)gameObject.position.y,(int)gameObject.size.x,(int)gameObject.size.y, true);
        }   
    }

    public void UpdateObject(){
        for(GameObject gameObject : gameObjects){
            gameObject.UpdateObject();
        }
    }


    @Override
    public void paint(Graphics g){
        //System.out.println("TEST");
        g.draw3DRect(100,100,100,100, true);
        g.setColor(Color.RED);
        g.draw3DRect(100,100,100,100, true);
        /* Graphics  newObject = g.create(100,100,100,100);
        newObject.setColor(Color.BLACK);
        newObject.draw3DRect(100,100,100,100, false); */
        
    }

}


