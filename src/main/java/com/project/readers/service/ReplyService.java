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

    public void createReply(ReplyDTO replyDTO) throws SecurityException{
        if (SessionConfig.getSessionDTO().getId() == null) {
            throw new SecurityException("회원만 작성할 수 있습니다.");
        }
//        if ((replyDTO.getRplNum() == null || "".equals(replyDTO.getRplNum())) && SessionConfig.getSessionDTO().getId() != null)
            replyDAO.createReply(replyDTO);
    }
    public List<ReplyDTO> getReplyList(Integer boardNum) {
        return replyDAO.getReplyList(boardNum);
    }

    public ReplyDTO getReplyOne(Integer rplNum) {
        return replyDAO.getReplyOne(rplNum);
    }

    public void updateReply(ReplyDTO replyDTO) throws SecurityException {
        String currentUserId = SessionConfig.getSessionDTO().getId();
        String creatorId = replyDTO.getCreator();

        if (currentUserId == null || !currentUserId.equals(creatorId)) {
            throw new SecurityException("게시물의 작성자만 수정할 수 있습니다.");
        }
//        if(!(replyDTO.getRplNum() == null || "".equals(replyDTO.getRplNum())))
            replyDAO.updateReply(replyDTO);
    }

    public void deleteReply(Integer rplNum) throws SecurityException{
        ReplyDTO replyDTO = replyDAO.getReplyOne(rplNum);
        if (!replyDTO.getCreator().equals(SessionConfig.getSessionDTO().getId())) {
            throw new SecurityException("게시물의 작성자만 삭제할 수 있습니다.");
        }
        replyDAO.deleteReply(rplNum);
    }
}
