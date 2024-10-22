package yeogi.moim.gathering.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeogi.moim.common.BaseAuditableEntity;
import yeogi.moim.participant.entity.Participant;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gathering extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "totalPersonnel", nullable = false)
    private Integer totalPersonnel;

    @Column(name = "currentPersonnel")
    private Integer currentPersonnel;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "gathering")
    private List<Participant> participantList = new ArrayList<>();

    public Gathering(String title, String description, Category category, Integer totalPersonnel, Integer currentPersonnel) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.totalPersonnel = totalPersonnel;
        this.currentPersonnel = currentPersonnel;
    }

    public void update(String title, String description, Integer totalPersonnel) {
        this.title = title;
        this.description = description;
        this.totalPersonnel = totalPersonnel;
    }
}
