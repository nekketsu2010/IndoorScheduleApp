package com.indoorscheduleapp.nekketsu.indoorscheduleapp;

import android.net.Uri;


public class DocumentClass {
    private String documentName;
    private String documentPass;
    private Uri uri;
    private boolean open = true; //自動オープンするか？(デフォルトでTrue)

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

    public void setUri(Uri uri){this.uri = uri;}

    public Uri getUri() { return uri; }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }
}
