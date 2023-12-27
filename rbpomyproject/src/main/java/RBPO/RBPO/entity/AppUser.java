package RBPO.RBPO.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

import java.util.List;


@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppUser {
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)//возможно изза этой строки у нас проблема с айдишниками
    @SequenceGenerator(name = "user_seq",
            sequenceName = "user_sequence",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name = "user_id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "passwordHash")
    private String passwordHash;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<MedicalRecords> medicalRecordss;
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<Comment> comments;

    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<MedicalRecords> getmedicalRecordss() {
        return medicalRecordss;
    }

    public void setmedicalRecordss(List<MedicalRecords> medicalRecordss) {
        this.medicalRecordss = medicalRecordss;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addmedicalRecordsToUser(MedicalRecords medicalRecords) {
        this.medicalRecordss.add(medicalRecords);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
