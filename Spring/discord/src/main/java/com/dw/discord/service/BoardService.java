package com.dw.discord.service;

import com.dw.discord.dto.BaseResponse;
import com.dw.discord.dto.BoardDto;
import com.dw.discord.model.Board;

import java.util.List;

public interface BoardService {
	//게시글 생성
    public BaseResponse<Void> createBoard(BoardDto boardDto);
    //게시글 목록
    public BaseResponse<List<Board>> getAllBoard();
    //게시글 삭제
    public BaseResponse<Long> deleteBoard(Long id);
}
