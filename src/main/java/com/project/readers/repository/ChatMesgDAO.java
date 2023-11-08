package com.project.readers.repository;

import com.project.readers.entity.ChatMesgDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMesgDAO {

    void insertChatMesg(ChatMesgDTO chatMesgDTO);

}
