package com.project.board.user.validation;

import javax.validation.GroupSequence;

public class ValidationSequenceGroups {

    public interface First {};
    public interface Second {};
    public interface Third {};
    public interface Fourth {};

    @GroupSequence({First.class, Second.class, Third.class, Fourth.class})
    public interface ValidationSeq{};

}
