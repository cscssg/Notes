package top.bluewort.Notes.design_mode.X011_flyweight;

import java.util.HashMap;

/**
 * 享元模式
 * 抽象享元角色（Flyweight）：是所有的具体享元类的基类，为具体享元规范需要实现的公共接口，非享元的外部状态以参数的形式通过方法传入。
 * 具体享元（Concrete Flyweight）角色：实现抽象享元角色中所规定的接口。
 * 非享元（Unsharable Flyweight)角色：是不可以共享的外部状态，它以参数的形式注入具体享元的相关方法中。
 * 享元工厂（Flyweight Factory）角色：负责创建和管理享元角色。当客户对象请求一个享元对象时，享元工厂检査系统中是否存在符合要求的享元对象，
 * 如果存在则提供给客户；如果不存在的话，则创建一个新的享元对象。
 *
 * 类似于数据库连接池和线程池
 */
public class Flyweight01 {
    public static void main(String[] args) {
        ShareFactory shareFactory = new ShareFactory();
        Shara a1 = shareFactory.getShara("a");
        Shara a2 = shareFactory.getShara("a");
        Shara a3 = shareFactory.getShara("a");
        Shara b1 = shareFactory.getShara("b");
        Shara b2 = shareFactory.getShara("b");
        Shara b3 = shareFactory.getShara("b");
        a1.oprate(new UnSharaRole("dada"));
        a2.oprate(new UnSharaRole("dada2"));
        a3.oprate(new UnSharaRole("dada3"));
        b1.oprate(new UnSharaRole("dada4"));
        b2.oprate(new UnSharaRole("dada5"));
        b3.oprate(new UnSharaRole("dada6"));
    }
}
//非享元角色
class UnSharaRole{
    public String state;
    UnSharaRole(String s){
        state = s;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
//抽象享元角色
interface Shara{
    public void oprate(UnSharaRole unSharaRole);
}
//具体像元角色
class ShareImpl implements Shara{
    public String key;
    ShareImpl(String key){
        this.key = key;
        System.out.println("具体享元角色"+key+"本创建");
    }
    @Override
    public void oprate(UnSharaRole unSharaRole) {
        System.out.println("享元角色为::"+key+",非享元角色被使用：："+unSharaRole.getState());
    }
}
//享元工厂
class ShareFactory{
    private HashMap<String,Shara> map = new HashMap<>();

    public Shara getShara(String key){
        Shara shara = map.get(key);
        if(shara != null){
            System.out.println("具体享元"+key+"已经存在");
        }else {
            shara = new ShareImpl(key);
            map.put(key,shara);
        }
        return shara;
    }
}
