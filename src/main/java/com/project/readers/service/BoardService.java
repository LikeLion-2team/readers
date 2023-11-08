package com.project.readers.service;

import com.project.readers.entity.BoardDTO;
import com.project.readers.repository.BoardDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardDAO boardDAO;

    public void createBoard(BoardDTO boardDTO) {

        boardDAO.createBoard(boardDTO);
    }

    public Integer getBoardCount(BoardDTO boardDTO){
        return boardDAO.getBoardCount(boardDTO);
    }
    public List<BoardDTO> getBoardList(BoardDTO boardDTO){

		return boardDAO.getBoardList(boardDTO);
	}

    public BoardDTO getBoardOne(Integer boardNum) {
        return boardDAO.getBoardOne(boardNum);
    }

    public void deleteBoard(Integer boardNum) {
        boardDAO.deleteBoard(boardNum);
    }

    public void updateBoard(BoardDTO boardDTO) {
        boardDAO.updateBoard(boardDTO);
    }
}