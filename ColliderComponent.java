
import java.util.List;

public class ColliderComponent extends KMGameComponent {
    
    public List<GameObject> colliders;
    public GameVector[] colliderVerts = new GameVector[4];

    public ColliderComponent(String componentName) {
        super(componentName);
        this.componentName = "ColliderComponent";
        // Initialize the colliders list
    }

    public void UpdateCollider(GameObject parenObject) {
        // Update the colliders' positions based on the game object's position
        //top left 
        colliderVerts[0] = new GameVector(parenObject.position.x, parenObject.position.y);
        //bottom left
        colliderVerts[1] = new GameVector(parenObject.position.x , parenObject.position.y + (parenObject.size.y));
        //bottom right     
        colliderVerts[2] = new GameVector(parenObject.position.x+(parenObject.size.x), parenObject.position.y +( parenObject.size.y));
        //top right
        colliderVerts[3] = new GameVector(parenObject.position.x+(parenObject.size.x), parenObject.position.y );
    }

    public GameVector[] GetColliderVerts() {
        return colliderVerts;
    }

    public boolean CheckCollision(GameObject otherObject) {
        for (GameVector vertex : this.colliderVerts) {
            // Check if the vertex is within the bounds of the other object's collider
            if (vertex.x >= otherObject.colliderComponent.colliderVerts[0].x && vertex.x >= otherObject.colliderComponent.colliderVerts[1].x &&

                vertex.x <= otherObject.colliderComponent.colliderVerts[2].x && vertex.x <= otherObject.colliderComponent.colliderVerts[3].x &&

                vertex.y >= otherObject.colliderComponent.colliderVerts[2].y && vertex.y >= otherObject.colliderComponent.colliderVerts[1].y &&

                vertex.y <= otherObject.colliderComponent.colliderVerts[0].y && vertex.y <= otherObject.colliderComponent.colliderVerts[3].y) {
                
                    System.out.println("Collision detected between " + this.componentName + " and " + otherObject.objectName);
                return true; // Collision detected
            }
          
        }
        // Check for collision with another object
        // Implement collision detection logic here
        // For example, using Axis-Aligned Bounding Box (AABB) collision detection
        return false; // Placeholder return value
    }

    @Override
    public void UpdateComponent(GameObject parentObject) {
        // Call the UpdateCollider method to update the collider's position
        UpdateCollider(parentObject);
    }

}
