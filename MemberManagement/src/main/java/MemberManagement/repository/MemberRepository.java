package MemberManagement.repository;

import MemberManagement.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Transactional
    @Query("select m from Member m where m.id in :ids")
    List<Member> findMembersInfoById(@Param("ids") List<Long> ids);
}
