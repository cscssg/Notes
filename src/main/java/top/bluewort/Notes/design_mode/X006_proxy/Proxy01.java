package top.bluewort.Notes.design_mode.X006_proxy;

/**
 * 代理模式
 * 抽象主题（Subject）类：通过接口或抽象类声明真实主题和代理对象实现的业务方法。
 * 真实主题（Real Subject）类：实现了抽象主题中的具体业务，是代理对象所代表的真实对象，是最终要引用的对象。
 * 代理（Proxy）类：提供了与真实主题相同的接口，其内部含有对真实主题的引用，它可以访问、控制或扩展真实主题的功能。
 */
public class Proxy01 {
    public static void main(String[] args) {
        Subject subject = new ProxySubject();
        subject.doSome();
    }
}
//抽象主题
interface Subject{
    void doSome();
}
//真是主题
class RealSubject implements Subject{

    @Override
    public void doSome() {
        System.out.println("我是真是主题");
    }
}
//代理
class ProxySubject implements Subject{
    private Subject subject;
    @Override
    public void doSome() {
        if(subject == null){
            subject = new RealSubject();
        }

        preDo();
        subject.doSome();
        postDo();
    }
    void preDo(){
        System.out.println("提前做");
    }
    void postDo(){
        System.out.println("滞后做");
    }
}
