package top.bluewort.Notes.design_mode.X008_bridge;

/**
 * 桥接模式
 * 抽象化（Abstraction）角色：定义抽象类，并包含一个对实现化对象的引用。
 * 扩展抽象化（Refined Abstraction）角色：是抽象化角色的子类，实现父类中的业务方法，并通过组合关系调用实现化角色中的业务方法。
 * 实现化（Implementor）角色：定义实现化角色的接口，供扩展抽象化角色调用。
 * 具体实现化（Concrete Implementor）角色：给出实现化角色接口的具体实现。
 */
public class Bridge01 {
    public static void main(String[] args) {
        Bridge01Interface bridge01Interface = new Bridge01InterfaceImpl();
        Abstraction01 abstraction01 = new Abstraction01Ext(bridge01Interface);
        abstraction01.oprateAbstract();
    }
}
// 抽象化角色
abstract class Abstraction01{
    protected Bridge01Interface bridge01Interface;
    protected Abstraction01(Bridge01Interface bridge01Interface){
        this.bridge01Interface = bridge01Interface;
    }
    abstract void oprateAbstract();
}
//扩展抽象化角色
class Abstraction01Ext extends Abstraction01{
    protected Abstraction01Ext(Bridge01Interface bridge01Interface){
        super(bridge01Interface);
    }
    @Override
    void oprateAbstract() {
        bridge01Interface.oprate();
    }
}
// 实现化角色
interface Bridge01Interface{
    void oprate();
}
//具体实现化角色
class Bridge01InterfaceImpl implements Bridge01Interface{

    @Override
    public void oprate() {
        System.out.println("具体实现角色");
    }
}

