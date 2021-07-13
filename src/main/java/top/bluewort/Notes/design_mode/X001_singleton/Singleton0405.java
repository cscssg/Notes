package top.bluewort.Notes.design_mode.X001_singleton;

/**
 * 懒汉式加载   不安全  安全
 */
public class Singleton0405 {
    private static Singleton0405 INSTANCE = null;
    private Singleton0405(){

    }
    public static synchronized Singleton0405 getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new Singleton0405();
        }
        return INSTANCE;
    }
}
class Singleton0405Test{
    public static void main(String[] args) {
        System.out.println(Singleton0405.getINSTANCE() == Singleton0405.getINSTANCE());
    }
}
