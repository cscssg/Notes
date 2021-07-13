package top.bluewort.Notes.design_mode.X001_singleton;

/**
 * 饿汉式创建
 * 枚举创建
 */
public enum  Singleton02 {
    INSTANCE;
}
class Singleton02Test{
    public static void main(String[] args) {
        Singleton02 s2 = Singleton02.INSTANCE;
        Singleton02 s1 = Singleton02.INSTANCE;
        System.out.println(s1 == s2);
    }
}
