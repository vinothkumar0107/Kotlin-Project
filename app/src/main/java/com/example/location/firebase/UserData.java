package com.example.location.firebase;

public class UserData {
    private static String searchText = "Your body may be chrome, but the heart never changes. It wants what it wants.";;
    private static String targetWord = "wants";
    private String fullName;
   private String email;
   private String password;
   private String mobile;
   private String age;


    public UserData(String name, String email, String password, String mobile_no,String age) {
        this.fullName=name;
        this.email=email;
        this.password=password;
        this.mobile=mobile_no;
        this.age=age;
    }
    public UserData(){

    }



    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public static void main(String[] args)
    {
        String[] words = searchText.replaceAll("\\p{Punct}", "").split(" ");
        int wordCount = 0;
        for (int i = 0; i < words.length; i++)
            if (words[i].equals(targetWord))
                wordCount++;
        System.out.println(wordCount);
    }

}
