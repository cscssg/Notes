package top.bluewort.Notes.design_mode.X009_decorator;

/**
 * 装饰器模式
 *  抽象构件（Component）角色：定义一个抽象接口以规范准备接收附加责任的对象。
 * 具体构件（ConcreteComponent）角色：实现抽象构件，通过装饰角色为其添加一些职责。
 * 抽象装饰（Decorator）角色：继承抽象构件，并包含具体构件的实例，可以通过其子类扩展具体构件的功能。
 * 具体装饰（ConcreteDecorator）角色：实现抽象装饰的相关方法，并给具体构件对象添加附加的责任。
 * 类似于代理模式 加了子类在子类中进行 装饰操作
 */
public class Decorator01 {
    public static void main(String[] args) {
        Comp comp = new CompImpl01();
        CompImp02Child compImp02Child = new CompImp02Child(comp);
        compImp02Child.save();
    }
}
//抽象构件
interface Comp{
    void save();
}
//具体构件
class CompImpl01 implements Comp{
    @Override
    public void save() {
        System.out.println("我是一个具体的保存操作");
    }
}
//抽象装饰
class CompImp02 implements Comp{
    private Comp comp;
    public CompImp02(Comp comp){
        this.comp = comp;
    }
    @Override
    public void save() {
        comp.save();
    }
}
//具体装饰
class CompImp02Child extends CompImp02{

    public CompImp02Child(Comp comp) {
        super(comp);
    }
    @Override
    public void save(){
        super.save();
        add();
    }
    void add(){
        System.out.println("我是一个装饰操作");
    }
}