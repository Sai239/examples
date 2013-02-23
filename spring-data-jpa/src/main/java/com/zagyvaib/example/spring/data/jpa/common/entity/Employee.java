package com.zagyvaib.example.spring.data.jpa.common.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String pwdHash;

    @ManyToOne
    private Employee manager;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "manager")
    private Set<Employee> directReports = new HashSet<>();

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();

    @Version
    private Integer version;

    // ----------------------------------------------------------------------------------------------------------------

    public Employee setManager(Employee manager) {
        this.manager = manager;
        manager.getDirectReports().add(this);
        return this;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                      .add("id", id)
                      .add("version", version)
                      .add("created", created)
                      .add("name", name)
                      .add("email", email)
                      .add("manager", manager)
                      .add("pwdHash", pwdHash)
                      .add("directReports", directReports)
                      .toString();
    }

    //region ####### equals() and hashCode() #######
    @Override
    public boolean equals(Object o) {
        return o instanceof Employee &&
               Objects.equal(email, ((Employee) o).getEmail());
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
    //endregion

    //region ####### Getters and Setters #######
    public Long getId() {
        return id;
    }

    public Employee setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Employee setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Employee setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPwdHash() {
        return pwdHash;
    }

    public Employee setPwdHash(String pwdHash) {
        this.pwdHash = pwdHash;
        return this;
    }

    public Employee getManager() {
        return manager;
    }

    public Set<Employee> getDirectReports() {
        return directReports;
    }

    public Employee setDirectReports(Set<Employee> directReports) {
        this.directReports = directReports;
        return this;
    }

    public Date getCreated() {
        return created;
    }
    //endregion
}
