package net.elenx.fishmash.updaters.listeners;

public interface PasswordListener
{
    void passwordChanged();
    void internalError();
    void passwordsAreDifferent();
    void oldPasswordIsWrong();
}
