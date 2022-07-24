package com.example.fyp.models;

public class ImagesUploadModel {
    String uid,selectedImage,selectedImage1,selectedImage2,pymcNumber;
    boolean isVerified;

    public ImagesUploadModel() {
    }

    public ImagesUploadModel(String uid, String selectedImage, String selectedImage1, String selectedImage2, String pymcNumber, boolean isVerified) {
        this.uid = uid;
        this.selectedImage = selectedImage;
        this.selectedImage1 = selectedImage1;
        this.selectedImage2 = selectedImage2;
        this.pymcNumber = pymcNumber;
        this.isVerified = isVerified;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(String selectedImage) {
        this.selectedImage = selectedImage;
    }

    public String getSelectedImage1() {
        return selectedImage1;
    }

    public void setSelectedImage1(String selectedImage1) {
        this.selectedImage1 = selectedImage1;
    }

    public String getSelectedImage2() {
        return selectedImage2;
    }

    public void setSelectedImage2(String selectedImage2) {
        this.selectedImage2 = selectedImage2;
    }

    public String getPymcNumber() {
        return pymcNumber;
    }

    public void setPymcNumber(String pymcNumber) {
        this.pymcNumber = pymcNumber;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
