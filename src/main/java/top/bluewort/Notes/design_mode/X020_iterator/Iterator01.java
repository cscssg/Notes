package top.bluewort.Notes.design_mode.X020_iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * 迭代器模式
 * 抽象聚合（Aggregate）角色：定义存储、添加、删除聚合对象以及创建迭代器对象的接口。
 * 具体聚合（ConcreteAggregate）角色：实现抽象聚合类，返回一个具体迭代器的实例。
 * 抽象迭代器（Iterator）角色：定义访问和遍历聚合元素的接口，通常包含 hasNext()、first()、next() 等方法。
 * 具体迭代器（Concretelterator）角色：实现抽象迭代器接口中所定义的方法，完成对聚合对象的遍历，记录遍历的当前位置。
 */
public class Iterator01 {
    public static void main(String[] args) {
        Aggregate aggregate = new ConAggregate();
        aggregate.add("1");
        aggregate.add("3");
        aggregate.add("4");
        aggregate.add("w");
        aggregate.add("d");
        aggregate.add("v");
        aggregate.add("g");
        Itera itera = aggregate.getItera();
        System.out.println(itera.first());
        while (itera.hasNext()){
            System.out.println(itera.next());
        }
    }
}
//抽象聚合
interface Aggregate{
    void add(Object obj);
    void remove(Object obj);
    Itera getItera();
}
class ConAggregate implements Aggregate{
    private List<Object> objs = new ArrayList<>();
    @Override
    public void add(Object obj) {
        objs.add(obj);
    }

    @Override
    public void remove(Object obj) {
        objs.remove(obj);
    }

    @Override
    public Itera getItera() {
        return (new ConItera(objs));
    }
}
//抽象迭代器
interface Itera{
    Object first();
    Object next();
    boolean hasNext();
}
class ConItera implements Itera{
    private List<Object> list = null;
    private int index = -1;
    ConItera(List<Object> list){
        this.list = list;
    }
    @Override
    public Object first() {
        index = 0;
        return list.get(index);
    }

    @Override
    public Object next() {
        if(hasNext()){
            return list.get(++index);
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        if(index<list.size()-1){
            return true;
        }
        return false;
    }
}
