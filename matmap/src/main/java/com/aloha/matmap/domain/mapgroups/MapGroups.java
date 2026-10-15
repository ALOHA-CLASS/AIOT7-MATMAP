package com.aloha.matmap.domain.mapgroups;

import jakarta.persistence.*;
import java.time.LocalDateTime;

public class MapGroups {

@Entity
@Table(name = "map_groups")
public class MapGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long no;

    @Column(name = "id", nullable = false, unique = true, length = 64)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_no", nullable = false)
    private User owner;

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

    protected MapGroup() {
    }

    public MapGroup(
            String id,
            User owner,
            String name,
            String description,
            MapVisibility visibility
    ) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.visibility = visibility == null
                ? MapVisibility.PRIVATE
                : visibility;
    }

    public Long getNo() {
        return no;
    }

    public String getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public MapVisibility getVisibility() {
        return visibility;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

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
}
}
