public class HellowWorld{
    public int x = 1;public int y = 1;public int z = 0;
    public static void main(String[] args) {
        HellowWorld HW = new HellowWorld();
        HW.test();
    }

    public void test(){
        if(x==y | x < ++y){
            z = x+y;
        }
        else{
            z = 1;
        }
        System.out.println(z);
    }
}
