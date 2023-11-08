package com.project.readers.repository;

import com.project.readers.entity.ReplyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyDAO {

    List<ReplyDTO> getReplyList(Integer boardNum);
    ReplyDTO getReplyOne(Integer rplNum);
    void createReply(ReplyDTO replyDTO);
    void updateReply(ReplyDTO replyDTO);
    void deleteReply(Integer rplNum);
}
