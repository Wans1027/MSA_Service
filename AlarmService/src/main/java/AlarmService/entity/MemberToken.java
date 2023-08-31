package AlarmService.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class MemberToken {
    @Id
    @GeneratedValue
    @Column(name = "member_token_id")
    private Long id;
    @NotEmpty
    private Long memberId;
    @NotEmpty
    private String token;

    public MemberToken(Long memberId, String token) {
        this.memberId = memberId;
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
