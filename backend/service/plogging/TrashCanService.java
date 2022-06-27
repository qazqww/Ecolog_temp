package com.thedebuggers.backend.service.plogging;

import com.thedebuggers.backend.domain.entity.user.User;
import com.thedebuggers.backend.dto.plogging.TrashCanReqDto;
import com.thedebuggers.backend.dto.plogging.TrashCanResDto;
import org.locationtech.jts.io.ParseException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TrashCanService {
    TrashCanResDto registTrashCan(TrashCanReqDto trashCanReqDto, MultipartFile imageFile, User user) throws ParseException;

    List<TrashCanResDto> getTrashCanList(double lat, double lng, double range);

    TrashCanResDto updateTrashCan(long trashCanNo, TrashCanReqDto trashCanReqDto, MultipartFile imageFile, User user) throws ParseException;

    boolean deleteTrashCan(long trashCanNo, User user);
}
