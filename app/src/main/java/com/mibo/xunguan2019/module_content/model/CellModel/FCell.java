package com.mibo.xunguan2019.module_content.model.CellModel;

import java.io.Serializable;
import java.util.List;

/**
 * Author liuyangchao
 * Date on 2019/6/25.9:43
 */

public class FCell implements Serializable {

    private List<SCell> check;
    private String description;

    public List<SCell> getCheck() {
        return check;
    }

    public void setCheck(List<SCell> check) {
        this.check = check;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
