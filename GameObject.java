public class GameObject {
    public GameVector position;
    public GameVector velocity;
    public GameVector size;

    public  GameObject[] colRecent = new GameObject[10];

    public GameObject parentObject;
    public GameObject childObject;

    public String objectName;
    public String objectTag;

    public GameObject(GameVector position, GameVector velocity,GameVector size,String objectName, String objectTag) {
        this.position = position;
        this.velocity = velocity;
        this.size = size;
        this.objectName = objectName;
        this.objectTag = objectTag;
    }

    public void UpdateObject(){
        position.x += velocity.x;
        position.y += velocity.y;
    }

    public boolean ObjectCollided(GameObject colGameObject){
        for(int i = 0; i < colRecent.length; i++){
            if(colRecent[i] == null){
                colRecent[i] = colGameObject;
                return true;
            }
            else if(colRecent[i].objectName.equals(colGameObject.objectName)){
                return false;
            }
        }
        ClearCollision();
        return false;
    }

    public  void ClearCollision(){
        for(int i = 0; i < colRecent.length; i++){
            System.out.println(colRecent[i]);
            colRecent[i] = null;
        }
    }

    public void SetChildObject(GameObject childObject) {
        this.childObject = childObject;
    }

    public void SetParentObject(GameObject parentObject) {
        this.parentObject = parentObject;
    }

    public void setPosition(GameVector position) {
        this.position = position;
    }

    public void setVelocity(GameVector velocity) {
        this.velocity = velocity;
    }

    public void setSize(GameVector size) {
        this.size = size;
    }
    public GameVector getPosition() {
        return position;
    }
    public GameVector getVelocity() {
        return velocity;
    }

}
