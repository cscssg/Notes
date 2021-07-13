package top.bluewort.Notes.design_mode.X002_prototype;

/**
 * 深克隆：创建一个新对象，属性中引用的其他对象也会被克隆，不再指向原有对象地址。
 * 1. 修改克隆方法  手动深度克隆
 * 2. 直接序列化 fastjson 实现克隆
 * 3. IO流克隆
 */
public class Prototype02 implements Cloneable {
    @Override
    protected Prototype02 clone() throws CloneNotSupportedException {
        Prototype02 p =(Prototype02)super.clone();
        p.child = (Prototype02Child) this.child.clone();
        return p;
    }
    public Prototype02Child child;
}
class Prototype02Child implements Cloneable{
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
class Prototype02Test{
    public static void main(String[] args) throws CloneNotSupportedException {
        Prototype02  prototype02 = new Prototype02();
        prototype02.child = new Prototype02Child();
        Prototype02 clone = (Prototype02) prototype02.clone();
        System.out.println(prototype02 == clone);
        System.out.println(prototype02.child == clone.child);
    }
}
