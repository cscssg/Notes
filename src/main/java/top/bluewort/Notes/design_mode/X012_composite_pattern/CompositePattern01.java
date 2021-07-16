package top.bluewort.Notes.design_mode.X012_composite_pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合模式
 * 抽象构件（Component）角色：它的主要作用是为树叶构件和树枝构件声明公共接口，并实现它们的默认行为。在透明式的组合模式中抽象构件还声明访问和管理子类的接口；在安全式的组合模式中不声明访问和管理子类的接口，管理工作由树枝构件完成。（总的抽象类或接口，定义一些通用的方法，比如新增、删除）
 * 树叶构件（Leaf）角色：是组合中的叶节点对象，它没有子节点，用于继承或实现抽象构件。
 * 树枝构件（Composite）角色 / 中间构件：是组合中的分支节点对象，它有子节点，用于继承和实现抽象构件。它的主要作用是存储和管理子部件，通常包含 Add()、Remove()、GetChild() 等方法。
 * 透明组合模式
 */
public class CompositePattern01 {
    public static void main(String[] args) {
        Comp01 a1 = new Comp01Gan();
        Comp01 a2 = new Comp01Gan();
        Comp01 a3 = new Comp01leaf("1");
        Comp01 a4 = new Comp01leaf("2");
        a2.add(a3);
        a2.add(a4);
        a1.add(a2);
        a1.oprate();
    }
}
//抽象构件
interface Comp01{
    void add(Comp01 comp01);
    void remove(Comp01 comp01);
    Comp01 get(int i);
    void oprate();
}
//树叶构件
class Comp01leaf implements Comp01{
    String name;
    Comp01leaf(String name){
        this.name = name;
    }
    @Override
    public void add(Comp01 comp01) {

    }

    @Override
    public void remove(Comp01 comp01) {

    }

    @Override
    public Comp01 get(int i) {
        return null;
    }

    @Override
    public void oprate() {
        System.out.println("树叶子被打印"+name);
    }
}
//树枝构件
class Comp01Gan implements Comp01{
    private List<Comp01> comps = new ArrayList<>();
    @Override
    public void add(Comp01 comp01) {
        comps.add(comp01);
    }

    @Override
    public void remove(Comp01 comp01) {
        comps.remove(comp01);
    }

    @Override
    public Comp01 get(int i) {
        return comps.get(i);
    }

    @Override
    public void oprate() {
        for(Comp01 co :comps){
            co.oprate();
        }
    }
}

