package top.bluewort.Notes.design_mode.X019_mediator;

import java.util.ArrayList;
import java.util.List;

/**
 * 中介者模式
 * 抽象中介者（Mediator）角色：它是中介者的接口，提供了同事对象注册与转发同事对象信息的抽象方法。
 * 具体中介者（Concrete Mediator）角色：实现中介者接口，定义一个 List 来管理同事对象，协调各个同事角色之间的交互关系，因此它依赖于同事角色。
 * 抽象同事类（Colleague）角色：定义同事类的接口，保存中介者对象，提供同事对象交互的抽象方法，实现所有相互影响的同事类的公共功能。
 * 具体同事类（Concrete Colleague）角色：是抽象同事类的实现者，当需要与其他同事对象交互时，由中介者对象负责后续的交互。
 */
public class Mediator01 {
    public static void main(String[] args) {
        //创建同事
        Colle colle1 = new ConColle("锅锅");
        Colle colle2 = new ConColle("虫虫");
        Colle colle3 = new ConColle("果果");
        Colle colle4 = new ConColle("充充");
        Colle colle5 = new ConColle("裹裹");
        //创建中介
        Medi medi = new ConMedi();
        //注册同事到中介
        medi.register(colle1);
        medi.register(colle2);
        medi.register(colle3);
        medi.register(colle4);
        medi.register(colle5);
        //发送消息
        colle1.send("大家好");
        colle3.send("我不好");
    }


}
//抽象中介者
abstract class Medi{
    public abstract void register(Colle colle);
    public abstract void relay(Colle colle,String msg);
}
//具体 中介类
class ConMedi extends Medi{
    private List<Colle> colles = new ArrayList<>();
    @Override
    public void register(Colle colle) {
        if(!colles.contains(colle)){
            colles.add(colle);
            colle.setMedi(this);
            System.out.println("同事::"+colle.name+"寻找中介成功");
        }
    }

    @Override
    public void relay(Colle colle,String msg) {
        for(Colle co:colles){
            if(!co.equals(colle)){
                co.reciv(msg);
            }
        }
    }
}
//抽象同事类
abstract class Colle{
    protected Medi medi;
    public void setMedi(Medi medi){
        this.medi= medi;
    }
    protected String name;
    public Colle(String name){
        this.name=name;
    }
    abstract void send(String msg);
    abstract void reciv(String msg);
}
//具体实现类
class ConColle extends Colle{
    public ConColle(String name){
        super(name);
    }
    @Override
    void send(String msg) {
        System.out.println("同事::"+name+"::发送消息::"+msg);
        medi.relay(this,msg);
    }

    @Override
    void reciv(String msg) {
        System.out.println("同事::"+name+"::接收到消息::"+msg);
    }
}

