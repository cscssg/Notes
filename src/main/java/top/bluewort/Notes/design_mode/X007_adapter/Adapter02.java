package top.bluewort.Notes.design_mode.X007_adapter;

/**
 * 适配器模式
 * 对象适配模式
 */
public class Adapter02 {
    public static void main(String[] args) {
        Adapter02Target adapter02Target = new Adapter02Adapter(new Adaptee02Adapter());
        adapter02Target.req();
    }
}

interface Adapter02Target{
    void req();
}

class Adaptee02Adapter{
    public void get(){
        System.out.println("我被适配了" +
                "");
    }
}
class Adapter02Adapter implements Adapter02Target{

    private Adaptee02Adapter adaptee02Adapter;
    public Adapter02Adapter(Adaptee02Adapter adaptee02Adapter){
        this.adaptee02Adapter = adaptee02Adapter;
    }
    @Override
    public void req() {
        adaptee02Adapter.get();
        System.out.println("被对象适配了");
    }
}
