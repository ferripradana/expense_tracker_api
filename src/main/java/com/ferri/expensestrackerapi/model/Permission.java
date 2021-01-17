package com.ferri.expensestrackerapi.model;

import javax.persistence.*;

@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String workspace;

    @Basic
    private Boolean read;

    @Basic
    private Boolean write;

    @Basic
    private Boolean delete;

    public Permission() {
        super();
    }

    public Permission(Long id, String workspace, Boolean read, Boolean write, Boolean delete) {
        super();
        this.id = id;
        this.workspace = workspace;
        this.read = read;
        this.write = write;
        this.delete = delete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getWrite() {
        return write;
    }

    public void setWrite(Boolean write) {
        this.write = write;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
}
