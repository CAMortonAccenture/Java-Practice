public class GameObject {
    public GameVector position;
    public GameVector velocity;
    public GameVector size;

    public  GameObject[] colRecent;

    public KMGameComponent[] objectComponents;
    public ColliderComponent colliderComponent;

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
        this.colRecent = new GameObject[10];
        this.objectComponents = new KMGameComponent[10];
        this.parentObject = null;
        this.childObject = null;

    }

    public boolean AddColliderComponent(ColliderComponent colliderComponent){
        for(int i = 0; i < objectComponents.length; i++){
            if(objectComponents[i] == null){
                objectComponents[i] = colliderComponent;
                this.colliderComponent = colliderComponent;
                return true;
            }
        }
        return false;
    }

    public boolean  AddComponent(KMGameComponent gameComponent){
        for(int i = 0; i < objectComponents.length; i++){
            if(objectComponents[i] == null){
                objectComponents[i] = gameComponent;
                return true;
            }
        }
        return false;
    }

    public void UpdateObject(){
        position.x += velocity.x;
        position.y += velocity.y;
        colliderComponent.UpdateComponent(this);
        for(int i = 0; i < objectComponents.length; i++){
            if(objectComponents[i] != null){
                objectComponents[i].UpdateComponent(this);
            }
        }
    }

    public boolean Collides(GameObject colGameObject){
        if(colGameObject.equals(this)){
          return false;
        }

        return colliderComponent.CheckCollision(colGameObject);

    }

    public boolean ObjectCollided(GameObject colGameObject){
       /*  for(int i = 0; i < colRecent.length; i++){
            if(colRecent[i] == null){
                //colRecent[i] = colGameObject;
                return true;
            }
            else if(colRecent[i].objectName.equals(colGameObject.objectName)){
                return false;
            }
        }
        ClearCollision(); */
        return true;
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
