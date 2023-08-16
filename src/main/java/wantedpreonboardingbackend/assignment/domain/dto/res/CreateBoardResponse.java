package wantedpreonboardingbackend.assignment.domain.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBoardResponse {

    private String author;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public static CreateBoardResponse createSuccessResponse(String author, String title, String content, LocalDateTime createdAt) {
        String authorMessage = author + " 님, 게시글이 등록되었습니다.";
        return new CreateBoardResponse(authorMessage, title, content, createdAt);
    }
}
