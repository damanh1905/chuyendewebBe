package com.example.chuyendeweb.model.response;

public class UserReponse {
    String userName;
    String email;
    String phone;
    String gender;

    public UserReponse(String userName, String email, String phone, String gender) {
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
    }
    

    public UserReponse() {
		super();
	}


	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "UserReponse [ email=" + email + ", gender=" + gender + ", phone=" + phone
                + ", userName=" + userName + "]";
    }

}