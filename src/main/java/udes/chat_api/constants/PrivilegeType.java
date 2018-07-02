package udes.chat_api.constants;

import org.springframework.stereotype.Service;

@Service
public class PrivilegeType
{
    public static final int admin = 1;
    public static final int moderator = 2;
    public static final int member = 3;
    public static final int muted = 4;
    public static final int banned = 5;
}
