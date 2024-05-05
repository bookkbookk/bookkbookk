package codesquad.bookkbookk.common.type;

import codesquad.bookkbookk.common.error.exception.ReactionNotFoundException;

import lombok.Getter;

@Getter
public enum Reaction {

    LIKE("1F44D"), LOVE("1F495"), CLAP("1F44F"),
    CONGRATULATION("1F389"), ROCKET("1F680");

    private final String unicode;

    Reaction(String unicode) {
        this.unicode = unicode;
    }

    public static Reaction of(String name) {
        String upperCaseName = name.toUpperCase();

        for (Reaction reaction : Reaction.values()) {
            if (reaction.name().equals(upperCaseName)) {
                return reaction;
            }
        }
        throw new ReactionNotFoundException();
    }

}
