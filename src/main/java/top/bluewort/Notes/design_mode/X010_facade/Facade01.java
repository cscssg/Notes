package top.bluewort.Notes.design_mode.X010_facade;

/**
 * 外观模式
 * 外观（Facade）角色：为多个子系统对外提供一个共同的接口。
 * 子系统（Sub System）角色：实现系统的部分功能，客户可以通过外观角色访问它。
 * 客户（Client）角色：通过一个外观角色访问各个子系统的功能。
 */
public class Facade01 {
    public static void main(String[] args) {
        Sys1 s = new Sys1();
        s.opt();
    }
}
class Sys1{
    private SubSys01 subSys01 = new SubSys01();
    private SubSys02 subSys02 = new SubSys02();
    private SubSys03 subSys03 = new SubSys03();
    void opt(){
        subSys01.method1();
        subSys02.method2();
        subSys03.method3();
    }
}
//子系统1
class SubSys01{
    void method1(){
        System.out.println("调用子系统1的方法1");
    }
}
//子系统2
class SubSys02{
    void method2(){
        System.out.println("调用子系统2的方法2");
    }
}
//子系统3
class SubSys03{
    void method3(){
        System.out.println("调用子系统3的方法3");
    }
}

