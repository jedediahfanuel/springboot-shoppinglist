package shoppinglist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import shoppinglist.entity.DaftarBelanjaDetil;

public interface DaftarBelanjaDetilRepo extends JpaRepository<DaftarBelanjaDetil, Long>
{
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM public.daftarbelanjadetil WHERE daftarbelanja_id = :id", nativeQuery = true)
    void deleteByDaftarbelanja_id(@Param("id") long daftarbelanja_id);
}
