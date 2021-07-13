package top.bluewort.Notes.design_mode.X001_singleton;

/**
 * 饿汉式 加载 001
 * 1. 对象私有 外部无法新建
 * 2. 对外暴漏实例 提供唯一实例
 * 3. 私有静态属性 用于存取实例
 */
public class Singleton01 {
    private static Singleton01 INSTANCE = new Singleton01();
    private Singleton01(){}
    public static Singleton01 getINSTANCE(){
        return INSTANCE;
    }
}
class Singleton01Test{
    public static void main(String[] args) {
        Singleton01 s1 = Singleton01.getINSTANCE();
        Singleton01 s2 = Singleton01.getINSTANCE();
        System.out.println(s1==s2);
    }
}
