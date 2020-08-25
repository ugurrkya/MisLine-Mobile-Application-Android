package com.misline.jua.misline;

public class User {

    private String identity;
    private String userid;
    private String studentphoto;
    private String bio;
    private String studentclass;
    private String email;
    private String studentnumber;
    private String status;
    private String search;

    public User(String identity, String userid, String studentphoto, String bio, String studentclass, String email, String studentnumber, String status, String search) {
        this.identity = identity;
        this.userid = userid;
        this.studentphoto = studentphoto;
        this.bio = bio;
        this.studentclass = studentclass;
        this.email = email;
        this.studentnumber = studentnumber;
        this.status= status;
        this.search = search;
    }

    public User() {

    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStudentphoto() {
        return studentphoto;
    }

    public void setStudentphoto(String studentphoto) {
        this.studentphoto = studentphoto;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getStudentclass() {
        return studentclass;
    }

    public void setStudentclass(String studentclass) {
        this.studentclass = studentclass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStudentnumber() {
        return studentnumber;
    }

    public void setStudentnumber(String studentnumber) {
        this.studentnumber = studentnumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
