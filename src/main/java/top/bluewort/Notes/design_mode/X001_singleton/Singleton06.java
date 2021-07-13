package top.bluewort.Notes.design_mode.X001_singleton;

/**
 * 懒汉式加载  使用静态内部类进行加载
 */
public class Singleton06 {
    private Singleton06(){}

    private static class Inner{
        private static Singleton06 INSTANCE = new Singleton06();
    }
    /**
     * 2静态的内部类的方式来创建实例
     * 在内部类被加载和初始化时，才创建实例。
     * 静态内部类不会随着外部类的加载和初始化而....
     */
    public static Singleton06 getInstance(){
        return Inner.INSTANCE;
    }
}
class Singleton06Test{
    public static void main(String[] args) {
        System.out.println(Singleton06.getInstance() == Singleton06.getInstance());
    }
}
