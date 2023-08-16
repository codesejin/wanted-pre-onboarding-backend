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
import wantedpreonboardingbackend.assignment.util.ResponseDto;
import wantedpreonboardingbackend.assignment.util.constants.ErrorMessages;
import wantedpreonboardingbackend.assignment.util.exception.UserBadRequestException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final TokenProvider tokenProvider;
    public CreateBoardResponse createBoard(HttpServletRequest request, BoardRequest boardRequest) {
//        Optional<Member> findMember = memberRepository.findByEmail(username);
//        if (findMember.isEmpty()) {
//            throw new UserBadRequestException(ErrorMessages.NOT_FOUND_USER);
//        }
//
//        Member member = findMember.get();

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

    public Page<BoardResponse> getAllBoards(String username, Pageable pageable) {
        Optional<Member> findMember = memberRepository.findByEmail(username);
        if (findMember.isEmpty()) {
            throw new UserBadRequestException(ErrorMessages.NOT_FOUND_USER);
        }
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return boardPage.map(board -> {
            Member member = board.getMember(); // Member 정보 가져오기
            return BoardResponse.fromEntity(board, member);
        });
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
}
