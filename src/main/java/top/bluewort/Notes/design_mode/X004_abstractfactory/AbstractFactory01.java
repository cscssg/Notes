package top.bluewort.Notes.design_mode.X004_abstractfactory;

/**
 * 抽象工厂模式
 * 抽象工厂（Abstract Factory）：提供了创建产品的接口，它包含多个创建产品的方法 newProduct()，可以创建多个不同等级的产品。
 * 具体工厂（Concrete Factory）：主要是实现抽象工厂中的多个抽象方法，完成具体产品的创建。
 * 抽象产品（Product）：定义了产品的规范，描述了产品的主要特性和功能，抽象工厂模式有多个抽象产品。
 * 具体产品（ConcreteProduct）：实现了抽象产品角色所定义的接口，由具体工厂来创建，它同具体工厂之间是多对一的关系。
 */
public class AbstractFactory01 {
    public static void main(String[] args) {
        HouseFactory hf = new MyHouse();
        Home home = hf.getHome();
        yard yard = hf.getYard();
        home.createHome();
        yard.createYard();
    }
}
//房子
interface Home{
    void createHome();
}
//大房子
class BigHome implements Home{
    @Override
    public void createHome() {
        System.out.println("买个大房子");
    }
}
//小房子
class SmallHome implements Home{

    @Override
    public void createHome() {
        System.out.println("买个小房子");
    }
}
//院子
interface yard{
    void createYard();
}
//大院子
class BigYard implements yard{

    @Override
    public void createYard() {
        System.out.println("买个大院子");
    }
}
//小院子
class smallYard implements yard{

    @Override
    public void createYard() {
        System.out.println("买个小院子");
    }
}
//工厂接口
interface HouseFactory{
    Home getHome();
    yard getYard();
}
class MyHouse implements HouseFactory{

    @Override
    public Home getHome() {
        return new BigHome();
    }

    @Override
    public yard getYard() {
        return new BigYard();
    }
}

