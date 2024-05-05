package codesquad.bookkbookk.domain.bookmark.data.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import codesquad.bookkbookk.common.type.Reaction;
import codesquad.bookkbookk.domain.mapping.entity.BookmarkReaction;
import codesquad.bookkbookk.domain.mapping.entity.CommentReaction;

import lombok.Getter;

@Getter
public class ReadReactionsResponse {

    @JsonProperty(value = "like")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<String> likeReactorNames = new ArrayList<>();
    @JsonProperty(value = "love")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<String> loveReactorNames = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(value = "clap")
    private final List<String> clapReactorNames= new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(value = "congratulation")
    private final List<String> congratulationReactorNames= new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(value = "rocket")
    private final List<String> rocketReactorNames= new ArrayList<>();

    public static ReadReactionsResponse fromBookmarkReactions(List<BookmarkReaction> bookmarkReactions) {
        ReadReactionsResponse response = new ReadReactionsResponse();

        bookmarkReactions.forEach(response::addName);
        return response;
    }

    public static ReadReactionsResponse fromCommentReactions(List<CommentReaction> commentReactions) {
        ReadReactionsResponse response = new ReadReactionsResponse();

        commentReactions.forEach(response::addName);
        return response;
    }

    private void addName(BookmarkReaction bookmarkReaction) {
        Reaction reaction = bookmarkReaction.getReaction();

        if (reaction.equals(Reaction.LIKE)) {
            likeReactorNames.add(bookmarkReaction.getReactor().getNickname());
            return;
        }
        if (reaction.equals(Reaction.LOVE)) {
            loveReactorNames.add(bookmarkReaction.getReactor().getNickname());
            return;
        }
        if (reaction.equals(Reaction.CLAP)) {
            clapReactorNames.add(bookmarkReaction.getReactor().getNickname());
            return;
        }
        if (reaction.equals(Reaction.CONGRATULATION)) {
            congratulationReactorNames.add(bookmarkReaction.getReactor().getNickname());
            return;
        }
        if (reaction.equals(Reaction.ROCKET)) {
            rocketReactorNames.add(bookmarkReaction.getReactor().getNickname());
        }
    }

    private void addName(CommentReaction commentReaction) {
        Reaction reaction = commentReaction.getReaction();

        if (reaction.equals(Reaction.LIKE)) {
            likeReactorNames.add(commentReaction.getReactor().getNickname());
            return;
        }
        if (reaction.equals(Reaction.LOVE)) {
            loveReactorNames.add(commentReaction.getReactor().getNickname());
            return;
        }
        if (reaction.equals(Reaction.CLAP)) {
            clapReactorNames.add(commentReaction.getReactor().getNickname());
            return;
        }
        if (reaction.equals(Reaction.CONGRATULATION)) {
            congratulationReactorNames.add(commentReaction.getReactor().getNickname());
            return;
        }
        if (reaction.equals(Reaction.ROCKET)) {
            rocketReactorNames.add(commentReaction.getReactor().getNickname());
        }
    }

}
