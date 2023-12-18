package com.dw.discord.controller;

import com.dw.discord.dto.BaseResponse;
import com.dw.discord.dto.BoardDto;
import com.dw.discord.enumstatus.ResultCode;
import com.dw.discord.model.Board;
import com.dw.discord.service.impl.BoardServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:3000",
        methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
public class BoardController {
    private final BoardServiceImpl boardServiceImpl;
    @Autowired
    public BoardController(BoardServiceImpl boardServiceImpl) {
        this.boardServiceImpl = boardServiceImpl;
    }
    //게시글 생성
    @PostMapping("/api/board")
    public ResponseEntity<BaseResponse<Void>> createBoard(@RequestBody @Valid BoardDto boardDto) {
        return new ResponseEntity<>(
                boardServiceImpl.createBoard(boardDto),
                HttpStatus.CREATED);
    }
    //게시글 가져오기
    @GetMapping("/api/board")
    public ResponseEntity<BaseResponse<List<Board>>> getAllBoard() {
        return new ResponseEntity<>(
                boardServiceImpl.getAllBoard(),
                HttpStatus.OK);
    }
    //게시글 삭제
    @DeleteMapping("/api/board/delete/{id}")
    public ResponseEntity<BaseResponse<Long>> deleteBoard(@PathVariable Long id) {
        return new ResponseEntity<>(
                boardServiceImpl.deleteBoard(id),
                HttpStatus.OK);
    }

    //요청정보를 헤더로 이용하는 방법
    @GetMapping("api/board/id")
    public ResponseEntity<BaseResponse<Void>> getTest(@RequestHeader("X-LoginID") String id) {
        return new ResponseEntity<>(
                new BaseResponse<>(ResultCode.SUCCESS.name(),
                        null,
                        id),
                HttpStatus.OK);
    }
}
