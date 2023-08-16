package wantedpreonboardingbackend.assignment.domain.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wantedpreonboardingbackend.assignment.domain.Board;
import wantedpreonboardingbackend.assignment.domain.Member;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse {
    private Long id;
    private String author;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static BoardResponse fromEntity(Board board, Member member) {
        BoardResponse response = new BoardResponse();
        response.setId(board.getId());
        response.setAuthor(member.getEmail());
        response.setTitle(board.getTitle());
        response.setContent(board.getContent());
        response.setCreatedAt(board.getCreatedAt());
        response.setUpdatedAt(board.getUpdatedAt());
        return response;
    }
}
