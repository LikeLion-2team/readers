package com.project.readers.service;

import com.project.readers.config.SessionConfig;
import com.project.readers.entity.ReplyDTO;
import com.project.readers.repository.ReplyDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {

    private final ReplyDAO replyDAO;

    public void createReply(ReplyDTO replyDTO) throws SecurityException {
        String currentUserId = SessionConfig.getSessionDTO().getId();
        if (currentUserId == null) {
            throw new SecurityException("회원만 작성할 수 있습니다.");
        }
        if (replyDTO.getRplContent() == null || replyDTO.getRplContent().isEmpty()) {
            throw new IllegalArgumentException("내용을 입력하세요");
        }
        replyDTO.setCreator(currentUserId); // creator 필드에 현재 사용자 ID 설정
        replyDAO.createReply(replyDTO);
    }

    public List<ReplyDTO> getReplyList(Integer boardNum) {
        return replyDAO.getReplyList(boardNum);
    }

    public ReplyDTO getReplyOne(Integer rplNum) {
        return replyDAO.getReplyOne(rplNum);
    }


    public void updateReply(Integer rplNum, ReplyDTO replyDTO) throws SecurityException {
        String currentUserId = SessionConfig.getSessionDTO().getId();
        ReplyDTO originalReplyDTO = replyDAO.getReplyOne(rplNum);

        if (currentUserId == null || !currentUserId.equals(originalReplyDTO.getCreator())) {
            throw new SecurityException("게시물의 작성자만 수정할 수 있습니다.");
        }
        if (replyDTO.getRplContent() == null || replyDTO.getRplContent().isEmpty()) {
            throw new IllegalArgumentException("내용을 입력하세요");
        }
        replyDTO.setRplNum(rplNum);
        replyDAO.updateReply(replyDTO);
    }

    public void deleteReply(Integer rplNum) throws SecurityException {
        ReplyDTO replyDTO = replyDAO.getReplyOne(rplNum);
        if (!replyDTO.getCreator().equals(SessionConfig.getSessionDTO().getId())) {
            throw new SecurityException("게시물의 작성자만 삭제할 수 있습니다.");
        }
        replyDAO.deleteReply(rplNum);
    }
}