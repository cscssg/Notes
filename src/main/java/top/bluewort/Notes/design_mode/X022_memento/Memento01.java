package top.bluewort.Notes.design_mode.X022_memento;

/**
 * 备忘录模式
 * 发起人（Originator）角色：记录当前时刻的内部状态信息，提供创建备忘录和恢复备忘录数据的功能，实现其他业务功能，它可以访问备忘录里的所有信息。
 * 备忘录（Memento）角色：负责存储发起人的内部状态，在需要的时候提供这些内部状态给发起人。
 * 管理者（Caretaker）角色：对备忘录进行管理，提供保存与获取备忘录的功能，但其不能对备忘录的内容进行访问与修改。
 */
public class Memento01 {
    public static void main(String[] args) {
        //新建发起人
        Orig orig = new Orig();
        Care care = new Care();
        //发起人改变状态
        orig.setStat("love");
        System.out.println(orig.getStat());
        //发起人存备忘录
        care.setMeme(orig.createMeme());
        //发起人改变状态
        orig.setStat("you");
        System.out.println(orig.getStat());
        //发起人重置状态
        orig.resStat(care.getMeme());
        System.out.println(orig.getStat());
    }
}
//Meme
class Meme{
    private String stat;
    Meme(String stat){
        this.stat = stat;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
//Orig
class Orig{
    private String stat;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public Meme createMeme(){
        return new Meme(stat);
    }
    public void resStat(Meme meme){
        this.setStat(meme.getStat());
    }
}
//Care
class Care{
    private Meme meme;

    public Meme getMeme() {
        return meme;
    }

    public void setMeme(Meme meme) {
        this.meme = meme;
    }
}
