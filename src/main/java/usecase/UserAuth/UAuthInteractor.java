package usecase.UserAuth;

import dataAccessObjects.spotifyAccessObjects.RandomSecureS256Generator;
import dataAccessObjects.spotifyAccessObjects.userOAuthObject;

public class UAuthInteractor implements UAuthInputBoundary{

    final UAuthOutputBoundary uAuthOutputBoundary;
    private String URL;
    final String redirectURL = "http://localhost:8080/callback";

    final String client_id = "ee5a4dc5c931462e9e630c64a8aee5ac";

    private final UAuthOutputData uAuthOutputData;


    public UAuthInteractor(UAuthOutputBoundary uAuthOutputBoundary, UAuthOutputData uAuthOutputData) {
        this.uAuthOutputBoundary = uAuthOutputBoundary;
        this.uAuthOutputData = uAuthOutputData;

        userOAuthObject o = new userOAuthObject();
        RandomSecureS256Generator rand = new RandomSecureS256Generator();
        //rand.generateSHA256Hash(rand.generateRandomString());

        //String random = rand.getHash();

        //this.URL = o.userAuthorizationURL(client_id, redirectURL, "user-top-read", random);
    }

    @Override
    public void execute() {
        this.uAuthOutputData.setURL(this.URL);
        this.uAuthOutputBoundary.prepareView();
    }
}
