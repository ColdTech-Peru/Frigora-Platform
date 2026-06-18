package com.frigora.frigoraplatform.assetsManagement.domain.repositories;

import com.frigora.frigoraplatform.assetsManagement.domain.model.aggregates.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISiteRepository extends JpaRepository<Site, Integer> {

    // - save(Site entity)
    // - findById(Integer id)
    // - delete(Site entity)
    // - findAll()
    // - Y muchos más...

}
