package yeogi.moim.participant.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeogi.moim.common.BaseTimeEntity;
import yeogi.moim.gathering.entity.Gathering;
import yeogi.moim.member.entity.Member;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "gatheringId")
    private Gathering gathering;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "rating")
    private double rating;

    public Participant(Member member, Gathering gathering, Role role, double rating) {
        this.member = member;
        this.gathering = gathering;
        this.role = role;
        this.rating = rating;
    }
}
