package com.example.bpm.entity;

import javax.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;

import java.io.Serializable;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Slf4j
@Table(name = "project_role")
@IdClass(ProjectRolePKEntity.class)
public class ProjectRoleEntity implements Serializable {
//
//    @EmbeddedId
//    private ProjectRolePKEntity id;

    //    @MapsId("projectId")
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private ProjectEntity projectIdInRole;

    //    @MapsId("uuid")
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uuid", referencedColumnName = "uuid")
    private UserEntity uuidInRole;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role")
    private RoleEntity role;

    public static ProjectRoleEntity toProjectRoleEntity(ProjectEntity projectEntity, UserEntity userEntity, RoleEntity roleEntity) {
        ProjectRoleEntity projectRoleEntity = new ProjectRoleEntity();
        projectRoleEntity.setProjectIdInRole(projectEntity);
        projectRoleEntity.setUuidInRole(userEntity);
        projectRoleEntity.setRole(roleEntity);
        return projectRoleEntity;
    }
//
//    @Override
//    public boolean isNew(){
//        return role == null;
//    }


}
