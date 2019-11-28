package info.esblurock.reaction.chemconnect.data.session;

import java.io.Serializable;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;

@Entity
public class UserSessionData implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id  Long key;
	@Index  String sessionID;
	@Index  String userName;
	@Index  String IP;
	@Unindex  Date loginDate;
	@Unindex String hostname;
	@Unindex String userLevel;

	
	public UserSessionData() {
		this.userName = null;
		this.sessionID = null;
		this.IP = null;
		this.hostname = null;
		this.userLevel = null;
		this.loginDate = new Date();
	}
	
	public UserSessionData(String userName, String sessionID, String IP, String hostname,
			String userLevel) {
		this.userName = userName;
		this.sessionID = sessionID;
		this.IP = IP;
		this.hostname = hostname;
		this.userLevel = userLevel;
		this.loginDate = new Date();
	}


	public Long getKey() {
		return key;
	}


	public void setKey(Long key) {
		this.key = key;
	}


	public String getSessionID() {
		return sessionID;
	}


	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getIP() {
		return IP;
	}


	public void setIP(String iP) {
		IP = iP;
	}


	public Date getLoginDate() {
		return loginDate;
	}


	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}


	public String getHostname() {
		return hostname;
	}


	public void setHostname(String hostname) {
		this.hostname = hostname;
	}


	public String getUserLevel() {
		return userLevel;
	}


	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}
	
	public String toString() {
		return toString("");
	}
	
	public String toString(String prefix) {
		StringBuilder build = new StringBuilder();
		build.append("userName:   " + userName + "\n");
		build.append("sessionid:  " + sessionID + "\n");
		build.append("IP:         " + IP + "\n");
		build.append("hostname:   " + hostname + "\n");
		build.append("userLevel:  " + userLevel + "\n");
		build.append("login date:  " + loginDate.toString() + "\n");
		return build.toString();
	}
	

}
