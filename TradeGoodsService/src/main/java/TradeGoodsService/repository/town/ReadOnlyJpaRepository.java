package TradeGoodsService.repository.town;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ReadOnlyJpaRepository<T,ID> extends Repository<T,ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    //https://www.baeldung.com/spring-data-read-only-repository
}
