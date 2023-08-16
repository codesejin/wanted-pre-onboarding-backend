package wantedpreonboardingbackend.assignment.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
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
    public ResponseEntity<CreateBoardResponse> createBoard(HttpServletRequest request,
                                                           @RequestBody final BoardRequest boardRequest) {
        CreateBoardResponse newBoard = boardService.createBoard(request, boardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBoard);
    }

    @Operation(summary = "게시글 목록 조회",
            tags = {"게시글"})
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping()
    public ResponseEntity<Page<BoardResponse>> getAllBoards(@ApiIgnore @AuthenticationPrincipal User user,
                                                            @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<BoardResponse> boardPage = boardService.getAllBoards(user.getUsername(), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(boardPage);
    }
}
