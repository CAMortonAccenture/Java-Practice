import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent; 
import java.util.ArrayList;
import java.util.Random;


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
        player.AddColliderComponent(new ColliderComponent("ColliderComponent"));
        for (int i = 0; i < 1; i++){
            MakeEnemy();
        }
        GameObject border = new GameObject(new GameVector(25,50), new GameVector(0,0), new GameVector(625,625), "Border", "Border");
        border.AddColliderComponent(new ColliderComponent("ColliderComponent"));
        gameObjects.add(border);
        gameObjects.add(player);
    }


    public void MakeEnemy(){
        Random r = new Random();
        GameObject enemy = new GameObject(new GameVector(r.nextDouble()*(700 - 0) + 0,r.nextDouble()*(700 - 0) + 0), new GameVector(r.nextDouble()*(0 - 0) + 0,r.nextDouble()*(0 - 0) + 0), new GameVector(15,15), "Enemy", "Enemy");
        enemy.AddColliderComponent(new ColliderComponent("ColliderComponent"));
        gameObjects.add(enemy);
    }


    public void Update(Graphics g){
       
        PlayerLogic();
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
            if(!gameObject.objectTag.equals("Border") && !gameObject2.objectTag.equals("Border")){
                if(gameObject.Collides(gameObject2)){ 
                    System.out.println("Collision Detected between " + gameObject.objectName + " and " + gameObject2.objectName);
                    System.out.println("Time" + System.currentTimeMillis());
                    System.out.println("\033[H\033[2J");
                    System.out.flush();
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
            //top left = 0
          g.setColor(Color.GREEN);
          g.draw3DRect((int) gameObject.colliderComponent.colliderVerts[0].x,  (int)gameObject.colliderComponent.colliderVerts[0].y, 1, 1, true);
                //bottom left = 1
          g.setColor(Color.RED);
          g.draw3DRect((int) gameObject.colliderComponent.colliderVerts[1].x,  (int)gameObject.colliderComponent.colliderVerts[1].y, 1, 1, true);
           //bottom right = 2  
          g.setColor(Color.BLUE);
           
          g.draw3DRect((int) gameObject.colliderComponent.colliderVerts[2].x,  (int)gameObject.colliderComponent.colliderVerts[2].y, 1, 1, true);
           //top right = 3  
          g.setColor(Color.YELLOW);
           
          g.draw3DRect((int) gameObject.colliderComponent.colliderVerts[3].x,  (int)gameObject.colliderComponent.colliderVerts[3].y, 1, 1, true);
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
          
           // g.draw3DRect((int)gameObject.position.x,(int)gameObject.position.y,(int)gameObject.size.x,(int)gameObject.size.y, true);
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


