package MemberManagement.repository;

import MemberManagement.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Transactional
    @Query("select m from Member m where m.id in :ids")
    List<Member> findMembersInfoById(@Param("ids") List<Long> ids);
}
