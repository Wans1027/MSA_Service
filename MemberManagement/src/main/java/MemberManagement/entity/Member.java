package MemberManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends TimeEntity{
    @Id
    @Column(name = "member_id")
    @GeneratedValue
    private Long id;
    @Column(name = "member_name",unique = true)
    private String memberName;
    private String memberPicture;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<NeighborhoodTown> neighborhoodTownList;


    public Member(String memberName, String memberPicture) {
        this.memberName = memberName;
        this.memberPicture = memberPicture;
    }

    public Member(String memberName) {
        this.memberName = memberName;
    }
}
