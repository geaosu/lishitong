package com.geaosu.mydialog;

public class ButtonBean {
    private String id;
    private String name;
    private boolean checked;

    public ButtonBean() {

    }

    public ButtonBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ButtonBean(String id, String name, boolean checked) {
        this.id = id;
        this.name = name;
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
