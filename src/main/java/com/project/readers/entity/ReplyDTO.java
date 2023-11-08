package com.project.readers.entity;

import com.project.readers.common.BaseDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data // @Getter + @Setter + @ToString + @EqualsAndHashCode + @RequiredArgsConstructor
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO extends BaseDTO {

    @NotNull
    // 게시글 번호
    private Integer boardNum;

    // 댓글 번호
    private Integer rplNum;

    @NotEmpty
    // 작성자 
    private String creator;

    @NotEmpty
    // 댓글 내용 
    private String rplContent;

    // 뎁스 
    private Integer depth;

    // 작성일,수정일
    private LocalDateTime crtTm, upTm;

}
