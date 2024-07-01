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
import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.member.data.entity.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookmarkReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_reaction_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bookmark_id", nullable = false)
    private Bookmark bookmark;

    @Column(name = "bookmark_id", nullable = false, insertable = false, updatable = false)
    private Long bookmarkId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reactor_id", nullable = false)
    private Member reactor;

    @Column(name = "reactor_id", nullable = false, insertable = false, updatable = false)
    private Long reactorId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Reaction reaction;

    public BookmarkReaction(Bookmark bookmark, Member reactor, Reaction reaction) {
        this.bookmark = bookmark;
        if (bookmark != null) this.bookmarkId = bookmark.getId();
        this.reactor = reactor;
        if (reactor != null) this.reactorId = reactor.getId();
        this.reaction = reaction;
    }

}
