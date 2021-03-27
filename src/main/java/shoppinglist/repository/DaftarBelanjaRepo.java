/*
  DaftarBelanjaRepo.java

  Created on Mar 22, 2021, 1:37:18 PM
 */
package shoppinglist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppinglist.entity.DaftarBelanja;

import java.util.List;

/**
 *
 * @author jeded
 */
public interface DaftarBelanjaRepo extends JpaRepository<DaftarBelanja, Long>
{
    List<DaftarBelanja> findByJudulIgnoreCaseContaining(String judul);
    DaftarBelanja findTopByOrderByIdDesc();
}
