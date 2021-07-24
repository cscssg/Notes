package top.bluewort.Notes.design_mode.X021_visitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 访问者毛hi
 * 抽象访问者（Visitor）角色：定义一个访问具体元素的接口，为每个具体元素类对应一个访问操作 visit() ，该操作中的参数类型标识了被访问的具体元素。
 * 具体访问者（ConcreteVisitor）角色：实现抽象访问者角色中声明的各个访问操作，确定访问者访问一个元素时该做什么。
 * 抽象元素（Element）角色：声明一个包含接受操作 accept() 的接口，被接受的访问者对象作为 accept() 方法的参数。
 * 具体元素（ConcreteElement）角色：实现抽象元素角色提供的 accept() 操作，其方法体通常都是 visitor.visit(this) ，另外具体元素中可能还包含本身业务逻辑的相关操作。
 * 对象结构（Object Structure）角色：是一个包含元素角色的容器，提供让访问者对象遍历容器中的所有元素的方法，通常由 List、Set、Map 等聚合类实现。
 */
public class Visitor01 {
    public static void main(String[] args) {
        ObjectStructure os = new ObjectStructure();
        os.add(new ConElemA());
        os.add(new ConElemB());
        Visi visitor = new ConVisia();
        os.accept(visitor);
        System.out.println("------------------------");
        visitor = new ConVisib();
        os.accept(visitor);
    }
}
//抽象访问者
interface Visi{
    void visit(ConElemA conElemA);
    void visit(ConElemB conElemB);
}
// 具体访问者a
class ConVisia implements Visi{
    @Override
    public void visit(ConElemA conElemA) {
        System.out.println("jv ti fang wen zhe a fang wen le "+conElemA.oprate());
    }

    @Override
    public void visit(ConElemB conElemB) {
        System.out.println("jv ti fang wen zhe a fang wen le "+conElemB.oprate());
    }
}
// 具体访问者b
class ConVisib implements Visi{
    @Override
    public void visit(ConElemA conElemA) {
        System.out.println("jv ti fang wen zhe b fang wen le "+conElemA.oprate());
    }

    @Override
    public void visit(ConElemB conElemB) {
        System.out.println("jv ti fang wen zhe b fang wen le "+conElemB.oprate());
    }
}
// 抽象元素
interface Elem{
    void acce(Visi visi);
}
// 具体元素A
class ConElemA implements Elem{
    @Override
    public void acce(Visi visi) {
        visi.visit(this);
    }
    public String oprate(){
        return "具体元素A的操作";
    }
}
// 具体元素B
class ConElemB implements Elem{
    @Override
    public void acce(Visi visi) {
        visi.visit(this);
    }
    public String oprate(){
        return "具体元素B的操作";
    }
}
// 对象结构
class ObjectStructure {
    private List<Elem> list = new ArrayList<Elem>();
    public void accept(Visi visitor) {
        Iterator<Elem> i = list.iterator();
        while (i.hasNext()) {
            ((Elem) i.next()).acce(visitor);
        }
    }
    public void add(Elem element) {
        list.add(element);
    }
    public void remove(Elem element) {
        list.remove(element);
    }
}