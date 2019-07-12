package com.mibo.xunguan2019.module_content.model.CellModel;

import java.io.Serializable;

/**
 * Author liuyangchao
 * Date on 2019/6/25.9:43
 */

public class SCell implements Serializable {

    private String Id;
    private String inputValue;          //false,true
    private String labelName;
    private String meType;          //(填值、打钩)

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getMeType() {
        return meType;
    }

    public void setMeType(String meType) {
        this.meType = meType;
    }
}
