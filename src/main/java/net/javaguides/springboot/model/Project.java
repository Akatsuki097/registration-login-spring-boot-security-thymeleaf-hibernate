package net.javaguides.springboot.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long projectId;

    @NotBlank(message = "PROJECT NAME is mandatory")
    @Column(name = "project_name", length = 200, nullable = false)
    private String projectName;

    @Column(name = "intro", length = 300)
    private String intro;


    @Column(name = "start_date_time", nullable = false)
    private Date startDateTime;


    @Column(name = "end_date_time", nullable = false)
    private Date endDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="project_owner")
    private User projectOwner;
//    @OneToOne(
//
//    )
//    private User owner;

    @ManyToMany(mappedBy = "projects")
    private Set<User> users = new HashSet<>();



}
