package org.example.view;

import org.example.entity.User;
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

    public View() throws SQLException, NoSuchAlgorithmException {
    }


    public void menu() throws SQLException {
        System.out.println("Welcome to Twitter ");
        System.out.println("1.Signup ");
        System.out.println("2.Login ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
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
                user = userService.signup(displayName, email, username, password, bio);
                if (user == null)
                    System.out.println("Email or Username is already taken. Try another one ");
                else System.out.println("Signup was successful ");
                break;
            case 2:
                System.out.println("1.Login with Email ");
                System.out.println("2.Login with Username ");
                int loginChoice = sc.nextInt();
                switch (loginChoice) {
                    case 1:
                        System.out.println("Enter your Email ");
                        String loginEmail = sc.next();
                        System.out.println("Enter your Password ");
                        String loginPassword = sc.next();
                        user = userService.loginWithEmail(loginEmail, loginPassword);
                        if (user == null)
                            System.out.println("Email or Password is wrong ");
                        else System.out.println("Login was successful ");
                        break;
                    case 2:
                        System.out.println("Enter your Username ");
                        String loginUsername = sc.next();
                        System.out.println("Enter your Password ");
                        String loginPassword2 = sc.next();
                        user = userService.loginWithUsername(loginUsername, loginPassword2);
                        if (user == null)
                            System.out.println("Username or Password is wrong ");
                        else {
                            System.out.println("Login was successful ");
                            System.out.println("Welcome dear " + user.getDisplayName());
                        }
                        while (Authentication.getLoggedInUser() != null)
                            loggedInMenu();
                        break;
                }
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
        switch (choice) {
            case 1:
                System.out.println("Enter your Tweet ");
                text = sc.next();
                userService.postTweet(user, text, 0);
                break;
            case 2:
                userService.viewMyTweets(user);
                System.out.println("1.Delete tweet ");
                System.out.println("2.Edit tweet ");
                System.out.println("3.Back to Previous menu ");
                choiceForTweet = sc.nextInt();
                switch (choiceForTweet) {
                    case 1:
                        System.out.println("Which tweet would you like to delete? ");
                        tweetId = sc.nextInt();
                        if (userService.deleteTweet(user, tweetId))
                            System.out.println("Deleting tweet was successful ");
                        else
                            System.out.println("Tweet not found! ");
                        break;
                    case 2:
                        System.out.println("Which tweet would you like to edit? ");
                        tweetId = sc.nextInt();
                        System.out.println("Enter you new text ");
                        text = sc.next();
                        if (userService.editTweet(user, tweetId, text))
                            System.out.println("Editing tweet was successful ");
                        else
                            System.out.println("Tweet not found! ");
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
                                userService.postTweet(user, text, tweetId);
                        }
                        break;
                    case 2:
                        loggedInMenu();
                        break;
                }
                break;
            case 4:
                break;
            case 5:
                Authentication.logout();
                break;
        }

    }
}
