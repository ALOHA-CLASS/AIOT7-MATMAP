package com.aloha.matmap.domain.mapgroups;

import java.time.LocalDateTime;

import com.aloha.matmap.domain.users.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data 
@Entity
@Table(name = "map_groups")
public class MapGroups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long no;

    @Column(name = "id", nullable = false, unique = true, length = 64)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_no", nullable = false)
    private Users owner;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private MapVisibility visibility = MapVisibility.PRIVATE;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    public void update(
            String name,
            String description,
            MapVisibility visibility
    ) {
        this.name = name;
        this.description = description;
        if (visibility != null) {
            this.visibility = visibility;
        }
    }

    public MapGroups(String publicId, Users owner,
            String name,
            String description, MapVisibility visibility) {
        this.id = publicId;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.visibility = visibility;
    }
}
