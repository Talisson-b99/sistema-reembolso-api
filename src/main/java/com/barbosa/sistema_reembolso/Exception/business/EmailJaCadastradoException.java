package com.barbosa.sistema_reembolso.Exception.business;

public class EmailJaCadastradoException  extends RuntimeException{
    public EmailJaCadastradoException(String email){
        super("E-mail já cadastrado na base de dados: " + email);
    }
}
