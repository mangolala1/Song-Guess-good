// Main.java
package app;


import dataAccessObjects.UserStorage.FileUserDataAccessObject;
import entities.*;
import entities.PlaylistQuiz;
import entities.Quiz;
import entities.SinglePlayer;
import entities.Users.CommonUserFactory;
import entities.Users.User;
import interface_adapter.PlayViewModel;
import interface_adapter.ViewManagerModel;

import interface_adapter.guess.GuessController;
import interface_adapter.guess.GuessPresenter;
import interface_adapter.login.LoginViewModel;


import interface_adapter.score.ScoreController;
import interface_adapter.score.ScorePresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.timer.TimerController;
import interface_adapter.timer.TimerPresenter;
import usecase.UserAuth.UAuthInputBoundary;
import usecase.UserAuth.UAuthInteractor;
import usecase.UserAuth.UAuthOutputBoundary;
import usecase.UserAuth.UAuthOutputData;
import usecase.guess.GuessInputBoundary;
import usecase.guess.GuessInteractor;
import usecase.guess.GuessOutputBoundary;
import usecase.score.ScoreInputBoundary;
import usecase.score.ScoreInteractor;
import usecase.score.ScoreOutputBoundary;
import usecase.timer.*;

import view.*;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        JFrame application = new JFrame("Song Playback Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);



        CommonUserFactory commonUserFactory= new CommonUserFactory();
        User user = commonUserFactory.createUser("a","b");



        Player player = new SinglePlayer(user);
        Quiz quiz = new PlaylistQuiz(player);

        PlayViewModel playViewModel = new PlayViewModel();

        // Assuming PlayController is correctly implemented and has the required methods

        GuessOutputBoundary guessOutputBoundary = new GuessPresenter(viewManagerModel, playViewModel);
        GuessInputBoundary guessInputBoundary = new GuessInteractor(guessOutputBoundary, quiz);
        GuessController guessController = new GuessController(guessInputBoundary);

        ScoreOutputBoundary scoreOutputBoundary = new ScorePresenter(playViewModel);
        ScoreInputBoundary scoreInputBoundary = new ScoreInteractor(quiz, scoreOutputBoundary);
        ScoreController scoreController = new ScoreController(scoreInputBoundary);

        TimeInputData timeInputData = new TimeInputData();
        TimeOutputData timeOutputData = new TimeOutputData();
        TimeOutputBoundary timeOutputBoundary = new TimerPresenter(playViewModel); // Assuming TimerPresenter is correctly implemented
        TimeInputBoundary timeInteractor = new TimeInteractor(quiz, timeOutputBoundary, timeInputData, timeOutputData);
        TimerController timerController = new TimerController(timeInteractor, timeInputData);

        // Pass the timerController to the PlayView

        LoginViewModel loginViewModel = new LoginViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();

        FileUserDataAccessObject userDataAccessObject;
        try {
            userDataAccessObject = new FileUserDataAccessObject("./users.csv", new CommonUserFactory());
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }


        SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, userDataAccessObject, playViewModel, quiz);



        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, userDataAccessObject, playViewModel, signupViewModel, quiz);

        PlayView playView = new PlayView(scoreController, playViewModel, timerController, guessController);

        // Here, the viewName is a public static final String field in the PlayView class
        views.add(loginView, loginView.viewName);
        views.add(signupView, signupView.viewName);
        //viewManagerModel.setActiveView(UAuthView.viewName);
        viewManagerModel.setActiveView(loginView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        // Set the size of the window
        application.setLocationRelativeTo(null); // Center the window
        application.setVisible(true);
        views.add(playView, PlayView.viewName);
    }


}
