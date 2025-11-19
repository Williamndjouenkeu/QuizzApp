package com.will.quiz.exception;

public class UserNotFoundException extends NotFoundException{
    public UserNotFoundException(String email, String provider) {
        super("Utilisateur avec l'email " + email + " et le provider " + provider + " non trouvé");
    }
}
