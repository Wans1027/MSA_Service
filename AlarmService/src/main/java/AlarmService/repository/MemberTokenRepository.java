package AlarmService.repository;

import AlarmService.entity.MemberToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberTokenRepository extends JpaRepository<MemberToken, Long> {

    @Transactional(readOnly = true)
    @Query("select t from MemberToken t where t.memberId = :memberId")
    Optional<MemberToken> findByMemberId(@Param("memberId") Long memberId);

    @Transactional(readOnly = true)
    @Query("select t.token from MemberToken t where t.memberId in :memberIds")
    List<String> findTokensByMemberIdList(@Param("memberIds") List<Long> memberIdList);
}
