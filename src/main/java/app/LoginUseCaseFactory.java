package app;

import entities.Quiz;
import entities.Users.CommonUserFactory;
import interface_adapter.PlayViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupViewModel;
import usecase.Login.LoginInputBoundary;
import usecase.Login.LoginInteractor;
import usecase.Login.LoginOutputBoundary;
import usecase.Login.LoginUserDataAccessInterface;
import view.LoginView;

import javax.swing.*;
import java.io.IOException;

public class LoginUseCaseFactory {
    /** Prevent instantiation. */
    private LoginUseCaseFactory() {}

    public static LoginView create(
            ViewManagerModel viewManagerModel,
            LoginViewModel loginViewModel,
            LoginUserDataAccessInterface userDataAccessObject,
            PlayViewModel playViewModel, SignupViewModel signupViewModel, Quiz quiz) {

        try {
            LoginController loginController = createLoginUseCase(viewManagerModel, loginViewModel, userDataAccessObject, playViewModel, signupViewModel, quiz);
            return new LoginView(loginViewModel, loginController, playViewModel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file.");
        }

        return null;
    }

    private static LoginController createLoginUseCase(
            ViewManagerModel viewManagerModel,
            LoginViewModel loginViewModel,
            LoginUserDataAccessInterface userDataAccessObject,
            PlayViewModel playViewModel,
            SignupViewModel signupViewModel, Quiz quiz) throws IOException {

        // Notice how we pass this method's parameters to the Presenter.
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, playViewModel, loginViewModel, signupViewModel);

        CommonUserFactory userFactory = new CommonUserFactory();

        LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary, quiz);

        return new LoginController(loginInteractor);
    }
}
