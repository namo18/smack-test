package com.mycompany.myfirstapp.dtabase;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    @Id
    private Long id;
    private String name;
    private String Group;
    @Generated(hash = 1703015971)
    public User(Long id, String name, String Group) {
        this.id = id;
        this.name = name;
        this.Group = Group;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGroup() {
        return this.Group;
    }
    public void setGroup(String Group) {
        this.Group = Group;
    }
}
