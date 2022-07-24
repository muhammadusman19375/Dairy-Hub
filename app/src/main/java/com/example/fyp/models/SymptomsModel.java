package com.example.fyp.models;

public class SymptomsModel {
    String symptomName;
    Boolean isChecked;

    public SymptomsModel() {
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
