package top.bluewort.Notes.design_mode.X005_builder;

/**
 * 建造者模式
 * 产品角色（Product）：它是包含多个组成部件的复杂对象，由具体建造者来创建其各个零部件。
 * 抽象建造者（Builder）：它是一个包含创建产品各个子部件的抽象方法的接口，通常还包含一个返回复杂产品的方法 getResult()。
 * 具体建造者(Concrete Builder）：实现 Builder 接口，完成复杂产品的各个部件的具体创建方法。
 * 指挥者（Director）：它调用建造者对象中的部件构造与装配方法完成复杂对象的创建，在指挥者中不涉及具体产品的信息。
 */
public class Builder01 {
    public static void main(String[] args) {
        BuilderAbs builderAbs = new BuildrA();
        Director directors = new Director(builderAbs);
        MyColor myColor = directors.get();
        myColor.show();
    }

}
class MyColor{
    private String one;
    private String two;
    private String three;

    public void setOne(String one) {
        this.one = one;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public void setThree(String three) {
        this.three = three;
    }
    public void show(){
        System.out.println("one::"+one);
        System.out.println("one::"+two);
        System.out.println("one::"+three);
    }
}
//建造者抽象接口
abstract class BuilderAbs{
    protected MyColor color = new MyColor();

    abstract void buildOne();
    abstract void buildTwo();
    abstract void buildThree();

    public MyColor getColor(){
        return color;
    }
}
//具体建造者
class BuildrA extends BuilderAbs{

    @Override
    void buildOne() {
        color.setOne("OOO");
    }

    @Override
    void buildTwo() {
        color.setTwo("oooo");
    }

    @Override
    void buildThree() {
        color.setThree("00000");
    }
}
//指挥者
class Director{
    private BuilderAbs builderAbs;
    public Director(BuilderAbs builderAbs){
        this.builderAbs = builderAbs;
    }
    public MyColor get(){
        builderAbs.buildOne();
        builderAbs.buildTwo();
        builderAbs.buildThree();
        return builderAbs.getColor();
    }
}
