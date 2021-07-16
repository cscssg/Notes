package top.bluewort.Notes.design_mode.X014_strategy;

/**
 * 策略模式
 * 抽象策略（Strategy）类：定义了一个公共接口，各种不同的算法以不同的方式实现这个接口，环境角色使用这个接口调用不同的算法，一般使用接口或抽象类实现。
 * 具体策略（Concrete Strategy）类：实现了抽象策略定义的接口，提供具体的算法实现。
 * 环境（Context）类：持有一个策略类的引用，最终给客户端调用。
 */
public class Strategy01 {
    public static void main(String[] args) {
        Strat strat = new Strat11();
        Conte conte = new Conte();
        conte.setStrat(strat);
        System.out.println(conte.calC(1,2));
        conte.setStrat(new Strat12());
        System.out.println(conte.calC(1,2));
    }
}
//strategy
interface Strat{
    Integer cal(Integer x,Integer y);
}
//concrete strategy
class Strat11 implements Strat{
    @Override
    public Integer cal(Integer x, Integer y) {
        return x+y;
    }
}
//concrete strategy
class Strat12 implements Strat{
    @Override
    public Integer cal(Integer x, Integer y) {
        return x-y;
    }
}
//context
class Conte{
    private Strat strat;

    public Strat getStrat() {
        return strat;
    }

    public void setStrat(Strat strat) {
        this.strat = strat;
    }
    public Integer calC(Integer x,Integer y){
        return strat.cal(x,y);
    }
}

