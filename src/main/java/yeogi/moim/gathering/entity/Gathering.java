package yeogi.moim.gathering.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeogi.moim.common.BaseTimeEntity;
import yeogi.moim.member.entity.Member;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    public Gathering(String title, String description, int totalPersonnel, int currentPersonnel, Member member) {
        this.title = title;
        this.description = description;
        this.totalPersonnel = totalPersonnel;
        this.currentPersonnel = currentPersonnel;
        this.member = member;
    }

    public void update(String title, String description, int totalPersonnel) {
        this.title = title;
        this.description = description;
        this.totalPersonnel = totalPersonnel;
    }
}
