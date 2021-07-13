package top.bluewort.Notes.design_mode.X023_interpreter;

import java.util.HashSet;
import java.util.Set;

/**
 * 解释器模式
 * 抽象表达式（Abstract Expression）角色：定义解释器的接口，约定解释器的解释操作，主要包含解释方法 interpret()。
 * 终结符表达式（Terminal Expression）角色：是抽象表达式的子类，用来实现文法中与终结符相关的操作，文法中的每一个终结符都有一个具体终结表达式与之相对应。
 * 非终结符表达式（Nonterminal Expression）角色：也是抽象表达式的子类，用来实现文法中与非终结符相关的操作，文法中的每条规则都对应于一个非终结符表达式。
 * 环境（Context）角色：通常包含各个解释器需要的数据或是公共的功能，一般用来传递被所有解释器共享的数据，后面的解释器可以从这里获取这些值。
 * 客户端（Client）：主要任务是将需要分析的句子或表达式转换成使用解释器对象描述的抽象语法树，然后调用解释器的解释方法，当然也可以通过环境角色间接访问解释器的解释方法。
 */
public class Interpreter01 {
    public static void main(String[] args) {
        Context bus = new Context();
        bus.freeRide("韶关的老人");
        bus.freeRide("韶关的年轻人");
        bus.freeRide("广州的妇女");
        bus.freeRide("广州的儿童");
        bus.freeRide("山东的儿童");
    }
}
//抽象表达式
interface Expression{
    boolean interpret(String info);
}
//终结符表达式
class TerminalExpression implements Expression{
    private Set<String> set = new HashSet<>();
    public TerminalExpression(String[] data){
        for(int i=0;i<data.length;i++) set.add(data[i]);
    }
    @Override
    public boolean interpret(String info) {
        if(set.contains(info)){
            return true;
        }
        return false;
    }
}
//非终结符表达式
class AndExpression implements Expression{
    private Expression city= null;
    private Expression person = null;
    public AndExpression(Expression city,Expression person){
        this.city = city;
        this.person = person;
    }
    @Override
    public boolean interpret(String info) {
        String s[] = info.split("的");
        return city.interpret(s[0])&&person.interpret(s[1]);
    }
}
//环境类
class Context{
    private String[] citys = {"韶关","广州"};
    private String[] persons = {"老人","妇女","儿童"};
    private Expression cityPerson;

    public Context(){
        Expression city = new TerminalExpression(citys);
        Expression person = new TerminalExpression(persons);
        cityPerson = new AndExpression(city, person);
    }
    public void freeRide(String info) {
        boolean ok = cityPerson.interpret(info);
        if (ok) System.out.println("您是" + info + "，您本次乘车免费！");
        else System.out.println(info + "，您不是免费人员，本次乘车扣费2元！");
    }
}
