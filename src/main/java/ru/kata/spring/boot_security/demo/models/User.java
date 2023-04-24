package ru.kata.spring.boot_security.demo.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;


@Entity
@Table(name = "users")
public class User implements UserDetails {

//-------------ID-------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

//-------------NAME-----------------------------------------------
    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 character")
    private String username;

//-------------AGE------------------------------------------------
    @Column(name = "age")
    @Min(value = 0, message = "Age should be greater than 0")
    private int age;

//-------------EMAIL----------------------------------------------
    @Column(name = "email")
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

//-----------PASSWORD---------------------------------------------
    @Size(min = 2, max = 100, message = "Password must be between 2 and 100 characters")
    @NotNull
    @Column(name = "password")
    private String password;

//-------------ROLE-----------------------------------------------
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Role> roles = new ArrayList<>();




    public User() {
    }

    public User(String username, int age, String email) {
        this.username = username;
        this.age = age;
        this.email = email;
    }



    //-------------------------Getters/Setters---------------------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }




    //--------------------Метод для добавления роли в колекшн буду  использовать в регистрации что бы
    //по умолчанию задать роль

    public void setRoles(Role role) {
        this.roles.add(role);
    }


    //метод для занесения в  Collection<Role> новых ролей,использую в сервисе для update чтобы назначать несколько ролей для юзера
    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
    //метод для возврата Collection<Role> использую в сервисе для update чтобы  назначать несколько ролей для юзера
    public Collection<Role> getRoles() {
        return this.roles;
    }

    //метод для вывода коллекции ролей без []
    public String getToStringRoleCollection() {
        return roles.toString().replace("]", "").replace("[", "");
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}



