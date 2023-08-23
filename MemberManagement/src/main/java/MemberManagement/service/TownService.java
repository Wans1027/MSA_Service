package MemberManagement.service;

import MemberManagement.entity.Member;
import MemberManagement.entity.NeighborhoodTown;
import MemberManagement.repository.TownRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TownService {
    private final TownRepository townRepository;
    private final TownSearchService townSearchService;
    @Transactional
    public void regTown(Member member, double positionX, double positionY) {
        List<List<String>> townList = townSearchService.geoJsonRead(positionX, positionY, 5d);
        //기존데이터가 있다면 삭제
        if(!townRepository.findByMember(member).isEmpty()) townRepository.deleteAllByMember(member);
        for (List<String> townInfo : townList) {
            String townName = townInfo.get(0);
            Integer townCode = Integer.parseInt(townInfo.get(1));
            Boolean isMyTown = Boolean.parseBoolean(townInfo.get(2));
            NeighborhoodTown neighborhoodTown = new NeighborhoodTown(member, townCode, townName, isMyTown);
            townRepository.save(neighborhoodTown);
        }
    }

}
