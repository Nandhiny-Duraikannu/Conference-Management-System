package models;

import lib.Api;
import lib.UserStorage;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Paper review
 */
public class Review {
    public Long id;

    public User user;

    public Paper paper;

    public String content;

    public String status;

    public Long paperId;

    public String paperTitle;

    public String paperFormat;

    public Long paperSize;
}
