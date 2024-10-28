package yeogi.moim.participant.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeogi.moim.gathering.entity.Gathering;
import yeogi.moim.member.entity.Member;
import yeogi.moim.common.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participant extends BaseEntity {

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
    private Double rating;

    public Participant(Member member, Gathering gathering, Role role, Double rating) {
        this.member = member;
        this.gathering = gathering;
        this.role = role;
        this.rating = rating;
    }
}
