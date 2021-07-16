package top.bluewort.Notes.design_mode.X017_state;

/**
 * 状态模式
 * 环境类（Context）角色：也称为上下文，它定义了客户端需要的接口，内部维护一个当前状态，并负责具体状态的切换。
 * 抽象状态（State）角色：定义一个接口，用以封装环境对象中的特定状态所对应的行为，可以有一个或多个行为。
 * 具体状态（Concrete State）角色：实现抽象状态所对应的行为，并且在需要的情况下进行状态切换
 */
public class State01 {
    public static void main(String[] args) {
        Con001 con001 = new Con001();
        con001.Handle();
        con001.Handle();
        con001.Handle();
        con001.Handle();
        con001.Handle();
        con001.Handle();
    }
}
abstract class stat{
    abstract void handel(Con001 con001);
}
class Con001{
    private stat stat;
    public Con001(){
        stat = new stat01();
    }

    public top.bluewort.Notes.design_mode.X017_state.stat getStat() {
        return stat;
    }

    public void setStat(top.bluewort.Notes.design_mode.X017_state.stat stat) {
        this.stat = stat;
    }

    void Handle(){
        stat.handel(this);
    }
}
class stat01 extends stat {
    @Override
    void handel(Con001 con001) {
        System.out.println("new state is 01");
        con001.setStat(new stat02());
    }
}
class stat02 extends stat {
    @Override
    void handel(Con001 con001) {
        System.out.println("new state is 02");
        con001.setStat(new stat01());
    }
}