package MemberManagement.service;

import MemberManagement.entity.Member;
import MemberManagement.entity.NeighborhoodTown;
import MemberManagement.repository.MemberRepository;
import MemberManagement.repository.TownRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TownServiceTest {
    @Autowired TownService townService;
    @Autowired
    TownRepository townRepository;
    @Autowired
    TownSearchService townSearchService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    public void regTownTest(){
        //given
        Member member = new Member("Kim");
        memberRepository.save(member);
        townService.regTown(member, 127.103179, 37.512567);//잠실타워좌표 송파구 잠실6동
        //when
        List<List<String>> searchedTown = townSearchService.geoJsonRead(127.103179, 37.512567, 5d);
        List<NeighborhoodTown> townList = townRepository.findByMember(member);
        //then
        Assertions.assertThat(townList.size()).isEqualTo(searchedTown.size());
        int myTownCnt = 0;
        for (int i = 0; i < townList.size(); i++) {
            List<String> townInfo = searchedTown.get(i);
            String townName = townInfo.get(0);
            Integer townCode = Integer.parseInt(townInfo.get(1));
            Boolean isMyTown = Boolean.parseBoolean(townInfo.get(2));
            if(townList.get(i).getMyTown()) myTownCnt++;
            Assertions.assertThat(townList.get(i).getTownCode()).isEqualTo(townCode);
        }
        Assertions.assertThat(myTownCnt).isEqualTo(1);

    }

}