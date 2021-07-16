package top.bluewort.Notes.design_mode.X018_observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察值模式
 * 抽象主题（Subject）角色：也叫抽象目标类，它提供了一个用于保存观察者对象的聚集类和增加、删除观察者对象的方法，以及通知所有观察者的抽象方法。
 * 具体主题（Concrete Subject）角色：也叫具体目标类，它实现抽象目标中的通知方法，当具体主题的内部状态发生改变时，通知所有注册过的观察者对象。
 * 抽象观察者（Observer）角色：它是一个抽象类或接口，它包含了一个更新自己的抽象方法，当接到具体主题的更改通知时被调用。
 * 具体观察者（Concrete Observer）角色：实现抽象观察者中定义的抽象方法，以便在得到目标的更改通知时更新自身的状态。
 */
public class Observer01 {
    public static void main(String[] args) {
        subj su = new subjchild();
        su.add(new obse01());
        su.add(new obse02());
        su.add(new obse03());
        su.method();
    }
}
//subject
abstract class subj{
    List<obse> obses = new ArrayList<>();
    public void add(obse o){
        obses.add(o);
    }
    public void del(obse o){
        obses.remove(o);
    }
    abstract void method();
}
class subjchild extends subj{
    @Override
    void method() {
        System.out.println("target is update  call other obse");
        for(obse o:obses){
            o.method();
        }
    }
}
interface obse{
    void method();
}
class obse01 implements obse{

    @Override
    public void method() {
        System.out.println("01 is be obs");
    }
}
class obse02 implements obse{

    @Override
    public void method() {
        System.out.println("02 is be obs");
    }
}
class obse03 implements obse{

    @Override
    public void method() {
        System.out.println("03 is be obs");
    }
}