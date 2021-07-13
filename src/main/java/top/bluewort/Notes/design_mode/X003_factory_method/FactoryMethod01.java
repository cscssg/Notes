package top.bluewort.Notes.design_mode.X003_factory_method;

/**
 * 工厂方法模式
 * 抽象工厂（Abstract Factory）：提供了创建产品的接口，调用者通过它访问具体工厂的工厂方法 newProduct() 来创建产品。
 * 具体工厂（ConcreteFactory）：主要是实现抽象工厂中的抽象方法，完成具体产品的创建。
 * 抽象产品（Product）：定义了产品的规范，描述了产品的主要特性和功能。
 * 具体产品（ConcreteProduct）：实现了抽象产品角色所定义的接口，由具体工厂来创建，它同具体工厂之间一一对应。
 */
public class FactoryMethod01 {
    public static void main(String[] args) {
        FactoryMethodInterface fmi = true?new AppleFactory():new PearFactory();
        Product p = fmi.getProduct();
        p.create();;
    }
}
//产品类接口
interface Product{
    void create();
}
class Apple implements Product{

    @Override
    public void create() {
        System.out.println("生产一个苹果");
    }
}
//梨子类
class Pear implements Product{

    @Override
    public void create() {
        System.out.println("生产一个梨子");
    }
}
//产品工厂接口
interface FactoryMethodInterface{
    Product getProduct();
}
//苹果工程
class AppleFactory implements FactoryMethodInterface{

    @Override
    public Product getProduct() {
        return new Apple();
    }
}
//梨子工程
class PearFactory implements FactoryMethodInterface{

    @Override
    public Product getProduct() {
        return new Pear();
    }
}