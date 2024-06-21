package codesquad.bookkbookk.domain.mapping.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import codesquad.bookkbookk.common.type.Reaction;
import codesquad.bookkbookk.domain.comment.data.entity.Comment;
import codesquad.bookkbookk.domain.member.data.entity.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Column(name = "comment_id", nullable = false, insertable = false, updatable = false)
    private Long commentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reactor_id")
    private Member reactor;

    @Column(name = "reactor_id", nullable = false, insertable = false, updatable = false)
    private Long reactorId;

    @Enumerated(value = EnumType.STRING)
    private Reaction reaction;

    public CommentReaction(Comment comment, Member reactor, Reaction reaction) {
        this.comment = comment;
        if (comment != null) this.commentId = comment.getId();
        this.reactor = reactor;
        if (reactor != null) this.reactorId = reactor.getId();
        this.reaction = reaction;
    }

}
