package losango;


import losango.domain.Tile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface TileRepository extends MongoRepository<Tile, String> {

    public Tile findByCode(String code);
    public List<Tile> findByRow(String row);
    public List<Tile> findByColumn(String column);

}
