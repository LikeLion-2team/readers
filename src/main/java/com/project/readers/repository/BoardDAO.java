package com.project.readers.repository;

import com.project.readers.entity.BoardDTO;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface BoardDAO {

    void createBoard(BoardDTO boardDTO);
    Integer getBoardCount(BoardDTO boardDTO);
    List<BoardDTO> getBoardList(BoardDTO boardDTO);

    BoardDTO getBoardOne(Integer boardNum);

    void deleteBoard(Integer boardNum);

    void updateBoard(BoardDTO boardDTO);
}
