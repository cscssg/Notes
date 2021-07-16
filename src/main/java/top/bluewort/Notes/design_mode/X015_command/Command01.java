package top.bluewort.Notes.design_mode.X015_command;

/**
 * 命令模式
 * 抽象命令类（Command）角色：声明执行命令的接口，拥有执行命令的抽象方法 execute()。
 * 具体命令类（Concrete Command）角色：是抽象命令类的具体实现类，它拥有接收者对象，并通过调用接收者的功能来完成命令要执行的操作。
 * 实现者/接收者（Receiver）角色：执行命令功能的相关操作，是具体命令对象业务的真正实现者。
 * 调用者/请求者（Invoker）角色：是请求的发送者，它通常拥有很多的命令对象，并通过访问命令对象来执行相关请求，它不直接访问接收者。
 */
public class Command01{
    public static void main(String[] args) {
        Comm comm = new conncreteComm();
        Invo invo = new Invo();
        invo.setComm(comm);
        invo.method();;
    }
}
//receiver
class Rece{
    void method(){
        System.out.println("the rece be run");
    }
}
//command
interface Comm{
    abstract void method();
}
//concrete command
class conncreteComm implements Comm{
    private Rece rece;
    conncreteComm(){
        if(rece == null){
            rece=new Rece();
        }
    }
    @Override
    public void method() {
        rece.method();
    }
}
class Invo{
    private Comm comm;

    public Comm getComm() {
        return comm;
    }

    public void setComm(Comm comm) {
        this.comm = comm;
    }

    void method(){
        System.out.println("invo be runn");
        comm.method();
    }
}