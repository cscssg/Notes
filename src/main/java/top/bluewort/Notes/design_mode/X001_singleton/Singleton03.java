package top.bluewort.Notes.design_mode.X001_singleton;

/**
 * 饿汉式创建
 * 使用静态代码块创建
 */
public class Singleton03 {
    private static Singleton03 INSTANCE = null;
    static {
        System.out.println("加载其它配置");
        INSTANCE = new Singleton03();
    }
    private Singleton03(){}
    public static Singleton03 getINSTANCE(){
        return INSTANCE;
    }
}
class Singleton03Test{
    public static void main(String[] args) {
        System.out.println(Singleton03.getINSTANCE() == Singleton03.getINSTANCE());
    }
}