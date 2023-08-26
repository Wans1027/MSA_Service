package MemberManagement.repository;

import MemberManagement.entity.Member;
import MemberManagement.entity.NeighborhoodTown;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TownRepository extends JpaRepository<NeighborhoodTown, Long> {
    @Transactional
    @EntityGraph(attributePaths = {"member"})
    @Query("select t from NeighborhoodTown t where t.member = :member")
    List<NeighborhoodTown> findByMember(@Param("member") Member member);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("delete from NeighborhoodTown t where t.member = :member")
    void deleteAllByMember(@Param("member") Member member);


    /*@Transactional
    @Modifying
    @Query("delete from Customer c where c.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);*/
}
