package org.example.view;

import org.example.entity.Tag;
import org.example.entity.Tweet;
import org.example.entity.User;
import org.example.exception.*;
import org.example.service.Authentication;
import org.example.service.TagService;
import org.example.service.TweetService;
import org.example.service.UserService;
import org.example.service.impl.TagServiceImpl;
import org.example.service.impl.TweetServiceImpl;
import org.example.service.impl.UserServiceImpl;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

public class View {

    Scanner sc = new Scanner(System.in);
    UserService userService = new UserServiceImpl();
    TweetService tweetService = new TweetServiceImpl();
    TagService tagService = new TagServiceImpl();
    User user;
    boolean isSignUp = false;

    public View() throws SQLException, NoSuchAlgorithmException {
    }

    public void menu() throws SQLException {
        System.out.println("Welcome to Twitter ");
        System.out.println("1.Signup ");
        System.out.println("2.Login ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                while (!isSignUp)
                    tryToSignupMenu();
                break;
            case 2:
                System.out.println("1.Login with Email ");
                System.out.println("2.Login with Username ");
                int loginChoice = sc.nextInt();
                switch (loginChoice) {
                    case 1:
                        tryToLoginWithEmailMenu();
                        while (Authentication.getLoggedInUser() != null)
                            loggedInMenu();
                        break;
                    case 2:
                        tryToLoginWithUsernameMenu();
                        while (Authentication.getLoggedInUser() != null)
                            loggedInMenu();
                        break;
                }
        }
    }

    private void tryToSignupMenu() throws SQLException {
        isSignUp = false;
        System.out.println("Enter your Display Name ");
        String displayName = sc.next();
        System.out.println("Enter your Email ");
        String email = sc.next();
        System.out.println("Enter your Username ");
        String username = sc.next();
        System.out.println("Enter your Password ");
        String password = sc.next();
        System.out.println("Enter your Bio ");
        String bio = sc.next();
        try {
            user = userService.signup(displayName, email, username, password, bio);
            System.out.println("Signup was successful ");
            isSignUp = true;
        } catch (TakenUsernameException | TakenEmailException | IncorrectEmailFormat e) {
            System.out.println(e.getMessage());
        }
    }

    private void tryToLoginWithEmailMenu() {
        System.out.println("Enter your Email ");
        String loginEmail = sc.next();
        System.out.println("Enter your Password ");
        String loginPassword = sc.next();
        try {
            user = userService.loginWithEmail(loginEmail, loginPassword);
            System.out.println("Login was successful ");
            System.out.println("Welcome dear " + user.getDisplayName());
        } catch (UserNotFoundException | IncorrectPasswordException | IncorrectEmailFormat e) {
            System.out.println(e.getMessage());
            tryToLoginWithEmailMenu();
        }
    }

    private void tryToLoginWithUsernameMenu() {
        System.out.println("Enter your Username ");
        String loginUsername = sc.next();
        System.out.println("Enter your Password ");
        String loginPassword = sc.next();
        try {
            user = userService.loginWithUsername(loginUsername, loginPassword);
            System.out.println("Login was successful ");
            System.out.println("Welcome dear " + user.getDisplayName());
        } catch (UserNotFoundException | IncorrectPasswordException e) {
            System.out.println(e.getMessage());
            tryToLoginWithUsernameMenu();
        }
    }

