package yeogi.moim.gathering.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeogi.moim.common.BaseTimeEntity;
import yeogi.moim.member.entity.Member;
import yeogi.moim.participant.entity.Participant;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gathering extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "totalPersonnel")
    private int totalPersonnel;

    @Column(name = "currentPersonnel")
    private int currentPersonnel;

    @OneToMany(mappedBy = "gathering")
    private List<Participant> participantList = new ArrayList<>();

    public Gathering(String title, String description, int totalPersonnel, int currentPersonnel) {
        this.title = title;
        this.description = description;
        this.totalPersonnel = totalPersonnel;
        this.currentPersonnel = currentPersonnel;
    }

    public void update(String title, String description, int totalPersonnel) {
        this.title = title;
        this.description = description;
        this.totalPersonnel = totalPersonnel;
    }
}
