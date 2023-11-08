package com.project.readers.service;

import com.project.readers.entity.BoardDTO;
import com.project.readers.entity.ReplyDTO;
import com.project.readers.repository.ReplyDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyDAO replyDAO;

    public List<ReplyDTO> getReplyList(Integer boardNum) {
        return replyDAO.getReplyList(boardNum);
    }

    public ReplyDTO getReplyOne(Integer rplNum) {
        return replyDAO.getReplyOne(rplNum);
    }

    public void createReply(ReplyDTO replyDTO) {
        if (replyDTO.getRplNum() == null || "".equals(replyDTO.getRplNum()))
            replyDAO.createReply(replyDTO);
    }

    public void updateReply(ReplyDTO replyDTO) {
        if(!(replyDTO.getRplNum() == null || "".equals(replyDTO.getRplNum())))
            replyDAO.updateReply(replyDTO);
    }

    public void deleteReply(Integer rplNum) {
        replyDAO.deleteReply(rplNum);
    }
}