    private void loggedInMenu() throws SQLException {
        System.out.println("1.Post new Tweet ");
        System.out.println("2.View my Tweets ");
        System.out.println("3.View all tweets ");
        System.out.println("4.Edit Profile ");
        System.out.println("5.Logout ");
        int choice = sc.nextInt();
        int choiceForTweet;
        int tweetId;
        String text;
        String tagChoice;
        switch (choice) {
            case 1:
                System.out.println("Enter your Tweet ");
                text = sc.next();
                Tweet tweet = tweetService.postTweet(user, text, 0);
                System.out.println("Do you need tags? (y/n) ");
                tagChoice = sc.next();
                while (tagChoice.equalsIgnoreCase("y")) {
                    tagMenu(tweet.getId());
                    System.out.println("Do you need tags? (y/n) ");
                    tagChoice = sc.next();
                }
                break;
            case 2:
                try {
                    tweetService.viewMyTweets(user);
                } catch (TweetListIsEmptyException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("1.Delete tweet ");
                System.out.println("2.Edit tweet ");
                System.out.println("3.Back to Previous menu ");
                choiceForTweet = sc.nextInt();
                switch (choiceForTweet) {
                    case 1:
                        System.out.println("Which tweet would you like to delete? ");
                        tweetId = sc.nextInt();
                        try {
                            try {
                                tweetService.deleteTweet(user, tweetId);
                                System.out.println("Deleting tweet was successful ");
                            } catch (RuntimeException e) {
                                System.out.println(e.getMessage());
                            }
                        } catch (UserDoesNotOwnTweetException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 2:
                        System.out.println("Which tweet would you like to edit? ");
                        tweetId = sc.nextInt();
                        System.out.println("1.Edit text ");
                        System.out.println("2.Edit tags ");
                        choice = sc.nextInt();
                        switch (choice) {
                            case 1:
                                System.out.println("Enter you new text ");
                                text = sc.next();
                                try {
                                    tweetService.editTweet(user, tweetId, text);
                                    System.out.println("Editing tweet was successful ");
                                } catch (UserDoesNotOwnTweetException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 2:
                                System.out.println("1.add tag ");
                                System.out.println("2.remove tag ");
                                int choice2 = sc.nextInt();
                                switch (choice2) {
                                    case 1:
                                        do {
                                            tagMenu(tweetId);
                                            System.out.println("Do you need tags? (y/n) ");
                                            tagChoice = sc.next();
                                        } while (tagChoice.equalsIgnoreCase("y"));
                                        break;
                                    case 2:
                                        System.out.println("which tag would you like to remove? ");
                                        String tagTitle = sc.next();
                                        try {
                                            tagService.deleteTagForTweet(user, tweetId, tagTitle);
                                        } catch (RuntimeException e) {
                                            System.out.println(e.getMessage());
                                            ;
                                        }
                                        break;
                                }
                        }
                        break;
                    case 3:
                        loggedInMenu();
                        break;
                }
                break;
            case 3:
                tweetService.viewAllTweets();
                System.out.println("1.Choose a Tweet for Like, Dislike or Retweet ");
                System.out.println("2.Back to Previous menu ");
                int choice2 = sc.nextInt();
                switch (choice2) {
                    case 1:
                        System.out.println("Enter your choice ");
                        tweetId = sc.nextInt();
                        System.out.println("1.Like tweet ");
                        System.out.println("2.Dislike tweet ");
                        System.out.println("3.Retweet tweet ");
                        choiceForTweet = sc.nextInt();
                        switch (choiceForTweet) {
                            case 1:
                                tweetService.likeTweet(tweetId);
                                break;
                            case 2:
                                tweetService.dislikeTweet(tweetId);
                                break;
                            case 3:
                                System.out.println("Enter your Tweet");
                                text = sc.next();
                                tweetService.postTweet(user, text, tweetId);
                        }
                        break;
                    case 2:
                        loggedInMenu();
                        break;
                }
                break;
            case 4:
                System.out.println("1.display name ");
                System.out.println("2.email address ");
                System.out.println("3.username ");
                System.out.println("4.password ");
                System.out.println("5.bio ");
                int editChoice = sc.nextInt();
                switch (editChoice) {
                    case 1:
                        System.out.println("Enter your new display name: ");
                        String displayName = sc.next();
                        userService.updateDisplayName(user, displayName);
                        System.out.println("Display name updated");
                        break;
                    case 2:
                        System.out.println("Enter your new email address: ");
                        String email = sc.next();
                        try {
                            userService.updateEmail(user, email);
                            System.out.println("Email updated");
                        }catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        System.out.println("Enter your new username: ");
                        String username = sc.next();
                        try {
                            userService.updateUsername(user, username);
                            System.out.println("Username updated");
                        }catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.println("Enter your old password: ");
                        String oldPassword = sc.next();
                        System.out.println("Enter your new password: ");
                        String newPassword = sc.next();
                        try {
                            userService.updatePassword(user, oldPassword, newPassword);
                            System.out.println("Password updated");
                        }catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 5:
                        System.out.println("Enter your new bio: ");
                        String bio = sc.next();
                        userService.updateBio(user, bio);
                        System.out.println("Bio updated");
                        break;
                }
                break;
            case 5:
                Authentication.logout();
                break;
        }
    }

    private void tagMenu(int tweetId) throws SQLException {
        System.out.println(tagService.findAll());
        System.out.println("1.chose an existing tag ");
        System.out.println("2.create a new tag ");
        int choiceForTweet = sc.nextInt();
        switch (choiceForTweet) {
            case 1:
                System.out.println("Enter tag id ");
                int tagId = sc.nextInt();
                try {
                    tagService.chooseTag(user, tweetId, tagId);
                } catch (TagDoesNotExistException | TweetHasThisTagException e) {
                    System.out.println(e.getMessage());
                    ;
                }
                break;
            case 2:
                System.out.println("Enter tag title: ");
                String tagTitle = sc.next();
                Tag tag;
                try {
                    tag = tagService.createTag(tagTitle);
                    tagService.chooseTag(user, tweetId, tag.getId());
                } catch (TweetHasThisTagException | TagExistsException e) {
                    System.out.println(e.getMessage());
                    ;
                }
                break;
        }
    }
}
