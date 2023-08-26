package TradeGoodsService.repository.town;

import TradeGoodsService.entity.town.NeighborhoodTown;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class testRepoTest {
    @Autowired
    TownRepository repo;
    @Test
    void test(){
        /*Optional<NeighborhoodTown> town = repo.findById(21L);
        System.out.println(town.orElseThrow().getTownName());*/
        int size = repo.findTownByMemberId(1L).size();
        System.out.println(size);

        int size1 = repo.findMyTownByMemberId(1L).size();
        System.out.println(size1);
    }

}