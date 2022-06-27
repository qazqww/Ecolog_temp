package com.thedebuggers.backend.domain.repository.user;

import com.thedebuggers.backend.domain.entity.user.UserAsset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAssetRepository extends JpaRepository<UserAsset, Long> {

    boolean existsByUserNoAndAssetNo(long userNo, long assetNo);

    List<UserAsset> findAllByUserNo(long userNo);
}
