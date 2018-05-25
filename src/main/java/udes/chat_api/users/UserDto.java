package udes.chat_api.users;

public class UserDto
{
    private String cip;
    private String firstName;
    private String lastName;

    public String getCip()
    {
        return cip;
    }

    public void setCip(String cip)
    {
        this.cip = cip;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
}
