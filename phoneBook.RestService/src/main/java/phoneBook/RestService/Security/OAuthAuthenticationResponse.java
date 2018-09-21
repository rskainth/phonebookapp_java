package phoneBook.RestService.Security;

import org.springframework.beans.factory.annotation.Autowired;

import phoneBook.Contracts.BusinessObjects.IPhoneBookXContact;
import phoneBook.Contracts.BusinessObjects.IUser;
import phoneBook.Contracts.ObjectFactory.IBusinessObjectFactory;

public class OAuthAuthenticationResponse {
	
    private String accessToken;
    private String tokenType = "Bearer";
    private CustomUser user;

    public CustomUser getUser() {
		return user;
	}

	public void setUser(CustomUser user) {
		this.user = user;
	}

	public OAuthAuthenticationResponse(String accessToken, CustomUser user) {
        this.accessToken = accessToken;
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}