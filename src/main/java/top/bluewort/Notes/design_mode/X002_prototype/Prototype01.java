package top.bluewort.Notes.design_mode.X002_prototype;

/**
 * 每次获取都赋值一个新的原型
 * 浅克隆：创建一个新对象，新对象的属性和原来对象完全相同，对于非基本类型属性，仍指向原有属性所指向的对象的内存地址。
 */
public class Prototype01 implements Cloneable{
    public Prototype01(){

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public Object object;
}
class Prototype01Test{
    public static void main(String[] args) throws CloneNotSupportedException {
        Prototype01 prototype01 = new Prototype01();
        prototype01.object = new Object();
        Prototype01 clone = (Prototype01) prototype01.clone();
        System.out.println(prototype01 == clone);
        System.out.println(prototype01.object == clone.object);
    }
}
