package top.bluewort.Notes.base_utils.X007_find_annotaction;

import lombok.Data;

@Data
@Beauty(name = "test",isOk = false)
public class BeautyTest {
    @Beauty
    private String name;

    @Beauty
    public void getName(){

    }
}
