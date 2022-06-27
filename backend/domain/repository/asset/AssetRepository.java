package com.thedebuggers.backend.domain.repository.asset;

import com.thedebuggers.backend.domain.entity.asset.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {

}
