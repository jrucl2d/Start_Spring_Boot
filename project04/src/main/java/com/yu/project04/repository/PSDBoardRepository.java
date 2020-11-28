package com.yu.project04.repository;

import com.yu.project04.domain.PSDBoard;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// One에 해당하는 Repository만 생성하는 것이 좋다. Domain Driven Design에서는 Aggregation이라고 부른다.
public interface PSDBoardRepository extends CrudRepository<PSDBoard, Long> {

    @Modifying // @Query는 select만 지원하지만 @Modifying 사용하면 DML 가능
    @Query("UPDATE PSDFile f SET f.pdsfile = ?2 WHERE f.fno = ?1")
    public int updatePSDFile(Long fno, String newFileName);

    @Modifying
    @Query("DELETE FROM PSDFile f WHERE f.fno = ?1")
    public int deletePsdFile(Long fno);

    @Query("SELECT p, count(f) FROM PSDBoard p LEFT OUTER JOIN p.files f WHERE p.pid > 0 GROUP BY p ORDER BY p.pid DESC")
    public List<Object[]> getSummary();
}
