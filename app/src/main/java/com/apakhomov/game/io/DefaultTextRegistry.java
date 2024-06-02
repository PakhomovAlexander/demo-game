package com.apakhomov.game.io;

import com.apakhomov.game.io.validation.ValidationIssue;

public class DefaultTextRegistry implements TextRegistry {
    private static final String WELCOME_TEXT = """
██████╗  ██████╗  ██████╗██╗  ██╗                           
██╔══██╗██╔═══██╗██╔════╝██║ ██╔╝                           
██████╔╝██║   ██║██║     █████╔╝                            
██╔══██╗██║   ██║██║     ██╔═██╗                            
██║  ██║╚██████╔╝╚██████╗██║  ██╗                           
╚═╝  ╚═╝ ╚═════╝  ╚═════╝╚═╝  ╚═╝                           
██████╗  █████╗ ██████╗ ███████╗██████╗                     
██╔══██╗██╔══██╗██╔══██╗██╔════╝██╔══██╗                    
██████╔╝███████║██████╔╝█████╗  ██████╔╝                    
██╔═══╝ ██╔══██║██╔═══╝ ██╔══╝  ██╔══██╗                    
██║     ██║  ██║██║     ███████╗██║  ██║                    
╚═╝     ╚═╝  ╚═╝╚═╝     ╚══════╝╚═╝  ╚═╝                    
███████╗ ██████╗██╗███████╗███████╗ ██████╗ ██████╗ ███████╗
██╔════╝██╔════╝██║██╔════╝██╔════╝██╔═══██╗██╔══██╗██╔════╝
███████╗██║     ██║███████╗███████╗██║   ██║██████╔╝███████╗
╚════██║██║     ██║╚════██║╚════██║██║   ██║██╔══██╗╚════██║
███████║╚██████╗██║███████║███████║╚██████╔╝██║  ██║███████║
╚══════╝ ╚═════╝╚═╝╚══════╝╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝

            """;

    private static final String YOU_WIN_TEXT = """
██╗   ██╗ ██████╗ ██╗   ██╗    
╚██╗ ██╔╝██╔═══██╗██║   ██║    
 ╚████╔╝ ██║   ██║██║   ██║    
  ╚██╔╝  ██║   ██║██║   ██║    
   ██║   ╚██████╔╝╚██████╔╝    
   ╚═╝    ╚═════╝  ╚═════╝     
██╗    ██╗██╗███╗   ██╗██╗     
██║    ██║██║████╗  ██║██║     
██║ █╗ ██║██║██╔██╗ ██║██║     
██║███╗██║██║██║╚██╗██║╚═╝     
╚███╔███╔╝██║██║ ╚████║██╗     
 ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝     
 
    """;

    private static final String YOU_LOSE_TEXT = """
██╗   ██╗ ██████╗ ██╗   ██╗                          
╚██╗ ██╔╝██╔═══██╗██║   ██║                          
 ╚████╔╝ ██║   ██║██║   ██║                          
  ╚██╔╝  ██║   ██║██║   ██║                          
   ██║   ╚██████╔╝╚██████╔╝                          
   ╚═╝    ╚═════╝  ╚═════╝                           
██╗      ██████╗  ██████╗ ███████╗███████╗        ██╗
██║     ██╔═══██╗██╔═══██╗██╔════╝██╔════╝    ██╗██╔╝
██║     ██║   ██║██║   ██║███████╗█████╗      ╚═╝██║ 
██║     ██║   ██║██║   ██║╚════██║██╔══╝      ██╗██║ 
███████╗╚██████╔╝╚██████╔╝███████║███████╗    ╚═╝╚██╗
╚══════╝ ╚═════╝  ╚═════╝ ╚══════╝╚══════╝        ╚═╝
                                                      
    """;




    @Override
    public String initialText(PromptMsg prompt) {
        return switch (prompt.type()) {
            case ENTER_USERNAME -> WELCOME_TEXT + "Enter your username:";
            case MOVE -> "Enter your move:";
        };
    }

    @Override
    public String invalidInputText(PromptMsg prompt, ValidationIssue issue) {
        return switch (prompt.type()) {
            case ENTER_USERNAME -> issueText(issue) + " Please try again:";
            case MOVE -> issueText(issue) + "Please try again:";
        };
    }

    private String issueText(ValidationIssue issue) {
        return switch (issue.type()) {
            case INVALID_CHARACTER_USERNAME -> "Username is invalid.";
            case ALREADY_EXISTS_USERNAME -> "User with this username already exists.";
        };
    }

    @Override
    public String notificationText(NotificationMsg notification) {
        return switch (notification.type()) {
            case YOU_WIN -> YOU_WIN_TEXT;
            case YOU_LOSE -> YOU_LOSE_TEXT;
            case INFO -> notification.content();
            case ERROR -> "Error: " + notification.content();
        };
    }
}
