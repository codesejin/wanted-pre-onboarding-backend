package wantedpreonboardingbackend.assignment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wantedpreonboardingbackend.assignment.config.jwt.TokenProvider;
import wantedpreonboardingbackend.assignment.domain.Board;
import wantedpreonboardingbackend.assignment.domain.BoardRepository;
import wantedpreonboardingbackend.assignment.domain.Member;
import wantedpreonboardingbackend.assignment.domain.MemberRepository;
import wantedpreonboardingbackend.assignment.domain.dto.req.BoardRequest;
import wantedpreonboardingbackend.assignment.domain.dto.res.BoardResponse;
import wantedpreonboardingbackend.assignment.domain.dto.res.CreateBoardResponse;
import wantedpreonboardingbackend.assignment.util.constants.ErrorMessages;
import wantedpreonboardingbackend.assignment.util.exception.BoardBadRequestException;
import wantedpreonboardingbackend.assignment.util.exception.UserBadRequestException;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final TokenProvider tokenProvider;
    public CreateBoardResponse createBoard(HttpServletRequest request, BoardRequest boardRequest) {
        Member member = validateMember(request);
        if (member == null) {
            throw new UserBadRequestException(ErrorMessages.BAD_TOKEN);
        }

        Board newBoard = Board.builder()
                .member(member)
                .title(boardRequest.getTitle())
                .content(boardRequest.getContent())
                .build();
        Board savedBoard = boardRepository.save(newBoard);

        return CreateBoardResponse.createSuccessResponse(member.getEmail(), savedBoard.getTitle(), savedBoard.getContent(), savedBoard.getCreatedAt());

    }

    public Page<BoardResponse> getAllBoards(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return boardPage.map(board -> {
            Member member = board.getMember(); // Member 정보 가져오기
            return BoardResponse.fromEntity(board, member);
        });
    }

    public BoardResponse getOneBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardBadRequestException(ErrorMessages.NOT_FOUND_BOARD));
        return BoardResponse.fromEntity(board, board.getMember());

    }

    public BoardResponse updateBoard(HttpServletRequest httpServletRequest, Long boardId, BoardRequest boardRequest) {
        Member member = validateMember(httpServletRequest);
        if (member == null) {
            throw new UserBadRequestException(ErrorMessages.BAD_TOKEN);
        }

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardBadRequestException(ErrorMessages.NOT_FOUND_BOARD));

        // 당사자만 수정할 수 있음
        if (!board.getMember().getId().equals(member.getId())) {
            throw new UserBadRequestException(ErrorMessages.UNAUTHORIZED);
        }

        board.setTitle(boardRequest.getTitle());
        board.setContent(boardRequest.getContent());

        Board updatedBoard = boardRepository.save(board);
        return BoardResponse.fromEntity(updatedBoard, updatedBoard.getMember());

    }

    public Member validateMember(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // "Bearer " 다음의 실제 토큰 값을 추출
            if (!tokenProvider.validateToken(token)) {
                return null;
            }
            return tokenProvider.getMemberFromAuthentication();
        }

        return null; // Bearer 토큰이 아닌 경우 또는 헤더가 없는 경우 처리
    }

    public String deleteBoard(HttpServletRequest httpServletRequest, Long boardId) {
        Member member = validateMember(httpServletRequest);
        if (member == null) {
            throw new UserBadRequestException(ErrorMessages.BAD_TOKEN);
        }

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardBadRequestException(ErrorMessages.NOT_FOUND_BOARD));

        // 당사자만 삭제할 수 있음
        if (!board.getMember().getId().equals(member.getId())) {
            throw new UserBadRequestException(ErrorMessages.UNAUTHORIZED);
        }
        boardRepository.delete(board);
        return "삭제가 완료되었습니다.";
    }
}
