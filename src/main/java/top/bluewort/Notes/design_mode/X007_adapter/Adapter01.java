package top.bluewort.Notes.design_mode.X007_adapter;

/**
 * 适配器模式
 * 目标（Target）接口：当前系统业务所期待的接口，它可以是抽象类或接口。
 * 适配者（Adaptee）类：它是被访问和适配的现存组件库中的组件接口。
 * 适配器（Adapter）类：它是一个转换器，通过继承或引用适配者的对象，把适配者接口转换成目标接口，让客户按目标接口的格式访问适配者。
 *
 * 类适配器模式
 */
public class Adapter01 {
    public static void main(String[] args) {
        AdapterTarget adapterTarget = new AdapterAdapter();
        adapterTarget.req();
    }
}
//目标接口
interface AdapterTarget{
    void req();
}
//适配者
class AdapterAdaptee {
    public void get(){
        System.out.println("适配本方法");
    }
}
//适配器
class AdapterAdapter extends AdapterAdaptee implements AdapterTarget{
    @Override
    public void req() {
        get();
        System.out.println("适配成功");
    }
}
