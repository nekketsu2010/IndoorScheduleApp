package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

public class DocumentClass {
    private String documentName;
    private String documentPass;
    private boolean open = true;

    public DocumentClass(){

    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentPass(String documentPass) {
        this.documentPass = documentPass;
    }

    public String getDocumentPass() {
        return documentPass;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }
}
