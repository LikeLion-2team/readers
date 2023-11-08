package com.project.readers.service;

import com.project.readers.config.SessionConfig;
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

    public void createBoard(BoardDTO boardDTO) throws SecurityException {
        if (SessionConfig.getSessionDTO().getId() == null) {
            throw new SecurityException("회원만 작성할 수 있습니다.");
        }

        boardDTO.setCreator(SessionConfig.getSessionDTO().getId());
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

    public void updateBoard(BoardDTO boardDTO) throws SecurityException {
        String currentUserId = SessionConfig.getSessionDTO().getId();
        String creatorId = boardDTO.getCreator();

        if (currentUserId == null || !currentUserId.equals(creatorId)) {
            throw new SecurityException("게시물의 작성자만 수정할 수 있습니다.");
        }

        // 수정할 내용을 게시물에 반영
        boardDAO.updateBoard(boardDTO);
    }

    public void deleteBoard(Integer boardNum) throws SecurityException {
        BoardDTO boardDTO = boardDAO.getBoardOne(boardNum);

        if (!boardDTO.getCreator().equals(SessionConfig.getSessionDTO().getId())) {
            throw new SecurityException("게시물의 작성자만 삭제할 수 있습니다.");
        }
        boardDAO.deleteBoard(boardNum);
    }
}