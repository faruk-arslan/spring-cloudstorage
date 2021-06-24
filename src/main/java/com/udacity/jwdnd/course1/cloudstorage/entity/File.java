package com.udacity.jwdnd.course1.cloudstorage.entity;

import java.sql.Blob;

public class File {
    private int fileid;
    private String filename;
    private String contenttype;
    private long filesize;
    private int userid;
    private byte[] filedata;

    public int getFileid() {
        return fileid;
    }

    public void setFileid(int fileId) {
        this.fileid = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }
}
