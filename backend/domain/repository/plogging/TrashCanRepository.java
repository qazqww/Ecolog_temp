package com.thedebuggers.backend.domain.repository.plogging;

import com.thedebuggers.backend.domain.entity.plogging.TrashCan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TrashCanRepository extends JpaRepository<TrashCan, Long> {

    @Query(
            value = "select distinct tc.* from trash_can tc where MBRContains(ST_LINESTRINGFROMTEXT(concat('LINESTRING(',:#{#x1}, ' ', :#{#y1}, ',', :#{#x2},' ',:#{#y2},')'), 4326), tc.location)"
            , nativeQuery = true
    )
    List<TrashCan> getTrashCanList(double x1, double y1, double x2, double y2);


    Optional<TrashCan> findByNo(long no);

}
