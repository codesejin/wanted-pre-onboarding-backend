package wantedpreonboardingbackend.assignment.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wantedpreonboardingbackend.assignment.domain.dto.req.BoardRequest;
import wantedpreonboardingbackend.assignment.domain.dto.res.BoardResponse;
import wantedpreonboardingbackend.assignment.domain.dto.res.CreateBoardResponse;
import wantedpreonboardingbackend.assignment.service.BoardService;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api-prefix}/board")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "새로운 게시글 생성",
            tags = {"게시글"})
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping()
    public ResponseEntity<CreateBoardResponse> createBoard(HttpServletRequest httpServletRequest,
                                                           @RequestBody final BoardRequest boardRequest) {
        CreateBoardResponse newBoard = boardService.createBoard(httpServletRequest, boardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBoard);
    }

    @Operation(summary = "게시글 목록 조회",
            tags = {"게시글"})
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/all")
    public ResponseEntity<Page<BoardResponse>> getAllBoards(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<BoardResponse> boardPage = boardService.getAllBoards(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(boardPage);
    }

    @Operation(summary = "특정 게시글 조회",
            tags = {"게시글"})
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> getOneBoard(@PathVariable Long boardId) {
        BoardResponse oneBoard = boardService.getOneBoard(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(oneBoard);
    }

    @Operation(summary = "특정 게시글 수정",
            tags = {"게시글"})
    @ResponseStatus(code = HttpStatus.CREATED)
    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardResponse> updateBoard(HttpServletRequest httpServletRequest,
                                                     @PathVariable Long boardId,
                                                     @RequestBody final BoardRequest boardRequest) {
        BoardResponse boardResponse = boardService.updateBoard(httpServletRequest, boardId, boardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(boardResponse);
    }

    @Operation(summary = "특정 게시글 삭제",
            tags = {"게시글"})
    @ResponseStatus(code = HttpStatus.OK)
    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(HttpServletRequest httpServletRequest,
                                              @PathVariable Long boardId) {
        String deleteMsg = boardService.deleteBoard(httpServletRequest, boardId);
        return ResponseEntity.status(HttpStatus.OK).body(deleteMsg);
    }

}
